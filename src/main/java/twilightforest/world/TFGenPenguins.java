package twilightforest.world;

import java.util.Random;

import net.minecraft.world.World;
import twilightforest.entity.passive.EntityTFPenguin;



public class TFGenPenguins extends TFGenerator {

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {

        for (int i = 0; i < 10; i++)
        {
            int dx = (x + rand.nextInt(8)) - rand.nextInt(8);
            int dz = (z + rand.nextInt(8)) - rand.nextInt(8);
            int dy = world.getTopSolidOrLiquidBlock(dx, dz);
            
            EntityTFPenguin penguin = new EntityTFPenguin(world);
            penguin.setLocationAndAngles(dx, dy, dz, rand.nextFloat() * 360.0F, 0.0F);
            
            world.spawnEntityInWorld(penguin);
            
        }

        return true;

	}

}
