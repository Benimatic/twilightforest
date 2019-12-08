package twilightforest.block;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockTFDirectionalRotatedPillar extends BlockRotatedPillar {

	public static final IProperty<Boolean> REVERSED = PropertyBool.create("reversed");

	public BlockTFDirectionalRotatedPillar(Material materialIn) {
		super(materialIn);
	}

	public BlockTFDirectionalRotatedPillar(Material materialIn, MaterialColor color) {
		super(materialIn, color);
	}

	@Override
	public BlockState getStateFromMeta(int meta) {
		BlockState state = super.getStateFromMeta(meta);
		return state.withProperty(REVERSED, (meta & 1) != 0);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		int meta = super.getMetaFromState(state);
		return meta | (state.getValue(REVERSED) ? 1 : 0);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, AXIS, REVERSED);
	}

	@Override
	public BlockState getStateForPlacement(World worldIn, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer)
		            .withProperty(REVERSED, facing.getAxisDirection() == Direction.AxisDirection.NEGATIVE);
	}

	@Override
	public BlockState withMirror(BlockState state, Mirror mirror) {
		if (mirror != Mirror.NONE) {
			Direction.Axis axis = state.getValue(AXIS);
			if (axis == Direction.Axis.Y
					|| mirror == Mirror.LEFT_RIGHT && axis == Direction.Axis.Z
					|| mirror == Mirror.FRONT_BACK && axis == Direction.Axis.X) {

				return state.cycleProperty(REVERSED);
			}
		}
		return super.withMirror(state, mirror);
	}
}
