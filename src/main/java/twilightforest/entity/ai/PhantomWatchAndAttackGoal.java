package twilightforest.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Hand;
import twilightforest.entity.boss.KnightPhantomEntity;

public class PhantomWatchAndAttackGoal extends Goal {

	private final KnightPhantomEntity boss;
	private int attackTime;
	private int guardCoolDownTime;
	private boolean isGuard;

	public PhantomWatchAndAttackGoal(KnightPhantomEntity entity) {
		boss = entity;
	}

	@Override
	public boolean shouldExecute() {
		return boss.getAttackTarget() != null;
	}

	@Override
	public void tick() {
		LivingEntity target = boss.getAttackTarget();
		if (target != null) {
			boss.faceEntity(target, 10.0F, 500.0F);

			if (target.isAlive()) {
				float f1 = target.getDistance(boss);

				if (boss.getEntitySenses().canSee(target)) {
					if (attackTime-- <= 0 && f1 < 2.0F && target.getBoundingBox().maxY > boss.getBoundingBox().minY && boss.getAttackTarget().getBoundingBox().minY < boss.getBoundingBox().maxY) {
						attackTime = 20;
						boss.attackEntityAsMob(target);
					}
				}

				if (this.boss.getHeldItemOffhand().getItem() instanceof ShieldItem && boss.getCurrentFormation() != KnightPhantomEntity.Formation.ATTACK_PLAYER_ATTACK && this.isGuard) {
					this.boss.setActiveHand(Hand.OFF_HAND);
				} else {
					this.boss.resetActiveHand();
				}

				if(this.isGuard){
					if(this.guardCoolDownTime <= 180) {
						++this.guardCoolDownTime;
					}else {
						this.isGuard = false;
					}
				}else {
					if (guardCoolDownTime > 0) {
						--this.guardCoolDownTime;
					}else {
						this.isGuard = true;
					}
				}
			}
		}
	}
}