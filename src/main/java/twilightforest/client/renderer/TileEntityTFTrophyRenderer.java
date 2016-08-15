package twilightforest.client.renderer;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFHydraHead;
import twilightforest.client.model.ModelTFLich;
import twilightforest.client.model.ModelTFNaga;
import twilightforest.client.model.ModelTFSnowQueen;
import twilightforest.client.model.ModelTFTowerBoss;
import twilightforest.tileentity.TileEntityTFTrophy;


public class TileEntityTFTrophyRenderer extends TileEntitySpecialRenderer {

    private ModelTFHydraHead hydraHeadModel;
    private static final ResourceLocation textureLocHydra = new ResourceLocation(TwilightForestMod.MODEL_DIR + "hydra4.png");
    private ModelTFNaga nagaHeadModel;
    private static final ResourceLocation textureLocNaga = new ResourceLocation(TwilightForestMod.MODEL_DIR + "nagahead.png");
    private ModelTFLich lichModel;
    private static final ResourceLocation textureLocLich = new ResourceLocation(TwilightForestMod.MODEL_DIR + "twilightlich64.png");
    private ModelTFTowerBoss urGhastModel;
    private static final ResourceLocation textureLocUrGhast = new ResourceLocation(TwilightForestMod.MODEL_DIR + "towerboss.png");
    private ModelTFSnowQueen snowQueenModel;
    private static final ResourceLocation textureLocSnowQueen = new ResourceLocation(TwilightForestMod.MODEL_DIR + "snowqueen.png");

    public TileEntityTFTrophyRenderer()
    {
    	hydraHeadModel = new ModelTFHydraHead();
    	nagaHeadModel = new ModelTFNaga();
    	lichModel = new ModelTFLich();
        urGhastModel = new ModelTFTowerBoss();
        snowQueenModel = new ModelTFSnowQueen();
    }

	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTime) {
		TileEntityTFTrophy trophy = (TileEntityTFTrophy)tileentity;

		
	    GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
	    
	    int meta = trophy.getBlockMetadata() & 7;
	
	    float rotation = (float)(trophy.func_145906_b() * 360) / 16.0F;
	    boolean onGround = true;
	
	    // wall mounted?
	    if (meta != 1)
	    {
	        switch (meta)
	        {
	            case 2:
	            	onGround = false;
	                break;
	            case 3:
	            	onGround = false;
	            	rotation = 180.0F;
	                break;
	            case 4:
	            	onGround = false;
	            	rotation = 270.0F;
	            	break;
	            case 5:
	            default:
	            	onGround = false;
	            	rotation = 90.0F;
	        }
	    }
	
	
	    GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);

		
		
		switch (trophy.func_145904_a())
		{
		case 0:
			renderHydraHead(rotation, onGround);
			break;
		case 1:
			renderNagaHead(rotation, onGround);
			break;
		case 2:
			renderLichHead(rotation, onGround);
			break;
		case 3:
			renderUrGhastHead(trophy, rotation, onGround, partialTime);
			break;
		case 4:
			renderSnowQueenHead(rotation, onGround);
			break;
		}
		
	    GL11.glPopMatrix();		

	}

	/**
	 * Render a hydra head
	 */
	private void renderHydraHead(float rotation, boolean onGround) {
	
	    GL11.glScalef(0.25f, 0.25f, 0.25f);
	
        this.bindTexture(textureLocHydra);
	
	    GL11.glScalef(1f, -1f, -1f);
	
	    // we seem to be getting a 180 degree rotation here
	    GL11.glRotatef(rotation, 0F, 1F, 0F);
	    GL11.glRotatef(180F, 0F, 1F, 0F);
	
	    GL11.glTranslatef(0, onGround ? 1F : -0F, 1.5F);
	
	    // open mouth?
	    hydraHeadModel.openMouthForTrophy(onGround ? 0F : 0.25F);
	    
	    // render the hydra head
	    hydraHeadModel.render((Entity)null, 0.0F, 0.0F, 0.0F, rotation, 0.0F, 0.0625F);
	}


	private void renderNagaHead(float rotation, boolean onGround) {
			
		GL11.glTranslatef(0, -0.125F, 0);


		GL11.glScalef(0.25f, 0.25f, 0.25f);

        this.bindTexture(textureLocNaga);

		GL11.glScalef(1f, -1f, -1f);

		// we seem to be getting a 180 degree rotation here
		GL11.glRotatef(rotation, 0F, 1F, 0F);
		GL11.glRotatef(180F, 0F, 1F, 0F);

		GL11.glTranslatef(0, onGround ? 1F : -0F, onGround ? 0F : 1F);

		// render the naga head
		nagaHeadModel.render((Entity)null, 0.0F, 0.0F, 0.0F, rotation, 0.0F, 0.0625F);
	}


	private void renderLichHead(float rotation, boolean onGround) {

		GL11.glTranslatef(0, 1, 0);


		//GL11.glScalef(0.5f, 0.5f, 0.5f);

        this.bindTexture(textureLocLich);

		GL11.glScalef(1f, -1f, -1f);

		// we seem to be getting a 180 degree rotation here
		GL11.glRotatef(rotation, 0F, 1F, 0F);
		GL11.glRotatef(180F, 0F, 1F, 0F);

		GL11.glTranslatef(0, onGround ? 1.75F : 1.5F, onGround ? 0F : 0.24F);

		// render the naga head
		lichModel.bipedHead.render(0.0625F);
		lichModel.bipedHeadwear.render(0.0625F);		
	}



	private void renderUrGhastHead(TileEntityTFTrophy trophy, float rotation, boolean onGround, float partialTime) {

		GL11.glTranslatef(0, 1, 0);

		GL11.glScalef(0.5f, 0.5f, 0.5f);

        this.bindTexture(textureLocUrGhast);

		GL11.glScalef(1f, -1f, -1f);

		// we seem to be getting a 180 degree rotation here
		GL11.glRotatef(rotation, 0F, 1F, 0F);
		GL11.glRotatef(180F, 0F, 1F, 0F);

		GL11.glTranslatef(0, 1F, 0F);//GL11.glTranslatef(0, onGround ? 1F : 1F, onGround ? 0F : 0F);

		// render the naga head
		urGhastModel.render((Entity)null, 0.0F, 0, trophy.ticksExisted + partialTime, 0, 0.0F, 0.0625F);
	}

	private void renderSnowQueenHead(float rotation, boolean onGround) {

		GL11.glTranslatef(0, 1, 0);


		//GL11.glScalef(0.5f, 0.5f, 0.5f);

        this.bindTexture(textureLocSnowQueen);

		GL11.glScalef(1f, -1f, -1f);

		// we seem to be getting a 180 degree rotation here
		GL11.glRotatef(rotation, 0F, 1F, 0F);
		GL11.glRotatef(180F, 0F, 1F, 0F);

		GL11.glTranslatef(0, onGround ? 1.5F : 1.25F, onGround ? 0F : 0.24F);

		// render the naga head
		snowQueenModel.bipedHead.render(0.0625F);
		snowQueenModel.bipedHeadwear.render(0.0625F);		
	}

}
