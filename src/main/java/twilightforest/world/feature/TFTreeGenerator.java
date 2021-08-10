package twilightforest.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.material.Material;
import twilightforest.block.TFBlocks;
import twilightforest.util.FeatureLogic;
import twilightforest.util.FeatureUtil;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;

// FIXME There's scars from at least 3 different ports, it may be high time to completely re-write this class soon. This comment was written mid-port for 1.17.1
public abstract class TFTreeGenerator<T extends TFTreeFeatureConfig> extends Feature<T> {

//	protected BlockState treeState = TFBlocks.twilight_log.getDefaultState();
//	protected BlockState branchState = TFBlocks.twilight_log.getDefaultState().with(BlockTFLog.LOG_AXIS, BlockLog.EnumAxis.NONE).with(BlockTFLog.VARIANT, WoodVariant.DARK);
//	protected BlockState leafState = TFBlocks.hedge.getDefaultState().with(BlockTFHedge.VARIANT, HedgeVariant.DARKWOOD_LEAVES);
//	protected BlockState rootState = TFBlocks.root.getDefaultState();
//
//	protected IPlantable source = TFBlocks.twilight_sapling;

//	public TFTreeGenerator() {
//		this(false);
//	}
//
//	public TFTreeGenerator(boolean notify) {
//		super(notify);
//	}

	public TFTreeGenerator(Codec<T> configIn) {
		super(configIn);
	}

//	@Override
//	public final void setBlockAndNotify(World world, BlockPos pos, BlockState state) {
//		setBlockAndNotifyAdequately(world, pos, state);
//	}

	//TODO: Figure out how to get this working
//	@Override
//	protected boolean canGrowInto(Block blockType) {
//		return TFGenHollowTree.canGrowInto(blockType);
//	}

	/*protected boolean generate(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> logpos, Set<BlockPos> leavespos, BoundingBox mbb, T config) {
		Set<BlockPos> branchSet = Sets.newHashSet();
		Set<BlockPos> rootSet = Sets.newHashSet();
		return generate(world, random, pos, logpos, leavespos, branchSet, rootSet, mbb, config);
	}*/

	//VanillaCopy TreeFeature.place, swapped TreeConfiguration for generic T. Omitted code are commented out instead of deleted
	@Override
	public final boolean place(FeaturePlaceContext<T> context) {
		WorldGenLevel contextWorldGenLevel = context.level();
		Random contextRandom = context.random();
		BlockPos contextBlockPos = context.origin();
		T contextConfig = context.config();
		//Set<BlockPos> trunkSet = Sets.newHashSet();
		//Set<BlockPos> leavesSet = Sets.newHashSet();
		//Set<BlockPos> decorationSet = Sets.newHashSet();
		BiConsumer<BlockPos, BlockState> trunkPlacer = (pos, state) -> {
			//trunkSet.add(pos.immutable());
			contextWorldGenLevel.setBlock(pos, state, 19);
		};
		BiConsumer<BlockPos, BlockState> leavesPlacer = (pos, state) -> {
			//leavesSet.add(pos.immutable());
			contextWorldGenLevel.setBlock(pos, state, 19);
		};
		BiConsumer<BlockPos, BlockState> decorationPlacer = (pos, state) -> {
			//decorationSet.add(pos.immutable());
			contextWorldGenLevel.setBlock(pos, state, 19);
		};

		boolean placementSuccess = this.generate(contextWorldGenLevel, contextRandom, contextBlockPos, trunkPlacer, leavesPlacer, decorationPlacer, contextConfig);

		// Instead of continuing, return instead
		return placementSuccess;

		/*if (placementSuccess && (!trunkSet.isEmpty() || !leavesSet.isEmpty())) {
			*//*if (!contextConfig.decorators.isEmpty()) {
				List<BlockPos> trunkList = Lists.newArrayList(trunkSet);
				List<BlockPos> leavesList = Lists.newArrayList(leavesSet);
				trunkList.sort(Comparator.comparingInt(Vec3i::getY));
				leavesList.sort(Comparator.comparingInt(Vec3i::getY));
				contextConfig.decorators.forEach((treeDecorator) -> {
					treeDecorator.place(contextWorldGenLevel, decorationPlacer, contextRandom, trunkList, leavesList);
				});
			}*//*

			return BoundingBox.encapsulatingPositions(Iterables.concat(trunkSet, leavesSet, decorationSet)).map((boundingBox) -> {
				DiscreteVoxelShape var4 = updateLeaves(contextWorldGenLevel, boundingBox, trunkSet, decorationSet);
				StructureTemplate.updateShapeAtEdge(contextWorldGenLevel, 3, var4, boundingBox.minX(), boundingBox.minY(), boundingBox.minZ());
				return true;
			}).orElse(false);
		} else {
			return false;
		}*/
	}

