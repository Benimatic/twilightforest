package twilightforest.client.model;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class ModelTFMosquitoSwarm extends ModelBase 
{
	
	ModelRenderer core;
	ModelRenderer node1;
	ModelRenderer node2;
	ModelRenderer node3;
	ModelRenderer node4;
	ModelRenderer node5;
	ModelRenderer node6;
	
	Random rand = new org.bogdang.modifications.random.XSTR();

	public ModelTFMosquitoSwarm()
	{
		core = new ModelRenderer(this, rand.nextInt(28), rand.nextInt(28));
		core.addBox(-4F, 0.0F, -2F, 1, 1, 1);
		core.setRotationPoint(0.0F, -4.0F, 0.0F);
		
		node1 = new ModelRenderer(this, rand.nextInt(28), rand.nextInt(28));
		node1.addBox(-5.5F, -5F, -13F, 1, 1, 1);
		node1.setRotationPoint(2F, -1F, -6F);
		core.addChild(node1);
		
		node2 = new ModelRenderer(this, rand.nextInt(28), rand.nextInt(28));
		node2.addBox(-5.5F, -13F, -5F, 1, 1, 1);
		node2.setRotationPoint(0F, -7F, -1F);
		core.addChild(node2);
		
		node3 = new ModelRenderer(this, rand.nextInt(28), rand.nextInt(28));
		node3.addBox(-13F, -5F, -5F, 1, 1, 1);
		node3.setRotationPoint(5F, -2F, -1F);
		core.addChild(node3);
		
		node4 = new ModelRenderer(this, rand.nextInt(28), rand.nextInt(28));
		node4.addBox(-5.5F, -5F, -13F, 1, 1, 1);
		node4.setRotationPoint(2F, -1F, -6F);
		core.addChild(node4);
		
		node5 = new ModelRenderer(this, rand.nextInt(28), rand.nextInt(28));
		node5.addBox(-5.5F, -13F, -5F, 1, 1, 1);
		node5.setRotationPoint(0F, -7F, -1F);
		core.addChild(node5);
		
		node6 = new ModelRenderer(this, rand.nextInt(28), rand.nextInt(28));
		node6.addBox(-13F, -5F, -5F, 1, 1, 1);
		node6.setRotationPoint(5F, -2F, -1F);
		core.addChild(node6);
		
		addBugsToNodes(node1);
		addBugsToNodes(node2);
		addBugsToNodes(node3);
		addBugsToNodes(node4);
		addBugsToNodes(node5);
		addBugsToNodes(node6);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		core.render(par7  / 2.0F);
//		node1.render(par7);
//		node2.render(par7);
//		node3.render(par7);
	}
	
	/**
	 * Add the proper number of mosquitoes to the nodes
	 */
	public void addBugsToNodes(ModelRenderer node)
	{
		int bugs = 16;
		
		for (int i = 0; i < bugs; i++) {
			Vec3 vec = Vec3.createVectorHelper(11, 0, 0);
			float rotateY = ((i * (360F / bugs)) * (float)Math.PI) / 180F;
			vec.rotateAroundY(rotateY);
			ModelRenderer bug = new ModelRenderer(this, rand.nextInt(28), rand.nextInt(28));
			
			float bugX = (rand.nextFloat() - rand.nextFloat()) * 4.0f;
			float bugY = (rand.nextFloat() - rand.nextFloat()) * 4.0f;
			float bugZ = (rand.nextFloat() - rand.nextFloat()) * 4.0f;
					
			bug.addBox(bugX, bugY, bugZ, 1, 1, 1);
			
			bug.setRotationPoint((float)vec.xCoord, (float)vec.yCoord, (float)vec.zCoord);
			bug.rotateAngleY = rotateY;
			node.addChild(bug);
		}
	}

	@Override
	public void setLivingAnimations(EntityLivingBase par1EntityLiving, float par2, float par3, float time) {
		//Bogdan-G: reduce duplicate sin/cos, improve perf
		float value0 = (par1EntityLiving.ticksExisted + time) / 5.0F;
		float msin = MathHelper.sin(value0);
		float mcos = MathHelper.cos(value0);
		float msin0 = MathHelper.sin((par1EntityLiving.ticksExisted + time) / 6.0F);
		float msin1 = MathHelper.sin((par1EntityLiving.ticksExisted + time) / 2.0F);
		float msin2 = MathHelper.sin((par1EntityLiving.ticksExisted + time) / 7.0F);
		float mcos0 = MathHelper.cos((par1EntityLiving.ticksExisted + time) / 4.0F);
		float mcos1 = MathHelper.cos((par1EntityLiving.ticksExisted + time) / 7.0F);
		
		core.rotateAngleY = value0;
		core.rotateAngleX =  msin / 4.0F;
		core.rotateAngleZ =  mcos / 4.0F;

		node1.rotateAngleY = (par1EntityLiving.ticksExisted + time) / 2.0F;
		node1.rotateAngleX =  msin0 / 2.0F;
		node1.rotateAngleZ =  mcos / 4.0F;

		node2.rotateAngleY =  msin1 / 3.0F;
		node2.rotateAngleX =  value0;
		node2.rotateAngleZ =  mcos / 4.0F;

		node3.rotateAngleY =  msin2 / 3.0F;
		node3.rotateAngleX =  mcos0 / 2.0F;
		node3.rotateAngleZ =  value0;

		node4.rotateAngleX = (par1EntityLiving.ticksExisted + time) / 2.0F;
		node4.rotateAngleZ =  msin0 / 2.0F;
		node4.rotateAngleY =  msin / 4.0F;

		node5.rotateAngleZ =  msin1 / 3.0F;
		node5.rotateAngleY =  mcos / 4.0F;
		node5.rotateAngleX =  mcos / 4.0F;

		node6.rotateAngleZ =  mcos1 / 3.0F;
		node6.rotateAngleX =  mcos0 / 2.0F;
		node6.rotateAngleY =  value0;

	}
	
	
}
