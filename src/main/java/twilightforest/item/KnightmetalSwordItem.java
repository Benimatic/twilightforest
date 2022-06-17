package twilightforest.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import twilightforest.TwilightForestMod;

import org.jetbrains.annotations.Nullable;
import java.util.List;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public class KnightmetalSwordItem extends SwordItem {

	public KnightmetalSwordItem(Tier material, Properties props) {
		super(material, 3, -2.4F, props);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flags) {
		super.appendHoverText(stack, level, list, flags);
		list.add(Component.translatable(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
	}
}
