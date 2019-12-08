package twilightforest.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

import javax.annotation.Nonnull;

public abstract class ItemTFArmor extends ArmorItem {
	private final Rarity RARITY;

	//protected ItemTFArmor(ArmorMaterial materialIn, EquipmentSlotType equipmentSlotIn) {
	//	super(materialIn, 0, equipmentSlotIn);
	//	this.setCreativeTab(TFItems.creativeTab);
	//	this.RARITY = Rarity.COMMON;
	//}

	protected ItemTFArmor(IArmorMaterial materialIn, EquipmentSlotType equipmentSlotIn, Rarity rarity, Properties props) {
		super(materialIn, equipmentSlotIn, props.group(TFItems.creativeTab));
		this.RARITY = rarity;
	}

	@Nonnull
	@Override
	public Rarity getRarity(ItemStack stack) {
		return stack.isEnchanted() ? Rarity.RARE.compareTo(RARITY) > 0 ? Rarity.RARE : RARITY : RARITY;
	}
}
