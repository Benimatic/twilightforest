package twilightforest.world.registration;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.WorldEvent;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.BiomeTagGenerator;
import twilightforest.world.components.chunkgenerators.ChunkGeneratorTwilight;
import twilightforest.world.components.structures.courtyard.NagaCourtyardPieces;
import twilightforest.world.components.structures.darktower.DarkTowerPieces;
import twilightforest.world.components.structures.finalcastle.FinalCastlePieces;
import twilightforest.world.components.structures.icetower.IceTowerPieces;
import twilightforest.world.components.structures.lichtower.LichTowerPieces;
import twilightforest.world.components.structures.lichtowerrevamp.LichTowerRevampPieces;
import twilightforest.world.components.structures.minotaurmaze.MinotaurMazePieces;
import twilightforest.world.components.structures.mushroomtower.MushroomTowerPieces;
import twilightforest.world.components.structures.start.LegacyStructureFeature;
import twilightforest.world.components.structures.stronghold.StrongholdPieces;
import twilightforest.world.components.structures.trollcave.TrollCavePieces;
import twilightforest.world.registration.biomes.BiomeKeys;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public class TFStructures {

	public static final Map<StructureFeature<?>, FeatureConfiguration> SEPARATION_SETTINGS = new HashMap<>();

	public static final StructureFeature<NoneFeatureConfiguration> HEDGE_MAZE = new LegacyStructureFeature(TFFeature.HEDGE_MAZE);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_HEDGE_MAZE = HEDGE_MAZE.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_HEDGE_MAZE_BIOMES);

	public static final StructureFeature<NoneFeatureConfiguration> QUEST_GROVE = new LegacyStructureFeature(TFFeature.QUEST_GROVE);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_QUEST_GROVE = QUEST_GROVE.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_QUEST_GROVE_BIOMES);

	public static final StructureFeature<NoneFeatureConfiguration> MUSHROOM_TOWER = new LegacyStructureFeature(TFFeature.MUSHROOM_TOWER);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_MUSHROOM_TOWER = MUSHROOM_TOWER.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_MUSHROOM_TOWER_BIOMES);

	public static final StructureFeature<NoneFeatureConfiguration> HOLLOW_HILL_SMALL = new LegacyStructureFeature(TFFeature.SMALL_HILL);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_HOLLOW_HILL_SMALL = HOLLOW_HILL_SMALL.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES);

	public static final StructureFeature<NoneFeatureConfiguration> HOLLOW_HILL_MEDIUM = new LegacyStructureFeature(TFFeature.MEDIUM_HILL);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_HOLLOW_HILL_MEDIUM = HOLLOW_HILL_MEDIUM.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES);

	public static final StructureFeature<NoneFeatureConfiguration> HOLLOW_HILL_LARGE = new LegacyStructureFeature(TFFeature.LARGE_HILL);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_HOLLOW_HILL_LARGE = HOLLOW_HILL_LARGE.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES);

	public static final StructureFeature<NoneFeatureConfiguration> NAGA_COURTYARD = new LegacyStructureFeature(TFFeature.NAGA_COURTYARD);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_NAGA_COURTYARD = NAGA_COURTYARD.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_NAGA_COURTYARD_BIOMES);

	public static final StructureFeature<NoneFeatureConfiguration> LICH_TOWER = new LegacyStructureFeature(TFFeature.LICH_TOWER);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_LICH_TOWER = LICH_TOWER.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_LICH_TOWER_BIOMES);

	public static final StructureFeature<NoneFeatureConfiguration> LABYRINTH = new LegacyStructureFeature(TFFeature.LABYRINTH);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_LABYRINTH = LABYRINTH.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_LABYRINTH_BIOMES);

	public static final StructureFeature<NoneFeatureConfiguration> HYDRA_LAIR = new LegacyStructureFeature(TFFeature.HYDRA_LAIR);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_HYDRA_LAIR = HYDRA_LAIR.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_HYDRA_LAIR_BIOMES);

	public static final StructureFeature<NoneFeatureConfiguration> KNIGHT_STRONGHOLD = new LegacyStructureFeature(TFFeature.KNIGHT_STRONGHOLD);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_KNIGHT_STRONGHOLD = KNIGHT_STRONGHOLD.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_HEDGE_MAZE_BIOMES);

	public static final StructureFeature<NoneFeatureConfiguration> DARK_TOWER = new LegacyStructureFeature(TFFeature.DARK_TOWER);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_DARK_TOWER = DARK_TOWER.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_DARK_TOWER_BIOMES);

	public static final StructureFeature<NoneFeatureConfiguration> YETI_CAVE = new LegacyStructureFeature(TFFeature.YETI_CAVE);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_YETI_CAVE = YETI_CAVE.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_YETI_CAVE_BIOMES);

	public static final StructureFeature<NoneFeatureConfiguration> AURORA_PALACE = new LegacyStructureFeature(TFFeature.ICE_TOWER);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_AURORA_PALACE = AURORA_PALACE.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_AURORA_PALACE_BIOMES);

	public static final StructureFeature<NoneFeatureConfiguration> TROLL_CAVE = new LegacyStructureFeature(TFFeature.TROLL_CAVE);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_TROLL_CAVE = TROLL_CAVE.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_TROLL_CAVE_BIOMES);

	public static final StructureFeature<NoneFeatureConfiguration> FINAL_CASTLE = new LegacyStructureFeature(TFFeature.FINAL_CASTLE);
	public static final ConfiguredStructureFeature<?, ?> CONFIGURED_FINAL_CASTLE = FINAL_CASTLE.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_FINAL_CASTLE_BIOMES);

	public static void register(RegistryEvent.Register<StructureFeature<?>> event) {
		SEPARATION_SETTINGS.clear();
		TFFeature.init();
		new MushroomTowerPieces();
		new NagaCourtyardPieces();
		new LichTowerPieces();
		new LichTowerRevampPieces();
		new MinotaurMazePieces();
		new StrongholdPieces();
		new DarkTowerPieces();
		new IceTowerPieces();
		new TrollCavePieces();
		new FinalCastlePieces();

		register(event, HEDGE_MAZE, CONFIGURED_HEDGE_MAZE, TwilightForestMod.prefix("hedge_maze"), 0, 1);
		register(event, QUEST_GROVE, CONFIGURED_QUEST_GROVE, TwilightForestMod.prefix("quest_grove"), 0, 1);
		register(event, MUSHROOM_TOWER, CONFIGURED_MUSHROOM_TOWER, TwilightForestMod.prefix("mushroom_tower"), 0, 1);
		register(event, HOLLOW_HILL_SMALL, CONFIGURED_HOLLOW_HILL_SMALL, TwilightForestMod.prefix("hollow_hill_small"), 0, 1);
		register(event, HOLLOW_HILL_MEDIUM, CONFIGURED_HOLLOW_HILL_MEDIUM, TwilightForestMod.prefix("hollow_hill_medium"), 0, 1);
		register(event, HOLLOW_HILL_LARGE, CONFIGURED_HOLLOW_HILL_LARGE, TwilightForestMod.prefix("hollow_hill_large"), 0, 1);
		register(event, NAGA_COURTYARD, CONFIGURED_NAGA_COURTYARD, TwilightForestMod.prefix("courtyard"), 0, 1);
		register(event, LICH_TOWER, CONFIGURED_LICH_TOWER, TwilightForestMod.prefix("lich_tower"), 0, 1);
		register(event, LABYRINTH, CONFIGURED_LABYRINTH, TwilightForestMod.prefix("labyrinth"), 0, 1);
		register(event, HYDRA_LAIR, CONFIGURED_HYDRA_LAIR, TwilightForestMod.prefix("hydra_lair"), 0, 1);
		register(event, KNIGHT_STRONGHOLD, CONFIGURED_KNIGHT_STRONGHOLD, TwilightForestMod.prefix("knight_stronghold"), 0, 1);
		register(event, DARK_TOWER, CONFIGURED_DARK_TOWER, TwilightForestMod.prefix("dark_tower"), 0, 1);
		register(event, YETI_CAVE, CONFIGURED_YETI_CAVE, TwilightForestMod.prefix("yeti_cave"), 0, 1);
		register(event, AURORA_PALACE, CONFIGURED_AURORA_PALACE, TwilightForestMod.prefix("aurora_palace"), 0, 1);
		register(event, TROLL_CAVE, CONFIGURED_TROLL_CAVE, TwilightForestMod.prefix("troll_cave"), 0, 1);
		register(event, FINAL_CASTLE, CONFIGURED_FINAL_CASTLE, TwilightForestMod.prefix("final_castle"), 0, 1);

		// TODO Beardify more structures (Or bury)
		StructureFeature.NOISE_AFFECTING_FEATURES = ImmutableList.<StructureFeature<?>>builder().addAll(StructureFeature.NOISE_AFFECTING_FEATURES).add(HEDGE_MAZE, QUEST_GROVE, NAGA_COURTYARD, LICH_TOWER, LABYRINTH, KNIGHT_STRONGHOLD, DARK_TOWER, TROLL_CAVE, FINAL_CASTLE).build();
	}

	private static void register(RegistryEvent.Register<StructureFeature<?>> event, StructureFeature<?> structure, ConfiguredStructureFeature<?, ?> config, ResourceLocation name, int min, int max) {
		event.getRegistry().register(structure.setRegistryName(name));
		StructureFeature.STRUCTURES_REGISTRY.put(name.toString(), structure);
		StructureFeatureConfiguration seperation = new StructureFeatureConfiguration(max, min, 0);
		StructureSettings.DEFAULTS = ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder().putAll(StructureSettings.DEFAULTS).
				put(structure, seperation).build();
		SEPARATION_SETTINGS.put(structure, seperation);
		Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(name.getNamespace(), "configured_".concat(name.getPath())), config);
		//FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(structure, config);
	}

	public static void load(WorldEvent.Load event) {
		if(event.getWorld() instanceof ServerLevel serverWorld && serverWorld.getChunkSource().getGenerator() instanceof ChunkGeneratorTwilight tfChunkGen) {
			Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(serverWorld.getChunkSource().getGenerator().getSettings().structureConfig());
			tempMap.putAll(SEPARATION_SETTINGS);
			serverWorld.getChunkSource().getGenerator().getSettings().structureConfig = tempMap;
			HashMap<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> tmpMap = new HashMap<>();
			associateBiomeToConfiguredStructure(tmpMap, CONFIGURED_HOLLOW_HILL_SMALL, BiomeKeys.CLEARING, BiomeKeys.DENSE_FOREST, BiomeKeys.DENSE_MUSHROOM_FOREST, BiomeKeys.FIREFLY_FOREST, BiomeKeys.FOREST, BiomeKeys.MUSHROOM_FOREST, BiomeKeys.OAK_SAVANNAH, BiomeKeys.SPOOKY_FOREST);
			associateBiomeToConfiguredStructure(tmpMap, CONFIGURED_HOLLOW_HILL_MEDIUM, BiomeKeys.CLEARING, BiomeKeys.DENSE_FOREST, BiomeKeys.DENSE_MUSHROOM_FOREST, BiomeKeys.FIREFLY_FOREST, BiomeKeys.FOREST, BiomeKeys.MUSHROOM_FOREST, BiomeKeys.OAK_SAVANNAH, BiomeKeys.SPOOKY_FOREST);
			associateBiomeToConfiguredStructure(tmpMap, CONFIGURED_HOLLOW_HILL_LARGE, BiomeKeys.CLEARING, BiomeKeys.DENSE_FOREST, BiomeKeys.DENSE_MUSHROOM_FOREST, BiomeKeys.FIREFLY_FOREST, BiomeKeys.FOREST, BiomeKeys.MUSHROOM_FOREST, BiomeKeys.OAK_SAVANNAH, BiomeKeys.SPOOKY_FOREST);

			associateBiomeToConfiguredStructure(tmpMap, CONFIGURED_NAGA_COURTYARD, BiomeKeys.CLEARING, BiomeKeys.DENSE_FOREST, BiomeKeys.DENSE_MUSHROOM_FOREST, BiomeKeys.FIREFLY_FOREST, BiomeKeys.FOREST, BiomeKeys.MUSHROOM_FOREST, BiomeKeys.OAK_SAVANNAH, BiomeKeys.SPOOKY_FOREST);
			associateBiomeToConfiguredStructure(tmpMap, CONFIGURED_LICH_TOWER, BiomeKeys.CLEARING, BiomeKeys.DENSE_FOREST, BiomeKeys.DENSE_MUSHROOM_FOREST, BiomeKeys.FIREFLY_FOREST, BiomeKeys.FOREST, BiomeKeys.MUSHROOM_FOREST, BiomeKeys.OAK_SAVANNAH, BiomeKeys.SPOOKY_FOREST);

			associateBiomeToConfiguredStructure(tmpMap, CONFIGURED_LABYRINTH, BiomeKeys.SWAMP);
			associateBiomeToConfiguredStructure(tmpMap, CONFIGURED_HYDRA_LAIR, BiomeKeys.FIRE_SWAMP);

			associateBiomeToConfiguredStructure(tmpMap, CONFIGURED_KNIGHT_STRONGHOLD, BiomeKeys.DARK_FOREST);
			associateBiomeToConfiguredStructure(tmpMap, CONFIGURED_DARK_TOWER, BiomeKeys.DARK_FOREST_CENTER);

			associateBiomeToConfiguredStructure(tmpMap, CONFIGURED_YETI_CAVE, BiomeKeys.SNOWY_FOREST);
			associateBiomeToConfiguredStructure(tmpMap, CONFIGURED_AURORA_PALACE, BiomeKeys.GLACIER);

			associateBiomeToConfiguredStructure(tmpMap, CONFIGURED_TROLL_CAVE, BiomeKeys.HIGHLANDS);
			associateBiomeToConfiguredStructure(tmpMap, CONFIGURED_FINAL_CASTLE, BiomeKeys.FINAL_PLATEAU);

			associateBiomeToConfiguredStructure(tmpMap, CONFIGURED_QUEST_GROVE, BiomeKeys.ENCHANTED_FOREST);
			//get taunted
			//associateBiomeToConfiguredStructure(tmpMap, CONFIGURED_MUSHROOM_TOWER, BiomeKeys.DENSE_MUSHROOM_FOREST);

			ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> tempStructureToMultiMap = ImmutableMap.builder();
			tfChunkGen.getSettings().configuredStructures.entrySet().stream().filter(entry -> !tmpMap.containsKey(entry.getKey())).forEach(tempStructureToMultiMap::put);
			tmpMap.forEach((key, value) -> tempStructureToMultiMap.put(key, ImmutableMultimap.copyOf(value)));
			tfChunkGen.getSettings().configuredStructures = tempStructureToMultiMap.build();
		}
	}

	@SafeVarargs
	private static void associateBiomeToConfiguredStructure(Map<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> map, ConfiguredStructureFeature<?, ?> configuredStructureFeature, ResourceKey<Biome>... biomeRegistryKey) {
		map.putIfAbsent(configuredStructureFeature.feature, HashMultimap.create());
		HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> configuredStructureToBiomeMultiMap = map.get(configuredStructureFeature.feature);
		for (ResourceKey<Biome> biome : biomeRegistryKey) {
			if (configuredStructureToBiomeMultiMap.containsValue(biome)) {
				TwilightForestMod.LOGGER.error("""
								    Detected 2 ConfiguredStructureFeatures that share the same base StructureFeature trying to be added to same biome. One will be prevented from spawning.
								    This issue happens with vanilla too and is why a Snowy Village and Plains Village cannot spawn in the same biome because they both use the Village base structure.
								    The two conflicting ConfiguredStructures are: {}, {}
								    The biome that is attempting to be shared: {}
								""",
						BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureFeature),
						BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureToBiomeMultiMap.entries().stream().filter(e -> e.getValue() == biome).findFirst().get().getKey()),
						biome
				);
			} else {
				configuredStructureToBiomeMultiMap.put(configuredStructureFeature, biome);
			}
		}
	}
}
