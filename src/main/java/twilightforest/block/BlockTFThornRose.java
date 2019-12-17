package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;

public class BlockTFThornRose extends Block {

	private static final float RADIUS = 0.4F;
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.5F - RADIUS, 0.5F - RADIUS, 0.5F - RADIUS, 0.5F + RADIUS, .5F + RADIUS, 0.5F + RADIUS);

	protected BlockTFThornRose() {
		super(Properties.create(Material.PLANTS).hardnessAndResistance(10.0F, 0.0F).sound(SoundType.PLANT));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

	@Override
	@Deprecated
	public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
		return AABB;
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
	@Deprecated
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockState state, BlockPos pos, Direction face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return canBlockStay(world, pos);
	}

	@Override
	@Deprecated
	public AxisAlignedBB getCollisionBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	@Deprecated
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		if (!canBlockStay(world, pos)) {
			world.destroyBlock(pos, true);
		}
	}

	private boolean canBlockStay(World world, BlockPos pos) {
		for (Direction dir : Direction.VALUES) {
			BlockPos pos_ = pos.offset(dir);
			BlockState state = world.getBlockState(pos_);

			if (state.getBlock().canSustainLeaves(state, world, pos_)) {
				return true;
			}
		}
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
}
