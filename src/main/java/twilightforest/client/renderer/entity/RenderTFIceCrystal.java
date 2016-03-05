package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFIceCrystal;

public class RenderTFIceCrystal extends RenderLiving {

    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "icecrystal.png");


	public RenderTFIceCrystal() {
		super(new ModelTFIceCrystal(), 1.0F);
	}

    
    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float partialTick)
    {
		float bounce = par1EntityLivingBase.ticksExisted + partialTick;
		
		GL11.glTranslatef(0F, MathHelper.sin((bounce) * 0.2F) * 0.15F, 0F);
    }


	@Override
	protected ResourceLocation getEntityTexture(Entity var1) {
		return RenderTFIceCrystal.textureLoc;
	}

}
