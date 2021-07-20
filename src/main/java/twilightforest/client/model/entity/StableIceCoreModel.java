package twilightforest.client.model.entity;

import net.minecraft.util.math.MathHelper;
import twilightforest.entity.StableIceCoreEntity;

public class StableIceCoreModel extends UnstableIceCoreModel<StableIceCoreEntity> {

	@Override
	public void setLivingAnimations(StableIceCoreEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		this.alive = entity.isAlive();

		for (int i = 0; i < spikes.length; i++) {
			// rotate the spikes
			this.spikes[i].rotateAngleY = (3.14159F / 2F) + (MathHelper.sin((entity.ticksExisted + partialTicks) / 5.0F) * 0.5F);
			this.spikes[i].rotateAngleX = (entity.ticksExisted + partialTicks) / 5.0F;
			this.spikes[i].rotateAngleZ = MathHelper.cos(i / 5.0F) / 4.0F;

			this.spikes[i].rotateAngleX += i * (Math.PI / 8F);

			this.spikes[i].childModels.get(0).rotationPointY = 9.5F + MathHelper.sin((i + entity.ticksExisted + partialTicks) / 3F) * 3F;
		}
	}
}
