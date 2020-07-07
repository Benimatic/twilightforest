package twilightforest.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BlockTFWallPillar extends BlockTFConnectableRotatedPillar {


    BlockTFWallPillar(Material material, double width, double height) {
        super(Properties.create(material).hardnessAndResistance(1.5F, 10.0F), width, height);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return /*(!state.getValue(UP)) || (!state.getValue(DOWN)) ? FULL_BLOCK_AABB :*/ super.getShape(state, world, pos, context);
    }
}
