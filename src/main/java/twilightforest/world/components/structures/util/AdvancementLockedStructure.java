package twilightforest.world.components.structures.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import twilightforest.util.PlayerHelper;

import java.util.List;

public interface AdvancementLockedStructure {
    default boolean doesPlayerHaveRequiredAdvancements(Player player) {
        return PlayerHelper.playerHasRequiredAdvancements(player, this.getRequiredAdvancements());
    }

    List<ResourceLocation> getRequiredAdvancements();
}
