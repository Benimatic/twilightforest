package twilightforest.world.registration.features;

import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.BiomeTagGenerator;
import twilightforest.world.registration.TFFeature;
import twilightforest.world.registration.TFStructures;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class TFConfiguredStructures {

	public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_HEDGE_MAZE = register(TFStructures.HEDGE_MAZE, structure -> structure.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_HEDGE_MAZE_BIOMES, true));
	public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_QUEST_GROVE = register(TFStructures.QUEST_GROVE, structure -> structure.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_QUEST_GROVE_BIOMES, true));
	public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_MUSHROOM_TOWER = register(TFStructures.MUSHROOM_TOWER, structure -> structure.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_MUSHROOM_TOWER_BIOMES));
	public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_HOLLOW_HILL_SMALL = register(TFStructures.HOLLOW_HILL_SMALL, structure -> structure.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES, false, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create(TFFeature.SMALL_HILL.getCombinedMonsterSpawnableList())))));
	public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_HOLLOW_HILL_MEDIUM = register(TFStructures.HOLLOW_HILL_MEDIUM, structure -> structure.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES, false, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create(TFFeature.MEDIUM_HILL.getCombinedMonsterSpawnableList())))));
	public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_HOLLOW_HILL_LARGE = register(TFStructures.HOLLOW_HILL_LARGE, structure -> structure.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES, false, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create(TFFeature.LARGE_HILL.getCombinedMonsterSpawnableList())))));
	public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_NAGA_COURTYARD = register(TFStructures.NAGA_COURTYARD, structure -> structure.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_NAGA_COURTYARD_BIOMES, true));
	public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_LICH_TOWER = register(TFStructures.LICH_TOWER, structure -> structure.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_LICH_TOWER_BIOMES, true, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create(TFFeature.LICH_TOWER.getCombinedMonsterSpawnableList())))));
	public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_LABYRINTH = register(TFStructures.LABYRINTH, structure -> structure.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_LABYRINTH_BIOMES, true, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create(TFFeature.LABYRINTH.getCombinedMonsterSpawnableList())))));
	public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_HYDRA_LAIR = register(TFStructures.HYDRA_LAIR, structure -> structure.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_HYDRA_LAIR_BIOMES));
	public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_KNIGHT_STRONGHOLD = register(TFStructures.KNIGHT_STRONGHOLD, structure -> structure.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_KNIGHT_STRONGHOLD_BIOMES, true, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create(TFFeature.KNIGHT_STRONGHOLD.getCombinedMonsterSpawnableList())))));
	public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_DARK_TOWER = register(TFStructures.DARK_TOWER, structure -> structure.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_DARK_TOWER_BIOMES, true, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create(TFFeature.DARK_TOWER.getCombinedMonsterSpawnableList())))));
	public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_YETI_CAVE = register(TFStructures.YETI_CAVE, structure -> structure.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_YETI_CAVE_BIOMES, false, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create(TFFeature.YETI_CAVE.getCombinedMonsterSpawnableList())))));
	public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_AURORA_PALACE = register(TFStructures.AURORA_PALACE, structure -> structure.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_AURORA_PALACE_BIOMES, false, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create(TFFeature.ICE_TOWER.getCombinedMonsterSpawnableList())))));
	public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_TROLL_CAVE = register(TFStructures.TROLL_CAVE, structure -> structure.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_TROLL_CAVE_BIOMES, true, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create(TFFeature.TROLL_CAVE.getCombinedMonsterSpawnableList())))));
	public static final Holder<ConfiguredStructureFeature<?, ?>> CONFIGURED_FINAL_CASTLE = register(TFStructures.FINAL_CASTLE, structure -> structure.configured(FeatureConfiguration.NONE, BiomeTagGenerator.VALID_FINAL_CASTLE_BIOMES, true, Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create(TFFeature.FINAL_CASTLE.getCombinedMonsterSpawnableList())))));

	private static <T extends FeatureConfiguration> Holder<ConfiguredStructureFeature<?, ?>> register(StructureFeature<T> structure, Function<StructureFeature<T>, ConfiguredStructureFeature<?, ?>> config) {
		Holder<ConfiguredStructureFeature<?, ?>> holder = BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, TwilightForestMod.prefix(Objects.requireNonNull(structure.getRegistryName()).getPath()).toString(), config.apply(structure));
		BuiltinRegistries.registerExact(BuiltinRegistries.STRUCTURE_SETS, TwilightForestMod.prefix(structure.getRegistryName().getPath()).toString(), new StructureSet(holder, new RandomSpreadStructurePlacement(1, 0, RandomSpreadType.LINEAR, 0, Vec3i.ZERO)));
		return holder;
	}

}
