package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class EntityTFSwarmSpider extends SpiderEntity {
	public static final ResourceLocation LOOT_TABLE = TwilightForestMod.prefix("entities/swarm_spider");

	protected boolean shouldSpawn = false;

	public EntityTFSwarmSpider(EntityType<? extends EntityTFSwarmSpider> type, World world) {
		this(type, world, true);
	}

	public EntityTFSwarmSpider(EntityType<? extends EntityTFSwarmSpider> type, World world, boolean spawnMore) {
		super(type, world);

		setSpawnMore(spawnMore);
		experienceValue = 2;
	}

	public EntityTFSwarmSpider(EntityType<? extends EntityTFSwarmSpider> type, World world, double x, double y, double z) {
		this(type, world);
		this.setPosition(x, y, z);
	}

	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(3.0D);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

		// Remove default spider melee task
		this.goalSelector.taskEntries.removeIf(t -> t.action instanceof MeleeAttackGoal);

		// Replace with one that doesn't become docile in light
		// [VanillaCopy] based on EntitySpider.AISpiderAttack
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1, true) {
			@Override
			protected double getAttackReachSqr(LivingEntity attackTarget) {
				return 4.0F + attackTarget.getWidth();
			}
		});

		// Remove default spider target player task
		this.targetSelector.taskEntries.removeIf(t -> t.priority == 2 && t.action instanceof NearestAttackableTargetGoal);
		// Replace with one that doesn't care about light
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}

	@Override
	public float getRenderSizeModifier() {
		return 0.5F;
	}

	@Override
	public float getEyeHeight(Pose pose) {
		return 0.3F;
	}

	@Override
	public void tick() {
		if (!world.isRemote && shouldSpawnMore()) {
			int more = 1 + rand.nextInt(2);
			for (int i = 0; i < more; i++) {
				// try twice to spawn
				if (!spawnAnother()) {
					spawnAnother();
				}
			}
			setSpawnMore(false);
		}

		super.tick();
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		return rand.nextInt(4) == 0 && super.attackEntityAsMob(entity);
	}

	protected boolean spawnAnother() {
		EntityTFSwarmSpider another = new EntityTFSwarmSpider(world, false);


		double sx = getX() + (rand.nextBoolean() ? 0.9 : -0.9);
		double sy = getY();
		double sz = getZ() + (rand.nextBoolean() ? 0.9 : -0.9);
		another.setLocationAndAngles(sx, sy, sz, rand.nextFloat() * 360F, 0.0F);
		if (!another.getCanSpawnHere()) {
			another.setDead();
			return false;
		}
		world.addEntity(another);
		another.spawnExplosionParticle();

		return true;
	}

	@Override
	protected boolean isValidLightLevel() {
		int chunkX = MathHelper.floor(getX()) >> 4;
		int chunkZ = MathHelper.floor(getZ()) >> 4;
		// We're allowed to spawn in bright light only in hedge mazes.
		return TFFeature.getNearestFeature(chunkX, chunkZ, world) == TFFeature.HEDGE_MAZE || super.isValidLightLevel();
	}

	public boolean shouldSpawnMore() {
		return shouldSpawn;
	}

	public void setSpawnMore(boolean flag) {
		this.shouldSpawn = flag;
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putBoolean("SpawnMore", shouldSpawnMore());
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		setSpawnMore(compound.getBoolean("SpawnMore"));
	}

	@Override
	protected float getSoundPitch() {
		return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F;
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 16;
	}

	@Override
	protected ResourceLocation getLootTable() {
		return LOOT_TABLE;
	}
}
