package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

public class FireflyModel extends ListModel<Entity> {
	public FireflyModel() {
		legs = new ModelPart(this, 0, 21);
		legs.addBox(-4F, 7.9F, -5F, 8, 1, 10, 0F);
		//legs.setRotationPoint(0F, 16F, 0F);

		fatbody = new ModelPart(this, 0, 11);
		fatbody.addBox(-2F, 6F, -4F, 4, 2, 6, 0F);
		//fatbody.setRotationPoint(0F, 16F, 0F);

		skinnybody = new ModelPart(this, 0, 0);
		skinnybody.addBox(-1F, 7F, -5F, 2, 1, 8, 0F);
		//skinnybody.setRotationPoint(0F, 16F, 0F);

		glow = new ModelPart(this, 20, 0);
		glow.addBox(-5F, 5.9F, -9F, 10, 0, 10, 0F);
		//glow.setRotationPoint(0F, 16F, 0F);

	}

	@Override
	public Iterable<ModelPart> parts() {
		return ImmutableList.of(
				legs,
				fatbody,
				skinnybody
		);
	}

	@Override
	public void setupAnim(Entity entity, float v, float v1, float v2, float v3, float v4) {
		//super.setRotationAngles(f, f1, f2, f3, f4, f5);
	}

	//fields
	public ModelPart legs;
	public ModelPart fatbody;
	public ModelPart skinnybody;
	public ModelPart glow;

}
