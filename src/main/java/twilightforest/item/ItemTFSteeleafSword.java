package twilightforest.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.NonNullList;
import twilightforest.client.ModelRegisterCallback;

public class ItemTFSteeleafSword extends ItemSword implements ModelRegisterCallback {

	public ItemTFSteeleafSword(Item.ToolMaterial material) {
		super(material);
		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (isInCreativeTab(tab)) {
			ItemStack istack = new ItemStack(this);
			istack.addEnchantment(Enchantments.LOOTING, 2);
			list.add(istack);
		}
	}
}
