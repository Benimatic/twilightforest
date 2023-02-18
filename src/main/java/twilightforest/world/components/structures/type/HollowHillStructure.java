package twilightforest.world.components.structures.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import twilightforest.data.tags.BiomeTagGenerator;
import twilightforest.init.TFLandmark;
import twilightforest.init.TFStructureTypes;
import twilightforest.world.components.structures.util.ConfigurableSpawns;
import twilightforest.world.components.structures.util.LandmarkStructure;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HollowHillStructure extends LandmarkStructure implements ConfigurableSpawns {
    public static final Codec<HollowHillStructure> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    // TODO Fix findGenerationPoint() first before thinking about increasing upper limit
                    Codec.intRange(1, 3).fieldOf("hill_size").forGetter(s -> s.size),
                    ControlledSpawningConfig.FLAT_CODEC.forGetter(s -> s.controlledSpawningConfig)
            )
            .and(landmarkCodec(instance))
            .apply(instance, HollowHillStructure::new)
    );

    private final int size;
    private final ControlledSpawningConfig controlledSpawningConfig;

    public HollowHillStructure(int size, ControlledSpawningConfig controlledSpawningConfig, DecorationConfig decorationConfig, StructureSettings structureSettings) {
        super(decorationConfig, structureSettings);
        this.size = size;
        this.controlledSpawningConfig = controlledSpawningConfig;
    }

    // "Cuts" the box into a half-dome
    public boolean canSpawnMob(BlockPos spawnPos, BoundingBox structureStartBox) {
        float hX = Mth.inverseLerp(spawnPos.getX(), structureStartBox.minX(), structureStartBox.maxX()) * 2 - 1;
        float hY = Mth.inverseLerp(spawnPos.getY(), structureStartBox.minY(), structureStartBox.maxY());
        float hZ = Mth.inverseLerp(spawnPos.getZ(), structureStartBox.minZ(), structureStartBox.maxZ()) * 2 - 1;

        return Mth.length(hX, hY, hZ) < 0.975f;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        // FIXME return an instance of HollowHillComponent directly
        //  Would be nice to pass more config data than just size, like a table for stalactites
        return switch (size) {
            case 1 -> TFLandmark.SMALL_HILL.generateStub(context, this);
            case 2 -> TFLandmark.MEDIUM_HILL.generateStub(context, this);
            default -> TFLandmark.LARGE_HILL.generateStub(context, this);
        };
    }

    @Override
    public StructureType<?> type() {
        return TFStructureTypes.HOLLOW_HILL.get();
    }

    @Override
    public ControlledSpawningConfig getConfig() {
        return this.controlledSpawningConfig;
    }

    public static HollowHillStructure buildSmallHillConfig(BootstapContext<Structure> context) {
        return new HollowHillStructure(
                1,
                ControlledSpawningConfig.create(TFLandmark.SMALL_HILL.getSpawnableMonsterLists(), List.of(), List.of()),
                new DecorationConfig(true, false, false),
                new StructureSettings(
                        context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES),
                        Map.of(), // Landmarks have Controlled Mob spawning
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.NONE
                )
        );
    }

    public static HollowHillStructure buildMediumHillConfig(BootstapContext<Structure> context) {
        return new HollowHillStructure(
                2,
                ControlledSpawningConfig.create(TFLandmark.MEDIUM_HILL.getSpawnableMonsterLists(), List.of(), List.of()),
                new DecorationConfig(true, false, false),
                new StructureSettings(
                        context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES),
                        Map.of(), // Landmarks have Controlled Mob spawning
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.NONE
                )
        );
    }

    public static HollowHillStructure buildLargeHillConfig(BootstapContext<Structure> context) {
        return new HollowHillStructure(
                3,
                ControlledSpawningConfig.create(TFLandmark.LARGE_HILL.getSpawnableMonsterLists(), List.of(), List.of()),
                new DecorationConfig(true, false, false),
                new StructureSettings(
                        context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_HOLLOW_HILL_BIOMES),
                        Map.of(), // Landmarks have Controlled Mob spawning
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.NONE
                )
        );
    }
}
