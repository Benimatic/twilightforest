package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.layers.SheepWoolLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SheepModel;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;

public class RenderTFBighorn extends SheepRenderer {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("bighorn.png");

	public RenderTFBighorn(EntityRendererManager manager, SheepModel<SheepEntity> baseModel, EntityModel coatModel, float shadowSize) {
		super(manager);
		this.shadowSize = shadowSize;
		this.entityModel = baseModel;
		this.addLayer(new SheepWoolLayer(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(SheepEntity ent) {
		return textureLoc;
	}
}
