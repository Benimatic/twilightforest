package twilightforest.world.components.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import twilightforest.data.custom.stalactites.entry.Stalactite;
import twilightforest.util.FeatureLogic;

import java.util.Map;

public class BlockSpikeFeature extends Feature<NoneFeatureConfiguration> {

	public static final Stalactite STONE_STALACTITE = new Stalactite(Map.of(Blocks.STONE, 1), 0.25F, 11, 1);

	public BlockSpikeFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		RandomSource random = context.random();
		return startSpike(context.level(), context.origin(), STONE_STALACTITE, random, false);
	}

	public static boolean startSpike(WorldGenLevel level, BlockPos startPos, Stalactite config, RandomSource random, boolean hanging) {
		UniformInt lengthBounds = UniformInt.of((int) (config.maxLength() * config.sizeVariation()), config.maxLength());

		return startSpike(level, startPos, config.ores(), lengthBounds.sample(random), lengthBounds.getMinValue(), ConstantInt.of(4).sample(random), hanging, random);
	}

	public static boolean startSpike(WorldGenLevel level, BlockPos startPos, Map<Block, Integer> ore, int length, int lengthMinimum, int clearance, boolean hang, RandomSource random) {
		BlockPos.MutableBlockPos movingPos = startPos.mutable();
		int clearedLength = 0;
		int dY = hang ? -1 : 1;

		// First find an air block
		for (int i = 0; i < length; i++) {
			clearedLength = i;

			if (FeatureLogic.worldGenReplaceable(level.getBlockState(movingPos))) break;

			movingPos.move(0, dY, 0);
		}

		// Since this gets skipped from the previous line, we invoke it manually
		movingPos.move(0, dY, 0);

		// Then find a solid block
		int remainingScanLength = length - clearedLength + clearance;
		int finalLength = clearedLength - clearance;
		for (int i = 0; i < remainingScanLength; i++) {
			finalLength = clearedLength + i;

			if (!FeatureLogic.worldGenReplaceable(level.getBlockState(movingPos))) break;

			movingPos.move(0, dY, 0);
		}

		finalLength = Math.min(length, finalLength);

		if (finalLength < lengthMinimum) return false;

		return makeSpike(level, startPos, ore, finalLength, dY, random, hang);
	}

	private static boolean makeSpike(WorldGenLevel level, BlockPos startPos, Map<Block, Integer> ore, int length, int dY, RandomSource random, boolean hang) {
		int diameter = (int) (length / 4.5F); // diameter of the base

		//only place spikes on solid ground, not on the tops of trees
		if (!hang && !FeatureLogic.worldGenReplaceable(level.getBlockState(startPos.below()))) return false;

		int highestWeight = 0;
		if (ore.size() > 1) {
			for (Map.Entry<Block, Integer> entry : ore.entrySet()) {
				if (entry.getValue() > highestWeight) highestWeight = entry.getValue();
			}
		}

		// let's see...
		for (int dx = -diameter; dx <= diameter; dx++) {
			for (int dz = -diameter; dz <= diameter; dz++) {
				// determine how long this spike will be.
				int absx = Math.abs(dx);
				int absz = Math.abs(dz);
				int dist = (int) (Math.max(absx, absz) + Math.min(absx, absz) * 0.5F);
				int spikeLength;

				if (dist <= 0) spikeLength = length;
				else spikeLength = random.nextInt((int) (length / (dist + 0.25F)));

				for (int i = -1; i < spikeLength; i++) {
					BlockPos placement = startPos.offset(dx, i * dY, dz);

					if (FeatureLogic.worldGenReplaceable(level.getBlockState(placement)) && (dY > 0 || placement.getY() < level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, placement.getX(), placement.getZ()) - 1))
						if (ore.size() == 1) {
							level.setBlock(placement, ore.keySet().stream().toList().get(0).defaultBlockState(), 3);
						} else {
							for (Map.Entry<Block, Integer> entry : ore.entrySet()) {
								if (random.nextInt(highestWeight) + 1 <= entry.getValue()) {
									level.setBlock(placement, entry.getKey().defaultBlockState(), 3);
									break;
								}
							}
						}
				}
			}
		}

		return true;
	}

	/**
	 * Makes a random stalactite appropriate to the cave size
	 * <p>
	 * All Stalactite configs are made through datapacks. They are found in modid:stalactites/entries
	 */
	public static Stalactite makeRandomOreStalactite(RandomSource rand, int hillSize) {
		if (hillSize >= 3 && rand.nextInt(5) == 0) {
			return Stalactite.getStalactiteConfig().getRandomStalactiteFromList(rand, Stalactite.getStalactiteConfig().getLargeStalactites());
		}
		if (hillSize >= 2 && rand.nextInt(5) == 0) {
			return Stalactite.getStalactiteConfig().getRandomStalactiteFromList(rand, Stalactite.getStalactiteConfig().getMediumStalactites());
		}
		return Stalactite.getStalactiteConfig().getRandomStalactiteFromList(rand, Stalactite.getStalactiteConfig().getSmallStalactites());
	}
}
