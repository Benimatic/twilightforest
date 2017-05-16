package twilightforest.block;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.TFAchievementPage;
import twilightforest.TwilightForestMod;
import twilightforest.block.enums.BossVariant;
import twilightforest.item.TFItems;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNullableByDefault;
import java.util.List;
import java.util.Random;

public class BlockTFTrophyPedestal extends Block {
	// BossVariant.NONE for inactive. Anything else that is a Boss is active. Minibosses filtered out
	public static final PropertyEnum<BossVariant> VARIANT = PropertyEnum.create("boss", BossVariant.class, (Predicate<BossVariant>) input -> input == null || input.hasTrophy());

	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);

	public BlockTFTrophyPedestal() {
		super(Material.ROCK);
		this.setHardness(2.0F);
		this.setResistance(2000.0F);
        this.setSoundType(SoundType.STONE);
		
		this.setCreativeTab(TFItems.creativeTab);
	}
    
    @Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 15));
    }
    
    @Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
    	return AABB;
    }
    
    @Override
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return true;
    }
    
    @Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block) {
        if (!world.isRemote && state.getValue(VARIANT) != BossVariant.NONE) {
        	// something has changed, is there a trophy on top of us?
        	if (isTrophyOnTop(world, pos)) {
        		world.scheduleBlockUpdate(pos, this, 1, this.tickRate(world));
        	}
        }
        
    }
    
    /*@Override Players always place regular pedestal. Activates when head placed on it
	public void onBlockPlacedBy(World par1World, BlockPos pos, IBlockState state, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        int facing = MathHelper.floor(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        
        int latent = par6ItemStack.getItemDamage() & 8;

        if (facing == 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, latent, 2);
        }

        if (facing == 1)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 1 | latent, 2);
        }

        if (facing == 2)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3 | latent, 2);
        }

        if (facing == 3)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2 | latent, 2);
        }
    }*/
    
    /**
     * Just checks if there is a trophy above the position indicated.  Perhaps in the future we can accept different kinds of trophies.
     */
    private boolean isTrophyOnTop(World world, BlockPos pos) {
		return world.getBlockState(pos.up()).getBlock() == TFBlocks.trophy;
	}

	/**
     * Ticks the block if it's been scheduled
     */
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random par5Random) {
		if (!world.isRemote) {
			//int meta = world.getBlockMetadata(x, y, z);

			if (this.isTrophyOnTop(world, pos)) {
				// is the pedestal still "latent"?
				if (state.getValue(VARIANT) != BossVariant.NONE) {
					// check enforced progression
					if (world.getGameRules().getBoolean(TwilightForestMod.ENFORCED_PROGRESSION_RULE)) {
						if (this.areNearbyPlayersEligible(world, pos)) {
							doPedestalEffect(world, pos, state);
						}
						// warn players whether it works or not
						warnIneligiblePlayers(world, pos);
					} else {
						// just do it if enforced progression is turned off
						doPedestalEffect(world, pos, state);
					}
				}

				rewardNearbyPlayers(world, pos);
			}
    	}
    }

	private void warnIneligiblePlayers(World world, BlockPos pos) {
		// scan for players nearby to give the achievement
		List<EntityPlayer> nearbyPlayers = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).expand(16.0D, 16.0D, 16.0D));
		
		for (EntityPlayer player : nearbyPlayers) {
			if (!isPlayerEligible(player)) {
				player.sendMessage(new TextComponentString("You are unworthy."));
			}
		}
	}

	private boolean areNearbyPlayersEligible(World world, BlockPos pos) {
		boolean isEligible = false;
		// scan for players nearby to give the achievement
		List<EntityPlayer> nearbyPlayers = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).expand(16.0D, 16.0D, 16.0D));
		
		for (EntityPlayer player : nearbyPlayers) {
			isEligible |= isPlayerEligible(player);
		}
		
		return isEligible;
	}

	private boolean isPlayerEligible(EntityPlayer player) {
		if (player instanceof EntityPlayerMP && ((EntityPlayerMP)player).getStatFile() != null) {
			StatisticsManager stats = ((EntityPlayerMP)player).getStatFile();
			
			return stats.hasAchievementUnlocked(TFAchievementPage.twilightProgressTrophyPedestal.parentAchievement);
		} else if (player instanceof EntityPlayerSP && ((EntityPlayerSP)player).getStatFileWriter() != null) {
			StatisticsManager stats = ((EntityPlayerSP)player).getStatFileWriter();
			
			return stats.hasAchievementUnlocked(TFAchievementPage.twilightProgressTrophyPedestal.parentAchievement);
		}
		return false;
	}

	private void doPedestalEffect(World world, BlockPos pos, IBlockState state) {
		// if we're a latent pedestal, change to a non-latent!

		// remove shield blocks
		removeNearbyShields(world, pos);

		// change this block meta to be non-latent
		// world.setBlockState(pos, state, 2);

		// sound
		//world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "mob.zombie.infect", 4.0F, 0.1F);
	}

    @SuppressWarnings("unchecked")
	private void rewardNearbyPlayers(World world, BlockPos pos) {
		// scan for players nearby to give the achievement
		List<EntityPlayer> nearbyPlayers = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).expand(16.0D, 16.0D, 16.0D));
		
		for (EntityPlayer player : nearbyPlayers) {
			player.addStat(TFAchievementPage.twilightProgressTrophyPedestal);
		}
	}

    /**
     * Remove shield blocks near the specified coordinates
     */
	protected void removeNearbyShields(World world, BlockPos pos) {
		for (int sx = -5; sx <= 5; sx++) {
			for (int sy = -5; sy <= 5; sy++) {
				for (int sz = -5; sz <= 5; sz++) {
		    		IBlockState blockAt = world.getBlockState(pos.add(sx, sy, sz));
		    		
		    		if (blockAt == TFBlocks.shield) {
		    			world.setBlockState(pos.add(sx, sy, sz), Blocks.AIR.getDefaultState(), 2);
		    			
		    			// TODO sound effect
		    			// world.playAuxSFX(2001, pos.add(sx, sy, sz), Block.getIdFromBlock(blockAt) + (metaAt << 12));
		    		}
				}
			}
		}
	}

    @Override
	public int tickRate(World world)
    {
        return 10;
    }
    
    @Override
	@ParametersAreNullableByDefault
	public float getPlayerRelativeBlockHardness(@Nonnull IBlockState state, EntityPlayer player, World world, BlockPos pos) {
		return state.getValue(VARIANT) != BossVariant.NONE? -1 : super.getPlayerRelativeBlockHardness(state, player, world, pos);
    }


}
