package twilightforest.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;

public class BlockTFNagastoneHead extends HorizontalBlock {

	public BlockTFNagastoneHead(Properties props) {
		super(props);
		this.setDefaultState(getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> container) {
		super.fillStateContainer(container);
		container.add(HORIZONTAL_FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		return getDefaultState().with(HORIZONTAL_FACING, ctx.getPlacementHorizontalFacing().getOpposite());
	}
}
