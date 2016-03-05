package twilightforest.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import twilightforest.item.TFItems;
import net.minecraft.init.Blocks;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;

public class BlockTFGiantLeaves extends BlockTFGiantBlock {

	public BlockTFGiantLeaves() {
		super(Blocks.leaves);
		
        this.setHardness(0.2F * 64F);
        this.setLightOpacity(1);
        
		this.setCreativeTab(TFItems.creativeTab);
	}

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }


    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        double d0 = 0.5D;
        double d1 = 1.0D;
        return ColorizerFoliage.getFoliageColor(d0, d1);
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int p_149741_1_)
    {
        return ColorizerFoliage.getFoliageColorBasic();
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z)
    {
        int red = 0;
        int grn = 0;
        int blu = 0;

        for (int dz = -1; dz <= 1; ++dz)
        {
            for (int dx = -1; dx <= 1; ++dx)
            {
                int nearbyColor = world.getBiomeGenForCoords(x + dx, z + dz).getBiomeFoliageColor(x + dx, y, z + dz);
                red += (nearbyColor & 16711680) >> 16;
                grn += (nearbyColor & 65280) >> 8;
                blu += nearbyColor & 255;
            }
        }

        return (red / 9 & 255) << 16 | (grn / 9 & 255) << 8 | blu / 9 & 255;
    }


    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        switch (side) {
        case 0:
        	return (y & 3) == 3; 
        case 1:
        	return (y & 3) == 0; 
        case 2:
        	return (z & 3) == 3; 
        case 3:
        	return (z & 3) == 0; 
        case 4:
        	return (x & 3) == 3; 
        case 5:
        	return (x & 3) == 0; 
        }
        
        return super.shouldSideBeRendered(world, x, y, z, side);
    }
}
