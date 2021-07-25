package twilightforest.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.client.particle.TFParticleType;

public class TFSmokerTileEntity extends BlockEntity {

	private long counter = 0;

	public TFSmokerTileEntity(BlockPos pos, BlockState state) {
		super(TFTileEntities.SMOKER.get(), pos, state);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TFSmokerTileEntity te) {
		if (level.isClientSide && ++te.counter % 4 == 0) {
			level.addParticle(TFParticleType.HUGE_SMOKE.get(), pos.getX() + 0.5, pos.getY() + 0.95, pos.getZ() + 0.5,
					Math.cos(te.counter / 10.0) * 0.05, 0.25D, Math.sin(te.counter / 10.0) * 0.05
			);
		}
	}
}
