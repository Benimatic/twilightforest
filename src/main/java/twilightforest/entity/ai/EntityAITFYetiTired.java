package twilightforest.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import twilightforest.entity.boss.EntityTFYetiAlpha;

public class EntityAITFYetiTired extends EntityAIBase {

	private EntityTFYetiAlpha yeti;
	private int tiredDuration;
	private int tiredTimer;

	public EntityAITFYetiTired(EntityTFYetiAlpha entityTFYetiAlpha, int i) {
		this.yeti = entityTFYetiAlpha;
		this.tiredDuration = i;
		this.setMutexBits(5);
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
	public void updateTask() {
		this.tiredTimer++;
	}

}
