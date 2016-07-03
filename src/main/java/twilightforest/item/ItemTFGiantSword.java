package twilightforest.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemTFGiantSword extends ItemSword {

	public ItemTFGiantSword(Item.ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		this.setCreativeTab(TFItems.creativeTab);
	}

    @Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
    	// repair with ironwood ingots
        return par2ItemStack.getItem() == TFItems.ironwoodIngot ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
}
