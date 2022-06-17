package twilightforest.item;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;
import twilightforest.data.tags.ItemTagGenerator;

import java.util.List;

public class KnightmetalShieldItem extends ShieldItem {

	public KnightmetalShieldItem(Properties properties) {
		super(properties);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return repair.is(ItemTagGenerator.KNIGHTMETAL_INGOTS) || !repair.is(ItemTags.PLANKS) && super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
		return ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction) || super.canPerformAction(stack, toolAction);
	}
}