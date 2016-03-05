package twilightforest.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class TFGiantBlockRenderer implements IItemRenderer {

    public TFGiantBlockRenderer(GameSettings gameSettings, TextureManager textureManager) {
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
		return type == ItemRenderType.ENTITY || type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
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
		return (type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.ENTITY_BOBBING)) || helper == ItemRendererHelper.BLOCK_3D;
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

		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			// extra scaling
			float scale = 4.0F;
			GL11.glScalef(scale, scale, scale);
			// move for first person
			GL11.glTranslatef(1.0F, 0.3F, 0.75F);
            GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F);

			
			// render item
			IIcon iicon = ((EntityLivingBase)data[1]).getItemIcon(item, 0);
			RenderBlocks renderBlocks = (RenderBlocks)data[0];
			renderGiantItemEquipped(iicon, renderBlocks, item);
		} else if (type == ItemRenderType.EQUIPPED) {
			// extra scaling
			float scale = 4.0F;
			GL11.glScalef(scale, scale, scale);
			// move item for equipped
			GL11.glTranslatef(0.25F, 0.3F, -0.5F);
            GL11.glRotatef(15.0F, 0.0F, 0.0F, 1.0F);
			
			// render item
			IIcon iicon = ((EntityLivingBase)data[1]).getItemIcon(item, 0);
			RenderBlocks renderBlocks = (RenderBlocks)data[0];
			renderGiantItemEquipped(iicon, renderBlocks, item);
		} else if (type == ItemRenderType.ENTITY) {
			// extra scaling
			float scale = 4.0F;
			GL11.glScalef(scale, scale, scale);
			// move item for dropped
			GL11.glTranslatef(0.0F, 0.5F, 0.0F);
			
			RenderBlocks renderBlocks = (RenderBlocks)data[0];
			EntityItem entityItem = (EntityItem)data[1];
		
			this.renderDroppedItem(entityItem, renderBlocks, item);
		}
	}


	private void renderGiantItemEquipped(IIcon iicon, RenderBlocks renderBlocks, ItemStack itemStack) {
        Block block = Block.getBlockFromItem(itemStack.getItem());
        
		renderBlocks.renderBlockAsItem(block, itemStack.getItemDamage(), 1.0F);

	}

	private void renderDroppedItem(EntityItem entityItem, RenderBlocks renderBlocks, ItemStack itemStack) {
		
        Block block = Block.getBlockFromItem(itemStack.getItem());

		renderBlocks.renderBlockAsItem(block, itemStack.getItemDamage(), 1.0F);
	}

}
