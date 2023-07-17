package twilightforest.entity.boss;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import twilightforest.entity.TFPart;

public abstract class HydraPart extends TFPart<Hydra> {

	private static final EntityDataAccessor<Boolean> DATA_SIZEACTIVE = SynchedEntityData.defineId(HydraPart.class, EntityDataSerializers.BOOLEAN);

	final float maxHealth = 1000F;
	float health = this.maxHealth;

	private EntityDimensions cacheSize;

	public HydraPart(Hydra hydra) {
		super(hydra);
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(DATA_SIZEACTIVE, true);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		super.onSyncedDataUpdated(accessor);
		if (accessor == DATA_SIZEACTIVE) {
			if (this.entityData.get(DATA_SIZEACTIVE))
				this.activate();
			else
				this.deactivate();
		}
	}

	// [VanillaCopy] from MobEntity
	public boolean canEntityBeSeen(Entity entity) {
		Vec3 vector3d = new Vec3(this.getX(), this.getEyeY(), this.getZ());
		Vec3 vector3d1 = new Vec3(entity.getX(), entity.getEyeY(), entity.getZ());
		return this.getLevel().clip(new ClipContext(vector3d, vector3d1, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() == HitResult.Type.MISS;
	}

	public HydraPart(Hydra parent, float width, float height) {
		this(parent);
		this.setSize(EntityDimensions.scalable(width, height));
		this.refreshDimensions();
	}

	@Override
	protected void setSize(EntityDimensions size) {
		super.setSize(size);
		this.cacheSize = size;
	}

	@Override
	public void tick() {
		clearFire();
		super.tick();

		if (this.hurtTime > 0)
			this.hurtTime--;

		if (this.health <= 0F)
			this.deathTime++;

	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		boolean flag = this.getParent() != null && this.getParent().attackEntityFromPart(this, source, amount);
		if (flag) {
			this.gameEvent(GameEvent.ENTITY_DAMAGE);
		}
		return flag;
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {

	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {

	}

	@Override
	public boolean is(Entity entity) {
		return this == entity || this.getParent() == entity;
	}

	@Override
	protected void setRot(float yaw, float pitch) {
		this.setYRot(yaw % 360.0F);
		this.setXRot(pitch % 360.0F);
	}

	@Override
	protected boolean canRide(Entity entityIn) {
		return false;
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	public void activate() {
		this.dimensions = this.cacheSize;
		this.entityData.set(DATA_SIZEACTIVE, true);
	}

	public void deactivate() {
		this.dimensions = EntityDimensions.scalable(0, 0);
		this.entityData.set(DATA_SIZEACTIVE, false);
	}
}
