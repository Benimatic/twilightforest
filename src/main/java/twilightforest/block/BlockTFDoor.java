package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import twilightforest.client.ModelRegisterCallback;

public class BlockTFDoor extends BlockDoor implements ModelRegisterCallback {

    private final MapColor mapColor;

    protected BlockTFDoor(Material material, MapColor mapColor) {
        super(material);
        this.mapColor = mapColor;
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        return mapColor;
    }
}
