package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFBlockGoblin;
import twilightforest.entity.EntityTFBlockGoblin;

public class RenderTFBlockGoblin<T extends EntityTFBlockGoblin, M extends ModelTFBlockGoblin<T>> extends BipedRenderer<T, M> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("blockgoblin.png");

	public RenderTFBlockGoblin(EntityRendererManager manager, M model, float shadowSize) {
		super(manager, model, shadowSize);
	}

	@Override
	public void doRender(T goblin, double x, double y, double z, float yaw, float partialTicks) {
		super.doRender(goblin, x, y, z, yaw, partialTicks);
		renderManager.renderEntityStatic(goblin.block, partialTicks, false);
		renderManager.renderEntityStatic(goblin.chain1, partialTicks, false);
		renderManager.renderEntityStatic(goblin.chain2, partialTicks, false);
		renderManager.renderEntityStatic(goblin.chain3, partialTicks, false);//renderEntity
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}
}
