package twilightforest.entity.ai;

import net.minecraft.entity.ai.goal.Goal;
import twilightforest.entity.EntityTFGoblinKnightUpper;

public class EntityAITFHeavySpearAttack extends Goal {

	private EntityTFGoblinKnightUpper entity;

	public EntityAITFHeavySpearAttack(EntityTFGoblinKnightUpper upperKnight) {
		this.entity = upperKnight;
		this.setMutexBits(3); // Prevent moving
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
