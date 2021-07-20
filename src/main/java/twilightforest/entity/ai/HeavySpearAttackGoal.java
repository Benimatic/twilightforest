package twilightforest.entity.ai;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.EntityPredicates;
import twilightforest.entity.UpperGoblinKnightEntity;

import java.util.EnumSet;

public class HeavySpearAttackGoal extends Goal {

	private UpperGoblinKnightEntity entity;

	public HeavySpearAttackGoal(UpperGoblinKnightEntity upperKnight) {
		this.entity = upperKnight;
		this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	@Override
	public void tick() {
		if (entity.heavySpearTimer == 25) {
			entity.landHeavySpearAttack();
		}
	}

	@Override
	public boolean shouldExecute() {
		return entity.heavySpearTimer > 0 && entity.heavySpearTimer < UpperGoblinKnightEntity.HEAVY_SPEAR_TIMER_START && EntityPredicates.CAN_HOSTILE_AI_TARGET.test(entity.getAttackTarget());
	}
}
