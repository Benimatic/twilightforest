package twilightforest.tileentity.spawner;

import net.minecraft.entity.*;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;

public abstract class BossSpawnerTileEntity<T extends Mob> extends BlockEntity implements TickableBlockEntity {

	protected static final int SHORT_RANGE = 9, LONG_RANGE = 50;

	protected final EntityType<T> entityType;
	protected Entity displayCreature = null;
	protected boolean spawnedBoss = false;

	protected BossSpawnerTileEntity(BlockEntityType<?> type, EntityType<T> entityType) {
		super(type);
		this.entityType = entityType;
	}

	public boolean anyPlayerInRange() {
		return level.hasNearbyAlivePlayer(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D, getRange());
	}

	@Override
	public void tick() {
		if (spawnedBoss || !anyPlayerInRange()) {
			return;
		}
		if (level.isClientSide) {
			// particles
			double rx = worldPosition.getX() + level.random.nextFloat();
			double ry = worldPosition.getY() + level.random.nextFloat();
			double rz = worldPosition.getZ() + level.random.nextFloat();
			level.addParticle(ParticleTypes.SMOKE, rx, ry, rz, 0.0D, 0.0D, 0.0D);
			level.addParticle(ParticleTypes.FLAME, rx, ry, rz, 0.0D, 0.0D, 0.0D);
		} else {
			if (level.getDifficulty() != Difficulty.PEACEFUL) {
				if (spawnMyBoss((ServerLevel)level)) {
					level.destroyBlock(worldPosition, false);
					spawnedBoss = true;
				}
			}
		}
	}

	protected boolean spawnMyBoss(ServerLevelAccessor world) {
		// create creature
		T myCreature = makeMyCreature();

		myCreature.moveTo(worldPosition, world.getLevel().random.nextFloat() * 360F, 0.0F);
		myCreature.finalizeSpawn(world, world.getCurrentDifficultyAt(worldPosition), MobSpawnType.SPAWNER, null, null);

		// set creature's home to this
		initializeCreature(myCreature);

		// spawn it
		return world.addFreshEntity(myCreature);
	}

	protected void initializeCreature(T myCreature) {
		myCreature.restrictTo(worldPosition, 46);
	}

	protected int getRange() {
		return SHORT_RANGE;
	}

	protected T makeMyCreature() {
		return entityType.create(level);
	}
}
