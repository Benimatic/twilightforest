package twilightforest.block;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;
import twilightforest.item.TFItems;

import java.util.Locale;

public class BlockTFNagastoneStairs extends StairsBlock {

	public static final EnumProperty<LeftRight> DIRECTION = EnumProperty.create("direction", LeftRight.class);

	BlockTFNagastoneStairs(BlockState state) {
		super(state);
		this.setSoundType(SoundType.STONE);
		this.setCreativeTab(TFItems.creativeTab);
		this.setDefaultState(this.getDefaultState().with(DIRECTION, LeftRight.LEFT));
		this.useNeighborBrightness = true;
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, HALF, SHAPE, DIRECTION);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return super.getMetaFromState(state) + (state.getValue(DIRECTION) == LeftRight.RIGHT ? 8 : 0);
	}

	@Override
	@Deprecated
	public BlockState getStateFromMeta(int meta) {
		return super.getStateFromMeta(meta & 0b0111).with(DIRECTION, (meta & 0b1000) == 8 ? LeftRight.RIGHT : LeftRight.LEFT);
	}

	@Override
	public int damageDropped(BlockState state) {
		return state.getValue(DIRECTION) == LeftRight.RIGHT ? 8 : 0;
	}

	@Override
	public BlockState withMirror(BlockState state, Mirror mirrorIn) {
		return super.withMirror(state, mirrorIn).with(DIRECTION, state.getValue(DIRECTION) == LeftRight.LEFT ? LeftRight.RIGHT : LeftRight.LEFT);
	}

	private enum LeftRight implements IStringSerializable {
		LEFT,
		RIGHT;

		@Override
		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
}