	/**
	 * This works akin to the AbstractTreeFeature.generate, but put our branches and roots here
	 */
	@Deprecated
	protected boolean generate(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> logpos, Set<BlockPos> leavespos, Set<BlockPos> branchpos, Set<BlockPos> rootpos, BoundingBox mbb, T config) {
		return false;
	}

	protected abstract boolean generate(WorldGenLevel world, Random random, BlockPos pos, BiConsumer<BlockPos, BlockState> trunkPlacer, BiConsumer<BlockPos, BlockState> leavesPlacer, BiConsumer<BlockPos, BlockState> decorationPlacer, T config);

	//AbstractTrunkPlacer.placeLog copy - Use that one instead when extending that Abstract
	// 1.17 adapted
	protected boolean setLogBlockState(LevelAccessor world, BiConsumer<BlockPos, BlockState> trunkPlacer, Random random, BlockPos pos, TFTreeFeatureConfig config) {
		if (TreeFeature.validTreePos(world, pos)) {
			trunkPlacer.accept(pos, config.trunkProvider.getState(random, pos));
			return true;
		} else {
			return false;
		}
	}

	//AbstractTrunkPlacer.placeLog copy - Use that one instead when extending that Abstract
	@Deprecated
	protected boolean setLogBlockState(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> logPos, BoundingBox mbb, TFTreeFeatureConfig config) {
		if (TreeFeature.validTreePos(world, pos)) {
			this.setBlockState(world, pos, config.trunkProvider.getState(random, pos), mbb);
			logPos.add(pos.immutable());
			return true;
		} else {
			return false;
		}
	}

	@Deprecated
	public boolean setBranchBlockState(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> branchpos, BoundingBox mbb, TFTreeFeatureConfig config) {
		if (TreeFeature.validTreePos(world, pos)) {
			this.setBlockState(world, pos, config.branchProvider.getState(random, pos), mbb);
			branchpos.add(pos.immutable());
			return true;
		} else {
			return false;
		}
	}

	public boolean setBranchBlockState(LevelAccessor world, BiConsumer<BlockPos, BlockState> trunkPlacer, Random random, BlockPos pos, TFTreeFeatureConfig config) {
		if (TreeFeature.validTreePos(world, pos)) {
			trunkPlacer.accept(pos, config.branchProvider.getState(random, pos));
			return true;
		} else {
			return false;
		}
	}

	protected boolean setRootsBlockState(LevelAccessor world, BiConsumer<BlockPos, BlockState> placer, Random random, BlockPos pos, TFTreeFeatureConfig config) {
		// XXX: This was originally an IWorld in AbstractTreeFeature.place, so it should be ok to cast it back.
		// If you're here investigating after it blew up, then the above assumption is no longer true.
		if (canRootGrowIn(world, pos)) {
			placer.accept(pos, config.rootsProvider.getState(random, pos));
			return true;
		} else {
			return false;
		}
	}

	@Deprecated
	protected boolean setRootsBlockState(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> branchpos, BoundingBox mbb, TFTreeFeatureConfig config) {
		// XXX: This was originally an IWorld in AbstractTreeFeature.place, so it should be ok to cast it back.
		// If you're here investigating after it blew up, then the above assumption is no longer true.
		if (canRootGrowIn(world, pos)) {
			this.setBlockState(world, pos, config.rootsProvider.getState(random, pos), mbb);
			branchpos.add(pos.immutable());
			return true;
		} else {
			return false;
		}
	}

	@Deprecated // Duplicated Functionality - This exists inside the BiConsumers
	protected final void setBlockState(LevelWriter world, BlockPos pos, BlockState state, BoundingBox mbb) {
		world.setBlock(pos, state, 19);
		mbb.encapsulate(new BoundingBox(pos));
	}

