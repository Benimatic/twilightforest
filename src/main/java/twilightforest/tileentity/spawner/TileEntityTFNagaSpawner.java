package twilightforest.tileentity.spawner;

import net.minecraft.entity.EntityType;
import twilightforest.entity.TFEntities;
import twilightforest.tileentity.TFTileEntities;

public class TileEntityTFNagaSpawner extends TileEntityTFBossSpawner {

	public TileEntityTFNagaSpawner() {
		super(TFTileEntities.NAGA_SPAWNER.get(), EntityType.getKey(TFEntities.naga.get()));
	}

	@Override
	protected int getRange() {
		return LONG_RANGE;
	}
}
