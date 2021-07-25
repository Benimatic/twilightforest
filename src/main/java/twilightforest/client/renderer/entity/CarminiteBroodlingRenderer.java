package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.entity.TowerBroodlingEntity;

public class CarminiteBroodlingRenderer<T extends TowerBroodlingEntity> extends SpiderRenderer<T> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("towerbroodling.png");

	public CarminiteBroodlingRenderer(EntityRenderDispatcher manager) {
		super(manager);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return textureLoc;
	}

	@Override
	protected void scale(T entity, PoseStack stack, float partialTicks) {
		float scale = 0.7F;
		stack.scale(scale, scale, scale);
	}
}
