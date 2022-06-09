package twilightforest.world.registration.features;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.TwilightForestMod;

public class TFStructureTypes {
	public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, TwilightForestMod.ID);

	public static final RegistryObject<StructureType<?>> HEDGE_MAZE_TYPE = STRUCTURE_TYPES.register("hedge_maze_type", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> QUEST_GROVE_TYPE = STRUCTURE_TYPES.register("quest_grove_type", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> MUSHROOM_TOWER_TYPE = STRUCTURE_TYPES.register("mushroom_tower_type", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> HOLLOW_HILL_SMALL_TYPE = STRUCTURE_TYPES.register("small_hollow_hill_type", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> HOLLOW_HILL_MEDIUM_TYPE = STRUCTURE_TYPES.register("medium_hollow_hill_type", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> HOLLOW_HILL_LARGE_TYPE = STRUCTURE_TYPES.register("large_hollow_hill_type", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> NAGA_COURTYARD_TYPE = STRUCTURE_TYPES.register("naga_courtyard_type", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> LICH_TOWER_TYPE = STRUCTURE_TYPES.register("lich_tower_type", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> LABYRINTH_TYPE = STRUCTURE_TYPES.register("labyrinth_type", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> HYDRA_LAIR_TYPE = STRUCTURE_TYPES.register("hydra_lair_type", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> KNIGHT_STRONGHOLD_TYPE = STRUCTURE_TYPES.register("knight_stronghold_type", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> DARK_TOWER_TYPE = STRUCTURE_TYPES.register("dark_tower_type", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> YETI_CAVE_TYPE = STRUCTURE_TYPES.register("yeti_cave_type", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> AURORA_PALACE_TYPE = STRUCTURE_TYPES.register("aurora_palace_type", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> TROLL_CAVE_TYPE = STRUCTURE_TYPES.register("troll_cave_type", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> FINAL_CASTLE_TYPE = STRUCTURE_TYPES.register("final_castle_type", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);

	/*private static <T extends FeatureConfiguration> Holder<ConfiguredStructureFeature<?, ?>> register(StructureFeature<T> structure, Function<StructureFeature<T>, ConfiguredStructureFeature<?, ?>> config) {
		Holder<ConfiguredStructureFeature<?, ?>> holder = BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, TwilightForestMod.prefix(Objects.requireNonNull(structure.getRegistryName()).getPath()).toString(), config.apply(structure));
		BuiltinRegistries.registerExact(BuiltinRegistries.STRUCTURE_SETS, TwilightForestMod.prefix(structure.getRegistryName().getPath()).toString(), new StructureSet(holder, new RandomSpreadStructurePlacement(1, 0, RandomSpreadType.LINEAR, 0, Vec3i.ZERO)));
		return holder;
	}*/
}
