package twilightforest.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CarminiteBlock extends Block {
	public CarminiteBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isStickyBlock(BlockState state) {
		return true;
	}
}
