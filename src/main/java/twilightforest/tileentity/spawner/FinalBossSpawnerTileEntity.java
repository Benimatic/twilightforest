package twilightforest.tileentity.spawner;

import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.PlateauBossEntity;
import twilightforest.tileentity.TFTileEntities;

public class FinalBossSpawnerTileEntity extends BossSpawnerTileEntity<PlateauBossEntity> {

	public FinalBossSpawnerTileEntity() {
		super(TFTileEntities.FINAL_BOSS_SPAWNER.get(), TFEntities.plateau_boss);
	}

	@Override
	public void tick() {

	}
}
