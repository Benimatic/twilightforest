package twilightforest.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CurioHeadRenderer implements ICurioRenderer {

	@Override
	public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if(renderLayerParent.getModel() instanceof HeadedModel headModel) {
			matrixStack.pushPose();
			headModel.getHead().translateAndRotate(matrixStack);
			matrixStack.translate(0.0D, -0.25D, 0.0D);
			matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
			matrixStack.scale(0.625F, -0.625F, -0.625F);
			Minecraft.getInstance().getItemInHandRenderer().renderItem(slotContext.entity(), stack, ItemTransforms.TransformType.HEAD, false, matrixStack, renderTypeBuffer, light);
			matrixStack.popPose();
		}
	}
}
