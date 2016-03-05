package twilightforest.client.renderer;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFFirefly;
import twilightforest.tileentity.TileEntityTFFirefly;


public class TileEntityTFFireflyRenderer extends TileEntitySpecialRenderer {

    private ModelTFFirefly fireflyModel;
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "firefly-tiny.png");

	public TileEntityTFFireflyRenderer()
    {
        fireflyModel = new ModelTFFirefly();
    }

	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		renderTileEntityFireflyAt((TileEntityTFFirefly)tileentity, d, d1, d2, f);

	}


	/**
	 * Render a cute firefly!
	 */
	private void renderTileEntityFireflyAt(TileEntityTFFirefly tileentity, double d, double d1, double d2, float f) {
        GL11.glPushMatrix();
        int facing = tileentity.getBlockMetadata();

        float rotX = 90.0F;
        float rotZ = 0.0F;
        if(facing == 3)
        {
        	rotZ = 0F;
        }
        if(facing == 4)
        {
        	rotZ = 180F;
        }
        if(facing == 1)
        {
        	rotZ = -90F;
        }
        if(facing == 2)
        {
        	rotZ = 90F;
        }
        if(facing == 5)
        {
        	rotX = 0F;
        }
        if(facing == 6)
        {
        	rotX = 180F;
        }
        GL11.glTranslatef((float)d + 0.5F, (float)d1 + 0.5F, (float)d2 + 0.5F);
        GL11.glRotatef(rotX, 1F, 0F, 0F);
        GL11.glRotatef(rotZ, 0F, 0F, 1F);
        GL11.glRotatef((float) tileentity.currentYaw, 0F, 1F, 0F);
 
        this.bindTexture(textureLoc);
        GL11.glPushMatrix();
        GL11.glScalef(1f, -1f, -1f);
        
        GL11.glColorMask(true, true, true, true);

        // render the firefly body
        GL11.glDisable(3042 /*GL_BLEND*/);
        fireflyModel.render(0.0625f);

//        
//        GL11.glEnable(3042 /*GL_BLEND*/);
//        GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
//        GL11.glBlendFunc(770, 771);
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);

        // render the firefly glow
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glBlendFunc(770, 1);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, tileentity.glowIntensity);
        fireflyModel.glow.render(0.0625f);
        GL11.glDisable(3042 /*GL_BLEND*/);
        GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();		
	}

}
