package twilightforest.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import twilightforest.client.ModelRegisterCallback;

import javax.annotation.Nonnull;

public class ItemTF extends Item implements ModelRegisterCallback {
	private final EnumRarity rarity;

	protected ItemTF() {
		this.setCreativeTab(TFItems.creativeTab);
		this.rarity = EnumRarity.COMMON;
	}

	protected ItemTF(EnumRarity rarity) {
		this.setCreativeTab(TFItems.creativeTab);
		this.rarity = rarity;
	}

	@Nonnull
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return stack.isItemEnchanted() ? EnumRarity.RARE.compareTo(rarity) < 1 ? EnumRarity.RARE : rarity : rarity;
	}
}
