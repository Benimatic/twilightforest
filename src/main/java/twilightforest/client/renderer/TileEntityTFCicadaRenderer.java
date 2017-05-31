package twilightforest.client.renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFCicada;
import twilightforest.tileentity.TileEntityTFCicada;


public class TileEntityTFCicadaRenderer extends TileEntitySpecialRenderer<TileEntityTFCicada> {

    private final ModelTFCicada cicadaModel = new ModelTFCicada();
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "cicada-model.png");

	@Override
	public void renderTileEntityAt(TileEntityTFCicada tileentity, double d, double d1, double d2, float partialTicks, int destroyStage) {
		GlStateManager.pushMatrix();
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
        GlStateManager.translate((float)d + 0.5F, (float)d1 + 0.5F, (float)d2 + 0.5F);
        GlStateManager.rotate(rotX, 1F, 0F, 0F);
        GlStateManager.rotate(rotZ, 0F, 0F, 1F);
		GlStateManager.rotate((float) tileentity.currentYaw, 0F, 1F, 0F);
        
        this.bindTexture(textureLoc);
        GlStateManager.pushMatrix();
        GlStateManager.scale(1f, -1f, -1f);
        cicadaModel.render(0.0625f);
        GlStateManager.popMatrix();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.popMatrix();
	}

}
