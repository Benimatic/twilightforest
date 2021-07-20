package twilightforest.tileentity.spawner;

import net.minecraft.entity.player.PlayerEntity;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.AlphaYetiEntity;
import twilightforest.tileentity.TFTileEntities;

public class AlphaYetiSpawnerTileEntity extends BossSpawnerTileEntity<AlphaYetiEntity> {

	public AlphaYetiSpawnerTileEntity() {
		super(TFTileEntities.ALPHA_YETI_SPAWNER.get(), TFEntities.yeti_alpha);
	}

	@Override
	public boolean anyPlayerInRange() {
		PlayerEntity closestPlayer = world.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, getRange(), false);
		return closestPlayer != null && closestPlayer.getPosY() > pos.getY() - 4;
	}
}
