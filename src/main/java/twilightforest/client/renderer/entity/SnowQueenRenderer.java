package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.SnowQueenModel;
import twilightforest.entity.boss.SnowQueenEntity;

public class SnowQueenRenderer extends BipedRenderer<SnowQueenEntity, SnowQueenModel> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("snowqueen.png");

	public SnowQueenRenderer(EntityRendererManager manager, SnowQueenModel model) {
		super(manager, model, 0.625F);
	}

	@Override
	public ResourceLocation getEntityTexture(SnowQueenEntity entity) {
		return textureLoc;
	}

	@Override
	protected void preRenderCallback(SnowQueenEntity queen, MatrixStack stack, float partialTicks) {
		float scale = 1.2F;
		stack.scale(scale, scale, scale);
	}

	@Override
	public void render(SnowQueenEntity queen, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
		super.render(queen, yaw, partialTicks, stack, buffer, light);

		//FIXME
    }
}
