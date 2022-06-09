package twilightforest.world.components.structures.start;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import twilightforest.data.tags.BiomeTagGenerator;
import twilightforest.init.TFEntities;
import twilightforest.init.TFLandmark;
import twilightforest.init.TFStructureTypes;
import twilightforest.world.components.structures.util.LandmarkStructure;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// TODO Split into ProgressionStructure and ConfigurableLandmarkStructure. ProgressionStructure should implement the hint stuff
public class ConfigurableProgressionStructure extends Structure implements LandmarkStructure {
    public static final Codec<ConfigurableProgressionStructure> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            AdvancementLockConfig.FLAT_CODEC.forGetter(s -> s.advancementLockConfig),
            HintConfig.FLAT_CODEC.forGetter(s -> s.hintConfig),
            TerraformConfig.FLAT_CODEC.forGetter(s -> s.terraformConfig),
            Structure.settingsCodec(instance)
    ).apply(instance, ConfigurableProgressionStructure::new));

    private final AdvancementLockConfig advancementLockConfig;
    private final HintConfig hintConfig;
    private final TerraformConfig terraformConfig;

    public ConfigurableProgressionStructure(AdvancementLockConfig advancementLockConfig, HintConfig hintConfig, TerraformConfig terraformConfig, StructureSettings structureSettings) {
        super(structureSettings);
        this.advancementLockConfig = advancementLockConfig;
        this.hintConfig = hintConfig;
        this.terraformConfig = terraformConfig;
    }

    public static ConfigurableProgressionStructure extractFromLegacy(TFLandmark landmark) {
        return new ConfigurableProgressionStructure(
                new AdvancementLockConfig(landmark.getRequiredAdvancements()),
                new HintConfig(landmark.createHintBook(), TFEntities.KOBOLD.get()),
                new TerraformConfig(landmark.isSurfaceDecorationsAllowed(), landmark.isUndergroundDecoAllowed(), landmark.shouldAdjustToTerrain()),
                new StructureSettings(
                        BuiltinRegistries.BIOME.getOrCreateTag(BiomeTagGenerator.VALID_HEDGE_MAZE_BIOMES),
                        Map.of(), // Landmarks has Controlled Mob spawning
                        landmark.getDecorationStage(),
                        TerrainAdjustment.NONE
                )
        );
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return Optional.empty();
    }

    @Override
    public StructureType<?> type() {
        return TFStructureTypes.CONFIGURABLE_LANDMARK.get();
    }

    private long lastSpawnedHintMonsterTime;
    /**
     * Try several times to spawn a hint monster
     */
    @Override
    public void trySpawnHintMonster(Level world, Player player, BlockPos pos) {
        // check if the timer is valid
        long currentTime = world.getGameTime();

        // if someone set the time backwards, fix the spawn timer
        if (currentTime < this.lastSpawnedHintMonsterTime) {
            this.lastSpawnedHintMonsterTime = 0;
        }

        if (currentTime - this.lastSpawnedHintMonsterTime > 1200) {
            // okay, time is good, try several times to spawn one
            for (int i = 0; i < 20; i++) {
                if (didSpawnHintMonster(world, player, pos)) {
                    this.lastSpawnedHintMonsterTime = currentTime;
                    break;
                }
            }
        }
    }

    @Override
    public List<ResourceLocation> getRequiredAdvancements() {
        return this.advancementLockConfig.requiredAdvancements();
    }

    @Override
    public ItemStack createHintBook() {
        return this.hintConfig.hintItem().copy();
    }

    @Override
    @Nullable
    public Mob createHintMonster(Level world) {
        return this.hintConfig.hintMob().create(world);
    }

    @Override
    public boolean isSurfaceDecorationsAllowed() {
        return this.terraformConfig.surfaceDecorations();
    }

    @Override
    public boolean isUndergroundDecoAllowed() {
        return this.terraformConfig.undergroundDecorations();
    }

    @Override
    public boolean shouldAdjustToTerrain() {
        return this.terraformConfig.adjustElevation();
    }
}
