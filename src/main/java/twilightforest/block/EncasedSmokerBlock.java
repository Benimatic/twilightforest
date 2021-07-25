package twilightforest.block;

import net.minecraft.block.*;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import twilightforest.TFSounds;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class EncasedSmokerBlock extends TFSmokerBlock {

	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

	protected EncasedSmokerBlock(Properties props) {
		super(props);
		this.registerDefaultState(stateDefinition.any().setValue(ACTIVE, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ACTIVE);
	}

	@Override
	@Deprecated
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if (world.isClientSide) return;

		boolean powered = world.hasNeighborSignal(pos);

		if (!state.getValue(ACTIVE) && powered) {
			world.setBlock(pos, state.setValue(ACTIVE, true), 3);
			world.playSound(null, pos, TFSounds.SMOKER_START, SoundSource.BLOCKS, 0.3F, 0.6F);
		}

		if (state.getValue(ACTIVE) && !powered) {
			world.setBlock(pos, state.setValue(ACTIVE, false), 3);
			world.playSound(null, pos, TFSounds.SMOKER_START, SoundSource.BLOCKS, 0.3F, 0.6F);
		}
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return state.getValue(ACTIVE);
	}

}
