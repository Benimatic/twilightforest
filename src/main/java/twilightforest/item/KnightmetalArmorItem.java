package twilightforest.item;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.armor.KnightmetalArmorModel;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class KnightmetalArmorItem extends ArmorItem {

	private static final Map<EquipmentSlot, HumanoidModel<?>> knightlyArmorModel = new EnumMap<>(EquipmentSlot.class);

	public KnightmetalArmorItem(ArmorMaterial material, EquipmentSlot slot, Properties props) {
		super(material, slot, props);
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot slot, String layer) {
		if (slot == EquipmentSlot.LEGS) {
			return TwilightForestMod.ARMOR_DIR + "knightly_2.png";
		} else {
			return TwilightForestMod.ARMOR_DIR + "knightly_1.png";
		}
	}

	@Nullable
	@Override
	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A original) {
		return (A) knightlyArmorModel.get(armorSlot);
	}

	@OnlyIn(Dist.CLIENT)
	public static void initArmorModel() {
		knightlyArmorModel.put(EquipmentSlot.HEAD, new KnightmetalArmorModel(0.75F));
		knightlyArmorModel.put(EquipmentSlot.CHEST, new KnightmetalArmorModel(1.0F));
		knightlyArmorModel.put(EquipmentSlot.LEGS, new KnightmetalArmorModel(0.5F));
		knightlyArmorModel.put(EquipmentSlot.FEET, new KnightmetalArmorModel(1.0F));
	}
}
