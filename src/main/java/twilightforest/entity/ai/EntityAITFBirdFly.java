package twilightforest.entity.ai;

import net.minecraft.entity.ai.goal.Goal;
import twilightforest.entity.passive.EntityTFTinyBird;

import java.util.EnumSet;

public class EntityAITFBirdFly extends Goal {

	private EntityTFTinyBird entity;

	public EntityAITFBirdFly(EntityTFTinyBird bird) {
		this.entity = bird;
		this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
	}

	@Override
	public boolean shouldExecute() {
		return !entity.isBirdLanded();
	}
}
