package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import twilightforest.entity.CubeOfAnnihilationEntity;

public class CubeOfAnnihilationModel extends ListModel<CubeOfAnnihilationEntity> {

	public ModelPart box;
	public ModelPart boxX;
	public ModelPart boxY;
	public ModelPart boxZ;

	public CubeOfAnnihilationModel() {
		texWidth = 64;
		texHeight = 64;
		box = new ModelPart(this, 0, 0);
		box.addBox(-8F, -8F, -8F, 16, 16, 16, 0F);
		box.setPos(0F, 0F, 0F);

		boxX = new ModelPart(this, 0, 32);
		boxX.addBox(-8F, -8F, -8F, 16, 16, 16, 0F);
		boxX.setPos(0F, 0F, 0F);

		boxY = new ModelPart(this, 0, 32);
		boxY.addBox(-8F, -8F, -8F, 16, 16, 16, 0F);
		boxY.setPos(0F, 0F, 0F);

		boxZ = new ModelPart(this, 0, 32);
		boxZ.addBox(-8F, -8F, -8F, 16, 16, 16, 0F);
		boxZ.setPos(0F, 0F, 0F);
	}

    @Override
	public Iterable<ModelPart> parts() {
		return ImmutableList.of(
				box,
				boxX,
				boxY,
				boxZ
		);
	}

	@Override
	public void setupAnim(CubeOfAnnihilationEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		boxX.xRot = (float) Math.sin((entity.tickCount + headPitch)) / 5F;
		boxY.yRot = (float) Math.sin((entity.tickCount + headPitch)) / 5F;
		boxZ.zRot = (float) Math.sin((entity.tickCount + headPitch)) / 5F;
	}
}
