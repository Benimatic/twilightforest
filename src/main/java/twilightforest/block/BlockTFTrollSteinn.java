package twilightforest.block;

import java.util.Random;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTFTrollSteinn extends Block {

	private static final int LIGHT_THRESHHOLD = 7;
	private IIcon blockIconLight;

	protected BlockTFTrollSteinn() {
		super(Material.rock);

        this.setHardness(2F);
        this.setResistance(15F);
        this.setStepSound(Block.soundTypeStone);
		this.setCreativeTab(TFItems.creativeTab);
        this.setBlockTextureName(TwilightForestMod.ID + ":trollsteinn");
	}


    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (rand.nextInt(2) == 0) {
            this.sparkle(world, x, y, z, rand);
        }
    }

    private void sparkle(World world, int x, int y, int z, Random rand) {
        Random random = rand;
        double pixel = 0.0625D;
        int threshhold = LIGHT_THRESHHOLD;

        for (int side = 0; side < 6; ++side)
        {
            double rx = (double)((float)x + random.nextFloat());
            double ry = (double)((float)y + random.nextFloat());
            double rz = (double)((float)z + random.nextFloat());

			if (side == 0 && !world.getBlock(x, y - 1, z).isOpaqueCube() && world.getBlockLightValue(x, y - 1, z) <= threshhold)
            {
                ry = (double)(y + 0) - pixel;
            }

            if (side == 1 && !world.getBlock(x, y + 1, z).isOpaqueCube() && world.getBlockLightValue(x, y + 1, z) <= threshhold)
            {
                ry = (double)(y + 1) + pixel;
            }

            if (side == 2 && !world.getBlock(x, y, z + 1).isOpaqueCube() && world.getBlockLightValue(x, y, z + 1) <= threshhold)
            {
                rz = (double)(z + 1) + pixel;
            }

            if (side == 3 && !world.getBlock(x, y, z - 1).isOpaqueCube() && world.getBlockLightValue(x, y, z - 1) <= threshhold)
            {
                rz = (double)(z + 0) - pixel;
            }

            if (side == 4 && !world.getBlock(x + 1, y, z).isOpaqueCube() && world.getBlockLightValue(x + 1, y, z) <= threshhold)
            {
                rx = (double)(x + 1) + pixel;
            }

            if (side == 5 && !world.getBlock(x - 1, y, z).isOpaqueCube() && world.getBlockLightValue(x - 1, y, z) <= threshhold)
            {
                rx = (double)(x + 0) - pixel;
            }

            if (rx < (double)x || rx > (double)(x + 1) || ry < 0.0D || ry > (double)(y + 1) || rz < (double)z || rz > (double)(z + 1))
            {
                world.spawnParticle("reddust", rx, ry, rz, 0.25D, -1.0D, 0.5D);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        
		// check brightness for the side
		if (side == 0 && isBlockLit(world, x, y - 1, z)) {
            return this.blockIconLight;
        }

        if (side == 1 && isBlockLit(world, x, y + 1, z)) {
            return this.blockIconLight;
        }

        if (side == 2 && isBlockLit(world, x, y, z - 1)) {
            return this.blockIconLight;
        }

        if (side == 3 && isBlockLit(world, x, y, z + 1)) {
            return this.blockIconLight;
        }

        if (side == 4 && isBlockLit(world, x - 1, y, z)) {
            return this.blockIconLight;
        }

        if (side == 5 && isBlockLit(world, x + 1, y, z)) {
            return this.blockIconLight;
        }
    	
        return this.getIcon(side, world.getBlockMetadata(x, y, z));
    }
	
    private boolean isBlockLit(IBlockAccess world, int x, int y, int z) {
        int threshhold = LIGHT_THRESHHOLD << 4;

        if (world.getBlock(x, y, z).isOpaqueCube()) {
    		return false;
        } else {
        	int light = world.getLightBrightnessForSkyBlocks(x, y, z, 0);
            int sky = light % 65536;
            int block = light / 65536;
        	
        	return sky > threshhold || block > threshhold;
        }
	}


	@Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(this.getTextureName());
        this.blockIconLight = par1IconRegister.registerIcon(this.getTextureName() + "_light");
    }
}
