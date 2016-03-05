package twilightforest.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockTFNagastone implements ISimpleBlockRenderingHandler {

	final int renderID;
	
	public RenderBlockTFNagastone(int nagastoneRenderID) {
		this.renderID = nagastoneRenderID;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        setRenderRotate(renderer, metadata);
		renderInvNormalBlock(renderer, block, metadata);
        restoreRendererRotate(renderer);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		// rotate the sides
        int meta = world.getBlockMetadata(x, y, z);
        
        setRenderRotate(renderer, meta);
        boolean didRender = renderer.renderStandardBlock(block, x, y, z);
        restoreRendererRotate(renderer);
        
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
		int type = meta & 12;
        int orient = meta & 3;

        // heads
        if (type == 0) 
        {
        	switch (orient)
        	{
        	case 0:
        		renderer.uvRotateTop = 1;
        		renderer.uvRotateBottom = 2;
        		break;
        	case 1:
        		renderer.uvRotateTop = 2;
        		renderer.uvRotateBottom = 1;
        		renderer.uvRotateSouth = 0;
        		break;
        	case 2:
        		renderer.uvRotateTop = 0;
        		renderer.uvRotateBottom = 3;
        		break;
        	case 3:
        		renderer.uvRotateTop = 3;
        		renderer.uvRotateBottom = 0;
        		break;
        	}
        }
        else if (type == 4 || type == 8)
        {
        	switch (orient)
        	{
        	case 0:
        		renderer.uvRotateTop = 2;
        		renderer.uvRotateBottom = 1;
        		renderer.uvRotateWest = 2;
        		break;
        	case 1:
        		renderer.uvRotateTop = 1;
        		renderer.uvRotateBottom = 2;
        		renderer.uvRotateEast = 2;
        		break;
        	case 2:
        		renderer.uvRotateTop = 3;
        		renderer.uvRotateBottom = 0;
        		renderer.uvRotateSouth = 2;
        		break;
        	case 3:
        		renderer.uvRotateTop = 0;
        		renderer.uvRotateBottom = 3;
        		renderer.uvRotateNorth = 2;
        		break;
        	}
        }
        else if (type == 12)
        {
        	switch (orient)
        	{
        	case 0:
        		renderer.uvRotateTop = 0;
        		renderer.uvRotateBottom = 0;
        		break;
        	case 1:
        		renderer.uvRotateTop = 1;
        		renderer.uvRotateBottom = 1;
        		break;
        	case 2:
        		renderer.uvRotateNorth = 2;
        		renderer.uvRotateSouth = 2;
        		renderer.uvRotateEast = 2;
        		renderer.uvRotateWest = 2;
        		break;
        	}
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
		
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);        

		// render jar portion
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
