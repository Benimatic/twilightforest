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
import twilightforest.entity.boss.EntityTFFallingIce;

import java.util.Random;

public class RenderTFFallingIce extends EntityRenderer<EntityTFFallingIce> {
	public RenderTFFallingIce(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize = 0.5F;
	}

	/**
	 * [VanillaCopy] {@link net.minecraft.client.renderer.entity.FallingBlockRenderer}, but scaled by 3
	 */
	@Override
	public void render(EntityTFFallingIce entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
		BlockState blockstate = entity.getBlockState();
		if (blockstate.getRenderType() == BlockRenderType.MODEL) {
			World world = entity.getWorldObj();
			if (blockstate != world.getBlockState(new BlockPos(entity)) && blockstate.getRenderType() != BlockRenderType.INVISIBLE) {
				stack.push();
				BlockPos blockpos = new BlockPos(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
				stack.translate(-0.5D, 0.0D, -0.5D);
				stack.scale(3, 3, 3); // TF - scale 3
				BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
				for (net.minecraft.client.renderer.RenderType type : net.minecraft.client.renderer.RenderType.getBlockLayers()) {
					if (RenderTypeLookup.canRenderInLayer(blockstate, type)) {
						net.minecraftforge.client.ForgeHooksClient.setRenderLayer(type);
						blockrendererdispatcher.getBlockModelRenderer().render(world, blockrendererdispatcher.getModelForState(blockstate), blockstate, blockpos, stack, buffer.getBuffer(type), false, new Random(), blockstate.getPositionRandom(entity.getOrigin()), OverlayTexture.DEFAULT_UV);
					}
				}
				net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
				stack.pop();
				super.render(entity, entityYaw, partialTicks, stack, buffer, light);
			}
		}
	}

	@Override
	public ResourceLocation getEntityTexture(EntityTFFallingIce entity) {
		return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
	}
}
