package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.entity.boss.EntityTFMinoshroom;

public class RenderTFMinoshroom extends RenderBiped<EntityTFMinoshroom> {

    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "minoshroomtaur.png");

	public RenderTFMinoshroom(RenderManager manager, ModelBiped model, float shadowSize) {
		super(manager, model, shadowSize);
        this.addLayer(new LayerHeldItem(this));
	}

    // FIXME: AtomicBlom: I think this needs it's own layer, this seems to do more than held items
    protected void renderMooshroomEquippedItems(EntityLivingBase par1EntityLiving, float par2)
    {
        super.renderEquippedItems(par1EntityLiving, par2);

        this.bindTexture(TextureMap.locationBlocksTexture);
        GlStateManager.enableCull();
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0F, -1.0F, 1.0F);
        GlStateManager.translate(0.2F, 0.375F, 0.5F);
        GlStateManager.rotate(42.0F, 0.0F, 1.0F, 0.0F);
        this.field_147909_c.renderBlockAsItem(Blocks.RED_MUSHROOM, 0, 1.0F);
        GlStateManager.translate(0.1F, 0.0F, -0.6F);
        GlStateManager.rotate(42.0F, 0.0F, 1.0F, 0.0F);
        this.field_147909_c.renderBlockAsItem(Blocks.RED_MUSHROOM, 0, 1.0F);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        ((ModelBiped)this.mainModel).bipedHead.postRender(0.0625F);
        GlStateManager.scale(1.0F, -1.0F, 1.0F);
        GlStateManager.translate(0.0F, 1.0F, 0F);
        GlStateManager.rotate(12.0F, 0.0F, 1.0F, 0.0F);
        this.field_147909_c.renderBlockAsItem(Blocks.RED_MUSHROOM, 0, 1.0F);
        GlStateManager.popMatrix();
        GlStateManager.disableCull();
    }
    
    @Override
    protected void renderEquippedItems(EntityLivingBase par1EntityLiving, float par2)
    {
        this.renderMooshroomEquippedItems(par1EntityLiving, par2);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTFMinoshroom par1Entity)
    {
        return textureLoc;
    }

}
