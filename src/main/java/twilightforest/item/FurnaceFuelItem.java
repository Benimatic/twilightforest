package twilightforest.item;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Item.Properties;

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
