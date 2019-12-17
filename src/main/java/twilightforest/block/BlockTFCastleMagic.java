package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Castle block makes a castle
 *
 * @author Ben
 */
public class BlockTFCastleMagic extends Block {

	public BlockTFCastleMagic() {
		super(Properties.create(Material.ROCK, MaterialColor.QUARTZ).hardnessAndResistance(100.0F, 15.0F).sound(SoundType.STONE));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}
	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
}
