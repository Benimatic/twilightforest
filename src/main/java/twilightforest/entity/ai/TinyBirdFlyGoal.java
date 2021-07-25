package twilightforest.entity.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import twilightforest.entity.passive.TinyBirdEntity;

import java.util.EnumSet;

import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class TinyBirdFlyGoal extends Goal {

	private TinyBirdEntity entity;

	public TinyBirdFlyGoal(TinyBirdEntity bird) {
		this.entity = bird;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
	}

	@Override
	public boolean canUse() {
		return !entity.isBirdLanded();
	}
}
