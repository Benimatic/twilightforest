package twilightforest.block;

import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;

public class BlockTFSlab extends SlabBlock {

    BlockTFSlab(MaterialColor mapColor) {
        super(Properties.create(Material.WOOD, mapColor).hardnessAndResistance(2.0F, 5.0F).sound(SoundType.WOOD));
    }
}
