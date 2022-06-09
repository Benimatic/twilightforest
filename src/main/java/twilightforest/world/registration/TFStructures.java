package twilightforest.world.registration;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.structures.JungleTempleStructure;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.BiomeTagGenerator;
import twilightforest.world.components.structures.courtyard.NagaCourtyardPieces;
import twilightforest.world.components.structures.darktower.DarkTowerPieces;
import twilightforest.world.components.structures.finalcastle.FinalCastlePieces;
import twilightforest.world.components.structures.icetower.IceTowerPieces;
import twilightforest.world.components.structures.lichtower.LichTowerPieces;
import twilightforest.world.components.structures.lichtowerrevamp.LichTowerRevampPieces;
import twilightforest.world.components.structures.minotaurmaze.MinotaurMazePieces;
import twilightforest.world.components.structures.mushroomtower.MushroomTowerPieces;
import twilightforest.world.components.structures.stronghold.StrongholdPieces;
import twilightforest.world.components.structures.trollcave.TrollCavePieces;

import java.util.Map;

public class TFStructures {
	public static final DeferredRegister<Structure> STRUCTURES = DeferredRegister.create(Registry.STRUCTURE_REGISTRY, TwilightForestMod.ID);

	// FIXME Move from Jungle Temple to replacement StructureFeature -> Structure impl
	public static final RegistryObject<Structure> HEDGE_MAZE = STRUCTURES.register("hedge_maze", () -> new JungleTempleStructure(makeSettings(BiomeTagGenerator.VALID_HEDGE_MAZE_BIOMES, TFFeature.HEDGE_MAZE.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));
	public static final RegistryObject<Structure> QUEST_GROVE = STRUCTURES.register("quest_grove", () -> new JungleTempleStructure(makeSettings(BiomeTagGenerator.VALID_QUEST_GROVE_BIOMES, TFFeature.QUEST_GROVE.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));
	public static final RegistryObject<Structure> MUSHROOM_TOWER = STRUCTURES.register("mushroom_tower", () -> new JungleTempleStructure(makeSettings(BiomeTagGenerator.VALID_MUSHROOM_TOWER_BIOMES, TFFeature.MUSHROOM_TOWER.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<Structure> HOLLOW_HILL_SMALL = STRUCTURES.register("small_hollow_hill", () -> new JungleTempleStructure(makeSettings(BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES, TFFeature.SMALL_HILL.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<Structure> HOLLOW_HILL_MEDIUM = STRUCTURES.register("medium_hollow_hill", () -> new JungleTempleStructure(makeSettings(BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES, TFFeature.MEDIUM_HILL.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<Structure> HOLLOW_HILL_LARGE = STRUCTURES.register("large_hollow_hill", () -> new JungleTempleStructure(makeSettings(BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES, TFFeature.LARGE_HILL.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<Structure> NAGA_COURTYARD = STRUCTURES.register("naga_courtyard", () -> new JungleTempleStructure(makeSettings(BiomeTagGenerator.VALID_NAGA_COURTYARD_BIOMES, TFFeature.NAGA_COURTYARD.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));
	public static final RegistryObject<Structure> LICH_TOWER = STRUCTURES.register("lich_tower", () -> new JungleTempleStructure(makeSettings(BiomeTagGenerator.VALID_LICH_TOWER_BIOMES, TFFeature.LICH_TOWER.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));
	public static final RegistryObject<Structure> LABYRINTH = STRUCTURES.register("labyrinth", () -> new JungleTempleStructure(makeSettings(BiomeTagGenerator.VALID_LABYRINTH_BIOMES, TFFeature.LABYRINTH.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<Structure> HYDRA_LAIR = STRUCTURES.register("hydra_lair", () -> new JungleTempleStructure(makeSettings(BiomeTagGenerator.VALID_HYDRA_LAIR_BIOMES, TFFeature.HYDRA_LAIR.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<Structure> KNIGHT_STRONGHOLD = STRUCTURES.register("knight_stronghold", () -> new JungleTempleStructure(makeSettings(BiomeTagGenerator.VALID_KNIGHT_STRONGHOLD_BIOMES, TFFeature.KNIGHT_STRONGHOLD.getDecorationStage(), TerrainAdjustment.BURY)));
	public static final RegistryObject<Structure> DARK_TOWER = STRUCTURES.register("dark_tower", () -> new JungleTempleStructure(makeSettings(BiomeTagGenerator.VALID_DARK_TOWER_BIOMES, TFFeature.DARK_TOWER.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));
	public static final RegistryObject<Structure> YETI_CAVE = STRUCTURES.register("yeti_cave", () -> new JungleTempleStructure(makeSettings(BiomeTagGenerator.VALID_YETI_CAVE_BIOMES, TFFeature.YETI_CAVE.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<Structure> AURORA_PALACE = STRUCTURES.register("aurora_palace", () -> new JungleTempleStructure(makeSettings(BiomeTagGenerator.VALID_AURORA_PALACE_BIOMES, TFFeature.ICE_TOWER.getDecorationStage(), TerrainAdjustment.NONE)));
	public static final RegistryObject<Structure> TROLL_CAVE = STRUCTURES.register("troll_cave", () -> new JungleTempleStructure(makeSettings(BiomeTagGenerator.VALID_TROLL_CAVE_BIOMES, TFFeature.TROLL_CAVE.getDecorationStage(), TerrainAdjustment.BURY)));
	public static final RegistryObject<Structure> FINAL_CASTLE = STRUCTURES.register("final_castle", () -> new JungleTempleStructure(makeSettings(BiomeTagGenerator.VALID_FINAL_CASTLE_BIOMES, TFFeature.FINAL_CASTLE.getDecorationStage(), TerrainAdjustment.BEARD_THIN)));

	private static Structure.StructureSettings makeSettings(TagKey<Biome> biomeTag, GenerationStep.Decoration generationStep, TerrainAdjustment terrainModification) {
		return new Structure.StructureSettings(BuiltinRegistries.BIOME.getOrCreateTag(biomeTag), Map.of(), generationStep, terrainModification);
	}

	public static void init() { // TODO Call from main class FIXME relocate/dissolve into new deferred registry
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
	}
}
