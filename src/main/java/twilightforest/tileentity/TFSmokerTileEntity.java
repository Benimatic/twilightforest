package twilightforest.tileentity;

import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import twilightforest.client.particle.TFParticleType;

public class TFSmokerTileEntity extends BlockEntity implements TickableBlockEntity {

	private long counter = 0;

	public TFSmokerTileEntity() {
		super(TFTileEntities.SMOKER.get());
	}

	@Override
	public void tick() {
		if (level.isClientSide && ++counter % 4 == 0) {
			level.addParticle(TFParticleType.HUGE_SMOKE.get(), worldPosition.getX() + 0.5, worldPosition.getY() + 0.95, worldPosition.getZ() + 0.5,
					Math.cos(counter / 10.0) * 0.05, 0.25D, Math.sin(counter / 10.0) * 0.05
			);
		}
	}
}
