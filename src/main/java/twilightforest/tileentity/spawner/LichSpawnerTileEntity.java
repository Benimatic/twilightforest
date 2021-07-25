package twilightforest.tileentity.spawner;

import net.minecraft.world.entity.player.Player;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.LichEntity;
import twilightforest.tileentity.TFTileEntities;

public class LichSpawnerTileEntity extends BossSpawnerTileEntity<LichEntity> {

	public LichSpawnerTileEntity() {
		super(TFTileEntities.LICH_SPAWNER.get(), TFEntities.lich);
	}

	@Override
	public boolean anyPlayerInRange() {
		Player closestPlayer = level.getNearestPlayer(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D, getRange(), false);
		return closestPlayer != null && closestPlayer.getY() > worldPosition.getY() - 4;
	}
}
