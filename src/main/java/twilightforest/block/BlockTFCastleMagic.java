package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;

/**
 * Castle block makes a castle
 *
 * @author Ben
 */
public class BlockTFCastleMagic extends Block {

	public BlockTFCastleMagic(Properties props) {
		super(props);
	}

	//TODO: Move to client
//	@Override
//	@OnlyIn(Dist.CLIENT)
//	public BlockRenderLayer getRenderLayer() {
//		return BlockRenderLayer.CUTOUT;
//	}
}
