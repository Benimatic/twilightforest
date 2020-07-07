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
			double dx = this.getX() - this.parentEntity.getPosX();
			double dy = this.getY() - this.parentEntity.getPosY();
			double dz = this.getZ() - this.parentEntity.getPosZ();
			double dist = dx * dx + dy * dy + dz * dz;

			if (this.courseChangeCooldown-- <= 0) {
				this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
				dist = MathHelper.sqrt(dist);

				this.parentEntity.setMotion(this.parentEntity.getMotion().add((dx / dist * 0.1D) * speed, (dy / dist * 0.1D) * speed, (dz / dist * 0.1D) * speed));
			}
		}
	}
}
