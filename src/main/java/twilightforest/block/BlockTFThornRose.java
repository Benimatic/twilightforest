package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import twilightforest.item.TFItems;

public class BlockTFThornRose extends Block {

	protected BlockTFThornRose() {
		super(Material.plants);
		
		this.setHardness(10.0F);
		this.setStepSound(soundTypeGrass);
		this.setCreativeTab(TFItems.creativeTab);
		
        float radius = 0.4F;
        this.setBlockBounds(0.5F - radius, 0.5F - radius, 0.5F - radius, 0.5F + radius, .5F + radius, 0.5F + radius);
	}
	
    /**
     * The type of render function that is called for this block
     */
    @Override
	public int getRenderType()
    {
    	return 1;
    }
    
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return canBlockStay(world, x, y, z);
    }
    
    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return null;
    }
    
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        if (!canBlockStay(world, x, y, z)) {
        	this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        	world.setBlockToAir(x, y, z);
        }
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
	public boolean canBlockStay(World world, int x, int y, int z) {
		boolean supported = false; 
    	
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
        	int dx = x + dir.offsetX;
        	int dy = y + dir.offsetY;
        	int dz = z + dir.offsetZ;
        	
        	if (world.getBlock(dx, dy, dz).canSustainLeaves(world, dx, dy, dz)) {
        		supported = true;
        	}
        }
		return supported;
	}

}
