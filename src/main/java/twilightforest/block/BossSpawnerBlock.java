package twilightforest.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import twilightforest.enums.BossVariant;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class BossSpawnerBlock extends Block {

	private final BossVariant boss;

	protected BossSpawnerBlock(BlockBehaviour.Properties props, BossVariant variant) {
		super(props);
		boss = variant;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return boss.hasSpawner();
	}

	@Override
	@Nullable
	public BlockEntity createTileEntity(BlockState state, BlockGetter reader) {
		return boss.getSpawner();
	}

	@Override
	public boolean canEntityDestroy(BlockState state, BlockGetter world, BlockPos pos, Entity entity) {
		return state.getDestroySpeed(world, pos) >= 0f;
	}
}
