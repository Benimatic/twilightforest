package twilightforest.client.model.armor;

import net.minecraft.inventory.EquipmentSlotType;

public class ModelTFPhantomArmor extends ModelTFKnightlyArmor {

	public ModelTFPhantomArmor(EquipmentSlotType part, float expand) {
		super(expand);

		this.righthorn1.rotateAngleY = -25F / (180F / (float) Math.PI);
		this.righthorn1.rotateAngleZ = 45F / (180F / (float) Math.PI);

		this.righthorn2.rotateAngleY = -15F / (180F / (float) Math.PI);
		this.righthorn2.rotateAngleZ = 45F / (180F / (float) Math.PI);

		this.lefthorn1.rotateAngleY = 25F / (180F / (float) Math.PI);
		this.lefthorn1.rotateAngleZ = -45F / (180F / (float) Math.PI);

		this.lefthorn2.rotateAngleY = 15F / (180F / (float) Math.PI);
		this.lefthorn2.rotateAngleZ = -45F / (180F / (float) Math.PI);
	}
}
