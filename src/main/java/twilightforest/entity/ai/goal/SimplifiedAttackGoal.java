package twilightforest.entity.ai.goal;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import twilightforest.entity.boss.Naga;
import twilightforest.entity.monster.Wraith;

public class SimplifiedAttackGoal extends Goal {

	private final Mob mob;
	private int attackTick;

	public SimplifiedAttackGoal(Mob mob) {
		this.mob = mob;
	}

	@Override
	public boolean canUse() {
		LivingEntity target = mob.getTarget();
		return target != null && this.getAttackReachSqr(target) >= this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void start() {
		this.attackTick = 0;
	}

	@Override
	public void stop() {
		this.attackTick = 0;
	}

	@Override
	public void tick() {
		if (this.attackTick > 0) {
			this.attackTick--;
		} else {
			LivingEntity livingentity = this.mob.getTarget();
			if (livingentity == null) {
				this.stop();
				return;
			}
			double d0 = this.mob.getPerceivedTargetDistanceSquareForMeleeAttack(livingentity);
			this.checkAndPerformAttack(livingentity, d0);
		}
	}

	protected void checkAndPerformAttack(LivingEntity entity, double distance) {
		double d0 = this.getAttackReachSqr(entity);
		if (distance <= d0) {
			this.attackTick = this.adjustedTickDelay(20);
			this.mob.swing(InteractionHand.MAIN_HAND);
			this.mob.doHurtTarget(entity);
		}

	}

	protected double getAttackReachSqr(LivingEntity entity) {
		return this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + entity.getBbWidth();
	}
}