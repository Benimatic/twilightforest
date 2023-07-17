package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.block.entity.GrowingBeanstalkBlockEntity;
import twilightforest.init.TFBlockEntities;

import javax.annotation.Nullable;

public class GrowingBeanstalkBlock extends BaseEntityBlock {
	public GrowingBeanstalkBlock(Properties properties) {
		super(properties);
	}

	//dont touch me
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext ctx) {
		return Shapes.empty();
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext ctx) {
		return Shapes.empty();
	}

	@Override
	public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter getter, BlockPos pos) {
		return false;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new GrowingBeanstalkBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, TFBlockEntities.BEANSTALK_GROWER.get(), GrowingBeanstalkBlockEntity::tick);
	}
}
