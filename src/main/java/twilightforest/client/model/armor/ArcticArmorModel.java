package twilightforest.client.model.armor;

import net.minecraft.client.model.geom.ModelPart;

public class ArcticArmorModel extends TFArmorModel {

	public ArcticArmorModel(float expand) {
		super(expand);

		ModelPart rightHood = new ModelPart(this, 0, 0);
		rightHood.addBox(-1.0F, -2.0F, -1.0F, 1, 4, 1, expand);
		rightHood.setPos(-2.5F, -3.0F, -5.0F);
		this.head.addChild(rightHood);

		ModelPart leftHood = new ModelPart(this, 0, 0);
		leftHood.addBox(0.0F, -2.0F, -1.0F, 1, 4, 1, expand);
		leftHood.setPos(2.5F, -3.0F, -5.0F);
		this.head.addChild(leftHood);

		ModelPart topHood = new ModelPart(this, 24, 0);
		topHood.addBox(-2.0F, -1.0F, -1.0F, 4, 1, 1, expand);
		topHood.setPos(0.0F, -5.5F, -5.0F);
		this.head.addChild(topHood);

		ModelPart bottomHood = new ModelPart(this, 24, 0);
		bottomHood.addBox(-2.0F, -1.0F, -1.0F, 4, 1, 1, expand);
		bottomHood.setPos(0.0F, 0.5F, -5.0F);
		this.head.addChild(bottomHood);
	}
}
