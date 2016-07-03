package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;

import org.lwjgl.opengl.GL11;

import twilightforest.entity.EntityTFGoblinKnightUpper;

public class RenderTFGoblinKnightUpper extends RenderTFBiped {

	public RenderTFGoblinKnightUpper(ModelBiped par1ModelBiped, float par2) {
		super(par1ModelBiped, par2, "doublegoblin.png");
	}

	
    @Override
	protected void rotateCorpse(EntityLivingBase par1EntityLiving, float par2, float par3, float par4)
    {
        this.rotateGoblinKnight((EntityTFGoblinKnightUpper)par1EntityLiving, par2, par3, par4);
    }
    
    /**
     * Rotates the goblin knight whether it is alive or dead
     */
    protected void rotateGoblinKnight(EntityTFGoblinKnightUpper upperKnight, float par2, float par3, float par4)
    {
        super.rotateCorpse(upperKnight, par2, par3, par4);

        if (upperKnight.heavySpearTimer > 0)
        {
            GL11.glRotatef(getPitchForAttack((60 - upperKnight.heavySpearTimer) + par4), 1.0F, 0.0F, 0.0F);
            
    		//System.out.println("Getting attack pitch.  Result  is " + getPitchForAttack((100 - upperKnight.heavySpearTimer) + (1.0F - par4)));

        }
    }

    /**
     * Figure out what pitch the goblin should be at depending on where it's at on the the timer
     */
	public float getPitchForAttack(float attackTime) {
		//System.out.println("Getting attack pitch.  AttackTime is " + attackTime);
		
		if (attackTime <= 10)
		{
			// rock back
			return attackTime * 3.0F;
		}
		if (attackTime > 10 && attackTime <= 30)
		{
			// hang back
			return 30F;
		}
		if (attackTime > 30 && attackTime <= 33)
		{
			// slam forward
			return (attackTime - 30) * -25F + 30F;
		}
		if (attackTime > 33 && attackTime <= 50)
		{
			// stay forward
			return -45F;
		}
		if (attackTime > 50 && attackTime <= 60)
		{
			// back to normal
			return (10 - (attackTime - 50)) * -4.5F;
		}
		
		
//		if (attackTime <= 10)
//		{
//			// rock back
//			return attackTime * 3.0F;
//		}
//		if (attackTime > 10 && attackTime <= 40)
//		{
//			// hang back
//			return 30F;
//		}
//		if (attackTime > 40 && attackTime <= 43)
//		{
//			// slam forward
//			return (attackTime - 40) * -25F + 30F;
//		}
//		if (attackTime > 43 && attackTime <= 90)
//		{
//			// stay forward
//			return -45F;
//		}
//		if (attackTime > 90 && attackTime <= 100)
//		{
//			// back to normal
//			return (10 - (attackTime - 90)) * -4.5F;
//		}
		
		return 0;

	}
}
