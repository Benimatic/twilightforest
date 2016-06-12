package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.entity.passive.EntityTFTinyFirefly;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFFireflyJar extends Block {
	
	public static IIcon jarTop;
	public static IIcon jarSide;
	public static IIcon jarCork;
	
	protected BlockTFFireflyJar() {
		super(Material.GLASS);
		this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
		this.setHardness(0.3F);
		this.setStepSound(Block.soundTypeWood);
		this.setCreativeTab(TFItems.creativeTab);
		this.setLightLevel(1.0F);
	}


    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
	public boolean renderAsNormalBlock()
    {
        return false;
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
     * The type of render function that is called for this block
     */
    @Override
	public int getRenderType()
    {
    	return TwilightForestMod.proxy.getComplexBlockRenderID();
    }
    
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
	public IIcon getIcon(int side, int meta)
    {
    	return (side == 1 || side == 0) ? jarTop : jarSide;        
    }

    /**
     * Get a light value for this block, normal ranges are between 0 and 15
     * 
     * @param world The current world
     * @param x X Position
     * @param y Y position
     * @param z Z position
     * @return The light value
     */
    @Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) 
    {
    	return 15;
    }
    
    /**
     * Return true if the block is a normal, solid cube.  This
     * determines indirect power state, entity ejection from blocks, and a few
     * others.
     * 
     * @param world The current world
     * @param x X Position
     * @param y Y position
     * @param z Z position
     * @return True if the block is a full cube
     */
    public boolean isBlockNormalCube(World world, int x, int y, int z) 
    {
    	return false;
    }
    
    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
    	this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
    }


    /**
     * Sets the block's bounds for rendering it as an item
     */
    @Override
	public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
    }

    
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {


    	double dx = x + ((rand.nextFloat() - rand.nextFloat()) * 0.3F + 0.5F);
    	double dy = y - 0.1F + ((rand.nextFloat() - rand.nextFloat()) * 0.4F);
    	double dz = z + ((rand.nextFloat() - rand.nextFloat()) * 0.3F + 0.5F);

    	EntityTFTinyFirefly tinyfly = new EntityTFTinyFirefly(world, dx, dy, dz);
    	world.addWeatherEffect(tinyfly);

    	dx = x + ((rand.nextFloat() - rand.nextFloat()) * 0.3F + 0.5F);
    	dy = y - 0.1F + ((rand.nextFloat() - rand.nextFloat()) * 0.4F);
    	dz = z + ((rand.nextFloat() - rand.nextFloat()) * 0.3F + 0.5F);

    	tinyfly = new EntityTFTinyFirefly(world, dx, dy, dz);
    	world.addWeatherEffect(tinyfly);
    }


    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
    }


    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        BlockTFFireflyJar.jarTop = par1IconRegister.registerIcon(TwilightForestMod.ID + ":fireflyjar_top");
        BlockTFFireflyJar.jarSide = par1IconRegister.registerIcon(TwilightForestMod.ID + ":fireflyjar_side");
        BlockTFFireflyJar.jarCork = par1IconRegister.registerIcon(TwilightForestMod.ID + ":fireflyjar_cork");
    }


}
