package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFTowerBroodling;

public class RenderTFTowerBroodling<T extends EntityTFTowerBroodling> extends SpiderRenderer<T> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("towerbroodling.png");

	public RenderTFTowerBroodling(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}

	@Override
	protected void preRenderCallback(T entity, MatrixStack stack, float partialTicks) {
		float scale = 0.7F;
		stack.scale(scale, scale, scale);
	}
}
