package twilightforest.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
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

public class ActiveGhastTrapTileEntity extends TileEntity implements ITickableTileEntity {

	private int counter = 0;
	private final List<CarminiteGhastlingEntity> dyingGhasts = new ArrayList<>();
	private final Random rand = new Random();

	public ActiveGhastTrapTileEntity() {
		super(TFTileEntities.GHAST_TRAP.get());
	}

	private void tickInactive() {
		// check to see if there are any dying mini ghasts within our scan range
		AxisAlignedBB aabb = new AxisAlignedBB(pos).grow(10D, 16D, 10D);

		List<CarminiteGhastlingEntity> nearbyGhasts = world.getEntitiesWithinAABB(CarminiteGhastlingEntity.class, aabb);

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

		if (this.world.isRemote) {
			// occasionally make a redstone line to a mini ghast
			if (this.counter % 20 == 0 && nearbyGhasts.size() > 0) {
				CarminiteGhastlingEntity highlight = nearbyGhasts.get(rand.nextInt(nearbyGhasts.size()));
				this.makeParticlesTo(highlight);
			}

			if (chargeLevel >= 1 && counter % 10 == 0) {
				TFBlocks.ghast_trap.get().sparkle(world, this.pos);
				world.playSound(this.pos.getX() + 0.5D, this.pos.getY() + 1.5D, this.pos.getZ() + 0.5D, SoundEvents.BLOCK_NOTE_BLOCK_HARP, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			}
			if (chargeLevel >= 2) {
				world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.1 + rand.nextFloat() * 0.8, pos.getY() + 1.05, pos.getZ() + 0.1 + rand.nextFloat() * 0.8, (rand.nextFloat() - rand.nextFloat()) * 0.05, 0.00, (rand.nextFloat() - rand.nextFloat()) * 0.05);
				if (counter % 10 == 0) {
					world.playSound(this.pos.getX() + 0.5D, this.pos.getY() + 1.5D, this.pos.getZ() + 0.5D, SoundEvents.BLOCK_NOTE_BLOCK_HARP, SoundCategory.BLOCKS, 1.2F, 0.8F, false);
				}
			}
			if (chargeLevel >= 3) {
				world.addParticle(ParticleTypes.LARGE_SMOKE, this.pos.getX() + 0.1 + rand.nextFloat() * 0.8, this.pos.getY() + 1.05, this.pos.getZ() + 0.1 + rand.nextFloat() * 0.8, (rand.nextFloat() - rand.nextFloat()) * 0.05, 0.05, (rand.nextFloat() - rand.nextFloat()) * 0.05);
				TFBlocks.ghast_trap.get().sparkle(world, this.pos);
				if (counter % 5 == 0) {
					world.playSound(this.pos.getX() + 0.5D, this.pos.getY() + 1.5D, this.pos.getZ() + 0.5D, SoundEvents.BLOCK_NOTE_BLOCK_HARP, SoundCategory.BLOCKS, 1.5F, 2F, false);
				}
			}
		}
	}

	private void makeParticlesTo(Entity highlight) {

		double sx = this.pos.getX() + 0.5D;
		double sy = this.pos.getY() + 1.0D;
		double sz = this.pos.getZ() + 0.5D;

		double dx = sx - highlight.getPosX();
		double dy = sy - highlight.getPosY() - highlight.getEyeHeight();
		double dz = sz - highlight.getPosZ();

		for (int i = 0; i < 5; i++) {
			world.addParticle(TFParticleType.GHAST_TRAP.get(), sx, sy, sz, -dx, -dy, -dz);
		}
	}

	public boolean isCharged() {
		return dyingGhasts.size() >= 3;
	}

	@Override
	public void tick() {
		if (getBlockState().get(GhastTrapBlock.ACTIVE)) {
			tickActive();
		} else {
			tickInactive();
		}
	}

