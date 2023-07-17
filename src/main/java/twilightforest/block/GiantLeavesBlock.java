package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GiantLeavesBlock extends GiantBlock {

	public GiantLeavesBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getBlockSupportShape(BlockState state, BlockGetter getter, BlockPos pos) {
		return Shapes.empty();
	}

	@Override
	public boolean isValidSpawn(BlockState state, BlockGetter getter, BlockPos pos, SpawnPlacements.Type type, EntityType<?> entityType) {
		return false;
	}
}
