package twilightforest.block;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.tileentity.TileEntityTFFirefly;

public class BlockTFFirefly extends BlockTFCritter {

	public static final Random rand = new Random();
	
	protected BlockTFFirefly() {
		this.setLightLevel(0.9375F);
	}

	@Override
    public int tickRate(World world)
    {
        return 50 + rand.nextInt(50);
    }
    
	@Override
	public int getLightValue(IBlockState state) {
    	return 15; 
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityTFFirefly();
	}
}