	@Override
	public boolean receiveClientEvent(int event, int payload) {
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

		if (world.isRemote) {
			// smoke when done
			if (counter > 100 && counter % 4 == 0) {
				world.addParticle(TFParticleType.HUGE_SMOKE.get(), this.pos.getX() + 0.5, this.pos.getY() + 0.95, this.pos.getZ() + 0.5, Math.cos(counter / 10.0) * 0.05, 0.25D, Math.sin(counter / 10.0) * 0.05);

			} else if (counter < 100) {

				double x = this.pos.getX() + 0.5D;
				double y = this.pos.getY() + 1.0D;
				double z = this.pos.getZ() + 0.5D;

				double dx = Math.cos(counter / 10.0) * 2.5;
				double dy = 20D;
				double dz = Math.sin(counter / 10.0) * 2.5;

				world.addParticle(TFParticleType.GHAST_TRAP.get(), x, y, z, dx, dy, dz);
				world.addParticle(TFParticleType.GHAST_TRAP.get(), x, y, z, -dx, dy, -dz);
				world.addParticle(TFParticleType.GHAST_TRAP.get(), x, y, z, -dx, dy / 2, dz);
				world.addParticle(TFParticleType.GHAST_TRAP.get(), x, y, z, dx, dy / 2, -dz);
				world.addParticle(TFParticleType.GHAST_TRAP.get(), x, y, z, dx / 2, dy / 4, dz / 2);
				world.addParticle(TFParticleType.GHAST_TRAP.get(), x, y, z, -dx / 2, dy / 4, -dz / 2);
			}

			// appropriate sound
			if (counter < 30) {
				world.playSound(pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, TFSounds.URGHAST_TRAP_WARMUP, SoundCategory.BLOCKS, 1.0F, 4.0F, false);
			} else if (counter < 80) {
				world.playSound(pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, TFSounds.URGHAST_TRAP_ON, SoundCategory.BLOCKS, 1.0F, 4.0F, false);
			} else {
				world.playSound(pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, TFSounds.URGHAST_TRAP_SPINDOWN, SoundCategory.BLOCKS, 1.0F, 4.0F, false);
			}
		} else {
			// trap nearby ghasts
			AxisAlignedBB aabb = new AxisAlignedBB(pos.up(16), pos.up(16).add(1, 1, 1)).grow(6D, 16D, 6D);

			List<GhastEntity> nearbyGhasts = world.getEntitiesWithinAABB(GhastEntity.class, aabb);

			for (GhastEntity ghast : nearbyGhasts) {
				//stop boss tantrum
				if (ghast instanceof UrGhastEntity) {
					((UrGhastEntity) ghast).setInTantrum(false);
					((UrGhastEntity) ghast).noClip = true; // turn this on so we can pull it in close

					// move boss to this point
					double mx = (ghast.getPosX() - this.pos.getX() - 0.5) * -0.1;
					double my = (ghast.getPosY() - this.pos.getY() - 2.5) * -0.1;
					double mz = (ghast.getPosZ() - this.pos.getZ() - 0.5) * -0.1;
					ghast.setMotion(mx, my, mz);

					if (rand.nextInt(10) == 0) {
						ghast.attackEntityFrom(DamageSource.GENERIC, 7);
						((UrGhastEntity) ghast).resetDamageUntilNextPhase();
					}

				} else {
					// move ghasts to this point
					double mx = (ghast.getPosX() - this.pos.getX() - 0.5) * -0.1;
					double my = (ghast.getPosY() - this.pos.getY() - 1.5) * -0.1;
					double mz = (ghast.getPosZ() - this.pos.getZ() - 0.5) * -0.1;
					ghast.setMotion(mx, my, mz);

					if (rand.nextInt(10) == 0) {
						ghast.attackEntityFrom(DamageSource.GENERIC, 10);
					}
				}

				if (ghast instanceof CarminiteGhastguardEntity) {
					((CarminiteGhastguardEntity) ghast).setInTrap();
				}

			}

			if (counter >= 120) {
				world.setBlockState(getPos(), getBlockState().with(GhastTrapBlock.ACTIVE, false));
				world.addBlockEvent(getPos(), getBlockState().getBlock(), GhastTrapBlock.DEACTIVATE_EVENT, 0);
			}
		}
	}
}
