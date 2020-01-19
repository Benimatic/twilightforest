package twilightforest.block;

import net.minecraft.block.material.PushReaction;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.state.IProperty;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTFThorns extends BlockTFConnectableRotatedPillar {

	private static final float THORN_DAMAGE = 4.0F;

	BlockTFThorns(Properties props) {
		super(props, 10);
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

//	@Override
//	protected boolean canConnectTo(BlockState state, BlockState otherState, IBlockAccess world, BlockPos pos, Direction connectTo) {
//		return (otherState.getBlock() instanceof BlockTFThorns
//				|| otherState.getBlock() == TFBlocks.thorn_rose
//				||(otherState.getBlock() == TFBlocks.twilight_leaves_3 && otherState.getValue(BlockTFLeaves3.VARIANT) == Leaves3Variant.THORN)
//				|| otherState.getMaterial() == Material.GRASS
//				|| otherState.getMaterial() == Material.GROUND)
//				|| super.canConnectTo(state, otherState, world, pos, connectTo);
//	}

	@Nullable
	@Override
	public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
		return PathNodeType.DAMAGE_CACTUS;
	}

	@Override
	@Deprecated
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entity) {
		entity.attackEntityFrom(DamageSource.CACTUS, THORN_DAMAGE);
	}

	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity) {
		BlockState state = world.getBlockState(pos);

		if (state.getBlock() instanceof BlockTFThorns && state.get(AXIS) == Direction.Axis.Y)
			onEntityCollision(state, world, pos, entity);

		super.onEntityWalk(world, pos, entity);
	}

	@Override
	public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
		if (!player.abilities.isCreativeMode) {
			if (!world.isRemote) {
				// grow back
				world.setBlockState(pos, state, 2);
				// grow more
				this.doThornBurst(world, pos, state);
			}
		} else {
			world.removeBlock(pos, false);
		}

		return true;
	}

	@Override
	@Deprecated
	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}

	/**
	 * Grow thorns out of both the ends, then maybe in another direction too
	 */
	private void doThornBurst(World world, BlockPos pos, BlockState state) {
		switch (state.get(AXIS)) {
			case Y:
				growThorns(world, pos, Direction.UP);
				growThorns(world, pos, Direction.DOWN);
				break;
			case X:
				growThorns(world, pos, Direction.EAST);
				growThorns(world, pos, Direction.WEST);
				break;
			case Z:
				growThorns(world, pos, Direction.NORTH);
				growThorns(world, pos, Direction.SOUTH);
				break;
		}

		// also try three random directions
		growThorns(world, pos, Direction.random(world.rand));
		growThorns(world, pos, Direction.random(world.rand));
		growThorns(world, pos, Direction.random(world.rand));
	}

	/**
	 * grow several green thorns in the specified direction
	 */
	private void growThorns(World world, BlockPos pos, Direction dir) {
		int length = 1 + world.rand.nextInt(3);

		for (int i = 1; i < length; i++) {
			BlockPos dPos = pos.offset(dir, i);

			if (world.isAirBlock(dPos)) {
				world.setBlockState(dPos, TFBlocks.green_thorns.get().getDefaultState().with(AXIS, dir.getAxis()), 2);
			} else {
				break;
			}
		}
	}

//	@Override
//	public void breakBlock(World world, BlockPos pos, BlockState state) {
//		int range = 4;
//		int exRange = range + 1;
//
//		if (world.isAreaLoaded(pos, exRange)) {
//			for (BlockPos pos_ : WorldUtil.getAllAround(pos, range)) {
//				BlockState state_ = world.getBlockState(pos_);
//				if (state_.getBlock().isLeaves(state_, world, pos_)) {
//					state.getBlock().beginLeavesDecay(state_, world, pos_);
//				}
//			}
//		}
//	}

//	@Override
//	public int quantityDropped(Random random) {
//		return 0;
//	}

	//TODO: This is likely to be handled by a Tag now
//	@Override
//	public boolean canSustainLeaves(BlockState state, IBlockAccess world, BlockPos pos) {
//		return true;
//	}

	//TODO: Move to client
//	@OnlyIn(Dist.CLIENT)
//	@Override
//	public BlockRenderLayer getRenderLayer() {
//		return BlockRenderLayer.CUTOUT;
//	}

	//TODO: Removed. Check client
//	@Override
//	@Deprecated
//	public boolean doesSideBlockRendering(BlockState blockState, IEnviromentBlockReader blockAccess, BlockPos pos, Direction side) {
//		return (blockAccess.getBlockState(pos.offset(side)).getBlock() instanceof BlockTFThorns || shouldSideBeRendered(blockState, blockAccess, pos, side));
//	}

	@Override
	protected IProperty[] getAdditionalProperties() {
		return new IProperty[0];
	}
}
