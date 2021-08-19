package twilightforest.world.components.feature.trees;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * Makes large trees with flat leaf ovals that provide a canopy for the forest
 *
 * @author Ben
 */

//Lots of things from TreeFeature, but we're checking for dirt to place on
public class TFGenDarkCanopyTree extends Feature<TreeConfiguration> {

	int difference = 0;
	BlockPos validPos = new BlockPos(0, 0, 0);

	public TFGenDarkCanopyTree(Codec<TreeConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<TreeConfiguration> ctx) {
		WorldGenLevel reader = ctx.level();
		BlockPos pos = ctx.origin();
		Random rand = ctx.random();
		TreeConfiguration config = ctx.config();

		// if we are given leaves as a starting position, seek dirt or grass underneath
		boolean foundDirt = false;
		Material materialUnder;
		for (int dy = pos.getY(); dy >= 0; dy--) {
			materialUnder = reader.getBlockState(new BlockPos(pos.getX(), dy - 1, pos.getZ())).getMaterial();
			if (materialUnder == Material.GRASS || materialUnder == Material.DIRT) {
				// yes!
				foundDirt = true;
				pos = new BlockPos(pos.getX(), dy, pos.getZ());
				validPos = pos;
				break;
			} else if (materialUnder == Material.STONE || materialUnder == Material.SAND) {
				// nope
				break;
			}
		}

		if (!foundDirt) {
			return false;
		}

		// do not grow next to another tree
		for (Direction e : Direction.Plane.HORIZONTAL) {
			if (reader.getBlockState(pos.relative(e)).getMaterial() == Material.WOOD)
				return false;
		}

		//Taken from TreeFeature.generate, adjusting our BoundingBox to fit where the dirt is
		Set<BlockPos> set = Sets.newHashSet();
		Set<BlockPos> set1 = Sets.newHashSet();
		Set<BlockPos> set2 = Sets.newHashSet();
		BoundingBox mutableboundingbox = BoundingBox.infinite();
		BiConsumer<BlockPos, BlockState> biconsumer = (p_160555_, p_160556_) -> {
			set.add(p_160555_.immutable());
			reader.setBlock(p_160555_, p_160556_, 19);
		};
		BiConsumer<BlockPos, BlockState> biconsumer1 = (p_160548_, p_160549_) -> {
			set1.add(p_160548_.immutable());
			reader.setBlock(p_160548_, p_160549_, 19);
		};
		BiConsumer<BlockPos, BlockState> biconsumer2 = (p_160543_, p_160544_) -> {
			set2.add(p_160543_.immutable());
			reader.setBlock(p_160543_, p_160544_, 19);
		};
		boolean flag = doPlace(reader, rand, pos, biconsumer, biconsumer1, config);
		difference = mutableboundingbox.minY() - pos.getY();
		//what do
		//mutableboundingbox.minY() = pos.getY();
		//mutableboundingbox.maxY() = mutableboundingbox.maxY() - difference;
		if(flag && !set.isEmpty()) {
			if (!config.decorators.isEmpty()) {
				List<BlockPos> list = Lists.newArrayList(set);
				List<BlockPos> list1 = Lists.newArrayList(set1);
				list.sort(Comparator.comparingInt(Vec3i::getY));
				list1.sort(Comparator.comparingInt(Vec3i::getY));
				config.decorators.forEach((p_236405_6_) -> {
					p_236405_6_.place(reader, biconsumer2, rand, list, list1);
				});
			}

			DiscreteVoxelShape voxelshapepart = updateLeaves(reader, mutableboundingbox, set, set2);
			StructureTemplate.updateShapeAtEdge(reader, 3, voxelshapepart, mutableboundingbox.minX(), mutableboundingbox.minY(), mutableboundingbox.minZ());
			return true;
		} else {
			return false;
		}
	}

