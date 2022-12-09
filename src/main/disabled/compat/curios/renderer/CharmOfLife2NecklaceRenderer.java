package twilightforest.compat.curios.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.TFModelLayers;
import twilightforest.compat.curios.model.CharmOfLife2NecklaceModel;

public class CharmOfLife2NecklaceRenderer implements ICurioRenderer {

	private final CharmOfLife2NecklaceModel model;

	public CharmOfLife2NecklaceRenderer() {
		this.model = new CharmOfLife2NecklaceModel(Minecraft.getInstance().getEntityModels().bakeLayer(TFModelLayers.CHARM_OF_LIFE_2));
	}

	@Override
	public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack item, SlotContext slotContext, PoseStack stack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

		ICurioRenderer.followBodyRotations(slotContext.entity(), model);
		VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(TwilightForestMod.getModelTexture("curios/charm_of_life_2.png")));
		model.renderToBuffer(stack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
