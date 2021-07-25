package twilightforest.block;

import net.minecraft.block.*;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import twilightforest.TFSounds;
import twilightforest.enums.FireJetVariant;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

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
		}
	}
}
