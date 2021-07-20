package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.entity.TowerBroodlingEntity;

public class CarminiteBroodlingRenderer<T extends TowerBroodlingEntity> extends SpiderRenderer<T> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("towerbroodling.png");

	public CarminiteBroodlingRenderer(EntityRendererManager manager) {
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
