package twilightforest.block;

import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;

public class BlockTFPressurePlate extends PressurePlateBlock {

    public BlockTFPressurePlate(MaterialColor mapColor) {
        super(Sensitivity.EVERYTHING, Properties.create(Material.WOOD, mapColor).hardnessAndResistance(0.5F).sound(SoundType.WOOD).doesNotBlockMovement());
    }
}
