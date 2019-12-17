package twilightforest.block;

import net.minecraft.block.FenceBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;

public class BlockTFFence extends FenceBlock {
    public BlockTFFence(MaterialColor mapColorIn) {
        super(Properties.create(Material.WOOD, mapColorIn).hardnessAndResistance(2.0F, 5.0F).sound(SoundType.WOOD));
    }
}
