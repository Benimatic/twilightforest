package twilightforest.tileentity.spawner;

import net.minecraft.entity.player.PlayerEntity;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.UrGhastEntity;
import twilightforest.tileentity.TFTileEntities;

public class TowerBossSpawnerTileEntity extends BossSpawnerTileEntity<UrGhastEntity> {

	public TowerBossSpawnerTileEntity() {
		super(TFTileEntities.TOWER_BOSS_SPAWNER.get(), TFEntities.ur_ghast);
	}

	@Override
	public boolean anyPlayerInRange() {
		PlayerEntity closestPlayer = world.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, getRange(), false);
		return closestPlayer != null && closestPlayer.getPosY() > pos.getY() - 4;
	}
}
