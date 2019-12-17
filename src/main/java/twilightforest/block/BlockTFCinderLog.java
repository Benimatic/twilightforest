package twilightforest.block;

import net.minecraft.block.LogBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class BlockTFCinderLog extends LogBlock {

	protected BlockTFCinderLog() {
		super(MaterialColor.GRAY, Properties.create(Material.WOOD, MaterialColor.GRAY).hardnessAndResistance(1.0F));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}
}
