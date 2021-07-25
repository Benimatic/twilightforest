package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import twilightforest.entity.MosquitoSwarmEntity;

import java.util.Random;

public class MosquitoSwarmModel extends ListModel<MosquitoSwarmEntity> {

	ModelPart core;
	ModelPart node1;
	ModelPart node2;
	ModelPart node3;
	ModelPart node4;
	ModelPart node5;
	ModelPart node6;

	Random rand = new Random();

	public MosquitoSwarmModel() {
		core = new ModelPart(this, rand.nextInt(28), rand.nextInt(28));
		core.addBox(-0.5F, 2F, -0.5F, 1, 1, 1);
		core.setPos(0.0F, -4.0F, 0.0F);

		node1 = new ModelPart(this, rand.nextInt(28), rand.nextInt(28));
		node1.addBox(0F, 0F, -11F, 1, 1, 1);
		node1.setPos(-0.5F, -2F, -0.5F);
		core.addChild(node1);

		node2 = new ModelPart(this, rand.nextInt(28), rand.nextInt(28));
		node2.addBox(0F, -11F, 0F, 1, 1, 1);
		node2.setPos(-0.5F, -2F, -0.5F);
		core.addChild(node2);

		node3 = new ModelPart(this, rand.nextInt(28), rand.nextInt(28));
		node3.addBox(-11F, 0F, 0F, 1, 1, 1);
		node3.setPos(-0.5F, -2F, -0.5F);
		core.addChild(node3);

		node4 = new ModelPart(this, rand.nextInt(28), rand.nextInt(28));
		node4.addBox(0F, 0F, 11F, 1, 1, 1);
		node4.setPos(-0.5F, -2F, -0.5F);
		core.addChild(node4);

		node5 = new ModelPart(this, rand.nextInt(28), rand.nextInt(28));
		node5.addBox(0F, 11F, 0F, 1, 1, 1);
		node5.setPos(-0.5F, -2F, -0.5F);
		core.addChild(node5);

		node6 = new ModelPart(this, rand.nextInt(28), rand.nextInt(28));
		node6.addBox(11F, 0F, 0F, 1, 1, 1);
		node6.setPos(-0.5F, -2F, -0.5F);
		core.addChild(node6);

		addBugsToNodes(node1);
		addBugsToNodes(node2);
		addBugsToNodes(node3);
		addBugsToNodes(node4);
		addBugsToNodes(node5);
		addBugsToNodes(node6);
	}

	@Override
	public Iterable<ModelPart> parts() {
		return ImmutableList.of(core, node1, node2, node3, node4, node5, node6);
	}

	/**
	 * Add the proper number of mosquitoes to the nodes
	 */
	public void addBugsToNodes(ModelPart node) {
		int bugs = 16;

		for (int i = 0; i < bugs; i++) {
			Vec3 vec = new Vec3(0, 0, 0);
			float rotateY = ((i * (360F / bugs)) * 3.141593F) / 180F;
			vec.xRot(rotateY);
			ModelPart bug = new ModelPart(this, rand.nextInt(28), rand.nextInt(28));

			float bugX = (rand.nextFloat() - rand.nextFloat()) * 16.0f;
			float bugY = (rand.nextFloat() - rand.nextFloat()) * 16.0f;
			float bugZ = (rand.nextFloat() - rand.nextFloat()) * 16.0f;

			bug.addBox(bugX, bugY, bugZ, 1, 1, 1);

			bug.setPos((float) vec.x, (float) vec.y, (float) vec.z);
			bug.yRot = rotateY;
			node.addChild(bug);
		}
	}

	@Override
	public void setupAnim(MosquitoSwarmEntity entity, float v, float v1, float v2, float v3, float v4) { }

	@Override
	public void prepareMobModel(MosquitoSwarmEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		core.yRot = (entity.tickCount + partialTicks) / 5.0F;
		core.xRot = Mth.sin((entity.tickCount + partialTicks) / 5.0F) / 4.0F;
		core.zRot = Mth.cos((entity.tickCount + partialTicks) / 5.0F) / 4.0F;

		node1.yRot = (entity.tickCount + partialTicks) / 2.0F;
		node1.xRot = Mth.sin((entity.tickCount + partialTicks) / 6.0F) / 2.0F;
		node1.zRot = Mth.cos((entity.tickCount + partialTicks) / 5.0F) / 4.0F;

		node2.yRot = Mth.sin((entity.tickCount + partialTicks) / 2.0F) / 3.0F;
		node2.xRot = (entity.tickCount + partialTicks) / 5.0F;
		node2.zRot = Mth.cos((entity.tickCount + partialTicks) / 5.0F) / 4.0F;

		node3.yRot = Mth.sin((entity.tickCount + partialTicks) / 7.0F) / 3.0F;
		node3.xRot = Mth.cos((entity.tickCount + partialTicks) / 4.0F) / 2.0F;
		node3.zRot = (entity.tickCount + partialTicks) / 5.0F;

		node4.xRot = (entity.tickCount + partialTicks) / 2.0F;
		node4.zRot = Mth.sin((entity.tickCount + partialTicks) / 6.0F) / 2.0F;
		node4.yRot = Mth.sin((entity.tickCount + partialTicks) / 5.0F) / 4.0F;

		node5.zRot = (entity.tickCount + partialTicks) / 2.0F;
		node5.yRot = Mth.cos((entity.tickCount + partialTicks) / 5.0F) / 4.0F;
		node5.xRot = Mth.cos((entity.tickCount + partialTicks) / 5.0F) / 4.0F;

		node6.zRot = Mth.cos((entity.tickCount + partialTicks) / 7.0F) / 3.0F;
		node6.xRot = Mth.cos((entity.tickCount + partialTicks) / 4.0F) / 2.0F;
		node6.yRot = (entity.tickCount + partialTicks) / 5.0F;
	}
}
