package twilightforest.world.feature;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

//TODO: It should be noted that there is a RandomPatchFeature class that accepts all BlockStates, since it also includes Pumpkins and Melons. Look into that Feature and make this redunant
// [VanillaCopy] of WorldGenTallGrass, relevant changes noted
public class TFGenTallGrass extends WorldGenerator {

	private final BlockState state;
	private final int amount;

	public TFGenTallGrass(BlockState state) {
		this(state, 128); // Default vanilla amount
	}

	public TFGenTallGrass(BlockState state, int amount) { // TF - Allow any blockstate, allow amount to be changed
		//WorldGenTallGrass use to use canBlockStay of Block, which was delegated to BlockBush.
		//It's sane to make sure that we pass BlockBush in via the constructor.
		if (!(state.getBlock() instanceof BlockBush)) {
			throw new RuntimeException("attempt to use TFGenTallGrass with a blockstate that isn't a BlockBush");
		}
		this.state = state;
		this.amount = amount;
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		do {
			BlockState state = worldIn.getBlockState(position);
			if (!state.getBlock().isAir(state, worldIn, position) && !state.getBlock().isLeaves(state, worldIn, position))
				break;
			position = position.down();
		} while (position.getY() > 0);

		// TF - 128 -> amount
		for (int i = 0; i < amount; ++i) {
			BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

			// TF - use our own state
			if (worldIn.isAirBlock(blockpos) && ((BlockBush) state.getBlock()).canBlockStay(worldIn, blockpos, state)) {
				worldIn.setBlockState(blockpos, state, 16 | 2);
			}
		}

		return true;
	}

}
