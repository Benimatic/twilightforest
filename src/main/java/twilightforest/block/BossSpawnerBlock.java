package twilightforest.block;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
import twilightforest.tileentity.TFTileEntities;
import twilightforest.tileentity.TrophyTileEntity;
import twilightforest.tileentity.spawner.BossSpawnerTileEntity;

public class BossSpawnerBlock extends BaseEntityBlock {

	private final BossVariant boss;

	protected BossSpawnerBlock(BlockBehaviour.Properties props, BossVariant variant) {
		super(props);
		boss = variant;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return boss.getSpawner();
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, boss.getSpawner().getType(), BossSpawnerTileEntity::tick);
	}

	@Override
	public boolean canEntityDestroy(BlockState state, BlockGetter world, BlockPos pos, Entity entity) {
		return state.getDestroySpeed(world, pos) >= 0f;
	}
}
