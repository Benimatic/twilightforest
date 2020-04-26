package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFBlockGoblin;
import twilightforest.entity.EntityTFBlockGoblin;

public class RenderTFBlockGoblin<T extends EntityTFBlockGoblin, M extends ModelTFBlockGoblin<T>> extends BipedRenderer<T, M> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("blockgoblin.png");

	public RenderTFBlockGoblin(EntityRendererManager manager, M model, float shadowSize) {
		super(manager, model, shadowSize);
	}

	@Override
	public void render(T goblin, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
		super.render(goblin, yaw, partialTicks, stack, buffer, light);
		double x = MathHelper.lerp((double)partialTicks, goblin.lastTickPosX, goblin.getX());
		double y = MathHelper.lerp((double)partialTicks, goblin.lastTickPosY, goblin.getY());
		double z = MathHelper.lerp((double)partialTicks, goblin.lastTickPosZ, goblin.getZ());

		renderManager.render(goblin.block, x, y, z, yaw, partialTicks, stack, buffer, light);
		renderManager.render(goblin.chain1, x, y, z, yaw, partialTicks, stack, buffer, light);
		renderManager.render(goblin.chain2, x, y, z, yaw, partialTicks, stack, buffer, light);
		renderManager.render(goblin.chain3, x, y, z, yaw, partialTicks, stack, buffer, light);//renderEntity
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}
}
