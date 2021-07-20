package twilightforest.entity.ai;

import net.minecraft.entity.ai.goal.Goal;
import twilightforest.entity.passive.TinyBirdEntity;

import java.util.EnumSet;

public class TinyBirdFlyGoal extends Goal {

	private TinyBirdEntity entity;

	public TinyBirdFlyGoal(TinyBirdEntity bird) {
		this.entity = bird;
		this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
	}

	@Override
	public boolean shouldExecute() {
		return !entity.isBirdLanded();
	}
}
