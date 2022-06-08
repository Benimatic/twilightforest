package twilightforest.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantments;

public class IronwoodAxeItem extends AxeItem {

	protected IronwoodAxeItem(Tier material, Properties props) {
		super(material, 6F, material.getSpeed() * 0.05f - 3.4f, props);
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
		if (this.allowedIn(tab)) {
			ItemStack istack = new ItemStack(this);
			istack.enchant(Enchantments.BLOCK_FORTUNE, 1);
			list.add(istack);
		}
	}
}
