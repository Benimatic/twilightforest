package twilightforest.client.model.entity;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import twilightforest.entity.HarbingerCubeEntity;

public class HarbingerCubeModel<T extends HarbingerCubeEntity> extends QuadrupedModel<T> {

	public HarbingerCubeModel() {
		this(0.0F);
	}

	public HarbingerCubeModel(float fNumber) {
		super(6, fNumber, false, 0.0F, 0.0F, 0.0F, 0.0F, 4); //All this is from AgeableModel. Do we scale?

		this.texWidth = 128;
		this.texHeight = 64;

		this.head = new ModelPart(this, 0, 0);

		body = new ModelPart(this, 0, 0);
		body.addBox(-16F, -16F, -16F, 32, 32, 32);
		body.setPos(0F, 0F, -2F);

        leg0 = new ModelPart(this, 0, 0);
        leg0.addBox(-4F, 0F, -4F, 8, 8, 8);
        leg0.setPos(-6F, 16F, 9F);

        leg1 = new ModelPart(this, 0, 0);
        leg1.addBox(-4F, 0F, -4F, 8, 8, 8);
        leg1.setPos(6F, 16F, 9F);

        leg2 = new ModelPart(this, 0, 0);
        leg2.addBox(-4F, 0F, -4F, 8, 8, 8);
        leg2.setPos(-9F, 16F, -14F);

        leg3 = new ModelPart(this, 0, 0);
        leg3.addBox(-4F, 0F, -4F, 8, 8, 8);
        leg3.setPos(9F, 16F, -14F);
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
	 * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
	 * "far" arms and legs can swing at most.
	 */
	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		this.body.xRot = 0F;
	}
}
