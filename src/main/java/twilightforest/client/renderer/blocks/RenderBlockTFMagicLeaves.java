package twilightforest.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockTFMagicLeaves implements ISimpleBlockRenderingHandler {

	final int renderID;
	
	public RenderBlockTFMagicLeaves(int myRenderID) {
		this.renderID = myRenderID;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glPushMatrix();
        setRenderRotate(renderer, metadata);
		renderInvNormalBlock(renderer, block, metadata);
        restoreRendererRotate(renderer);
        GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		GL11.glPushMatrix();
		// rotate the sides
        int meta = world.getBlockMetadata(x, y, z);
        
        setRenderRotate(renderer, meta, x, y, z);
        boolean didRender = renderer.renderStandardBlock(block, x, y, z);
        restoreRendererRotate(renderer);
        GL11.glPopMatrix();
        
        return didRender;

	}

	private void restoreRendererRotate(RenderBlocks renderer) {
		renderer.uvRotateSouth = 0;
        renderer.uvRotateEast = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateNorth = 0;
        renderer.uvRotateTop = 0;
        renderer.uvRotateBottom = 0;
	}

	private void setRenderRotate(RenderBlocks renderer, int meta) {
		this.setRenderRotate(renderer, meta, 0, 0, 0);
	}

	private void setRenderRotate(RenderBlocks renderer, int meta, int x, int y, int z) {
        int type = meta & 3;

        if (type == 0)
        {
        	renderer.uvRotateEast = 3;
        	renderer.uvRotateBottom = 0;

        	renderer.uvRotateNorth = 2;
        	renderer.uvRotateSouth = 2;
        }
        else if (type == 1)
        {
        	// bottom and top are semi-random
        	renderer.uvRotateBottom = (x + y + z) & 3;
        	renderer.uvRotateTop = (x + y + z) & 3;
        	
        	// sides flow down
        	renderer.uvRotateEast = 1;
        	renderer.uvRotateWest = 2;

        	renderer.uvRotateNorth = 2;
        	renderer.uvRotateSouth = 1;
        }
        else if (type == 2)
        {
        	// bottom and top are semi-random
        	renderer.uvRotateBottom = (x + y + z) & 3;
        	renderer.uvRotateTop = (x + y + z) & 3;
        	
        	// sides flow up
        	renderer.uvRotateEast = 2;
        	renderer.uvRotateWest = 1;

        	renderer.uvRotateNorth = 1;
        	renderer.uvRotateSouth = 2;
        }
        else if (type == 3)
        {
        	// all semi-random
        	renderer.uvRotateBottom = (x + y + z) & 3;
        	renderer.uvRotateTop = (x + y + z) & 3;
        	
        	renderer.uvRotateEast = (x + y + z) & 3;;
        	renderer.uvRotateWest = (x + y + z) & 3;;

        	renderer.uvRotateNorth = (x + y + z) & 3;;
        	renderer.uvRotateSouth = (x + y + z) & 3;;
        }


	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return renderID;
	}
	
	
	public static void renderInvNormalBlock(RenderBlocks renderblocks, Block par1Block, int meta) {
		Tessellator tessellator =  Tessellator.instance;
		
        if (renderblocks.useInventoryTint)
        {
            int colorInt = par1Block.getRenderColor(meta);

            float red = (float)(colorInt >> 16 & 255) / 255.0F;
            float green = (float)(colorInt >> 8 & 255) / 255.0F;
            float blue = (float)(colorInt & 255) / 255.0F;
            GL11.glColor4f(red, green, blue, 1.0F);
        }
		
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		// block!
        par1Block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

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
