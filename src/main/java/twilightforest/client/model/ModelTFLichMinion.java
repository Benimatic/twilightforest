package twilightforest.client.model;


import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

import org.lwjgl.opengl.GL11;

import twilightforest.entity.boss.EntityTFLichMinion;




public class ModelTFLichMinion extends ModelZombie {

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLivingBase par1EntityLiving, float par2, float par3, float time)
    {
        EntityTFLichMinion minion = (EntityTFLichMinion)par1EntityLiving;
        // make minions greener
        if (minion.getActivePotionEffect(MobEffects.DAMAGEBOOST) != null) {
        	GL11.glColor3f(0.25F, 2.0F, 0.25F);
        }
        else {
        	GL11.glColor3f(0.5F, 1.0F, 0.5F);
        }
    }
    
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        EntityTFLichMinion minion = (EntityTFLichMinion)par1Entity;
        // make strong minions bigger
        if (minion.getActivePotionEffect(MobEffects.DAMAGEBOOST) != null) {
        	super.render(par1Entity, par2, par3, par4, par5, par6, par7);
        }
        else {
        	super.render(par1Entity, par2, par3, par4, par5, par6, par7);
        }
    }

}
