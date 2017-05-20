package twilightforest.client.renderer.entity;


import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFLich;
import twilightforest.entity.boss.EntityTFLich;

public class RenderTFLich extends RenderBiped<EntityTFLich> {
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "twilightlich64.png");

	public RenderTFLich(RenderManager manager, ModelBiped modelbiped, float shadowSize) {
		super(manager, modelbiped, shadowSize);
		this.setRenderPassModel(new ModelTFLich(true));
	}
	
    protected int shouldRenderPass(EntityLivingBase entity, int i, float f)
    {
    	EntityTFLich lich = (EntityTFLich)entity;
    	if (i == 2) {
	        GL11.glEnable(3042 /*GL_BLEND*/);
//	        GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        if (lich.isShadowClone()) {
	        	// clone alpha
	        	float shadow = 0.33f;
	            GL11.glColor4f(shadow, shadow, shadow, 0.8F);
	        	return 2;
	        }
	        else 
	        {
	        	if (lich.ticksExisted > 0)
	        	{
	        		BossStatus.setBossStatus(lich, false);
	        	}
	        	// shield alpha (shield texture already has alpha
	        	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0f);
	        	return 1;
	        }
	    	
	        
    	} else {
    		return 0;
    	}
    }

	/**
	 * Return our specific texture
	 */
    @Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return textureLoc;
    }

}
