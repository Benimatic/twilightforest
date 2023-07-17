package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStats;

public class TrollRootBlock extends Block {

	protected static final VoxelShape AABB = Shapes.create(new AABB(0.1, 0.0, 0.1, 0.9, 1.0, 0.9));

	public TrollRootBlock(Properties properties) {
		super(properties);
	}

	public static boolean canPlaceRootBelow(LevelReader reader, BlockPos pos) {
		BlockState state = reader.getBlockState(pos);

		return state.is(BlockTags.BASE_STONE_OVERWORLD) || state.is(TFBlocks.TROLLVIDR.get()) || state.is(TFBlocks.TROLLBER.get()) || state.is(TFBlocks.UNRIPE_TROLLBER.get());
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if (state.is(TFBlocks.TROLLBER.get())) {
			level.setBlock(pos, TFBlocks.TROLLVIDR.get().defaultBlockState(), 2);
			level.playSound(null, pos, TFSounds.PICKED_TORCHBERRIES.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
			ItemEntity torchberries = new ItemEntity(level, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, new ItemStack(TFItems.TORCHBERRIES.get()));
			level.addFreshEntity(torchberries);
			if (player instanceof ServerPlayer) player.awardStat(TFStats.TORCHBERRIES_HARVESTED.get());
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		return super.use(state, level, pos, player, hand, result);
	}

	@Override
	@Deprecated
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		return canPlaceRootBelow(reader, pos.above());
	}

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return AABB;
	}

	@Override
	@Deprecated
	public BlockState updateShape(BlockState state, Direction dirToNeighbor, BlockState neighborState, LevelAccessor accessor, BlockPos pos, BlockPos neighborPos) {
		if (dirToNeighbor == Direction.UP) {
			return canSurvive(state, accessor, pos) ? state : Blocks.AIR.defaultBlockState();
		}
		return state;
	}
}
