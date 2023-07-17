package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.PlantType;
import twilightforest.init.TFSounds;
import twilightforest.init.TFItems;
import twilightforest.init.TFStats;

public class TorchberryPlantBlock extends TFPlantBlock implements BonemealableBlock {

	public static final BooleanProperty HAS_BERRIES = BooleanProperty.create("has_torchberries");
	private static final VoxelShape TORCHBERRY_SHAPE = Block.box(1, 2, 1, 15, 16, 15);

	public TorchberryPlantBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(HAS_BERRIES, false));
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		return TFPlantBlock.canPlaceRootAt(reader, pos);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return TORCHBERRY_SHAPE;
	}

	@Override
	public PlantType getPlantType(BlockGetter getter, BlockPos pos) {
		return PlantType.CAVE;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if (state.getValue(HAS_BERRIES)) {
			level.setBlockAndUpdate(pos, state.setValue(HAS_BERRIES, false));
			level.playSound(null, pos, TFSounds.PICKED_TORCHBERRIES.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
			ItemEntity torchberries = new ItemEntity(level, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, new ItemStack(TFItems.TORCHBERRIES.get()));
			level.addFreshEntity(torchberries);
			if (player instanceof ServerPlayer) player.awardStat(TFStats.TORCHBERRIES_HARVESTED.get());
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		return super.use(state, level, pos, player, hand, result);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader getter, BlockPos pos, BlockState state, boolean isClient) {
		return !state.getValue(HAS_BERRIES);
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource rand, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
		level.setBlock(pos, state.setValue(HAS_BERRIES, true), 2);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HAS_BERRIES);
	}

}
