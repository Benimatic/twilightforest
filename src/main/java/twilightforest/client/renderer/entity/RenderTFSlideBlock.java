package twilightforest.client.renderer.entity;

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

public class RenderTFSlideBlock extends Render {
	
    private final RenderBlocks renderBlocks = new RenderBlocks();
    
    public RenderTFSlideBlock() {
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float time) {
		
		EntityTFSlideBlock sliderEntity = (EntityTFSlideBlock)entity;
		
        World world = entity.worldObj;
        Block block = sliderEntity.getBlock();
        int meta = sliderEntity.getMeta();
        
        int bx = MathHelper.floor(entity.posX);
        int by = MathHelper.floor(entity.posY);
        int bz = MathHelper.floor(entity.posZ);

        if (block != null && block != world.getBlock(bx, by, bz))
        {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x, (float)y + 0.5F, (float)z);
            
            // spin
            if ((meta & 12) == 0) {
            	GL11.glRotatef((entity.ticksExisted + time) * 60F, 0, 1, 0);
            } else if ((meta & 12) == 4) {
            	GL11.glRotatef((entity.ticksExisted + time) * 60F, 1, 0, 0);
            } else if ((meta & 12) == 8) {
            	GL11.glRotatef((entity.ticksExisted + time) * 60F, 0, 0, 1);
            }
            
            this.bindEntityTexture(entity);
            GL11.glDisable(GL11.GL_LIGHTING);
            Tessellator tessellator;

            ((BlockTFSlider) block).setBlockBoundsBasedOnMeta(meta);
            
            this.renderBlocks.setRenderBoundsFromBlock(block);
            this.renderBlocks.renderBlockSandFalling(block, world, bx, by, bz, meta);

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }

	}

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return TextureMap.locationBlocksTexture;
	}

}
