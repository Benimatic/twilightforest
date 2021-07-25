package twilightforest.tileentity.spawner;

import net.minecraft.world.entity.player.Player;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.MinoshroomEntity;
import twilightforest.tileentity.TFTileEntities;

public class MinoshroomSpawnerTileEntity extends BossSpawnerTileEntity<MinoshroomEntity> {

	public MinoshroomSpawnerTileEntity() {
		super(TFTileEntities.MINOSHROOM_SPAWNER.get(), TFEntities.minoshroom);
	}

	@Override
	public boolean anyPlayerInRange() {
		Player closestPlayer = level.getNearestPlayer(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D, getRange(), false);
		return closestPlayer != null && closestPlayer.getY() > worldPosition.getY() - 4;
	}
}
