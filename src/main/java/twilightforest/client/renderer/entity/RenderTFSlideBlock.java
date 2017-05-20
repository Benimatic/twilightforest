package twilightforest.client.renderer.entity;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import twilightforest.block.BlockTFSlider;
import twilightforest.entity.EntityTFSlideBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderTFSlideBlock extends Render<EntityTFSlideBlock> {

    public RenderTFSlideBlock(RenderManager manager) {
        super(manager);
        shadowSize = 0.0f;
    }

	@Override
	public void doRender(EntityTFSlideBlock entity, double x, double y, double z, float p_76986_8_, float time) {
		
		EntityTFSlideBlock sliderEntity = (EntityTFSlideBlock)entity;
		
        World world = entity.world;
        IBlockState block = sliderEntity.getBlockState();
        
        int bx = MathHelper.floor(entity.posX);
        int by = MathHelper.floor(entity.posY);
        int bz = MathHelper.floor(entity.posZ);

        if (block != null && block.getBlock() != world.getBlockState(new BlockPos(bx, by, bz)).getBlock())
        {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x, (float)y + 0.5F, (float)z);
            
            // spin
            if (block.getValue(BlockRotatedPillar.AXIS) == EnumFacing.Axis.Y) {
            	GL11.glRotatef((entity.ticksExisted + time) * 60F, 0, 1, 0);
            } else if (block.getValue(BlockRotatedPillar.AXIS) == EnumFacing.Axis.X) {
            	GL11.glRotatef((entity.ticksExisted + time) * 60F, 1, 0, 0);
            } else if (block.getValue(BlockRotatedPillar.AXIS) == EnumFacing.Axis.Z) {
            	GL11.glRotatef((entity.ticksExisted + time) * 60F, 0, 0, 1);
            }
            
            this.bindEntityTexture(entity);
            GL11.glDisable(GL11.GL_LIGHTING);
            Tessellator tessellator;

            this.renderBlocks.setRenderBoundsFromBlock(block);
            this.renderBlocks.renderBlockSandFalling(block, world, bx, by, bz, meta);

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }

	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTFSlideBlock p_110775_1_) {
		return TextureMap.locationBlocksTexture;
	}

}
