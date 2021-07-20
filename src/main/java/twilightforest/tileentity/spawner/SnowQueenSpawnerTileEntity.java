package twilightforest.tileentity.spawner;

import net.minecraft.entity.player.PlayerEntity;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.SnowQueenEntity;
import twilightforest.tileentity.TFTileEntities;

public class SnowQueenSpawnerTileEntity extends BossSpawnerTileEntity<SnowQueenEntity> {

	public SnowQueenSpawnerTileEntity() {
		super(TFTileEntities.SNOW_QUEEN_SPAWNER.get(), TFEntities.snow_queen);
	}

	@Override
	public boolean anyPlayerInRange() {
		PlayerEntity closestPlayer = world.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, getRange(), false);
		return closestPlayer != null && closestPlayer.getPosY() > pos.getY() - 4;
	}
}
