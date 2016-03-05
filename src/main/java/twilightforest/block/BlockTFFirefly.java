package twilightforest.block;

import java.util.Random;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.tileentity.TileEntityTFFirefly;

public class BlockTFFirefly extends BlockTFCritter {

	
	public static int sprFirefly = 4;
	
	public static Random rand = new Random();
	
	protected BlockTFFirefly() {
		super();
		this.setLightLevel(0.9375F);
	}
    
    /**
     * How often do we check for incorrect lighting on fireflies, etc.
     */
    public int tickRate()
    {
        return 50 + rand.nextInt(50);
    }
    
    /**
     * Get a light value for this block, normal ranges are between 0 and 15
     * 
     * @param world The current world
     * @param x X Position
     * @param y Y position
     * @param z Z position
     * @return The light value
     */
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
    	return 15; 
	}


	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityTFFirefly();
	}
	
	
    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
	@Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
		super.onBlockAdded(world, x, y, z);
//		if (!world.isRemote)
//		{
//			world.scheduleBlockUpdate(x, y, z, blockID, tickRate());
//		}
    }

    /**
     * Ticks the block if it's been scheduled
     * Check the lighting and make the world relight it if it's incorrect.
     */
    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
    	if (!world.isRemote && world.getBlockLightValue(x, y, z) < 12) {
			//world.updateLightByType(EnumSkyBlock.Block, x, y, z);
			world.markBlockForUpdate(x, y, z); // do we need this now?
			//System.out.println("Updating firefly light value");
			// do another update to check that we got it right
			world.scheduleBlockUpdate(x, y, z, this, tickRate());
		}
	}
}
