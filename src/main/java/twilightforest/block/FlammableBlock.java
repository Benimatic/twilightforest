package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class FlammableBlock extends Block {
	private final int flammability;
	private final int spreadSpeed;

	public FlammableBlock(int flammability, int spreadSpeed, BlockBehaviour.Properties properties) {
		super(properties);
		this.flammability = flammability;
		this.spreadSpeed = spreadSpeed;
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter getter, BlockPos pos, Direction face) {
		return flammability;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter getter, BlockPos pos, Direction face) {
		return spreadSpeed;
	}
}
