package twilightforest.world;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import twilightforest.block.TFBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class TFGenBigMushgloom extends TFGenerator {

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		int height = 3 + rand.nextInt(2) + rand.nextInt(2);
		
		if (!this.isAreaSuitable(world, rand, x - 1, y, z - 1, 3, height, 3)) {
			return false;
		}
		
        Block blockUnder = world.getBlockState(pos.down()).getBlock();
        if (blockUnder != Blocks.DIRT && blockUnder != Blocks.GRASS && blockUnder != Blocks.MYCELIUM) {
            return false;
        }
        
        // generate!
        for (int dy = 0; dy < height - 2; dy++) {
            this.setBlockAndNotifyAdequately(world, x, y + dy, z, TFBlocks.hugeGloomBlock, 10);
        }
         
		makeMushroomCap(world, x, z, y + (height - 2));
		if (rand.nextBoolean()) {
			makeMushroomCap(world, x, z, y + (height - 1));
		}
		
		return true;
	}

	private void makeMushroomCap(World world, int x, int z, int dy) {
		this.setBlockAndNotifyAdequately(world, x - 1, dy, z - 1, TFBlocks.hugeGloomBlock, 1);
        this.setBlockAndNotifyAdequately(world, x + 0, dy, z - 1, TFBlocks.hugeGloomBlock, 2);
        this.setBlockAndNotifyAdequately(world, x + 1, dy, z - 1, TFBlocks.hugeGloomBlock, 3);
        this.setBlockAndNotifyAdequately(world, x - 1, dy, z + 0, TFBlocks.hugeGloomBlock, 4);
        this.setBlockAndNotifyAdequately(world, x + 0, dy, z + 0, TFBlocks.hugeGloomBlock, 5);
        this.setBlockAndNotifyAdequately(world, x + 1, dy, z + 0, TFBlocks.hugeGloomBlock, 6);
        this.setBlockAndNotifyAdequately(world, x - 1, dy, z + 1, TFBlocks.hugeGloomBlock, 7);
        this.setBlockAndNotifyAdequately(world, x + 0, dy, z + 1, TFBlocks.hugeGloomBlock, 8);
        this.setBlockAndNotifyAdequately(world, x + 1, dy, z + 1, TFBlocks.hugeGloomBlock, 9);
	}

}
