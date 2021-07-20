package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.CubeOfAnnihilationModel;
import twilightforest.entity.CubeOfAnnihilationEntity;

public class CubeOfAnnihilationRenderer extends EntityRenderer<CubeOfAnnihilationEntity> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("cubeofannihilation.png");
	private final Model model = new CubeOfAnnihilationModel();

	public CubeOfAnnihilationRenderer(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	public void render(CubeOfAnnihilationEntity entity, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
		super.render(entity, yaw, partialTicks, stack, buffer, light);

		stack.push();

		stack.scale(-1.0F, -1.0F, 1.0F);
		stack.rotate(Vector3f.YP.rotationDegrees(MathHelper.wrapDegrees((entity.ticksExisted + partialTicks) * 11F)));
		stack.translate(0F, -0.5F, 0F);
		model.render(stack, buffer.getBuffer(model.getRenderType(textureLoc)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

		stack.pop();
	}

	@Override
	public ResourceLocation getEntityTexture(CubeOfAnnihilationEntity entity) {
		return textureLoc;
	}
}
