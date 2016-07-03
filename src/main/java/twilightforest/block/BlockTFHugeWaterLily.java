package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import twilightforest.item.TFItems;

public class BlockTFHugeWaterLily extends BlockBush {

	protected BlockTFHugeWaterLily() {
		super(Material.PLANTS);
		
		this.setStepSound(soundTypeGrass);
		this.setCreativeTab(TFItems.creativeTab);
		
        float radius = 0.4F;
        this.setBlockBounds(0.5F - radius, 0.5F - radius, 0.5F - radius, 0.5F + radius, .5F + radius, 0.5F + radius);
	}
	
    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
	public boolean canBlockStay(World world, int x, int y, int z) {
		return world.getBlock(x, y - 1, z).getMaterial() == Material.WATER && world.getBlockMetadata(x, y - 1, z) == 0;
	}


    /**
     * is the block grass, dirt or farmland
     */
    protected boolean canPlaceBlockOn(Block p_149854_1_)
    {
        return p_149854_1_ == Blocks.WATER;
    }
}
