package twilightforest.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.material.PushReaction;
import twilightforest.TFSounds;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TranslucentBuiltBlock extends Block {

	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

	public TranslucentBuiltBlock(Properties props) {
		super(props);
		this.registerDefaultState(stateDefinition.any().setValue(ACTIVE, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> container) {
		super.createBlockStateDefinition(container);
		container.add(ACTIVE);
	}

	@Override
	@Deprecated
	public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
		if (state.getValue(ACTIVE)) {
			world.removeBlock(pos, false);
			world.playSound(null, pos, TFSounds.BUILDER_REPLACE, SoundSource.BLOCKS, 0.3F, 0.5F);

			for (Direction e : Direction.values()) {
				BuilderBlock.activateBuiltBlocks(world, pos.relative(e));
			}
		}
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState pState) {
		return PushReaction.BLOCK;
	}
}
