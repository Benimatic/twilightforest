package twilightforest.client.renderer.entity;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import twilightforest.entity.boss.EntityTFIceBomb;

// [VanillaCopy] direct of RenderFallingBlock because of generic type restrictions
public class RenderTFThrownIce<T extends EntityTFIceBomb> extends EntityRenderer<T> {

	public RenderTFThrownIce(EntityRendererManager manager) {
		super(manager);
		this.shadowSize = 0.5F;
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if (entity.getBlock() != null) {
			BlockState iblockstate = entity.getBlock();

			if (iblockstate.getRenderType() == BlockRenderType.MODEL) {
				World world = entity.world;

				if (iblockstate != world.getBlockState(new BlockPos(entity)) && iblockstate.getRenderType() != BlockRenderType.INVISIBLE) {
					this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
					GlStateManager.pushMatrix();
					GlStateManager.disableLighting();
					Tessellator tessellator = Tessellator.getInstance();
					BufferBuilder bufferbuilder = tessellator.getBuffer();

					if (this.renderOutlines) {
						GlStateManager.enableColorMaterial();
						GlStateManager.enableOutlineMode(this.getTeamColor(entity));
					}

					bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
					BlockPos blockpos = new BlockPos(entity.posX, entity.getBoundingBox().maxY, entity.posZ);
					GlStateManager.translatef((float) (x - (double) blockpos.getX() - 0.5D), (float) (y - (double) blockpos.getY()), (float) (z - (double) blockpos.getZ() - 0.5D));
					BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
					blockrendererdispatcher.getBlockModelRenderer().renderModel(world, blockrendererdispatcher.getModelForState(iblockstate), iblockstate, blockpos, bufferbuilder, false, MathHelper.getPositionRandom(BlockPos.ORIGIN));
					tessellator.draw();

					if (this.renderOutlines) {
						GlStateManager.disableOutlineMode();
						GlStateManager.disableColorMaterial();
					}

					GlStateManager.enableLighting();
					GlStateManager.popMatrix();
					super.doRender(entity, x, y, z, entityYaw, partialTicks);
				}
			}
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}
