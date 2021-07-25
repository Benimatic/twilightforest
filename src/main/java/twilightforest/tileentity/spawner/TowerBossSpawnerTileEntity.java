package twilightforest.tileentity.spawner;

import net.minecraft.world.entity.player.Player;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.UrGhastEntity;
import twilightforest.tileentity.TFTileEntities;

public class TowerBossSpawnerTileEntity extends BossSpawnerTileEntity<UrGhastEntity> {

	public TowerBossSpawnerTileEntity() {
		super(TFTileEntities.TOWER_BOSS_SPAWNER.get(), TFEntities.ur_ghast);
	}

	@Override
	public boolean anyPlayerInRange() {
		Player closestPlayer = level.getNearestPlayer(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D, getRange(), false);
		return closestPlayer != null && closestPlayer.getY() > worldPosition.getY() - 4;
	}
}
