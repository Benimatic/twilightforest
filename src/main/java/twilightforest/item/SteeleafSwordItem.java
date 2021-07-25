package twilightforest.item;

import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.item.*;
import net.minecraft.core.NonNullList;

import net.minecraft.world.item.Item.Properties;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class SteeleafSwordItem extends SwordItem {

	public SteeleafSwordItem(Tier material, Properties props) {
		super(material, 3, -2.4F, props);
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
		if (allowdedIn(tab)) {
			ItemStack istack = new ItemStack(this);
			istack.enchant(Enchantments.MOB_LOOTING, 2);
			list.add(istack);
		}
	}
}
