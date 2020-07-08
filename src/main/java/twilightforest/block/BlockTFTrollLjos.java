package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockTFTrollLjos extends Block {

	protected BlockTFTrollLjos() {
		super(Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 15.0F).sound(SoundType.STONE).setLightLevel((state) -> 15));
	}
}
