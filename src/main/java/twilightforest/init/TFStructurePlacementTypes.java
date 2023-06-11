package twilightforest.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.TwilightForestMod;
import twilightforest.world.components.structures.placements.BiomeForcedLandmarkPlacement;

import java.util.function.Supplier;

public class TFStructurePlacementTypes {
	public static final DeferredRegister<StructurePlacementType<?>> STRUCTURE_PLACEMENT_TYPES = DeferredRegister.create(Registries.STRUCTURE_PLACEMENT, TwilightForestMod.ID);

	public static final RegistryObject<StructurePlacementType<BiomeForcedLandmarkPlacement>> FORCED_LANDMARK_PLACEMENT_TYPE = registerPlacer("forced_landmark", () -> () -> BiomeForcedLandmarkPlacement.CODEC);

    private static <P extends StructurePlacement> RegistryObject<StructurePlacementType<P>> registerPlacer(String name, Supplier<StructurePlacementType<P>> factory) {
        return STRUCTURE_PLACEMENT_TYPES.register(name, factory);
    }
}
