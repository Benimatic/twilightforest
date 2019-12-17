package twilightforest.block;

import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class BlockTFFenceGate extends FenceGateBlock {
    public BlockTFFenceGate(MaterialColor color) {
        super(Properties.create(Material.WOOD, color).hardnessAndResistance(2.0F, 5.0F).sound(SoundType.WOOD));
    }
}
