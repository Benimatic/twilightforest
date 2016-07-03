package twilightforest.world;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

// [VanillaCopy] of WorldGenTallGrass, relevant changes noted
public class TFGenTallGrass extends WorldGenerator {

    private final IBlockState state;
	private final int amount;

	public TFGenTallGrass(IBlockState state, int amount) { // TF - Allow any blockstate, allow amount to be changed
		this.state = state;
        this.amount = amount;
	}

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        do
        {
            IBlockState state = worldIn.getBlockState(position);
            if (!state.getBlock().isAir(state, worldIn, position) && !state.getBlock().isLeaves(state, worldIn, position)) break;
            position = position.down();
        } while (position.getY() > 0);

        for (int i = 0; i < amount; ++i) // TF - 128 -> amount
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            // TF - use our own state
            if (worldIn.isAirBlock(blockpos) && state.getBlock().canBlockStay(worldIn, blockpos, state))
            {
                worldIn.setBlockState(blockpos, state, 2);
            }
        }

        return true;
    }

}
