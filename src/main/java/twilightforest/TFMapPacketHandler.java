package twilightforest;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S34PacketMaps;
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
	public S34PacketMaps readMapPacket(ByteBuf byteBuf) {
		
		S34PacketMaps mapPacket = new S34PacketMaps();
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
		
		S34PacketMaps mapPacket = new S34PacketMaps(mapID, datas);
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
		
		if (event.packet.channel().equals(ItemTFMagicMap.STR_ID))
		{
			//System.out.println("Incoming magic map packet detected!");
			S34PacketMaps mapPacket = readMapPacket(event.packet.payload());
			ItemTFMagicMap.getMPMapData(mapPacket.func_149188_c(), TwilightForestMod.proxy.getClientWorld()).updateMPMapData(mapPacket.func_149187_d());
		}
		else if (event.packet.channel().equals(ItemTFMazeMap.STR_ID))
		{
			//System.out.println("Incoming maze map packet detected!");

			S34PacketMaps mapPacket = readMapPacket(event.packet.payload());
			TFMazeMapData data = ItemTFMazeMap.getMPMapData(mapPacket.func_149188_c(), TwilightForestMod.proxy.getClientWorld());
			data.updateMPMapData(mapPacket.func_149187_d());
	        Minecraft.getMinecraft().entityRenderer.getMapItemRenderer().func_148246_a(data);
		}
	}

//	@Override
//	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, EntityPlayer player) {
//		// read magic map packets
//		if (packet.channel.equals("tfmagicmap")) {
//			Packet131MapData mapPacket = readMapPacket(packet.data);
//			ItemTFMagicMap.getMPMapData(mapPacket.uniqueID, TwilightForestMod.proxy.getClientWorld()).updateMPMapData(mapPacket.itemData);
//		}
//
//		// read maze map packets
//		if (packet.channel.equals("tfmazemap")) {
//			Packet131MapData mapPacket = readMapPacket(packet.data);
//			ItemTFMazeMap.getMPMapData(mapPacket.uniqueID, TwilightForestMod.proxy.getClientWorld()).updateMPMapData(mapPacket.itemData);
//		}
//
//		// these packets are probably sent when we first connect
//		if (packet.channel.equals(TwilightForestMod.ID) && packet.data[0] == CHANGE_DIM_ID) 
//		{
//			ByteBuffer bBuffer = ByteBuffer.wrap(packet.data);
//			
//			// discard first byte;
//			bBuffer.get();
//			
//			// get dimension ID
//			int dim = bBuffer.getInt();
//			//System.out.println("The connection packet said that the dim = " + dim);
//			TwilightForestMod.setDimensionID(dim);
//		}
//		
//		// these packets are sent by the tree of transformation effect
//		if (packet.channel.equals(TwilightForestMod.ID) && packet.data[0] == TRANSFORM_BIOME) 
//		{
//			ByteBuffer bBuffer = ByteBuffer.wrap(packet.data);
//			
//			// discard first byte;
//			bBuffer.get();
//			
//			// get coords
//			int x = bBuffer.getInt();
//			int z = bBuffer.getInt();
//			// biome?
//			byte biomeID = bBuffer.get();
//			
//			// change it
//			//FMLLog.info("Got tree of transformation effect packet for %d, %d, biomeid = %d", x, z, biomeID);
//			
//			EntityPlayer playerEntity = (EntityPlayer)player;
//			
//			Chunk chunkAt = playerEntity.worldObj.getChunkFromBlockCoords(x, z);
//
//			chunkAt.getBiomeArray()[(z & 15) << 4 | (x & 15)] = biomeID;
//			
//			playerEntity.worldObj.markBlockRangeForRenderUpdate(x, 0, z, x, 255, z);
//
//			
//		}
//
//		
//		// packets are fun!
//		// what other packets do we need?! :D
//	}


//    /**
//     * Called when a player logs into the server
//     *  SERVER SIDE
//     */
//	@Override
//	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) 
//	{
//	}
//
//	@Override
//	public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) 
//	{
//		//System.out.println("A player is logging in to the server.  We should send them which tf dimension we are using");
//		
//		ByteBuffer bBuffer = ByteBuffer.allocate(5);
//		bBuffer.put(CHANGE_DIM_ID);
//		bBuffer.putInt(TwilightForestMod.dimensionID);
//		
//		Packet250CustomPayload packet = new Packet250CustomPayload(TwilightForestMod.ID, bBuffer.array());
//		
//		manager.addToSendQueue(packet);
//		
//		return null;
//	}
//
//	@Override
//	public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
//		;
//	}
//
//	@Override
//	public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {
//		;		
//	}
//
//	@Override
//	public void connectionClosed(INetworkManager manager) {
//		;		
//	}
//
//	@Override
//	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
//		;		
//	}

}
