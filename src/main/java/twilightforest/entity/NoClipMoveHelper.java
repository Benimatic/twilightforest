package twilightforest.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.MathHelper;

public class NoClipMoveHelper extends MovementController {
	private final LivingEntity parentEntity;
	private int courseChangeCooldown;

	public NoClipMoveHelper(MobEntity entity) {
		super(entity);
		this.parentEntity = entity;
	}

	@Override
	public void tick() {
		if (this.action == MovementController.Action.MOVE_TO) {
			double d0 = this.getX() - this.parentEntity.getX();
			double d1 = this.getY() - this.parentEntity.getY();
			double d2 = this.getZ() - this.parentEntity.getZ();
			double d3 = d0 * d0 + d1 * d1 + d2 * d2;

			if (this.courseChangeCooldown-- <= 0) {
				this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
				d3 = (double) MathHelper.sqrt(d3);

//				this.parentEntity.motionX += (d0 / d3 * 0.1D) * speed;
//				this.parentEntity.motionY += (d1 / d3 * 0.1D) * speed;
//				this.parentEntity.motionZ += (d2 / d3 * 0.1D) * speed;
				this.parentEntity.getMotion().add((d0 / d3 * 0.1D) * speed, (d1 / d3 * 0.1D) * speed, (d2 / d3 * 0.1D) * speed);
			}
		}
	}
}
