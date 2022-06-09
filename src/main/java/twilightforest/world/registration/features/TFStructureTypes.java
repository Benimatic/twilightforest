package twilightforest.world.registration.features;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.TwilightForestMod;

// TODO Rename to TFStructureTypes
public class TFStructureTypes {
	public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, TwilightForestMod.ID);

	public static final RegistryObject<StructureType<?>> CONFIGURED_HEDGE_MAZE = STRUCTURE_TYPES.register("hedge_maze", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> CONFIGURED_QUEST_GROVE = STRUCTURE_TYPES.register("quest_grove", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> CONFIGURED_MUSHROOM_TOWER = STRUCTURE_TYPES.register("mushroom_tower", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> CONFIGURED_HOLLOW_HILL_SMALL = STRUCTURE_TYPES.register("small_hollow_hill", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> CONFIGURED_HOLLOW_HILL_MEDIUM = STRUCTURE_TYPES.register("medium_hollow_hill", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> CONFIGURED_HOLLOW_HILL_LARGE = STRUCTURE_TYPES.register("large_hollow_hill", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> CONFIGURED_NAGA_COURTYARD = STRUCTURE_TYPES.register("naga_courtyard", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> CONFIGURED_LICH_TOWER = STRUCTURE_TYPES.register("lich_tower", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> CONFIGURED_LABYRINTH = STRUCTURE_TYPES.register("labyrinth", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> CONFIGURED_HYDRA_LAIR = STRUCTURE_TYPES.register("hydra_lair", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> CONFIGURED_KNIGHT_STRONGHOLD = STRUCTURE_TYPES.register("knight_stronghold", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> CONFIGURED_DARK_TOWER = STRUCTURE_TYPES.register("dark_tower", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> CONFIGURED_YETI_CAVE = STRUCTURE_TYPES.register("yeti_cave", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> CONFIGURED_AURORA_PALACE = STRUCTURE_TYPES.register("aurora_palace", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> CONFIGURED_TROLL_CAVE = STRUCTURE_TYPES.register("troll_cave", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);
	public static final RegistryObject<StructureType<?>> CONFIGURED_FINAL_CASTLE = STRUCTURE_TYPES.register("final_castle", () -> (StructureType<?>) NoneFeatureConfiguration.CODEC);

	/*private static <T extends FeatureConfiguration> Holder<ConfiguredStructureFeature<?, ?>> register(StructureFeature<T> structure, Function<StructureFeature<T>, ConfiguredStructureFeature<?, ?>> config) {
		Holder<ConfiguredStructureFeature<?, ?>> holder = BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, TwilightForestMod.prefix(Objects.requireNonNull(structure.getRegistryName()).getPath()).toString(), config.apply(structure));
		BuiltinRegistries.registerExact(BuiltinRegistries.STRUCTURE_SETS, TwilightForestMod.prefix(structure.getRegistryName().getPath()).toString(), new StructureSet(holder, new RandomSpreadStructurePlacement(1, 0, RandomSpreadType.LINEAR, 0, Vec3i.ZERO)));
		return holder;
	}*/
}
