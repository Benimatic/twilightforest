package twilightforest.item;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.armor.ModelTFKnightlyArmor;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

public class ItemTFKnightlyArmor extends ArmorItem {

	private static final Map<EquipmentSlotType, BipedModel> knightlyArmorModel = new EnumMap<>(EquipmentSlotType.class);

	public ItemTFKnightlyArmor(IArmorMaterial material, EquipmentSlotType slot, Properties props) {
		super(material, slot, props);
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlotType slot, String layer) {
		if (slot == EquipmentSlotType.LEGS) {
			return TwilightForestMod.ARMOR_DIR + "knightly_2.png";
		} else {
			return TwilightForestMod.ARMOR_DIR + "knightly_1.png";
		}
	}

	@Nullable
	@Override
	@OnlyIn(Dist.CLIENT)
	public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, BipedModel original) {
		return knightlyArmorModel.get(armorSlot);
	}

	@OnlyIn(Dist.CLIENT)
	public static void initArmorModel() {
		knightlyArmorModel.put(EquipmentSlotType.HEAD, new ModelTFKnightlyArmor(0.5F));
		knightlyArmorModel.put(EquipmentSlotType.CHEST, new ModelTFKnightlyArmor(1.0F));
		knightlyArmorModel.put(EquipmentSlotType.LEGS, new ModelTFKnightlyArmor(0.5F));
		knightlyArmorModel.put(EquipmentSlotType.FEET, new ModelTFKnightlyArmor(0.5F));
	}
}
