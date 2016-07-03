package twilightforest.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemTFSteeleafSword extends ItemSword {

	public ItemTFSteeleafSword(Item.ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		this.setCreativeTab(TFItems.creativeTab);
	}
	
    @Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
    	ItemStack istack = new ItemStack(par1, 1, 0);
    	istack.addEnchantment(Enchantments.LOOTING, 2);
        par3List.add(istack);
    }
    
    @Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
    	// repair with steeleaf ingots
        return par2ItemStack.getItem() == TFItems.steeleafIngot ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
}
