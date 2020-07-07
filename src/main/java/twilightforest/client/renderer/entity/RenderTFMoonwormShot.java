package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFMoonworm;
import twilightforest.entity.projectile.EntityTFMoonwormShot;

public class RenderTFMoonwormShot extends EntityRenderer<EntityTFMoonwormShot> {

	private static final Quaternion ROT = new Vector3f(1, 0, 1).rotationDegrees(90);
	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("moonworm.png");
	private final ModelTFMoonworm wormModel = new ModelTFMoonworm();

	public RenderTFMoonwormShot(EntityRendererManager manager) {
		super(manager);
		this.shadowSize = 0.5F;
	}

	@Override
	public void render(EntityTFMoonwormShot entity, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
		stack.push();
		stack.rotate(ROT);

		IVertexBuilder builder = buffer.getBuffer(RenderType.getEntityCutout(getEntityTexture(entity)));
		wormModel.render(stack, builder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

		stack.pop();
	}

	@Override
	public ResourceLocation getEntityTexture(EntityTFMoonwormShot entity) {
		return textureLoc;
	}
}
