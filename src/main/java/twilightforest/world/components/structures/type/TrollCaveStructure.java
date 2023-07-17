package twilightforest.world.components.structures.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
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
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.init.TFStructureTypes;
import twilightforest.world.components.structures.trollcave.TrollCaveMainComponent;
import twilightforest.world.components.structures.util.ConfigurableSpawns;
import twilightforest.world.components.structures.util.ControlledSpawns;
import twilightforest.world.components.structures.util.ProgressionStructure;

import java.util.List;
import java.util.Map;

public class TrollCaveStructure extends ProgressionStructure implements ConfigurableSpawns {
    public static final Codec<TrollCaveStructure> CODEC = RecordCodecBuilder.create(instance -> instance
                    .group(ControlledSpawns.ControlledSpawningConfig.FLAT_CODEC.forGetter(ConfigurableSpawns::getConfig))
                    .and(progressionCodec(instance))
                    .apply(instance, TrollCaveStructure::new)
    );
    private final ControlledSpawningConfig controlledSpawningConfig;

    public TrollCaveStructure(ControlledSpawningConfig controlledSpawningConfig, AdvancementLockConfig advancementLockConfig, HintConfig hintConfig, DecorationConfig decorationConfig, StructureSettings structureSettings) {
        super(advancementLockConfig, hintConfig, decorationConfig, structureSettings);
        this.controlledSpawningConfig = controlledSpawningConfig;
    }

    @Override
    protected @Nullable StructurePiece getFirstPiece(GenerationContext context, RandomSource random, ChunkPos chunkPos, int x, int y, int z) {
        return new TrollCaveMainComponent(TFStructurePieceTypes.TFTCMai.get(), 0, x, y, z);
    }

    @Override
    public StructureType<?> type() {
        return TFStructureTypes.TROLL_CAVE.get();
    }

    @Override
    public ControlledSpawningConfig getConfig() {
        return this.controlledSpawningConfig;
    }

    @Override
    protected boolean dontCenter() {
        return true;
    }

    public static TrollCaveStructure buildTrollCaveConfig(BootstapContext<Structure> context) {
        return new TrollCaveStructure(
                ControlledSpawningConfig.create(List.of(List.of(
                        new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 5, 1, 2),
                        new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 10, 1, 2),
                        new MobSpawnSettings.SpawnerData(TFEntities.TROLL.get(), 20, 1, 2),
                        new MobSpawnSettings.SpawnerData(EntityType.WITCH, 5, 1, 1)
                ), List.of(
                        // cloud monsters
                        new MobSpawnSettings.SpawnerData(TFEntities.GIANT_MINER.get(), 10, 1, 1),
                        new MobSpawnSettings.SpawnerData(TFEntities.ARMORED_GIANT.get(), 10, 1, 1)
                )), List.of(), List.of()),
                new AdvancementLockConfig(List.of(TwilightForestMod.prefix("progress_merge"))),
                new HintConfig(HintConfig.book("trollcave", 3), TFEntities.KOBOLD.get()),
                new DecorationConfig(4, true, true, false),
                new StructureSettings(
                        context.lookup(Registries.BIOME).getOrThrow(BiomeTagGenerator.VALID_TROLL_CAVE_BIOMES),
                        Map.of(), // Landmarks have Controlled Mob spawning
                        GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
                        TerrainAdjustment.BURY
                )
        );
    }
}
