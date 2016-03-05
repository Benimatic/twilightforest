package twilightforest.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import twilightforest.block.BlockTFPlant;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBlockTFPlants implements ISimpleBlockRenderingHandler {

	final int renderID;

	public RenderBlockTFPlants(int blockRenderID) {
		this.renderID = blockRenderID;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == BlockTFPlant.META_MOSSPATCH) {
			renderMossPatch(x, y, z, block, renderer);
			
		}
		else if (meta == BlockTFPlant.META_CLOVERPATCH) {
			renderCloverPatch(x, y, z, block, renderer);

		}
		else if (meta == BlockTFPlant.META_MAYAPPLE) {
			renderMayapple(x, y, z, block, renderer);
		}
		else if (meta == BlockTFPlant.META_ROOT_STRAND) {
			renderer.renderBlockCrops(block, x, y, z);
		}
		else 
		{
			renderer.renderCrossedSquares(block, x, y, z);
		}
		
		return true;
	}

	private void renderMayapple(int x, int y, int z, Block block,
			RenderBlocks renderer) {
		renderer.clearOverrideBlockTexture();
		renderer.setRenderBounds(4F / 16F, 6F / 16F, 4F / 16F, 13F / 16F, 6F / 16F, 13F / 16F);
		renderer.renderStandardBlock(block, x, y, z);
		
		renderer.overrideBlockTexture = BlockTFPlant.mayappleSide;
		renderer.setRenderBounds(8F / 16F, 0F, 8F / 16F, 9F / 16F, 5.99F / 16F, 9F / 16F);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.clearOverrideBlockTexture();
	}

	private void renderCloverPatch(int x, int y, int z, Block block,
			RenderBlocks renderer) {
		renderer.renderMinY = renderer.renderMaxY;
		renderer.renderStandardBlock(block, x, y, z);
		
		renderer.renderMinY = 0F;
		renderer.renderMaxY -= 0.01F;
		
		renderer.renderMinX += 1F / 16F;
		renderer.renderMinZ += 1F / 16F;
		renderer.renderMaxX -= 1F / 16F;
		renderer.renderMaxZ -= 1F / 16F;
		renderer.renderStandardBlock(block, x, y, z);
	}

	private void renderMossPatch(int x, int y, int z, Block block,
			RenderBlocks renderer) {
		renderer.renderStandardBlock(block, x, y, z);
		
		// add on shaggy edges
		if (renderer.renderMinX > 0)
		{
		    double originalMaxZ = renderer.renderMaxZ;

		    long seed = (long)(x * 3129871) ^ (long)y * 116129781L ^ (long)z;
		    seed = seed * seed * 42317861L + seed * 7L;
		    
		    int num0 = (int) (seed >> 12 & 3L) + 1;
		    int num1 = (int) (seed >> 15 & 3L) + 1;
		    int num2 = (int) (seed >> 18 & 3L) + 1;
		    int num3 = (int) (seed >> 21 & 3L) + 1;
			
			renderer.renderMaxX = renderer.renderMinX;
			renderer.renderMinX -= 1F / 16F;
			renderer.renderMinZ += num0 / 16F;				
			if (renderer.renderMaxZ - ((num1 +num2 + num3) / 16F) > renderer.renderMinZ)
			{
				// draw two blobs
				renderer.renderMaxZ = renderer.renderMinZ + num1 / 16F;
				renderer.renderStandardBlock(block, x, y, z);
				renderer.renderMaxZ = originalMaxZ - num2 / 16F;
				renderer.renderMinZ = renderer.renderMaxZ - num3 / 16F;
				renderer.renderStandardBlock(block, x, y, z);
			}
			else
			{
				//draw one blob
				renderer.renderMaxZ -= num2 / 16F;
				renderer.renderStandardBlock(block, x, y, z);
			}

			// reset render bounds
			renderer.setRenderBoundsFromBlock(block);
		}
		if (renderer.renderMaxX < 1F)
		{
		    double originalMaxZ = renderer.renderMaxZ;

		    long seed = (long)(x * 3129871) ^ (long)y * 116129781L ^ (long)z;
		    seed = seed * seed * 42317861L + seed * 17L;
		    
		    int num0 = (int) (seed >> 12 & 3L) + 1;
		    int num1 = (int) (seed >> 15 & 3L) + 1;
		    int num2 = (int) (seed >> 18 & 3L) + 1;
		    int num3 = (int) (seed >> 21 & 3L) + 1;
			
			renderer.renderMinX = renderer.renderMaxX;
			renderer.renderMaxX += 1F / 16F;
			renderer.renderMinZ += num0 / 16F;
			if (renderer.renderMaxZ - ((num1 +num2 + num3) / 16F) > renderer.renderMinZ)
			{
				// draw two blobs
				renderer.renderMaxZ = renderer.renderMinZ + num1 / 16F;
				renderer.renderStandardBlock(block, x, y, z);
				renderer.renderMaxZ = originalMaxZ - num2 / 16F;
				renderer.renderMinZ = renderer.renderMaxZ - num3 / 16F;
				renderer.renderStandardBlock(block, x, y, z);
			}
			else
			{
				//draw one blob
				renderer.renderMaxZ -= num2 / 16F;
				renderer.renderStandardBlock(block, x, y, z);
			}
			// reset render bounds
			renderer.setRenderBoundsFromBlock(block);
		}
		if (renderer.renderMinZ > 0)
		{
		    double originalMaxX = renderer.renderMaxX;

		    long seed = (long)(x * 3129871) ^ (long)y * 116129781L ^ (long)z;
		    seed = seed * seed * 42317861L + seed * 23L;
		    
		    int num0 = (int) (seed >> 12 & 3L) + 1;
		    int num1 = (int) (seed >> 15 & 3L) + 1;
		    int num2 = (int) (seed >> 18 & 3L) + 1;
		    int num3 = (int) (seed >> 21 & 3L) + 1;
			
			renderer.renderMaxZ = renderer.renderMinZ;
			renderer.renderMinZ -= 1F / 16F;
			renderer.renderMinX += num0 / 16F;
			renderer.renderMaxX = renderer.renderMinX + num1 / 16F;
			renderer.renderStandardBlock(block, x, y, z);
			renderer.renderMaxX = originalMaxX - num2 / 16F;
			renderer.renderMinX = renderer.renderMaxX - num3 / 16F;
			renderer.renderStandardBlock(block, x, y, z);
			// reset render bounds
			renderer.setRenderBoundsFromBlock(block);
		}
		if (renderer.renderMaxZ < 1F)
		{
		    double originalMaxX = renderer.renderMaxX;

		    long seed = (long)(x * 3129871) ^ (long)y * 116129781L ^ (long)z;
		    seed = seed * seed * 42317861L + seed * 11L;
		    
		    int num0 = (int) (seed >> 12 & 3L) + 1;
		    int num1 = (int) (seed >> 15 & 3L) + 1;
		    int num2 = (int) (seed >> 18 & 3L) + 1;
		    int num3 = (int) (seed >> 21 & 3L) + 1;
			
			renderer.renderMinZ = renderer.renderMaxZ;
			renderer.renderMaxZ += 1F / 16F;
			renderer.renderMinX += num0 / 16F;
			renderer.renderMaxX = renderer.renderMinX + num1 / 16F;
			renderer.renderStandardBlock(block, x, y, z);
			renderer.renderMaxX = originalMaxX - num2 / 16F;
			renderer.renderMinX = renderer.renderMaxX - num3 / 16F;
			renderer.renderStandardBlock(block, x, y, z);
			// reset render bounds
			renderer.setRenderBoundsFromBlock(block);
		}
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
