package twilightforest.block;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.tileentity.TileEntityTFMoonworm;

public class BlockTFMoonworm extends BlockTFCritter {

	@Override
	public float getWidth() {
		return 0.25F;
	}

	@Override
    public int tickRate(World world)
    {
        return 50;
    }

	@Override
	public int getLightValue(IBlockState state) {
    	return 14; 
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityTFMoonworm();
	}

	@Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
		super.onBlockAdded(world, pos, state);
		world.scheduleUpdate(pos, this, tickRate(world));
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
    {
    	if (world.getLight(pos) < 12) {
			//world.updateLightByType(EnumSkyBlock.Block, x, y, z);
			//world.markBlockForUpdate(x, y, z); // do we need this now?
			//System.out.println("Updating moonworm light value");
			// do another update to check that we got it right
			world.scheduleUpdate(pos, this, tickRate(world));
		}
	}

    @Override
	public boolean dropCritterIfCantStay(World world, BlockPos pos)
    {
        if(!canPlaceBlockAt(world, pos))
        {
            world.destroyBlock(pos, false);
			return false;
        } else
        {
            return true;
        }
    }

    @Override
	public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        return 0;
    }
}
