package twilightforest.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// Shim interface to expose the WorldGenerator's setBlock method
// Mainly so TFGenerator's util methods don't have to be duplicated between the TFGenerator subclasses and the
// tree generators
// todo 1.9 improve this?
public interface IBlockSettable {
    // [VanillaCopy] pin to signature of WorldGenerator.setBlockAndNotifyAdequately
    void setBlockAndNotifyAdequately(World world, BlockPos pos, IBlockState state);
}
