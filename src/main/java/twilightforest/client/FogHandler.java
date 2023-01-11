package twilightforest.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFBiomes;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID, value = Dist.CLIENT)
public class FogHandler {

	private static final float[] spoopColors = new float[3];
	private static float spoopColor = 0F;

	@SubscribeEvent
	public static void fogColors(ViewportEvent.ComputeFogColor event) {
		boolean flag = isSpooky(Minecraft.getInstance().level, Minecraft.getInstance().player);
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

	private static float TERRAIN_NEAR = Float.NaN;
	private static float TERRAIN_FAR = Float.NaN;

	private static float SKY_NEAR = Float.NaN;
	private static float SKY_FAR = Float.NaN;

	@SubscribeEvent
	public static void renderFog(ViewportEvent.RenderFog event) {
		if (Minecraft.getInstance().cameraEntity instanceof LocalPlayer player && player.level instanceof ClientLevel clientLevel && clientLevel.effects() instanceof TwilightForestRenderInfo renderInfo) {
			event.setCanceled(true);
			boolean spooky = isSpooky(clientLevel, player);

			if (event.getMode().equals(FogRenderer.FogMode.FOG_TERRAIN)) {
				float far = spooky ? event.getFarPlaneDistance() * 0.5F : event.getFarPlaneDistance();
				float near = spooky ? far * 0.75F : event.getNearPlaneDistance();

				if (Float.isNaN(TERRAIN_FAR) && clientLevel.isLoaded(player.blockPosition())) TERRAIN_FAR = far;
				else TERRAIN_FAR = Mth.lerp(0.003F, TERRAIN_FAR, far);
				if (Float.isNaN(TERRAIN_NEAR) && clientLevel.isLoaded(player.blockPosition())) TERRAIN_NEAR = near;
				else TERRAIN_NEAR = Mth.lerp(0.003F * (TERRAIN_NEAR < near ? 0.5F : 2.0F), TERRAIN_NEAR, near);

				if (event.getType().equals(FogType.NONE)) {
					event.setNearPlaneDistance(TERRAIN_NEAR);
					event.setFarPlaneDistance(TERRAIN_FAR);
				}
			} else {
				float far = spooky ? event.getNearPlaneDistance() * 0.5F : event.getNearPlaneDistance();
				float near = spooky ? 0.0F : event.getNearPlaneDistance();

				if (Float.isNaN(SKY_FAR) && clientLevel.isLoaded(player.blockPosition())) SKY_FAR = far;
				else SKY_FAR = Mth.lerp(0.003F, SKY_FAR, far);
				if (Float.isNaN(SKY_NEAR) && clientLevel.isLoaded(player.blockPosition())) SKY_NEAR = near;
				else SKY_NEAR = Mth.lerp(0.003F * (SKY_FAR < near ? 0.5F : 2.0F), SKY_NEAR, near);

				if (event.getType().equals(FogType.NONE)) {
					event.setNearPlaneDistance(SKY_NEAR);
					event.setFarPlaneDistance(SKY_FAR);
				}
			}
		}
	}

	private static boolean isSpooky(@Nullable ClientLevel level, @Nullable LocalPlayer player) {
		return level != null && player != null && level.getBiome(player.blockPosition()).is(TFBiomes.SPOOKY_FOREST);
	}
}
