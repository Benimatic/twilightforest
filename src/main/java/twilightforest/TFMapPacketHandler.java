package twilightforest;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketMaps;
import twilightforest.item.ItemTFMagicMap;
import twilightforest.item.ItemTFMazeMap;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Sharable
public class TFMapPacketHandler
{
	
	
	/**
	 * Extract a Packet131MapData packet from a Packet250CustomPayload.  This is a little silly, huh?
	 */
	public SPacketMaps readMapPacket(ByteBuf byteBuf) {
		
		SPacketMaps mapPacket = new SPacketMaps();
		try {
			mapPacket.readPacketData(new PacketBuffer(byteBuf));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mapPacket;
	}
	
	/**
	 * Make a Packet250CustomPayload that wraps around a MapData packet, and sends on a specific channel
	 */
	public static Packet makeMagicMapPacket(String mapChannel, short mapID, byte[] datas) {
		
		SPacketMaps mapPacket = new SPacketMaps(mapID, datas);
        PacketBuffer payload = new PacketBuffer(Unpooled.buffer());

        try {
			mapPacket.writePacketData(payload);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//        Packet pkt = new S3FPacketCustomPayload(mapChannel, payload);
        
		FMLProxyPacket pkt = new FMLProxyPacket(payload, mapChannel);

		return pkt;
	}
	

	/**
	 * Packet!
	 */
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void incomingPacket(ClientCustomPacketEvent event)
	{
//		System.out.println("Incoming packet detected!");
		
		if (event.getPacket().channel().equals(ItemTFMagicMap.STR_ID))
		{
			//System.out.println("Incoming magic map packet detected!");
			SPacketMaps mapPacket = readMapPacket(event.getPacket().payload());
			ItemTFMagicMap.getMPMapData(mapPacket.getMapId(), TwilightForestMod.proxy.getClientWorld()).updateMPMapData(mapPacket.func_149187_d());
		}
		else if (event.getPacket().channel().equals(ItemTFMazeMap.STR_ID))
		{
			//System.out.println("Incoming maze map packet detected!");

			SPacketMaps mapPacket = readMapPacket(event.getPacket().payload());
			TFMazeMapData data = ItemTFMazeMap.getMPMapData(mapPacket.getMapId(), TwilightForestMod.proxy.getClientWorld());
			data.updateMPMapData(mapPacket.func_149187_d());
	        Minecraft.getMinecraft().entityRenderer.getMapItemRenderer().updateMapTexture(data);
		}
	}
}
