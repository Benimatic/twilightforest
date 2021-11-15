package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import twilightforest.TFSounds;
import twilightforest.enums.FireJetVariant;

public class EncasedFireJetBlock extends FireJetBlock {

	public static final EnumProperty<FireJetVariant> STATE = EnumProperty.create("state", FireJetVariant.class);

	protected EncasedFireJetBlock(Properties props) {
		super(props);
	}

	@Override
	@Deprecated
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		FireJetVariant variant = state.getValue(STATE);
		boolean powered = world.hasNeighborSignal(pos);

		if (variant == FireJetVariant.IDLE && powered) {
			world.setBlockAndUpdate(pos, state.setValue(STATE, FireJetVariant.POPPING));
			world.playSound(null, pos, TFSounds.JET_START, SoundSource.BLOCKS, 0.3F, 0.6F);

		} else if (variant == FireJetVariant.TIMEOUT && !powered) {
			world.setBlockAndUpdate(pos, state.setValue(STATE, FireJetVariant.IDLE));
		}
	}
}
