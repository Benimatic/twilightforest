package twilightforest.block;

import net.minecraft.block.Blocks;

public class BlockTFGiantObsidian extends BlockTFGiantBlock {

	protected BlockTFGiantObsidian() {
		super(Blocks.OBSIDIAN.getDefaultState(), 50.0F * 64F * 64F, 2000.0F * 64F * 64F);
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}
}
