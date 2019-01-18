package twilightforest.client.renderer;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.IRenderHandler;

import org.lwjgl.opengl.GL11;

import twilightforest.world.TFWorld;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TFSkyRenderer extends IRenderHandler {

    /** The star GL Call list */
    private int starGLCallList;

    /** OpenGL sky list */
    private int glSkyList;

    /** OpenGL sky list 2 */
    private int glSkyList2;

	@SideOnly(Side.CLIENT)
    public TFSkyRenderer() 
	{
        this.starGLCallList = GLAllocation.generateDisplayLists(3);
        GL11.glPushMatrix();
        GL11.glNewList(this.starGLCallList, GL11.GL_COMPILE);
        this.renderStars();
        GL11.glEndList();
        GL11.glPopMatrix();
        Tessellator var5 = Tessellator.instance;
        this.glSkyList = this.starGLCallList + 1;
        GL11.glNewList(this.glSkyList, GL11.GL_COMPILE);
        byte var7 = 64;
        int var8 = 256 / var7 + 2;
        float var6 = 16.0F;
        int var9;
        int var10;

        for (var9 = -var7 * var8; var9 <= var7 * var8; var9 += var7)
        {
            for (var10 = -var7 * var8; var10 <= var7 * var8; var10 += var7)
            {
                var5.startDrawingQuads();
                var5.addVertex((double)(var9 + 0), (double)var6, (double)(var10 + 0));
                var5.addVertex((double)(var9 + var7), (double)var6, (double)(var10 + 0));
                var5.addVertex((double)(var9 + var7), (double)var6, (double)(var10 + var7));
                var5.addVertex((double)(var9 + 0), (double)var6, (double)(var10 + var7));
                var5.draw();
            }
        }

        GL11.glEndList();
        this.glSkyList2 = this.starGLCallList + 2;
        GL11.glNewList(this.glSkyList2, GL11.GL_COMPILE);
        var6 = -16.0F;
        var5.startDrawingQuads();

        for (var9 = -var7 * var8; var9 <= var7 * var8; var9 += var7)
        {
            for (var10 = -var7 * var8; var10 <= var7 * var8; var10 += var7)
            {
                var5.addVertex((double)(var9 + var7), (double)var6, (double)(var10 + 0));
                var5.addVertex((double)(var9 + 0), (double)var6, (double)(var10 + 0));
                var5.addVertex((double)(var9 + 0), (double)var6, (double)(var10 + var7));
                var5.addVertex((double)(var9 + var7), (double)var6, (double)(var10 + var7));
            }
        }

        var5.draw();
        GL11.glEndList();
        GL11.glPopMatrix();
    }
    
	
	@Override
	@SideOnly(Side.CLIENT)
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Vec3 var2 = getTwilightSkyColor(world);//Vec3 var2 = world.getSkyColor(mc.renderViewEntity, partialTicks);
        float var3 = (float)var2.xCoord;
        float var4 = (float)var2.yCoord;
        float var5 = (float)var2.zCoord;
        float var8;

        if (mc.gameSettings.anaglyph)
        {
            float var6 = (var3 * 30.0F + var4 * 59.0F + var5 * 11.0F) / 100.0F;
            float var7 = (var3 * 30.0F + var4 * 70.0F) / 100.0F;
            var8 = (var3 * 30.0F + var5 * 70.0F) / 100.0F;
            var3 = var6;
            var4 = var7;
            var5 = var8;
        }

        GL11.glColor3f(var3, var4, var5);
        Tessellator var23 = Tessellator.instance;
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glColor3f(var3, var4, var5);
        GL11.glCallList(this.glSkyList);
        GL11.glDisable(GL11.GL_FOG);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.disableStandardItemLighting();
//        float[] var24 = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);
        float var9;
        float var10;
        float var11;
        float var12;

//        if (var24 != null)
//        {
//            GL11.glDisable(GL11.GL_TEXTURE_2D);
//            GL11.glShadeModel(GL11.GL_SMOOTH);
//            GL11.glPushMatrix();
//            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
//            GL11.glRotatef(MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
//            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
//            var8 = var24[0];
//            var9 = var24[1];
//            var10 = var24[2];
//            float var13;
//
//            if (mc.gameSettings.anaglyph)
//            {
//                var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
//                var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
//                var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
//                var8 = var11;
//                var9 = var12;
//                var10 = var13;
//            }
//
//            var23.startDrawing(6);
//            var23.setColorRGBA_F(var8, var9, var10, var24[3]);
//            var23.addVertex(0.0D, 100.0D, 0.0D);
//            byte var26 = 16;
//            var23.setColorRGBA_F(var24[0], var24[1], var24[2], 0.0F);
//
//            for (int var27 = 0; var27 <= var26; ++var27)
//            {
//                var13 = (float)var27 * (float)Math.PI * 2.0F / (float)var26;
//                float var14 = MathHelper.sin(var13);
//                float var15 = MathHelper.cos(var13);
//                var23.addVertex((double)(var14 * 120.0F), (double)(var15 * 120.0F), (double)(-var15 * 40.0F * var24[3]));
//            }
//
//            var23.draw();
//            GL11.glPopMatrix();
//            GL11.glShadeModel(GL11.GL_FLAT);
//        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glPushMatrix();
        var8 = 1.0F - world.getRainStrength(partialTicks);
        var9 = 0.0F;
        var10 = 0.0F;
        var11 = 0.0F;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, var8);
        GL11.glTranslatef(var9, var10, var11);
        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(getRealCelestialAngle(world, partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);//GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
        var12 = 30.0F;
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/terrain/sun.png"));
//        var23.startDrawingQuads();
//        var23.addVertexWithUV((double)(-var12), 100.0D, (double)(-var12), 0.0D, 0.0D);
//        var23.addVertexWithUV((double)var12, 100.0D, (double)(-var12), 1.0D, 0.0D);
//        var23.addVertexWithUV((double)var12, 100.0D, (double)var12, 1.0D, 1.0D);
//        var23.addVertexWithUV((double)(-var12), 100.0D, (double)var12, 0.0D, 1.0D);
//        var23.draw();
//        var12 = 20.0F;
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/terrain/moon_phases.png"));
//        int var28 = world.getMoonPhase(partialTicks);
//        int var30 = var28 % 4;
//        int var29 = var28 / 4 % 2;
//        float var16 = (float)(var30 + 0) / 4.0F;
//        float var17 = (float)(var29 + 0) / 2.0F;
//        float var18 = (float)(var30 + 1) / 4.0F;
//        float var19 = (float)(var29 + 1) / 2.0F;
//        var23.startDrawingQuads();
//        var23.addVertexWithUV((double)(-var12), -100.0D, (double)var12, (double)var18, (double)var19);
//        var23.addVertexWithUV((double)var12, -100.0D, (double)var12, (double)var16, (double)var19);
//        var23.addVertexWithUV((double)var12, -100.0D, (double)(-var12), (double)var16, (double)var17);
//        var23.addVertexWithUV((double)(-var12), -100.0D, (double)(-var12), (double)var18, (double)var17);
//        var23.draw();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        float var20 = 1.0f;//world.getStarBrightness(partialTicks) * var8;

        if (var20 > 0.0F)
        {
            GL11.glColor4f(var20, var20, var20, var20);
            GL11.glCallList(this.starGLCallList);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor3f(0.0F, 0.0F, 0.0F);
        double var25 = mc.thePlayer.getPosition(partialTicks).yCoord - TFWorld.SEALEVEL;

        if (var25 < 0.0D)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 12.0F, 0.0F);
            GL11.glCallList(this.glSkyList2);
            GL11.glPopMatrix();
            var10 = 1.0F;
            var11 = -((float)(var25 + 65.0D));
            var12 = -var10;
            var23.startDrawingQuads();
            var23.setColorRGBA_I(0, 255);
            var23.addVertex((double)(-var10), (double)var11, (double)var10);
            var23.addVertex((double)var10, (double)var11, (double)var10);
            var23.addVertex((double)var10, (double)var12, (double)var10);
            var23.addVertex((double)(-var10), (double)var12, (double)var10);
            var23.addVertex((double)(-var10), (double)var12, (double)(-var10));
            var23.addVertex((double)var10, (double)var12, (double)(-var10));
            var23.addVertex((double)var10, (double)var11, (double)(-var10));
            var23.addVertex((double)(-var10), (double)var11, (double)(-var10));
            var23.addVertex((double)var10, (double)var12, (double)(-var10));
            var23.addVertex((double)var10, (double)var12, (double)var10);
            var23.addVertex((double)var10, (double)var11, (double)var10);
            var23.addVertex((double)var10, (double)var11, (double)(-var10));
            var23.addVertex((double)(-var10), (double)var11, (double)(-var10));
            var23.addVertex((double)(-var10), (double)var11, (double)var10);
            var23.addVertex((double)(-var10), (double)var12, (double)var10);
            var23.addVertex((double)(-var10), (double)var12, (double)(-var10));
            var23.addVertex((double)(-var10), (double)var12, (double)(-var10));
            var23.addVertex((double)(-var10), (double)var12, (double)var10);
            var23.addVertex((double)var10, (double)var12, (double)var10);
            var23.addVertex((double)var10, (double)var12, (double)(-var10));
            var23.draw();
        }

        if (world.provider.isSkyColored())
        {
            GL11.glColor3f(var3 * 0.2F + 0.04F, var4 * 0.2F + 0.04F, var5 * 0.6F + 0.1F);
        }
        else
        {
            GL11.glColor3f(var3, var4, var5);
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -((float)(var25 - 16.0D)), 0.0F);
        GL11.glCallList(this.glSkyList2);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(true);
	}
	
	private float getRealCelestialAngle(World world, float partialTicks) {
        int var4 = (int)(world.getWorldTime() % 24000L);
        float var5 = ((float)var4 + partialTicks) / 24000.0F - 0.25F;

        if (var5 < 0.0F)
        {
            ++var5;
        }

        if (var5 > 1.0F)
        {
            --var5;
        }

        float var6 = var5;
        var5 = 1.0F - (((float)Math.cos(var5 * (float)Math.PI) + 1.0F) / 2.0F);
        var5 = var6 + (var5 - var6) / 3.0F;
        return var5;
	}

	/**
	 * Maybe in the future we can get the return of sky color by biome?
	 * @return
	 */
	private Vec3 getTwilightSkyColor(World world) {
		return Vec3.createVectorHelper(32 / 256.0, 34 / 256.0, 74 / 256.0);
//		return Vec3.createVectorHelper(43 / 256.0, 46 / 256.0, 99 / 256.0);
 	}

	
	 private void renderStars()
	    {
	        Random var1 = new org.bogdang.modifications.random.XSTR(10842L);
	        Tessellator var2 = Tessellator.instance;
	        var2.startDrawingQuads();

	        for (int var3 = 0; var3 < 3000; ++var3)
	        {//Bogdan-G: dont rewrite temp values
	            final float var4 = (var1.nextFloat() * 2.0F - 1.0F);
	            final float var6 = (var1.nextFloat() * 2.0F - 1.0F);
	            final float var8 = (var1.nextFloat() * 2.0F - 1.0F);
	            final float size = (0.10F + var1.nextFloat() * 0.25F);
	            final float var12 = var4 * var4 + var6 * var6 + var8 * var8;

	            if (var12 < 1.0F && var12 > 0.01F)
	            {
	                final float var12d = 1.0F / (float)Math.sqrt(var12);
	                final float var4d = var4 * var12d;
	                final float var6d = var6 * var12d;
	                final float var8d = var8 * var12d;
	                final float var14 = var4d * 100.0F;
	                final float var16 = var6d * 100.0F;
	                final float var18 = var8d * 100.0F;
	                final float var20 = org.bogdang.modifications.math.TrigMath2.atan2(var4d, var8d);
	                final float var22 = (float)Math.sin(var20);
	                final float var24 = (float)Math.cos(var20);
	                final float var26 = org.bogdang.modifications.math.TrigMath2.atan2((float)Math.sqrt(var4d * var4d + var8d * var8d), var6d);
	                final float var28 = (float)Math.sin(var26);
	                final float var30 = (float)Math.cos(var26);
	                final float var32 = var1.nextFloat() * (float)Math.PI * 2.0F;
	                final float var34 = (float)Math.sin(var32);
	                final float var36 = (float)Math.cos(var32);

	                //for (int var38 = 0; var38 < 4; ++var38)
	                //{//Bogdan-G: drop cycle for, calculate the known values
	                    /*final float var41_1a = size * var36;
	                    final float var41_2a = size * var34;
	                    
	                    final float var53 = (var41_2a - var41_1a) * var28;
	                    final float var55 = -((var41_2a - var41_1a) * var30);
	                    final float var57 = var55 * var22 - ((-var41_1a) - var41_2a) * var24;
	                    final float var61 = ((-var41_1a) - var41_2a) * var22 + var55 * var24;
	                    var2.addVertex((double)(var14 + var57), (double)(var16 + var53), (double)(var18 + var61));
	                    
	                    final float var53a = (-var41_1a) * var28;
	                    final float var55a = var41_1a * var30;
	                    final float var57a = var55a * var22 + var41_2a * var24;
	                    final float var61a = (-var41_2a) * var22 + var55a * var24;
	                    var2.addVertex((double)(var14 + var57a), (double)(var16 + var53a), (double)(var18 + var61a));
	                    
	                    final float var47 = var41_1a - var41_2a;
	                    final float var49 = var41_1a + var41_2a;
	                    final float var53b = var47 * var28;
	                    final float var55b = -(var47 * var30);
	                    final float var57b = var55b * var22 - var49 * var24;
	                    final float var61b = var49 * var22 + var55b * var24;
	                    var2.addVertex((double)(var14 + var57b), (double)(var16 + var53b), (double)(var18 + var61b));
	                    
	                    final float var47a = var41_1a - (2f) * var41_2a;
	                    final float var49a = (2f) * var41_1a + var41_2a;
	                    final float var53c = var47a * var28;
	                    final float var55c = -(var47a * var30);
	                    final float var57c = var55c * var22 - var49a * var24;
	                    final float var61c = var49a * var22 + var55c * var24;
	                    var2.addVertex((double)(var14 + var57c), (double)(var16 + var53c), (double)(var18 + var61c));*/
	                /*for (int var38 = 0; var38 < 4; ++var38)
	                {
	                    float var39 = 0.0f;
	                    float var41 = ((var38 & 2) - 1) * size;
	                    float var43 = ((var38 + 1 & 2) - 1) * size;
	                    float var47 = var41 * var36 - var43 * var34;
	                    float var49 = var43 * var36 + var41 * var34;
	                    float var53 = var47 * var28 + var39 * var30;
	                    float var55 = var39 * var28 - var47 * var30;
	                    float var57 = var55 * var22 - var49 * var24;
	                    float var61 = var49 * var22 + var55 * var24;
	                    var2.addVertex((double)(var14 + var57), (double)(var16 + var53), (double)(var18 + var61));
	                }*/
	                    final float var39 = 0.0f;
	                    final float var41 = ((0 & 2) - 1) * size;
	                    final float var43 = ((0 + 1 & 2) - 1) * size;
	                    final float var47 = var41 * var36 - var43 * var34;
	                    final float var49 = var43 * var36 + var41 * var34;
	                    final float var53 = var47 * var28 + var39 * var30;
	                    final float var55 = var39 * var28 - var47 * var30;
	                    final float var57 = var55 * var22 - var49 * var24;
	                    final float var61 = var49 * var22 + var55 * var24;
	                    var2.addVertex((double)(var14 + var57), (double)(var16 + var53), (double)(var18 + var61));
	                    final float var39a = 0.0f;
	                    final float var41a = ((1 & 2) - 1) * size;
	                    final float var43a = ((1 + 1 & 2) - 1) * size;
	                    final float var47a = var41a * var36 - var43a * var34;
	                    final float var49a = var43a * var36 + var41a * var34;
	                    final float var53a = var47a * var28 + var39a * var30;
	                    final float var55a = var39a * var28 - var47a * var30;
	                    final float var57a = var55a * var22 - var49a * var24;
	                    final float var61a = var49a * var22 + var55a * var24;
	                    var2.addVertex((double)(var14 + var57a), (double)(var16 + var53a), (double)(var18 + var61a));
	                    final float var39b = 0.0f;
	                    final float var41b = ((2 & 2) - 1) * size;
	                    final float var43b = ((2 + 1 & 2) - 1) * size;
	                    final float var47b = var41b * var36 - var43b * var34;
	                    final float var49b = var43b * var36 + var41b * var34;
	                    final float var53b = var47b * var28 + var39b * var30;
	                    final float var55b = var39b * var28 - var47b * var30;
	                    final float var57b = var55b * var22 - var49b * var24;
	                    final float var61b = var49b * var22 + var55b * var24;
	                    var2.addVertex((double)(var14 + var57b), (double)(var16 + var53b), (double)(var18 + var61b));
	                    final float var39c = 0.0f;
	                    final float var41c = ((3 & 2) - 1) * size;
	                    final float var43c = ((3 + 1 & 2) - 1) * size;
	                    final float var47c = var41c * var36 - var43c * var34;
	                    final float var49c = var43c * var36 + var41c * var34;
	                    final float var53c = var47c * var28 + var39c * var30;
	                    final float var55c = var39c * var28 - var47c * var30;
	                    final float var57c = var55c * var22 - var49c * var24;
	                    final float var61c = var49c * var22 + var55c * var24;
	                    var2.addVertex((double)(var14 + var57c), (double)(var16 + var53c), (double)(var18 + var61c));
	                //}
	                
	            }
	        }

	        var2.draw();
	    }

}
