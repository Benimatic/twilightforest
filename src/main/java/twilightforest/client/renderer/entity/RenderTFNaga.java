package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.entity.boss.EntityTFNaga;


public class RenderTFNaga extends RenderLiving {
	
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "nagahead.png");
	
	public RenderTFNaga(ModelBase modelbase, float f) {
		super(modelbase, f);
	}

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		super.doRender(entity, d, d1, d2, f, f1);
		// we also render segments here, and don't need to do this for them
//		if (entity instanceof EntityTFNagaOld)
//		{
//	        BossStatus.setBossStatus((EntityTFNagaOld)entity, false);
//		}
		
		if (entity instanceof EntityTFNaga && ((EntityTFNaga)entity).getParts() != null)
		{
			EntityTFNaga naga = (EntityTFNaga)entity;
			
			for (int i = 0; i < naga.getParts().length; i++) {
				if (!naga.getParts()[i].isDead){
					RenderManager.instance.renderEntitySimple(naga.getParts()[i], f1);
				}
			}
			
	        BossStatus.setBossStatus(naga, false);

		}
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
