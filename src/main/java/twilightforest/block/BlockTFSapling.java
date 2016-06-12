package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import twilightforest.world.TFGenCanopyTree;
import twilightforest.world.TFGenDarkCanopyTree;
import twilightforest.world.TFGenHollowTree;
import twilightforest.world.TFGenLargeRainboak;
import twilightforest.world.TFGenMangroveTree;
import twilightforest.world.TFGenMinersTree;
import twilightforest.world.TFGenSmallRainboak;
import twilightforest.world.TFGenSmallTwilightOak;
import twilightforest.world.TFGenSortingTree;
import twilightforest.world.TFGenTreeOfTime;
import twilightforest.world.TFGenTreeOfTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTFSapling extends BlockSapling
{

	private IIcon[] icons;
	private String[] iconNames = new String[] {"sapling_oak", "sapling_canopy", "sapling_mangrove", "sapling_darkwood", "sapling_hollow_oak", "sapling_time", "sapling_transformation", "sapling_mining", "sapling_sorting", "sapling_rainboak"};

	protected BlockTFSapling() {
		super();
        float var3 = 0.4F;
        this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3);
		this.setCreativeTab(TFItems.creativeTab);
	}
	
    /**
     * Ticks the block if it's been scheduled
     */
    @Override
	public void updateTick(World par1World, int x, int y, int z, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            //this.checkFlowerChange(par1World, x, y, z);

            if (par1World.getBlockLightValue(x, y + 1, z) >= 9 && par5Random.nextInt(7) == 0)
            {
                this.func_149878_d(par1World, x, y, z, par5Random);
            }
        }
    }


    /**
     * Attempts to grow a sapling into a tree
     */
    @Override
	public void func_149878_d(World world, int x, int y, int z, Random rand) {
    	int meta = world.getBlockMetadata(x, y, z);
    	WorldGenerator treeGenerator = null;
    	int var8 = 0;
    	int var9 = 0;
    	boolean largeTree = false;

    	if (meta == 1)
    	{
    		treeGenerator = new TFGenCanopyTree(true);
    	}
    	else if (meta == 2)
    	{
    		treeGenerator = new TFGenMangroveTree(true);
    	}
    	else if (meta == 3)
    	{
    		treeGenerator = new TFGenDarkCanopyTree(true);
    	}
    	else if (meta == 4)
    	{
    		treeGenerator = new TFGenHollowTree(true);
    	}
    	else if (meta == 5)
    	{
    		treeGenerator = new TFGenTreeOfTime(true);
    	}
    	else if (meta == 6)
    	{
    		treeGenerator = new TFGenTreeOfTransformation(true);
    	}
    	else if (meta == 7)
    	{
    		treeGenerator = new TFGenMinersTree(true);
    	}
    	else if (meta == 8)
    	{
    		treeGenerator = new TFGenSortingTree(true);
    	}
    	else if (meta == 9)
    	{
    		treeGenerator = rand.nextInt(7) == 0 ? new TFGenLargeRainboak(true) : new TFGenSmallRainboak(true);
    	}
    	else
    	{
    		treeGenerator = new TFGenSmallTwilightOak(true);
    	}

    	if (largeTree)
    	{
    		world.setBlock(x + var8, y, z + var9, Blocks.AIR, 0, 4);
    		world.setBlock(x + var8 + 1, y, z + var9, Blocks.AIR, 0, 4);
    		world.setBlock(x + var8, y, z + var9 + 1, Blocks.AIR, 0, 4);
    		world.setBlock(x + var8 + 1, y, z + var9 + 1, Blocks.AIR, 0, 4);
    	}
    	else
    	{
    		world.setBlock(x, y, z, Blocks.AIR, 0, 4);
    	}

    	if (!treeGenerator.generate(world, rand, x + var8, y, z + var9))
    	{
    		if (largeTree)
    		{
    			world.setBlock(x + var8, y, z + var9, this, meta, 4);
    			world.setBlock(x + var8 + 1, y, z + var9, this, meta, 4);
    			world.setBlock(x + var8, y, z + var9 + 1, this, meta, 4);
    			world.setBlock(x + var8 + 1, y, z + var9 + 1, this, meta, 4);
    		}
    		else
    		{
    			world.setBlock(x, y, z, this, meta, 4);
    		}
    	}
    }
    
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
	public IIcon getIcon(int side, int metadata)
    {
    	if (metadata < this.icons.length)
    	{
    		return this.icons[metadata];
    	}
    	else
    	{
    		return null;
    	}
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.icons = new IIcon[iconNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = par1IconRegister.registerIcon(TwilightForestMod.ID + ":" + iconNames[i]);
        }
    }

	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	@Override
	public int damageDropped(int par1)
	{
        return par1;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
        par3List.add(new ItemStack(par1, 1, 4));
        par3List.add(new ItemStack(par1, 1, 5));
        par3List.add(new ItemStack(par1, 1, 6));
        par3List.add(new ItemStack(par1, 1, 7));
        par3List.add(new ItemStack(par1, 1, 8));
        par3List.add(new ItemStack(par1, 1, 9));
    }

}
