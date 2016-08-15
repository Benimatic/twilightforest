package twilightforest.client.renderer;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.client.IRenderHandler;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.biomes.TFBiomeDarkForest;
import twilightforest.biomes.TFBiomeFireSwamp;
import twilightforest.biomes.TFBiomeGlacier;
import twilightforest.biomes.TFBiomeHighlands;
import twilightforest.biomes.TFBiomeFinalPlateau;
import twilightforest.biomes.TFBiomeSnow;
import twilightforest.biomes.TFBiomeSwamp;
import twilightforest.biomes.TFBiomeThornlands;

/**
 * Copypasta of EntityRenderer.renderRainSnow() hacked to include progression environmental effects
 */
public class TFWeatherRenderer extends IRenderHandler {
	
    private static final ResourceLocation locationRainPng = new ResourceLocation("textures/environment/rain.png");
    private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");
    private static final ResourceLocation locationBlizzardPng = new ResourceLocation(TwilightForestMod.ENVRIO_DIR + "blizzard.png");
    private static final ResourceLocation locationMosquitoPng = new ResourceLocation(TwilightForestMod.ENVRIO_DIR + "mosquitoes.png");
    private static final ResourceLocation locationAshesPng = new ResourceLocation(TwilightForestMod.ENVRIO_DIR + "ashes.png");
    private static final ResourceLocation locationDarkstreamPng = new ResourceLocation(TwilightForestMod.ENVRIO_DIR + "darkstream.png");
    private static final ResourceLocation locationBigrainPng = new ResourceLocation(TwilightForestMod.ENVRIO_DIR + "bigrain.png");
    private static final ResourceLocation locationSparklesPng = new ResourceLocation(TwilightForestMod.ENVRIO_DIR + "sparkles.png");

    /** Rain X coords */
    float[] rainXCoords;
    /** Rain Y coords */
    float[] rainYCoords;
	private int rendererUpdateCount;
	private Random random;
	
	
	private StructureBoundingBox protectedBox;
	
	
	public TFWeatherRenderer() {
		this.random = new org.bogdang.modifications.random.XSTR();
	}



	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		
        ++this.rendererUpdateCount;
		
		// do normal weather rendering
		renderNormalWeather(partialTicks, mc);
        
