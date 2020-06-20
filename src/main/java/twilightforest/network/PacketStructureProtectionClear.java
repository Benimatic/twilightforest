package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.dimension.Dimension;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.network.NetworkEvent;
import twilightforest.client.renderer.TFWeatherRenderer;
import twilightforest.world.TwilightForestDimension;

import java.util.function.Supplier;

public class PacketStructureProtectionClear {

	public PacketStructureProtectionClear() {}

	public PacketStructureProtectionClear(PacketBuffer unused) {}

	public void encode(PacketBuffer unused) {}

	public static class Handler {
		public static boolean onMessage(PacketStructureProtectionClear message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				Dimension provider = Minecraft.getInstance().world.dimension;

				// add weather box if needed
				if (provider instanceof TwilightForestDimension) {
					IRenderHandler weatherRenderer = provider.getWeatherRenderer();

					if (weatherRenderer instanceof TFWeatherRenderer) {
						((TFWeatherRenderer) weatherRenderer).setProtectedBox(null);
					}
				}
			});

			return true;
		}
	}
}
