package twilightforest.tileentity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.sounds.SoundSource;
import twilightforest.TFConfig;
import twilightforest.TFSounds;

public class CicadaTileEntity extends BlockEntity implements TickableBlockEntity {
	private int yawDelay;
	public int currentYaw;
	private int desiredYaw;

	private int singDuration;
	private boolean singing;
	private int singDelay;

	public CicadaTileEntity() {
		super(TFTileEntities.CICADA.get());
	}

	@Override
	public void tick() {
		if (level.isClientSide) {
			if (yawDelay > 0) {
				yawDelay--;
			} else {
				if (currentYaw == 0 && desiredYaw == 0) {
					// make it rotate!
					yawDelay = 200 + level.random.nextInt(200);
					desiredYaw = level.random.nextInt(15) - level.random.nextInt(15);
				}

				if (currentYaw < desiredYaw) {
					currentYaw++;
				}
				if (currentYaw > desiredYaw) {
					currentYaw--;
				}
				if (currentYaw == desiredYaw) {
					desiredYaw = 0;
				}
			}

			if (singDelay > 0) {
				singDelay--;
			} else {
				if (singing && singDuration == 0) {
					playSong();
				}
				if (singing && singDuration >= 100) {
					singing = false;
					singDuration = 0;
				}
				if (singing && singDuration < 100) {
					singDuration++;
					doSingAnimation();
				}
				if (!singing && singDuration <= 0) {
					singing = true;
					singDelay = 100 + level.random.nextInt(100);
				}
			}
		}
	}

	private void doSingAnimation() {
		if (level.random.nextInt(5) == 0) {
			double rx = worldPosition.getX() + level.random.nextFloat();
			double ry = worldPosition.getY() + level.random.nextFloat();
			double rz = worldPosition.getZ() + level.random.nextFloat();
			level.addParticle(ParticleTypes.NOTE, rx, ry, rz, 0.0D, 0.0D, 0.0D);
		}
	}

	private void playSong() {
		if (!TFConfig.CLIENT_CONFIG.silentCicadas.get()) {
			level.playLocalSound(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), TFSounds.CICADA, SoundSource.NEUTRAL, 1.0f, (level.random.nextFloat() - level.random.nextFloat()) * 0.2F + 1.0F, false);
		}
	}
}
