package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import twilightforest.init.TFSounds;

public class EncasedSmokerBlock extends TFSmokerBlock {

	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

	public EncasedSmokerBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(ACTIVE, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ACTIVE);
	}

	@Override
	@Deprecated
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		if (level.isClientSide()) return;

		boolean powered = level.hasNeighborSignal(pos);

		if (!state.getValue(ACTIVE) && powered) {
			level.setBlock(pos, state.setValue(ACTIVE, true), 3);
			level.playSound(null, pos, TFSounds.SMOKER_START.get(), SoundSource.BLOCKS, 0.3F, 0.6F);
		}

		if (state.getValue(ACTIVE) && !powered) {
			level.setBlock(pos, state.setValue(ACTIVE, false), 3);
			level.playSound(null, pos, TFSounds.SMOKER_START.get(), SoundSource.BLOCKS, 0.3F, 0.6F);
		}
	}
}
