package twilightforest.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import twilightforest.entity.boss.EntityTFKnightPhantom;

import java.util.EnumSet;

public class EntityAIPhantomAttackStart extends Goal {

	private final EntityTFKnightPhantom boss;

	public EntityAIPhantomAttackStart(EntityTFKnightPhantom entity) {
		boss = entity;
		setMutexFlags(EnumSet.of(Flag.LOOK));
	}

	@Override
	public boolean shouldExecute() {
		return boss.getAttackTarget() != null && boss.getCurrentFormation() == EntityTFKnightPhantom.Formation.ATTACK_PLAYER_START;
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