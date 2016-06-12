package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;


public class RenderTFThrownAxe extends RenderItem {
	
	Item myItem;

	public RenderTFThrownAxe(Item knightlyAxe) {
		this.myItem = knightlyAxe;
	}

	@Override
	public void doRender(Entity entity, double par2, double par4, double par6, float par8, float par9) {

        GL11.glPushMatrix();
        //GL11.glScalef(1.25F, 1.25F, 1.25F);
        
        float spin = (entity.ticksExisted + par9) * -10F + 90F;
		
        this.doRenderItem(null, par2, par4, par6, par8, spin);
        
        GL11.glPopMatrix();

	}
	
    /**
     * Renders the item
     */
	public void doRenderItem(EntityItem par1EntityItem, double x, double y, double z, float rotation, float spin)
	{
		GL11.glPushMatrix();

		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		// size up
		GL11.glScalef(1.25F, 1.25F, 1.25F);

		IIcon icon1 = this.myItem.getIconFromDamage(0);

		this.renderDroppedItem(icon1, rotation, spin);

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}


    private void renderDroppedItem(IIcon par2Icon, float rotation, float spin)
    {
    	Tessellator tessellator = Tessellator.instance;

    	float f9 = 0.5F;
    	float f10 = 0.25F;

    	GL11.glPushMatrix();

    	GL11.glRotatef(rotation + 270f, 0.0F, 1.0F, 0.0F);
    	GL11.glRotatef(spin, 0.0F, 0.0F, 1.0F);

    	float f12 = 0.0625F;
    	float f11 = 0.021875F;


    	GL11.glTranslatef(-f9, -f10, -(f12 + f11));

    	GL11.glTranslatef(0f, 0f, f12 + f11);

    	this.bindTexture(TextureMap.locationItemsTexture);

    	//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	ItemRenderer.renderItemIn2D(tessellator, par2Icon.getMaxU(), par2Icon.getMinV(), par2Icon.getMinU(), par2Icon.getMaxV(), par2Icon.getIconWidth(), par2Icon.getIconHeight(), f12);


    	GL11.glPopMatrix();
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
        return this.renderManager.renderEngine.getResourceLocation(this.myItem.getSpriteNumber());
	}

	
    /**
     * Items should have a bob effect
     * @return
     */
    public boolean shouldBob()
    {
       return false;
    }
}
