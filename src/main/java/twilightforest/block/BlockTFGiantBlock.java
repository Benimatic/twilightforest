package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.PushReaction;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class BlockTFGiantBlock extends Block {

	private boolean isSelfDestructing;

	//Atomic: Suppressing deprecation because this seems like a reasonable use for it.
	@SuppressWarnings("deprecation")
	public BlockTFGiantBlock(BlockState state, float hardness, float resistance) {
		super(Properties.create(state.getMaterial()).hardnessAndResistance(hardness, resistance).sound(state.getBlock().getSoundType(state)));
	}

//	@Override
//	public boolean canPlaceBlockAt(World world, BlockPos pos) {
//		for (BlockPos dPos : getVolume(pos)) {
//			BlockState state = world.getBlockState(dPos);
//			if (!state.getBlock().isReplaceable(world, dPos)) {
//				return false;
//			}
//		}
//		return super.canPlaceBlockAt(world, pos);
//	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (!world.isRemote) {
			for (BlockPos dPos : getVolume(pos)) {
				world.setBlockState(dPos, getDefaultState(), 2);
			}
		}
	}

	@Override
	@Deprecated
	public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
		super.onReplaced(state, world, pos, newState, isMoving);
		if (!this.isSelfDestructing && !canBlockStay(world, pos)) {
			this.setGiantBlockToAir(world, pos);
		}
	}

	private void setGiantBlockToAir(World world, BlockPos pos) {
		// this flag is maybe not totally perfect
		this.isSelfDestructing = true;

		for (BlockPos iterPos : getVolume(pos)) {
			if (!pos.equals(iterPos)) {
				if (world.getBlockState(iterPos).getBlock() == this) {
					world.destroyBlock(iterPos, false);
				}
			}
		}

		this.isSelfDestructing = false;
	}

	private boolean canBlockStay(World world, BlockPos pos) {
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

	public static BlockPos roundCoords(BlockPos pos) {
		return new BlockPos(pos.getX() & ~0b11, pos.getY() & ~0b11, pos.getZ() & ~0b11);
	}

	public static Iterable<BlockPos> getVolume(BlockPos pos) {
		return BlockPos.getAllInBoxMutable(
				pos.getX() & ~0b11, pos.getY() & ~0b11, pos.getZ() & ~0b11,
				pos.getX() |  0b11, pos.getY() |  0b11, pos.getZ() |  0b11
		);
	}
}
