package twilightforest.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SMapDataPacket;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.fml.network.NetworkEvent;
import twilightforest.TFMazeMapData;
import twilightforest.item.ItemTFMazeMap;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * Vanilla's SPacketMaps handler looks for and loads the vanilla MapData instances.
 * We rewrap the packet here in order to load our own MapData instances properly.
 */
public class PacketMazeMap {

	private final SMapDataPacket inner;

	public PacketMazeMap(SMapDataPacket inner) {
		this.inner = inner;
	}

	public PacketMazeMap(PacketBuffer buf) {
		inner = new SMapDataPacket();
		try {
			inner.readPacketData(buf);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't read inner SPacketMaps", e);
		}
	}

	public void encode(PacketBuffer buf) {
		try {
			inner.writePacketData(buf);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't write inner SPacketMaps", e);
		}
	}

	public static class Handler {
		public static boolean onMessage(PacketMazeMap message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(new Runnable() {
				@Override
				public void run() {

//					MapItemRenderer mapItemRenderer = Minecraft.getInstance().entityRenderer.getMapItemRenderer();
					MapData mapData = ItemTFMazeMap.loadMapData(message.inner.getMapId(), Minecraft.getInstance().world);

					// Adapted from NetHandlerPlayClient#handleMaps
					if (mapData == null) {

						String s = ItemTFMazeMap.STR_ID + "_" + message.inner.getMapId();
						mapData = new TFMazeMapData(s);

//						if (mapItemRenderer.getMapInstanceIfExists(s) != null) {
//							MapData mapData1 = mapItemRenderer.getData(mapItemRenderer.getMapInstanceIfExists(s));
//							if (mapData1 != null) {
//								mapData = mapData1;
//							}
//						}

//						Minecraft.getInstance().world.setData(s, mapData);
					}

					message.inner.setMapdataTo(mapData);
//					mapItemRenderer.updateMapTexture(mapData);
				}
			});
			return true;
		}
	}
}
