package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityTFCharmEffect extends Entity {
	private static final DataParameter<Integer> DATA_OWNER = EntityDataManager.createKey(EntityTFCharmEffect.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> DATA_ITEMID = EntityDataManager.createKey(EntityTFCharmEffect.class, DataSerializers.VARINT);
	private static final double DISTANCE = 1.75;
	private double interpTargetX;
	private double interpTargetY;
	private double interpTargetZ;
	private double interpTargetYaw;
	private double interpTargetPitch;
	private int newPosRotationIncrements;

	public float offset;

	public EntityTFCharmEffect(EntityType<? extends EntityTFCharmEffect> type, World world) {
		super(type, world);
	}

	public EntityTFCharmEffect(EntityType<? extends EntityTFCharmEffect> type, World world, LivingEntity owner, Item item) {
		this(type, world);

		this.setOwner(owner);
		this.setItemID(item);

		Vec3d look = new Vec3d(DISTANCE, 0, 0);

		this.setLocationAndAngles(owner.getX(), owner.getY() + owner.getEyeHeight(), owner.getZ(), owner.rotationYaw, owner.rotationPitch);
		this.getX() += look.x * DISTANCE;
		//this.getY() += look.y * DISTANCE;
		this.getZ() += look.z * DISTANCE;
		this.setPosition(this.getX(), this.getY(), this.getZ());
	}

	@Override
	public void tick() {
		this.lastTickPosX = this.getX();
		this.lastTickPosY = this.getY();
		this.lastTickPosZ = this.getZ();
		super.tick();

		//[VanillaCopy] Beginning of LivingEntity.onLivingUpdate
		if (this.newPosRotationIncrements > 0) {
			double d0 = this.getX() + (this.interpTargetX - this.getX()) / (double) this.newPosRotationIncrements;
			double d1 = this.getY() + (this.interpTargetY - this.getY()) / (double) this.newPosRotationIncrements;
			double d2 = this.getZ() + (this.interpTargetZ - this.getZ()) / (double) this.newPosRotationIncrements;
			double d3 = MathHelper.wrapDegrees(this.interpTargetYaw - (double) this.rotationYaw);
			this.rotationYaw = (float) ((double) this.rotationYaw + d3 / (double) this.newPosRotationIncrements);
			this.rotationPitch = (float) ((double) this.rotationPitch + (this.interpTargetPitch - (double) this.rotationPitch) / (double) this.newPosRotationIncrements);
			--this.newPosRotationIncrements;
			this.setPosition(d0, d1, d2);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		}

		LivingEntity orbiting = getOwner();

		if (orbiting != null) {
			this.setLocationAndAngles(orbiting.getX(), orbiting.getY() + orbiting.getEyeHeight(), orbiting.getZ(), orbiting.rotationYaw, orbiting.rotationPitch);

			float rotation = this.ticksExisted / 5.0F + offset;
			Vec3d look = new Vec3d(DISTANCE, 0, 0).rotateYaw(rotation);
			this.getX() += look.x;
//        	this.getY() += Math.sin(this.ticksExisted / 3.0F + offset);
			this.getZ() += look.z;

			this.setPosition(this.getX(), this.getY(), this.getZ());
		}

		if (this.getItemID() > -1) {
			for (int i = 0; i < 3; i++) {
				double dx = getX() + 0.5 * (rand.nextDouble() - rand.nextDouble());
				double dy = getY() + 0.5 * (rand.nextDouble() - rand.nextDouble());
				double dz = getZ() + 0.5 * (rand.nextDouble() - rand.nextDouble());

				world.addParticle(ParticleTypes.ITEM_CRACK, dx, dy, dz, 0, 0.2, 0, getItemID());
			}
		}

		if (!this.world.isRemote
				&& (this.ticksExisted > 200 || (orbiting != null && orbiting.isDead))) {
			this.setDead();
		}
	}

	@Override
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
		this.interpTargetX = x;
		this.interpTargetY = y;
		this.interpTargetZ = z;
		this.interpTargetYaw = yaw;
		this.interpTargetPitch = pitch;
		this.newPosRotationIncrements = posRotationIncrements;
	}

	@Override
	protected void registerData() {
		dataManager.register(DATA_ITEMID, -1);
		dataManager.register(DATA_OWNER, -1);
	}

	public void setOwner(LivingEntity owner) {
		dataManager.set(DATA_OWNER, owner.getEntityId());
	}

	@Nullable
	public LivingEntity getOwner() {
		Entity e = this.world.getEntityByID(dataManager.get(DATA_OWNER));
		if (e instanceof LivingEntity)
			return (LivingEntity) e;
		else return null;
	}

	public int getItemID() {
		return dataManager.get(DATA_ITEMID);
	}

	public void setItemID(Item item) {
		dataManager.set(DATA_ITEMID, Item.getIdFromItem(item));
	}

	@Override
	protected void readAdditional(CompoundNBT cmp) {}

	@Override
	protected void writeAdditional(CompoundNBT cmp) {}

}
