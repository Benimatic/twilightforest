package twilightforest.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class ModelTFSkeletonDruid extends ModelBiped {

	public ModelTFSkeletonDruid() {
        float f = 0.0F;
        
        bipedBody = new ModelRenderer(this, 8, 16);
        bipedBody.addBox(-4F, 0.0F, -2F, 8, 12, 4, f);
        bipedBody.setRotationPoint(0.0F, 0.0F + f, 0.0F);
        
        bipedRightArm = new ModelRenderer(this, 0, 16);
        bipedRightArm.addBox(-1F, -2F, -1F, 2, 12, 2, f);
        bipedRightArm.setRotationPoint(-5F, 2.0F, 0.0F);
        bipedLeftArm = new ModelRenderer(this, 0, 16);
        bipedLeftArm.mirror = true;
        bipedLeftArm.addBox(-1F, -2F, -1F, 2, 12, 2, f);
        bipedLeftArm.setRotationPoint(5F, 2.0F, 0.0F);
        
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2);
        this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2);
        this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);

        
		dress = new ModelRenderer(this, 32, 16);
		dress.addBox(-4F, 12.0F, -2F, 8, 12, 4, f);
		dress.setRotationPoint(0.0F, 0.0F, 0.0F);
	}
  
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        
        dress.render(f5);
    }

    public ModelRenderer dress;

}
