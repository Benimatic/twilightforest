package twilightforest.init;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.TwilightForestMod;
import twilightforest.world.components.structures.start.LandmarkStructure;
import twilightforest.world.components.structures.start.LegacyLandmark;
import twilightforest.world.components.structures.start.ProgressionStructure;

public class TFStructureTypes {
	public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, TwilightForestMod.ID);

	public static final RegistryObject<StructureType<LegacyLandmark>> LEGACY_LANDMARK = STRUCTURE_TYPES.register("legacy_landmark", () -> () -> LegacyLandmark.CODEC);
	public static final RegistryObject<StructureType<LandmarkStructure>> CONFIGURABLE_LANDMARK = STRUCTURE_TYPES.register("configurable_landmark", () -> () -> LandmarkStructure.CODEC);
	public static final RegistryObject<StructureType<ProgressionStructure>> PROGRESSION_LANDMARK = STRUCTURE_TYPES.register("progression_landmark", () -> () -> ProgressionStructure.CODEC);

	// TODO Investigate if Structure Sets need any action
	/*private static <T extends FeatureConfiguration> Holder<ConfiguredStructureFeature<?, ?>> register(StructureFeature<T> structure, Function<StructureFeature<T>, ConfiguredStructureFeature<?, ?>> config) {
		Holder<ConfiguredStructureFeature<?, ?>> holder = BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, TwilightForestMod.prefix(Objects.requireNonNull(structure.getRegistryName()).getPath()).toString(), config.apply(structure));
		BuiltinRegistries.registerExact(BuiltinRegistries.STRUCTURE_SETS, TwilightForestMod.prefix(structure.getRegistryName().getPath()).toString(), new StructureSet(holder, new RandomSpreadStructurePlacement(1, 0, RandomSpreadType.LINEAR, 0, Vec3i.ZERO)));
		return holder;
	}*/
}
