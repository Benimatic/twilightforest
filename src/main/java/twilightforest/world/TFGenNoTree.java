package twilightforest.world;

import java.util.Random;

import net.minecraft.world.World;

/**
 * Used to generate no particular tree
 */
public class TFGenNoTree extends TFTreeGenerator {

	public TFGenNoTree() {
	}

	public TFGenNoTree(boolean par1) {
		super(par1);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		return false;
	}

}
