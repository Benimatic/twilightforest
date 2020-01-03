package twilightforest.tileentity.spawner;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityType;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.EntityTFHydra;
import twilightforest.entity.boss.EntityTFHydraHead;
import twilightforest.tileentity.TFTileEntities;

public class TileEntityTFHydraSpawner extends TileEntityTFBossSpawner {

	public TileEntityTFHydraSpawner() {
		super(TFTileEntities.HYDRA_SPAWNER.get(), EntityType.getKey(TFEntities.hydra.get()));
	}

	@Override
	protected int getRange() {
		return LONG_RANGE;
	}

	@Override
	public Entity getDisplayEntity() {
		if (this.displayCreature == null) {
			this.displayCreature = EntityType.create(EntityType.getKey(TFEntities.hydra_head.get()), world);
		}
		return this.displayCreature;
	}
}
