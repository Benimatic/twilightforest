package twilightforest.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import twilightforest.entity.EntityTFRedcap;

public class EntityAITFRedcapShy extends EntityAITFRedcapBase {

	private EntityLivingBase entityTarget;
	private final float speed;
	private final boolean lefty = Math.random() < 0.5;
	private double targetX;
	private double targetY;
	private double targetZ;

	private static final double minDistance = 3.0;
	private static final double maxDistance = 6.0;

	public EntityAITFRedcapShy(EntityTFRedcap entityTFRedcap, float moveSpeed) {
		super(entityTFRedcap);
		this.speed = moveSpeed;
		this.setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {
		EntityLivingBase attackTarget = this.redcap.getAttackTarget();

		if (attackTarget == null
				|| !this.redcap.isShy()
				|| attackTarget.getDistance(redcap) > maxDistance
				|| attackTarget.getDistance(redcap) < minDistance
				|| !isTargetLookingAtMe(attackTarget)) {
			return false;
		} else {
			this.entityTarget = attackTarget;
			Vec3d avoidPos = findCirclePoint(redcap, entityTarget, 5, lefty ? 1 : -1);

			this.targetX = avoidPos.x;
			this.targetY = avoidPos.y;
			this.targetZ = avoidPos.z;
			return true;
		}

	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.redcap.getNavigator().tryMoveToXYZ(this.targetX, this.targetY, this.targetZ, this.speed);
	}

	@Override
	public boolean shouldContinueExecuting() {
		EntityLivingBase attackTarget = this.redcap.getAttackTarget();

		if (attackTarget == null) {
			return false;
		} else if (!this.entityTarget.isEntityAlive()) {
			return false;
		} else if (this.redcap.getNavigator().noPath()) {
			return false;
		}

		return redcap.isShy() && attackTarget.getDistance(redcap) < maxDistance && attackTarget.getDistance(redcap) > minDistance && isTargetLookingAtMe(attackTarget);
	}

	@Override
	public void updateTask() {
		this.redcap.getLookHelper().setLookPositionWithEntity(this.entityTarget, 30.0F, 30.0F);
	}

	@Override
	public void resetTask() {
		this.entityTarget = null;
		this.redcap.getNavigator().clearPath();
	}

	private Vec3d findCirclePoint(Entity circler, Entity toCircle, double radius, double rotation) {
		// compute angle
		double vecx = circler.posX - toCircle.posX;
		double vecz = circler.posZ - toCircle.posZ;
		float rangle = (float) (Math.atan2(vecz, vecx));

		// add a little, so he circles
		rangle += rotation;

		// figure out where we're headed from the target angle
		double dx = MathHelper.cos(rangle) * radius;
		double dz = MathHelper.sin(rangle) * radius;

		// add that to the target entity's position, and we have our destination
		return new Vec3d(toCircle.posX + dx, circler.getEntityBoundingBox().minY, toCircle.posZ + dz);
	}

}
