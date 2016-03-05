package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTFHugeStalk extends Block {

	private IIcon topIcon;

	protected BlockTFHugeStalk() {
		super(Material.wood);
		
		this.setHardness(1.25F);
		this.setResistance(7.0F);
		
        this.setBlockTextureName(TwilightForestMod.ID + ":huge_stalk");
		
		this.setStepSound(soundTypeGrass);
		this.setCreativeTab(TFItems.creativeTab);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		super.registerBlockIcons(par1IconRegister);

		this.topIcon = par1IconRegister.registerIcon(this.getTextureName() + "_top");

	}
	
    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
    	if (side == 0 || side == 1) {
    		return this.topIcon;
    	} else {
    		return this.blockIcon;
    	}
    }

    /**
     * Determines if this block can prevent leaves connected to it from decaying.
     *
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @return true if the presence this block can prevent leaves from decaying.
     */
	@Override
	public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
		return true;
	}
	
	/**
	 * Begin leaf decay when this block is broken
	 */
    public void breakBlock(World world, int x, int y, int z, Block myBlock, int meta) {
        byte radius = 4;
        int rad1 = radius + 1;

        if (world.checkChunksExist(x - rad1, y - rad1, z - rad1, x + rad1, y + rad1, z + rad1)) {
            for (int dx = -radius; dx <= radius; ++dx) {
                for (int dy = -radius; dy <= radius; ++dy) {
                    for (int dz = -radius; dz <= radius; ++dz) {
                        Block block = world.getBlock(x + dx, y + dy, z + dz);
                        if (block.isLeaves(world, x + dx, y + dy, z + dz)) {
                            block.beginLeavesDecay(world, x + dx, y + dy, z + dz);
                        }
                    }
                }
            }
        }
    }
	
}
