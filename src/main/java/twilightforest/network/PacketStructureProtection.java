package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.network.NetworkEvent;
import twilightforest.client.renderer.TFWeatherRenderer;
import twilightforest.world.TwilightForestDimension;

import java.util.function.Supplier;

public class PacketStructureProtection {

	private final MutableBoundingBox sbb;

	public PacketStructureProtection(MutableBoundingBox sbb) {
		this.sbb = sbb;
	}

	public PacketStructureProtection(PacketBuffer buf) {
		sbb = new MutableBoundingBox(
				buf.readInt(), buf.readInt(), buf.readInt(),
				buf.readInt(), buf.readInt(), buf.readInt()
		);
	}

	public void encode(PacketBuffer buf) {
		buf.writeInt(sbb.minX);
		buf.writeInt(sbb.minY);
		buf.writeInt(sbb.minZ);
		buf.writeInt(sbb.maxX);
		buf.writeInt(sbb.maxY);
		buf.writeInt(sbb.maxZ);
	}

	public static class Handler {
		public static boolean onMessage(PacketStructureProtection message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				Dimension provider = Minecraft.getInstance().world.dimension;

                // add weather box if needed
                if (provider instanceof TwilightForestDimension) {
					IRenderHandler weatherRenderer = provider.getWeatherRenderer();

                    if (weatherRenderer instanceof TFWeatherRenderer) {
						((TFWeatherRenderer) weatherRenderer).setProtectedBox(message.sbb);
					}
                }
            });

			return true;
		}
	}
}
