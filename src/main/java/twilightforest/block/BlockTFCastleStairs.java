package twilightforest.block;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.enums.CastlePillarVariant;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.item.TFItems;

public class BlockTFCastleStairs extends BlockStairs implements ModelRegisterCallback {

	public static final IProperty<CastlePillarVariant> VARIANT = PropertyEnum.create("variant", CastlePillarVariant.class);

	BlockTFCastleStairs(BlockState state) {
		super(state);
		this.setHardness(100F);
		this.setResistance(35F);
		this.setSoundType(SoundType.STONE);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(getDefaultState().withProperty(VARIANT, CastlePillarVariant.ENCASED));
		this.useNeighborBrightness = true;
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, HALF, SHAPE, VARIANT);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return super.getMetaFromState(state) + (state.getValue(VARIANT) == CastlePillarVariant.BOLD ? 8 : 0);
	}

	@Override
	@Deprecated
	public BlockState getStateFromMeta(int meta) {
		return super.getStateFromMeta(meta & 0b0111).withProperty(VARIANT, (meta & 0b1000) == 8 ? CastlePillarVariant.BOLD : CastlePillarVariant.ENCASED);
	}

	@Override
	public void getSubBlocks(CreativeTabs creativeTab, NonNullList<ItemStack> list) {
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 8));
	}

	@Override
	public int damageDropped(BlockState state) {
		return state.getValue(VARIANT) == CastlePillarVariant.BOLD ? 8 : 0;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void registerModel() {
		ModelUtils.registerToState(this, 0, getDefaultState().withProperty(FACING, Direction.EAST));
		ModelUtils.registerToState(this, 8, getDefaultState().withProperty(FACING, Direction.EAST).withProperty(VARIANT, CastlePillarVariant.BOLD));
	}

}
