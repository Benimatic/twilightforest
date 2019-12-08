package twilightforest.item;

import net.minecraft.block.CauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;

import javax.annotation.Nullable;
import java.util.List;

public class ItemTFArcticArmor extends ItemTFArmor implements IDyeableArmorItem {
	public ItemTFArcticArmor(IArmorMaterial armorMaterial, EquipmentSlotType armorType, Rarity rarity, Properties props) {
		super(armorMaterial, armorType, rarity, props);
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlotType slot, String layer) {
		if (slot == EquipmentSlotType.LEGS) {
			return TwilightForestMod.ARMOR_DIR + "arcticarmor_2" + (layer == null ? "_dyed" : "_overlay") + ".png";
		} else {
			return TwilightForestMod.ARMOR_DIR + "arcticarmor_1" + (layer == null ? "_dyed" : "_overlay") + ".png";
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, BipedModel oldM) {
		return TwilightForestMod.proxy.getArcticArmorModel(armorSlot);
	}

	//TODO 1.14: No substitute?
	@Override
	public boolean hasOverlay(ItemStack stack) {
		return getColor(stack) != 0xFFFFFF;
	}

	@Override
	public boolean hasColor(ItemStack stack) {
		CompoundNBT CompoundNBT = stack.getTag();
		return (CompoundNBT != null && CompoundNBT.contains("display", 10)) && CompoundNBT.getCompound("display").contains("color", 3);
	}

	@Override
	public int getColor(ItemStack stack) {
		return this.getColor(stack, 1);
	}

	@Override
	public void removeColor(ItemStack stack) {
		this.removeColor(stack, 1);
	}

	@Override
	public void setColor(ItemStack stack, int color) {
		this.setColor(stack, color, 1);
	}

	public int getColor(ItemStack stack, int type) {
		String string = "";//type == 0 ? "" : ("" + type);
		CompoundNBT stackTagCompound = stack.getTag();

		int color = 0xBDCFD9;

		if (stackTagCompound != null) {
			CompoundNBT displayCompound = stackTagCompound.getCompound("display");

			if (displayCompound.contains("color" + string, 3))
				color = displayCompound.getInt("color" + string);
		}

		switch (type) {
			//case 0:
				//return stack.getItem() != TFItems.arctic_helmet ? 0x793828 : 0xFFFFFF;
			case 0:
				return 0xFFFFFF;
			default:
				return color;
		}
	}

	public void removeColor(ItemStack stack, int type) {
		String string = "";//type == 0 ? "" : ("" + type);
		CompoundNBT stackTagCompound = stack.getTag();

		if (stackTagCompound != null) {
			CompoundNBT displayCompound = stackTagCompound.getCompound("display");

			if (displayCompound.contains("color" + string))
				displayCompound.remove("color" + string);

			if (displayCompound.contains("hasColor"))
				displayCompound.putBoolean("hasColor", false);
		}
	}

	public void setColor(ItemStack stack, int color, int type) {
		String string = "";//type == 0 ? "" : ("" + type);
		CompoundNBT stackTagCompound = stack.getTag();

		if (stackTagCompound == null) {
			stackTagCompound = new CompoundNBT();
			stack.setTag(stackTagCompound);
		}

		CompoundNBT displayCompound = stackTagCompound.getCompound("display");

		if (!stackTagCompound.contains("display", 10))
			stackTagCompound.put("display", displayCompound);

		displayCompound.putInt("color" + string, color);
		displayCompound.putBoolean("hasColor", true);
	}

	@Override
	public ActionResultType onItemUseFirst(ItemStack itemstack, ItemUseContext context) {
		//ItemStack stack = player.getHeldItem(hand);

		if (this.hasColor(itemstack)) {
			BlockState blockAt = context.getWorld().getBlockState(context.getPos());

			if (blockAt.getBlock() instanceof CauldronBlock && blockAt.get(CauldronBlock.LEVEL) > 0) {
				removeColor(itemstack);
				context.getPlayer().addStat(Stats.CLEAN_ARMOR);

				((CauldronBlock) blockAt.getBlock()).setWaterLevel(context.getWorld(), context.getPos(), blockAt, blockAt.get(CauldronBlock.LEVEL) - 1);
				return ActionResultType.SUCCESS;
			}
		}

		return ActionResultType.PASS;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent("item.twilightforest.arctic_armor.tooltip"));
	}
}