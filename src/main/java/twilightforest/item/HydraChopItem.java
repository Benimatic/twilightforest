package twilightforest.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import twilightforest.advancements.TFAdvancements;

public class HydraChopItem extends Item {

	public HydraChopItem(Properties props) {
		super(props);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemStack, Level world, LivingEntity living) {
		// if the player is at zero food, achievements
		if (living instanceof ServerPlayer && ((ServerPlayer) living).getFoodData().getFoodLevel() <= 0) {
			TFAdvancements.CONSUME_HYDRA_CHOP.trigger((ServerPlayer) living);
		}
		// then normal effects
		return super.finishUsingItem(itemStack, world, living);
	}
}