		if (world.getGameRules().getGameRuleBooleanValue(TwilightForestMod.ENFORCED_PROGRESSION_RULE) && !mc.thePlayer.capabilities.isCreativeMode) {
			// locked biome weather effects
			renderLockedBiome(partialTicks, world, mc);

			// locked structures
			renderLockedStructure(partialTicks, world, mc);
		}
	}



	private void renderNormalWeather(float partialTicks, Minecraft mc) {
		float rainStrength = mc.theWorld.getRainStrength(partialTicks);

        if (rainStrength > 0.0F)
        {
            mc.entityRenderer.enableLightmap((double)partialTicks);

            this.initializeRainCoords();

            EntityLivingBase entitylivingbase = mc.renderViewEntity;
            WorldClient worldclient = mc.theWorld;
            int k2 = MathHelper.floor_double(entitylivingbase.posX);
            int l2 = MathHelper.floor_double(entitylivingbase.posY);
            int i3 = MathHelper.floor_double(entitylivingbase.posZ);
            Tessellator tessellator = Tessellator.instance;
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            double d0 = entitylivingbase.lastTickPosX + (entitylivingbase.posX - entitylivingbase.lastTickPosX) * (double)partialTicks;
            double d1 = entitylivingbase.lastTickPosY + (entitylivingbase.posY - entitylivingbase.lastTickPosY) * (double)partialTicks;
            double d2 = entitylivingbase.lastTickPosZ + (entitylivingbase.posZ - entitylivingbase.lastTickPosZ) * (double)partialTicks;
            int k = MathHelper.floor_double(d1);
            byte range = 5;

            if (mc.gameSettings.fancyGraphics)
            {
                range = 10;
            }

            byte b1 = -1;
            float f5 = (float)this.rendererUpdateCount + partialTicks;

            if (mc.gameSettings.fancyGraphics)
            {
                range = 10;
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            for (int l = i3 - range; l <= i3 + range; ++l)
            {
                for (int i1 = k2 - range; i1 <= k2 + range; ++i1)
                {
                    int j1 = (l - i3 + 16) * 32 + i1 - k2 + 16;
                    float f6 = this.rainXCoords[j1] * 0.5F;
                    float f7 = this.rainYCoords[j1] * 0.5F;
                    BiomeGenBase biomegenbase = worldclient.getBiomeGenForCoords(i1, l);

                    if (biomegenbase.canSpawnLightningBolt() || biomegenbase.getEnableSnow())
                    {
                        int k1 = worldclient.getPrecipitationHeight(i1, l);
                        int l1 = l2 - range;
                        int i2 = l2 + range;

                        if (l1 < k1)
                        {
                            l1 = k1;
                        }

                        if (i2 < k1)
                        {
                            i2 = k1;
                        }

                        float f8 = 1.0F;
                        int j2 = k1;

                        if (k1 < k)
                        {
                            j2 = k;
                        }

                        if (l1 != i2)
                        {
                            this.random.setSeed((long)(i1 * i1 * 3121 + i1 * 45238971 ^ l * l * 418711 + l * 13761));
                            float f9 = biomegenbase.getFloatTemperature(i1, l1, l);
                            float downwardsMotion;
                            double xDist;

                            if (worldclient.getWorldChunkManager().getTemperatureAtHeight(f9, k1) >= 0.15F)
                            {
                                if (b1 != 0)
                                {
                                    if (b1 >= 0)
                                    {
                                        tessellator.draw();
                                    }

                                    b1 = 0;
                                    mc.getTextureManager().bindTexture(locationRainPng);
                                    tessellator.startDrawingQuads();
                                }

                                downwardsMotion = ((float)(this.rendererUpdateCount + i1 * i1 * 3121 + i1 * 45238971 + l * l * 418711 + l * 13761 & 31) + partialTicks) / 32.0F * (3.0F + this.random.nextFloat());
                                double d3 = (double)((float)i1 + 0.5F) - entitylivingbase.posX;
                                xDist = (double)((float)l + 0.5F) - entitylivingbase.posZ;
                                float f12 = MathHelper.sqrt_double(d3 * d3 + xDist * xDist) / (float)range;
                                float f13 = 1.0F;
                                tessellator.setBrightness(worldclient.getLightBrightnessForSkyBlocks(i1, j2, l, 0));
                                tessellator.setColorRGBA_F(f13, f13, f13, ((1.0F - f12 * f12) * 0.5F + 0.5F) * rainStrength);
                                tessellator.setTranslation(-d0 * 1.0D, -d1 * 1.0D, -d2 * 1.0D);
                                tessellator.addVertexWithUV((double)((float)i1 - f6) + 0.5D, (double)l1, (double)((float)l - f7) + 0.5D, (double)(0.0F * f8), (double)((float)l1 * f8 / 4.0F + downwardsMotion * f8));
                                tessellator.addVertexWithUV((double)((float)i1 + f6) + 0.5D, (double)l1, (double)((float)l + f7) + 0.5D, (double)(1.0F * f8), (double)((float)l1 * f8 / 4.0F + downwardsMotion * f8));
                                tessellator.addVertexWithUV((double)((float)i1 + f6) + 0.5D, (double)i2, (double)((float)l + f7) + 0.5D, (double)(1.0F * f8), (double)((float)i2 * f8 / 4.0F + downwardsMotion * f8));
                                tessellator.addVertexWithUV((double)((float)i1 - f6) + 0.5D, (double)i2, (double)((float)l - f7) + 0.5D, (double)(0.0F * f8), (double)((float)i2 * f8 / 4.0F + downwardsMotion * f8));
                                tessellator.setTranslation(0.0D, 0.0D, 0.0D);
                            }
                            else
                            {
                                if (b1 != 1)
                                {
                                    if (b1 >= 0)
                                    {
                                        tessellator.draw();
                                    }

                                    b1 = 1;
                                    mc.getTextureManager().bindTexture(locationSnowPng);
                                    tessellator.startDrawingQuads();
                                }

                                downwardsMotion = ((float)(this.rendererUpdateCount & 511) + partialTicks) / 512.0F;
                                float f16 = this.random.nextFloat() + f5 * 0.01F * (float)this.random.nextGaussian();
                                float f11 = this.random.nextFloat() + f5 * (float)this.random.nextGaussian() * 0.001F;
                                xDist = (double)((float)i1 + 0.5F) - entitylivingbase.posX;
                                double zDist = (double)((float)l + 0.5F) - entitylivingbase.posZ;
                                float f14 = MathHelper.sqrt_double(xDist * xDist + zDist * zDist) / (float)range;
                                float f15 = 1.0F;
                                tessellator.setBrightness((worldclient.getLightBrightnessForSkyBlocks(i1, j2, l, 0) * 3 + 15728880) / 4);
                                tessellator.setColorRGBA_F(f15, f15, f15, ((1.0F - f14 * f14) * 0.3F + 0.5F) * rainStrength);
                                tessellator.setTranslation(-d0 * 1.0D, -d1 * 1.0D, -d2 * 1.0D);
                                tessellator.addVertexWithUV((double)((float)i1 - f6) + 0.5D, (double)l1, (double)((float)l - f7) + 0.5D, (double)(0.0F * f8 + f16), (double)((float)l1 * f8 / 4.0F + downwardsMotion * f8 + f11));
                                tessellator.addVertexWithUV((double)((float)i1 + f6) + 0.5D, (double)l1, (double)((float)l + f7) + 0.5D, (double)(1.0F * f8 + f16), (double)((float)l1 * f8 / 4.0F + downwardsMotion * f8 + f11));
                                tessellator.addVertexWithUV((double)((float)i1 + f6) + 0.5D, (double)i2, (double)((float)l + f7) + 0.5D, (double)(1.0F * f8 + f16), (double)((float)i2 * f8 / 4.0F + downwardsMotion * f8 + f11));
                                tessellator.addVertexWithUV((double)((float)i1 - f6) + 0.5D, (double)i2, (double)((float)l - f7) + 0.5D, (double)(0.0F * f8 + f16), (double)((float)i2 * f8 / 4.0F + downwardsMotion * f8 + f11));
                                tessellator.setTranslation(0.0D, 0.0D, 0.0D);
                            }
                        }
                    }
                }
            }

            if (b1 >= 0)
            {
                tessellator.draw();
            }

            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            mc.entityRenderer.disableLightmap((double)partialTicks);
        }
	}



	private void renderLockedBiome(float partialTicks, WorldClient world, Minecraft mc) {
		// check nearby for locked biome
        if (isNearLockedBiome(world, mc.renderViewEntity)) {
            this.initializeRainCoords();

            EntityLivingBase entitylivingbase = mc.renderViewEntity;
            WorldClient worldclient = mc.theWorld;
            int px = MathHelper.floor_double(entitylivingbase.posX);
            int py = MathHelper.floor_double(entitylivingbase.posY);
            int pz = MathHelper.floor_double(entitylivingbase.posZ);
            Tessellator tessellator = Tessellator.instance;
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            double offX = entitylivingbase.lastTickPosX + (entitylivingbase.posX - entitylivingbase.lastTickPosX) * (double)partialTicks;
            double offY = entitylivingbase.lastTickPosY + (entitylivingbase.posY - entitylivingbase.lastTickPosY) * (double)partialTicks;
            double offZ = entitylivingbase.lastTickPosZ + (entitylivingbase.posZ - entitylivingbase.lastTickPosZ) * (double)partialTicks;
            int floorY = MathHelper.floor_double(offY);
            byte range = 5;

            if (mc.gameSettings.fancyGraphics)
            {
                range = 10;
            }

            byte drawFlag = -1;
            float preciseCount = (float)this.rendererUpdateCount + partialTicks;

            if (mc.gameSettings.fancyGraphics)
            {
                range = 15;
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            for (int dz = pz - range; dz <= pz + range; ++dz)
            {
                for (int dx = px - range; dx <= px + range; ++dx)
                {
                    int rainIndex = (dz - pz + 16) * 32 + dx - px + 16;
                    float rainX = this.rainXCoords[rainIndex] * 0.5F;
                    float rainZ = this.rainYCoords[rainIndex] * 0.5F;
                    BiomeGenBase biomegenbase = worldclient.getBiomeGenForCoords(dx, dz);


                    if (biomegenbase instanceof TFBiomeBase && entitylivingbase instanceof EntityPlayer && !((TFBiomeBase)biomegenbase).doesPlayerHaveRequiredAchievement((EntityPlayer)entitylivingbase)) {

                        int rainHeight = 0;//worldclient.getPrecipitationHeight(dx, dz);
                        int rainMin = py - range;
                        int rainMax = py + range * 2;

                        if (rainMin < rainHeight)
                        {
                            rainMin = rainHeight;
                        }

                        if (rainMax < rainHeight)
                        {
                            rainMax = rainHeight;
                        }

                        float one = 1.0F;
                        int rainFloor = rainHeight;

                        if (rainHeight < floorY)
                        {
                            rainFloor = floorY;
                        }

                        if (rainMin != rainMax)
                        {
                            this.random.setSeed((long)(dx * dx * 3121 + dx * 45238971 ^ dz * dz * 418711 + dz * 13761));

                            if (biomegenbase instanceof TFBiomeSnow || biomegenbase instanceof TFBiomeGlacier) {
                                // SNOW

                            	if (drawFlag != 0)
                            	{
                            		if (drawFlag >= 0)
                            		{
                            			tessellator.draw();
                            		}

                            		drawFlag = 0;
                            		mc.getTextureManager().bindTexture(locationBlizzardPng);
                            		tessellator.startDrawingQuads();
                            	}

                            	float countFactor = ((float)(this.rendererUpdateCount & 511) + partialTicks) / 512.0F;
                            	float uFactor = this.random.nextFloat() + preciseCount * 0.03F * (float)this.random.nextGaussian();
                            	float vFactor = this.random.nextFloat() + preciseCount * 0.001F * (float)this.random.nextGaussian();
                            	double xRange = (double)((float)dx + 0.5F) - entitylivingbase.posX;
                            	double zRange = (double)((float)dz + 0.5F) - entitylivingbase.posZ;
                            	float f14 = MathHelper.sqrt_double(xRange * xRange + zRange * zRange) / (float)range;
                            	float onee = 1.0F;
                            	tessellator.setBrightness((worldclient.getLightBrightnessForSkyBlocks(dx, rainFloor, dz, 0) * 3 + 15728880) / 4);
                            	tessellator.setColorRGBA_F(onee, onee, onee, ((1.0F - f14 * f14) * 0.3F + 0.5F) * 1.0F);
                            	tessellator.setTranslation(-offX * 1.0D, -offY * 1.0D, -offZ * 1.0D);
                            	tessellator.addVertexWithUV((double)((float)dx - rainX) + 0.5D, (double)rainMin, (double)((float)dz - rainZ) + 0.5D, (double)(0.0F * one + uFactor), (double)((float)rainMin * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx + rainX) + 0.5D, (double)rainMin, (double)((float)dz + rainZ) + 0.5D, (double)(1.0F * one + uFactor), (double)((float)rainMin * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx + rainX) + 0.5D, (double)rainMax, (double)((float)dz + rainZ) + 0.5D, (double)(1.0F * one + uFactor), (double)((float)rainMax * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx - rainX) + 0.5D, (double)rainMax, (double)((float)dz - rainZ) + 0.5D, (double)(0.0F * one + uFactor), (double)((float)rainMax * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.setTranslation(0.0D, 0.0D, 0.0D);
                            	
                            } else if (biomegenbase instanceof TFBiomeSwamp) {
                                // MOSQUITOES

                            	if (drawFlag != 1)
                            	{
                            		if (drawFlag >= 0)
                            		{
                            			tessellator.draw();
                            		}

                            		drawFlag = 1;
                            		mc.getTextureManager().bindTexture(locationMosquitoPng);
                            		tessellator.startDrawingQuads();
                            	}

                            	float countFactor = 0;//((float)(this.rendererUpdateCount & 511) + partialTicks) / 512.0F;
                            	float uFactor = this.random.nextFloat() + preciseCount * 0.03F * (float)this.random.nextGaussian();
                            	float vFactor = this.random.nextFloat() + preciseCount * 0.003F * (float)this.random.nextGaussian();
                            	//double xRange = (double)((float)dx + 0.5F) - entitylivingbase.posX;
                            	//double zRange = (double)((float)dz + 0.5F) - entitylivingbase.posZ;
                            	//float distanceFromPlayer = MathHelper.sqrt_double(xRange * xRange + zRange * zRange) / (float)range;
                                tessellator.setBrightness(983055);
                            	float r = random.nextFloat() * 0.3F;
                            	float g = random.nextFloat() * 0.3F;
                            	float b = random.nextFloat() * 0.3F;
                            	tessellator.setColorRGBA_F(r, g, b, 1.0F);//((1.0F - distanceFromPlayer * distanceFromPlayer) * 0.3F + 0.5F) * 1.0F);
                            	tessellator.setTranslation(-offX * 1.0D, -offY * 1.0D, -offZ * 1.0D);
                            	tessellator.addVertexWithUV((double)((float)dx - rainX) + 0.5D, (double)rainMin, (double)((float)dz - rainZ) + 0.5D, (double)(0.0F * one + uFactor), (double)((float)rainMin * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx + rainX) + 0.5D, (double)rainMin, (double)((float)dz + rainZ) + 0.5D, (double)(1.0F * one + uFactor), (double)((float)rainMin * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx + rainX) + 0.5D, (double)rainMax, (double)((float)dz + rainZ) + 0.5D, (double)(1.0F * one + uFactor), (double)((float)rainMax * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx - rainX) + 0.5D, (double)rainMax, (double)((float)dz - rainZ) + 0.5D, (double)(0.0F * one + uFactor), (double)((float)rainMax * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.setTranslation(0.0D, 0.0D, 0.0D);
                            } else if (biomegenbase instanceof TFBiomeFireSwamp) {
                                // ASHES

                            	if (drawFlag != 2)
                            	{
                            		if (drawFlag >= 0)
                            		{
                            			tessellator.draw();
                            		}

                            		drawFlag = 2;
                            		mc.getTextureManager().bindTexture(locationAshesPng);
                            		tessellator.startDrawingQuads();
                            	}

                            	float countFactor = -((float)(this.rendererUpdateCount & 1023) + partialTicks) / 1024.0F;
                            	float uFactor = this.random.nextFloat() + preciseCount * 0.001F * (float)this.random.nextGaussian();
                            	float vFactor = this.random.nextFloat() + preciseCount * 0.001F * (float)this.random.nextGaussian();
                            	double xRange = (double)((float)dx + 0.5F) - entitylivingbase.posX;
                            	double zRange = (double)((float)dz + 0.5F) - entitylivingbase.posZ;
                            	float distanceFromPlayer = MathHelper.sqrt_double(xRange * xRange + zRange * zRange) / (float)range;
                                tessellator.setBrightness(983055);
                            	float bright = random.nextFloat() * 0.2F + 0.8F;
                            	tessellator.setColorRGBA_F(bright, bright, bright, ((1.0F - distanceFromPlayer * distanceFromPlayer) * 0.3F + 0.5F) * 1.0F);
                            	tessellator.setTranslation(-offX * 1.0D, -offY * 1.0D, -offZ * 1.0D);
                            	tessellator.addVertexWithUV((double)((float)dx - rainX) + 0.5D, (double)rainMin, (double)((float)dz - rainZ) + 0.5D, (double)(0.0F * one + uFactor), (double)((float)rainMin * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx + rainX) + 0.5D, (double)rainMin, (double)((float)dz + rainZ) + 0.5D, (double)(1.0F * one + uFactor), (double)((float)rainMin * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx + rainX) + 0.5D, (double)rainMax, (double)((float)dz + rainZ) + 0.5D, (double)(1.0F * one + uFactor), (double)((float)rainMax * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx - rainX) + 0.5D, (double)rainMax, (double)((float)dz - rainZ) + 0.5D, (double)(0.0F * one + uFactor), (double)((float)rainMax * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.setTranslation(0.0D, 0.0D, 0.0D);
                            } else if (biomegenbase instanceof TFBiomeDarkForest && random.nextInt(2) == 0) {
                                // DARKTHINGS

                            	if (drawFlag != 3)
                            	{
                            		if (drawFlag >= 0)
                            		{
                            			tessellator.draw();
                            		}

                            		drawFlag = 3;
                            		mc.getTextureManager().bindTexture(locationDarkstreamPng);
                            		tessellator.startDrawingQuads();
                            	}
                            	
                            	int darkRainMax = Math.min(rainMax, worldclient.getPrecipitationHeight(dx, dz));
                            	int darkRainMin = Math.min(rainMin, darkRainMax);

                            	float countFactor = -((float)(this.rendererUpdateCount & 511) + partialTicks) / 512.0F;
                            	float uFactor = 0;//this.random.nextFloat() + preciseCount * 0.001F * (float)this.random.nextGaussian();
                            	float vFactor = this.random.nextFloat() + preciseCount * 0.01F * (float)this.random.nextGaussian();
                            	double xRange = (double)((float)dx + 0.5F) - entitylivingbase.posX;
                            	double zRange = (double)((float)dz + 0.5F) - entitylivingbase.posZ;
                            	float distanceFromPlayer = MathHelper.sqrt_double(xRange * xRange + zRange * zRange) / (float)range;
                                tessellator.setBrightness(983055);
                            	float bright = 1.0F;
                            	float alpha = random.nextFloat();
                            	tessellator.setColorRGBA_F(bright, bright, bright, ((1.0F - distanceFromPlayer * distanceFromPlayer) * 0.3F + 0.5F) * alpha);
                            	tessellator.setTranslation(-offX * 1.0D, -offY * 1.0D, -offZ * 1.0D);
                            	tessellator.addVertexWithUV((double)((float)dx - rainX) + 0.5D, (double)darkRainMin, (double)((float)dz - rainZ) + 0.5D, (double)(0.0F * one + uFactor), (double)((float)darkRainMin * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx + rainX) + 0.5D, (double)darkRainMin, (double)((float)dz + rainZ) + 0.5D, (double)(1.0F * one + uFactor), (double)((float)darkRainMin * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx + rainX) + 0.5D, (double)darkRainMax, (double)((float)dz + rainZ) + 0.5D, (double)(1.0F * one + uFactor), (double)((float)darkRainMax * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx - rainX) + 0.5D, (double)darkRainMax, (double)((float)dz - rainZ) + 0.5D, (double)(0.0F * one + uFactor), (double)((float)darkRainMax * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.setTranslation(0.0D, 0.0D, 0.0D);
                            } else if (biomegenbase instanceof TFBiomeHighlands || biomegenbase instanceof TFBiomeThornlands || biomegenbase instanceof TFBiomeFinalPlateau) {
                                // GREENRAIN

                            	if (drawFlag != 4)
                            	{
                            		if (drawFlag >= 0)
                            		{
                            			tessellator.draw();
                            		}

                            		drawFlag = 4;
                            		mc.getTextureManager().bindTexture(locationBigrainPng);
                            		tessellator.startDrawingQuads();
                            	}
  
                            	float countFactor = ((float)(this.rendererUpdateCount + dx * dx * 3121 + dx * 45238971 + dz * dz * 418711 + dz * 13761 & 31) + partialTicks) / 32.0F * (3.0F + this.random.nextFloat());
                            	float uFactor = this.random.nextFloat();
                            	float vFactor = this.random.nextFloat();
                            	double xRange = (double)((float)dx + 0.5F) - entitylivingbase.posX;
                            	double zRange = (double)((float)dz + 0.5F) - entitylivingbase.posZ;
                            	float distanceFromPlayer = MathHelper.sqrt_double(xRange * xRange + zRange * zRange) / (float)range;
                                tessellator.setBrightness(worldclient.getLightBrightnessForSkyBlocks(dx, rainFloor, dz, 0));
                            	float bright = 1.0F;
                            	float alpha = 1.0F;//random.nextFloat();
                            	tessellator.setColorRGBA_F(bright, bright, bright, ((1.0F - distanceFromPlayer * distanceFromPlayer) * 0.3F + 0.5F) * alpha);
                            	tessellator.setTranslation(-offX * 1.0D, -offY * 1.0D, -offZ * 1.0D);
                            	tessellator.addVertexWithUV((double)((float)dx - rainX) + 0.5D, (double)rainMin, (double)((float)dz - rainZ) + 0.5D, (double)(0.0F * one + uFactor), (double)((float)rainMin * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx + rainX) + 0.5D, (double)rainMin, (double)((float)dz + rainZ) + 0.5D, (double)(1.0F * one + uFactor), (double)((float)rainMin * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx + rainX) + 0.5D, (double)rainMax, (double)((float)dz + rainZ) + 0.5D, (double)(1.0F * one + uFactor), (double)((float)rainMax * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx - rainX) + 0.5D, (double)rainMax, (double)((float)dz - rainZ) + 0.5D, (double)(0.0F * one + uFactor), (double)((float)rainMax * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.setTranslation(0.0D, 0.0D, 0.0D);
                            }

                        }
                    }
                }
            }

            if (drawFlag >= 0)
            {
                tessellator.draw();
            }

            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            mc.entityRenderer.disableLightmap((double)partialTicks);

        }
	}



	private void renderLockedStructure(float partialTicks, WorldClient world, Minecraft mc) {
		// draw locked structure thing
        if (isNearLockedStructure(world, mc.renderViewEntity)) {
            this.initializeRainCoords();

            EntityLivingBase entitylivingbase = mc.renderViewEntity;
            int px = MathHelper.floor_double(entitylivingbase.posX);
            int py = MathHelper.floor_double(entitylivingbase.posY);
            int pz = MathHelper.floor_double(entitylivingbase.posZ);
            Tessellator tessellator = Tessellator.instance;
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            double offX = entitylivingbase.lastTickPosX + (entitylivingbase.posX - entitylivingbase.lastTickPosX) * (double)partialTicks;
            double offY = entitylivingbase.lastTickPosY + (entitylivingbase.posY - entitylivingbase.lastTickPosY) * (double)partialTicks;
            double offZ = entitylivingbase.lastTickPosZ + (entitylivingbase.posZ - entitylivingbase.lastTickPosZ) * (double)partialTicks;
            byte range = 5;

            if (mc.gameSettings.fancyGraphics)
            {
                range = 10;
            }

            byte drawFlag = -1;
            float preciseCount = (float)this.rendererUpdateCount + partialTicks;

            if (mc.gameSettings.fancyGraphics)
            {
                range = 15;
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            for (int dz = pz - range; dz <= pz + range; ++dz)
            {
                for (int dx = px - range; dx <= px + range; ++dx)
                {
                    int rainIndex = (dz - pz + 16) * 32 + dx - px + 16;
                    float rainX = this.rainXCoords[rainIndex] * 0.5F;
                    float rainZ = this.rainYCoords[rainIndex] * 0.5F;

            		if (this.protectedBox != null && this.protectedBox.intersectsWith(dx, dz, dx, dz)) {
            			
            			//System.out.println("Column in structure");
 
                    	int structureMin = this.protectedBox.minY - 4;
                    	int structureMax = this.protectedBox.maxY + 4;
                    	int rainMin = py - range;
                    	int rainMax = py + range * 2;

                    	if (rainMin < structureMin)
                    	{
                            rainMin = structureMin;
                        }

                        if (rainMax < structureMin)
                        {
                            rainMax = structureMin;
                        }

                    	if (rainMin > structureMax)
                    	{
                            rainMin = structureMax;
                        }

                        if (rainMax > structureMax)
                        {
                            rainMax = structureMax;
                        }

                        float one = 1.0F;
                        if (rainMin != rainMax)
                        {
                            this.random.setSeed((long)(dx * dx * 3121 + dx * 45238971 ^ dz * dz * 418711 + dz * 13761));

                            if (true) {

                            	if (drawFlag != 0)
                            	{
                            		if (drawFlag >= 0)
                            		{
                            			tessellator.draw();
                            		}

                            		drawFlag = 0;
                            		mc.getTextureManager().bindTexture(locationSparklesPng);
                            		tessellator.startDrawingQuads();
                            	}

                            	float countFactor = ((float)(this.rendererUpdateCount & 511) + partialTicks) / 512.0F;
                            	float uFactor = this.random.nextFloat() + preciseCount * 0.01F * (float)this.random.nextGaussian();
                            	float vFactor = this.random.nextFloat() + preciseCount * 0.01F * (float)this.random.nextGaussian();
                            	double xRange = (double)((float)dx + 0.5F) - entitylivingbase.posX;
                            	double zRange = (double)((float)dz + 0.5F) - entitylivingbase.posZ;
                            	float distanceFromPlayer = MathHelper.sqrt_double(xRange * xRange + zRange * zRange) / (float)range;
                                tessellator.setBrightness(983055);
                            	float bright = 1.0F;
                            	float alpha = random.nextFloat();
                            	tessellator.setColorRGBA_F(bright, bright, bright, ((1.0F - distanceFromPlayer * distanceFromPlayer) * 0.3F + 0.5F) * alpha);
                            	tessellator.setTranslation(-offX * 1.0D, -offY * 1.0D, -offZ * 1.0D);
                            	tessellator.addVertexWithUV((double)((float)dx - rainX) + 0.5D, (double)rainMin, (double)((float)dz - rainZ) + 0.5D, (double)(0.0F * one + uFactor), (double)((float)rainMin * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx + rainX) + 0.5D, (double)rainMin, (double)((float)dz + rainZ) + 0.5D, (double)(1.0F * one + uFactor), (double)((float)rainMin * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx + rainX) + 0.5D, (double)rainMax, (double)((float)dz + rainZ) + 0.5D, (double)(1.0F * one + uFactor), (double)((float)rainMax * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.addVertexWithUV((double)((float)dx - rainX) + 0.5D, (double)rainMax, (double)((float)dz - rainZ) + 0.5D, (double)(0.0F * one + uFactor), (double)((float)rainMax * one / 4.0F + countFactor * one + vFactor));
                            	tessellator.setTranslation(0.0D, 0.0D, 0.0D);

                            }

                        }
                    }
                }
            }

            if (drawFlag >= 0)
            {
            	tessellator.draw();
            }

            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            mc.entityRenderer.disableLightmap((double)partialTicks);

        }
	}



	private void initializeRainCoords() {
		if (this.rainXCoords == null)
		{
		    this.rainXCoords = new float[1024];
		    this.rainYCoords = new float[1024];

		    for (int i = 0; i < 32; ++i)
		    {
		        for (int j = 0; j < 32; ++j)
		        {
		            float f2 = (float)(j - 16);
		            float f3 = (float)(i - 16);
		            float f4 = MathHelper.sqrt_float(f2 * f2 + f3 * f3);
		            this.rainXCoords[i << 5 | j] = -f3 / f4;
		            this.rainYCoords[i << 5 | j] = f2 / f4;
		        }
		    }
		}
	}



	private boolean isNearLockedBiome(World world, EntityLivingBase viewEntity) {
        int range = 15;
        int px = MathHelper.floor_double(viewEntity.posX);
        int pz = MathHelper.floor_double(viewEntity.posZ);
        
		for (int z = pz - range ; z <= pz + range; ++z) {
            for (int x = px - range; x <= px + range; ++x) {
                BiomeGenBase biomegenbase = world.getBiomeGenForCoords(x, z);
                if (biomegenbase instanceof TFBiomeBase && viewEntity instanceof EntityPlayer) {
                	TFBiomeBase tfBiome = (TFBiomeBase)biomegenbase;
                	EntityPlayer player = (EntityPlayer)viewEntity;
                	if (!tfBiome.doesPlayerHaveRequiredAchievement(player)) {
                		return true;
                	}
                }
            }
        }
		
		return false;
	}

	private boolean isNearLockedStructure(World world, EntityLivingBase viewEntity) {
        int range = 15;
        int px = MathHelper.floor_double(viewEntity.posX);
        int pz = MathHelper.floor_double(viewEntity.posZ);
        
        if (this.protectedBox != null && this.protectedBox.intersectsWith(px - range, pz - range, px + range, pz + range)) {
        	return true;
        }

        return false;
	}



	public StructureBoundingBox getProtectedBox() {
		return protectedBox;
	}



	public void setProtectedBox(StructureBoundingBox protectedBox) {
		this.protectedBox = protectedBox;
		
		//System.out.println("Set protected box to " + protectedBox);
	}

}



//if (l > 0 && this.random.nextInt(3) < this.rainSoundCounter++)
//{
//    this.rainSoundCounter = 0;
//
//    if (d1 > entitylivingbase.posY + 1.0D && worldclient.getPrecipitationHeight(MathHelper.floor_double(entitylivingbase.posX), MathHelper.floor_double(entitylivingbase.posZ)) > MathHelper.floor_double(entitylivingbase.posY))
//    {
//        this.mc.theWorld.playSound(d0, d1, d2, "ambient.weather.rain", 0.1F, 0.5F, false);
//    }
//    else
//    {
//        this.mc.theWorld.playSound(d0, d1, d2, "ambient.weather.rain", 0.2F, 1.0F, false);
//    }
//}
