package twilightforest.world.components.structures.util;

import com.mojang.datafixers.Products;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.structure.Structure;

// Landmark structure without progression lock; Hollow Hills/Hedge Maze/Naga Courtyard/Quest Grove
public abstract class LandmarkStructure extends Structure implements DecorationClearance {
    protected static <S extends LandmarkStructure> Products.P2<RecordCodecBuilder.Mu<S>, DecorationConfig, StructureSettings> landmarkCodec(RecordCodecBuilder.Instance<S> instance) {
        return instance.group(
                DecorationConfig.FLAT_CODEC.forGetter(s -> s.decorationConfig),
                Structure.settingsCodec(instance)
        );
    }

    final DecorationConfig decorationConfig;

    public LandmarkStructure(DecorationConfig decorationConfig, StructureSettings structureSettings) {
        super(structureSettings);
        this.decorationConfig = decorationConfig;
    }

    @Override
    public boolean isSurfaceDecorationsAllowed() {
        return this.decorationConfig.surfaceDecorations();
    }

    @Override
    public boolean isUndergroundDecoAllowed() {
        return this.decorationConfig.undergroundDecorations();
    }

    @Override
    public boolean shouldAdjustToTerrain() {
        return this.decorationConfig.adjustElevation();
    }
}
