package twilightforest.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;


/**
 * Makes a Stalagmite suitable for outside appearances.
 * 
 * @author Ben
 *
 */
public class TFGenOutsideStalagmite extends TFGenCaveStalactite {

	public TFGenOutsideStalagmite() {
		super(Blocks.stone, 1.0F, false);
	}
	
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		int length = rand.nextInt(10) + 5;
		
		if (!isAreaSuitable(world, rand, x, y, z, 1, length, 1))
		{
			return false;
		}
		
		// I think we already have code for this! :D
		return makeSpike(world, rand, x, y - 1, z, length);
	}

}
