package twilightforest.block;

import java.util.Locale;
import java.util.Random;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import twilightforest.item.TFItems;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockTFAuroraSlab extends BlockSlab {

    private static final PropertyEnum<Dummy> DUMMY_PROP = PropertyEnum.create("dummy", Dummy.class);

    private final boolean isDouble;

	public BlockTFAuroraSlab(boolean isDouble) {
		super(Material.PACKED_ICE);
        this.isDouble = isDouble;
		this.setCreativeTab(TFItems.creativeTab);
		this.setHardness(2.0F);
		this.setResistance(10.0F);
		
        this.setLightOpacity(isDouble ? 255 : 0);

        this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, EnumBlockHalf.BOTTOM));
	}

	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, HALF);
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

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(TFBlocks.auroraSlab);
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(TFBlocks.auroraSlab), 2, 0);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
	    return this.getDefaultState().withProperty(HALF, EnumBlockHalf.values()[meta % EnumBlockHalf.values().length]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
	    return state.getValue(HALF).ordinal();
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
