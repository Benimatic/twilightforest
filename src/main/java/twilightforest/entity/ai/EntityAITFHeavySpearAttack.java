package twilightforest.entity.ai;

import net.minecraft.entity.ai.goal.Goal;
import twilightforest.entity.EntityTFGoblinKnightUpper;

import java.util.EnumSet;

public class EntityAITFHeavySpearAttack extends Goal {

	private EntityTFGoblinKnightUpper entity;

	public EntityAITFHeavySpearAttack(EntityTFGoblinKnightUpper upperKnight) {
		this.entity = upperKnight;
		this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK)); // Prevent moving TODO 1.15: check this again, I'm uncertain
	}

	@Override
	public void tick() {
		if (entity.heavySpearTimer == 25) {
			entity.landHeavySpearAttack();
		}
	}

	@Override
	public boolean shouldExecute() {
		return entity.heavySpearTimer > 0 && entity.heavySpearTimer < EntityTFGoblinKnightUpper.HEAVY_SPEAR_TIMER_START;
	}
}
