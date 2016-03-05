package twilightforest.client.renderer;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFMoonworm;
import twilightforest.tileentity.TileEntityTFMoonworm;


public class TileEntityTFMoonwormRenderer extends TileEntitySpecialRenderer {

    private ModelTFMoonworm moonwormModel;
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "moonworm.png");

	public TileEntityTFMoonwormRenderer()
    {
        moonwormModel = new ModelTFMoonworm();
    }

	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		renderTileEntityFireflyAt((TileEntityTFMoonworm)tileentity, d, d1, d2, f);

	}

	/**
	 * Render a cute firefly!
	 */
	private void renderTileEntityFireflyAt(TileEntityTFMoonworm tileentity, double d, double d1, double d2, float partialTime) {
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

        GL11.glScalef(1f, -1f, -1f);
        
        moonwormModel.setLivingAnimations(tileentity, partialTime);

        // render the firefly body
        moonwormModel.render(0.0625f);

        GL11.glPopMatrix();		
	}

}
