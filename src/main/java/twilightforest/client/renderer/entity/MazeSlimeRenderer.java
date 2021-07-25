package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;

public class MazeSlimeRenderer extends SlimeRenderer {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("mazeslime.png");

	public MazeSlimeRenderer(EntityRenderDispatcher manager, float shadowSize) {
		super(manager);
		this.shadowRadius = shadowSize;
	}

	@Override
	public void render(Slime entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		if(this.model.riding) matrixStackIn.translate(0, 0.25F, 0);
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(Slime entity) {
		return textureLoc;
	}
}
