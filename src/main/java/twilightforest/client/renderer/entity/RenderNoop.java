package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderNoop<T extends Entity> extends EntityRenderer<T> {
	public RenderNoop(EntityRendererManager mgr) {
		super(mgr);
	}

	@Override
	public ResourceLocation getEntityTexture(T p_110775_1_) {
		throw new UnsupportedOperationException();
	}
}
