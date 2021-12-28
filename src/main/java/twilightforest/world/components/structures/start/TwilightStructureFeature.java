package twilightforest.world.components.structures.start;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;

// TODO Interface for Special terraforming (Hollow Hills/Hydra Lair/Labyrinth Mound/Giant Cloud/Yeti Lair)
// TODO Interface for Conquer status
// TODO Config for optional Structure Progression Lock status. They should reference an advancement in the config (Requirement via advancement reference)
// TODO Config for Kobold Book text if lockable
public abstract class TwilightStructureFeature<C extends FeatureConfiguration> extends StructureFeature<C> {
    public TwilightStructureFeature(Codec<C> codec, PieceGeneratorSupplier<C> pieceGeneratorSupplier) {
        super(codec, pieceGeneratorSupplier);
    }
}
