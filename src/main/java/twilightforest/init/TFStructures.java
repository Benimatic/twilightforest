package twilightforest.init;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.BiomeTagGenerator;
import twilightforest.world.components.structures.start.LandmarkStructure;
import twilightforest.world.components.structures.start.LegacyLandmark;
import twilightforest.world.components.structures.start.ProgressionStructure;
import twilightforest.world.components.structures.util.AdvancementLockedStructure;
import twilightforest.world.components.structures.util.ControlledSpawns;
import twilightforest.world.components.structures.util.StructureHints;
import twilightforest.world.components.structures.util.DecorationClearance;

import java.util.Map;

public class TFStructures {
	public static final DeferredRegister<Structure> STRUCTURES = DeferredRegister.create(Registry.STRUCTURE_REGISTRY, TwilightForestMod.ID);

	public static final RegistryObject<LegacyLandmark> HEDGE_MAZE = STRUCTURES.register("hedge_maze", () -> new LegacyLandmark(TFLandmark.HEDGE_MAZE, makeSettings(BiomeTagGenerator.VALID_HEDGE_MAZE_BIOMES, TFLandmark.HEDGE_MAZE.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));
	public static final RegistryObject<LegacyLandmark> QUEST_GROVE = STRUCTURES.register("quest_grove", () -> new LegacyLandmark(TFLandmark.QUEST_GROVE, makeSettings(BiomeTagGenerator.VALID_QUEST_GROVE_BIOMES, TFLandmark.QUEST_GROVE.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));
	public static final RegistryObject<LegacyLandmark> MUSHROOM_TOWER = STRUCTURES.register("mushroom_tower", () -> new LegacyLandmark(TFLandmark.MUSHROOM_TOWER, makeSettings(BiomeTagGenerator.VALID_MUSHROOM_TOWER_BIOMES, TFLandmark.MUSHROOM_TOWER.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<LegacyLandmark> HOLLOW_HILL_SMALL = STRUCTURES.register("small_hollow_hill", () -> new LegacyLandmark(TFLandmark.SMALL_HILL, makeSettings(BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES, TFLandmark.SMALL_HILL.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<LegacyLandmark> HOLLOW_HILL_MEDIUM = STRUCTURES.register("medium_hollow_hill", () -> new LegacyLandmark(TFLandmark.MEDIUM_HILL, makeSettings(BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES, TFLandmark.MEDIUM_HILL.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<LegacyLandmark> HOLLOW_HILL_LARGE = STRUCTURES.register("large_hollow_hill", () -> new LegacyLandmark(TFLandmark.LARGE_HILL, makeSettings(BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES, TFLandmark.LARGE_HILL.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<LegacyLandmark> NAGA_COURTYARD = STRUCTURES.register("naga_courtyard", () -> new LegacyLandmark(TFLandmark.NAGA_COURTYARD, makeSettings(BiomeTagGenerator.VALID_NAGA_COURTYARD_BIOMES, TFLandmark.NAGA_COURTYARD.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));
	public static final RegistryObject<LegacyLandmark> LICH_TOWER = STRUCTURES.register("lich_tower", () -> new LegacyLandmark(TFLandmark.LICH_TOWER, makeSettings(BiomeTagGenerator.VALID_LICH_TOWER_BIOMES, TFLandmark.LICH_TOWER.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));
	public static final RegistryObject<LegacyLandmark> LABYRINTH = STRUCTURES.register("labyrinth", () -> new LegacyLandmark(TFLandmark.LABYRINTH, makeSettings(BiomeTagGenerator.VALID_LABYRINTH_BIOMES, TFLandmark.LABYRINTH.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<LegacyLandmark> HYDRA_LAIR = STRUCTURES.register("hydra_lair", () -> new LegacyLandmark(TFLandmark.HYDRA_LAIR, makeSettings(BiomeTagGenerator.VALID_HYDRA_LAIR_BIOMES, TFLandmark.HYDRA_LAIR.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<LegacyLandmark> KNIGHT_STRONGHOLD = STRUCTURES.register("knight_stronghold", () -> new LegacyLandmark(TFLandmark.KNIGHT_STRONGHOLD, makeSettings(BiomeTagGenerator.VALID_KNIGHT_STRONGHOLD_BIOMES, TFLandmark.KNIGHT_STRONGHOLD.getDecorationStage(), TerrainAdjustment.BURY)));
	public static final RegistryObject<LegacyLandmark> DARK_TOWER = STRUCTURES.register("dark_tower", () -> new LegacyLandmark(TFLandmark.DARK_TOWER, makeSettings(BiomeTagGenerator.VALID_DARK_TOWER_BIOMES, TFLandmark.DARK_TOWER.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));
	public static final RegistryObject<LegacyLandmark> YETI_CAVE = STRUCTURES.register("yeti_cave", () -> new LegacyLandmark(TFLandmark.YETI_CAVE, makeSettings(BiomeTagGenerator.VALID_YETI_CAVE_BIOMES, TFLandmark.YETI_CAVE.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<LegacyLandmark> AURORA_PALACE = STRUCTURES.register("aurora_palace", () -> new LegacyLandmark(TFLandmark.ICE_TOWER, makeSettings(BiomeTagGenerator.VALID_AURORA_PALACE_BIOMES, TFLandmark.ICE_TOWER.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<LegacyLandmark> TROLL_CAVE = STRUCTURES.register("troll_cave", () -> new LegacyLandmark(TFLandmark.TROLL_CAVE, makeSettings(BiomeTagGenerator.VALID_TROLL_CAVE_BIOMES, TFLandmark.TROLL_CAVE.getDecorationStage(), TerrainAdjustment.BURY)));
	public static final RegistryObject<LegacyLandmark> FINAL_CASTLE = STRUCTURES.register("final_castle", () -> new LegacyLandmark(TFLandmark.FINAL_CASTLE, makeSettings(BiomeTagGenerator.VALID_FINAL_CASTLE_BIOMES, TFLandmark.FINAL_CASTLE.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));

	public static LandmarkStructure extractLandmarkFromLegacy(TFLandmark landmark) {
		return new LandmarkStructure(
				ControlledSpawns.ControlledSpawningConfig.create(landmark.getSpawnableMonsterLists(), landmark.getAmbientCreatureList(), landmark.getWaterCreatureList()),
				new DecorationClearance.DecorationConfig(landmark.isSurfaceDecorationsAllowed(), landmark.isUndergroundDecoAllowed(), landmark.shouldAdjustToTerrain()),
				new Structure.StructureSettings(
						BuiltinRegistries.BIOME.getOrCreateTag(BiomeTagGenerator.VALID_HEDGE_MAZE_BIOMES),
						Map.of(), // Landmarks have Controlled Mob spawning
						landmark.getDecorationStage(),
						TerrainAdjustment.NONE
				)
		);
	}

	public static ProgressionStructure extractLandmarkFromLegacyWithRequirements(TFLandmark landmark) {
		return new ProgressionStructure(
				new AdvancementLockedStructure.AdvancementLockConfig(landmark.getRequiredAdvancements()),
				ControlledSpawns.ControlledSpawningConfig.create(landmark.getSpawnableMonsterLists(), landmark.getAmbientCreatureList(), landmark.getWaterCreatureList()),
				new StructureHints.HintConfig(landmark.createHintBook(), TFEntities.KOBOLD.get()),
				new DecorationClearance.DecorationConfig(landmark.isSurfaceDecorationsAllowed(), landmark.isUndergroundDecoAllowed(), landmark.shouldAdjustToTerrain()),
				new Structure.StructureSettings(
						BuiltinRegistries.BIOME.getOrCreateTag(BiomeTagGenerator.VALID_HEDGE_MAZE_BIOMES),
						Map.of(), // Landmarks have Controlled Mob spawning
						landmark.getDecorationStage(),
						TerrainAdjustment.NONE
				)
		);
	}

	private static Structure.StructureSettings makeSettings(TagKey<Biome> biomeTag, GenerationStep.Decoration generationStep, TerrainAdjustment terrainModification) {
		return new Structure.StructureSettings(BuiltinRegistries.BIOME.getOrCreateTag(biomeTag), Map.of(), generationStep, terrainModification);
	}
}
