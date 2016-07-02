package twilightforest.block;

import java.util.Locale;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BlockTFAuroraSlab extends BlockSlab {

    private static final PropertyEnum<Dummy> DUMMY_PROP = PropertyEnum.create("dummy", Dummy.class);

	private IIcon sideIcon;
    private final boolean isDouble;

	public BlockTFAuroraSlab(boolean isDouble) {
		super(Material.PACKED_ICE);
        this.isDouble = isDouble;
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

	@Override
	public String getUnlocalizedName(int meta) {
		return super.getUnlocalizedName();
	}

    @Override
    public boolean isDouble() {
        return isDouble;
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return DUMMY_PROP;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return Dummy.SINGLETON;
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

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(TFBlocks.auroraSlab);
    }

    @Override
    protected ItemStack createStackedBlock(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(TFBlocks.auroraSlab), 2, 0);
    }
    
    @SideOnly(Side.CLIENT)
    private static boolean isSingleSlab(Block p_150003_0_)
    {
        return p_150003_0_ == TFBlocks.auroraSlab;
    }

    private enum Dummy implements IStringSerializable {
        SINGLETON {
            @Override
            public String getName() {
                return name().toLowerCase(Locale.ROOT);
            }
        }
    }

}
