package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import twilightforest.client.ModelRegisterCallback;

public class BlockTFTrapDoor extends BlockTrapDoor implements ModelRegisterCallback {

    protected BlockTFTrapDoor(Material materialIn) {
        super(materialIn);
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }

    @Override
    public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos) {
        return PathNodeType.TRAPDOOR;
    }
}
