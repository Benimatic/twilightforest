package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFBlockGoblin;

public class RenderTFBlockGoblin extends RenderBiped {

    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "blockgoblin.png");

	public RenderTFBlockGoblin(ModelBiped par1ModelBiped, float par2) {
		super(par1ModelBiped, par2);
	}

	/**
	 * Render the goblin and the block both
	 */
	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {

		EntityTFBlockGoblin goblin = (EntityTFBlockGoblin)entity;

		super.doRender(entity, d, d1, d2, f, f1);

		RenderManager.instance.renderEntitySimple(goblin.block, f1);
		RenderManager.instance.renderEntitySimple(goblin.chain1, f1);
		RenderManager.instance.renderEntitySimple(goblin.chain2, f1);
		RenderManager.instance.renderEntitySimple(goblin.chain3, f1);//renderEntity
	}
    
	/**
	 * Return our specific texture
	 */
    @Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return textureLoc;
    }
}
