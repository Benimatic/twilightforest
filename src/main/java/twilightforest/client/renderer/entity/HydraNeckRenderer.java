package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.HydraNeckModel;
import twilightforest.entity.boss.HydraNeckEntity;
import twilightforest.entity.boss.HydraHeadContainer;

public class HydraNeckRenderer extends TFPartRenderer<HydraNeckEntity, HydraNeckModel> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("hydra4.png");


	public HydraNeckRenderer(EntityRendererManager manager) {
		super(manager, new HydraNeckModel());
	}

	@Override
	public void render(HydraNeckEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int light) {
		HydraHeadContainer headCon = HydraHeadRenderer.getHeadObject(entityIn.head);
		if (headCon != null)
			if (headCon.shouldRenderHead()) {
				float yawDiff = entityIn.rotationYaw - entityIn.prevRotationYaw;
				if (yawDiff > 180) {
					yawDiff -= 360;
				} else if (yawDiff < -180) {
					yawDiff += 360;
				}
				float yaw2 = entityIn.prevRotationYaw + yawDiff * partialTicks;

				matrixStackIn.rotate(Vector3f.YN.rotationDegrees(yaw2 + 180));
				super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, light);
			}
	}

	@Override
	public ResourceLocation getEntityTexture(HydraNeckEntity entity) {
		return textureLoc;
	}
}
