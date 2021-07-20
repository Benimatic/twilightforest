package twilightforest.item;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;

public class IronwoodSwordItem extends SwordItem {

	public IronwoodSwordItem(IItemTier material, Properties props) {
		super(material, 3, -2.4F, props);
	}

	@Override
	public void fillItemGroup(ItemGroup tab, NonNullList<ItemStack> list) {
		if (isInGroup(tab)) {
			ItemStack istack = new ItemStack(this);
			istack.addEnchantment(Enchantments.KNOCKBACK, 1);
			list.add(istack);
		}
	}
}
