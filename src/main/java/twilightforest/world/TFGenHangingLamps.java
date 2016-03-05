package twilightforest.world;

import java.util.Random;

import twilightforest.block.TFBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;



public class TFGenHangingLamps extends TFGenerator
{
    private static final int MAX_HANG = 8;

	public boolean generate(World par1World, Random par2Random, int x, int y, int z)
    {
        // this must be an air block, surrounded by air
        if (par1World.isAirBlock(x, y, z) && TFGenerator.surroundedByAir(par1World, x, y, z)) {
            // there should be leaves or wood within 12 blocks above
        	if (areLeavesAbove(par1World, x, y, z)) {
        		// we need to be at least 4 above ground
            	if (isClearBelow(par1World, x, y, z)) {
            		// generate lamp
            		par1World.setBlock(x, y, z, TFBlocks.fireflyJar);
            		
            		for (int cy = 1; cy < MAX_HANG; cy++) {
            			Material above = par1World.getBlock(x, y + cy, z).getMaterial();
            			if (above.isSolid() || above == Material.leaves) {
            				break;
            			} else {
                    		par1World.setBlock(x, y + cy, z, Blocks.fence);
            			}
            		}
            	}
        	}

        }

        return false;
    }

	private boolean areLeavesAbove(World par1World, int x, int y, int z) {
		boolean areLeavesAbove = false;
		for (int cy = 1; cy < MAX_HANG; cy++) {
			Material above = par1World.getBlock(x, y + cy, z).getMaterial();
			if (above.isSolid() || above == Material.leaves) {
				areLeavesAbove = true;
			}
		}
		return areLeavesAbove;
	}

	private boolean isClearBelow(World par1World, int x, int y, int z) {
		boolean isClearBelow = true;
		for (int cy = 1; cy < 4; cy++) {
			if (World.doesBlockHaveSolidTopSurface(par1World, x, y - cy, z)) {
				isClearBelow = false;
			}
		}
		return isClearBelow;
	}
}
