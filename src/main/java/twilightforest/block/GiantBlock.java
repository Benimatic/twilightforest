package twilightforest.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class GiantBlock extends Block {

	private boolean isSelfDestructing;

	public GiantBlock(Properties props) {
		super(props);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		for (BlockPos dPos : getVolume(ctx.getClickedPos())) {
			if (!ctx.getLevel().getBlockState(dPos).canBeReplaced(ctx)) {
				return null;
			}
		}
		return defaultBlockState();
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (!world.isClientSide) {
			for (BlockPos dPos : getVolume(pos)) {
				world.setBlockAndUpdate(dPos, defaultBlockState());
			}
		}
	}

	@Override
	@Deprecated
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		super.onRemove(state, world, pos, newState, isMoving);
		if (!this.isSelfDestructing && !isVolumeFilled(world, pos)) {
			this.setGiantBlockToAir(world, pos);
		}
	}

	private void setGiantBlockToAir(Level world, BlockPos pos) {
		// prevent mutual infinite recursion
		this.isSelfDestructing = true;

		for (BlockPos iterPos : getVolume(pos)) {
			if (!pos.equals(iterPos) && world.getBlockState(iterPos).getBlock() == this) {
				world.destroyBlock(iterPos, false);
			}
		}

		this.isSelfDestructing = false;
	}

	private boolean isVolumeFilled(Level world, BlockPos pos) {
		for (BlockPos dPos : getVolume(pos)) {
			if (world.getBlockState(dPos).getBlock() != this) {
				return false;
			}
		}
		return true;
	}

	@Override
	@Deprecated
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}

	public static Iterable<BlockPos> getVolume(BlockPos pos) {
		return BlockPos.betweenClosed(
				pos.getX() & ~0b11, pos.getY() & ~0b11, pos.getZ() & ~0b11,
				pos.getX() |  0b11, pos.getY() |  0b11, pos.getZ() |  0b11
		);
	}
}
