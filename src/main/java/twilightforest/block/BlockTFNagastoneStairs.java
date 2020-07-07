package twilightforest.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public class BlockTFNagastoneStairs extends StairsBlock {

	//TODO: Verify on testing
	//public static final EnumProperty<LeftRight> DIRECTION = EnumProperty.create("direction", LeftRight.class);

	BlockTFNagastoneStairs(BlockState state) {
		super(() -> state, Properties.create(state.getMaterial()).hardnessAndResistance(1.5F, 10.0F).sound(SoundType.STONE));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
		//this.setDefaultState(this.getDefaultState().with(DIRECTION, LeftRight.LEFT));
	}

//	@Override
//	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
//		builder.add(DIRECTION);
//	}

//	@Override
//	public int damageDropped(BlockState state) {
//		return state.getValue(DIRECTION) == LeftRight.RIGHT ? 8 : 0;
//	}

//	@Override
//	@Deprecated
//	public BlockState mirror(BlockState state, Mirror mirrorIn) {
//		return super.mirror(state, mirrorIn).with(DIRECTION, state.get(DIRECTION) == LeftRight.LEFT ? LeftRight.RIGHT : LeftRight.LEFT);
//	}

	private enum LeftRight implements IStringSerializable {
		LEFT,
		RIGHT;

		@Override
		public String func_176610_l() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
}
