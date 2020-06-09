package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFHydraMortar;
import twilightforest.entity.boss.EntityTFHydraMortar;

public class RenderTFHydraMortar extends EntityRenderer<EntityTFHydraMortar> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("hydramortar.png");
	private final ModelTFHydraMortar mortarModel = new ModelTFHydraMortar();

	public RenderTFHydraMortar(EntityRendererManager manager) {
		super(manager);
		this.shadowSize = 0.5F;
	}

	@Override
	public void render(EntityTFHydraMortar mortar, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
		stack.push();
		float var10;

		if ((float) mortar.fuse - partialTicks + 1.0F < 10.0F) {
			var10 = 1.0F - ((float) mortar.fuse - partialTicks + 1.0F) / 10.0F;

			if (var10 < 0.0F) {
				var10 = 0.0F;
			}

			if (var10 > 1.0F) {
				var10 = 1.0F;
			}

			var10 *= var10;
			var10 *= var10;
			float var11 = 1.0F + var10 * 0.3F;
			stack.scale(var11, var11, var11);
		}

		var10 = (1.0F - ((float) mortar.fuse - partialTicks + 1.0F) / 100.0F) * 0.8F;
		//this.bindTexture(textureLoc);
		IVertexBuilder builder = buffer.getBuffer(RenderType.getEntitySolid(textureLoc));

		mortarModel.render(stack, builder, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 0.075F);

		if (mortar.fuse / 5 % 2 == 0) {
			RenderSystem.disableTexture();
			RenderSystem.disableLighting();
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, var10);

			mortarModel.render(stack, builder, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 0.075F);

			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.disableBlend();
			RenderSystem.enableLighting();
			RenderSystem.enableTexture();
		}

		stack.pop();
	}

	@Override
	public ResourceLocation getEntityTexture(EntityTFHydraMortar entity) {
		return textureLoc;
	}
}
