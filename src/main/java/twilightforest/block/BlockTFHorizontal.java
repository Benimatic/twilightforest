package twilightforest.block;

import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;

import javax.annotation.Nullable;

public class BlockTFHorizontal extends HorizontalBlock {

    protected BlockTFHorizontal(Properties props) {
        super(props);
    }

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
}
