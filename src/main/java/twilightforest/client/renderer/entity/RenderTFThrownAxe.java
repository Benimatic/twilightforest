package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import twilightforest.entity.boss.EntityTFThrownAxe;


public class RenderTFThrownAxe extends Render<EntityTFThrownAxe> {
	private final Item myItem;

	public RenderTFThrownAxe(RenderManager manager, Item knightlyAxe) {
		super(manager);
		this.myItem = knightlyAxe;
	}

	@Override
	public void doRender(EntityTFThrownAxe entity, double par2, double par4, double par6, float par8, float par9) {

        GlStateManager.pushMatrix();
        //GlStateManager.scale(1.25F, 1.25F, 1.25F);
        
        float spin = (entity.ticksExisted + par9) * -10F + 90F;
		
        this.doRenderItem(null, par2, par4, par6, par8, spin);
        
        GlStateManager.popMatrix();

	}
	
    /**
     * Renders the item
     */
	public void doRenderItem(EntityItem par1EntityItem, double x, double y, double z, float rotation, float spin)
	{
		GlStateManager.pushMatrix();

		GlStateManager.translate((float)x, (float)y, (float)z);
		GlStateManager.enableRescaleNormal();

		// size up
		GlStateManager.scale(1.25F, 1.25F, 1.25F);

		IIcon icon1 = this.myItem.getIconFromDamage(0);

		this.renderDroppedItem(icon1, rotation, spin);

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}


    private void renderDroppedItem(IIcon par2Icon, float rotation, float spin)
    {
    	Tessellator tessellator = Tessellator.instance;

    	float f9 = 0.5F;
    	float f10 = 0.25F;

    	GlStateManager.pushMatrix();

    	GlStateManager.rotate(rotation + 270f, 0.0F, 1.0F, 0.0F);
    	GlStateManager.rotate(spin, 0.0F, 0.0F, 1.0F);

    	float f12 = 0.0625F;
    	float f11 = 0.021875F;


    	GlStateManager.translate(-f9, -f10, -(f12 + f11));

    	GlStateManager.translate(0f, 0f, f12 + f11);

    	this.bindTexture(TextureMap.locationItemsTexture);

    	//GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	ItemRenderer.renderItemIn2D(tessellator, par2Icon.getMaxU(), par2Icon.getMinV(), par2Icon.getMinU(), par2Icon.getMaxV(), par2Icon.getIconWidth(), par2Icon.getIconHeight(), f12);


    	GlStateManager.popMatrix();
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityTFThrownAxe entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
}
