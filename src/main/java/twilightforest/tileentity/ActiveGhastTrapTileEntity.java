package twilightforest.tileentity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import twilightforest.TFSounds;
import twilightforest.block.GhastTrapBlock;
import twilightforest.block.TFBlocks;
import twilightforest.client.particle.TFParticleType;
import twilightforest.entity.CarminiteGhastlingEntity;
import twilightforest.entity.CarminiteGhastguardEntity;
import twilightforest.entity.boss.UrGhastEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ActiveGhastTrapTileEntity extends BlockEntity implements TickableBlockEntity {

	private int counter = 0;
	private final List<CarminiteGhastlingEntity> dyingGhasts = new ArrayList<>();
	private final Random rand = new Random();

	public ActiveGhastTrapTileEntity() {
		super(TFTileEntities.GHAST_TRAP.get());
	}

	private void tickInactive() {
		// check to see if there are any dying mini ghasts within our scan range
		AABB aabb = new AABB(worldPosition).inflate(10D, 16D, 10D);

		List<CarminiteGhastlingEntity> nearbyGhasts = level.getEntitiesOfClass(CarminiteGhastlingEntity.class, aabb);

		for (CarminiteGhastlingEntity ghast : nearbyGhasts) {
			if (ghast.deathTime > 0) {
				this.makeParticlesTo(ghast);

				if (!dyingGhasts.contains(ghast)) {
					dyingGhasts.add(ghast);
				}
			}
		}

		// display charge level, up to 3
		int chargeLevel = Math.min(3, dyingGhasts.size());

		counter++;

		if (this.level.isClientSide) {
			// occasionally make a redstone line to a mini ghast
			if (this.counter % 20 == 0 && nearbyGhasts.size() > 0) {
				CarminiteGhastlingEntity highlight = nearbyGhasts.get(rand.nextInt(nearbyGhasts.size()));
				this.makeParticlesTo(highlight);
			}

			if (chargeLevel >= 1 && counter % 10 == 0) {
				TFBlocks.ghast_trap.get().sparkle(level, this.worldPosition);
				level.playLocalSound(this.worldPosition.getX() + 0.5D, this.worldPosition.getY() + 1.5D, this.worldPosition.getZ() + 0.5D, SoundEvents.NOTE_BLOCK_HARP, SoundSource.BLOCKS, 1.0F, 1.0F, false);
			}
			if (chargeLevel >= 2) {
				level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + 0.1 + rand.nextFloat() * 0.8, worldPosition.getY() + 1.05, worldPosition.getZ() + 0.1 + rand.nextFloat() * 0.8, (rand.nextFloat() - rand.nextFloat()) * 0.05, 0.00, (rand.nextFloat() - rand.nextFloat()) * 0.05);
				if (counter % 10 == 0) {
					level.playLocalSound(this.worldPosition.getX() + 0.5D, this.worldPosition.getY() + 1.5D, this.worldPosition.getZ() + 0.5D, SoundEvents.NOTE_BLOCK_HARP, SoundSource.BLOCKS, 1.2F, 0.8F, false);
				}
			}
			if (chargeLevel >= 3) {
				level.addParticle(ParticleTypes.LARGE_SMOKE, this.worldPosition.getX() + 0.1 + rand.nextFloat() * 0.8, this.worldPosition.getY() + 1.05, this.worldPosition.getZ() + 0.1 + rand.nextFloat() * 0.8, (rand.nextFloat() - rand.nextFloat()) * 0.05, 0.05, (rand.nextFloat() - rand.nextFloat()) * 0.05);
				TFBlocks.ghast_trap.get().sparkle(level, this.worldPosition);
				if (counter % 5 == 0) {
					level.playLocalSound(this.worldPosition.getX() + 0.5D, this.worldPosition.getY() + 1.5D, this.worldPosition.getZ() + 0.5D, SoundEvents.NOTE_BLOCK_HARP, SoundSource.BLOCKS, 1.5F, 2F, false);
				}
			}
		}
	}

	private void makeParticlesTo(Entity highlight) {

		double sx = this.worldPosition.getX() + 0.5D;
		double sy = this.worldPosition.getY() + 1.0D;
		double sz = this.worldPosition.getZ() + 0.5D;

		double dx = sx - highlight.getX();
		double dy = sy - highlight.getY() - highlight.getEyeHeight();
		double dz = sz - highlight.getZ();

		for (int i = 0; i < 5; i++) {
			level.addParticle(TFParticleType.GHAST_TRAP.get(), sx, sy, sz, -dx, -dy, -dz);
		}
	}

	public boolean isCharged() {
		return dyingGhasts.size() >= 3;
	}

	@Override
	public void tick() {
		if (getBlockState().getValue(GhastTrapBlock.ACTIVE)) {
			tickActive();
		} else {
			tickInactive();
		}
	}

	@Override
	public boolean triggerEvent(int event, int payload) {
		if (event == GhastTrapBlock.ACTIVATE_EVENT) {
			counter = 0;
			dyingGhasts.clear();
			return true;
		}
		if (event == GhastTrapBlock.DEACTIVATE_EVENT) {
			counter = 0;
			return true;
		}
		return false;
	}

	private void tickActive() {
		++counter;

		if (level.isClientSide) {
			// smoke when done
			if (counter > 100 && counter % 4 == 0) {
				level.addParticle(TFParticleType.HUGE_SMOKE.get(), this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.95, this.worldPosition.getZ() + 0.5, Math.cos(counter / 10.0) * 0.05, 0.25D, Math.sin(counter / 10.0) * 0.05);

			} else if (counter < 100) {

				double x = this.worldPosition.getX() + 0.5D;
				double y = this.worldPosition.getY() + 1.0D;
				double z = this.worldPosition.getZ() + 0.5D;

				double dx = Math.cos(counter / 10.0) * 2.5;
				double dy = 20D;
				double dz = Math.sin(counter / 10.0) * 2.5;

				level.addParticle(TFParticleType.GHAST_TRAP.get(), x, y, z, dx, dy, dz);
				level.addParticle(TFParticleType.GHAST_TRAP.get(), x, y, z, -dx, dy, -dz);
				level.addParticle(TFParticleType.GHAST_TRAP.get(), x, y, z, -dx, dy / 2, dz);
				level.addParticle(TFParticleType.GHAST_TRAP.get(), x, y, z, dx, dy / 2, -dz);
				level.addParticle(TFParticleType.GHAST_TRAP.get(), x, y, z, dx / 2, dy / 4, dz / 2);
				level.addParticle(TFParticleType.GHAST_TRAP.get(), x, y, z, -dx / 2, dy / 4, -dz / 2);
			}

			// appropriate sound
			if (counter < 30) {
				level.playLocalSound(worldPosition.getX() + 0.5D, worldPosition.getY() + 1.5D, worldPosition.getZ() + 0.5D, TFSounds.URGHAST_TRAP_WARMUP, SoundSource.BLOCKS, 1.0F, 4.0F, false);
			} else if (counter < 80) {
				level.playLocalSound(worldPosition.getX() + 0.5D, worldPosition.getY() + 1.5D, worldPosition.getZ() + 0.5D, TFSounds.URGHAST_TRAP_ON, SoundSource.BLOCKS, 1.0F, 4.0F, false);
			} else {
				level.playLocalSound(worldPosition.getX() + 0.5D, worldPosition.getY() + 1.5D, worldPosition.getZ() + 0.5D, TFSounds.URGHAST_TRAP_SPINDOWN, SoundSource.BLOCKS, 1.0F, 4.0F, false);
			}
		} else {
			// trap nearby ghasts
			AABB aabb = new AABB(worldPosition.above(16), worldPosition.above(16).offset(1, 1, 1)).inflate(6D, 16D, 6D);

			List<Ghast> nearbyGhasts = level.getEntitiesOfClass(Ghast.class, aabb);

			for (Ghast ghast : nearbyGhasts) {
				//stop boss tantrum
				if (ghast instanceof UrGhastEntity) {
					((UrGhastEntity) ghast).setInTantrum(false);
					((UrGhastEntity) ghast).noPhysics = true; // turn this on so we can pull it in close

					// move boss to this point
					double mx = (ghast.getX() - this.worldPosition.getX() - 0.5) * -0.1;
					double my = (ghast.getY() - this.worldPosition.getY() - 2.5) * -0.1;
					double mz = (ghast.getZ() - this.worldPosition.getZ() - 0.5) * -0.1;
					ghast.setDeltaMovement(mx, my, mz);

					if (rand.nextInt(10) == 0) {
						ghast.hurt(DamageSource.GENERIC, 7);
						((UrGhastEntity) ghast).resetDamageUntilNextPhase();
					}

				} else {
					// move ghasts to this point
					double mx = (ghast.getX() - this.worldPosition.getX() - 0.5) * -0.1;
					double my = (ghast.getY() - this.worldPosition.getY() - 1.5) * -0.1;
					double mz = (ghast.getZ() - this.worldPosition.getZ() - 0.5) * -0.1;
					ghast.setDeltaMovement(mx, my, mz);

					if (rand.nextInt(10) == 0) {
						ghast.hurt(DamageSource.GENERIC, 10);
					}
				}

				if (ghast instanceof CarminiteGhastguardEntity) {
					((CarminiteGhastguardEntity) ghast).setInTrap();
				}

			}

			if (counter >= 120) {
				level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(GhastTrapBlock.ACTIVE, false));
				level.blockEvent(getBlockPos(), getBlockState().getBlock(), GhastTrapBlock.DEACTIVATE_EVENT, 0);
			}
		}
	}
}
