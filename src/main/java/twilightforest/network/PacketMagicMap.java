package twilightforest.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SMapDataPacket;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.network.NetworkEvent;
import twilightforest.TFMagicMapData;
import twilightforest.item.ItemTFMagicMap;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

// Rewraps vanilla SPacketMaps to properly expose our custom decorations
public class PacketMagicMap {
	private final int mapID;
	private final byte[] featureData;
	private final SMapDataPacket inner;

	public PacketMagicMap(int mapID, TFMagicMapData mapData, SMapDataPacket inner) {
		this.mapID = mapID;
		this.featureData = mapData.serializeFeatures();
		this.inner = inner;
	}

	public PacketMagicMap(PacketBuffer buf) {
		mapID = buf.readVarInt();
		featureData = buf.readByteArray();

		inner = new SMapDataPacket();
		try {
			inner.readPacketData(buf);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't read inner SPacketMaps", e);
		}
	}

	public void encode(PacketBuffer buf) {
		buf.writeVarInt(mapID);
		buf.writeByteArray(featureData);

		try {
			inner.writePacketData(buf);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't write inner SPacketMaps", e);
		}
	}

	public static class Handler {
		public static boolean onMessage(PacketMagicMap message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(new Runnable() {
				@Override
				public void run() {

//					MapItemRenderer mapItemRenderer = Minecraft.getInstance().entityRenderer.getMapItemRenderer();
					TFMagicMapData mapData = ItemTFMagicMap.loadMapData(message.mapID, Minecraft.getInstance().world);

					// Adapted from NetHandlerPlayClient#handleMaps
					if (mapData == null)
					{
						String s = ItemTFMagicMap.STR_ID + "_" + message.mapID;
						mapData = new TFMagicMapData(s);

//						if (mapItemRenderer.getMapInstanceIfExists(s) != null)
//						{
//							MapData mapdata1 = mapItemRenderer.getData(mapItemRenderer.getMapInstanceIfExists(s));
//
//							if (mapdata1 instanceof TFMagicMapData)
//							{
//								mapData = (TFMagicMapData) mapdata1;
//							}
//						}

//						Minecraft.getInstance().world.setData(s, mapData);
					}

					message.inner.setMapdataTo(mapData);

					// Deserialize to tfDecorations
					mapData.deserializeFeatures(message.featureData);

					// Cheat and put tfDecorations into main collection so they are called by renderer
					// However, clear the decorations vanilla put there so player markers go above feature markers.
					Map<String, MapDecoration> saveVanilla = mapData.mapDecorations;
//					mapData.mapDecorations = new LinkedHashMap<>();

					for (TFMagicMapData.TFMapDecoration tfDecor : mapData.tfDecorations) {
						mapData.mapDecorations.put(tfDecor.toString(), tfDecor);
					}

					mapData.mapDecorations.putAll(saveVanilla);

//					mapItemRenderer.updateMapTexture(mapData);
				}
			});

			return true;
		}
	}

}
