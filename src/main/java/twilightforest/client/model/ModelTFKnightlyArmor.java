package twilightforest.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;

public class ModelTFKnightlyArmor extends ModelBiped {
	
    public ModelRenderer righthorn1;
    public ModelRenderer righthorn2;
    public ModelRenderer lefthorn1;
    public ModelRenderer lefthorn2;
    
    public ModelRenderer shoulderSpike1;
    public ModelRenderer shoulderSpike2;

    public ModelRenderer shoeSpike1;
    public ModelRenderer shoeSpike2;


    public ModelTFKnightlyArmor(int part, float expand)
    {
    	super(expand);

        this.righthorn1 = new ModelRenderer(this, 32, 0);
        this.righthorn1.addBox(-5.5F, -1.5F, -1.5F, 5, 3, 3);
        this.righthorn1.setRotationPoint(-4.0F, -6.5F, 0.0F);
        this.righthorn1.rotateAngleY = -15F / (180F / (float)Math.PI);
        this.righthorn1.rotateAngleZ = 10F / (180F / (float)Math.PI);
        
        this.righthorn2 = new ModelRenderer(this, 32, 6);
        this.righthorn2.addBox(-3.5F, -1.0F, -1.0F, 3, 2, 2);
        this.righthorn2.setRotationPoint(-4.5F, 0.0F, 0.0F);
        this.righthorn2.rotateAngleZ = 10F / (180F / (float)Math.PI);
        
        this.righthorn1.addChild(righthorn2);
        
        this.lefthorn1 = new ModelRenderer(this, 32, 0);
        this.lefthorn1.mirror = true;
        this.lefthorn1.addBox(0.5F, -1.5F, -1.5F, 5, 3, 3);
        this.lefthorn1.setRotationPoint(4.0F, -6.5F, 0.0F);
        this.lefthorn1.rotateAngleY = 15F / (180F / (float)Math.PI);
        this.lefthorn1.rotateAngleZ = -10F / (180F / (float)Math.PI);
        
        this.lefthorn2 = new ModelRenderer(this, 32, 6);
        this.lefthorn2.addBox(0.5F, -1.0F, -1.0F, 3, 2, 2);
        this.lefthorn2.setRotationPoint(4.5F, 0.0F, 0.0F);
        this.lefthorn2.rotateAngleZ = -10F / (180F / (float)Math.PI);
        
        this.lefthorn1.addChild(lefthorn2);
           
        this.bipedHead.addChild(righthorn1);
        this.bipedHead.addChild(lefthorn1);

        
        this.shoulderSpike1 = new ModelRenderer(this, 32, 10);
        this.shoulderSpike1.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.5F);
        this.shoulderSpike1.setRotationPoint(-3.75F, -2.5F, 0.0F);
        this.shoulderSpike1.rotateAngleX = 45F / (180F / (float)Math.PI);
        this.shoulderSpike1.rotateAngleY = 10F / (180F / (float)Math.PI);
        this.shoulderSpike1.rotateAngleZ = 35F / (180F / (float)Math.PI);
        
        this.bipedRightArm.addChild(shoulderSpike1);

        this.shoulderSpike2 = new ModelRenderer(this, 32, 10);
        this.shoulderSpike2.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.5F);
        this.shoulderSpike2.setRotationPoint(3.75F, -2.5F, 0.0F);
        this.shoulderSpike2.rotateAngleX = -45F / (180F / (float)Math.PI);
        this.shoulderSpike2.rotateAngleY = -10F / (180F / (float)Math.PI);
        this.shoulderSpike2.rotateAngleZ = 55F / (180F / (float)Math.PI);
        
        this.bipedLeftArm.addChild(shoulderSpike2);

        this.shoeSpike1 = new ModelRenderer(this, 32, 10);
        this.shoeSpike1.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.5F);
        this.shoeSpike1.setRotationPoint(-2.5F, 11F, 2.0F);
        this.shoeSpike1.rotateAngleY = -45F / (180F / (float)Math.PI);
        
        this.bipedRightLeg.addChild(shoeSpike1);

        this.shoeSpike2 = new ModelRenderer(this, 32, 10);
        this.shoeSpike2.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.5F);
        this.shoeSpike2.setRotationPoint(2.5F, 11F, 2.0F);
        this.shoeSpike2.rotateAngleY = 45F / (180F / (float)Math.PI);
        
        this.bipedLeftLeg.addChild(shoeSpike2);

        
        
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
			this.rightArmPose = ((EntityLivingBase)par1Entity).getHeldItem(EnumHand.MAIN_HAND) != null ? ArmPose.ITEM : ArmPose.EMPTY;
		}
		
		super.render(par1Entity, par2, par3, par4, par5, par6, par7);
	}

    
}
