package twilightforest.block;

import java.util.List;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.item.ItemTFMazebreakerPick;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



/**
 * 
 * Castle block makes a castle
 * 
 * @author Ben
 *
 */
public class BlockTFCastleMagic extends Block {
	
	public static IIcon[] magicIcons = new IIcon[8];
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

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		for (int i = 0; i < 8; i++) {
			this.magicIcons[i] = par1IconRegister.registerIcon(TwilightForestMod.ID + ":castleblock_magic_" + i);
		}
	}
	
	public static IIcon getMagicIconFor(int x, int y, int z) {
		
        long seed = x * 3129871 ^ y * 116129781L ^ z;
        seed = seed * seed * 42317861L + seed * 11L;
        
        int index = (int) (seed >> 12 & 7L);
		
		return magicIcons[index];
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
