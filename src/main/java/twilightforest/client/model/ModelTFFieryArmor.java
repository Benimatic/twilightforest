package twilightforest.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelTFFieryArmor extends ModelBiped {

    public ModelTFFieryArmor(int part, float expand)
    {
    	super(expand);

        
        switch (part)
        {
        case 0: // helmet
            this.bipedHead.showModel = true;
            this.bipedHeadwear.showModel = false;
            this.bipedBody.showModel = false;
            this.bipedRightArm.showModel = false;
            this.bipedLeftArm.showModel = false;
            this.bipedRightLeg.showModel = false;
            this.bipedLeftLeg.showModel = false;
            break;
        case 1: // chest
            this.bipedHead.showModel = false;
            this.bipedHeadwear.showModel = false;
            this.bipedBody.showModel = true;
            this.bipedRightArm.showModel = true;
            this.bipedLeftArm.showModel = true;
            this.bipedRightLeg.showModel = false;
            this.bipedLeftLeg.showModel = false;
            break;
        case 2: // pants
            this.bipedHead.showModel = false;
            this.bipedHeadwear.showModel = false;
            this.bipedBody.showModel = true;
            this.bipedRightArm.showModel = false;
            this.bipedLeftArm.showModel = false;
            this.bipedRightLeg.showModel = true;
            this.bipedLeftLeg.showModel = true;
            break;
        case 3: // boots
            this.bipedHead.showModel = false;
            this.bipedHeadwear.showModel = false;
            this.bipedBody.showModel = false;
            this.bipedRightArm.showModel = false;
            this.bipedLeftArm.showModel = false;
            this.bipedRightLeg.showModel = true;
            this.bipedLeftLeg.showModel = true;
            break;
        	
        }
    }


    /**
     * Sets the models various rotation angles then renders the model.
     */
	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		
		if (par1Entity != null) {
			this.isSneak = par1Entity.isSneaking();
		}
		
		if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
			this.heldItemRight = ((EntityLivingBase)par1Entity).getHeldItem() != null ? 1 : 0;
		}
		
        // FULL BRIGHT
        Minecraft.getMinecraft().entityRenderer.disableLightmap(0);

		
		super.render(par1Entity, par2, par3, par4, par5, par6, par7);
		

		Minecraft.getMinecraft().entityRenderer.enableLightmap(0);
	}

    
}
