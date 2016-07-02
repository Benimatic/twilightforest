package twilightforest.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTF extends Item {
	
	private boolean isRare = false;

	protected ItemTF() {
		this.setCreativeTab(TFItems.creativeTab);
	}
	
    @Override
    @SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
    	return isRare ? EnumRarity.RARE : EnumRarity.UNCOMMON;
	}

	public ItemTF makeRare()
	{
		this.isRare = true;
		return this;
	}
}
