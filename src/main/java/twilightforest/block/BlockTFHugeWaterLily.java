package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import twilightforest.item.TFItems;

public class BlockTFHugeWaterLily extends BlockBush {

	protected BlockTFHugeWaterLily() {
		super(Material.plants);
		
		this.setStepSound(soundTypeGrass);
		this.setCreativeTab(TFItems.creativeTab);
		
        float radius = 0.4F;
        this.setBlockBounds(0.5F - radius, 0.5F - radius, 0.5F - radius, 0.5F + radius, .5F + radius, 0.5F + radius);
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
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
	public boolean canBlockStay(World world, int x, int y, int z) {
		return world.getBlock(x, y - 1, z).getMaterial() == Material.water && world.getBlockMetadata(x, y - 1, z) == 0;
	}


    /**
     * is the block grass, dirt or farmland
     */
    protected boolean canPlaceBlockOn(Block p_149854_1_)
    {
        return p_149854_1_ == Blocks.WATER;
    }
}
