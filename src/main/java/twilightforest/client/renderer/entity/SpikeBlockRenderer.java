package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import twilightforest.TwilightForestMod;

public class SpikeBlockRenderer<T extends Entity, M extends EntityModel<T>> extends EntityRenderer<T> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("blockgoblin.png");

	private final Model model;

	public SpikeBlockRenderer(EntityRenderDispatcher manager, M model) {
		super(manager);
		this.model = model;
	}

	@Override
	public void render(T goblin, float yaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {

		stack.pushPose();
		stack.scale(-1.0F, -1.0F, 1.0F);

		VertexConsumer ivertexbuilder = buffer.getBuffer(this.model.renderType(textureLoc));

		stack.mulPose(Vector3f.ZP.rotationDegrees(yaw));

		this.model.renderToBuffer(stack, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		stack.popPose();
		super.render(goblin, yaw, partialTicks, stack, buffer, light);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return textureLoc;
	}
}
