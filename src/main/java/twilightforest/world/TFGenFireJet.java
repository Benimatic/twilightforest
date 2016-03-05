package twilightforest.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class TFGenFireJet extends TFGenerator
{
    private Block plantBlockId;
    private int plantBlockMeta;

    public TFGenFireJet(Block fireJet, int meta)
    {
        this.plantBlockId = fireJet;
        this.plantBlockMeta = meta;
    }

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
        for (int var6 = 0; var6 < 4; ++var6)
        {
            int dx = x + rand.nextInt(8) - rand.nextInt(8);
            int dy = y + rand.nextInt(4) - rand.nextInt(4);
            int dz = z + rand.nextInt(8) - rand.nextInt(8);

            if (world.isAirBlock(dx, dy, dz) && world.canBlockSeeTheSky(dx, dy, dz) && world.getBlock(dx, dy - 1, dz).getMaterial() == Material.grass
            		 && world.getBlock(dx + 1, dy - 1, dz).getMaterial() == Material.grass && world.getBlock(dx - 1, dy - 1, dz).getMaterial() == Material.grass
            		 && world.getBlock(dx, dy - 1, dz + 1).getMaterial() == Material.grass && world.getBlock(dx, dy - 1, dz - 1).getMaterial() == Material.grass)
            {
            	// jet
            	world.setBlock(dx, dy - 1, dz, this.plantBlockId, this.plantBlockMeta, 0);

            	// create reservoir with stone walls
            	for (int rx = -2; rx <= 2; rx++)
            	{
                	for (int rz = -2; rz <= 2; rz++)
                	{
                		if ((rx == 1 || rx == 0 || rx == -1) && (rz == 1 || rz == 0 || rz == -1))
                		{
                        	// lava reservoir
                    		world.setBlock(dx + rx, dy - 2, dz + rz, Blocks.flowing_lava, 0, 0);
                		}
                		else if (world.getBlock(dx + rx, dy - 2, dz + rz).getMaterial() != Material.lava)
                		{
                			// only stone where there is no lava
                    		world.setBlock(dx + rx, dy - 2, dz + rz, Blocks.stone, 0, 0);
                		}
                		world.setBlock(dx + rx, dy - 3, dz + rz, Blocks.stone, 0, 0);
                	}
            	}
            }
        }

        return true;

	}
}
