package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFBlockGoblin;

public class RenderTFBlockGoblin extends RenderBiped<EntityTFBlockGoblin> {
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "blockgoblin.png");

	public RenderTFBlockGoblin(RenderManager manager, ModelBiped model, float shadowSize) {
		super(manager, model, shadowSize);
	}

	@Override
	public void doRender(EntityTFBlockGoblin goblin, double d, double d1, double d2, float f, float f1) {
		super.doRender(goblin, d, d1, d2, f, f1);

		RenderManager.instance.renderEntitySimple(goblin.block, f1);
		RenderManager.instance.renderEntitySimple(goblin.chain1, f1);
		RenderManager.instance.renderEntitySimple(goblin.chain2, f1);
		RenderManager.instance.renderEntitySimple(goblin.chain3, f1);//renderEntity
	}

    @Override
	protected ResourceLocation getEntityTexture(EntityTFBlockGoblin par1Entity)
    {
        return textureLoc;
    }
}
