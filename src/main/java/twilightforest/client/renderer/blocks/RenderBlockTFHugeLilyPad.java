package twilightforest.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockTFHugeLilyPad implements ISimpleBlockRenderingHandler {
	
	final int renderID;

	public RenderBlockTFHugeLilyPad(int blockComplexRenderID) {
		this.renderID = blockComplexRenderID;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		;
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
		int orient = meta >> 2;
		
		// why can't this just be simple?
		if (orient == 2) {
			orient = 3;
		} else if (orient == 3) {
			orient = 2;
		}

		renderer.uvRotateTop = orient;
		renderer.uvRotateBottom = orient;

	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return renderID;
	}


}
