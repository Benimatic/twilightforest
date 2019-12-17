package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;

public class BlockTFUnderBrick extends Block {

	public BlockTFUnderBrick() {
		super(Properties.create(Material.ROCK, MaterialColor.WOOD).hardnessAndResistance(1.5F, 10.0F).sound(SoundType.STONE));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}
}
