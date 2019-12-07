package twilightforest.block;

import net.minecraft.block.Blocks;
import twilightforest.item.TFItems;

public class BlockTFGiantLog extends BlockTFGiantBlock {

	protected BlockTFGiantLog() {
		super(Blocks.LOG.getDefaultState());
		this.setHardness(2.0F * 64F);
		this.setCreativeTab(TFItems.creativeTab);
	}

}
