package twilightforest.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.block.entity.SkullCandleBlockEntity;

import java.util.List;
import java.util.Map;

public class WallSkullCandleBlock extends AbstractSkullCandleBlock {

	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	//im not doing individual boxes for each candle per facing, the boxes get cut off since the heads are halfway up the wall, and that would be 16!! boxes to make. No thanks.
	private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(ImmutableMap.of(
			Direction.NORTH, Block.box(4.0D, 4.0D, 8.0D, 12.0D, 12.0D, 16.0D),
			Direction.SOUTH, Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 8.0D),
			Direction.EAST, Block.box(0.0D, 4.0D, 4.0D, 8.0D, 12.0D, 12.0D),
			Direction.WEST, Block.box(8.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D)));

	private static final Int2ObjectMap<List<Vec3>> PARTICLE_OFFSETS = Util.make(() -> {
		Int2ObjectMap<List<Vec3>> var0 = new Int2ObjectOpenHashMap<>();
		var0.defaultReturnValue(ImmutableList.of());
		var0.put(1, ImmutableList.of(new Vec3(0.5D, 1.25D, 0.5D)));
		var0.put(2, ImmutableList.of(new Vec3(0.375D, 1.19D, 0.5D), new Vec3(0.625D, 1.25D, 0.44D)));
		var0.put(3, ImmutableList.of(new Vec3(0.5D, 1.063D, 0.625D), new Vec3(0.375D, 1.19D, 0.5D), new Vec3(0.56D, 1.25D, 0.44D)));
		var0.put(4, ImmutableList.of(new Vec3(0.44D, 1.063D, 0.56D), new Vec3(0.625D, 1.19D, 0.56D), new Vec3(0.375D, 1.19D, 0.375D), new Vec3(0.56D, 1.25D, 0.375D)));
		return Int2ObjectMaps.unmodifiable(var0);
	});

	public WallSkullCandleBlock(SkullBlock.Type type, Properties properties) {
		super(type, properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
	}

	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return AABBS.get(state.getValue(FACING));
	}

	@Override
	protected Iterable<Vec3> getParticleOffsets(BlockState state, LevelAccessor accessor, BlockPos pos) {
		if (accessor.getBlockEntity(pos) instanceof SkullCandleBlockEntity sc) {
			return PARTICLE_OFFSETS.get(sc.candleAmount);
		}
		return PARTICLE_OFFSETS.get(1);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		BlockState state = this.defaultBlockState();
		BlockGetter getter = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();
		Direction[] directions = ctx.getNearestLookingDirections();

		for (Direction dir : directions) {
			if (dir.getAxis().isHorizontal()) {
				Direction var10 = dir.getOpposite();
				state = state.setValue(FACING, var10);
				if (!getter.getBlockState(pos.relative(dir)).canBeReplaced(ctx)) {
					return state.setValue(LIGHTING, Lighting.NONE);
				}
			}
		}
		return null;
	}

	//we want the candle flames to offset based on which way the skull is facing, so apply it here
	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
		Direction dir = state.getValue(FACING);
		if (state.getValue(LIGHTING) != Lighting.NONE) {
			this.getParticleOffsets(state, level, pos).forEach((offset) ->
					addParticlesAndSound(level, offset.add(pos.getX() - (float) dir.getStepX() * 0.25F, pos.getY(), pos.getZ() - (float) dir.getStepZ() * 0.25F), rand, state.getValue(LIGHTING) == Lighting.OMINOUS));
		}
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}
}
