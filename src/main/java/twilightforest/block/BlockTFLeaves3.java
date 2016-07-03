package twilightforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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

    @Override
    public int damageDropped(IBlockState state)
    {
        return super.damageDropped(p_149692_1_) + 4;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return Blocks.LEAVES.isOpaqueCube();
    }

    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDamageValue(World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z) & 3;
    }
    
    @Override
    public int quantityDropped(Random rand)
    {
        return 0;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess iblockaccess, BlockPos pos, EnumFacing side)
    {
    	return Blocks.LEAVES.shouldSideBeRendered(state, iblockaccess, pos, side);
    }
    
    @Override
	public Item getItemDropped(IBlockState state, Random par2Random, int par3)
    {
    	return TFItems.magicBeans;
    }
    
    @Override
    public IIcon getIcon(int i, int j)
    {
        return Blocks.LEAVES.getIcon(i, 0 & 3);
    }
    
	@Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
    	for (int i = 0; i < names.length; i++) {
            par3List.add(new ItemStack(par1, 1, i));
    	}
    }

	@Override
	public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}
	
	
}
