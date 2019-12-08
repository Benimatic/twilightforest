package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import twilightforest.client.ModelRegisterCallback;

public class BlockTFPressurePlate extends BlockPressurePlate implements ModelRegisterCallback {

    private final MaterialColor mapColor;

    public BlockTFPressurePlate(Material material, MaterialColor mapColor, BlockPressurePlate.Sensitivity sensitivity) {
        super(material, sensitivity);
        this.mapColor = mapColor;
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }

    @Override
    public MaterialColor getMaterialColor(BlockState state, IBlockAccess world, BlockPos pos) {
        return mapColor;
    }
}
