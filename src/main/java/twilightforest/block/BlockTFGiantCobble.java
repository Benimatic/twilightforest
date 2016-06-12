package twilightforest.block;

import twilightforest.item.TFItems;
import net.minecraft.init.Blocks;

public class BlockTFGiantCobble extends BlockTFGiantBlock  {


	protected BlockTFGiantCobble() {
		super(Blocks.COBBLESTONE);
		this.setHardness(2.0F * 64F);
		this.setResistance(10.0F);
		
		this.setCreativeTab(TFItems.creativeTab);
	}
	
}
