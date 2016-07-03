package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.math.BlockPos;
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
        super(Material.GLASS);
        this.setHardness(50.0F);
        this.setResistance(2000.0F);
        this.setStepSound(Block.soundTypeMetal);
		this.setCreativeTab(TFItems.creativeTab);

    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

	@Override
    public int tickRate(World world)
    {
        return 15;
    }
    
	@Override
	public Item getItemDropped(IBlockState state, Random par2Random, int par3) {
		return null;
	}
	
    @Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World par1World, BlockPos pos)
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

    @Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess par1IBlockAccess, BlockPos pos)
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
    
    @Override
	public float getBlockHardness(IBlockState state, World world, BlockPos pos)
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
	public boolean isPassable(IBlockAccess par1IBlockAccess, BlockPos pos)
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
			Block toMimic = sideRNG.nextBoolean() ? (sideRNG.nextBoolean() ? Blocks.PORTAL : Blocks.NETHERRACK) : (sideRNG.nextBoolean() ? Blocks.BEDROCK : Blocks.OBSIDIAN);
			return toMimic.getIcon(side, meta);
		case META_FAKE_GOLD:
			return Blocks.GOLD_BLOCK.getIcon(side, meta);
		case META_FAKE_DIAMOND:
			return Blocks.DIAMOND_BLOCK.getIcon(side, meta);
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
	
    @Override
	public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            int meta = par1World.getBlockMetadata(x, y, z);

            if (meta == META_BUILT_ACTIVE)
            {
            	par1World.setBlock(x, y, z, Blocks.AIR, 0, 3);
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

    @Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List) {}

}
