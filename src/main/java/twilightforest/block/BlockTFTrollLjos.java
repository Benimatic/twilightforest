package twilightforest.block;

import net.minecraft.block.SoundType;
import twilightforest.item.TFItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockTFTrollLjos extends Block {
	
	protected BlockTFTrollLjos() {
		super(Material.ROCK);

        this.setHardness(2F);
        this.setResistance(15F);
        this.setSoundType(SoundType.STONE);
		this.setCreativeTab(TFItems.creativeTab);
        this.setLightLevel(1.0F);
	}
}
