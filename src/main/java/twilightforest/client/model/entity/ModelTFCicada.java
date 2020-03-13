package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTFCicada extends SegmentedModel {
	public ModelTFCicada() {
		legs = new ModelRenderer(this, 0, 21);
		legs.addCuboid(-4F, 7.9F, -5F, 8, 1, 9, 0F);
		//legs.setRotationPoint(0F, 16F, 0F);

		fatbody = new ModelRenderer(this, 0, 11);
		fatbody.addCuboid(-2F, 6F, -4F, 4, 2, 6, 0F);
		//fatbody.setRotationPoint(0F, 16F, 0F);

		skinnybody = new ModelRenderer(this, 0, 0);
		skinnybody.addCuboid(-1F, 7F, -5F, 2, 1, 8, 0F);
		//skinnybody.setRotationPoint(0F, 16F, 0F);


		eye1 = new ModelRenderer(this, 20, 15);
		eye1.addCuboid(1F, 5F, 2F, 2, 2, 2, 0F);
		//eye1.setRotationPoint(0F, 16F, 0F);

		eye2 = new ModelRenderer(this, 20, 15);
		eye2.addCuboid(-3F, 5F, 2F, 2, 2, 2, 0F);
		//eye2.setRotationPoint(0F, 16F, 0F);

		wings = new ModelRenderer(this, 20, 0);
		wings.addCuboid(-4F, 5F, -7F, 8, 1, 8, 0F);
		//wings.setRotationPoint(0F, 16F, 0F);
	}

//	public void render(float f5) {
////		super.render(f, f1, f2, f3, f4, f5);
////		setRotationAngles(f, f1, f2, f3, f4, f5);
//	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(
				legs,
				fatbody,
				skinnybody,
				eye1,
				eye2,
				wings
		);
	}

//	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
////		super.setRotationAngles(f, f1, f2, f3, f4, f5);
//	}

	@Override
	public void setAngles(Entity entity, float v, float v1, float v2, float v3, float v4) {

	}

	//fields
	public ModelRenderer legs;
	public ModelRenderer fatbody;
	public ModelRenderer skinnybody;
	public ModelRenderer eye1;
	public ModelRenderer eye2;
	public ModelRenderer wings;

}
