package twilightforest.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

public class PlayerHelper {
	/**
	 * Fulfills all remaining criteria of the given advancement
	 */
	@Deprecated
	public static void grantAdvancement(ServerPlayer player, ResourceLocation id) {
		PlayerAdvancements advancements = player.getAdvancements();
		Advancement advancement = player.getServer().getAdvancements().getAdvancement(id);
		if (advancement != null) {
			for (String criterion : advancements.getOrStartProgress(advancement).getRemainingCriteria()) {
				advancements.award(advancement, criterion);
			}
		}
	}

	@Deprecated
	public static void grantCriterion(ServerPlayer player, ResourceLocation id, String criterion) {
		PlayerAdvancements advancements = player.getAdvancements();
		Advancement advancement = player.getServer().getAdvancements().getAdvancement(id);
		if (advancement != null) {
			advancements.award(advancement, criterion);
		}
	}

	public static boolean doesPlayerHaveRequiredAdvancements(Player player, ResourceLocation... requiredAdvancements) {
		for (ResourceLocation advancementLocation : requiredAdvancements) {
			if (player.level.isClientSide()) {
				if (player instanceof LocalPlayer) {
					ClientAdvancements manager = ((LocalPlayer) player).connection.getAdvancements();
					Advancement adv = manager.getAdvancements().get(advancementLocation);
					if (adv == null)
						return false;
					AdvancementProgress progress = manager.progress.get(adv);
					return progress != null && progress.isDone();
				}
				return false;
			} else {
				if (player instanceof ServerPlayer) {
					ServerLevel world = ((ServerPlayer) player).getLevel();
					Advancement adv = world.getServer().getAdvancements().getAdvancement(advancementLocation);
					return adv != null && ((ServerPlayer) player).getAdvancements().getOrStartProgress(adv).isDone();
				}
				return false;
			}
		}
		return true;
	}

}
