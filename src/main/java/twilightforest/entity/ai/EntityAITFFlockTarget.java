package twilightforest.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;

import java.util.ArrayList;
import java.util.List;

public class EntityAITFFlockTarget extends TargetGoal {

	private final LivingEntity flockCreature;
	private LivingEntity flockTarget;

	public EntityAITFFlockTarget(CreatureEntity creature, boolean checkSight) {
		super(creature, false);
		this.flockCreature = creature;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		List<LivingEntity> flockList = this.flockCreature.world.getEntitiesWithinAABB(this.flockCreature.getClass(), this.flockCreature.getBoundingBox().grow(16.0D, 4.0D, 16.0D));
		List<LivingEntity> targetList = new ArrayList<LivingEntity>();

		for (LivingEntity flocker : flockList) {
			if (flocker.getRevengeTarget() != null) {
				targetList.add(flocker.getRevengeTarget());
			}
		}

		if (targetList.isEmpty()) {
			return false;
		} else {
			// hmm, just pick a random target?
			this.flockTarget = targetList.get(this.flockCreature.world.rand.nextInt(targetList.size()));
			return this.isSuitableTarget(this.flockTarget, true);
		}
	}

	@Override
	public void startExecuting() {
		this.taskOwner.setAttackTarget(this.flockTarget);
		super.startExecuting();
	}

	@Override
	public void resetTask() {
		this.flockTarget = null;
		super.resetTask();
	}
}
