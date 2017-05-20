package twilightforest.client.renderer.entity;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import twilightforest.entity.EntityTFSlideBlock;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderTFSlideBlock extends Render<EntityTFSlideBlock> {

    public RenderTFSlideBlock(RenderManager manager) {
        super(manager);
        shadowSize = 0.0f;
    }

	@Override
	public void doRender(EntityTFSlideBlock sliderEntity, double x, double y, double z, float p_76986_8_, float time) {
        World world = sliderEntity.world;
        IBlockState block = sliderEntity.getBlockState();

        BlockPos pos = new BlockPos(sliderEntity);

        if (block != null && block.getBlock() != world.getBlockState(pos).getBlock())
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);

            // spin
            if (block.getValue(BlockRotatedPillar.AXIS) == EnumFacing.Axis.Y) {
            	GlStateManager.rotate((sliderEntity.ticksExisted + time) * 60F, 0, 1, 0);
            } else if (block.getValue(BlockRotatedPillar.AXIS) == EnumFacing.Axis.X) {
            	GlStateManager.rotate((sliderEntity.ticksExisted + time) * 60F, 1, 0, 0);
            } else if (block.getValue(BlockRotatedPillar.AXIS) == EnumFacing.Axis.Z) {
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
        }
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTFSlideBlock p_110775_1_) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}
