package twilightforest.tileentity.spawner;

import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.HydraEntity;
import twilightforest.tileentity.TFTileEntities;

public class HydraSpawnerTileEntity extends BossSpawnerTileEntity<HydraEntity> {

	public HydraSpawnerTileEntity() {
		super(TFTileEntities.HYDRA_SPAWNER.get(), TFEntities.hydra);
	}

	@Override
	protected int getRange() {
		return LONG_RANGE;
	}
}
