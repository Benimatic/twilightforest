package twilightforest.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

public class GreaterFlaskItem extends BrittleFlaskItem {

	public GreaterFlaskItem(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack getDefaultInstance() {
		ItemStack stack = new ItemStack(this);
		stack.getOrCreateTag().putInt("Uses", 0);
		PotionUtils.setPotion(stack, Potions.EMPTY);
		return stack;
	}

	@Override
	public boolean isBarVisible(ItemStack pStack) {
		return false;
	}

	//no break
	@Override
	public boolean canBreak() {
		return false;
	}

	@Override
	public boolean canBeRefilled(ItemStack stack) {
		return true;
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
		if(allowdedIn(tab)) {
			ItemStack stack = new ItemStack(this);
			stack.getOrCreateTag().putInt("Uses", 0);
			items.add(stack);
		}
	}
}
