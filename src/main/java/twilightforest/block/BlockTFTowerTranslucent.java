package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.world.IWorldReader;

public class BlockTFTowerTranslucent extends Block {

	public BlockTFTowerTranslucent(Properties props) {
		super(props.noDrops().nonOpaque());
	}

	@Override
	public int tickRate(IWorldReader world) {
		return 15;
	}

	// todo 1.10 smart model for REACTOR_DEBRIS that randomly chooses sides from portal/netherrack/bedrock/obsidian

	//TODO: Move to client
//	@OnlyIn(Dist.CLIENT)
//	@Override
//	public BlockRenderLayer getRenderLayer() {
//		return BlockRenderLayer.CUTOUT;
//	}
}
