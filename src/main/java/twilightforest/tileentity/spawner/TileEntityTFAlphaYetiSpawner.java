package twilightforest.tileentity.spawner;

import net.minecraft.entity.player.PlayerEntity;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.EntityTFYetiAlpha;
import twilightforest.tileentity.TFTileEntities;

public class TileEntityTFAlphaYetiSpawner extends TileEntityTFBossSpawner<EntityTFYetiAlpha> {

	public TileEntityTFAlphaYetiSpawner() {
		super(TFTileEntities.ALPHA_YETI_SPAWNER.get(), TFEntities.yeti_alpha);
	}

	@Override
	public boolean anyPlayerInRange() {
		PlayerEntity closestPlayer = world.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, getRange(), false);
		return closestPlayer != null && closestPlayer.getY() > pos.getY() - 4;
	}
}
