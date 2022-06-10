package twilightforest.world.components.structures.start;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import twilightforest.init.TFStructureTypes;
import twilightforest.world.components.structures.util.AdvancementLockedStructure;
import twilightforest.world.components.structures.util.StructureHints;

import javax.annotation.Nullable;
import java.util.List;

// Landmark structure with a progression lock; Lich Tower/Labyrinth/Hydra Lair/Final Castle/etc
public class ProgressionStructure extends LandmarkStructure implements AdvancementLockedStructure, StructureHints {
    public static final Codec<ProgressionStructure> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            AdvancementLockConfig.CODEC.fieldOf("advancements_required").forGetter(s -> s.advancementLockConfig),
            ControlledSpawningConfig.FLAT_CODEC.forGetter(s -> s.controlledSpawningConfig),
            HintConfig.FLAT_CODEC.forGetter(s -> s.hintConfig),
            DecorationConfig.FLAT_CODEC.forGetter(s -> s.decorationConfig),
            Structure.settingsCodec(instance)
    ).apply(instance, ProgressionStructure::new));

    final AdvancementLockConfig advancementLockConfig;
    final HintConfig hintConfig;

    public ProgressionStructure(AdvancementLockConfig advancementLockConfig, ControlledSpawningConfig controlledSpawningConfig, HintConfig hintConfig, DecorationConfig decorationConfig, StructureSettings structureSettings) {
        super(controlledSpawningConfig, decorationConfig, structureSettings);

        this.advancementLockConfig = advancementLockConfig;
        this.hintConfig = hintConfig;
    }

    @Override
    public StructureType<?> type() {
        return TFStructureTypes.PROGRESSION_LANDMARK.get();
    }

    @Override
    public List<ResourceLocation> getRequiredAdvancements() {
        return this.advancementLockConfig.requiredAdvancements();
    }

    /**
     * Try several times to spawn a hint monster
     */
    private long lastSpawnedHintMonsterTime;
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
    public ItemStack createHintBook() {
        return this.hintConfig.hintItem().copy();
    }

    @Override
    @Nullable
    public Mob createHintMonster(Level world) {
        return this.hintConfig.hintMob().create(world);
    }
}
