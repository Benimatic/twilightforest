package twilightforest.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantments;

public class SteeleafSwordItem extends SwordItem {

	public SteeleafSwordItem(Tier material, Properties props) {
		super(material, 3, -2.4F, props);
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
		if (this.allowedIn(tab)) {
			ItemStack istack = new ItemStack(this);
			istack.enchant(Enchantments.MOB_LOOTING, 2);
			list.add(istack);
		}
	}
}
