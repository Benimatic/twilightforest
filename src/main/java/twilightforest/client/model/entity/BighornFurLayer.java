package twilightforest.client.model.entity;

import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.geom.ModelPart;
import twilightforest.entity.passive.BighornEntity;

public class BighornFurLayer extends SheepFurModel<BighornEntity> {

	public BighornFurLayer() {
		super();
		head = new ModelPart(this, 0, 0);
		head.addBox(-3F, -4F, -4F, 6, 6, 6, 0.6F);
		head.setPos(0.0F, 6F, -8F);
		body = new ModelPart(this, 28, 8);
		body.addBox(-4F, -9F, -7F, 8, 15, 6, 0.5F);
		body.setPos(0.0F, 5F, 2.0F);
		float f = 0.4F;
		leg0 = new ModelPart(this, 0, 16);
		leg0.addBox(-2F, 0.0F, -2F, 4, 6, 4, f);
		leg0.setPos(-3F, 12F, 7F);
		leg1 = new ModelPart(this, 0, 16);
		leg1.addBox(-2F, 0.0F, -2F, 4, 6, 4, f);
		leg1.setPos(3F, 12F, 7F);
		leg2 = new ModelPart(this, 0, 16);
		leg2.addBox(-2F, 0.0F, -2F, 4, 6, 4, f);
		leg2.setPos(-3F, 12F, -5F);
		leg3 = new ModelPart(this, 0, 16);
		leg3.addBox(-2F, 0.0F, -2F, 4, 6, 4, f);
		leg3.setPos(3F, 12F, -5F);
	}
}
