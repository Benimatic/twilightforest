package twilightforest.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class BlockTFAuroraSlab extends BlockSlab {

	private IIcon sideIcon;

	public BlockTFAuroraSlab(boolean isDouble) {
		super(isDouble, Material.packedIce);
		this.setCreativeTab(TFItems.creativeTab);
		this.setHardness(2.0F);
		this.setResistance(10.0F);
		
        this.setLightOpacity(isDouble ? 255 : 0);

	}

	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	 * when first determining what to render.
	 */
	@Override
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int x, int y, int z) {
		return TFBlocks.auroraPillar.colorMultiplier(par1IBlockAccess, -x, y, -z);
	}
	
	
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return this.colorMultiplier(null, 0, 0, 16);
	}

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return this.getBlockColor();
    }
	
	@Override
	public String func_150002_b(int var1) {
		return super.getUnlocalizedName();
	}

	
	   /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        if (side == 0 || side == 1) {
        	return this.blockIcon;
        } else {
        	return this.sideIcon;
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = TFBlocks.auroraPillar.getIcon(0, 0);
        this.sideIcon = iconRegister.registerIcon(TwilightForestMod.ID + ":aurora_slab_side");
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(TFBlocks.auroraSlab);
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack createStackedBlock(int meta)
    {
        return new ItemStack(Item.getItemFromBlock(TFBlocks.auroraSlab), 2, 0);
    }

    
    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        if (this.field_150004_a)
        {
            return super.shouldSideBeRendered(world, x, y, z, side);
        }
        else if (side != 1 && side != 0 && !super.shouldSideBeRendered(world, x, y, z, side))
        {
            return false;
        }
        else
        {
            int i1 = x + Facing.offsetsXForSide[Facing.oppositeSide[side]];
            int j1 = y + Facing.offsetsYForSide[Facing.oppositeSide[side]];
            int k1 = z + Facing.offsetsZForSide[Facing.oppositeSide[side]];
            boolean flag = (world.getBlockMetadata(i1, j1, k1) & 8) != 0;
            return flag ? (side == 0 ? true : (side == 1 && super.shouldSideBeRendered(world, x, y, z, side) ? true : !isSingleSlab(world.getBlock(x, y, z)) || (world.getBlockMetadata(x, y, z) & 8) == 0)) : (side == 1 ? true : (side == 0 && super.shouldSideBeRendered(world, x, y, z, side) ? true : !isSingleSlab(world.getBlock(x, y, z)) || (world.getBlockMetadata(x, y, z) & 8) != 0));
        }
    }
    
    @SideOnly(Side.CLIENT)
    private static boolean isSingleSlab(Block p_150003_0_)
    {
        return p_150003_0_ == TFBlocks.auroraSlab;
    }
}
