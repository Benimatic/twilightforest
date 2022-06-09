package twilightforest.world.components.structures.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import twilightforest.util.PlayerHelper;

import java.util.List;

public interface AdvancementLockedStructure {
    default boolean doesPlayerHaveRequiredAdvancements(Player player) {
        return PlayerHelper.playerHasRequiredAdvancements(player, this.getRequiredAdvancements());
    }

    List<ResourceLocation> getRequiredAdvancements();

    record AdvancementLockConfig(List<ResourceLocation> requiredAdvancements) {
        public static MapCodec<AdvancementLockConfig> FLAT_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ResourceLocation.CODEC.listOf().fieldOf("required_advancements").forGetter(AdvancementLockConfig::requiredAdvancements)
        ).apply(instance, AdvancementLockConfig::new));

        public static Codec<AdvancementLockConfig> CODEC = FLAT_CODEC.codec();
    }
}
