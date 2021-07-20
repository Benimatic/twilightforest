package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.CicadaModel;
import twilightforest.entity.projectile.CicadaShotEntity;

public class CicadaShotRenderer extends EntityRenderer<CicadaShotEntity> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("cicada-model.png");
	private final CicadaModel cicadaModel = new CicadaModel();

	public CicadaShotRenderer(EntityRendererManager manager) {
		super(manager);
		this.shadowSize = 0.25F;
	}

	@Override
	public void render(CicadaShotEntity entity, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
		stack.push();
		stack.translate(0.0, 0.5, 0.0);
		stack.scale(-1f, -1f, -1f);

		stack.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entity.prevRotationYaw, entity.rotationYaw) - 180.0F));
		stack.rotate(Vector3f.XP.rotationDegrees(MathHelper.lerp(partialTicks, entity.prevRotationPitch, entity.rotationPitch)));

		IVertexBuilder builder = buffer.getBuffer(cicadaModel.getRenderType(textureLoc));
		cicadaModel.render(stack, builder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

		stack.pop();
	}

	@Override
	public ResourceLocation getEntityTexture(CicadaShotEntity entity) {
		return textureLoc;
	}
}
