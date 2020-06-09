package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFWraith;
import twilightforest.entity.EntityTFWraith;

import javax.annotation.Nonnull;

public class RenderTFWraith extends BipedRenderer<EntityTFWraith, ModelTFWraith> {

	private static final ResourceLocation textureWraith = TwilightForestMod.getModelTexture("ghost.png");

	public RenderTFWraith(EntityRendererManager manager, ModelTFWraith modelbiped, float shadowSize) {
		super(manager, modelbiped, shadowSize);
	}

	@Override
	public ResourceLocation getEntityTexture(EntityTFWraith wraith) {
		return textureWraith;
	}
}
