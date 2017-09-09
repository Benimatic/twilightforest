package twilightforest.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class PlayerHelper {
	/**
	 * Fullfills all remaining criteria of the given advancement
	 */
	@Deprecated
	public static void grantAdvancement(EntityPlayerMP player, ResourceLocation id) {
		PlayerAdvancements advancements = player.getAdvancements();
		Advancement advancement = player.getServerWorld().getAdvancementManager().getAdvancement(id);
		if (advancement != null) {
			for (String criterion : advancements.getProgress(advancement).getRemaningCriteria()) {
				advancements.grantCriterion(advancement, criterion);
			}
		}
	}

	@Deprecated
	public static void grantCriterion(EntityPlayerMP player, ResourceLocation id, String criterion) {
		PlayerAdvancements advancements = player.getAdvancements();
		Advancement advancement = player.getServerWorld().getAdvancementManager().getAdvancement(id);
		if (advancement != null) {
			advancements.grantCriterion(advancement, criterion);
		}
	}
}
