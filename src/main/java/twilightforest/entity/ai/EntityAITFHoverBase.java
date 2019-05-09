package twilightforest.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import twilightforest.entity.boss.EntityTFSnowQueen;

public abstract class EntityAITFHoverBase extends EntityAIBase {

	protected final Class<? extends EntityLivingBase> classTarget;
	protected final EntityTFSnowQueen attacker;

	protected EntityAITFHoverBase(EntityTFSnowQueen snowQueen, Class<? extends EntityLivingBase> targetClass) {
		this.classTarget = targetClass;
		this.attacker = snowQueen;
	}

	@Override
	public void startExecuting() {
		EntityLivingBase target = this.attacker.getAttackTarget();
		if (target != null) {
			// find a spot above the player
			makeNewHoverSpot(target);
		}
	}

	/**
	 * Make a new spot to hover at!
	 */
	protected abstract void makeNewHoverSpot(EntityLivingBase target);

	// FIXME: is return value correct here?
	protected boolean isPositionOccupied(double hx, double hy, double hz) {
		float radius = this.attacker.width / 2F;
		AxisAlignedBB aabb = new AxisAlignedBB(hx - radius, hy, hz - radius, hx + radius, hy + this.attacker.height, hz + radius);
		return this.attacker.world.getCollisionBoxes(attacker, aabb).isEmpty();
	}

	/**
	 * Can the specified entity see the specified location?
	 */
	protected boolean canEntitySee(Entity entity, double dx, double dy, double dz) {
		return entity.world.rayTraceBlocks(new Vec3d(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ), new Vec3d(dx, dy, dz)) == null;
	}
}
