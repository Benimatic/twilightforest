package twilightforest.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import twilightforest.entity.boss.EntityTFIceBomb;

public class RenderTFThrownIce extends Render<EntityTFIceBomb> {

    public RenderTFThrownIce(RenderManager manager) {
        super(manager);
    }

    @Override
	public void doRender(EntityTFIceBomb var1, double var2, double var4, double var6, float var8, float var9) {
        World world = entity.world;
        Block block = entity.getBlock();
        int i = MathHelper.floor(entity.posX);
        int j = MathHelper.floor(entity.posY);
        int k = MathHelper.floor(entity.posZ);

        if (block != null && block != world.getBlock(i, j, k))
        {
        	GL11.glPushMatrix();
        	GL11.glTranslatef((float)x, (float)y, (float)z);
        	this.bindEntityTexture(entity);
        	GL11.glDisable(GL11.GL_LIGHTING);

        	this.renderBlocks.setRenderBoundsFromBlock(block);
        	this.renderBlocks.renderBlockSandFalling(block, world, i, j, k, 0);

        	GL11.glEnable(GL11.GL_LIGHTING);
        	GL11.glPopMatrix();
        }
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityTFIceBomb var1) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}
