package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BlockTFTowerTranslucent extends Block 
{

	public static final int META_REAPPEARING_INACTIVE = 0;
	public static final int META_REAPPEARING_ACTIVE = 1;
	public static final int META_BUILT_INACTIVE = 2;
	public static final int META_BUILT_ACTIVE = 3;
	public static final int META_REVERTER_REPLACEMENT = 4;
	public static final int META_REACTOR_DEBRIS = 5;
	public static final int META_FAKE_GOLD = 6;
	public static final int META_FAKE_DIAMOND = 7;
	
	public static IIcon TEX_REAPPEARING_INACTIVE;
	public static IIcon TEX_REAPPEARING_ACTIVE;
	public static IIcon TEX_BUILT_INACTIVE;
	public static IIcon TEX_BUILT_ACTIVE;
	public static IIcon TEX_REVERTER_REPLACEMENT;
	
	private static Random sideRNG = new Random(); 
	
    public BlockTFTowerTranslucent()
    {
        super(Material.glass);
        this.setHardness(50.0F);
        this.setResistance(2000.0F);
        this.setStepSound(Block.soundTypeMetal);
		this.setCreativeTab(TFItems.creativeTab);

    }
    
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 15;
    }
    
    /**
     * Returns the ID of the items to drop on destruction.
     */
	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return null;
	}
	
    /**
     * Return true if a player with Silk Touch can harvest this block directly, and not its normal drops.
     */
    @Override
	protected boolean canSilkHarvest()
    {
        return false;
    }
	
    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		int meta = par1World.getBlockMetadata(par2, par3, par4) & 7;

		if (meta == META_REAPPEARING_INACTIVE || meta == META_REAPPEARING_ACTIVE)
		{
			return null;
		}
		else
		{
			this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
			return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
		}
	}


    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int meta = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

        if (meta == META_REAPPEARING_INACTIVE || meta == META_REAPPEARING_ACTIVE)
        {
            this.setBlockBounds(0.375F, 0.375F, 0.375F, 0.625F, 0.625F, 0.625F);
        }
        if (meta == META_REACTOR_DEBRIS)
        {
            this.setBlockBounds(sideRNG.nextFloat() * 0.4F, sideRNG.nextFloat() * 0.4F, sideRNG.nextFloat() * 0.4F, 
            		1.0F - sideRNG.nextFloat() * 0.4F, 1.0F - sideRNG.nextFloat() * 0.4F, 1.0F - sideRNG.nextFloat() * 0.4F);
        }
        else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }
    
    /**
     * Returns the block hardness at a location. Args: world, x, y, z
     */
    @Override
	public float getBlockHardness(World world, int x, int y, int z)
    {
    	// reverter replacement is like glass
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if (meta == META_REVERTER_REPLACEMENT || meta == META_REACTOR_DEBRIS)
    	{
    		return 0.3F;
    	}
    	else
    	{
    		return super.getBlockHardness(world, x, y, z);
    	}
    }
    



    @Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
    	int meta = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

    	switch (meta)
    	{
    	case META_REAPPEARING_INACTIVE:
    	case META_REAPPEARING_ACTIVE:
    	default:
    		return false;
    	case META_BUILT_INACTIVE:
    	case META_BUILT_ACTIVE:
    	case META_REVERTER_REPLACEMENT:
    	case META_REACTOR_DEBRIS:
    		return true;
    	}    
    }


    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
	@Override
	public IIcon getIcon(int side, int meta) {
		switch (meta)
		{
		case META_REAPPEARING_INACTIVE:
		default:
			return TEX_REAPPEARING_INACTIVE;
		case META_REAPPEARING_ACTIVE:
			return TEX_REAPPEARING_ACTIVE;
		case META_BUILT_INACTIVE:
			return TEX_BUILT_INACTIVE;
		case META_BUILT_ACTIVE:
			return TEX_BUILT_ACTIVE;
		case META_REVERTER_REPLACEMENT:
			return TEX_REVERTER_REPLACEMENT;
		case META_REACTOR_DEBRIS:
			Block toMimic = sideRNG.nextBoolean() ? (sideRNG.nextBoolean() ? Blocks.portal : Blocks.netherrack) : (sideRNG.nextBoolean() ? Blocks.bedrock : Blocks.obsidian);
			return toMimic.getIcon(side, meta);
		case META_FAKE_GOLD:
			return Blocks.gold_block.getIcon(side, meta);
		case META_FAKE_DIAMOND:
			return Blocks.diamond_block.getIcon(side, meta);
		}
	}
	
	
    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        BlockTFTowerTranslucent.TEX_REAPPEARING_INACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_reappearing_trace_off");
        BlockTFTowerTranslucent.TEX_REAPPEARING_ACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_reappearing_trace_on");
        BlockTFTowerTranslucent.TEX_BUILT_INACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_built_off");
        BlockTFTowerTranslucent.TEX_BUILT_ACTIVE = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_built_on");
        BlockTFTowerTranslucent.TEX_REVERTER_REPLACEMENT = par1IconRegister.registerIcon(TwilightForestMod.ID + ":towerdev_antibuilt");
    }
	
    /**
     * Ticks the block if it's been scheduled
     */
    @Override
	public void updateTick(World par1World, int x, int y, int z, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            int meta = par1World.getBlockMetadata(x, y, z);

            if (meta == META_BUILT_ACTIVE)
            {
            	par1World.setBlock(x, y, z, Blocks.air, 0, 3);
                par1World.notifyBlocksOfNeighborChange(x, y, z, this);
                par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.pop", 0.3F, 0.5F);
                //par1World.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
                
                // activate all adjacent inactive vanish blocks
                BlockTFTowerDevice.checkAndActivateVanishBlock(par1World, x - 1, y, z);
                BlockTFTowerDevice.checkAndActivateVanishBlock(par1World, x + 1, y, z);
                BlockTFTowerDevice.checkAndActivateVanishBlock(par1World, x, y + 1, z);
                BlockTFTowerDevice.checkAndActivateVanishBlock(par1World, x, y - 1, z);
                BlockTFTowerDevice.checkAndActivateVanishBlock(par1World, x, y, z + 1);
                BlockTFTowerDevice.checkAndActivateVanishBlock(par1World, x, y, z - 1);

            }
            if (meta == META_REAPPEARING_ACTIVE)
            {
            	par1World.setBlock(x, y, z, TFBlocks.towerDevice, BlockTFTowerDevice.META_REAPPEARING_INACTIVE, 3);
                par1World.notifyBlocksOfNeighborChange(x, y, z, this);
                par1World.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.click", 0.3F, 0.5F);
                //par1World.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
             }
            else if (meta == META_REAPPEARING_INACTIVE)
            {
            	BlockTFTowerDevice.changeToActiveVanishBlock(par1World, x, y, z, META_REAPPEARING_ACTIVE);
            }

        }
    }

	
 	/**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	// none!
    }

}
