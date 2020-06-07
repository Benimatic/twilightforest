package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;

/**
 * Just a dummy subclass to register the extra blockstate property.
 * See the comments on the superclass.
 */
public class BlockReappearing extends BlockTFVanishingBlock {

	public BlockReappearing(Properties props) {
		super(props);
		this.setDefaultState(getDefaultState().with(VANISHED, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(VANISHED);
	}
}
