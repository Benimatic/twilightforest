package twilightforest.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;

//TODO: Unused?
public class EntityAITFKidnapRider extends Goal {
	private CreatureEntity theEntityCreature;
	private float speed;
	private double randPosX;
	private double randPosY;
	private double randPosZ;

	public EntityAITFKidnapRider(CreatureEntity creature, float speed) {
		this.theEntityCreature = creature;
		this.speed = speed;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		if (this.theEntityCreature.getRidingEntity() == null || this.theEntityCreature.getRNG().nextInt(5) > 0) {
			return false;
		} else {
			Vec3d target = RandomPositionGenerator.findRandomTarget(this.theEntityCreature, 5, 4);

			if (target == null) {
				return false;
			} else {
				this.randPosX = target.x;
				this.randPosY = target.y;
				this.randPosZ = target.z;
				return true;
			}
		}
	}

	@Override
	public void startExecuting() {
		this.theEntityCreature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
	}

	@Override
	public boolean shouldContinueExecuting() {
		return !this.theEntityCreature.getNavigator().noPath();
	}
}
