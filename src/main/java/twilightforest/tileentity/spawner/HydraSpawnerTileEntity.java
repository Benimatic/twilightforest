package twilightforest.tileentity.spawner;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.block.TFBlocks;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.HydraEntity;
import twilightforest.tileentity.TFTileEntities;

public class HydraSpawnerTileEntity extends BossSpawnerTileEntity<HydraEntity> {

	public HydraSpawnerTileEntity(BlockPos pos, BlockState state) {
		super(TFTileEntities.HYDRA_SPAWNER.get(), TFEntities.hydra, pos, state);
	}

	public HydraSpawnerTileEntity() {
		this(BlockPos.ZERO, TFBlocks.boss_spawner_hydra.get().defaultBlockState());
	}

	@Override
	protected int getRange() {
		return LONG_RANGE;
	}
}
