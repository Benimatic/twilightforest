package twilightforest.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import twilightforest.block.TFBlocks;

public class CreativeTabTwilightForest extends CreativeTabs 
{

	public CreativeTabTwilightForest(String label) {
		super(label);
	}
	
    /**
     * Get the ItemStack that will be rendered to the tab.
     */
	@Override
    public Item getTabIconItem()
    {
        return Item.getItemFromBlock(TFBlocks.fireflyJar);
    }
}
