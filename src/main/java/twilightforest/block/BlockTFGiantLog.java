package twilightforest.block;

import net.minecraft.block.Blocks;

public class BlockTFGiantLog extends BlockTFGiantBlock {

	protected BlockTFGiantLog() {
		super(Blocks.OAK_LOG.getDefaultState(), 2.0F * 64F, 0.0F);
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

}
