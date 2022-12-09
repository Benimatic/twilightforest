package twilightforest.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.TwilightForestMod;
import twilightforest.world.components.structures.start.LegacyLandmark;

import java.util.function.Supplier;

public class TFStructureTypes {
	public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, TwilightForestMod.ID);

	public static final RegistryObject<StructureType<LegacyLandmark>> LEGACY_LANDMARK = registerType("legacy_landmark", () -> () -> LegacyLandmark.CODEC);

	private static <P extends Structure> RegistryObject<StructureType<P>> registerType(String name, Supplier<StructureType<P>> factory) {
		return STRUCTURE_TYPES.register(name, factory);
	}
}
