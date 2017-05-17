package twilightforest.block;

import mcp.MethodsReturnNonnullByDefault;
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
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.ParametersAreNullableByDefault;
import java.util.List;
import java.util.Random;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockTFTrophyPedestal extends Block {
	// BossVariant.NONE for inactive. Anything else that is a Boss is active. Minibosses filtered out
	public static final PropertyEnum<BossVariant> VARIANT = PropertyEnum.create("boss", BossVariant.class, BossVariant::hasTrophy);

	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);

	public BlockTFTrophyPedestal() {
		super(Material.ROCK);
		this.setHardness(2.0F);
		this.setResistance(2000.0F);
        this.setSoundType(SoundType.STONE);
		
		this.setCreativeTab(TFItems.creativeTab);
	}
    
    /*@Override // Not needed if there's only 1 item
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        //par3List.add(new ItemStack(par1, 1, 15)); // y tho
    }*/
    
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
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block) {
        if (!world.isRemote && state.getValue(VARIANT) != BossVariant.NONE) {
        	// something has changed, is there a trophy on top of us?
        	if (isTrophyOnTop(world, pos)) {
        		world.scheduleBlockUpdate(pos, this, 1, this.tickRate(world));
        	}
        }
        
    }

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
			if (this.isTrophyOnTop(world, pos) && state.getValue(VARIANT) == BossVariant.NONE) { // Activate
				// check enforced progression
				if (world.getGameRules().getBoolean(TwilightForestMod.ENFORCED_PROGRESSION_RULE)) {
					if (this.areNearbyPlayersEligible(world, pos)) doPedestalEffect(world, pos, state);
					// warn players whether it works or not
					warnIneligiblePlayers(world, pos);
				} else {
					// just do it if enforced progression is turned off
					doPedestalEffect(world, pos, state);
				}

				rewardNearbyPlayers(world, pos);
			} else if (!this.isTrophyOnTop(world, pos) && (state.getValue(VARIANT) != BossVariant.NONE)) { // Deactivate
				world.setBlockState(pos, state.withProperty(VARIANT, BossVariant.NONE));
			}
    	}
    }

	private void warnIneligiblePlayers(World world, BlockPos pos) {
		for (EntityPlayer player : world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).expand(16.0D, 16.0D, 16.0D)))
			if (!isPlayerEligible(player)) player.sendMessage(new TextComponentString("You are unworthy."));
	}

	private boolean areNearbyPlayersEligible(World world, BlockPos pos) {
		for (EntityPlayer player : world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).expand(16.0D, 16.0D, 16.0D)))
			if (isPlayerEligible(player))
				return true;
		
		return false;
	}

	private boolean isPlayerEligible(EntityPlayer player) {
		return ((player instanceof EntityPlayerMP) && ((EntityPlayerMP)player).getStatFile().hasAchievementUnlocked(TFAchievementPage.twilightProgressTrophyPedestal.parentAchievement)) ||
				((player instanceof EntityPlayerSP) && ((EntityPlayerSP)player).getStatFileWriter().hasAchievementUnlocked(TFAchievementPage.twilightProgressTrophyPedestal.parentAchievement));
	}

	private void doPedestalEffect(World world, BlockPos pos, IBlockState state) {
		// if we're a latent pedestal, change to a non-latent!
		// TODO integrate BossVariant into the trophy and change this below
		world.setBlockState(pos, state.withProperty(VARIANT, BossVariant.NAGA));

		// remove shield blocks
		removeNearbyShields(world, pos);

		// change this block meta to be non-latent
		// world.setBlockState(pos, state, 2);

		// sound
		//world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "mob.zombie.infect", 4.0F, 0.1F);
	}

	private void rewardNearbyPlayers(World world, BlockPos pos) {
		for (EntityPlayer player : world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).expand(16.0D, 16.0D, 16.0D)))
			player.addStat(TFAchievementPage.twilightProgressTrophyPedestal);
	}

    /**
     * Remove shield blocks near the specified coordinates
     */
	protected void removeNearbyShields(World world, BlockPos pos) {
		for (int sx = -5; sx <= 5; sx++)
			for (int sy = -5; sy <= 5; sy++)
				for (int sz = -5; sz <= 5; sz++)
					if (world.getBlockState(pos.add(sx, sy, sz)) == TFBlocks.shield) {
						world.setBlockState(pos.add(sx, sy, sz), Blocks.AIR.getDefaultState(), 2);

						// TODO sound effect
						// world.playAuxSFX(2001, pos.add(sx, sy, sz), Block.getIdFromBlock(blockAt) + (metaAt << 12));
					}
	}

    @Override
	public int tickRate(World world)
    {
        return 10;
    }
    
    @Override
	public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World world, BlockPos pos) {
		return state.getValue(VARIANT) != BossVariant.NONE ? -1 : super.getPlayerRelativeBlockHardness(state, player, world, pos);
    }


}
