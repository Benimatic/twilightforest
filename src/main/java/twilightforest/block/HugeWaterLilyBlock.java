package twilightforest.block;

import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;

public class HugeWaterLilyBlock extends WaterlilyBlock {

	private static final VoxelShape AABB = box(1.6, 1.6, 1.6, 14.4, 14.4, 14.4);

	protected HugeWaterLilyBlock(Properties props) {
		super(props);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return AABB;
	}
}
