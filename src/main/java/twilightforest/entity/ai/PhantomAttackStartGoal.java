package twilightforest.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import twilightforest.entity.boss.KnightPhantomEntity;

import java.util.EnumSet;

public class PhantomAttackStartGoal extends Goal {

	private final KnightPhantomEntity boss;

	public PhantomAttackStartGoal(KnightPhantomEntity entity) {
		boss = entity;
		setMutexFlags(EnumSet.of(Flag.LOOK));
	}

	@Override
	public boolean shouldExecute() {
		return boss.getAttackTarget() != null && boss.getCurrentFormation() == KnightPhantomEntity.Formation.ATTACK_PLAYER_START;
	}

	@Override
	public void tick() {
		LivingEntity target = boss.getAttackTarget();
		if (target != null) {
			BlockPos targetPos = new BlockPos(target.lastTickPosX, target.lastTickPosY, target.lastTickPosZ);

			if (boss.isWithinHomeDistanceFromPosition(targetPos)) {
				boss.setChargePos(targetPos);
			} else {
				boss.setChargePos(boss.getHomePosition());
			}
		}
	}
}