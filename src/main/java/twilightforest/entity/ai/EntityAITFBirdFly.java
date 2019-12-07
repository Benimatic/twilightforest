package twilightforest.entity.ai;

import net.minecraft.entity.ai.goal.Goal;
import twilightforest.entity.passive.EntityTFTinyBird;

public class EntityAITFBirdFly extends Goal {

	private EntityTFTinyBird entity;

	public EntityAITFBirdFly(EntityTFTinyBird bird) {
		this.entity = bird;
		this.setMutexBits(5);
	}

	@Override
	public boolean shouldExecute() {
		return !entity.isBirdLanded();
	}
}
