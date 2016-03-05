package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;

public class RenderTFMinoshroom extends RenderBiped {

    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "minoshroomtaur.png");

	public RenderTFMinoshroom(ModelBiped par1ModelBase, float par2) {
		super(par1ModelBase, par2);
	}
	
    protected void renderMooshroomEquippedItems(EntityLivingBase par1EntityLiving, float par2)
    {
        super.renderEquippedItems(par1EntityLiving, par2);

        this.bindTexture(TextureMap.locationBlocksTexture);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPushMatrix();
        GL11.glScalef(1.0F, -1.0F, 1.0F);
        GL11.glTranslatef(0.2F, 0.375F, 0.5F);
        GL11.glRotatef(42.0F, 0.0F, 1.0F, 0.0F);
        this.field_147909_c.renderBlockAsItem(Blocks.red_mushroom, 0, 1.0F);
        GL11.glTranslatef(0.1F, 0.0F, -0.6F);
        GL11.glRotatef(42.0F, 0.0F, 1.0F, 0.0F);
        this.field_147909_c.renderBlockAsItem(Blocks.red_mushroom, 0, 1.0F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        ((ModelBiped)this.mainModel).bipedHead.postRender(0.0625F);
        GL11.glScalef(1.0F, -1.0F, 1.0F);
        GL11.glTranslatef(0.0F, 1.0F, 0F);
        GL11.glRotatef(12.0F, 0.0F, 1.0F, 0.0F);
        this.field_147909_c.renderBlockAsItem(Blocks.red_mushroom, 0, 1.0F);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
    }
    
    @Override
    protected void renderEquippedItems(EntityLivingBase par1EntityLiving, float par2)
    {
        this.renderMooshroomEquippedItems(par1EntityLiving, par2);
    }

	/**
	 * Return our specific texture
	 */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return textureLoc;
    }

}
