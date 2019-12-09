package twilightforest.item;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;

import javax.annotation.Nullable;
import java.util.List;

public class ItemTFYetiArmor extends ItemTFArmor {

	public ItemTFYetiArmor(IArmorMaterial material, EquipmentSlotType slot, Rarity rarity, Properties props) {
		super(material, slot, rarity, props);
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlotType slot, String layer) {
		if (slot == EquipmentSlotType.LEGS || slot == EquipmentSlotType.CHEST) {
			return TwilightForestMod.ARMOR_DIR + "yetiarmor_2.png";
		} else {
			return TwilightForestMod.ARMOR_DIR + "yetiarmor_1.png";
		}
	}

	@Override
	public void fillItemGroup(ItemGroup tab, NonNullList<ItemStack> list) {
		if (isInGroup(tab)) {
			ItemStack istack = new ItemStack(this);
			switch (this.slot) {
				case HEAD:
				case CHEST:
				case LEGS:
					istack.addEnchantment(Enchantments.PROTECTION, 2);
					break;
				case FEET:
					istack.addEnchantment(Enchantments.PROTECTION, 2);
					istack.addEnchantment(Enchantments.FEATHER_FALLING, 4);
					break;
				default:
					break;
			}
			list.add(istack);
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, BipedModel _default) {
		return TwilightForestMod.proxy.getYetiArmorModel(armorSlot);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flags) {
		super.addInformation(stack, world, tooltips, flags);
		tooltips.add(new TranslationTextComponent(getTranslationKey() + ".tooltip"));
	}
}
