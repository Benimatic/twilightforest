package twilightforest.tileentity.spawner;

import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.NagaEntity;
import twilightforest.tileentity.TFTileEntities;

public class NagaSpawnerTileEntity extends BossSpawnerTileEntity<NagaEntity> {

	public NagaSpawnerTileEntity() {
		super(TFTileEntities.NAGA_SPAWNER.get(), TFEntities.naga);
	}

	@Override
	protected int getRange() {
		return LONG_RANGE;
	}
}
