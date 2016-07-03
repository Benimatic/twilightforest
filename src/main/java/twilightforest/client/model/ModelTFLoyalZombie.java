package twilightforest.client.model;


import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.EntityLivingBase;

import org.lwjgl.opengl.GL11;

public class ModelTFLoyalZombie extends ModelZombie {
	
    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    @Override
    public void setLivingAnimations(EntityLivingBase par1EntityLiving, float par2, float par3, float time)
    {
    	// GREEEEN
        GL11.glColor3f(0.25F, 2.0F, 0.25F);
    }

}
