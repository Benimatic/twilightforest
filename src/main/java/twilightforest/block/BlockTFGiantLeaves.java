package twilightforest.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.item.TFItems;
import net.minecraft.init.Blocks;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;

public class BlockTFGiantLeaves extends BlockTFGiantBlock {

	public BlockTFGiantLeaves() {
		super(Blocks.LEAVES.getDefaultState());
        this.setHardness(0.2F * 64F);
        this.setLightOpacity(1);
		this.setCreativeTab(TFItems.creativeTab);
	}

    @Override
    public boolean isOpaqueCube(IBlockState state)
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

    @SideOnly(Side.CLIENT)
    public int getRenderColor(int p_149741_1_)
    {
        return ColorizerFoliage.getFoliageColorBasic();
    }

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

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
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
        
        return super.shouldSideBeRendered(state, world, pos, side);
    }
}
