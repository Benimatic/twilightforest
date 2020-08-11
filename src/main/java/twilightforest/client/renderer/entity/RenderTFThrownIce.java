package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.entity.boss.EntityTFIceBomb;

import java.util.Random;

/**
 * [VanillaCopy] of {@link net.minecraft.client.renderer.entity.FallingBlockRenderer} because of generic type restrictions
  */
public class RenderTFThrownIce extends EntityRenderer<EntityTFIceBomb> {

	public RenderTFThrownIce(EntityRendererManager manager) {
		super(manager);
		this.shadowSize = 0.5F;
	}

	@Override
	public void render(EntityTFIceBomb entity, float yaw, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffers, int light) {
		BlockState blockstate = entity.getBlockState();
		if (blockstate.getRenderType() == BlockRenderType.MODEL) {
			World world = entity.getEntityWorld();
			if (blockstate != world.getBlockState(entity.getPosition()) && blockstate.getRenderType() != BlockRenderType.INVISIBLE) {
				ms.push();
				BlockPos blockpos = new BlockPos(entity.getPosX(), entity.getBoundingBox().maxY, entity.getPosZ());
				ms.translate(-0.5D, 0.0D, -0.5D);
				BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
				for (net.minecraft.client.renderer.RenderType type : net.minecraft.client.renderer.RenderType.getBlockRenderTypes()) {
					if (RenderTypeLookup.canRenderInLayer(blockstate, type)) {
						net.minecraftforge.client.ForgeHooksClient.setRenderLayer(type);
						blockrendererdispatcher.getBlockModelRenderer().renderModel(world, blockrendererdispatcher.getModelForState(blockstate), blockstate, blockpos, ms, buffers.getBuffer(type), false, new Random(), blockstate.getPositionRandom(BlockPos.ZERO), OverlayTexture.NO_OVERLAY);
					}
				}
				net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
				ms.pop();
				super.render(entity, yaw, partialTicks, ms, buffers, light);
			}
		}
	}

	@Override
	public ResourceLocation getEntityTexture(EntityTFIceBomb entity) {
		return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
	}
}
