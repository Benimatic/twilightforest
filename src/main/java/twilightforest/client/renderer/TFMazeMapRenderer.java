package twilightforest.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import twilightforest.TFMazeMapData;
import twilightforest.item.ItemTFMazeMap;
import twilightforest.item.TFItems;

public class TFMazeMapRenderer implements IItemRenderer {
	
    private static final ResourceLocation mapBackgroundTextures = new ResourceLocation("textures/map/map_background.png");


	public TFMazeMapRenderer(GameSettings gameSettings, TextureManager textureManager) {
		// TODO Auto-generated constructor stub
	}

    /** 
     * Checks if this renderer should handle a specific item's render type
     * @param item The item we are trying to render
     * @param type A render type to check if this renderer handles
     * @return true if this renderer should handle the given render type,
     * otherwise false
     */
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return (item.getItem() == TFItems.mazeMap || item.getItem() == TFItems.oreMap) && (RenderItem.renderInFrame && type == ItemRenderType.ENTITY);
	}

    /**
     * Checks if certain helper functionality should be executed for this renderer.
     * See ItemRendererHelper for more info
     * 
     * @param type The render type
     * @param item The ItemStack being rendered
     * @param helper The type of helper functionality to be ran
     * @return True to run the helper functionality, false to not.
     */
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

    /**
     * Called to do the actual rendering, see ItemRenderType for details on when specific 
     * types are run, and what extra data is passed into the data parameter.
     * 
     * @param type The render type
     * @param item The ItemStack being rendered
     * @param data Extra Type specific data
     */
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		if (RenderItem.renderInFrame)
		{
			//RenderBlocks renderBlocks = (RenderBlocks)data[0];
			EntityItem entity = (EntityItem)data[1];

			TFMazeMapData mapData = ((ItemTFMazeMap) TFItems.mazeMap).getMapData(item, entity.worldObj);


			// if we have data, render it
			if (mapData != null)
			{
				renderMapInFrame(item, RenderManager.instance, mapData);
			}
		}
	}

	private void renderMapInFrame(ItemStack item, RenderManager renderManager, TFMazeMapData mapData) {
		
		// do some rotations so that we get vaguely in the right place
        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(0.00781250F, 0.00781250F, 0.00781250F);
        GL11.glTranslatef(-65.0F, -111.0F, -3.0F);
        GL11.glNormal3f(0.0F, 0.0F, -1.0F);
        
        // draw background
        renderManager.renderEngine.bindTexture(mapBackgroundTextures);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        byte b0 = 7;
        tessellator.addVertexWithUV((double)(0 - b0), (double)(128 + b0), 0.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV((double)(128 + b0), (double)(128 + b0), 0.0D, 1.0D, 1.0D);
        tessellator.addVertexWithUV((double)(128 + b0), (double)(0 - b0), 0.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV((double)(0 - b0), (double)(0 - b0), 0.0D, 0.0D, 0.0D);
        tessellator.draw();

        // push map texture slightly off background
        GL11.glTranslatef(0.0F, 0.0F, -1.0F);

		// draw map
        Minecraft.getMinecraft().entityRenderer.getMapItemRenderer().func_148250_a(mapData, false);

	}

}
