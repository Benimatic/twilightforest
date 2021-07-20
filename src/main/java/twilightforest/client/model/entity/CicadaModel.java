package twilightforest.client.model.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class CicadaModel extends Model {
	public CicadaModel() {
		super(RenderType::getEntityCutoutNoCull);
		legs = new ModelRenderer(this, 0, 21);
		legs.addBox(-4F, 7.9F, -5F, 8, 1, 9, 0F);
		//legs.setRotationPoint(0F, 16F, 0F);

		fatbody = new ModelRenderer(this, 0, 11);
		fatbody.addBox(-2F, 6F, -4F, 4, 2, 6, 0F);
		//fatbody.setRotationPoint(0F, 16F, 0F);

		skinnybody = new ModelRenderer(this, 0, 0);
		skinnybody.addBox(-1F, 7F, -5F, 2, 1, 8, 0F);
		//skinnybody.setRotationPoint(0F, 16F, 0F);


		eye1 = new ModelRenderer(this, 20, 15);
		eye1.addBox(1F, 5F, 2F, 2, 2, 2, 0F);
		//eye1.setRotationPoint(0F, 16F, 0F);

		eye2 = new ModelRenderer(this, 20, 15);
		eye2.addBox(-3F, 5F, 2F, 2, 2, 2, 0F);
		//eye2.setRotationPoint(0F, 16F, 0F);

		wings = new ModelRenderer(this, 20, 0);
		wings.addBox(-4F, 5F, -7F, 8, 1, 8, 0F);
		//wings.setRotationPoint(0F, 16F, 0F);
	}

	@Override
	public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a) {
		legs.render(ms, buffer, light, overlay, r, g, b, a);
		fatbody.render(ms, buffer, light, overlay, r, g, b, a);
		skinnybody.render(ms, buffer, light, overlay, r, g, b, a);
		eye1.render(ms, buffer, light, overlay, r, g, b, a);
		eye2.render(ms, buffer, light, overlay, r, g, b, a);
		wings.render(ms, buffer, light, overlay, r, g, b, a);
	}

	public ModelRenderer legs;
	public ModelRenderer fatbody;
	public ModelRenderer skinnybody;
	public ModelRenderer eye1;
	public ModelRenderer eye2;
	public ModelRenderer wings;

}
