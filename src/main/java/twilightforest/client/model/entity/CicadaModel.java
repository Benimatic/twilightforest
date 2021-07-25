package twilightforest.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;

public class CicadaModel extends Model {
	public CicadaModel() {
		super(RenderType::entityCutoutNoCull);
		legs = new ModelPart(this, 0, 21);
		legs.addBox(-4F, 7.9F, -5F, 8, 1, 9, 0F);
		//legs.setRotationPoint(0F, 16F, 0F);

		fatbody = new ModelPart(this, 0, 11);
		fatbody.addBox(-2F, 6F, -4F, 4, 2, 6, 0F);
		//fatbody.setRotationPoint(0F, 16F, 0F);

		skinnybody = new ModelPart(this, 0, 0);
		skinnybody.addBox(-1F, 7F, -5F, 2, 1, 8, 0F);
		//skinnybody.setRotationPoint(0F, 16F, 0F);


		eye1 = new ModelPart(this, 20, 15);
		eye1.addBox(1F, 5F, 2F, 2, 2, 2, 0F);
		//eye1.setRotationPoint(0F, 16F, 0F);

		eye2 = new ModelPart(this, 20, 15);
		eye2.addBox(-3F, 5F, 2F, 2, 2, 2, 0F);
		//eye2.setRotationPoint(0F, 16F, 0F);

		wings = new ModelPart(this, 20, 0);
		wings.addBox(-4F, 5F, -7F, 8, 1, 8, 0F);
		//wings.setRotationPoint(0F, 16F, 0F);
	}

	@Override
	public void renderToBuffer(PoseStack ms, VertexConsumer buffer, int light, int overlay, float r, float g, float b, float a) {
		legs.render(ms, buffer, light, overlay, r, g, b, a);
		fatbody.render(ms, buffer, light, overlay, r, g, b, a);
		skinnybody.render(ms, buffer, light, overlay, r, g, b, a);
		eye1.render(ms, buffer, light, overlay, r, g, b, a);
		eye2.render(ms, buffer, light, overlay, r, g, b, a);
		wings.render(ms, buffer, light, overlay, r, g, b, a);
	}

	public ModelPart legs;
	public ModelPart fatbody;
	public ModelPart skinnybody;
	public ModelPart eye1;
	public ModelPart eye2;
	public ModelPart wings;

}
