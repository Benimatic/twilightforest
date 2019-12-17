package twilightforest.block;

import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;

public class BlockTFCastlePillar extends RotatedPillarBlock {

    BlockTFCastlePillar() {
        super(Properties.create(Material.ROCK, MaterialColor.QUARTZ).hardnessAndResistance(100.0F, 35.0F).sound(SoundType.STONE));
        //this.setCreativeTab(TFItems.creativeTab); TODO 1.14
    }
}