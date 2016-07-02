package twilightforest.block;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.BlockLog;
import net.minecraft.item.Item;
import twilightforest.item.TFItems;

public class BlockTFCinderLog extends BlockLog {

	protected BlockTFCinderLog() {
		this.setHardness(1.0F);
		this.setCreativeTab(TFItems.creativeTab);
	}

    @Override
	public Item getItemDropped(IBlockState state, Random par2Random, int par3)
    {
        return Item.getItemFromBlock(TFBlocks.cinderLog); // hey that's my block ID!
    }


}
