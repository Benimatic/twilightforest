package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.NagaModel;
import twilightforest.entity.boss.NagaSegmentEntity;

//FIXME legacy: scaling was commented out since textures are bigger now
public class NagaSegmentRenderer<T extends NagaSegmentEntity> extends TFPartRenderer<T, NagaModel<T>> {
	private static final ResourceLocation part_TextureLoc = TwilightForestMod.getModelTexture("nagasegment.png");

	public NagaSegmentRenderer(EntityRendererManager m) {
		super(m, new NagaModel<>());
	}

	@Override
	public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		if(!entityIn.isInvisible()) {
			matrixStackIn.push();

			float yawDiff = entityIn.rotationYaw - entityIn.prevRotationYaw;
			if (yawDiff > 180) {
				yawDiff -= 360;
			} else if (yawDiff < -180) {
				yawDiff += 360;
			}
			float yaw2 = entityIn.prevRotationYaw + yawDiff * partialTicks;

			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(yaw2));
			matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityIn.rotationPitch));

			//matrixStackIn.scale(2.0F, 2.0F, 2.0F);
			//matrixStackIn.translate(0.0D, -1.501F, 0.0D);

			int light = renderManager.getPackedLight(entityIn.getParent(), partialTicks);
			super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, light);
			matrixStackIn.pop();
		}
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return part_TextureLoc;
	}
}
