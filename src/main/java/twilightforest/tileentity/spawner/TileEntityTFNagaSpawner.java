package twilightforest.tileentity.spawner;

import net.minecraft.entity.EntityType;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.EntityTFNaga;
import twilightforest.tileentity.TFTileEntities;

public class TileEntityTFNagaSpawner extends TileEntityTFBossSpawner<EntityTFNaga> {

	public TileEntityTFNagaSpawner() {
		super(TFTileEntities.NAGA_SPAWNER.get(), TFEntities.naga.get());
	}

	@Override
	protected int getRange() {
		return LONG_RANGE;
	}
}
