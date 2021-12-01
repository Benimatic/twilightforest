package twilightforest.network;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.client.IWeatherRenderHandler;
import net.minecraftforge.network.NetworkEvent;
import twilightforest.TwilightForestMod;
import twilightforest.client.TwilightForestRenderInfo;
import twilightforest.client.renderer.TFWeatherRenderer;

import java.util.function.Supplier;

public class StructureProtectionClearPacket {

	public StructureProtectionClearPacket() {}

	public StructureProtectionClearPacket(FriendlyByteBuf unused) {}

	public void encode(FriendlyByteBuf unused) {}

	public static class Handler {
		public static boolean onMessage(StructureProtectionClearPacket message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				DimensionSpecialEffects info = DimensionSpecialEffects.EFFECTS.get(TwilightForestMod.prefix("renderer"));

				// add weather box if needed
				if (info instanceof TwilightForestRenderInfo) {
					IWeatherRenderHandler weatherRenderer = info.getWeatherRenderHandler();

					if (weatherRenderer instanceof TFWeatherRenderer) {
						((TFWeatherRenderer) weatherRenderer).setProtectedBox(null);
					}
				}
			});

			return true;
		}
	}
}
