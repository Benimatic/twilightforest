package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.MapRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraftforge.network.NetworkEvent;
import twilightforest.TFMazeMapData;
import twilightforest.item.MazeMapItem;

import java.util.function.Supplier;

/**
 * Vanilla's SPacketMaps handler looks for and loads the vanilla MapData instances.
 * We rewrap the packet here in order to load our own MapData instances properly.
 */
public class MazeMapPacket {

	private final ClientboundMapItemDataPacket inner;

	public MazeMapPacket(ClientboundMapItemDataPacket inner) {
		this.inner = inner;
	}

	public MazeMapPacket(FriendlyByteBuf buf) {
		this.inner = new ClientboundMapItemDataPacket(buf);
	}

	public void encode(FriendlyByteBuf buf) {
		this.inner.write(buf);
	}

	public static class Handler {

		@SuppressWarnings("Convert2Lambda")
		public static boolean onMessage(MazeMapPacket message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(new Runnable() {
				@Override
				public void run() {
					// [VanillaCopy] ClientPlayNetHandler#handleMaps with our own mapdatas
					MapRenderer mapitemrenderer = Minecraft.getInstance().gameRenderer.getMapRenderer();
					String s = MazeMapItem.getMapName(message.inner.getMapId());
					TFMazeMapData mapdata = TFMazeMapData.getMazeMapData(Minecraft.getInstance().level, s);
					if (mapdata == null) {
						mapdata = new TFMazeMapData(0, 0, message.inner.getScale(), false, false, message.inner.isLocked(), Minecraft.getInstance().level.dimension());
						TFMazeMapData.registerMazeMapData(Minecraft.getInstance().level, mapdata, s);
					}

					message.inner.applyToMap(mapdata);
					mapitemrenderer.update(message.inner.getMapId(), mapdata);
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
