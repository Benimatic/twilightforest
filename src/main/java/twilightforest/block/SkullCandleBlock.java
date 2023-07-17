package twilightforest.block;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.block.entity.SkullCandleBlockEntity;

import java.util.List;

public class SkullCandleBlock extends AbstractSkullCandleBlock {

	public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;

	protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
	protected static final VoxelShape ONE_CANDLE = Block.box(7.0D, 8.0D, 7.0D, 9.0D, 14.0D, 9.0D);
	protected static final VoxelShape TWO_CANDLE = Block.box(5.0D, 8.0D, 6.0D, 11.0D, 14.0D, 9.0D);
	protected static final VoxelShape THREE_CANDLE = Block.box(5.0D, 8.0D, 6.0D, 10.0D, 14.0D, 11.0D);
	protected static final VoxelShape FOUR_CANDLE = Block.box(5.0D, 8.0D, 5.0D, 11.0D, 14.0D, 10.0D);
	protected static final VoxelShape SKULL_WITH_ONE = Shapes.or(SHAPE, ONE_CANDLE);
	protected static final VoxelShape SKULL_WITH_TWO = Shapes.or(SHAPE, TWO_CANDLE);
	protected static final VoxelShape SKULL_WITH_THREE = Shapes.or(SHAPE, THREE_CANDLE);
	protected static final VoxelShape SKULL_WITH_FOUR = Shapes.or(SHAPE, FOUR_CANDLE);

	private static final Int2ObjectMap<List<Vec3>> PARTICLE_OFFSETS = Util.make(() -> {
		Int2ObjectMap<List<Vec3>> var0 = new Int2ObjectOpenHashMap<>();
		var0.defaultReturnValue(ImmutableList.of());
		var0.put(1, ImmutableList.of(new Vec3(0.5D, 1.0D, 0.5D)));
		var0.put(2, ImmutableList.of(new Vec3(0.375D, 0.94D, 0.5D), new Vec3(0.625D, 1.0D, 0.44D)));
		var0.put(3, ImmutableList.of(new Vec3(0.5D, 0.813D, 0.625D), new Vec3(0.375D, 0.94D, 0.5D), new Vec3(0.56D, 1.0D, 0.44D)));
		var0.put(4, ImmutableList.of(new Vec3(0.44D, 0.813D, 0.56D), new Vec3(0.625D, 0.94D, 0.56D), new Vec3(0.375D, 0.94D, 0.375D), new Vec3(0.56D, 1.0D, 0.375D)));
		return Int2ObjectMaps.unmodifiable(var0);
	});

	public SkullCandleBlock(SkullBlock.Type type, Properties properties) {
		super(type, properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(ROTATION, 0));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext ctx) {
		return switch (getter.getBlockEntity(pos) instanceof SkullCandleBlockEntity sc ? sc.candleAmount : 1) {
			default -> SKULL_WITH_ONE;
			case 2 -> SKULL_WITH_TWO;
			case 3 -> SKULL_WITH_THREE;
			case 4 -> SKULL_WITH_FOUR;
		};
	}

	@Override
	protected Iterable<Vec3> getParticleOffsets(BlockState state, LevelAccessor accessor, BlockPos pos) {
		return PARTICLE_OFFSETS.get(accessor.getBlockEntity(pos) instanceof SkullCandleBlockEntity sc ? sc.candleAmount : 1);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(ROTATION, Mth.floor((double) (ctx.getRotation() * 16.0F / 360.0F) + 0.5D) & 15).setValue(LIGHTING, Lighting.NONE);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(ROTATION, rot.rotate(state.getValue(ROTATION), 16));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.setValue(ROTATION, mirror.mirror(state.getValue(ROTATION), 16));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ROTATION);
	}
}
