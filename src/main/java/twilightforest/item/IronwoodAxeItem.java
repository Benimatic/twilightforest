package twilightforest.item;

import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.item.*;
import net.minecraft.core.NonNullList;

import net.minecraft.world.item.Item.Properties;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class IronwoodAxeItem extends AxeItem {

	protected IronwoodAxeItem(Tier material, Properties props) {
		super(material, 6F, material.getSpeed() * 0.05f - 3.4f, props);
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
		if (allowdedIn(tab)) {
			ItemStack istack = new ItemStack(this);
			istack.enchant(Enchantments.BLOCK_FORTUNE, 1);
			list.add(istack);
		}
	}
}
