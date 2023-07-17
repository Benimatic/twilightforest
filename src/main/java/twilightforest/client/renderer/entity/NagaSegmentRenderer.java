package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.TFModelLayers;
import twilightforest.client.model.entity.NagaModel;
import twilightforest.entity.boss.NagaSegment;

public class NagaSegmentRenderer<T extends NagaSegment> extends TFPartRenderer<T, NagaModel<T>> {
	private static final ResourceLocation part_TextureLoc = TwilightForestMod.getModelTexture("nagasegment.png");

	public NagaSegmentRenderer(EntityRendererProvider.Context m) {
		super(m, new NagaModel<>(m.bakeLayer(TFModelLayers.NAGA_BODY)));
	}

	@Override
	public void render(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		if(!entityIn.isInvisible()) {
			matrixStackIn.pushPose();

			float yawDiff = entityIn.getYRot() - entityIn.yRotO;
			if (yawDiff > 180) {
				yawDiff -= 360;
			} else if (yawDiff < -180) {
				yawDiff += 360;
			}
			float yaw2 = entityIn.yRotO + yawDiff * partialTicks;

			matrixStackIn.mulPose(Axis.YP.rotationDegrees(yaw2));
			matrixStackIn.mulPose(Axis.XP.rotationDegrees(entityIn.getXRot()));

			int light = entityRenderDispatcher.getPackedLightCoords(entityIn.getParent(), partialTicks);
			super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, light);
			matrixStackIn.popPose();
		}
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return part_TextureLoc;
	}
}
