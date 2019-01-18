package twilightforest.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockTFPedestal implements ISimpleBlockRenderingHandler {
	
	final int renderID;

	public RenderBlockTFPedestal(int blockComplexRenderID) {
		this.renderID = blockComplexRenderID;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		renderInvJar(renderer, block, metadata);

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return renderPedestal(renderer, world, x, y, z, block);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return renderID;
	}

	/**
	 * Look, this is no longer in the Block class!  I'm an object oriented genius!
	 */
	public static boolean renderPedestal(RenderBlocks renderblocks, IBlockAccess world, int x, int y, int z, Block block) {
		GL11.glPushMatrix();
		// top
        renderblocks.setRenderBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.1875F, 0.9375F);
        renderblocks.renderStandardBlock(block, x, y, z);
        
        // middle
        renderblocks.setRenderBounds(0.125, 0.1875F, 0.125, 0.875F, 0.8125F, 0.875F);
        renderblocks.renderStandardBlock(block, x, y, z);
        
        // bottom
        renderblocks.setRenderBounds(0.0625F, 0.8125F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
        renderblocks.renderStandardBlock(block, x, y, z);
        
        block.setBlockBoundsForItemRender();
        GL11.glPopMatrix();
        return true;
	}
	
	public static void renderInvJar(RenderBlocks renderblocks, Block par1Block, int meta) {
		GL11.glPushMatrix();
		Tessellator tessellator =  Tessellator.instance;
		
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);        

		// top
        renderblocks.setRenderBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.1875F, 0.9375F);
        renderInvBlock(renderblocks, par1Block, meta, tessellator);
        
        // middle
        renderblocks.setRenderBounds(0.125, 0.1875F, 0.125, 0.875F, 0.8125F, 0.875F);
        renderInvBlock(renderblocks, par1Block, meta, tessellator);
        
        // bottom
        renderblocks.setRenderBounds(0.0625F, 0.8125F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
        renderInvBlock(renderblocks, par1Block, meta, tessellator);
        
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);

        par1Block.setBlockBoundsForItemRender();
        GL11.glPopMatrix();
	}

	protected static void renderInvBlock(RenderBlocks renderblocks, Block par1Block, int meta, Tessellator tessellator) {
		tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderblocks.renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(0, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderblocks.renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(1, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderblocks.renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(2, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderblocks.renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(3, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderblocks.renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(4, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderblocks.renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(5, meta));
        tessellator.draw();
	}


}
