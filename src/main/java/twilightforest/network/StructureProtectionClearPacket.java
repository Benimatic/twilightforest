package twilightforest.network;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.client.DimensionSpecialEffectsManager;
import net.minecraftforge.network.NetworkEvent;
import twilightforest.TwilightForestMod;
import twilightforest.client.TwilightForestRenderInfo;
import twilightforest.client.renderer.TFWeatherRenderer;

import java.util.function.Supplier;

public class StructureProtectionClearPacket {

	public StructureProtectionClearPacket() {
	}

	public StructureProtectionClearPacket(FriendlyByteBuf unused) {
	}

	public void encode(FriendlyByteBuf unused) {
	}

	public static class Handler {
		public static boolean onMessage(StructureProtectionClearPacket message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				DimensionSpecialEffects info = DimensionSpecialEffectsManager.getForType(TwilightForestMod.prefix("renderer"));

				// add weather box if needed
				if (info instanceof TwilightForestRenderInfo) {
					TFWeatherRenderer.setProtectedBox(null);
				}
			});

			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
