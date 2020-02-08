package twilightforest.entity.ai;

import net.minecraft.entity.ai.goal.Goal;
import twilightforest.TFSounds;
import twilightforest.entity.boss.EntityTFYetiAlpha;

import java.util.EnumSet;

public class EntityAITFYetiTired extends Goal {

	private EntityTFYetiAlpha yeti;
	private int tiredDuration;
	private int tiredTimer;

	public EntityAITFYetiTired(EntityTFYetiAlpha entityTFYetiAlpha, int i) {
		this.yeti = entityTFYetiAlpha;
		this.tiredDuration = i;
		this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
	}

	@Override
	public boolean shouldExecute() {
		return this.yeti.isTired();
	}

	@Override
	public boolean shouldContinueExecuting() {
		return this.tiredTimer < this.tiredDuration;
	}

	@Override
	public boolean isInterruptible() {
		return false;
	}

	@Override
	public void startExecuting() {
		this.tiredTimer = 0;
	}

	@Override
	public void resetTask() {
		this.tiredTimer = 0;
		this.yeti.setTired(false);
	}

	@Override
	public void tick() {
		if(++this.tiredTimer % 10 == 0)
			this.yeti.playSound(TFSounds.ALPHAYETI_PANT, 4F, 0.5F + yeti.getRNG().nextFloat() * 0.5F);
	}
}
