package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockTFDirectionalRotatedPillar extends RotatedPillarBlock {

	public static final BooleanProperty REVERSED = BooleanProperty.create("reversed");

	public BlockTFDirectionalRotatedPillar(Material materialIn) {
		super(materialIn);
	}

	public BlockTFDirectionalRotatedPillar(Material materialIn, MaterialColor color) {
		super(materialIn, color);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(REVERSED);
	}

	@Override
	public BlockState getStateForPlacement(World worldIn, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer)
		            .with(REVERSED, facing.getAxisDirection() == Direction.AxisDirection.NEGATIVE);
	}

	@Override
	@Deprecated
	public BlockState mirror(BlockState state, Mirror mirror) {
		if (mirror != Mirror.NONE) {
			Direction.Axis axis = state.get(AXIS);
			if (axis == Direction.Axis.Y
					|| mirror == Mirror.LEFT_RIGHT && axis == Direction.Axis.Z
					|| mirror == Mirror.FRONT_BACK && axis == Direction.Axis.X) {

				return state.cycle(REVERSED);
			}
		}
		return super.mirror(state, mirror);
	}
}
