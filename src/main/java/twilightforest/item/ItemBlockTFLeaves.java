package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockTFLeaves extends ItemBlock {

	public ItemBlockTFLeaves(Block par1) {
		super(par1);
        setHasSubtypes(true);
        setMaxDamage(0);
	}
	
    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
    	int meta = itemstack.getItemDamage();
    	return (new StringBuilder()).append(super.getUnlocalizedName()).append(".").append(meta).toString();
    }

    @Override
    public int getMetadata(int i)
    {
        return i;
    }
}
