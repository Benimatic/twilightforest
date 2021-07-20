package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.HarbingerCubeModel;
import twilightforest.entity.HarbingerCubeEntity;

public class HarbingerCubeRenderer<T extends HarbingerCubeEntity> extends MobRenderer<T, HarbingerCubeModel<T>> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("apocalypse2.png");

	public HarbingerCubeRenderer(EntityRendererManager manager) {
		super(manager, new HarbingerCubeModel<>(), 1.0F);
	}

	@Override
	public ResourceLocation getEntityTexture(HarbingerCubeEntity entity) {
		return textureLoc;
	}

	@Override
	protected void preRenderCallback(T entity, MatrixStack stack, float partialTicks) {
		float scale = 1.0F;
		stack.scale(scale, scale, scale);
	}
}
