package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;

import java.util.List;
import java.util.Random;

public class BlockTFTrollRoot extends Block implements IShearable {

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.1, 0.0, 0.1, 0.9, 1.0, 0.9);

	protected BlockTFTrollRoot() {
		super(Properties.create(Material.PLANTS).sound(SoundType.PLANT).tickRandomly().doesNotBlockMovement());
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		NonNullList<ItemStack> ret = NonNullList.create();
		ret.add(new ItemStack(this));
		return ret;
	}

	private boolean canBlockStay(World world, BlockPos pos) {
		return canPlaceRootBelow(world, pos.up());
	}

	public static boolean canPlaceRootBelow(World world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		Block blockAbove = state.getBlock();

		return state.getMaterial() == Material.ROCK || blockAbove == TFBlocks.trollvidr || blockAbove == TFBlocks.trollber || blockAbove == TFBlocks.unripe_trollber;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return super.canPlaceBlockAt(world, pos) && this.canBlockStay(world, pos);
	}

	@Override
	@Deprecated
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	@Deprecated
	public AxisAlignedBB getCollisionBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	@Deprecated
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, BlockState state, BlockPos pos, Direction face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	@Deprecated
	public boolean isOpaqueCube(BlockState state) {
		return false;
	}

	@Override
	@Deprecated
	public boolean isFullCube(BlockState state) {
		return false;
	}

	@Override
	public void updateTick(World world, BlockPos pos, BlockState state, Random rand) {
		this.checkAndDropBlock(world, pos);
	}

	@Override
	@Deprecated
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		this.checkAndDropBlock(world, pos);
	}

	private void checkAndDropBlock(World world, BlockPos pos) {
		if (!this.canBlockStay(world, pos)) {
			world.destroyBlock(pos, true);
		}
	}

	@Override
	public int quantityDropped(BlockState state, int fortune, Random random) {
		return 0;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
}
