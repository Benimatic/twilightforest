package twilightforest.client.model.entity.legacy;

import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.ModelPart;
import twilightforest.entity.passive.BighornEntity;

public class BighornLegacyModel<T extends BighornEntity> extends SheepModel<T> {

	public BighornLegacyModel() {
		super();
		head = new ModelPart(this, 0, 0);
		head.addBox(-3F, -4F, -6F, 6, 6, 7, 0F);
		head.setPos(0F, 6F, -8F);

		body = new ModelPart(this, 36, 10);
		body.addBox(-4F, -9F, -7F, 8, 15, 6, 0F);
		body.setPos(0F, 5F, 2F);

		leg0 = new ModelPart(this, 0, 16);
		leg0.addBox(-2F, 0.0F, -2F, 4, 12, 4, 0F);
		leg0.setPos(-3F, 12F, 7F);

		leg1 = new ModelPart(this, 0, 16);
		leg1.addBox(-2F, 0.0F, -2F, 4, 12, 4, 0F);
		leg1.setPos(3F, 12F, 7F);

		leg2 = new ModelPart(this, 0, 16);
		leg2.addBox(-2F, 0.0F, -2F, 4, 12, 4, 0F);
		leg2.setPos(-3F, 12F, -5F);

		leg3 = new ModelPart(this, 0, 16);
		leg3.addBox(-2F, 0.0F, -2F, 4, 12, 4, 0F);
		leg3.setPos(3F, 12F, -5F);

		// curly horn 1
		head.texOffs(28, 16).addBox(-5F, -4F, -4F, 2, 2, 2, 0F);
		head.texOffs(16, 13).addBox(-6F, -5F, -3F, 2, 2, 4, 0F);
		head.texOffs(16, 19).addBox(-7F, -4F, 0F, 2, 5, 2, 0F);
		head.texOffs(18, 27).addBox(-8F, 0F, -2F, 2, 2, 3, 0F);
		head.texOffs(28, 27).addBox(-9F, -1F, -3F, 2, 2, 1, 0F);

		// curly horn 2
		head.texOffs(28, 16).addBox(3F, -4F, -4F, 2, 2, 2, 0F);
		head.texOffs(16, 13).addBox(4F, -5F, -3F, 2, 2, 4, 0F);
		head.texOffs(16, 19).addBox(5F, -4F, 0F, 2, 5, 2, 0F);
		head.texOffs(18, 27).addBox(6F, 0F, -2F, 2, 2, 3, 0F);
		head.texOffs(28, 27).addBox(7F, -1F, -3F, 2, 2, 1, 0F);
	}
}
