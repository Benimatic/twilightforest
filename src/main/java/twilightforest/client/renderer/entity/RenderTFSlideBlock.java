package twilightforest.client.renderer.entity;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import twilightforest.entity.EntityTFSlideBlock;

public class RenderTFSlideBlock extends Render<EntityTFSlideBlock> {

	public RenderTFSlideBlock(RenderManager manager) {
		super(manager);
		shadowSize = 0.0f;
	}

	// [VanillaCopy] RenderFallingBlock, with spin
	@Override
	public void doRender(EntityTFSlideBlock entity, double x, double y, double z, float yaw, float partialTicks) {

		if (entity.getBlockState() != null) {
			IBlockState iblockstate = entity.getBlockState();

			if (iblockstate.getRenderType() == EnumBlockRenderType.MODEL) {
				World world = entity.world;

				if (iblockstate != world.getBlockState(new BlockPos(entity)) && iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE) {
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
					BlockPos blockpos = new BlockPos(entity.posX, entity.getEntityBoundingBox().maxY, entity.posZ);

					// spin
					if (iblockstate.getPropertyKeys().contains(BlockRotatedPillar.AXIS)) {
						EnumFacing.Axis axis = iblockstate.getValue(BlockRotatedPillar.AXIS);
						float angle = (entity.ticksExisted + partialTicks) * 60F;
						double dy = y + 0.5;
						GlStateManager.translate((float) x, (float) dy, (float) z);
						if (axis == EnumFacing.Axis.Y) {
							GlStateManager.rotate(angle, 0F, 1F, 0F);
						} else if (axis == EnumFacing.Axis.X) {
							GlStateManager.rotate(angle, 1F, 0F, 0F);
						} else if (axis == EnumFacing.Axis.Z) {
							GlStateManager.rotate(angle, 0F, 0F, 1F);
						}
						GlStateManager.translate((float) -x, (float) -dy, (float) -z);
					}

					GlStateManager.translate((float) (x - (double) blockpos.getX() - 0.5D), (float) (y - (double) blockpos.getY()), (float) (z - (double) blockpos.getZ() - 0.5D));

					BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
					blockrendererdispatcher.getBlockModelRenderer().renderModel(world, blockrendererdispatcher.getModelForState(iblockstate), iblockstate, blockpos, bufferbuilder, false, 0L);
					tessellator.draw();

					if (this.renderOutlines) {
						GlStateManager.disableOutlineMode();
						GlStateManager.disableColorMaterial();
					}

					GlStateManager.enableLighting();
					GlStateManager.popMatrix();
					super.doRender(entity, x, y, z, yaw, partialTicks);
				}
			}
		}


        /*World world = sliderEntity.world;
		IBlockState block = sliderEntity.getBlockState();

        BlockPos pos = new BlockPos(sliderEntity);

        if (block != null && block.getBlock() != world.getBlockState(pos).getBlock())
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);

            // spin
            if (block.getValue(BlockRotatedPillar.AXIS_FACING) == EnumFacing.Axis.Y) {
            	GlStateManager.rotate((sliderEntity.ticksExisted + time) * 60F, 0, 1, 0);
            } else if (block.getValue(BlockRotatedPillar.AXIS_FACING) == EnumFacing.Axis.X) {
            	GlStateManager.rotate((sliderEntity.ticksExisted + time) * 60F, 1, 0, 0);
            } else if (block.getValue(BlockRotatedPillar.AXIS_FACING) == EnumFacing.Axis.Z) {
            	GlStateManager.rotate((sliderEntity.ticksExisted + time) * 60F, 0, 0, 1);
            }

            this.bindEntityTexture(sliderEntity);
            GlStateManager.disableLighting();

            Tessellator.getInstance().getBuffer().begin(7, DefaultVertexFormats.BLOCK);
            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            blockrendererdispatcher.getBlockModelRenderer().renderModel(world, blockrendererdispatcher.getModelForState(block), block, pos, Tessellator.getInstance().getBuffer(), false, MathHelper.getPositionRandom(pos));
            Tessellator.getInstance().draw();

            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }*/
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTFSlideBlock entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}
