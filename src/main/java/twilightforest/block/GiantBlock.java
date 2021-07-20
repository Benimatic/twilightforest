package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.PushReaction;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GiantBlock extends Block {

	private boolean isSelfDestructing;

	public GiantBlock(Properties props) {
		super(props);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		for (BlockPos dPos : getVolume(ctx.getPos())) {
			if (!ctx.getWorld().getBlockState(dPos).isReplaceable(ctx)) {
				return null;
			}
		}
		return getDefaultState();
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (!world.isRemote) {
			for (BlockPos dPos : getVolume(pos)) {
				world.setBlockState(dPos, getDefaultState());
			}
		}
	}

	@Override
	@Deprecated
	public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
		super.onReplaced(state, world, pos, newState, isMoving);
		if (!this.isSelfDestructing && !isVolumeFilled(world, pos)) {
			this.setGiantBlockToAir(world, pos);
		}
	}

	private void setGiantBlockToAir(World world, BlockPos pos) {
		// prevent mutual infinite recursion
		this.isSelfDestructing = true;

		for (BlockPos iterPos : getVolume(pos)) {
			if (!pos.equals(iterPos) && world.getBlockState(iterPos).getBlock() == this) {
				world.destroyBlock(iterPos, false);
			}
		}

		this.isSelfDestructing = false;
	}

	private boolean isVolumeFilled(World world, BlockPos pos) {
		for (BlockPos dPos : getVolume(pos)) {
			if (world.getBlockState(dPos).getBlock() != this) {
				return false;
			}
		}
		return true;
	}

	@Override
	@Deprecated
	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}

	public static Iterable<BlockPos> getVolume(BlockPos pos) {
		return BlockPos.getAllInBoxMutable(
				pos.getX() & ~0b11, pos.getY() & ~0b11, pos.getZ() & ~0b11,
				pos.getX() |  0b11, pos.getY() |  0b11, pos.getZ() |  0b11
		);
	}
}
