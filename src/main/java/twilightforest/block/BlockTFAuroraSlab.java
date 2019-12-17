package twilightforest.block;

import net.minecraft.block.SlabBlock;
import net.minecraft.block.material.Material;

public class BlockTFAuroraSlab extends SlabBlock {

	public BlockTFAuroraSlab() {
		super(Properties.create(Material.PACKED_ICE).hardnessAndResistance(2.0F, 10.0F));
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}
}
