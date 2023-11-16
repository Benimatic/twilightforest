package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.MapRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.neoforged.neoforge.network.NetworkEvent;
import twilightforest.TFMagicMapData;
import twilightforest.item.MagicMapItem;

// Rewraps vanilla SPacketMaps to properly expose our custom decorations
public class MagicMapPacket {
	private final byte[] featureData;
	private final ClientboundMapItemDataPacket inner;

	public MagicMapPacket(TFMagicMapData mapData, ClientboundMapItemDataPacket inner) {
		this.featureData = mapData.serializeFeatures();
		this.inner = inner;
	}

	public MagicMapPacket(FriendlyByteBuf buf) {
		this.featureData = buf.readByteArray();
		this.inner = new ClientboundMapItemDataPacket(buf);
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeByteArray(this.featureData);
		this.inner.write(buf);
	}

	public static class Handler {

		@SuppressWarnings("Convert2Lambda")
		public static boolean onMessage(MagicMapPacket message, NetworkEvent.Context ctx) {
			ctx.enqueueWork(new Runnable() {
				@Override
				public void run() {
					// [VanillaCopy] ClientPlayNetHandler#handleMaps with our own mapdatas
					MapRenderer mapitemrenderer = Minecraft.getInstance().gameRenderer.getMapRenderer();
					String s = MagicMapItem.getMapName(message.inner.getMapId());
					TFMagicMapData mapdata = TFMagicMapData.getMagicMapData(Minecraft.getInstance().level, s);
					if (mapdata == null) {
						mapdata = new TFMagicMapData(0, 0, message.inner.getScale(), false, false, message.inner.isLocked(), Minecraft.getInstance().level.dimension());
						TFMagicMapData.registerMagicMapData(Minecraft.getInstance().level, mapdata, s);
					}

					message.inner.applyToMap(mapdata);

					// TF - handle custom decorations
					mapdata.deserializeFeatures(message.featureData);

					mapitemrenderer.update(message.inner.getMapId(), mapdata);
				}
			});

			ctx.setPacketHandled(true);
			return true;
		}
	}

}
