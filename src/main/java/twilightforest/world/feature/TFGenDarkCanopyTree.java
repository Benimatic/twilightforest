package twilightforest.world.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
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
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.LevelSimulatedRW;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import twilightforest.world.TFGenerationSettings;

import java.util.*;

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
	public boolean place(WorldGenLevel reader, ChunkGenerator generator, Random rand, BlockPos pos, TreeConfiguration config) {
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
		BoundingBox mutableboundingbox = BoundingBox.getUnknownBox();
		boolean flag = place(reader, rand, pos, set, set1, mutableboundingbox, config);
		difference = mutableboundingbox.y0 - pos.getY();
		mutableboundingbox.y0 = pos.getY();
		mutableboundingbox.y1 = mutableboundingbox.y1 - difference;
		if(flag && !set.isEmpty()) {
			if (!config.decorators.isEmpty()) {
				List<BlockPos> list = Lists.newArrayList(set);
				List<BlockPos> list1 = Lists.newArrayList(set1);
				list.sort(Comparator.comparingInt(Vec3i::getY));
				list1.sort(Comparator.comparingInt(Vec3i::getY));
				config.decorators.forEach((p_236405_6_) -> {
					p_236405_6_.place(reader, rand, list, list1, set2, mutableboundingbox);
				});
			}

			DiscreteVoxelShape voxelshapepart = this.updateLeaves(reader, mutableboundingbox, set, set2);
			StructureTemplate.updateShapeAtEdge(reader, 3, voxelshapepart, mutableboundingbox.x0, mutableboundingbox.y0, mutableboundingbox.z0);
			return true;
		} else {
			return false;
		}
	}

	//Mostly [VanillaCopy] of TreeFeature.place, edits noted
	private boolean place(LevelSimulatedRW generationReader, Random rand, BlockPos positionIn, Set<BlockPos> p_225557_4_, Set<BlockPos> p_225557_5_, BoundingBox boundingBoxIn, TreeConfiguration configIn) {
		int i = configIn.trunkPlacer.getTreeHeight(rand);
		int j = configIn.foliagePlacer.foliageHeight(rand, i, configIn);
		int k = i - j;
		int l = configIn.foliagePlacer.foliageRadius(rand, k);
		BlockPos blockpos;
		if (!configIn.fromSapling) {
			int i1 = generationReader.getHeightmapPos(Heightmap.Types.OCEAN_FLOOR, positionIn).getY();
			int j1 = generationReader.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, positionIn).getY();
			if (j1 - i1 > configIn.maxWaterDepth) {
				return false;
			}
			//set our blockpos to the valid dirt pos, not highest ground
			blockpos = new BlockPos(positionIn.getX(), validPos.getY(), positionIn.getZ());
		} else {
			blockpos = positionIn;
		}

		if (blockpos.getY() >= 1 && blockpos.getY() + i + 1 <= 256) {
			OptionalInt optionalint = configIn.minimumSize.minClippedHeight();
			int l1 = this.getMaxFreeTreeHeight(generationReader, i, blockpos, configIn);
			if (l1 >= i || optionalint.isPresent() && l1 >= optionalint.getAsInt()) {
				List<FoliagePlacer.FoliageAttachment> list = configIn.trunkPlacer.placeTrunk(generationReader, rand, l1, blockpos, p_225557_4_, boundingBoxIn, configIn);
				list.forEach((p_236407_8_) -> {
					configIn.foliagePlacer.createFoliage(generationReader, rand, configIn, l1, p_236407_8_, j, l, p_225557_5_, boundingBoxIn);
				});
				return true;
			} else {
				return false;
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

	private DiscreteVoxelShape updateLeaves(LevelAccessor p_236403_1_, BoundingBox p_236403_2_, Set<BlockPos> p_236403_3_, Set<BlockPos> p_236403_4_) {
		List<Set<BlockPos>> list = Lists.newArrayList();
		DiscreteVoxelShape voxelshapepart = new BitSetDiscreteVoxelShape(p_236403_2_.getXSpan(), p_236403_2_.getYSpan(), p_236403_2_.getZSpan());

		for(int j = 0; j < 6; ++j) {
			list.add(Sets.newHashSet());
		}

		BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

		for(BlockPos blockpos : Lists.newArrayList(p_236403_4_)) {
			if (p_236403_2_.isInside(blockpos)) {
				voxelshapepart.setFull(blockpos.getX() - p_236403_2_.x0, blockpos.getY() - p_236403_2_.y0, blockpos.getZ() - p_236403_2_.z0, true, true);
			}
		}

		for(BlockPos blockpos1 : Lists.newArrayList(p_236403_3_)) {
			if (p_236403_2_.isInside(blockpos1)) {
				voxelshapepart.setFull(blockpos1.getX() - p_236403_2_.x0, blockpos1.getY() - p_236403_2_.y0, blockpos1.getZ() - p_236403_2_.z0, true, true);
			}

			for(Direction direction : Direction.values()) {
				blockpos$mutable.setWithOffset(blockpos1, direction);
				if (!p_236403_3_.contains(blockpos$mutable)) {
					BlockState blockstate = p_236403_1_.getBlockState(blockpos$mutable);
					if (blockstate.hasProperty(BlockStateProperties.DISTANCE)) {
						list.get(0).add(blockpos$mutable.immutable());
						setBlockKnownShape(p_236403_1_, blockpos$mutable, blockstate.setValue(BlockStateProperties.DISTANCE, Integer.valueOf(1)));
						if (p_236403_2_.isInside(blockpos$mutable)) {
							voxelshapepart.setFull(blockpos$mutable.getX() - p_236403_2_.x0, blockpos$mutable.getY() - p_236403_2_.y0, blockpos$mutable.getZ() - p_236403_2_.z0, true, true);
						}
					}
				}
			}
		}

		for(int l = 1; l < 6; ++l) {
			Set<BlockPos> set = list.get(l - 1);
			Set<BlockPos> set1 = list.get(l);

			for(BlockPos blockpos2 : set) {
				if (p_236403_2_.isInside(blockpos2)) {
					voxelshapepart.setFull(blockpos2.getX() - p_236403_2_.x0, blockpos2.getY() - p_236403_2_.y0, blockpos2.getZ() - p_236403_2_.z0, true, true);
				}

				for(Direction direction1 : Direction.values()) {
					blockpos$mutable.setWithOffset(blockpos2, direction1);
					if (!set.contains(blockpos$mutable) && !set1.contains(blockpos$mutable)) {
						BlockState blockstate1 = p_236403_1_.getBlockState(blockpos$mutable);
						if (blockstate1.hasProperty(BlockStateProperties.DISTANCE)) {
							int k = blockstate1.getValue(BlockStateProperties.DISTANCE);
							if (k > l + 1) {
								BlockState blockstate2 = blockstate1.setValue(BlockStateProperties.DISTANCE, Integer.valueOf(l + 1));
								setBlockKnownShape(p_236403_1_, blockpos$mutable, blockstate2);
								if (p_236403_2_.isInside(blockpos$mutable)) {
									voxelshapepart.setFull(blockpos$mutable.getX() - p_236403_2_.x0, blockpos$mutable.getY() - p_236403_2_.y0, blockpos$mutable.getZ() - p_236403_2_.z0, true, true);
								}

								set1.add(blockpos$mutable.immutable());
							}
						}
					}
				}
			}
		}

		return voxelshapepart;
	}
}
