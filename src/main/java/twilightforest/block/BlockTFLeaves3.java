package twilightforest.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.item.TFItems;

public class BlockTFLeaves3 extends BlockLeaves {
	
    public static final String[] names = new String[] {"thorn", "beanstalk"};

	protected BlockTFLeaves3() {
		super();
		this.setStepSound(Block.soundTypeGrass);
		this.setCreativeTab(TFItems.creativeTab);
	}

    public String[] func_150125_e()
    {
        return names;
    }
    
    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta)
    {
        return (meta & 3) == 1 ? ColorizerFoliage.getFoliageColorPine() : ((meta & 3) == 2 ? ColorizerFoliage.getFoliageColorBirch() : super.getRenderColor(meta));
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        return (meta & 3) == 1 ? ColorizerFoliage.getFoliageColorPine() : ((meta & 3) == 2 ? ColorizerFoliage.getFoliageColorBirch() : super.colorMultiplier(world, x, y, z));
    }


    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int p_149692_1_)
    {
        return super.damageDropped(p_149692_1_) + 4;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return Blocks.leaves.isOpaqueCube();
    }

    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDamageValue(World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z) & 3;
    }
    
    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random rand)
    {
        return 0;
    }
    
    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int side)
    {
    	return Blocks.leaves.shouldSideBeRendered(iblockaccess, i, j, k, side);
    }
    
    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
    {
    	return TFItems.magicBeans;
    }
    
    @Override
    public IIcon getIcon(int i, int j)
    {
        return Blocks.leaves.getIcon(i, 0 & 3);
    }
    
	/**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for (int i = 0; i < names.length; i++) {
            par3List.add(new ItemStack(par1, 1, i));
    	}
    }

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) {
		return true;
	}
	
	
}
