package twilightforest.tileentity;

import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import twilightforest.client.particle.TFParticleType;

public class FireflyTileEntity extends BlockEntity implements TickableBlockEntity {

	private int yawDelay;
	public int currentYaw;
	private int desiredYaw;

	public float glowIntensity;
	private boolean glowing;
	private int glowDelay;

	public FireflyTileEntity() {
		super(TFTileEntities.FIREFLY.get());
	}

	@Override
	public void tick() {
		if (level.isClientSide) {
			if (anyPlayerInRange() && level.random.nextInt(20) == 0) {
				spawnParticles();
			}

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

			if (glowDelay > 0) {
				glowDelay--;
			} else {
				if (glowing && glowIntensity >= 1.0) {
					glowing = false;
				}
				if (glowing && glowIntensity < 1.0) {
					glowIntensity += 0.05;
				}
				if (!glowing && glowIntensity > 0) {
					glowIntensity -= 0.05;
				}
				if (!glowing && glowIntensity <= 0) {
					glowing = true;
					glowDelay = level.random.nextInt(50);
				}
			}
		}
	}

	private boolean anyPlayerInRange() {
		return level.getNearestPlayer(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D, 16D, false) != null;
	}

	private void spawnParticles() {
		double rx = worldPosition.getX() + level.random.nextFloat();
		double ry = worldPosition.getY() + level.random.nextFloat();
		double rz = worldPosition.getZ() + level.random.nextFloat();
//    	ModLoader.getMinecraftInstance().effectRenderer.addEffect(fireflyfx);
		// ^ keeping here only for pure lolz
		level.addParticle(TFParticleType.FIREFLY.get(), rx, ry, rz, 0, 0, 0);
	}
}
