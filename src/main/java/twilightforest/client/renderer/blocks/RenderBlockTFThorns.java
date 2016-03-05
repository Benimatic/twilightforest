package twilightforest.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import twilightforest.block.TFBlocks;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockTFThorns implements ISimpleBlockRenderingHandler {
	
	final int renderID;

	public RenderBlockTFThorns(int blockComplexRenderID) {
		this.renderID = blockComplexRenderID;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		renderInvBlock(renderer, block, metadata);

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        int l = block.colorMultiplier(world, x, y, z);
        float f = (float)(l >> 16 & 255) / 255.0F;
        float f1 = (float)(l >> 8 & 255) / 255.0F;
        float f2 = (float)(l & 255) / 255.0F;
        
        int metadata = world.getBlockMetadata(x, y, z);
        int type = metadata & 12;
        
        switch (type) {
        case 0:
    	default:
            return this.renderCactusLikeY(block, x, y, z, f, f1, f2, metadata, world, renderer);
        case 4:
            return this.renderCactusLikeX(block, x, y, z, f, f1, f2, metadata, world, renderer);
        case 8:
            return this.renderCactusLikeZ(block, x, y, z, f, f1, f2, metadata, world, renderer);
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

    /**
	 * Render block cactus implementation
     * @param metadata 
	 */
	public boolean renderCactusLikeX(Block block, int x, int y, int z, float red, float green, float blue, int metadata, IBlockAccess world, RenderBlocks renderer)
	{
		setUVRotationX(renderer);
		
	    Tessellator tessellator = Tessellator.instance;
	    float middle = 0.5F;
	    float full = 1.0F;
	    float f5 = 0.8F;
	    float f6 = 0.6F;
	    float bRed = middle * red;
	    float tRed = full * red;
	    float zRed = f5 * red;
	    float xRed = f6 * red;
	    float bGreen = middle * green;
	    float tGreen = full * green;
	    float zGreen = f5 * green;
	    float xGreen = f6 * green;
	    float bBlue = middle * blue;
	    float tBlue = full * blue;
	    float zBlue = f5 * blue;
	    float xBlue = f6 * blue;
	    float onePixel = 0.0625F * 3F;
	    int blockBrightness = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
	
	    if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x - 1, y, z, 4))
	    {
	        tessellator.setBrightness(renderer.renderMinX > 0.0D ? blockBrightness : block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z));
	        tessellator.setColorOpaque_F(bRed, bGreen, bBlue);
	        renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 4));
	    }
	
	    if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x + 1, y, z, 5))
	    {
	        tessellator.setBrightness(renderer.renderMaxX < 1.0D ? blockBrightness : block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z));
	        tessellator.setColorOpaque_F(tRed, tGreen, tBlue);
	        renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 5));
	    }
	
	    drawXSides(block, x, y, z, renderer, metadata, zRed, xRed, zGreen, xGreen, zBlue, xBlue, onePixel, blockBrightness);
	    
		resetUVRotation(renderer);
		
        
        if (canConnectTo(world, x, y, z + 1)) {
           	setUVRotationZ(renderer);
        	renderer.setRenderBounds(0F, 0F, 1F - onePixel, 1F, 1F, 1F);
            drawZSides(block, x, y, z, renderer, metadata, zRed, xRed, zGreen, xGreen, zBlue, xBlue, 0.063F  * 3F, blockBrightness);
        	resetUVRotation(renderer);
        }
        
        if (canConnectTo(world, x, y, z - 1)) {
           	setUVRotationZ(renderer);
        	renderer.setRenderBounds(0F, 0F, 0F, 1F, 1F, onePixel);
            drawZSides(block, x, y, z, renderer, metadata, zRed, xRed, zGreen, xGreen, zBlue, xBlue, 0.063F  * 3F, blockBrightness);
        	resetUVRotation(renderer);
        }
	    
        if (canConnectTo(world, x, y + 1, z)) {
        	resetUVRotation(renderer);
        	renderer.setRenderBounds(0F, 1F - onePixel, 0F, 1F, 1F, 1F);
            drawYSides(block, x, y, z, renderer, metadata, zRed, xRed, zGreen, xGreen, zBlue, xBlue, 0.063F  * 3F, blockBrightness);
        }
        
        if (canConnectTo(world, x, y - 1, z)) {
        	resetUVRotation(renderer);
        	renderer.setRenderBounds(0F, 0F, 0F, 1F, onePixel, 1F);
            drawYSides(block, x, y, z, renderer, metadata, zRed, xRed, zGreen, xGreen, zBlue, xBlue, 0.063F  * 3F, blockBrightness);
        }
	    
	    
	    return true;
	}

	/**
     * Render block cactus implementation
	 * @param metadata 
     */
    public boolean renderCactusLikeY(Block block, int x, int y, int z, float red, float green, float blue, int metadata, IBlockAccess world, RenderBlocks renderer)
    {
        Tessellator tessellator = Tessellator.instance;
        float middle = 0.5F;
        float full = 1.0F;
        float f5 = 0.8F;
        float f6 = 0.6F;
        float bRed = middle * red;
        float tRed = full * red;
        float zRed = f5 * red;
        float xRed = f6 * red;
        float bGreen = middle * green;
        float tGreen = full * green;
        float zGreen = f5 * green;
        float xGreen = f6 * green;
        float bBlue = middle * blue;
        float tBlue = full * blue;
        float zBlue = f5 * blue;
        float xBlue = f6 * blue;
        float onePixel = 0.0625F * 3F;
        int blockBrightness = block.getMixedBrightnessForBlock(world, x, y, z);

        if (renderer.renderAllFaces || block.shouldSideBeRendered(world, x, y - 1, z, 0))
        {
            tessellator.setBrightness(renderer.renderMinY > 0.0D ? blockBrightness : block.getMixedBrightnessForBlock(world, x, y - 1, z));
            tessellator.setColorOpaque_F(bRed, bGreen, bBlue);
            renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, renderer.getBlockIcon(block, world, x, y, z, 0));
        }

        if (renderer.renderAllFaces || block.shouldSideBeRendered(world, x, y + 1, z, 1))
        {
            tessellator.setBrightness(renderer.renderMaxY < 1.0D ? blockBrightness : block.getMixedBrightnessForBlock(world, x, y + 1, z));
            tessellator.setColorOpaque_F(tRed, tGreen, tBlue);
            renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, renderer.getBlockIcon(block, world, x, y, z, 1));
        }

        drawYSides(block, x, y, z, renderer, metadata, zRed, xRed, zGreen, xGreen, zBlue, xBlue, onePixel, blockBrightness);
        
        if (canConnectTo(world, x + 1, y, z)) {
           	setUVRotationX(renderer);
        	renderer.setRenderBounds(1F - onePixel, 0F, 0F, 1F, 1F, 1F);
            drawXSides(block, x, y, z, renderer, metadata, zRed, xRed, zGreen, xGreen, zBlue, xBlue, 0.063F  * 3F, blockBrightness);
        	resetUVRotation(renderer);
        }
        
        if (canConnectTo(world, x - 1, y, z)) {
           	setUVRotationX(renderer);
        	renderer.setRenderBounds(0F, 0F, 0F, onePixel, 1F, 1F);
            drawXSides(block, x, y, z, renderer, metadata, zRed, xRed, zGreen, xGreen, zBlue, xBlue, 0.063F  * 3F, blockBrightness);
        	resetUVRotation(renderer);
        }
        
        if (canConnectTo(world, x, y, z + 1)) {
           	setUVRotationZ(renderer);
        	renderer.setRenderBounds(0F, 0F, 1F - onePixel, 1F, 1F, 1F);
            drawZSides(block, x, y, z, renderer, metadata, zRed, xRed, zGreen, xGreen, zBlue, xBlue, 0.063F  * 3F, blockBrightness);
        	resetUVRotation(renderer);
        }
        
        if (canConnectTo(world, x, y, z - 1)) {
           	setUVRotationZ(renderer);
        	renderer.setRenderBounds(0F, 0F, 0F, 1F, 1F, onePixel);
            drawZSides(block, x, y, z, renderer, metadata, zRed, xRed, zGreen, xGreen, zBlue, xBlue, 0.063F  * 3F, blockBrightness);
        	resetUVRotation(renderer);
        }
        
        
        return true;
    }

	/**
	 * Render block cactus implementation
	 * @param metadata 
	 */
	public boolean renderCactusLikeZ(Block block, int x, int y, int z, float red, float green, float blue, int metadata, IBlockAccess world, RenderBlocks renderer)
	{
		setUVRotationZ(renderer);
		
	    Tessellator tessellator = Tessellator.instance;
	    float middle = 0.5F;
	    float full = 1.0F;
	    float f5 = 0.8F;
	    float f6 = 0.6F;
	    float bRed = middle * red;
	    float tRed = full * red;
	    float zRed = f5 * red;
	    float xRed = f6 * red;
	    float bGreen = middle * green;
	    float tGreen = full * green;
	    float zGreen = f5 * green;
	    float xGreen = f6 * green;
	    float bBlue = middle * blue;
	    float tBlue = full * blue;
	    float zBlue = f5 * blue;
	    float xBlue = f6 * blue;
	    float onePixel = 0.0625F * 3F;
	    int blockBrightness = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
	
	    if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y, z - 1, 0))
	    {
	        tessellator.setBrightness(renderer.renderMinZ > 0.0D ? blockBrightness : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z - 1));
	        tessellator.setColorOpaque_F(bRed, bGreen, bBlue);
	        renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 2));
	    }
	
	    if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y, z + 1, 1))
	    {
	        tessellator.setBrightness(renderer.renderMaxZ < 1.0D ? blockBrightness : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z + 1));
	        tessellator.setColorOpaque_F(tRed, tGreen, tBlue);
	        renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 3));
	    }
	
	    drawZSides(block, x, y, z, renderer, metadata, zRed, xRed, zGreen, xGreen, zBlue, xBlue, onePixel, blockBrightness);
	    
		resetUVRotation(renderer);
		
        if (canConnectTo(world, x + 1, y, z)) {
           	setUVRotationX(renderer);
        	renderer.setRenderBounds(1F - onePixel, 0F, 0F, 1F, 1F, 1F);
            drawXSides(block, x, y, z, renderer, metadata, zRed, xRed, zGreen, xGreen, zBlue, xBlue, 0.063F  * 3F, blockBrightness);
        	resetUVRotation(renderer);
        }
        
        if (canConnectTo(world, x - 1, y, z)) {
           	setUVRotationX(renderer);
        	renderer.setRenderBounds(0F, 0F, 0F, onePixel, 1F, 1F);
            drawXSides(block, x, y, z, renderer, metadata, zRed, xRed, zGreen, xGreen, zBlue, xBlue, 0.063F  * 3F, blockBrightness);
        	resetUVRotation(renderer);
        }
	    
        if (canConnectTo(world, x, y + 1, z)) {
        	resetUVRotation(renderer);
        	renderer.setRenderBounds(0F, 1F - onePixel, 0F, 1F, 1F, 1F);
            drawYSides(block, x, y, z, renderer, metadata, zRed, xRed, zGreen, xGreen, zBlue, xBlue, 0.063F  * 3F, blockBrightness);
        }
        
        if (canConnectTo(world, x, y - 1, z)) {
        	resetUVRotation(renderer);
        	renderer.setRenderBounds(0F, 0F, 0F, 1F, onePixel, 1F);
            drawYSides(block, x, y, z, renderer, metadata, zRed, xRed, zGreen, xGreen, zBlue, xBlue, 0.063F  * 3F, blockBrightness);
        }
	    
	    return true;
	}

	private void drawXSides(Block block, int x, int y, int z, RenderBlocks renderer, int metadata, float zRed,
			float xRed, float zGreen, float xGreen, float zBlue, float xBlue, float onePixel, int l) {
		Tessellator tessellator = Tessellator.instance;
		
		tessellator.setBrightness(l);
	    tessellator.setColorOpaque_F(zRed, zGreen, zBlue);
	    tessellator.addTranslation(0.0F, 0.0F, onePixel);
	    renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, getSideIcon(block, metadata));
	    tessellator.addTranslation(0.0F, 0.0F, -onePixel);
	    tessellator.addTranslation(0.0F, 0.0F, -onePixel);
	    renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, getSideIcon(block, metadata));
	    tessellator.addTranslation(0.0F, 0.0F, onePixel);
	    tessellator.setColorOpaque_F(xRed, xGreen, xBlue);
	    tessellator.addTranslation(0.0F, onePixel, 0.0F);
	    renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, getSideIcon(block, metadata));
	    tessellator.addTranslation(0.0F, -onePixel, 0.0F);
	    tessellator.addTranslation(0.0F, -onePixel, 0.0F);
	    renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, getSideIcon(block, metadata));
	    tessellator.addTranslation(0.0F, onePixel, 0.0F);
	}

	private void drawYSides(Block block, int x, int y, int z, RenderBlocks renderer, int metadata, 
			float zRed, float xRed, float zGreen, float xGreen, float zBlue, float xBlue, float onePixel, int blockBrightness) {
	    Tessellator tessellator = Tessellator.instance;
	
		tessellator.setBrightness(blockBrightness);
	    tessellator.setColorOpaque_F(zRed, zGreen, zBlue);
	    tessellator.addTranslation(0.0F, 0.0F, onePixel);
	    renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, getSideIcon(block, metadata));
	    tessellator.addTranslation(0.0F, 0.0F, -onePixel);
	    tessellator.addTranslation(0.0F, 0.0F, -onePixel);
	    renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, getSideIcon(block, metadata));
	    tessellator.addTranslation(0.0F, 0.0F, onePixel);
	    tessellator.setColorOpaque_F(xRed, xGreen, xBlue);
	    tessellator.addTranslation(onePixel, 0.0F, 0.0F);
	    renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, getSideIcon(block, metadata));
	    tessellator.addTranslation(-onePixel, 0.0F, 0.0F);
	    tessellator.addTranslation(-onePixel, 0.0F, 0.0F);
	    renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, getSideIcon(block, metadata));
	    tessellator.addTranslation(onePixel, 0.0F, 0.0F);
	}

	private void drawZSides(Block block, int x, int y, int z, RenderBlocks renderer, int metadata, 
			float zRed, float xRed, float zGreen, float xGreen, float zBlue, float xBlue, float onePixel, int blockBrightness) {
	    Tessellator tessellator = Tessellator.instance;
	
		tessellator.setBrightness(blockBrightness);
	    tessellator.setColorOpaque_F(xRed, xGreen, xBlue);
	    tessellator.addTranslation(onePixel, 0.0F, 0.0F);
	    renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, getSideIcon(block, metadata));
	    tessellator.addTranslation(-onePixel, 0.0F, 0.0F);
	    tessellator.addTranslation(-onePixel, 0.0F, 0.0F);
	    renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, getSideIcon(block, metadata));
	    tessellator.addTranslation(onePixel, 0.0F, 0.0F);
	    tessellator.setColorOpaque_F(zRed, zGreen, zBlue);
	    tessellator.addTranslation(0.0F, onePixel, 0.0F);
	    renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, getSideIcon(block, metadata));
	    tessellator.addTranslation(0.0F, -onePixel, 0.0F);
	    tessellator.addTranslation(0.0F, -onePixel, 0.0F);
	    renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, getSideIcon(block, metadata));
	    tessellator.addTranslation(0.0F, onePixel, 0.0F);
	}

	private boolean canConnectTo(IBlockAccess world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		return block == TFBlocks.thorns || block == TFBlocks.burntThorns || block == TFBlocks.thornRose;
	}

	private void setUVRotationX(RenderBlocks renderer) {
		renderer.uvRotateEast = 1;
    	renderer.uvRotateWest = 1;
    	renderer.uvRotateTop = 1;
    	renderer.uvRotateBottom = 1;
	}

	private void setUVRotationZ(RenderBlocks renderer) {
		renderer.uvRotateSouth = 1;
		renderer.uvRotateNorth = 1;
	}

	private void resetUVRotation(RenderBlocks renderer) {
    	renderer.uvRotateTop = 0;
    	renderer.uvRotateBottom = 0;
		renderer.uvRotateEast = 0;
    	renderer.uvRotateWest = 0;
		renderer.uvRotateNorth = 0;
    	renderer.uvRotateSouth = 0;
	}

	private IIcon getSideIcon(Block block, int metadata) {
		return block.getIcon(2, metadata & 3);
	}
	
	public static void renderInvBlock(RenderBlocks renderblocks, Block par1Block, int meta) {
		Tessellator tessellator =  Tessellator.instance;
		
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);        

