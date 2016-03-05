package twilightforest.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import twilightforest.entity.passive.EntityTFRaven;


/**
 * A 2x2 monolith of obsidian
 * 
 * @author Ben
 *
 */
public class TFGenMonolith extends TFGenerator {


	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		int ht = rand.nextInt(10) + 10;
		int dir = rand.nextInt(4);
		int h0, h1, h2, h3;

		if (!isAreaClear(world, rand, x, y, z, 2, ht, 2))
		{
			return false;
		}
		
		
		switch (dir)
		{
		case 0:
			h0 = ht;
			h1 = (int)(ht * .75);
			h2 = (int)(ht * .75);
			h3 = (int)(ht * .5);
			break;
		case 1:
			h0 = (int)(ht * .5);
			h1 = ht;
			h2 = (int)(ht * .75);
			h3 = (int)(ht * .75);
			break;
		case 2:
			h0 = (int)(ht * .75);
			h1 = (int)(ht * .5);
			h2 = ht;
			h3 = (int)(ht * .75);
			break;
		case 3:
		default:
			h0 = (int)(ht * .75);
			h1 = (int)(ht * .75);
			h2 = (int)(ht * .5);
			h3 = ht;
			break;
		}
		
		
		for (int cy = 0; cy <= h0; cy++)
		{
			setBlock(world, x + 0, y + cy - 1, z + 0, cy == ht ? Blocks.lapis_block : Blocks.obsidian);
		}
		for (int cy = 0; cy <= h1; cy++)
		{
			setBlock(world, x + 1, y + cy - 1, z + 0, cy == ht ? Blocks.lapis_block : Blocks.obsidian);
		}
		for (int cy = 0; cy <= h2; cy++)
		{
			setBlock(world, x + 0, y + cy - 1, z + 1, cy == ht ? Blocks.lapis_block : Blocks.obsidian);
		}
		for (int cy = 0; cy <= h3; cy++)
		{
			setBlock(world, x + 1, y + cy - 1, z + 1, cy == ht ? Blocks.lapis_block : Blocks.obsidian);
		}

		// spawn a few ravens nearby
        for (int i = 0; i < 2; i++)
        {
            int dx = (x + rand.nextInt(8)) - rand.nextInt(8);
            int dz = (z + rand.nextInt(8)) - rand.nextInt(8);
            int dy = world.getTopSolidOrLiquidBlock(dx, dz);
            
            EntityTFRaven raven = new EntityTFRaven(world);
            raven.setLocationAndAngles(dx, dy, dz, rand.nextFloat() * 360.0F, 0.0F);
            
            world.spawnEntityInWorld(raven);
            
        }
		
		return true;
	}
}

