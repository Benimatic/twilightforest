package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import com.mojang.math.Vector3f;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.MoonwormModel;
import twilightforest.entity.projectile.MoonwormShotEntity;

public class MoonwormShotRenderer extends EntityRenderer<MoonwormShotEntity> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("moonworm.png");
	private final MoonwormModel wormModel = new MoonwormModel();

	public MoonwormShotRenderer(EntityRenderDispatcher manager) {
		super(manager);
		this.shadowRadius = 0.25F;
	}

	@Override
	public void render(MoonwormShotEntity entity, float yaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
		stack.pushPose();
		stack.translate(0.0, 0.5, 0.0);
		stack.scale(-1f, -1f, -1f);

		stack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.yRot) - 180.0F));
		stack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.xRot)));

		VertexConsumer builder = buffer.getBuffer(wormModel.renderType(textureLoc));
		wormModel.renderToBuffer(stack, builder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

		stack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(MoonwormShotEntity entity) {
		return textureLoc;
	}
}
