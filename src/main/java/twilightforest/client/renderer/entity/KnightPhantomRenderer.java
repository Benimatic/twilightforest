package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.KnightPhantomModel;
import twilightforest.entity.boss.KnightPhantomEntity;

public class KnightPhantomRenderer extends BipedRenderer<KnightPhantomEntity, KnightPhantomModel> {

	private static final ResourceLocation PHANTOM_TEXTURE = TwilightForestMod.getModelTexture("phantomskeleton.png");

	public KnightPhantomRenderer(EntityRendererManager manager, KnightPhantomModel model, float shadowSize) {
		super(manager, model, shadowSize);
		this.addLayer(new HeldItemLayer<>(this));
		this.addLayer(new BipedArmorLayer<>(this, new KnightPhantomModel(), new KnightPhantomModel()));
	}

	@Override
	public ResourceLocation getEntityTexture(KnightPhantomEntity entity) {
		return PHANTOM_TEXTURE;
	}

	@Override
	protected void preRenderCallback(KnightPhantomEntity entity, MatrixStack stack, float partialTicks) {
		float scale = entity.isChargingAtPlayer() ? 1.8F : 1.2F;
		stack.scale(scale, scale, scale);
	}
}
