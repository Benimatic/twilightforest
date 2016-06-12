package twilightforest.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockTFKnightMetal implements ISimpleBlockRenderingHandler {
	
	final int renderID;

	public RenderBlockTFKnightMetal(int blockComplexRenderID) {
		this.renderID = blockComplexRenderID;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		renderInvJar(renderer, block, metadata);

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return renderSpikeBlock(renderer, world, x, y, z, block);
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
	public static boolean renderSpikeBlock(RenderBlocks renderblocks, IBlockAccess world, int x, int y, int z, Block block) {
		float p = 1F / 16F;
		float a = 1F / 1024F;
		float p4 = 4F / 16F - a;
		
		for (int rx = 0; rx < 3; rx++) {
			for (int ry = 0; ry < 3; ry++) {
				for (int rz = 0; rz < 3; rz++) {
					renderblocks.setRenderBounds(rx * 6F * p + a, ry * 6F * p + a, rz * 6F * p + a, rx * 6F * p + p4, ry * 6F * p + p4, rz * 6F * p + p4);
					renderblocks.renderStandardBlock(block, x, y, z);
				}
			}
		}
   
        // middle
        renderblocks.setRenderBounds(p, p, p, 15 * p, 15 * p, 15 * p);
        renderblocks.renderStandardBlock(block, x, y, z);

        
        block.setBlockBoundsForItemRender();
        return true;
	}
	
	public static void renderInvJar(RenderBlocks renderblocks, Block par1Block, int meta) {
		Tessellator tessellator =  Tessellator.instance;
		
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);        

        // spikes
		float p = 1F / 16F;
		float a = 1F / 1024F;
		float p4 = 4F / 16F - a;
		
		for (int rx = 0; rx < 3; rx++) {
			for (int ry = 0; ry < 3; ry++) {
				for (int rz = 0; rz < 3; rz++) {
					renderblocks.setRenderBounds(rx * 6F * p + a, ry * 6F * p + a, rz * 6F * p + a, rx * 6F * p + p4, ry * 6F * p + p4, rz * 6F * p + p4);
			        renderInvBlock(renderblocks, par1Block, meta, tessellator);

				}
			}
		}
   
        // middle
        renderblocks.setRenderBounds(p, p, p, 15 * p, 15 * p, 15 * p);
        renderInvBlock(renderblocks, par1Block, meta, tessellator);

        
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);

        par1Block.setBlockBoundsForItemRender();
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