//		// top
//        renderblocks.setRenderBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.1875F, 0.9375F);
//        renderInvBlock(renderblocks, par1Block, meta, tessellator);
//        
//        // middle
//        renderblocks.setRenderBounds(0.125, 0.1875F, 0.125, 0.875F, 0.8125F, 0.875F);
//        renderInvBlock(renderblocks, par1Block, meta, tessellator);
//        
//        // bottom
//        renderblocks.setRenderBounds(0.0625F, 0.8125F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
        renderInvBlock(renderblocks, par1Block, meta, tessellator);
        
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);

        par1Block.setBlockBoundsForItemRender();
	}

	protected static void renderInvBlock(RenderBlocks renderblocks, Block par1Block, int meta, Tessellator tessellator) {
	    float onePixel = 0.0625F * 3F;

		
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
        renderblocks.renderFaceXPos(par1Block, -onePixel, 0.0D, 0.0D, par1Block.getIcon(2, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderblocks.renderFaceXNeg(par1Block, onePixel, 0.0D, 0.0D, par1Block.getIcon(3, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderblocks.renderFaceZNeg(par1Block, 0.0D, 0.0D, onePixel, par1Block.getIcon(4, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderblocks.renderFaceZPos(par1Block, 0.0D, 0.0D, -onePixel, par1Block.getIcon(5, meta));
        tessellator.draw();
	}


}
