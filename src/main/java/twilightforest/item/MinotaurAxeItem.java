package twilightforest.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import twilightforest.TwilightForestMod;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public class MinotaurAxeItem extends AxeItem {

	public MinotaurAxeItem(Tier material, Properties props) {
		super(material, 6F, material.getSpeed() * 0.05f - 3.4f, props);
	}

	@Override
	public int getEnchantmentValue() {
		return Tiers.GOLD.getEnchantmentValue();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flags) {
		super.appendHoverText(stack, world, tooltip, flags);
		tooltip.add(Component.translatable("item.twilightforest.minotaur_axe.tooltip").withStyle(ChatFormatting.GRAY));
	}
}
