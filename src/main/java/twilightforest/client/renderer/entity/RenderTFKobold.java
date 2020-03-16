package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import twilightforest.client.model.entity.ModelTFKobold;
import twilightforest.entity.EntityTFKobold;

public class RenderTFKobold<T extends EntityTFKobold, M extends ModelTFKobold<T>> extends RenderTFBiped<T, M> {

	public RenderTFKobold(EntityRendererManager manager, M modelBiped, float shadowSize, String textureName) {
		super(manager, modelBiped, shadowSize, textureName);
		this.addLayer(new HeldItemLayer<>(this));
	}

	/**
	 * How far down the arm should we render the equipped item?
	 */
	//TODO: AtomicBlom: Somehow introduce this into LayerHeldItem
	//er, actually, this seems like it's not available...
	protected void func_82422_c() {
		RenderSystem.translatef(0.0F, 0.01875F, 0.0F);
	}
}
