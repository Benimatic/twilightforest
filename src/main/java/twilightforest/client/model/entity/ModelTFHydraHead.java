package twilightforest.client.model.entity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import twilightforest.entity.boss.EntityTFHydraHead;
import twilightforest.entity.boss.EntityTFHydraPart;

public class ModelTFHydraHead<T extends EntityTFHydraHead> extends EntityModel<T> {

	RendererModel head;
	RendererModel jaw;
	RendererModel frill;

	public ModelTFHydraHead() {
		textureWidth = 512;
		textureHeight = 256;

		setTextureOffset("head.box", 272, 0);
		setTextureOffset("head.upperlip", 272, 56);
		setTextureOffset("head.rearjaw", 272, 132);
		setTextureOffset("head.fin", 128, 200);
		setTextureOffset("head.fang1", 272, 156);
		setTextureOffset("head.fang2", 272, 156);
		setTextureOffset("head.teeth", 280, 156);
		setTextureOffset("head.teeth2", 280, 160);
		setTextureOffset("head.teeth3", 280, 160);
		setTextureOffset("jaw.jaw", 272, 92);
		setTextureOffset("jaw.fang1", 272, 156);
		setTextureOffset("jaw.fang2", 272, 156);
		setTextureOffset("jaw.teeth", 280, 156);
		setTextureOffset("jaw.teeth2", 280, 160);
		setTextureOffset("jaw.teeth3", 280, 160);
		setTextureOffset("frill.frill", 272, 200);


		head = new RendererModel(this, "head");
		head.addBox("box", -16F, -14F, -32F, 32, 24, 32);
		head.addBox("upperlip", -15F, -2F, -56F, 30, 12, 24);
		head.addBox("rearjaw", -15F, 10F, -20F, 30, 8, 16);
		head.addBox("fin", -2F, -30F, -12F, 4, 24, 24);
		head.addBox("fang1", -12F, 10, -49F, 2, 5, 2);
		head.addBox("fang2", 10F, 10, -49F, 2, 5, 2);
		head.addBox("teeth", -8F, 9, -49F, 16, 2, 2);
		head.addBox("teeth2", -10F, 9, -45F, 2, 2, 16);
		head.addBox("teeth3", 8F, 9, -45F, 2, 2, 16);
		head.setRotationPoint(0F, 0F, 0F);

		jaw = new RendererModel(this, "jaw");
		jaw.setRotationPoint(0F, 10F, -20F);
		jaw.addBox("jaw", -15F, 0F, -32F, 30, 8, 32);
		jaw.addBox("fang1", -10F, -5, -29F, 2, 5, 2);
		jaw.addBox("fang2", 8F, -5, -29F, 2, 5, 2);
		jaw.addBox("teeth", -8F, -1, -29F, 16, 2, 2);
		jaw.addBox("teeth2", -10F, -1, -25F, 2, 2, 16);
		jaw.addBox("teeth3", 8F, -1, -25F, 2, 2, 16);
		setRotation(jaw, 0F, 0F, 0F);
		head.addChild(jaw);

		frill = new RendererModel(this, "frill");
		frill.setRotationPoint(0F, 0F, -14F);
		frill.addBox("frill", -24F, -40.0F, 0F, 48, 48, 4);
		setRotation(frill, -0.5235988F, 0F, 0F);
		head.addChild(frill);
	}

	private void setRotation(RendererModel model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		head.render(scale);
	}

	public void render(float f5) {
		head.render(f5);
	}

	@Override
	public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
//		head.rotateAngleY = netHeadYaw / (180F / (float)Math.PI);
//		head.rotateAngleX = headPitch / (180F / (float)Math.PI);
	}

	@Override
	public void setLivingAnimations(T entity, float limbSwing, float limbSwingAmount, float partialTicks) {

		head.rotateAngleY = getRotationY(entity, partialTicks);
		head.rotateAngleX = getRotationX(entity, partialTicks);

		float mouthOpen = entity.getMouthOpen();
		head.rotateAngleX -= (float) (mouthOpen * (Math.PI / 12.0));
		jaw.rotateAngleX = (float) (mouthOpen * (Math.PI / 3.0));
	}

	public void openMouthForTrophy(float mouthOpen) {
		head.rotateAngleY = 0;
		head.rotateAngleX = 0;

		head.rotateAngleX -= (float) (mouthOpen * (Math.PI / 12.0));
		jaw.rotateAngleX = (float) (mouthOpen * (Math.PI / 3.0));

	}

	public float getRotationY(EntityTFHydraPart whichHead, float time) {

		//float yawOffset = hydra.prevRenderYawOffset + (hydra.renderYawOffset - hydra.prevRenderYawOffset) * time;
		float yaw = whichHead.prevRotationYaw + (whichHead.rotationYaw - whichHead.prevRotationYaw) * time;

		return yaw / 57.29578F;
	}

	public float getRotationX(EntityTFHydraPart whichHead, float time) {

		return (whichHead.prevRotationPitch + (whichHead.rotationPitch - whichHead.prevRotationPitch) * time) / 57.29578F;
	}

}
