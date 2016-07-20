package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockNagastone extends ItemBlock {

	public ItemBlockNagastone(Block block) {
		super(block);
        setHasSubtypes(true);
        setMaxDamage(0);
	}
	
    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return super.getUnlocalizedName(itemstack) + "." + itemstack.getItemDamage();
    }

    @Override
    public int getMetadata(int i)
    {
        return i;
    }
}
