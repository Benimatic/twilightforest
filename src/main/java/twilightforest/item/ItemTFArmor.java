package twilightforest.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

import javax.annotation.Nonnull;

public abstract class ItemTFArmor extends ArmorItem {
	protected ItemTFArmor(IArmorMaterial materialIn, EquipmentSlotType equipmentSlotIn, Properties props) {
		super(materialIn, equipmentSlotIn, props);
	}
}
