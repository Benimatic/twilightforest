package twilightforest.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import twilightforest.util.FeatureUtil;
import twilightforest.world.feature.config.CaveStalactiteConfig;

import java.util.Random;

/**
 * Makes a Stalagmite suitable for outside appearances.
 *
 * @author Ben
 */
public class TFGenOutsideStalagmite extends TFGenCaveStalactite {

	public TFGenOutsideStalagmite(Codec<CaveStalactiteConfig> configIn) {
		super(configIn);
	}

	@Override
	public boolean place(FeaturePlaceContext<CaveStalactiteConfig> ctx) {
		WorldGenLevel world = ctx.level();
		BlockPos pos = ctx.origin();
		Random rand = ctx.random();
		CaveStalactiteConfig config = ctx.config();

		int length = rand.nextInt(10) + 5;

		if (!FeatureUtil.isAreaSuitable(world, pos, 1, length, 1)) {
			return false;
		}

		// I think we already have code for this! :D
		return makeSpike(world, rand, pos.below(), length, config);
	}
}
