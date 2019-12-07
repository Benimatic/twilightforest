package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.MathHelper;
import twilightforest.client.model.entity.ModelTFSnowGuardian;
import twilightforest.entity.EntityTFSnowGuardian;

public class RenderTFSnowGuardian<T extends EntityTFSnowGuardian, M extends ModelTFSnowGuardian<T>> extends RenderTFBiped<T, M> {

	public RenderTFSnowGuardian(EntityRendererManager manager) {
		super(manager, new ModelTFSnowGuardian(), 0.25F, "textures/entity/zombie/zombie.png");
		this.addLayer(new LayerBipedArmor(this));
	}

	@Override
	protected void preRenderCallback(T entity, float partialTicks) {
		float bounce = entity.ticksExisted + partialTicks;
		GlStateManager.translatef(0F, MathHelper.sin((bounce) * 0.2F) * 0.15F, 0F);
	}
}
