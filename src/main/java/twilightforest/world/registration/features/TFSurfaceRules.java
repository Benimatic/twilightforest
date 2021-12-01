package twilightforest.world.registration.features;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.levelgen.SurfaceRules;
import twilightforest.world.registration.BlockConstants;
import twilightforest.world.registration.biomes.BiomeKeys;

public class TFSurfaceRules {

	public static SurfaceRules.RuleSource tfSurface() {
		//surface is a normal overworld surface as the base
		//snowy forest is all snow on the top layers
		//glacier has 1 ice layer, 30 packed ice layers, gravel for a few layers, then stone
		//highlands has a noise-based mixture of podzol and coarse dirt

		SurfaceRules.ConditionSource isSnowyForest = SurfaceRules.isBiome(BiomeKeys.SNOWY_FOREST);
		SurfaceRules.ConditionSource isGlacier = SurfaceRules.isBiome(BiomeKeys.GLACIER);
		SurfaceRules.ConditionSource isHighlands = SurfaceRules.isBiome(BiomeKeys.HIGHLANDS);
		SurfaceRules.RuleSource highlandsNoise = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.noiseCondition(TFNoiseRules.COARSE_DIRT, 1.75D), SurfaceRules.state(BlockConstants.COARSE_DIRT)), SurfaceRules.ifTrue(SurfaceRules.noiseCondition(TFNoiseRules.PODZOL, -0.95D), SurfaceRules.state(BlockConstants.PODZOL)));

		ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
		return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
	}
}
