package twilightforest.tileentity.spawner;

import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Difficulty;

public abstract class TileEntityTFBossSpawner extends TileEntity implements ITickableTileEntity {

	protected static final int SHORT_RANGE = 9, LONG_RANGE = 50;

	protected final ResourceLocation mobID;
	protected Entity displayCreature = null;
	protected boolean spawnedBoss = false;

	protected TileEntityTFBossSpawner(TileEntityType<?> type, ResourceLocation mobID) {
		super(type);
		this.mobID = mobID;
	}

	public boolean anyPlayerInRange() {
		return world.isPlayerWithin(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, getRange());
	}

	@Override
	public void tick() {
		if (spawnedBoss || !anyPlayerInRange()) {
			return;
		}
		if (world.isRemote) {
			// particles
			double rx = pos.getX() + world.rand.nextFloat();
			double ry = pos.getY() + world.rand.nextFloat();
			double rz = pos.getZ() + world.rand.nextFloat();
			world.addParticle(ParticleTypes.SMOKE, rx, ry, rz, 0.0D, 0.0D, 0.0D);
			world.addParticle(ParticleTypes.FLAME, rx, ry, rz, 0.0D, 0.0D, 0.0D);
		} else {
			if (world.getDifficulty() != Difficulty.PEACEFUL) {
				if (spawnMyBoss()) {
					world.destroyBlock(pos, false);
					spawnedBoss = true;
				}
			}
		}
	}

	/**
	 * Spawn the boss
	 */
	protected boolean spawnMyBoss() {
		// create creature
		LivingEntity myCreature = makeMyCreature();

		myCreature.moveToBlockPosAndAngles(pos, world.rand.nextFloat() * 360F, 0.0F);
		myCreature.onInitialSpawn(world.getDifficultyForLocation(pos), null);

		// set creature's home to this
		initializeCreature(myCreature);

		// spawn it
		return world.addEntity(myCreature);
	}

	/**
	 * Get a temporary copy of the creature we're going to summon for display purposes
	 */
	public Entity getDisplayEntity() {
		if (this.displayCreature == null) {
			this.displayCreature = makeMyCreature();
		}
		return this.displayCreature;
	}

	/**
	 * Any post-creation initialization goes here
	 */
	protected void initializeCreature(LivingEntity myCreature) {
		if (myCreature instanceof CreatureEntity) {
			((CreatureEntity) myCreature).setHomePosAndDistance(pos, 46);
		}
	}

	protected int getRange() {
		return SHORT_RANGE;
	}

	protected LivingEntity makeMyCreature() {
		return (LivingEntity) EntityList.createEntityByIDFromName(mobID, world);
	}
}
