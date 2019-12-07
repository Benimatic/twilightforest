package twilightforest.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import twilightforest.entity.EntityTFGiantMiner;

public class RenderTFGiant extends RenderBiped<EntityTFGiantMiner> {

	private boolean typeCache = false;

	public RenderTFGiant(EntityRendererManager manager) {
		super(manager, new ModelPlayer(0, false), 1.8F);
		this.addLayer(new LayerBipedArmor(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTFGiantMiner entity) {
		Minecraft mc = Minecraft.getInstance();
		boolean type = false;
		ResourceLocation texture = DefaultPlayerSkin.getDefaultSkinLegacy();
		if (mc.getRenderViewEntity() instanceof AbstractClientPlayerEntity) {
			AbstractClientPlayerEntity client = ((AbstractClientPlayerEntity) mc.getRenderViewEntity());
			texture = client.getLocationSkin();
			type = client.getSkinType().equals("slim");
		}
		if (type != typeCache) {
			typeCache = type;
			mainModel = new ModelPlayer(0, type);
		}
		return texture;
	}

	@Override
	protected void preRenderCallback(EntityTFGiantMiner entity, float partialTicks) {
		float scale = 4.0F;
		GlStateManager.scalef(scale, scale, scale);
	}
}
