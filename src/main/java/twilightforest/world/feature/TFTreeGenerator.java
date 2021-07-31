package twilightforest.world.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import twilightforest.block.TFBlocks;
import twilightforest.util.FeatureUtil;
import twilightforest.world.feature.config.CaveStalactiteConfig;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

import java.util.List;
import java.util.Random;
import java.util.Set;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.WorldGenLevel;

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

	protected boolean generate(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> logpos, Set<BlockPos> leavespos, BoundingBox mbb, T config) {
		Set<BlockPos> branchSet = Sets.newHashSet();
		Set<BlockPos> rootSet = Sets.newHashSet();
		return generate(world, random, pos, logpos, leavespos, branchSet, rootSet, mbb, config);
	}

	//AbstractTreeFeature.place from 1.15, modified for us
	@Override
	public boolean place(FeaturePlaceContext<T> ctx) {
		WorldGenLevel world = ctx.level();
		BlockPos pos = ctx.origin();
		Random random = ctx.random();
		T config = ctx.config();

		Set<BlockPos> logs = Sets.newHashSet();
		Set<BlockPos> leaves = Sets.newHashSet();
		BoundingBox mutableboundingbox = BoundingBox.infinite();
		boolean flag = this.generate(world, random, pos, logs, leaves, mutableboundingbox, config);
		if (mutableboundingbox.minX() <= mutableboundingbox.maxX() && flag && !logs.isEmpty()) {
			DiscreteVoxelShape voxelshapepart = this.getVoxelShapePart(world, mutableboundingbox, logs);
			StructureTemplate.updateShapeAtEdge(world, 3, voxelshapepart, mutableboundingbox.minX(), mutableboundingbox.minY(), mutableboundingbox.minZ());
			return true;
		} else {
			return false;
		}
	}

	//TreeFeature.func_227214_a_ copy, modified to remove decorations
	private DiscreteVoxelShape getVoxelShapePart(LevelAccessor world, BoundingBox mbb, Set<BlockPos> logPosSet) {
		List<Set<BlockPos>> list = Lists.newArrayList();
		DiscreteVoxelShape voxelshapepart = new BitSetDiscreteVoxelShape(mbb.getXSpan(), mbb.getYSpan(), mbb.getZSpan());

		for(int j = 0; j < 6; ++j) {
			list.add(Sets.newHashSet());
		}

		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		for(BlockPos logPos : Lists.newArrayList(logPosSet)) {
			if (mbb.isInside(logPos)) {
				voxelshapepart.setFull(logPos.getX() - mbb.minX(), logPos.getY() - mbb.minY(), logPos.getZ() - mbb.minZ(), true, true);
			}

			for(Direction direction : Direction.values()) {
				mutable.setWithOffset(logPos, direction);
				if (!logPosSet.contains(mutable)) {
					BlockState blockstate = world.getBlockState(mutable);
					if (blockstate.hasProperty(BlockStateProperties.DISTANCE)) {
						list.get(0).add(mutable.immutable());
						TreeFeature.setBlockKnownShape(world, mutable, blockstate.setValue(BlockStateProperties.DISTANCE, 1));
						if (mbb.isInside(mutable)) {
							voxelshapepart.setFull(mutable.getX() - mbb.minX(), mutable.getY() - mbb.minY(), mutable.getZ() - mbb.minZ(), true, true);
						}
					}
				}
			}
		}

		for(int l = 1; l < 6; ++l) {
			Set<BlockPos> set = list.get(l - 1);
			Set<BlockPos> set1 = list.get(l);

			for(BlockPos blockpos2 : set) {
				if (mbb.isInside(blockpos2)) {
					voxelshapepart.setFull(blockpos2.getX() - mbb.minX(), blockpos2.getY() - mbb.minY(), blockpos2.getZ() - mbb.minZ(), true, true);
				}

				for(Direction direction1 : Direction.values()) {
					mutable.setWithOffset(blockpos2, direction1);
					if (!set.contains(mutable) && !set1.contains(mutable)) {
						BlockState blockstate1 = world.getBlockState(mutable);
						if (blockstate1.hasProperty(BlockStateProperties.DISTANCE)) {
							int k = blockstate1.getValue(BlockStateProperties.DISTANCE);
							if (k > l + 1) {
								BlockState blockstate2 = blockstate1.setValue(BlockStateProperties.DISTANCE, l + 1);
								TreeFeature.setBlockKnownShape(world, mutable, blockstate2);
								if (mbb.isInside(mutable)) {
									voxelshapepart.setFull(mutable.getX() - mbb.minX(), mutable.getY() - mbb.minY(), mutable.getZ() - mbb.minZ(), true, true);
								}

								set1.add(mutable.immutable());
							}
						}
					}
				}
			}
		}

		return voxelshapepart;
	}

	/**
	 * This works akin to the AbstractTreeFeature.generate, but put our branches and roots here
	 */
	protected abstract boolean generate(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> logpos, Set<BlockPos> leavespos, Set<BlockPos> branchpos, Set<BlockPos> rootpos, BoundingBox mbb, T config);

	//AbstractTrunkPlacer.placeLog copy - Use that one instead when extending that Abstract
	protected boolean setLogBlockState(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> logPos, BoundingBox mbb, TFTreeFeatureConfig config) {
		if (TreeFeature.validTreePos(world, pos)) {
			this.setBlockState(world, pos, config.trunkProvider.getState(random, pos), mbb);
			logPos.add(pos.immutable());
			return true;
		} else {
			return false;
		}
	}

	//We aren't actually using this, but it is here just in case
	protected boolean setLeavesBlockState(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> leavesPos, BoundingBox mbb, TFTreeFeatureConfig config) {
		if (TreeFeature.validTreePos(world, pos)) {
			this.setBlockState(world, pos, config.leavesProvider.getState(random, pos), mbb);
			leavesPos.add(pos.immutable());
			return true;
		} else {
			return false;
		}
	}

	public boolean setBranchBlockState(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> branchpos, BoundingBox mbb, TFTreeFeatureConfig config) {
		if (TreeFeature.validTreePos(world, pos)) {
			this.setBlockState(world, pos, config.branchProvider.getState(random, pos), mbb);
			branchpos.add(pos.immutable());
			return true;
		} else {
			return false;
		}
	}

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

	protected final void setBlockState(LevelWriter world, BlockPos pos, BlockState state, BoundingBox mbb) {
		world.setBlock(pos, state, 19);
		mbb.encapsulate(new BoundingBox(pos));
	}

	/**
	 * Build a root, but don't let it stick out too far into thin air because that's weird
	 */
	protected void buildRoot(LevelAccessor world, Random rand, BlockPos pos, Set<BlockPos> setpos, double offset, int b, BoundingBox mbb, T config) {
		BlockPos dest = FeatureUtil.translate(pos.below(b + 2), 5, 0.3 * b + offset, 0.8);

		// go through block by block and stop drawing when we head too far into open air
		BlockPos[] lineArray = FeatureUtil.getBresenhamArrays(pos.below(), dest);
		for (BlockPos coord : lineArray) {
			this.setRootsBlockState(world, rand, coord, setpos, mbb, config);
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
