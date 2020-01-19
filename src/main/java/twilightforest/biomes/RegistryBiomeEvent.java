package twilightforest.biomes;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import twilightforest.TwilightForestMod;

@EventBusSubscriber(modid = TwilightForestMod.ID)
public final class RegistryBiomeEvent {

	public static final Type TWILIGHT = Type.getType("TWILIGHT");

	@SuppressWarnings("OverlyCoupledMethod")
	@SubscribeEvent
	public static void onRegisterBiomes(Register<Biome> event) {

		final BiomeRegistry biomes = new BiomeRegistry(event.getRegistry());

		biomes.register(
				"twilight_lake",
				new TFBiomeTwilightLake(
						new Biome.Builder()
								.temperature(0.66F)
								.downfall(1)
								.setBaseHeight(-1.8F)
								.setHeightVariation(0.1F)
				),
				TWILIGHT, Type.OCEAN
		);

		biomes.register(
				"twilight_forest",
				new TFBiomeBase(
						new Biome.Builder()
				),
				TWILIGHT, Type.FOREST
		);

		biomes.register(
				"dense_twilight_forest",
				new TFBiomeTwilightForestVariant(
						new Biome.Builder()
								.waterColor(0x005522)
								.temperature(0.7F)
								.downfall(0.8F)
								.setBaseHeight(0.2F)
								.setHeightVariation(0.2F)
				),
				TWILIGHT, Type.FOREST, Type.DENSE
		);

		biomes.register(
				"twilight_highlands",
				new TFBiomeHighlands(
						new Biome.Builder()
								.temperature(0.4F)
								.downfall(0.7F)
								.setBaseHeight(3.5F)
								.setHeightVariation(0.05F)
				),
				TWILIGHT, Type.FOREST, Type.MOUNTAIN, Type.CONIFEROUS
		);

		biomes.register(
				"mushroom_forest",
				new TFBiomeMushrooms(
						new Biome.Builder()
								.temperature(0.8F)
								.downfall(0.8F)
				),
				TWILIGHT, Type.FOREST, Type.MUSHROOM
		);

		biomes.register(
				"twilight_swamp",
				new TFBiomeSwamp(
						new Biome.Builder()
								.temperature(0.8F)
								.downfall(0.9F)
								.setBaseHeight(-0.125F)
								.setHeightVariation(0.125F)
								.waterColor(0xE0FFAE)
				),
				TWILIGHT, Type.SWAMP, Type.WET
		);

		biomes.register(
				"twilight_stream",
				new TFBiomeStream(
						new Biome.Builder()
								.temperature(0.5F)
								.downfall(0.1F)
								.setBaseHeight(-0.5F)
								.setHeightVariation(0)
				),
				TWILIGHT, Type.RIVER
		);

		biomes.register(
				"snowy_forest",
				new TFBiomeSnow(
						new Biome.Builder()
								.temperature(0.09F)
								.downfall(0.9F)
								.setBaseHeight(0.2F)
								.setHeightVariation(0.2F)
				),
				TWILIGHT, Type.FOREST, Type.SNOWY, Type.COLD, Type.CONIFEROUS
		);

		biomes.register(
				"twilight_glacier",
				new TFBiomeGlacier(
						new Biome.Builder()
								.temperature(0)
								.downfall(0.1F)
				),
				TWILIGHT, Type.COLD, Type.SNOWY, Type.WASTELAND
		);

		biomes.register(
				"twilight_clearing",
				new TFBiomeClearing(
						new Biome.Builder()
								.temperature(0.8F)
								.downfall(0.4F)
								.setBaseHeight(0.125F)
								.setHeightVariation(0.05F)
				),
				TWILIGHT, Type.PLAINS, Type.SPARSE
		);

		biomes.register(
				"oak_savannah",
				new TFBiomeOakSavanna(
						new Biome.Builder()
								.temperature(0.9F)
								.downfall(0)
								.setBaseHeight(0.2F)
								.setHeightVariation(0.2F)
				),
				TWILIGHT, Type.FOREST, Type.SPARSE
		);

		biomes.register(
				"firefly_forest",
				new TFBiomeFireflyForest(
						new Biome.Builder()
								.temperature(0.5F)
								.downfall(1)
								.setBaseHeight(0.125F)
								.setHeightVariation(0.05F)
				),
				TWILIGHT, Type.FOREST, Type.LUSH
		);

		biomes.register(
				"deep_mushroom_forest",
				new TFBiomeDeepMushrooms(
						new Biome.Builder()
								.temperature(0.8F)
								.downfall(1)
								.setBaseHeight(0.125F)
								.setHeightVariation(0.05F)
				),
				TWILIGHT, Type.FOREST, Type.MUSHROOM
		);

		biomes.register(
				"dark_forest",
				new TFBiomeDarkForest(
						new Biome.Builder()
								.temperature(0.7F)
								.downfall(0.8F)
								.setBaseHeight(0.125F)
								.setHeightVariation(0.05F)
				),
				TWILIGHT, Type.FOREST, Type.DENSE, Type.SPOOKY
		);

		biomes.register(
				"enchanted_forest",
				new TFBiomeEnchantedForest(
						new Biome.Builder()
				),
				TWILIGHT, Type.FOREST, Type.MAGICAL
		);

		biomes.register(
				"fire_swamp",
				new TFBiomeFireSwamp(
						new Biome.Builder()
								.temperature(1)
								.downfall(0.4F)
								.waterColor(0x6C2C2C)
								.setBaseHeight(0.1F)
								.setHeightVariation(0.2F)
				),
				TWILIGHT, Type.SWAMP, Type.WASTELAND, Type.HOT
		);

		biomes.register(
				"dark_forest_center",
				new TFBiomeDarkForestCenter(
						new Biome.Builder()
								.setBaseHeight(0.125F)
								.setHeightVariation(0.05F)
				),
				TWILIGHT, Type.FOREST, Type.DENSE, Type.SPOOKY, Type.MAGICAL
		);

		biomes.register(
				"highlands_center",
				new TFBiomeFinalPlateau(
						new Biome.Builder()
								.temperature(0.3F)
								.downfall(0.2F)
								.setBaseHeight(10.5F)
								.setHeightVariation(0.025F)
				),
				TWILIGHT, Type.MESA, Type.DEAD, Type.DRY, Type.WASTELAND
		);

		biomes.register(
				"thornlands",
				new TFBiomeThornlands(
						new Biome.Builder()
								.temperature(0.3F)
								.downfall(0.2F)
								.setBaseHeight(6)
								.setHeightVariation(0.1F)
				),
				TWILIGHT, Type.HILLS, Type.DEAD, Type.DRY, Type.WASTELAND
		);

		biomes.register(
				"spooky_forest",
				new TFBiomeSpookyForest(
						new Biome.Builder()
								.temperature(0.5F)
								.downfall(1)
								.setBaseHeight(0.125F)
								.setHeightVariation(0.05F)
								.waterColor(0XFA9111)
				),
				TWILIGHT, Type.FOREST, Type.DEAD, Type.DENSE, Type.SPOOKY
		);
	}

	private static class BiomeRegistry {

		private final IForgeRegistry<Biome> registry;

		BiomeRegistry(IForgeRegistry<Biome> registry) {

			this.registry = registry;
		}

		public void register(String registryName, Biome biome, Type... biomeTypes) {
			biome.setRegistryName(TwilightForestMod.ID, registryName);
			registry.register(biome);
			BiomeDictionary.addTypes(biome, biomeTypes);
		}
	}
}
