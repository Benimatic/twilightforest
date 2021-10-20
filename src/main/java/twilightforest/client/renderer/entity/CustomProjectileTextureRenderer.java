package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.entity.projectile.TFThrowable;

/**
 * This renderer serves as a way to render item textures on a projectile without needing an actual item registered for it.
 * Consider using {@link net.minecraft.client.renderer.entity.ThrownItemRenderer} if your projectile is an existing item already.
 * Note: If youre going to implement this as a projectile renderer, make sure the projectile entity defines a texture.
 * Override the getTexture() method in {@link twilightforest.entity.projectile.ITFProjectile} to add it.
 *
 * If you see a think115 as the texure, you did something wrong. Thats the fallback if its null lmao
 */
public class CustomProjectileTextureRenderer extends EntityRenderer<TFThrowable> {

	private final ResourceLocation FALLBACK_TEX = TwilightForestMod.prefix("textures/items/think115.png");

	public CustomProjectileTextureRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);
	}

	//[VanillaCopy] of DragonFireballRender.render, we just input our own texture stuff instead
	@Override
	public void render(TFThrowable entity, float entityYaw, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light) {
		ms.pushPose();
		ms.scale(0.5F, 0.5F, 0.5F);
		ms.mulPose(this.entityRenderDispatcher.cameraOrientation());
		ms.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		PoseStack.Pose posestack$pose = ms.last();
		Matrix4f matrix4f = posestack$pose.pose();
		Matrix3f matrix3f = posestack$pose.normal();
		VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(entity.getTexture() != null ? entity.getTexture() : FALLBACK_TEX));
		vertex(vertexconsumer, matrix4f, matrix3f, light, 0.0F, 0, 0, 1);
		vertex(vertexconsumer, matrix4f, matrix3f, light, 1.0F, 0, 1, 1);
		vertex(vertexconsumer, matrix4f, matrix3f, light, 1.0F, 1, 1, 0);
		vertex(vertexconsumer, matrix4f, matrix3f, light, 0.0F, 1, 0, 0);
		ms.popPose();
		super.render(entity, entityYaw, partialTicks, ms, buffer, light);
	}

	private static void vertex(VertexConsumer p_114090_, Matrix4f p_114091_, Matrix3f p_114092_, int p_114093_, float p_114094_, int p_114095_, int p_114096_, int p_114097_) {
		p_114090_.vertex(p_114091_, p_114094_ - 0.5F, (float)p_114095_ - 0.25F, 0.0F).color(255, 255, 255, 255).uv((float)p_114096_, (float)p_114097_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_114093_).normal(p_114092_, 0.0F, 1.0F, 0.0F).endVertex();
	}

	@Override
	public ResourceLocation getTextureLocation(TFThrowable entity) {
		if(entity.getTexture() != null) {
			return entity.getTexture();
		}
		return FALLBACK_TEX;
	}
}
