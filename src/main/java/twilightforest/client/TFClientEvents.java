package twilightforest.client;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderLivingEvent;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.item.ItemTFBowBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TFClientEvents {

	private Random random; 
	
	public TFClientEvents() {
		this.random = new Random();
	}


    /**
     * Do ice effect on slowed monsters
	 */
	@SubscribeEvent
	public void renderLivingPost(RenderLivingEvent.Post event) {
		if (event.entity.getDataWatcher().getWatchableObjectInt(7) == MobEffects.POTIONTYPES[MobEffects.MOVESLOWDOWN.getId()].getLiquidColor() && event.entity.getDataWatcher().getWatchableObjectByte(8) > 0) {
			
			
			//System.out.println("Rendering slowed entity");
			this.renderIcedEntity(event.entity, event.renderer, event.x, event.y, event.z);
		}

	}

    /**
     * Alter FOV for our bows
	 */
	@SubscribeEvent
	public void fovUpdate(FOVUpdateEvent event) {
        if (event.entity.isUsingItem() && (event.entity.getItemInUse().getItem() instanceof ItemTFBowBase))
        {
            int i = event.entity.getItemInUseDuration();
            float f1 = (float)i / 20.0F;

            if (f1 > 1.0F)
            {
                f1 = 1.0F;
            }
            else
            {
                f1 *= f1;
            }

            event.newfov *= 1.0F - f1 * 0.15F;
        }
	}

	/**
	 * Render an entity with the ice effect.
	 * This just displays a bunch of ice cubes around on their model
	 */
	private void renderIcedEntity(EntityLivingBase entity, RendererLivingEntity renderer, double x, double y, double z) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		RenderManager.instance.renderEngine.bindTexture(TextureMap.locationBlocksTexture);

		this.random.setSeed(entity.getEntityId() * entity.getEntityId() * 3121 + entity.getEntityId() * 45238971);

		// number of cubes
		int numCubes = (int) (entity.height / 0.4F);
		
		// make cubes
		for (int i = 0; i < numCubes; i++) {
			GL11.glPushMatrix();
			float dx = (float)(x + random.nextGaussian() * 0.2F * entity.width);
			float dy = (float)(y + random.nextGaussian() * 0.2F * entity.height) + entity.height / 2F;
			float dz = (float)(z + random.nextGaussian() * 0.2F * entity.width);
			GL11.glTranslatef(dx, dy, dz);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glRotatef(random.nextFloat() * 360F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(random.nextFloat() * 360F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(random.nextFloat() * 360F, 0.0F, 0.0F, 1.0F);

			RenderBlocks.getInstance().renderBlockAsItem(Blocks.ICE, 0, 1.0F);
			GL11.glPopMatrix();
		}

		GL11.glDisable(GL11.GL_BLEND);
	}
	
}
