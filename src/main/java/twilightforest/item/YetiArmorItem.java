package twilightforest.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.TFModelLayers;
import twilightforest.client.model.armor.YetiArmorModel;

import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class YetiArmorItem extends ArmorItem {
	private static final MutableComponent TOOLTIP = Component.translatable("item.twilightforest.yeti_armor.tooltip").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY));

	public YetiArmorItem(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
		super(material, slot, properties);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String layer) {
		if (slot == EquipmentSlot.LEGS || slot == EquipmentSlot.CHEST) {
			return TwilightForestMod.ARMOR_DIR + "yetiarmor_2.png";
		} else {
			return TwilightForestMod.ARMOR_DIR + "yetiarmor_1.png";
		}
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
		if (this.allowedIn(tab)) {
			ItemStack stack = new ItemStack(this);
			switch (this.getSlot()) {
				case HEAD, CHEST, LEGS -> stack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
				case FEET -> {
					stack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
					stack.enchant(Enchantments.FALL_PROTECTION, 4);
				}
				default -> { }
			}
			items.add(stack);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		tooltip.add(TOOLTIP);
	}

	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(ArmorRender.INSTANCE);
	}

	private static final class ArmorRender implements IItemRenderProperties {
		private static final ArmorRender INSTANCE = new ArmorRender();

		@Override
		public HumanoidModel<?> getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> model) {
			EntityModelSet models = Minecraft.getInstance().getEntityModels();
			ModelPart root = models.bakeLayer(slot == EquipmentSlot.LEGS ? TFModelLayers.YETI_ARMOR_INNER : TFModelLayers.YETI_ARMOR_OUTER);
			return new YetiArmorModel(slot, root);
		}
	}
}