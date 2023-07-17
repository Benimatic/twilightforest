package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.joml.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.data.ModelData;
import org.apache.commons.lang3.ArrayUtils;
import twilightforest.TwilightForestMod;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.shield.IShieldCapability;
import twilightforest.entity.boss.Lich;

public class ShieldLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

	public static final ModelResourceLocation LOC = new ModelResourceLocation(new ResourceLocation(TwilightForestMod.ID, "shield"), "inventory");
	private static final Direction[] DIRS = ArrayUtils.add(Direction.values(), null);

	public ShieldLayer(RenderLayerParent<T, M> renderer) {
		super(renderer);
	}

	private int getShieldCount(T entity) {
		return entity instanceof Lich
						? ((Lich) entity).getShieldStrength()
						: entity.getCapability(CapabilityList.SHIELDS).map(IShieldCapability::shieldsLeft).orElse(0);
	}

	private void renderShields(PoseStack stack, MultiBufferSource buffer, T entity, float partialTicks) {
		float age = entity.tickCount + partialTicks;
		float rotateAngleY = age / 5.0F;
		float rotateAngleX = Mth.sin(age / 5.0F) / 4.0F;
		float rotateAngleZ = Mth.cos(age / 5.0F) / 4.0F;

		int count = getShieldCount(entity);
		for (int c = 0; c < count; c++) {
			stack.pushPose();

			// shift to the torso
			stack.translate(-0.5, 0.5, -0.5);

			// invert Y
			stack.scale(1, -1, 1);

			// perform the rotations, accounting for the fact that baked models are corner-based
			stack.translate(0.5, 0.5, 0.5);
			stack.mulPose(Axis.ZP.rotationDegrees(rotateAngleZ * (180F / (float) Math.PI)));
			stack.mulPose(Axis.YP.rotationDegrees(rotateAngleY * (180F / (float) Math.PI) + (c * (360F / count))));
			stack.mulPose(Axis.XP.rotationDegrees(rotateAngleX * (180F / (float) Math.PI)));
			stack.translate(-0.5, -0.5, -0.5);

			// push the shields outwards from the center of rotation
			stack.translate(0F, 0F, -0.7F);

			BakedModel model = Minecraft.getInstance().getModelManager().getModel(LOC);
			for (Direction dir : DIRS) {
				Minecraft.getInstance().getItemRenderer().renderQuadList(
						stack,
						buffer.getBuffer(Sheets.translucentCullBlockSheet()),
						model.getQuads(null, dir, entity.getRandom(), ModelData.EMPTY, Sheets.translucentCullBlockSheet()),
						ItemStack.EMPTY,
						0xF000F0,
						OverlayTexture.NO_OVERLAY
				);
			}

			stack.popPose();
		}
	}

	@Override
	public void render(PoseStack stack, MultiBufferSource buffer, int light, T living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (getShieldCount(living) > 0) {
			renderShields(stack, buffer, living, partialTicks);
		}
	}
}
