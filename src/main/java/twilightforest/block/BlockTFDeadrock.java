package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockTFDeadrock extends Block {

	protected BlockTFDeadrock() {
		super(Properties.create(Material.ROCK).hardnessAndResistance(100.0F, 6000000.0F).sound(SoundType.STONE));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}
}
