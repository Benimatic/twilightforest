package twilightforest.tileentity;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import twilightforest.client.particle.TFParticleType;

public class TileEntityTFSmoker extends TileEntity implements ITickableTileEntity {

	private long counter = 0;

	public TileEntityTFSmoker() {
		super(TFTileEntities.SMOKER.get());
	}

	@Override
	public void tick() {
		if (world.isRemote && ++counter % 4 == 0) {
			world.addParticle(TFParticleType.HUGE_SMOKE.get(), pos.getX() + 0.5, pos.getY() + 0.95, pos.getZ() + 0.5,
					Math.cos(counter / 10.0) * 0.05, 0.25D, Math.sin(counter / 10.0) * 0.05
			);
		}
	}
}
