package twilightforest.block;

import java.util.Random;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.tileentity.TileEntityTFMoonworm;

public class BlockTFMoonworm extends BlockTFCritter {

	
	public static int sprMoonworm = 52;
	
	protected BlockTFMoonworm() {
		super();
	}
    
    /**
     * How often do we check for incorrect lighting on fireflies, etc.
     */
    public int tickRate()
    {
        return 50;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) 
    {
    	int facing = world.getBlockMetadata(x, y, z) & 7;
    	float wide = 0.25F;
    	if (facing == 1)
    	{
    		setBlockBounds(0.0F, 0.25F, 0.5F - wide, wide, 0.75F, 0.5F + wide);
    	} 
    	else if(facing == 2)
    	{
    		setBlockBounds(1.0F - wide, 0.25F, 0.5F - wide, 1.0F, 0.75F, 0.5F + wide);
    	} 
    	else if(facing == 3)
    	{
    		setBlockBounds(0.5F - wide, 0.25F, 0.0F, 0.5F + wide, 0.75F, wide);
    	} 
    	else if(facing == 4)
    	{
    		setBlockBounds(0.5F - wide, 0.25F, 1.0F - wide, 0.5F + wide, 0.75F, 1.0F);
    	} 
    	else if(facing == 5)
    	{
    		setBlockBounds(0.5F - wide, 0.0F, 0.25F, 0.5F + wide, wide, 0.75F);
    	} 
    	else if(facing == 6)
    	{
    		setBlockBounds(0.5F - wide, 1.0F - wide, 0.25F, 0.5F + wide, 1.0F, 0.75F);
    	} 
    	else
    	{
    		float f1 = 0.1F;
    		setBlockBounds(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, 0.6F, 0.5F + f1);
    	}   
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
    	return 14; 
	}


	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityTFMoonworm();
		
	}
	
	
    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
	@Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
		super.onBlockAdded(world, x, y, z);
		world.scheduleBlockUpdate(x, y, z, this, tickRate());
    }

    /**
     * Ticks the block if it's been scheduled
     * Check the lighting and make the world relight it if it's incorrect.
     */
    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
    	if (world.getBlockLightValue(x, y, z) < 12) {
			//world.updateLightByType(EnumSkyBlock.Block, x, y, z);
			//world.markBlockForUpdate(x, y, z); // do we need this now?
			//System.out.println("Updating moonworm light value");
			// do another update to check that we got it right
			world.scheduleBlockUpdate(x, y, z, this, tickRate());
		}
	}
    
    /**
     * Don't drop anything
     */
    @Override
	public boolean dropCritterIfCantStay(World world, int x, int y, int z)
    {
        if(!canPlaceBlockAt(world, x, y, z))
        {
            world.setBlockToAir(x, y, z);
            return false;
        } else
        {
            return true;
        }
    }
    
    /**
     * Metadata and fortune sensitive version, this replaces the old (int meta, Random rand)
     * version in 1.1. 
     * 
     * @param meta Blocks Metadata
     * @param fortune Current item fortune level
     * @param random Random number generator
     * @return The number of items to drop
     */
    @Override
	public int quantityDropped(int meta, int fortune, Random random)
    {
        return 0;
    }

    
}
