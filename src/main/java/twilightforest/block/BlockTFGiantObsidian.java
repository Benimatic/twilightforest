package twilightforest.block;

import twilightforest.item.TFItems;
import net.minecraft.init.Blocks;

public class BlockTFGiantObsidian extends BlockTFGiantBlock  {



	protected BlockTFGiantObsidian() {
		super(Blocks.obsidian);
        this.setHardness(50.0F * 64F * 64F);
        this.setResistance(2000.0F * 64F * 64F);
        
		this.setCreativeTab(TFItems.creativeTab);
	}

}
