package twilightforest.client.model.armor;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import twilightforest.client.model.entity.ModelTFArmor;

public class ModelTFFieryArmor extends ModelTFArmor {

	public ModelTFFieryArmor(float expand) {
		super(expand);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		// FULL BRIGHT
		Minecraft.getMinecraft().entityRenderer.disableLightmap();
		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		Minecraft.getMinecraft().entityRenderer.enableLightmap();
	}
}
