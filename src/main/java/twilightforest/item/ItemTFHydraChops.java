package twilightforest.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import twilightforest.advancements.TFAdvancements;

import javax.annotation.Nonnull;

public class ItemTFHydraChops extends ItemTFFood {

	public ItemTFHydraChops(Food food, Properties props) {
		super(food, props);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack itemStack, World world, LivingEntity living) {
		// if the player is at zero food, achievements
		if (living instanceof ServerPlayerEntity && ((ServerPlayerEntity) living).getFoodStats().getFoodLevel() <= 0) {
			TFAdvancements.CONSUME_HYDRA_CHOP.trigger((ServerPlayerEntity) living);
		}
		// then normal effects
		return super.onItemUseFinish(itemStack, world, living);
	}

	private static final Rarity RARITY = Rarity.UNCOMMON;

	@Nonnull
	@Override
	public Rarity getRarity(ItemStack stack) {
		return stack.isEnchanted() ? Rarity.RARE.compareTo(RARITY) > 0 ? Rarity.RARE : RARITY : RARITY;
	}
}
