package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockTFFluffyCloud extends Block {

	protected BlockTFFluffyCloud() {
		super(Properties.create(Material.PACKED_ICE).hardnessAndResistance(0.8F, 0.0F).sound(SoundType.CLOTH));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}
}
