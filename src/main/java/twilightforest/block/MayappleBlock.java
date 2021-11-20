package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MayappleBlock extends TFPlantBlock {

	private static final VoxelShape MAYAPPLE_SHAPE = box(4, 0, 4, 13, 6, 13);

	public MayappleBlock(Properties props) {
		super(props);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter access, BlockPos pos, CollisionContext context) {
		return MAYAPPLE_SHAPE;
	}
}
