package twilightforest.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
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
	public void doRender(EntityTFIceBomb entity, double var2, double var4, double var6, float var8, float var9) {
        World world = entity.world;
        Block block = entity.getBlock();
        int i = MathHelper.floor(entity.posX);
        int j = MathHelper.floor(entity.posY);
        int k = MathHelper.floor(entity.posZ);

        if (block != null && block != world.getBlockState(new BlockPos(i, j, k)).getBlock())
        {
        	GlStateManager.pushMatrix();
        	GlStateManager.translate((float)x, (float)y, (float)z);
        	this.bindEntityTexture(entity);
            GlStateManager.disableLighting();

        	this.renderBlocks.setRenderBoundsFromBlock(block);
        	this.renderBlocks.renderBlockSandFalling(block, world, i, j, k, 0);

        	GlStateManager.enableLighting();
        	GlStateManager.popMatrix();
        }
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityTFIceBomb var1) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}
