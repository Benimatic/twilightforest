package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.HarbingerCubeModel;
import twilightforest.entity.HarbingerCubeEntity;

public class HarbingerCubeRenderer<T extends HarbingerCubeEntity> extends MobRenderer<T, HarbingerCubeModel<T>> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("apocalypse2.png");

	public HarbingerCubeRenderer(EntityRenderDispatcher manager) {
		super(manager, new HarbingerCubeModel<>(), 1.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(HarbingerCubeEntity entity) {
		return textureLoc;
	}

	@Override
	protected void scale(T entity, PoseStack stack, float partialTicks) {
		float scale = 1.0F;
		stack.scale(scale, scale, scale);
	}
}
