package twilightforest.block;

import java.util.List;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import twilightforest.item.TFItems;

/**
 * 
 * Castle block makes a castle
 * 
 * @author Ben
 *
 */
public class BlockTFCastleMagic extends Block {
	public static final int[] magicColors = new int[] { 0x00FFFF, 0xFFFF00, 0xFF00FF, 0x4B0082 };

	private static final List<EnumDyeColor> VALID_COLORS = ImmutableList.of(EnumDyeColor.PINK, EnumDyeColor.BLUE, EnumDyeColor.YELLOW, EnumDyeColor.PURPLE);
	public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class, VALID_COLORS);

    public BlockTFCastleMagic()
    {
        super(Material.ROCK);
        this.setHardness(100F);
        this.setResistance(15F);
        this.setSoundType(SoundType.STONE);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState().withProperty(COLOR, EnumDyeColor.PINK));
    }

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, COLOR);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return VALID_COLORS.indexOf(state.getValue(COLOR));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(COLOR, VALID_COLORS.get(meta));
	}

	public static int getMagicColorFor(int meta) {
		int color =  magicColors[meta & 3];
		
		if ((meta & 8) != 0) {
			color = 0xFFFFFF ^ color;
		}
		
		return color;
	}

	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
    }
    
    @Override
	public int damageDropped(IBlockState state) {
    	return getMetaFromState(state);
	}

}
