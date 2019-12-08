package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import twilightforest.client.ModelRegisterCallback;

public class BlockTF extends Block implements ModelRegisterCallback {
    public BlockTF(Material blockMaterialIn, MaterialColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }
}