	/**
	 * Build a root, but don't let it stick out too far into thin air because that's weird
	 */
	@Deprecated
	protected void buildRoot(LevelAccessor world, Random rand, BlockPos pos, Set<BlockPos> setpos, double offset, int b, BoundingBox mbb, T config) {
		BlockPos dest = FeatureLogic.translate(pos.below(b + 2), 5, 0.3 * b + offset, 0.8);

		// go through block by block and stop drawing when we head too far into open air
		BlockPos[] lineArray = FeatureLogic.getBresenhamArrays(pos.below(), dest);
		for (BlockPos coord : lineArray) {
			this.setRootsBlockState(world, rand, coord, setpos, mbb, config);
		}
	}

	/**
	 * Build a root, but don't let it stick out too far into thin air because that's weird
	 */
	protected void buildRoot(LevelAccessor world, BiConsumer<BlockPos, BlockState> placer, Random rand, BlockPos pos, double offset, int b, T config) {
		BlockPos dest = FeatureLogic.translate(pos.below(b + 2), 5, 0.3 * b + offset, 0.8);

		// go through block by block and stop drawing when we head too far into open air
		BlockPos[] lineArray = FeatureLogic.getBresenhamArrays(pos.below(), dest);
		for (BlockPos coord : lineArray) {
			this.setRootsBlockState(world, placer, rand, coord, config);
		}
	}

	// TODO should move to FeatureUtil
	public static boolean canRootGrowIn(LevelReader world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);
		Block blockID = blockState.getBlock();

		if (blockState.isAir()) {
			// roots can grow through air if they are near a solid block
			return FeatureUtil.isNearSolid(world, pos);
		} else {
			return (blockState.getDestroySpeed(world, pos) >= 0)
					&& blockID != TFBlocks.stronghold_shield.get()
					&& blockID != TFBlocks.trophy_pedestal.get()
					&& blockID != TFBlocks.boss_spawner_naga.get()
					&& blockID != TFBlocks.boss_spawner_lich.get()
					&& blockID != TFBlocks.boss_spawner_hydra.get()
					&& blockID != TFBlocks.boss_spawner_ur_ghast.get()
					&& blockID != TFBlocks.boss_spawner_knight_phantom.get()
					&& blockID != TFBlocks.boss_spawner_snow_queen.get()
					&& blockID != TFBlocks.boss_spawner_minoshroom.get()
					&& blockID != TFBlocks.boss_spawner_alpha_yeti.get()
					&& (blockState.getMaterial() == Material.GRASS || blockState.getMaterial() == Material.DIRT || blockState.getMaterial() == Material.STONE || blockState.getMaterial() == Material.WATER);
		}
	}

	/**
	 * Add a firefly at the specified height and angle.
	 *
	 * @param height how far up the tree
	 * @param angle  from 0 - 1 rotation around the tree
	 */
	protected void addFirefly(LevelAccessor world, BlockPos pos, int height, double angle) {
		int iAngle = (int) (angle * 4.0);
		if (iAngle == 0) {
			setIfEmpty(world, pos.offset( 1, height,  0), TFBlocks.firefly.get().defaultBlockState().setValue(DirectionalBlock.FACING, Direction.EAST));
		} else if (iAngle == 1) {
			setIfEmpty(world, pos.offset(-1, height,  0), TFBlocks.firefly.get().defaultBlockState().setValue(DirectionalBlock.FACING, Direction.WEST));
		} else if (iAngle == 2) {
			setIfEmpty(world, pos.offset( 0, height,  1), TFBlocks.firefly.get().defaultBlockState().setValue(DirectionalBlock.FACING, Direction.SOUTH));
		} else if (iAngle == 3) {
			setIfEmpty(world, pos.offset( 0, height, -1), TFBlocks.firefly.get().defaultBlockState().setValue(DirectionalBlock.FACING, Direction.NORTH));
		}
	}

	private void setIfEmpty(LevelAccessor world, BlockPos pos, BlockState state) {
		if (world.isEmptyBlock(pos)) {
			world.setBlock(pos, state,3);
		}
	}
}
