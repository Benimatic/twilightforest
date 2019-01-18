package twilightforest.block;

import twilightforest.item.TFItems;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockTFGiantLog extends BlockTFGiantBlock  {



	protected BlockTFGiantLog() {
		super(Blocks.log);
        this.setHardness(2.0F * 64F);
        
		this.setCreativeTab(TFItems.creativeTab);
	}

    @Override
    public int tickRate(World p_149738_1_)
    {
        return 20;
    }

}
