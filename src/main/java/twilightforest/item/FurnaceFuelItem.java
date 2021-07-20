package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class FurnaceFuelItem extends BlockItem {
    private final int burntime;

    public FurnaceFuelItem(Block block, Properties properties, int burn) {
        super(block, properties);
        this.burntime = burn;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return burntime;
    }
}
