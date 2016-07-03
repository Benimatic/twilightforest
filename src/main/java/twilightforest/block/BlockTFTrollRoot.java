package twilightforest.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class BlockTFTrollRoot extends Block implements IShearable {

	protected BlockTFTrollRoot() {
		super(Material.PLANTS);
        this.setTickRandomly(true);
		this.setCreativeTab(TFItems.creativeTab);
		this.setSoundType(SoundType.PLANT);
		
        this.setBlockTextureName(TwilightForestMod.ID + ":troll_root");

	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this));
        return ret;
	}

	public boolean canBlockStay(World world, int x, int y, int z) {
    	return canPlaceRootBelow(world, x, y + 1, z);
    }
    
    public static boolean canPlaceRootBelow(World world, int x, int y, int z) {
    	Block blockAbove = world.getBlock(x, y, z);
    	
    	return blockAbove.getMaterial() == Material.ROCK || blockAbove == TFBlocks.trollVidr || blockAbove == TFBlocks.trollBer || blockAbove == TFBlocks.unripeTrollBer;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z);
    }
    
    @Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World par1World, BlockPos pos) {
    	return NULL_AABB;
    }
    
    @Override
	public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
	@Override
	public int getRenderType() {
        return 1;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        this.checkAndDropBlock(world, pos);
    }
	
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block) {
        this.checkAndDropBlock(world, pos);
    }
    
    /**
     * checks if the block can stay, if not drop as item
     */
    protected void checkAndDropBlock(World world, BlockPos pos) {
        if (!this.canBlockStay(world, x, y, z)) {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        	world.setBlockToAir(x, y, z);
        }
    }
    
    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return 0;
    }
}
