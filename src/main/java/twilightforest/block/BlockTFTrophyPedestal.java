package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.IIcon;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFTrophyPedestal extends Block {

	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);

	private IIcon sprTopActive;
	private IIcon sprTop;
	private IIcon sprBottom;
	private IIcon sprNagaActive;
	private IIcon sprNaga;
	private IIcon sprLichActive;
	private IIcon sprLich;
	private IIcon sprHydraActive;
	private IIcon sprHydra;
	private IIcon sprUrghastActive;
	private IIcon sprUrghast;

	public BlockTFTrophyPedestal() {
		super(Material.ROCK);
		this.setHardness(2.0F);
		this.setResistance(2000.0F);
        this.setSoundType(SoundType.STONE);
		
		this.setCreativeTab(TFItems.creativeTab);
	}
	
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
	public IIcon getIcon(int side, int meta)
    {
    	
    	if (side == 1)
    	{
    		return meta > 7 ? sprTopActive : sprTop;
    	}
    	else if (side >= 2 && side <= 5)
    	{
    		// determine rotated side to show
        	int rotate = meta & 3;
        	int rotatedSide = ((side - 2) + rotate) % 4;
        	
        	switch (rotatedSide)
        	{
        	case 0:
        		return meta > 7 ? sprNagaActive : sprNaga;
        	case 1:
        		return meta > 7 ? sprLichActive : sprLich;
        	case 2:
        		return meta > 7 ? sprHydraActive : sprHydra;
        	case 3:
        		return meta > 7 ? sprUrghastActive : sprUrghast;
        	}
        	
    	}
//    	if (side == 2)
//    	{
//    		return meta > 7 ? sprNagaActive : sprNaga;
//    	}
//    	if (side == 3)
//    	{
//    		return meta > 7 ? sprLichActive : sprLich;
//    	}
//    	if (side == 4)
//    	{
//    		return meta > 7 ? sprHydraActive : sprHydra;
//    	}
//    	if (side == 5)
//    	{
//    		return meta > 7 ? sprUrghastActive : sprUrghast;
//    	}
    	
    	return sprTop;
    }
    
	/**
	 * Properly register icon source
	 */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
    	this.sprTopActive = par1IconRegister.registerIcon(TwilightForestMod.ID + ":pedestal_top_active");
    	this.sprTop = par1IconRegister.registerIcon(TwilightForestMod.ID + ":pedestal_top");
    	this.sprBottom = par1IconRegister.registerIcon(TwilightForestMod.ID + ":pedestal_top");
    	this.sprNagaActive = par1IconRegister.registerIcon(TwilightForestMod.ID + ":pedestal_naga_active");
    	this.sprNaga = par1IconRegister.registerIcon(TwilightForestMod.ID + ":pedestal_naga");
    	this.sprLichActive = par1IconRegister.registerIcon(TwilightForestMod.ID + ":pedestal_lich_active");
    	this.sprLich = par1IconRegister.registerIcon(TwilightForestMod.ID + ":pedestal_lich");
    	this.sprHydraActive = par1IconRegister.registerIcon(TwilightForestMod.ID + ":pedestal_hydra_active");
    	this.sprHydra = par1IconRegister.registerIcon(TwilightForestMod.ID + ":pedestal_hydra");
    	this.sprUrghastActive = par1IconRegister.registerIcon(TwilightForestMod.ID + ":pedestal_urghast_active");
    	this.sprUrghast = par1IconRegister.registerIcon(TwilightForestMod.ID + ":pedestal_urghast");
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
	public int getRenderType()
    {
    	return TwilightForestMod.proxy.getPedestalBlockRenderID();
    }

    @Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
    }
    
    @Override
	public void neighborChanged(IBlockState state, World par1World, BlockPos pos, Block myBlockID)
    {
        int meta = par1World.getBlockMetadata(x, y, z);

        if (!par1World.isRemote && meta > 0)
        {
        	// something has changed, is there a trophy on top of us?
        	if (isTrophyOnTop(par1World, x, y, z))
        	{
        		par1World.scheduleBlockUpdate(x, y, z, this, 1);
        	}
        }
        
    }
    
    @Override
	public void onBlockPlacedBy(World par1World, BlockPos pos, IBlockState state, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        int facing = MathHelper.floor(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        
        int latent = par6ItemStack.getItemDamage() & 8;

        if (facing == 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 0 | latent, 2);
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
    }
    
    /**
     * Just checks if there is a trophy above the position indicated.  Perhaps in the future we can accept different kinds of trophies.
     */
    private boolean isTrophyOnTop(World world, int x, int y, int z) {
		return world.getBlock(x, y + 1, z) == TFBlocks.trophy;
	}

	/**
     * Ticks the block if it's been scheduled
     */
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random par5Random)
    {
		if (!world.isRemote) {
			int meta = world.getBlockMetadata(x, y, z);

			if (this.isTrophyOnTop(world, x, y, z)) {
				// is the pedestal still "latent"?
				if (meta > 7) {
					// check enforced progression
					if (world.getGameRules().getBoolean(TwilightForestMod.ENFORCED_PROGRESSION_RULE)) {
						if (this.areNearbyPlayersEligible(world, x, y, z)) {
							doPedestalEffect(world, x, y, z, meta);
						}
						// warn players whether it works or not
						warnIneligiblePlayers(world, x, y, z);
					} else {
						// just do it if enforced progression is turned off
						doPedestalEffect(world, x, y, z, meta);
					}
				}

				rewardNearbyPlayers(world, x, y, z);
			}
    	}
    }

	private void warnIneligiblePlayers(World world, int x, int y, int z) {
		// scan for players nearby to give the achievement
		List<EntityPlayer> nearbyPlayers = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1).expand(16.0D, 16.0D, 16.0D));
		
		for (EntityPlayer player : nearbyPlayers) {
			if (!isPlayerEligible(player)) {
				player.sendMessage(new TextComponentString("You are unworthy."));
			}
		}
	}

	private boolean areNearbyPlayersEligible(World world, int x, int y, int z) {
		boolean isEligible = false;
		// scan for players nearby to give the achievement
		List<EntityPlayer> nearbyPlayers = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1).expand(16.0D, 16.0D, 16.0D));
		
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

	private void doPedestalEffect(World world, int x, int y, int z, int meta) {
		// if we're a latent pedestal, change to a non-latent!
			// remove shield blocks
			removeNearbyShields(world, x, y, z);

			// change this block meta to be non-latent
			world.setBlockMetadataWithNotify(x, y, z, meta & 7, 2);

			// sound
			world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "mob.zombie.infect", 4.0F, 0.1F);
	}

    @SuppressWarnings("unchecked")
	private void rewardNearbyPlayers(World world, int x, int y, int z) {
		// scan for players nearby to give the achievement
		List<EntityPlayer> nearbyPlayers = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1).expand(16.0D, 16.0D, 16.0D));
		
		for (EntityPlayer player : nearbyPlayers) {
			player.addStat(TFAchievementPage.twilightProgressTrophyPedestal);
		}
	}

    /**
     * Remove shield blocks near the specified coordinates
     */
	protected void removeNearbyShields(World world, int x, int y, int z) {
		for (int sx = -5; sx <= 5; sx++)
		{
			for (int sy = -5; sy <= 5; sy++)
			{
				for (int sz = -5; sz <= 5; sz++)
				{
		    		Block blockAt = world.getBlock(x + sx, y + sy, z + sz);
		    		int metaAt = world.getBlockMetadata(x + sx, y + sy, z + sz);
		    		
		    		if (blockAt == TFBlocks.shield && metaAt == 15)
		    		{
		    			world.setBlock(x + sx, y + sy, z + sz, Blocks.AIR, 0, 2);
		    			
		    			// effect
		    			world.playAuxSFX(2001, x + sx, y + sy, z + sz, Block.getIdFromBlock(blockAt) + (metaAt << 12));
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
	public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World world, BlockPos pos)
    {
    	// not breakable if meta > 0
		int meta = world.getBlockMetadata(x, y, z);
    	
        if (meta > 0)
        {
        	return -1;
        }
        else
        {
        	return super.getPlayerRelativeBlockHardness(par1EntityPlayer, world, x, y, z);
        }
    }


}
