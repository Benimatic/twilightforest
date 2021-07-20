package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import twilightforest.entity.ProtectionBoxEntity;

public class ProtectionBoxModel<T extends ProtectionBoxEntity> extends SegmentedModel<T> {

	private T entity;

	public ModelRenderer box;
	private int lastPixelsX;
	private int lastPixelsY;
	private int lastPixelsZ;

	public ProtectionBoxModel() {
		textureWidth = 16;
		textureHeight = 16;
		box = new ModelRenderer(this, 0, 0);
		box.addBox(0F, 0F, 0F, 16, 16, 16, 0F);
		box.setRotationPoint(0F, 0F, 0F);
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.box);
	}

	@Override
	public void render(MatrixStack stack, IVertexBuilder builder, int light, int overlay, float red, float green, float blue, float scale) {
		ProtectionBoxEntity boxEntity = entity;

		int pixelsX = boxEntity.sizeX * 16 + 2;
		int pixelsY = boxEntity.sizeY * 16 + 2;
		int pixelsZ = boxEntity.sizeZ * 16 + 2;

		if (pixelsX != this.lastPixelsX || pixelsY != this.lastPixelsY || pixelsZ != this.lastPixelsZ) {
			resizeBoxElement(pixelsX, pixelsY, pixelsZ);
		}

		super.render(stack, builder, light, overlay, red, green, blue, scale);
	}

	@Override
	public void setRotationAngles(T entity, float v, float v1, float v2, float v3, float v4) {

	}

	@Override
	public void setLivingAnimations(T entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		this.entity = entity;
	}

	private void resizeBoxElement(int pixelsX, int pixelsY, int pixelsZ) {
		box = new ModelRenderer(this, 0, 0);
		box.addBox(-1F, -1F, -1F, pixelsX, pixelsY, pixelsZ, 0F);
		box.setRotationPoint(0F, 0F, 0F);

		this.lastPixelsX = pixelsX;
		this.lastPixelsY = pixelsY;
		this.lastPixelsZ = pixelsZ;
	}
}
