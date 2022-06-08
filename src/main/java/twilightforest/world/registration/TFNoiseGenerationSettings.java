package twilightforest.world.registration;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.CubicSpline;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.TwilightForestMod;
import twilightforest.world.registration.surface_rules.TFSurfaceRules;

import java.util.List;

public class TFNoiseGenerationSettings {

	public static final DeferredRegister<NoiseGeneratorSettings> NOISE_GENERATORS = DeferredRegister.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, TwilightForestMod.ID);

	public static final RegistryObject<NoiseGeneratorSettings> TWILIGHT_NOISE_GEN = NOISE_GENERATORS.register("twilight_noise_gen", TFNoiseGenerationSettings::tfDefault);
	public static final RegistryObject<NoiseGeneratorSettings> SKYLIGHT_NOISE_GEN = NOISE_GENERATORS.register("skylight_noise_gen", TFNoiseGenerationSettings::skylight);

	public static NoiseGeneratorSettings tfDefault() {
		NoiseSettings tfNoise = NoiseSettings.create(
				-32, //TODO Deliberate over this. For now it'll be -32
				256,
				1,
				2
		);

		return new NoiseGeneratorSettings(
				tfNoise,
				Blocks.STONE.defaultBlockState(),
				Blocks.WATER.defaultBlockState(),
				new NoiseRouter(
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero()
				),
				TFSurfaceRules.tfSurface(),
				List.of(),
				0,
				false,
				false,
				false,
				false
		);
	}

	public static NoiseGeneratorSettings skylight() {
		NoiseSettings skylightNoise = NoiseSettings.create(
				-32, //min height
				256, // height
				2, // size_horizontal
				1 // size_vertical
		);

		// Problem island at /tp 9389.60 90.00 11041.66
		return new NoiseGeneratorSettings(
				// https://misode.github.io/worldgen/noise-settings/
				// So far this looks great! We just need to raise the island levels to sea level. Otherwise is generates flat-flakey islands that really show the roots on dirt bottoms from trees
				skylightNoise,
				Blocks.STONE.defaultBlockState(),
				Blocks.WATER.defaultBlockState(),
				new NoiseRouter(
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero(),
						DensityFunctions.zero()
				),
				TFSurfaceRules.tfSurface(),
				List.of(),
				0,
				false,
				false,
				false,
				false
		);
	}
}
