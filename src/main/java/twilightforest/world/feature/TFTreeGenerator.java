package twilightforest.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import twilightforest.util.FeatureLogic;
import twilightforest.util.FeaturePlacers;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

import java.util.Random;
import java.util.function.BiConsumer;

// FIXME There's scars from at least 3 different ports, it may be high time to completely re-write this class soon. This comment was written mid-port for 1.17.1
public abstract class TFTreeGenerator<T extends TFTreeFeatureConfig> extends Feature<T> {
	public TFTreeGenerator(Codec<T> configIn) {
		super(configIn);
	}

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
	protected abstract boolean generate(WorldGenLevel world, Random random, BlockPos pos, BiConsumer<BlockPos, BlockState> trunkPlacer, BiConsumer<BlockPos, BlockState> leavesPlacer, BiConsumer<BlockPos, BlockState> decorationPlacer, T config);

	/**
	 * Build a root, but don't let it stick out too far into thin air because that's weird
	 */
	protected void buildRoot(LevelAccessor world, BiConsumer<BlockPos, BlockState> placer, Random rand, BlockPos pos, double offset, int b, T config) {
		BlockPos dest = FeatureLogic.translate(pos.below(b + 2), 5, 0.3 * b + offset, 0.8);

		// go through block by block and stop drawing when we head too far into open air
		BlockPos[] lineArray = FeatureLogic.getBresenhamArrays(pos.below(), dest);
		for (BlockPos coord : lineArray) {
			FeaturePlacers.placeIfValidRootPos(world, placer, rand, coord, config.rootsProvider);
		}
	}

}
