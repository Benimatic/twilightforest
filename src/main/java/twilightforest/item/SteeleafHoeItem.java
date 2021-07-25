package twilightforest.item;

import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;

import net.minecraft.world.item.Item.Properties;

public class SteeleafHoeItem extends HoeItem {

	public SteeleafHoeItem(Tier material, Properties props) {
		super(material, -3, -0.5F, props);
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
		if (allowdedIn(tab)) {
			ItemStack istack = new ItemStack(this);
			istack.enchant(Enchantments.BLOCK_EFFICIENCY, 1);
			list.add(istack);
		}
	}
}
