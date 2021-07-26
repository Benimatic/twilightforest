package twilightforest.client.model.armor;

import net.minecraft.client.model.geom.ModelPart;

public class PhantomArmorModel extends KnightmetalArmorModel {

	//TODO will this work?
	public PhantomArmorModel(ModelPart expand) {
		super(expand);

		this.righthorn1.yRot = -25F / (180F / (float) Math.PI);
		this.righthorn1.zRot = 45F / (180F / (float) Math.PI);

		this.righthorn2.yRot = -15F / (180F / (float) Math.PI);
		this.righthorn2.zRot = 45F / (180F / (float) Math.PI);

		this.lefthorn1.yRot = 25F / (180F / (float) Math.PI);
		this.lefthorn1.zRot = -45F / (180F / (float) Math.PI);

		this.lefthorn2.yRot = 15F / (180F / (float) Math.PI);
		this.lefthorn2.zRot = -45F / (180F / (float) Math.PI);
	}
}
