package twilightforest.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.vector.Vector3d;
import twilightforest.entity.EntityTFKobold;

import java.util.EnumSet;
import java.util.List;

public class EntityAITFPanicOnFlockDeath extends Goal {
	private CreatureEntity flockCreature;
	private float speed;
	private double fleeX;
	private double fleeY;
	private double fleeZ;

	private int fleeTimer;

	public EntityAITFPanicOnFlockDeath(CreatureEntity creature, float speed) {
		this.flockCreature = creature;
		this.speed = speed;
		this.setMutexFlags(EnumSet.of(Flag.MOVE));
		this.fleeTimer = 0;
	}

	@Override
	public boolean shouldExecute() {
		boolean yikes = fleeTimer > 0;

		// check if any of us is dead within 4 squares
		List<CreatureEntity> flockList = this.flockCreature.world.getEntitiesWithinAABB(this.flockCreature.getClass(), this.flockCreature.getBoundingBox().grow(4.0D, 2.0D, 4.0D));
		for (LivingEntity flocker : flockList) {
			if (flocker.deathTime > 0) {
				yikes = true;
				break;
			}
		}

		if (!yikes) {
			return false;
		} else {
			Vector3d target = RandomPositionGenerator.findRandomTarget(this.flockCreature, 5, 4);

			if (target == null) {
				return false;
			} else {
				this.fleeX = target.x;
				this.fleeY = target.y;
				this.fleeZ = target.z;
				return true;
			}
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.fleeTimer = 40;
		this.flockCreature.getNavigator().tryMoveToXYZ(this.fleeX, this.fleeY, this.fleeZ, this.speed);

		// panic flag for kobold animations
		if (flockCreature instanceof EntityTFKobold) {
			((EntityTFKobold) flockCreature).setPanicked(true);
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting() {
		return fleeTimer > 0 && !this.flockCreature.getNavigator().noPath();
	}

	/**
	 * Updates the task
	 */
	@Override
	public void tick() {
		fleeTimer--;
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		fleeTimer -= 20;

		// panic flag for kobold animations
		if (flockCreature instanceof EntityTFKobold) {
			((EntityTFKobold) flockCreature).setPanicked(false);
		}
	}
}
