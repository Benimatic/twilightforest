package twilightforest.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import twilightforest.client.ModelRegisterCallback;

import javax.annotation.Nonnull;

public abstract class ItemTFArmor extends ItemArmor implements ModelRegisterCallback {
	private final EnumRarity RARITY;

	//protected ItemTFArmor(ArmorMaterial materialIn, EquipmentSlotType equipmentSlotIn) {
	//	super(materialIn, 0, equipmentSlotIn);
	//	this.setCreativeTab(TFItems.creativeTab);
	//	this.RARITY = EnumRarity.COMMON;
	//}

	protected ItemTFArmor(ArmorMaterial materialIn, EquipmentSlotType equipmentSlotIn, EnumRarity rarity) {
		super(materialIn, 0, equipmentSlotIn);
		this.setCreativeTab(TFItems.creativeTab);
		this.RARITY = rarity;
	}

	@Nonnull
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return stack.isItemEnchanted() ? EnumRarity.RARE.compareTo(RARITY) > 0 ? EnumRarity.RARE : RARITY : RARITY;
	}
}
