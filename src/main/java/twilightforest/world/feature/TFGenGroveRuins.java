package twilightforest.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import twilightforest.util.FeatureUtil;

import java.util.Random;
import java.util.function.Function;

/**
 * Several ruin types that look like the quest grove
 *
 * @author Ben
 */
public class TFGenGroveRuins extends Feature<NoFeatureConfig> {

	private static final BlockState MOSSY_STONEBRICK = Blocks.MOSSY_STONE_BRICKS.getDefaultState();
	private static final BlockState CHISELED_STONEBRICK = Blocks.CHISELED_STONE_BRICKS.getDefaultState();

	public TFGenGroveRuins(Function<Dynamic<?>, NoFeatureConfig> configIn) {
		super(configIn);
	}

	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		if (rand.nextBoolean()) {
			return generateLargeArch(world.getWorld(), rand, pos);
		} else {
			return generateSmallArch(world.getWorld(), rand, pos);
		}
	}

	/**
	 * Generate a ruin with the larger arch
	 */
	private boolean generateLargeArch(World world, Random rand, BlockPos pos) {
		if (!FeatureUtil.isAreaSuitable(world, rand, pos, 2, 7, 6)) {
			return false;
		}

		// pillar
		for (int dy = -2; dy <= 7; dy++) {
			world.setBlockState(pos.add(0, dy, 1), MOSSY_STONEBRICK);
			world.setBlockState(pos.add(1, dy, 1), MOSSY_STONEBRICK);
			world.setBlockState(pos.add(0, dy, 2), MOSSY_STONEBRICK);
			world.setBlockState(pos.add(1, dy, 2), MOSSY_STONEBRICK);
		}

		// broken floor part
		world.setBlockState(pos.add(0, -1, 3), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(1, -1, 3), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(0, -2, 3), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(1, -2, 3), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(0, -1, 4), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(1, -1, 4), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(0, -2, 4), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(1, -2, 4), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(0, -1, 5), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(1, -2, 5), MOSSY_STONEBRICK);

		// broken top part
		world.setBlockState(pos.add(0, 6, 3), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(1, 6, 3), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(0, 7, 3), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(1, 7, 3), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(0, 6, 4), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(1, 6, 4), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(0, 7, 4), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(1, 7, 4), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(1, 7, 5), MOSSY_STONEBRICK);

		// small piece of chiseled stone brick
		world.setBlockState(pos.add(0, 5, 0), CHISELED_STONEBRICK);

		return true;
	}

	/**
	 * Generate a ruin with the smaller arch
	 */
	private boolean generateSmallArch(World world, Random rand, BlockPos pos) {
		if (!FeatureUtil.isAreaSuitable(world, rand, pos, 7, 5, 9)) {
			return false;
		}

		// corner
		world.setBlockState(pos.add(0, 4, 0), CHISELED_STONEBRICK);
		world.setBlockState(pos.add(0, 3, 0), CHISELED_STONEBRICK);
		world.setBlockState(pos.add(1, 4, 0), CHISELED_STONEBRICK);
		world.setBlockState(pos.add(2, 4, 0), CHISELED_STONEBRICK);
		world.setBlockState(pos.add(0, 4, 1), CHISELED_STONEBRICK);
		world.setBlockState(pos.add(0, 4, 2), CHISELED_STONEBRICK);

		// broken arch in x direction
		for (int dy = -1; dy <= 5; dy++) {
			world.setBlockState(pos.add(3, dy, 0), MOSSY_STONEBRICK);
		}
		world.setBlockState(pos.add(4, -1, 0), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(5, -1, 0), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(6, -1, 0), MOSSY_STONEBRICK);

		world.setBlockState(pos.add(4, 5, 0), MOSSY_STONEBRICK);
		world.setBlockState(pos.add(5, 5, 0), MOSSY_STONEBRICK);

		// full arch in z direction
		for (int dy = -1; dy <= 5; dy++) {
			world.setBlockState(pos.add(0, dy, 3), MOSSY_STONEBRICK);
			world.setBlockState(pos.add(0, dy, 7), MOSSY_STONEBRICK);
		}
		for (int dz = 4; dz < 7; dz++) {
			world.setBlockState(pos.add(0, -1, dz), MOSSY_STONEBRICK);
			world.setBlockState(pos.add(0, 5, dz), MOSSY_STONEBRICK);
		}

		// small piece of chiseled stone brick
		world.setBlockState(pos.add(0, 4, 8), CHISELED_STONEBRICK);

		return true;
	}
}

