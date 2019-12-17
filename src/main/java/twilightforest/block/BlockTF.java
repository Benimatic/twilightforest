package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;

public class BlockTF extends Block {
    public BlockTF(MaterialColor blockMapColorIn) {
        super(Properties.create(Material.WOOD, blockMapColorIn).hardnessAndResistance(2.0F, 5.0F).sound(SoundType.WOOD));
    }
}
