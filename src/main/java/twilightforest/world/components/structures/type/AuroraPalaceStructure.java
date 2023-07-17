package twilightforest.world.components.structures.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.BiomeTagGenerator;
import twilightforest.init.TFEntities;
import twilightforest.init.TFStructureTypes;
import twilightforest.world.components.structures.icetower.IceTowerMainComponent;
import twilightforest.world.components.structures.util.ConquerableStructure;

import java.util.List;
import java.util.Map;

public class AuroraPalaceStructure extends ConquerableStructure {
    public static final Codec<AuroraPalaceStructure> CODEC = RecordCodecBuilder.create(instance ->
            conquerStatusCodec(instance).apply(instance, AuroraPalaceStructure::new)
    );

    public AuroraPalaceStructure(ControlledSpawningConfig controlledSpawningConfig, AdvancementLockConfig advancementLockConfig, HintConfig hintConfig, DecorationConfig decorationConfig, StructureSettings structureSettings) {
        super(controlledSpawningConfig, advancementLockConfig, hintConfig, decorationConfig, structureSettings);
    }

    @Override
    protected @Nullable StructurePiece getFirstPiece(GenerationContext context, RandomSource random, ChunkPos chunkPos, int x, int y, int z) {
        return new IceTowerMainComponent(random, 0, x, y, z);
    }

    @Override
    public StructureType<?> type() {
        return TFStructureTypes.AURORA_PALACE.get();
    }

    public static AuroraPalaceStructure buildAuroraPalaceConfig(BootstapContext<Structure> context) {
        return new AuroraPalaceStructure(
                ControlledSpawningConfig.firstIndexMonsters(
                        new MobSpawnSettings.SpawnerData(TFEntities.SNOW_GUARDIAN.get(), 10, 1, 2),
                        new MobSpawnSettings.SpawnerData(TFEntities.STABLE_ICE_CORE.get(), 10, 1, 2),
                        new MobSpawnSettings.SpawnerData(TFEntities.UNSTABLE_ICE_CORE.get(), 5, 1, 2)
                ),
                new AdvancementLockConfig(List.of(TwilightForestMod.prefix("progress_yeti"))),
                new HintConfig(HintConfig.book("icetower", 3), TFEntities.KOBOLD.get()),
                new DecorationConfig(2, false, true, false),
                new StructureSettings(
                        context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_AURORA_PALACE_BIOMES),
                        Map.of(), // Landmarks have Controlled Mob spawning
                        GenerationStep.Decoration.SURFACE_STRUCTURES,
                        TerrainAdjustment.NONE
                )
        );
    }
}
