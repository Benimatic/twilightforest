package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.enums.DeadrockVariant;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.item.TFItems;

public class BlockTFDeadrock extends Block {

	//TODO 1.14: Flatten
	public static final IProperty<DeadrockVariant> VARIANT = PropertyEnum.create("variant", DeadrockVariant.class);

	protected BlockTFDeadrock() {
		super(Properties.create(Material.ROCK).hardnessAndResistance(100.0F, 6000000.0F).sound(SoundType.STONE));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
		this.setDefaultState(blockState.getBaseState().with(VARIANT, DeadrockVariant.SURFACE));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, VARIANT);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return state.getValue(VARIANT).ordinal();
	}

	@Override
	@Deprecated
	public BlockState getStateFromMeta(int meta) {
		return getDefaultState().with(VARIANT, DeadrockVariant.values()[meta]);
	}

	@Override
	public int damageDropped(BlockState state) {
		return getMetaFromState(state);
	}
}
