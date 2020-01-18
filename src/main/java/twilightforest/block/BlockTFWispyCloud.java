package twilightforest.block;

import net.minecraft.block.BreakableBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockTFWispyCloud extends BreakableBlock {

	protected BlockTFWispyCloud() {
		//super(new Material(MaterialColor.SNOW), false); TODO: Why are we making a new Material, exactly?
		super(Properties.create(Material.SNOW).hardnessAndResistance(0.3F).sound(SoundType.CLOTH));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

	//TODO: Move to client
//	@Override
//	public BlockRenderLayer getRenderLayer() {
//		return BlockRenderLayer.TRANSLUCENT;
//	}

//	@Override
//	public boolean canSilkHarvest(World world, BlockPos pos, BlockState state, PlayerEntity player) {
//		return true;
//	}
//
//	@Override
//	public int quantityDropped(Random random) {
//		return 0;
//	}
}
