package twilightforest.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import twilightforest.util.FeatureUtil;
import twilightforest.world.feature.config.CaveStalactiteConfig;

import java.util.Random;
import java.util.function.Function;

/**
 * Makes a Stalagmite suitable for outside appearances.
 *
 * @author Ben
 */
public class TFGenOutsideStalagmite<T extends CaveStalactiteConfig> extends TFGenCaveStalactite<T> {

	public TFGenOutsideStalagmite(Function<Dynamic<?>, T> configIn) {
		super(configIn);
	}

	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, T config) {
		int length = rand.nextInt(10) + 5;

		if (!FeatureUtil.isAreaSuitable(world, rand, pos, 1, length, 1)) {
			return false;
		}

		// I think we already have code for this! :D
		return makeSpike(world, rand, pos.down(), length, config);
	}
}
