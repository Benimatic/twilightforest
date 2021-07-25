package twilightforest.item;

import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.armor.ArcticArmorModel;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.minecraft.world.item.Item.Properties;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class ArcticArmorItem extends ArmorItem implements DyeableLeatherItem {

	private static final Map<EquipmentSlot, HumanoidModel<?>> arcticArmorModel = new EnumMap<>(EquipmentSlot.class);

	public ArcticArmorItem(ArmorMaterial armorMaterial, EquipmentSlot armorType, Properties props) {
		super(armorMaterial, armorType, props);
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot slot, String layer) {
		if (slot == EquipmentSlot.LEGS) {
			return TwilightForestMod.ARMOR_DIR + "arcticarmor_2" + (layer == null ? "_dyed" : "_overlay") + ".png";
		} else {
			return TwilightForestMod.ARMOR_DIR + "arcticarmor_1" + (layer == null ? "_dyed" : "_overlay") + ".png";
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	@SuppressWarnings("unchecked")
	public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A oldM) {
		return (A) arcticArmorModel.get(armorSlot);
	}

	@OnlyIn(Dist.CLIENT)
	public static void initArmorModel() {
		arcticArmorModel.put(EquipmentSlot.HEAD, new ArcticArmorModel(0.75F));
		arcticArmorModel.put(EquipmentSlot.CHEST, new ArcticArmorModel(1.0F));
		arcticArmorModel.put(EquipmentSlot.LEGS, new ArcticArmorModel(0.5F));
		arcticArmorModel.put(EquipmentSlot.FEET, new ArcticArmorModel(1.0F));
	}

	@Override
	public boolean hasCustomColor(ItemStack stack) {
		CompoundTag CompoundNBT = stack.getTag();
		return (CompoundNBT != null && CompoundNBT.contains("display", 10)) && CompoundNBT.getCompound("display").contains("color", 3);
	}

	@Override
	public int getColor(ItemStack stack) {
		return this.getColor(stack, 1);
	}

	@Override
	public void clearColor(ItemStack stack) {
		this.removeColor(stack, 1);
	}

	@Override
	public void setColor(ItemStack stack, int color) {
		this.setColor(stack, color, 1);
	}

	public int getColor(ItemStack stack, int type) {
		String string = "";//type == 0 ? "" : ("" + type);
		CompoundTag stackTagCompound = stack.getTag();

		int color = 0xBDCFD9;

		if (stackTagCompound != null) {
			CompoundTag displayCompound = stackTagCompound.getCompound("display");

			if (displayCompound.contains("color" + string, 3))
				color = displayCompound.getInt("color" + string);
		}

		switch (type) {
			case 0:
				return 0xFFFFFF;
			default:
				return color;
		}
	}

	public void removeColor(ItemStack stack, int type) {
		String string = "";
		CompoundTag stackTagCompound = stack.getTag();

		if (stackTagCompound != null) {
			CompoundTag displayCompound = stackTagCompound.getCompound("display");

			if (displayCompound.contains("color" + string))
				displayCompound.remove("color" + string);

			if (displayCompound.contains("hasColor"))
				displayCompound.putBoolean("hasColor", false);
		}
	}

	public void setColor(ItemStack stack, int color, int type) {
		String string = "";
		CompoundTag stackTagCompound = stack.getTag();

		if (stackTagCompound == null) {
			stackTagCompound = new CompoundTag();
			stack.setTag(stackTagCompound);
		}

		CompoundTag displayCompound = stackTagCompound.getCompound("display");

		if (!stackTagCompound.contains("display", 10))
			stackTagCompound.put("display", displayCompound);

		displayCompound.putInt("color" + string, color);
		displayCompound.putBoolean("hasColor", true);
	}

	@Override
	public InteractionResult onItemUseFirst(ItemStack itemstack, UseOnContext context) {

		if (this.hasCustomColor(itemstack)) {
			BlockState blockAt = context.getLevel().getBlockState(context.getClickedPos());

			if (blockAt.getBlock() instanceof CauldronBlock && blockAt.getValue(CauldronBlock.LEVEL) > 0) {
				clearColor(itemstack);
				context.getPlayer().awardStat(Stats.CLEAN_ARMOR);

				((CauldronBlock) blockAt.getBlock()).setWaterLevel(context.getLevel(), context.getClickedPos(), blockAt, blockAt.getValue(CauldronBlock.LEVEL) - 1);
				return InteractionResult.SUCCESS;
			}
		}

		return InteractionResult.PASS;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslatableComponent("item.twilightforest.arctic_armor.tooltip"));
	}
}
