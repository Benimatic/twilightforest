package twilightforest.world.components.structures.start;

import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import twilightforest.world.registration.TFFeature;

@Deprecated
public class LegacyStructureFeature extends TwilightStructureFeature<NoneFeatureConfiguration> {
    public final TFFeature feature;

    public LegacyStructureFeature(@Deprecated TFFeature feature) {
        super(NoneFeatureConfiguration.CODEC, configContext -> feature.generatePieces(configContext).map(piece -> (structurePiecesBuilder, context) -> {
			piece.addChildren(piece, structurePiecesBuilder, context.random());
			structurePiecesBuilder.addPiece(piece);
		}));
        this.feature = feature;
    }

    //Temporally fixed Generation step error
    @Override
    public GenerationStep.Decoration step() {
        return feature.getDecorationStage();
    }
}
