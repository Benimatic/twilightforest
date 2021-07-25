package twilightforest.client.model.entity.legacy;

import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelPart;
import twilightforest.entity.passive.BoarEntity;

public class BoarLegacyModel<T extends BoarEntity> extends PigModel<T> {
	public BoarLegacyModel() {
		super();

		head = new ModelPart(this, 0, 0);
		head.addBox(-4F, -2F, -6F, 8, 7, 6, 0F);
		head.setPos(0F, 12F, -6F);

		body = new ModelPart(this, 28, 10);
		body.addBox(-5F, -8F, -7F, 10, 14, 8, 0F);
		body.setPos(0F, 11F, 2F);

		body.xRot = 1.570796F;

		leg0 = new ModelPart(this, 0, 16);
		leg0.addBox(-2F, 0F, -2F, 4, 6, 4, 0F);
		leg0.setPos(-3F, 18F, 7F);

		leg1 = new ModelPart(this, 0, 16);
		leg1.addBox(-2F, 0F, -2F, 4, 6, 4, 0F);
		leg1.setPos(3F, 18F, 7F);

		leg2 = new ModelPart(this, 0, 16);
		leg2.addBox(-2F, 0F, -2F, 4, 6, 4, 0F);
		leg2.setPos(-3F, 18F, -5F);

		leg3 = new ModelPart(this, 0, 16);
		leg3.addBox(-2F, 0F, -2F, 4, 6, 4, 0F);
		leg3.setPos(3F, 18F, -5F);

		head.texOffs(28, 0).addBox(-3F, 1F, -9F, 6, 4, 3, 0F); // snout

		head.texOffs(17, 17).addBox(3F, 2F, -9F, 1, 2, 1, 0F); // tusk1
		head.texOffs(17, 17).addBox(-4F, 2F, -9F, 1, 2, 1, 0F); // tusk2
	}
}
