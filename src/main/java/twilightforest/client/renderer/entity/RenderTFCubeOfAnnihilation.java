package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFCubeOfAnnihilation;
import twilightforest.entity.EntityTFCubeOfAnnihilation;

public class RenderTFCubeOfAnnihilation extends EntityRenderer<EntityTFCubeOfAnnihilation> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("cubeofannihilation.png");
	private final Model model = new ModelTFCubeOfAnnihilation();

	public RenderTFCubeOfAnnihilation(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	public void render(EntityTFCubeOfAnnihilation entity, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
		super.render(entity, yaw, partialTicks, stack, buffer, light);

		stack.push();

		stack.scale(-1.0F, -1.0F, 1.0F);
		stack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.wrapDegrees((entity.ticksExisted + partialTicks) * 11F)));
		stack.translate(0F, -0.5F, 0F);
		model.render(stack, buffer.getBuffer(model.getLayer(textureLoc)), light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);

		stack.pop();
	}

	@Override
	public ResourceLocation getEntityTexture(EntityTFCubeOfAnnihilation entity) {
		return textureLoc;
	}
}
