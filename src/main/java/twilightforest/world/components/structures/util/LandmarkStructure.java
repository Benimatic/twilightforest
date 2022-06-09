package twilightforest.world.components.structures.util;

// TODO Interface for Special terraforming (Hollow Hills/Hydra Lair/Labyrinth Mound/Giant Cloud/Yeti Lair)
// TODO Interface for Conquer status
// TODO Config for optional Structure Progression Lock status. They should reference an advancement in the config (Requirement via advancement reference)
// TODO Config for Kobold Book text if lockable [StructureHintBook written]
public interface LandmarkStructure extends StructureHints, StructureSpecialSpawns, AdvancementLockedStructure, TerraformingStructure {
}
