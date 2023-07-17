package twilightforest.world.components.structures.placements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import twilightforest.init.TFLandmark;
import twilightforest.init.TFStructurePlacementTypes;
import twilightforest.util.LegacyLandmarkPlacements;
import twilightforest.world.components.chunkgenerators.ChunkGeneratorTwilight;

import java.util.Optional;

public class BiomeForcedLandmarkPlacement extends StructurePlacement {
    public static final Codec<BiomeForcedLandmarkPlacement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            TFLandmark.CODEC.fieldOf("landmark_set").forGetter(p -> p.landmark),
            Codec.intRange(-32, 256).fieldOf("scan_elevation").forGetter(p -> p.scanHeight)
    ).apply(inst, BiomeForcedLandmarkPlacement::new));

    private final TFLandmark landmark;
    private final int scanHeight;

    public BiomeForcedLandmarkPlacement(TFLandmark landmark, int biomeScanHeight) {
        super(Vec3i.ZERO, FrequencyReductionMethod.DEFAULT, 1f, 0, Optional.empty()); // None of these params matter except for possibly flat-world or whatever

        this.landmark = landmark;
        this.scanHeight = biomeScanHeight;
    }

    public boolean isTFPlacementChunk(ChunkGenerator chunkGen, ChunkGeneratorStructureState state, int chunkX, int chunkZ) {
        if (chunkGen instanceof ChunkGeneratorTwilight twilightGenerator)
            return twilightGenerator.isLandmarkPickedForChunk(this.landmark, chunkGen.getBiomeSource().getNoiseBiome(chunkX << 2, this.scanHeight, chunkZ << 2, state.randomState().sampler()), chunkX, chunkZ, state.getLevelSeed());

        if (!LegacyLandmarkPlacements.chunkHasLandmarkCenter(chunkX, chunkZ))
            return false;

        return LegacyLandmarkPlacements.pickVarietyLandmark(chunkX, chunkZ, state.getLevelSeed()) == this.landmark;
    }

    @Override
    protected boolean isPlacementChunk(ChunkGeneratorStructureState state, int chunkX, int chunkZ) {
        if (!LegacyLandmarkPlacements.chunkHasLandmarkCenter(chunkX, chunkZ))
            return false;

        return LegacyLandmarkPlacements.pickVarietyLandmark(chunkX, chunkZ, state.getLevelSeed()) == this.landmark;
    }

    @Override
    public StructurePlacementType<?> type() {
        return TFStructurePlacementTypes.FORCED_LANDMARK_PLACEMENT_TYPE.get();
    }

    public TFLandmark getLandmark() {
        return this.landmark;
    }
}
