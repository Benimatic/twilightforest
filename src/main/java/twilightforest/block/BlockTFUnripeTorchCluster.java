package twilightforest.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;

public class BlockTFUnripeTorchCluster extends BlockTFTrollRoot {
	
	private static final int RIPEN_THRESHHOLD = 6;


	protected BlockTFUnripeTorchCluster() {
		super();

        this.setBlockTextureName(TwilightForestMod.ID + ":unripe_torch_cluster");

	}

	
    /**
     * Ticks the block if it's been scheduled
     */
	@Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);

        if (world.getBlockLightValue(x, y, z) >= RIPEN_THRESHHOLD) {
        	// ripen!
        	world.setBlock(x, y, z, TFBlocks.trollBer);
        }
    }
}
