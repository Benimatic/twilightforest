package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockTFLapisBlock extends Block {

    protected BlockTFLapisBlock() {
        super(Properties.create(Material.IRON).hardnessAndResistance(3.0F, 5.0F).sound(SoundType.STONE));
        //this.setCreativeTab(TFItems.creativeTab); TODO 1.14
    }
}