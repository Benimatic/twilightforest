package twilightforest.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import twilightforest.TwilightForestMod;
import twilightforest.init.BiomeKeys;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID, value = Dist.CLIENT)
public class FogHandler {

	private static final float[] spoopColors = new float[3];
	private static float spoopColor = 0F;
	private static float spoopFog = 1F;

	@SubscribeEvent
	public static void fogColors(ViewportEvent.ComputeFogColor event) {
		boolean flag = isSpooky();
		if (flag || spoopColor > 0F) {
			final float[] realColors = {event.getRed(), event.getGreen(), event.getBlue()};
			final float[] lerpColors = {130F / 255F, 115F / 255F, 145F / 255F};
			for (int i = 0; i < 3; i++) {
				final float real = realColors[i];
				final float spoop = lerpColors[i];
				final boolean inverse = real > spoop;
				spoopColors[i] = real == spoop ? spoop : Mth.clampedLerp(inverse ? spoop : real, inverse ? real : spoop, spoopColor);
			}
			float shift = (float) (0.01F * event.getPartialTick());
			if (flag)
				spoopColor += shift;
			else
				spoopColor -= shift;
			spoopColor = Mth.clamp(spoopColor, 0F, 1F);
			event.setRed(spoopColors[0]);
			event.setGreen(spoopColors[1]);
			event.setBlue(spoopColors[2]);
		}
	}

	@SubscribeEvent
	public static void fog(ViewportEvent.RenderFog event) {
		boolean flag = isSpooky();
		if (flag || spoopFog < 1F) {
			float f = 48F;
			f = f >= event.getFarPlaneDistance() ? event.getFarPlaneDistance() : Mth.clampedLerp(f, event.getFarPlaneDistance(), spoopFog);
			float shift = (float) (0.001F * event.getPartialTick());
			if (flag)
				spoopFog -= shift;
			else
				spoopFog += shift;
			spoopFog = Mth.clamp(spoopFog, 0F, 1F);

			if (event.getMode() == FogRenderer.FogMode.FOG_SKY) {
				RenderSystem.setShaderFogStart(0.0F);
				RenderSystem.setShaderFogEnd(f);
			} else {
				RenderSystem.setShaderFogStart(f * 0.75F);
				RenderSystem.setShaderFogEnd(f);
			}
		}
	}

	private static boolean isSpooky() {
		return Minecraft.getInstance().level != null && Minecraft.getInstance().player != null &&
				Minecraft.getInstance().level.getBiome(Minecraft.getInstance().player.blockPosition()).is(BiomeKeys.SPOOKY_FOREST);
	}
}
