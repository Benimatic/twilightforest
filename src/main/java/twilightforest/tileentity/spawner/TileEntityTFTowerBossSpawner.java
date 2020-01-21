package twilightforest.tileentity.spawner;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import twilightforest.entity.TFEntities;
import twilightforest.tileentity.TFTileEntities;

public class TileEntityTFTowerBossSpawner extends TileEntityTFBossSpawner {

	public TileEntityTFTowerBossSpawner() {
		super(TFTileEntities.TOWER_BOSS_SPAWNER.get(), EntityType.getKey(TFEntities.ur_ghast.get()));
	}

	@Override
	public boolean anyPlayerInRange() {
		PlayerEntity closestPlayer = world.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, getRange(), false);
		return closestPlayer != null && closestPlayer.getY() > pos.getY() - 4;
	}
}
