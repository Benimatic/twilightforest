package twilightforest.network;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.client.DimensionSpecialEffectsManager;
import net.neoforged.neoforge.network.NetworkEvent;
import twilightforest.TwilightForestMod;
import twilightforest.client.TwilightForestRenderInfo;
import twilightforest.client.renderer.TFWeatherRenderer;

public class StructureProtectionClearPacket {

	public StructureProtectionClearPacket() {
	}

	public StructureProtectionClearPacket(FriendlyByteBuf unused) {
	}

	public void encode(FriendlyByteBuf unused) {
	}

	public static class Handler {
		public static boolean onMessage(StructureProtectionClearPacket message, NetworkEvent.Context ctx) {
			ctx.enqueueWork(() -> {
				DimensionSpecialEffects info = DimensionSpecialEffectsManager.getForType(TwilightForestMod.prefix("renderer"));

				// add weather box if needed
				if (info instanceof TwilightForestRenderInfo) {
					TFWeatherRenderer.setProtectedBox(null);
				}
			});

			ctx.setPacketHandled(true);
			return true;
		}
	}
}
