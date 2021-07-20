package twilightforest.entity.ai;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.EntityPredicates;
import twilightforest.entity.LowerGoblinKnightEntity;
import twilightforest.entity.UpperGoblinKnightEntity;

import java.util.EnumSet;

public class RiderSpearAttackGoal extends Goal {

	private LowerGoblinKnightEntity entity;

	public RiderSpearAttackGoal(LowerGoblinKnightEntity lowerKnight) {
		this.entity = lowerKnight;
		this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK)); // Prevent moving
	}

	@Override
	public boolean shouldExecute() {
		if (!this.entity.getPassengers().isEmpty() && this.entity.getPassengers().get(0) instanceof UpperGoblinKnightEntity && EntityPredicates.CAN_HOSTILE_AI_TARGET.test(entity.getAttackTarget())) {
			int timer = ((UpperGoblinKnightEntity) this.entity.getPassengers().get(0)).heavySpearTimer;
			return timer > 0 && timer < UpperGoblinKnightEntity.HEAVY_SPEAR_TIMER_START;
		} else {
			return false;
		}
	}
}
