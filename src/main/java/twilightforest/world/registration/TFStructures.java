package twilightforest.world.registration;

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
import twilightforest.world.components.structures.start.LegacyStructure;

import java.util.Map;

public class TFStructures {
	public static final DeferredRegister<Structure> STRUCTURES = DeferredRegister.create(Registry.STRUCTURE_REGISTRY, TwilightForestMod.ID);

	public static final RegistryObject<Structure> HEDGE_MAZE = STRUCTURES.register("hedge_maze", () -> new LegacyStructure(TFFeature.HEDGE_MAZE, makeSettings(BiomeTagGenerator.VALID_HEDGE_MAZE_BIOMES, TFFeature.HEDGE_MAZE.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));
	public static final RegistryObject<Structure> QUEST_GROVE = STRUCTURES.register("quest_grove", () -> new LegacyStructure(TFFeature.QUEST_GROVE, makeSettings(BiomeTagGenerator.VALID_QUEST_GROVE_BIOMES, TFFeature.QUEST_GROVE.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));
	public static final RegistryObject<Structure> MUSHROOM_TOWER = STRUCTURES.register("mushroom_tower", () -> new LegacyStructure(TFFeature.MUSHROOM_TOWER, makeSettings(BiomeTagGenerator.VALID_MUSHROOM_TOWER_BIOMES, TFFeature.MUSHROOM_TOWER.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<Structure> HOLLOW_HILL_SMALL = STRUCTURES.register("small_hollow_hill", () -> new LegacyStructure(TFFeature.SMALL_HILL, makeSettings(BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES, TFFeature.SMALL_HILL.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<Structure> HOLLOW_HILL_MEDIUM = STRUCTURES.register("medium_hollow_hill", () -> new LegacyStructure(TFFeature.MEDIUM_HILL, makeSettings(BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES, TFFeature.MEDIUM_HILL.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<Structure> HOLLOW_HILL_LARGE = STRUCTURES.register("large_hollow_hill", () -> new LegacyStructure(TFFeature.LARGE_HILL, makeSettings(BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES, TFFeature.LARGE_HILL.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<Structure> NAGA_COURTYARD = STRUCTURES.register("naga_courtyard", () -> new LegacyStructure(TFFeature.NAGA_COURTYARD, makeSettings(BiomeTagGenerator.VALID_NAGA_COURTYARD_BIOMES, TFFeature.NAGA_COURTYARD.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));
	public static final RegistryObject<Structure> LICH_TOWER = STRUCTURES.register("lich_tower", () -> new LegacyStructure(TFFeature.LICH_TOWER, makeSettings(BiomeTagGenerator.VALID_LICH_TOWER_BIOMES, TFFeature.LICH_TOWER.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));
	public static final RegistryObject<Structure> LABYRINTH = STRUCTURES.register("labyrinth", () -> new LegacyStructure(TFFeature.LABYRINTH, makeSettings(BiomeTagGenerator.VALID_LABYRINTH_BIOMES, TFFeature.LABYRINTH.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<Structure> HYDRA_LAIR = STRUCTURES.register("hydra_lair", () -> new LegacyStructure(TFFeature.HYDRA_LAIR, makeSettings(BiomeTagGenerator.VALID_HYDRA_LAIR_BIOMES, TFFeature.HYDRA_LAIR.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<Structure> KNIGHT_STRONGHOLD = STRUCTURES.register("knight_stronghold", () -> new LegacyStructure(TFFeature.KNIGHT_STRONGHOLD, makeSettings(BiomeTagGenerator.VALID_KNIGHT_STRONGHOLD_BIOMES, TFFeature.KNIGHT_STRONGHOLD.getDecorationStage(), TerrainAdjustment.BURY)));
	public static final RegistryObject<Structure> DARK_TOWER = STRUCTURES.register("dark_tower", () -> new LegacyStructure(TFFeature.DARK_TOWER, makeSettings(BiomeTagGenerator.VALID_DARK_TOWER_BIOMES, TFFeature.DARK_TOWER.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));
	public static final RegistryObject<Structure> YETI_CAVE = STRUCTURES.register("yeti_cave", () -> new LegacyStructure(TFFeature.YETI_CAVE, makeSettings(BiomeTagGenerator.VALID_YETI_CAVE_BIOMES, TFFeature.YETI_CAVE.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<Structure> AURORA_PALACE = STRUCTURES.register("aurora_palace", () -> new LegacyStructure(TFFeature.ICE_TOWER, makeSettings(BiomeTagGenerator.VALID_AURORA_PALACE_BIOMES, TFFeature.ICE_TOWER.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<Structure> TROLL_CAVE = STRUCTURES.register("troll_cave", () -> new LegacyStructure(TFFeature.TROLL_CAVE, makeSettings(BiomeTagGenerator.VALID_TROLL_CAVE_BIOMES, TFFeature.TROLL_CAVE.getDecorationStage(), TerrainAdjustment.BURY)));
	public static final RegistryObject<Structure> FINAL_CASTLE = STRUCTURES.register("final_castle", () -> new LegacyStructure(TFFeature.FINAL_CASTLE, makeSettings(BiomeTagGenerator.VALID_FINAL_CASTLE_BIOMES, TFFeature.FINAL_CASTLE.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));

	private static Structure.StructureSettings makeSettings(TagKey<Biome> biomeTag, GenerationStep.Decoration generationStep, TerrainAdjustment terrainModification) {
		return new Structure.StructureSettings(BuiltinRegistries.BIOME.getOrCreateTag(biomeTag), Map.of(), generationStep, terrainModification);
	}
}
