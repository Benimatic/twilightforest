package twilightforest.block;

import twilightforest.item.TFItems;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockTFGiantObsidian extends BlockTFGiantBlock  {



	protected BlockTFGiantObsidian() {
		super(Blocks.obsidian);
        this.setHardness(50.0F * 64F * 64F);
        this.setResistance(2000.0F * 64F * 64F);
        
		this.setCreativeTab(TFItems.creativeTab);
	}

    @Override
    public int tickRate(World p_149738_1_)
    {
        return 20;
    }

}
