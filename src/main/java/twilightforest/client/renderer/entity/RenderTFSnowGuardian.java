package twilightforest.client.renderer.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

import org.lwjgl.opengl.GL11;

import twilightforest.client.model.ModelTFSnowGuardian;

public class RenderTFSnowGuardian extends RenderTFBiped {

	public RenderTFSnowGuardian() {
		super(new ModelTFSnowGuardian(), 1.0F, "textures/entity/zombie/zombie.png");
	}

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    @Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float partialTick)
    {
		float bounce = par1EntityLivingBase.ticksExisted + partialTick;
		
		GL11.glTranslatef(0F, MathHelper.sin((bounce) * 0.2F) * 0.15F, 0F);
    }
}
