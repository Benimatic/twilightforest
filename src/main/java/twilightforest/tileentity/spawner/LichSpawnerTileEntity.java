package twilightforest.tileentity.spawner;

import net.minecraft.entity.player.PlayerEntity;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.LichEntity;
import twilightforest.tileentity.TFTileEntities;

public class LichSpawnerTileEntity extends BossSpawnerTileEntity<LichEntity> {

	public LichSpawnerTileEntity() {
		super(TFTileEntities.LICH_SPAWNER.get(), TFEntities.lich);
	}

	@Override
	public boolean anyPlayerInRange() {
		PlayerEntity closestPlayer = world.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, getRange(), false);
		return closestPlayer != null && closestPlayer.getPosY() > pos.getY() - 4;
	}
}
