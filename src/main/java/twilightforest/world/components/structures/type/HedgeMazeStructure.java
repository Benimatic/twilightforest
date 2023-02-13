package twilightforest.world.components.structures.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import twilightforest.data.tags.BiomeTagGenerator;
import twilightforest.init.TFLandmark;
import twilightforest.init.TFStructureTypes;
import twilightforest.world.components.structures.util.*;

import java.util.Map;
import java.util.Optional;

public class HedgeMazeStructure extends LandmarkStructure {
    public static final Codec<HedgeMazeStructure> CODEC = RecordCodecBuilder.create(instance -> landmarkCodec(instance).apply(instance, HedgeMazeStructure::new));

    public HedgeMazeStructure(DecorationConfig decorationConfig, StructureSettings structureSettings) {
        super(decorationConfig, structureSettings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return TFLandmark.HEDGE_MAZE.generateStub(context, this);
    }

    @Override
    public StructureType<?> type() {
        return TFStructureTypes.HEDGE_MAZE.get();
    }

    public static HedgeMazeStructure buildStructureConfig(BootstapContext<Structure> context) {
        return new HedgeMazeStructure(
                new DecorationClearance.DecorationConfig(false, true, true),
                new Structure.StructureSettings(
                        context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_HEDGE_MAZE_BIOMES),
                        Map.of(), // Landmarks have Controlled Mob spawning
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.BEARD_THIN
                )
        );
    }
}
