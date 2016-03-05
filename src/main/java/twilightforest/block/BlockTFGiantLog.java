package twilightforest.block;

import twilightforest.item.TFItems;
import net.minecraft.init.Blocks;

public class BlockTFGiantLog extends BlockTFGiantBlock  {



	protected BlockTFGiantLog() {
		super(Blocks.log);
        this.setHardness(2.0F * 64F);
        
		this.setCreativeTab(TFItems.creativeTab);
	}

}