	//Mostly [VanillaCopy] of TreeFeature.doPlace, edits noted
	private boolean doPlace(WorldGenLevel p_160511_, Random p_160512_, BlockPos p_160513_, BiConsumer<BlockPos, BlockState> p_160514_, BiConsumer<BlockPos, BlockState> p_160515_, TreeConfiguration p_160516_) {
		int i = p_160516_.trunkPlacer.getTreeHeight(p_160512_);
		int j = p_160516_.foliagePlacer.foliageHeight(p_160512_, i, p_160516_);
		int k = i - j;
		int l = p_160516_.foliagePlacer.foliageRadius(p_160512_, k);
		//set our blockpos to the valid dirt pos, not highest ground
		p_160513_ = new BlockPos(p_160513_.getX(), validPos.getY(), p_160513_.getZ());
		if (p_160513_.getY() >= p_160511_.getMinBuildHeight() + 1 && p_160513_.getY() + i + 1 <= p_160511_.getMaxBuildHeight()) {
			if (!p_160516_.saplingProvider.getState(p_160512_, p_160513_).canSurvive(p_160511_, p_160513_)) {
				return false;
			} else {
				OptionalInt optionalint = p_160516_.minimumSize.minClippedHeight();
				int i1 = this.getMaxFreeTreeHeight(p_160511_, i, p_160513_, p_160516_);
				if (i1 >= i || optionalint.isPresent() && i1 >= optionalint.getAsInt()) {
					List<FoliagePlacer.FoliageAttachment> list = p_160516_.trunkPlacer.placeTrunk(p_160511_, p_160514_, p_160512_, i1, p_160513_, p_160516_);
					list.forEach((p_160539_) -> {
						p_160516_.foliagePlacer.createFoliage(p_160511_, p_160515_, p_160512_, p_160516_, i1, p_160539_, j, l);
					});
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}

	//everything beyond this point is a [VanillaCopy] of TreeFeature
	private int getMaxFreeTreeHeight(LevelSimulatedReader p_241521_1_, int p_241521_2_, BlockPos p_241521_3_, TreeConfiguration p_241521_4_) {
		BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

		for(int i = 0; i <= p_241521_2_ + 1; ++i) {
			int j = p_241521_4_.minimumSize.getSizeAtHeight(p_241521_2_, i);

			for(int k = -j; k <= j; ++k) {
				for(int l = -j; l <= j; ++l) {
					blockpos$mutable.setWithOffset(p_241521_3_, k, i, l);
					if (!p_241521_4_.ignoreVines) {
						return i - 2;
					}
				}
			}
		}

		return p_241521_2_;
	}

	protected void setBlock(LevelWriter world, BlockPos pos, BlockState state) {
		setBlockKnownShape(world, pos, state);
	}

	public static void setBlockKnownShape(LevelWriter p_236408_0_, BlockPos p_236408_1_, BlockState p_236408_2_) {
		p_236408_0_.setBlock(p_236408_1_, p_236408_2_, 19);
	}

	private static DiscreteVoxelShape updateLeaves(LevelAccessor p_67203_, BoundingBox p_67204_, Set<BlockPos> p_67205_, Set<BlockPos> p_67206_) {
		List<Set<BlockPos>> list = Lists.newArrayList();
		DiscreteVoxelShape discretevoxelshape = new BitSetDiscreteVoxelShape(p_67204_.getXSpan(), p_67204_.getYSpan(), p_67204_.getZSpan());
		int i = 6;

		for(int j = 0; j < 6; ++j) {
			list.add(Sets.newHashSet());
		}

		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for(BlockPos blockpos : Lists.newArrayList(p_67206_)) {
			if (p_67204_.isInside(blockpos)) {
				discretevoxelshape.fill(blockpos.getX() - p_67204_.minX(), blockpos.getY() - p_67204_.minY(), blockpos.getZ() - p_67204_.minZ());
			}
		}

		for(BlockPos blockpos1 : Lists.newArrayList(p_67205_)) {
			if (p_67204_.isInside(blockpos1)) {
				discretevoxelshape.fill(blockpos1.getX() - p_67204_.minX(), blockpos1.getY() - p_67204_.minY(), blockpos1.getZ() - p_67204_.minZ());
			}

			for(Direction direction : Direction.values()) {
				blockpos$mutableblockpos.setWithOffset(blockpos1, direction);
				if (!p_67205_.contains(blockpos$mutableblockpos)) {
					BlockState blockstate = p_67203_.getBlockState(blockpos$mutableblockpos);
					if (blockstate.hasProperty(BlockStateProperties.DISTANCE)) {
						list.get(0).add(blockpos$mutableblockpos.immutable());
						setBlockKnownShape(p_67203_, blockpos$mutableblockpos, blockstate.setValue(BlockStateProperties.DISTANCE, Integer.valueOf(1)));
						if (p_67204_.isInside(blockpos$mutableblockpos)) {
							discretevoxelshape.fill(blockpos$mutableblockpos.getX() - p_67204_.minX(), blockpos$mutableblockpos.getY() - p_67204_.minY(), blockpos$mutableblockpos.getZ() - p_67204_.minZ());
						}
					}
				}
			}
		}

		for(int l = 1; l < 6; ++l) {
			Set<BlockPos> set = list.get(l - 1);
			Set<BlockPos> set1 = list.get(l);

			for(BlockPos blockpos2 : set) {
				if (p_67204_.isInside(blockpos2)) {
					discretevoxelshape.fill(blockpos2.getX() - p_67204_.minX(), blockpos2.getY() - p_67204_.minY(), blockpos2.getZ() - p_67204_.minZ());
				}

				for(Direction direction1 : Direction.values()) {
					blockpos$mutableblockpos.setWithOffset(blockpos2, direction1);
					if (!set.contains(blockpos$mutableblockpos) && !set1.contains(blockpos$mutableblockpos)) {
						BlockState blockstate1 = p_67203_.getBlockState(blockpos$mutableblockpos);
						if (blockstate1.hasProperty(BlockStateProperties.DISTANCE)) {
							int k = blockstate1.getValue(BlockStateProperties.DISTANCE);
							if (k > l + 1) {
								BlockState blockstate2 = blockstate1.setValue(BlockStateProperties.DISTANCE, Integer.valueOf(l + 1));
								setBlockKnownShape(p_67203_, blockpos$mutableblockpos, blockstate2);
								if (p_67204_.isInside(blockpos$mutableblockpos)) {
									discretevoxelshape.fill(blockpos$mutableblockpos.getX() - p_67204_.minX(), blockpos$mutableblockpos.getY() - p_67204_.minY(), blockpos$mutableblockpos.getZ() - p_67204_.minZ());
								}

								set1.add(blockpos$mutableblockpos.immutable());
							}
						}
					}
				}
			}
		}

		return discretevoxelshape;
	}
}
