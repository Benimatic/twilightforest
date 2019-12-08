package twilightforest.block;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.item.TFItems;

import java.util.List;

/**
 * Castle block makes a castle
 *
 * @author Ben
 */
public class BlockTFCastleMagic extends Block implements ModelRegisterCallback {

	public static final List<DyeColor> VALID_COLORS = ImmutableList.of(DyeColor.PINK, DyeColor.BLUE, DyeColor.YELLOW, DyeColor.PURPLE);
	public static final IProperty<DyeColor> COLOR = PropertyEnum.create("color", DyeColor.class, VALID_COLORS);

	public BlockTFCastleMagic() {
		super(Material.ROCK, MaterialColor.QUARTZ);
		this.setHardness(100F);
		this.setResistance(15F);
		this.setSoundType(SoundType.STONE);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(blockState.getBaseState().withProperty(COLOR, DyeColor.PINK));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, COLOR);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return VALID_COLORS.indexOf(state.getValue(COLOR));
	}

	@Override
	@Deprecated
	public BlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(COLOR, VALID_COLORS.get(meta));
	}

	@Override
	public void getSubBlocks(CreativeTabs creativeTab, NonNullList<ItemStack> list) {
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 1));
		list.add(new ItemStack(this, 1, 2));
		list.add(new ItemStack(this, 1, 3));
	}

	@Override
	public int damageDropped(BlockState state) {
		return getMetaFromState(state);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerModel() {
		ModelUtils.registerToStateSingleVariant(this, COLOR);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
}
