package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderTFGiant extends RenderBiped {
	public RenderTFGiant(RenderManager manager) {
		super(manager, new ModelBiped(), 0.625F);
	}

    @Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
		return Minecraft.getMinecraft().player.getLocationSkin();
    }
    
    @Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	float scale = 4.0F;
        GlStateManager.scale(scale, scale, scale);
    }
}
