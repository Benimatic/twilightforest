package twilightforest.client.renderer.entity;


import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFLich;
import twilightforest.entity.boss.EntityTFLich;

public class RenderTFLich extends RenderBiped<EntityTFLich> {
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "twilightlich64.png");

	public RenderTFLich(RenderManager manager, ModelBiped modelbiped, float shadowSize) {
		super(manager, modelbiped, shadowSize);
		//FIXME: AtomicBlom: setRenderPassModel was replaced with layers
		this.setRenderPassModel(new ModelTFLich(true));
	}
	
    protected int shouldRenderPass(EntityLivingBase entity, int i, float f)
    {
    	EntityTFLich lich = (EntityTFLich)entity;
    	if (i == 2) {
			GlStateManager.enableBlend();
//	        GlStateManager.disableAlpha();
	        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        if (lich.isShadowClone()) {
	        	// clone alpha
	        	float shadow = 0.33f;
	            GlStateManager.color(shadow, shadow, shadow, 0.8F);
	        	return 2;
	        }
	        else 
	        {
	        	// shield alpha (shield texture already has alpha
	        	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0f);
	        	return 1;
	        }
	    	
	        
    	} else {
    		return 0;
    	}
    }

    @Override
	protected ResourceLocation getEntityTexture(EntityTFLich par1Entity)
    {
        return textureLoc;
    }

}
