package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import twilightforest.item.TFItems;

public class BlockTFThornRose extends Block {

	protected BlockTFThornRose() {
		super(Material.PLANTS);
		
		this.setHardness(10.0F);
		this.setStepSound(soundTypeGrass);
		this.setCreativeTab(TFItems.creativeTab);
		
        float radius = 0.4F;
        this.setBlockBounds(0.5F - radius, 0.5F - radius, 0.5F - radius, 0.5F + radius, .5F + radius, 0.5F + radius);
	}
	
    @Override
	public int getRenderType()
    {
    	return 1;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        return canBlockStay(world, x, y, z);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos)
    {
        return NULL_AABB;
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block)
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
