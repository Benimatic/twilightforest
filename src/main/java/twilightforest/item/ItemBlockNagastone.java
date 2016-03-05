package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import twilightforest.block.TFBlocks;

public class ItemBlockNagastone extends ItemBlock {

	public ItemBlockNagastone(Block block) {
		super(block);
        setHasSubtypes(true);
        setMaxDamage(0);
	}
	
    @Override
    public IIcon getIconFromDamage(int i)
    {
        int j = MathHelper.clamp_int(i, 0, 15);
        return TFBlocks.nagastone.getIcon(2, j);

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
