package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.IceCrystalModel;
import twilightforest.entity.boss.IceCrystalEntity;

public class IceCrystalRenderer extends MobRenderer<IceCrystalEntity, IceCrystalModel> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("icecrystal.png");

	public IceCrystalRenderer(EntityRendererManager manager) {
		super(manager, new IceCrystalModel(), 0.25F);
	}

	@Override
	protected void preRenderCallback(IceCrystalEntity entity, MatrixStack stack, float partialTicks) {
		float bounce = entity.ticksExisted + partialTicks;
		stack.translate(0F, MathHelper.sin((bounce) * 0.2F) * 0.15F, 0F);
	}

	@Override
	public ResourceLocation getEntityTexture(IceCrystalEntity entity) {
		return textureLoc;
	}
}
