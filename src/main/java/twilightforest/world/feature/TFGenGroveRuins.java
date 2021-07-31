package twilightforest.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import twilightforest.util.FeatureUtil;

import java.util.Random;

/**
 * Several ruin types that look like the quest grove
 *
 * @author Ben
 */
public class TFGenGroveRuins extends Feature<NoneFeatureConfiguration> {

	private static final BlockState MOSSY_STONEBRICK = Blocks.MOSSY_STONE_BRICKS.defaultBlockState();
	private static final BlockState CHISELED_STONEBRICK = Blocks.CHISELED_STONE_BRICKS.defaultBlockState();

	public TFGenGroveRuins(Codec<NoneFeatureConfiguration> configIn) {
		super(configIn);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
		WorldGenLevel world = ctx.level();
		BlockPos pos = ctx.origin();
		Random rand = ctx.random();

		if (rand.nextBoolean()) {
			return generateLargeArch(world, pos);
		} else {
			return generateSmallArch(world, pos);
		}
	}

	/**
	 * Generate a ruin with the larger arch
	 */
	private boolean generateLargeArch(LevelAccessor world, BlockPos pos) {
		if (!FeatureUtil.isAreaSuitable(world, pos, 2, 7, 6)) {
			return false;
		}

		// pillar
		for (int dy = -2; dy <= 7; dy++) {
			world.setBlock(pos.offset(0, dy, 1), MOSSY_STONEBRICK, 3);
			world.setBlock(pos.offset(1, dy, 1), MOSSY_STONEBRICK, 3);
			world.setBlock(pos.offset(0, dy, 2), MOSSY_STONEBRICK, 3);
			world.setBlock(pos.offset(1, dy, 2), MOSSY_STONEBRICK, 3);
		}

		// broken floor part
		world.setBlock(pos.offset(0, -1, 3), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(1, -1, 3), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(0, -2, 3), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(1, -2, 3), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(0, -1, 4), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(1, -1, 4), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(0, -2, 4), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(1, -2, 4), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(0, -1, 5), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(1, -2, 5), MOSSY_STONEBRICK, 3);

		// broken top part
		world.setBlock(pos.offset(0, 6, 3), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(1, 6, 3), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(0, 7, 3), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(1, 7, 3), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(0, 6, 4), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(1, 6, 4), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(0, 7, 4), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(1, 7, 4), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(1, 7, 5), MOSSY_STONEBRICK, 3);

		// small piece of chiseled stone brick
		world.setBlock(pos.offset(0, 5, 0), CHISELED_STONEBRICK, 3);

		return true;
	}

	/**
	 * Generate a ruin with the smaller arch
	 */
	private boolean generateSmallArch(LevelAccessor world, BlockPos pos) {
		if (!FeatureUtil.isAreaSuitable(world, pos, 7, 5, 9)) {
			return false;
		}

		// corner
		world.setBlock(pos.offset(0, 4, 0), CHISELED_STONEBRICK, 3);
		world.setBlock(pos.offset(0, 3, 0), CHISELED_STONEBRICK, 3);
		world.setBlock(pos.offset(1, 4, 0), CHISELED_STONEBRICK, 3);
		world.setBlock(pos.offset(2, 4, 0), CHISELED_STONEBRICK, 3);
		world.setBlock(pos.offset(0, 4, 1), CHISELED_STONEBRICK, 3);
		world.setBlock(pos.offset(0, 4, 2), CHISELED_STONEBRICK, 3);

		// broken arch in x direction
		for (int dy = -1; dy <= 5; dy++) {
			world.setBlock(pos.offset(3, dy, 0), MOSSY_STONEBRICK, 3);
		}
		world.setBlock(pos.offset(4, -1, 0), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(5, -1, 0), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(6, -1, 0), MOSSY_STONEBRICK, 3);

		world.setBlock(pos.offset(4, 5, 0), MOSSY_STONEBRICK, 3);
		world.setBlock(pos.offset(5, 5, 0), MOSSY_STONEBRICK, 3);

		// full arch in z direction
		for (int dy = -1; dy <= 5; dy++) {
			world.setBlock(pos.offset(0, dy, 3), MOSSY_STONEBRICK, 3);
			world.setBlock(pos.offset(0, dy, 7), MOSSY_STONEBRICK, 3);
		}
		for (int dz = 4; dz < 7; dz++) {
			world.setBlock(pos.offset(0, -1, dz), MOSSY_STONEBRICK, 3);
			world.setBlock(pos.offset(0, 5, dz), MOSSY_STONEBRICK, 3);
		}

		// small piece of chiseled stone brick
		world.setBlock(pos.offset(0, 4, 8), CHISELED_STONEBRICK, 3);

		return true;
	}
}

