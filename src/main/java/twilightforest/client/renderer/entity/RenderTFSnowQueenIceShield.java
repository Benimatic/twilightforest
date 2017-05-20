package twilightforest.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import twilightforest.entity.boss.EntityTFSnowQueenIceShield;

public class RenderTFSnowQueenIceShield extends Render<EntityTFSnowQueenIceShield> {

    public RenderTFSnowQueenIceShield(RenderManager manager) {
        super(manager);
    }

    @Override
	public void doRender(EntityTFSnowQueenIceShield var1, double var2, double var4, double var6, float var8, float var9) {
        this.doRender((EntityTFSnowQueenIceShield)var1, var2, var4, var6, var8, var9);
        World world = entity.world;
        Block block = Blocks.PACKED_ICE;
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
	protected ResourceLocation getEntityTexture(EntityTFSnowQueenIceShield var1) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}
