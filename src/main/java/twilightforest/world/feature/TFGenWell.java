package twilightforest.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import twilightforest.loot.TFTreasure;
import twilightforest.util.FeatureUtil;

import java.util.Random;
import java.util.function.Function;

public class TFGenWell extends Feature<NoFeatureConfig> {

	public TFGenWell(Function<Dynamic<?>, NoFeatureConfig> configIn) {
		super(configIn);
	}

	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		if (rand.nextInt(4) == 0) {
			return generate4x4Well(world.getWorld(), rand, pos);
		} else {
			return generate3x3Well(world.getWorld(), rand, pos);
		}
	}

	/**
	 * make a cute little well
	 */
	public boolean generate3x3Well(World world, Random rand, BlockPos pos) {
		if (!FeatureUtil.isAreaSuitable(world, rand, pos, 3, 4, 3)) {
			return false;
		}

		// make a cute well!
		world.setBlockState(pos, Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(1, 0, 0), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(2, 0, 0), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(0, 0, 2), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(1, 0, 2), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(2, 0, 2), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(0, 0, 1), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(2, 0, 1), Blocks.MOSSY_COBBLESTONE.getDefaultState());

		world.setBlockState(pos.add(1, 0, 1), Blocks.WATER.getDefaultState());

		world.setBlockState(pos.add(0, 1, 0), Blocks.OAK_FENCE.getDefaultState());
		world.setBlockState(pos.add(2, 1, 0), Blocks.OAK_FENCE.getDefaultState());
		world.setBlockState(pos.add(0, 1, 2), Blocks.OAK_FENCE.getDefaultState());
		world.setBlockState(pos.add(2, 1, 2), Blocks.OAK_FENCE.getDefaultState());

		world.setBlockState(pos.add(0, 2, 0), Blocks.OAK_FENCE.getDefaultState());
		world.setBlockState(pos.add(2, 2, 0), Blocks.OAK_FENCE.getDefaultState());
		world.setBlockState(pos.add(0, 2, 2), Blocks.OAK_FENCE.getDefaultState());
		world.setBlockState(pos.add(2, 2, 2), Blocks.OAK_FENCE.getDefaultState());

		world.setBlockState(pos.add(0, 3, 0), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(1, 3, 0), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(2, 3, 0), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(0, 3, 2), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(1, 3, 2), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(2, 3, 2), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(0, 3, 1), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(2, 3, 1), Blocks.OAK_SLAB.getDefaultState());

		world.setBlockState(pos.add(1, 3, 1), Blocks.OAK_PLANKS.getDefaultState());


		boolean madeTreasure = false;
		// now drill each the well square down 20 squares, or until we hit something
		for (int dy = -1; dy >= -20; dy--) {
			Block dblock = world.getBlockState(pos.add(1, dy, 1)).getBlock();
			// we only drill through dirt, grass, gravel and stone
			if (dblock != Blocks.DIRT && dblock != Blocks.GRASS && dblock != Blocks.GRAVEL && dblock != Blocks.STONE) {
				break;
			}
			// we also need a solid block under where we're digging
			if (!world.getBlockState(pos.add(1, dy - 1, 1)).getMaterial().isSolid()) {
				break;
			}

			// okay, we're good to dig.
			world.setBlockState(pos.add(1, dy, 1), Blocks.WATER.getDefaultState());

			// if we're below 15 squares, there's a small chance of treasure
			if (dy < -15 && !madeTreasure && rand.nextInt(8) == 0) {
				//TODO: more directions
				world.setBlockState(pos.add(2, dy, 1), Blocks.WATER.getDefaultState());
				world.setBlockState(pos.add(3, dy + 1, 1), Blocks.AIR.getDefaultState());
				world.setBlockState(pos.add(3, dy, 1), Blocks.AIR.getDefaultState());

				//TODO: unique treasure table that is themed for underwater well exploration
				TFTreasure.basement.generateChest(world, pos.add(3, dy, 1), false);

				// set flag so we only get one chest
				madeTreasure = true;
			}
		}

		return true;
	}

	private boolean generate4x4Well(World world, Random rand, BlockPos pos) {
		if (!FeatureUtil.isAreaSuitable(world, rand, pos, 4, 4, 4)) {
			return false;
		}

		// make a cute well!
		world.setBlockState(pos, Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(1, 0, 0), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(2, 0, 0), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(3, 0, 0), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(0, 0, 3), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(1, 0, 3), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(2, 0, 3), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(3, 0, 3), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(0, 0, 1), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(0, 0, 2), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(3, 0, 1), Blocks.MOSSY_COBBLESTONE.getDefaultState());
		world.setBlockState(pos.add(3, 0, 2), Blocks.MOSSY_COBBLESTONE.getDefaultState());

		world.setBlockState(pos.add(1, 0, 1), Blocks.WATER.getDefaultState());
		world.setBlockState(pos.add(2, 0, 1), Blocks.WATER.getDefaultState());
		world.setBlockState(pos.add(1, 0, 2), Blocks.WATER.getDefaultState());
		world.setBlockState(pos.add(2, 0, 2), Blocks.WATER.getDefaultState());

		world.setBlockState(pos.add(0, 1, 0), Blocks.OAK_FENCE.getDefaultState());
		world.setBlockState(pos.add(3, 1, 0), Blocks.OAK_FENCE.getDefaultState());
		world.setBlockState(pos.add(0, 1, 3), Blocks.OAK_FENCE.getDefaultState());
		world.setBlockState(pos.add(3, 1, 3), Blocks.OAK_FENCE.getDefaultState());

		world.setBlockState(pos.add(0, 2, 0), Blocks.OAK_FENCE.getDefaultState());
		world.setBlockState(pos.add(3, 2, 0), Blocks.OAK_FENCE.getDefaultState());
		world.setBlockState(pos.add(0, 2, 3), Blocks.OAK_FENCE.getDefaultState());
		world.setBlockState(pos.add(3, 2, 3), Blocks.OAK_FENCE.getDefaultState());

		world.setBlockState(pos.add(0, 3, 0), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(1, 3, 0), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(2, 3, 0), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(3, 3, 0), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(0, 3, 3), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(1, 3, 3), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(2, 3, 3), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(3, 3, 3), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(0, 3, 1), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(0, 3, 2), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(3, 3, 1), Blocks.OAK_SLAB.getDefaultState());
		world.setBlockState(pos.add(3, 3, 2), Blocks.OAK_SLAB.getDefaultState());

		world.setBlockState(pos.add(1, 3, 1), Blocks.OAK_PLANKS.getDefaultState());
		world.setBlockState(pos.add(2, 3, 1), Blocks.OAK_PLANKS.getDefaultState());
		world.setBlockState(pos.add(1, 3, 2), Blocks.OAK_PLANKS.getDefaultState());
		world.setBlockState(pos.add(2, 3, 2), Blocks.OAK_PLANKS.getDefaultState());


		// now drill each of the 4 well squares down 20 squares, or until we hit something
		for (int dx = 1; dx <= 2; dx++) {
			for (int dz = 1; dz <= 2; dz++) {
				for (int dy = -1; dy >= -20; dy--) {
					BlockPos dPos = pos.add(dx, dy, dz);
					BlockState dState = world.getBlockState(dPos);
					Block dblock = dState.getBlock();

					// we only drill through dirt, grass, gravel and stone
					if (dblock != Blocks.DIRT && dblock != Blocks.GRASS && dblock != Blocks.GRAVEL && dblock != Blocks.STONE) {
						break;
					}
					// we also need a solid block under where we're digging
					if (!world.getBlockState(dPos.down()).getMaterial().isSolid()) {
						break;
					}

					// okay, we're good to dig.
					world.setBlockState(dPos, Blocks.WATER.getDefaultState());
				}

			}

		}

		return true;
	}
}
