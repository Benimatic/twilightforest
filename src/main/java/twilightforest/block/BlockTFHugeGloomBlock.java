package twilightforest.block;

import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;

public class BlockTFHugeGloomBlock extends HugeMushroomBlock {

	public BlockTFHugeGloomBlock() {
		super(Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(0.2F).sound(SoundType.WOOD).lightValue(5));
		//TODO: Add to item group (item)
	}
}