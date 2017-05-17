package twilightforest.client.renderer;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.IRenderHandler;

import org.lwjgl.opengl.GL11;

import twilightforest.world.TFWorld;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        Tessellator tessellator = Tessellator.getInstance();
		final VertexBuffer buffer = tessellator.getBuffer();
		this.glSkyList = this.starGLCallList + 1;
        GL11.glNewList(this.glSkyList, GL11.GL_COMPILE);
        byte var7 = 64;
        int var8 = 256 / var7 + 2;
        float var6 = 16.0F;
        int var9;
        int var10;

		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        for (var9 = -var7 * var8; var9 <= var7 * var8; var9 += var7)
        {
            for (var10 = -var7 * var8; var10 <= var7 * var8; var10 += var7)
            {
	            buffer.pos((double)(var9 + 0), (double)var6, (double)(var10 + 0)).endVertex();
	            buffer.pos((double)(var9 + var7), (double)var6, (double)(var10 + 0)).endVertex();
	            buffer.pos((double)(var9 + var7), (double)var6, (double)(var10 + var7)).endVertex();
	            buffer.pos((double)(var9 + 0), (double)var6, (double)(var10 + var7)).endVertex();
            }
        }
		tessellator.draw();

        GL11.glEndList();
        this.glSkyList2 = this.starGLCallList + 2;
        GL11.glNewList(this.glSkyList2, GL11.GL_COMPILE);
        var6 = -16.0F;
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);

        for (var9 = -var7 * var8; var9 <= var7 * var8; var9 += var7)
        {
            for (var10 = -var7 * var8; var10 <= var7 * var8; var10 += var7)
            {
	            buffer.pos((double)(var9 + var7), (double)var6, (double)(var10 + 0)).endVertex();
	            buffer.pos((double)(var9 + 0), (double)var6, (double)(var10 + 0)).endVertex();
	            buffer.pos((double)(var9 + 0), (double)var6, (double)(var10 + var7)).endVertex();
	            buffer.pos((double)(var9 + var7), (double)var6, (double)(var10 + var7)).endVertex();
            }
        }

        tessellator.draw();
        GL11.glEndList();
    }
    
	
	@Override
	@SideOnly(Side.CLIENT)
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Vec3d var2 = getTwilightSkyColor(world);//Vec3 var2 = world.getSkyColor(mc.renderViewEntity, partialTicks);
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
        Tessellator tessellator = Tessellator.getInstance();
		final VertexBuffer buffer = tessellator.getBuffer();

		GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glColor3f(var3, var4, var5);
        GL11.glCallList(this.glSkyList);
        GL11.glDisable(GL11.GL_FOG);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.disableStandardItemLighting();
        float var9;
        float var10;
        float var11;
        float var12;

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

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        float var20 = 1.0f;

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
        double var25 = mc.player.getPositionEyes(partialTicks).yCoord - TFWorld.SEALEVEL;

        if (var25 < 0.0D)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 12.0F, 0.0F);
            GL11.glCallList(this.glSkyList2);
            GL11.glPopMatrix();
            var10 = 1.0F;
            var11 = -((float)(var25 + 65.0D));
            var12 = -var10;
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
	        buffer.pos((double)(-var10), (double)var11, (double)var10).color(0, 0,0, 255).endVertex();
            buffer.pos((double)var10, (double)var11, (double)var10).color(0, 0,0, 255).endVertex();
            buffer.pos((double)var10, (double)var12, (double)var10).color(0, 0,0, 255).endVertex();
            buffer.pos((double)(-var10), (double)var12, (double)var10).color(0, 0,0, 255).endVertex();
            buffer.pos((double)(-var10), (double)var12, (double)(-var10)).color(0, 0,0, 255).endVertex();
            buffer.pos((double)var10, (double)var12, (double)(-var10)).color(0, 0,0, 255).endVertex();
            buffer.pos((double)var10, (double)var11, (double)(-var10)).color(0, 0,0, 255).endVertex();
            buffer.pos((double)(-var10), (double)var11, (double)(-var10)).color(0, 0,0, 255).endVertex();
            buffer.pos((double)var10, (double)var12, (double)(-var10)).color(0, 0,0, 255).endVertex();
            buffer.pos((double)var10, (double)var12, (double)var10).color(0, 0,0, 255).endVertex();
            buffer.pos((double)var10, (double)var11, (double)var10).color(0, 0,0, 255).endVertex();
            buffer.pos((double)var10, (double)var11, (double)(-var10)).color(0, 0,0, 255).endVertex();
            buffer.pos((double)(-var10), (double)var11, (double)(-var10)).color(0, 0,0, 255).endVertex();
            buffer.pos((double)(-var10), (double)var11, (double)var10).color(0, 0,0, 255).endVertex();
            buffer.pos((double)(-var10), (double)var12, (double)var10).color(0, 0,0, 255).endVertex();
            buffer.pos((double)(-var10), (double)var12, (double)(-var10)).color(0, 0,0, 255).endVertex();
            buffer.pos((double)(-var10), (double)var12, (double)(-var10)).color(0, 0,0, 255).endVertex();
            buffer.pos((double)(-var10), (double)var12, (double)var10).color(0, 0,0, 255).endVertex();
            buffer.pos((double)var10, (double)var12, (double)var10).color(0, 0,0, 255).endVertex();
            buffer.pos((double)var10, (double)var12, (double)(-var10)).color(0, 0,0, 255).endVertex();
            tessellator.draw();
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
        var5 = 1.0F - (float)((Math.cos((double)var5 * Math.PI) + 1.0D) / 2.0D);
        var5 = var6 + (var5 - var6) / 3.0F;
        return var5;
	}

	/**
	 * Maybe in the future we can get the return of sky color by biome?
	 * @return
	 */
	private Vec3d getTwilightSkyColor(World world) {
		return new Vec3d(32 / 256.0, 34 / 256.0, 74 / 256.0);
//		return Vec3.createVectorHelper(43 / 256.0, 46 / 256.0, 99 / 256.0);
 	}

	
	 private void renderStars()
	    {
	        Random var1 = new Random(10842L);
	        Tessellator tessellator = Tessellator.getInstance();
		    final VertexBuffer buffer = tessellator.getBuffer();
		    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);

		    for (int var3 = 0; var3 < 3000; ++var3)
	        {
	            double var4 = (double)(var1.nextFloat() * 2.0F - 1.0F);
	            double var6 = (double)(var1.nextFloat() * 2.0F - 1.0F);
	            double var8 = (double)(var1.nextFloat() * 2.0F - 1.0F);
	            double size = (double)(0.10F + var1.nextFloat() * 0.25F);
	            double var12 = var4 * var4 + var6 * var6 + var8 * var8;

	            if (var12 < 1.0D && var12 > 0.01D)
	            {
	                var12 = 1.0D / Math.sqrt(var12);
	                var4 *= var12;
	                var6 *= var12;
	                var8 *= var12;
	                double var14 = var4 * 100.0D;
	                double var16 = var6 * 100.0D;
	                double var18 = var8 * 100.0D;
	                double var20 = Math.atan2(var4, var8);
	                double var22 = Math.sin(var20);
	                double var24 = Math.cos(var20);
	                double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
	                double var28 = Math.sin(var26);
	                double var30 = Math.cos(var26);
	                double var32 = var1.nextDouble() * Math.PI * 2.0D;
	                double var34 = Math.sin(var32);
	                double var36 = Math.cos(var32);

	                for (int var38 = 0; var38 < 4; ++var38)
	                {
	                    double var39 = 0.0D;
	                    double var41 = (double)((var38 & 2) - 1) * size;
	                    double var43 = (double)((var38 + 1 & 2) - 1) * size;
	                    double var47 = var41 * var36 - var43 * var34;
	                    double var49 = var43 * var36 + var41 * var34;
	                    double var53 = var47 * var28 + var39 * var30;
	                    double var55 = var39 * var28 - var47 * var30;
	                    double var57 = var55 * var22 - var49 * var24;
	                    double var61 = var49 * var22 + var55 * var24;
	                    buffer.pos(var14 + var57, var16 + var53, var18 + var61).endVertex();
	                }
	            }
	        }

	        tessellator.draw();
	    }

}
