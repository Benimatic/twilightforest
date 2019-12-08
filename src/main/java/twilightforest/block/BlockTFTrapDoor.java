package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import twilightforest.client.ModelRegisterCallback;

public class BlockTFTrapDoor extends BlockTrapDoor implements ModelRegisterCallback {

    private final MaterialColor mapColor;

    protected BlockTFTrapDoor(Material material, MaterialColor mapColor) {
        super(material);
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

    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockAccess world, BlockPos pos) {
        return PathNodeType.TRAPDOOR;
    }
}
