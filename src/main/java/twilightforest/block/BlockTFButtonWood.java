package twilightforest.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;

public class BlockTFButtonWood extends WoodButtonBlock {

	public BlockTFButtonWood() {
		super(Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.5F).sound(SoundType.WOOD));
	}
}
