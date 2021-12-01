package twilightforest.world.registration.features;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import twilightforest.TwilightForestMod;

public class TFNoiseRules {

	public static final ResourceKey<NormalNoise.NoiseParameters> COARSE_DIRT = createKey("coarse_dirt");
	public static final ResourceKey<NormalNoise.NoiseParameters> PODZOL = createKey("podzol");

	public static void init() {
		//TODO these may need different octave values, not sure yet
		register(COARSE_DIRT, -6, 1.0D, 1.0D, 1.0D, 1.0D);
		register(PODZOL, -6, 1.0D, 1.0D, 1.0D, 1.0D);
	}

	//registering the noise key
	private static ResourceKey<NormalNoise.NoiseParameters> createKey(String name) {
		return ResourceKey.create(Registry.NOISE_REGISTRY, TwilightForestMod.prefix(name));
	}

	//registering the actual noise itself
	private static void register(ResourceKey<NormalNoise.NoiseParameters> key, int firstOctave, double firstAmplitude, double... amplitudes) {
		BuiltinRegistries.register(BuiltinRegistries.NOISE, key, new NormalNoise.NoiseParameters(firstOctave, firstAmplitude, amplitudes));
	}
}
