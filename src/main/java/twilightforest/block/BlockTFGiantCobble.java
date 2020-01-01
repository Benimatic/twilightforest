package twilightforest.block;

import net.minecraft.block.Blocks;

public class BlockTFGiantCobble extends BlockTFGiantBlock {

	protected BlockTFGiantCobble() {
		super(Blocks.COBBLESTONE.getDefaultState(), 2.0F * 64F, 10.0F);
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}
}
