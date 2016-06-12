package twilightforest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.client.renderer.TFWeatherRenderer;
import twilightforest.entity.EntityTFProtectionBox;
import twilightforest.world.WorldProviderTwilightForest;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TFGenericPacketHandler {

	public static final byte CHANGE_DIM_ID = 1;
	public static final byte TRANSFORM_BIOME = 2;
	public static final byte THROW_PLAYER = 3;
	public static final byte AREA_PROTECTION = 4;
	public static final byte STRUCTURE_PROTECTION = 5;
	public static final byte STRUCTURE_PROTECTION_CLEAR = 6;
	public static final byte ENFORCED_PROGRESSION_STATUS = 7;
	public static final byte ANNIHILATE_BLOCK = 8;

	/**
	 * Packet!
	 */
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void incomingPacket(ClientCustomPacketEvent event)
	{
		ByteBuf buf = event.getPacket().payload();
		
		// read first byte to see what kind of packet
		int discriminatorByte = buf.readByte();
		
		if (discriminatorByte == TRANSFORM_BIOME) {
			processTransformBiomeData(buf);
		}
		if (discriminatorByte == THROW_PLAYER) {
			processThrowPlayerData(buf);
		}
		if (discriminatorByte == AREA_PROTECTION) {
			processAreaProtectionData(buf);
		}
		if (discriminatorByte == STRUCTURE_PROTECTION) {
			processStructureProtectionData(buf);
		}		
		if (discriminatorByte == STRUCTURE_PROTECTION_CLEAR) {
			processStructureProtectionClearData(buf);
		}		
		if (discriminatorByte == ENFORCED_PROGRESSION_STATUS) {
			processEnforcedProgressionStatusData(buf);
		}
		if (discriminatorByte == ANNIHILATE_BLOCK) {
			processAnnihilateBlock(buf);
		}
	}

	
	@SideOnly(Side.CLIENT)
	private void processTransformBiomeData(ByteBuf buf) {
		// get coords
		int x = buf.readInt();
		int z = buf.readInt();
		// biome?
		byte biomeID = buf.readByte();

		//FMLLog.info("Got tree of transformation effect packet for %d, %d, biomeid = %d", x, z, biomeID);

		
		// change it!
		// we're making the assumption here that we're only getting this packet if the player attached to this client is in the same world as the tree
		// thus, we don't need to send a dimension or anything
		World worldObj = Minecraft.getMinecraft().theWorld;
		
		Chunk chunkAt = worldObj.getChunkFromBlockCoords(x, z);

		chunkAt.getBiomeArray()[(z & 15) << 4 | (x & 15)] = biomeID;
		
		worldObj.markBlockRangeForRenderUpdate(x, 0, z, x, 255, z);
	}
	
	@SideOnly(Side.CLIENT)
	private void processThrowPlayerData(ByteBuf buf) {
		// get vector
		float motionX = buf.readFloat();
		float motionY = buf.readFloat();
		float motionZ = buf.readFloat();
		
		// add it to the player
		 Minecraft.getMinecraft().thePlayer.addVelocity(motionX, motionY, motionZ);
	}

	@SideOnly(Side.CLIENT)
	private void processAreaProtectionData(ByteBuf buf) {
		
		int minX = buf.readInt();
		int minY = buf.readInt();
		int minZ = buf.readInt();
		int maxX = buf.readInt();
		int maxY = buf.readInt();
		int maxZ = buf.readInt();
		
		int blockX = buf.readInt();
		int blockY = buf.readInt();
		int blockZ = buf.readInt();
		
		// make a box entity
		World worldObj = Minecraft.getMinecraft().theWorld;
		EntityTFProtectionBox box = new EntityTFProtectionBox(worldObj, minX, minY, minZ, maxX, maxY, maxZ);
		
		worldObj.addWeatherEffect(box);

		// particles from the block?
		for (int i = 0; i < 20; i++) {
	        
	        double d0 = worldObj.rand.nextGaussian() * 0.02D;
	        double d1 = worldObj.rand.nextGaussian() * 0.02D;
	        double d2 = worldObj.rand.nextGaussian() * 0.02D;

	        float dx = blockX + 0.5F + worldObj.rand.nextFloat() - worldObj.rand.nextFloat();
	        float dy = blockY + 0.5F + worldObj.rand.nextFloat() - worldObj.rand.nextFloat();
	        float dz = blockZ + 0.5F + worldObj.rand.nextFloat() - worldObj.rand.nextFloat();

            //worldObj.spawnParticle("mobSpell", blockX + 0.5F, blockY + 0.5F, blockZ + 0.5F, red, grn, blu);
			TwilightForestMod.proxy.spawnParticle(worldObj, "protection", dx, dy, dz, d0, d1, d2);

		}
	}

	/**
	 * Incoming structure protection box
	 */
	@SideOnly(Side.CLIENT)
	private void processStructureProtectionData(ByteBuf buf) {
		
		int minX = buf.readInt();
		int minY = buf.readInt();
		int minZ = buf.readInt();
		int maxX = buf.readInt();
		int maxY = buf.readInt();
		int maxZ = buf.readInt();
		
		StructureBoundingBox sbb = new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
		
		World worldObj = Minecraft.getMinecraft().theWorld;
		
		//System.out.println("Getting a new structure box " + sbb);
		
		// add weather box if needed
		if (worldObj.provider instanceof WorldProviderTwilightForest) {
			TFWeatherRenderer weatherRenderer = (TFWeatherRenderer) worldObj.provider.getWeatherRenderer();
			
			weatherRenderer.setProtectedBox(sbb);
		}

	}

	/**
	 * Incoming structure all clear
	 */
	@SideOnly(Side.CLIENT)
	private void processStructureProtectionClearData(ByteBuf buf) {

		World worldObj = Minecraft.getMinecraft().theWorld;
		
		//System.out.println("Getting structure all clear");
		
		// add weather box if needed
		if (worldObj.provider instanceof WorldProviderTwilightForest) {
			TFWeatherRenderer weatherRenderer = (TFWeatherRenderer) worldObj.provider.getWeatherRenderer();
			
			weatherRenderer.setProtectedBox(null);
		}

	}

	@SideOnly(Side.CLIENT)
	private void processEnforcedProgressionStatusData(ByteBuf buf) {
		
		World worldObj = Minecraft.getMinecraft().theWorld;

		boolean isEnforced = buf.readBoolean();
		
		worldObj.getGameRules().setOrCreateGameRule(TwilightForestMod.ENFORCED_PROGRESSION_RULE, Boolean.valueOf(isEnforced).toString());
	}
	
	
	@SideOnly(Side.CLIENT)
	private void processAnnihilateBlock(ByteBuf buf) {
		int blockX = buf.readInt();
		int blockY = buf.readInt();
		int blockZ = buf.readInt();
		
		World worldObj = Minecraft.getMinecraft().theWorld;

		TwilightForestMod.proxy.doBlockAnnihilateEffect(worldObj, blockX, blockY, blockZ);

	}






	/**
	 * Make a FMLProxyPacket that contains the data we need to change a biome in the world.
	 */
	public static FMLProxyPacket makeBiomeChangePacket(int x, int z, byte biomeID) {
		
        PacketBuffer payload = new PacketBuffer(Unpooled.buffer());
        
        payload.writeByte(TRANSFORM_BIOME); // discriminator byte
        payload.writeInt(x);
        payload.writeInt(z);
        payload.writeByte(biomeID);
        
		FMLProxyPacket pkt = new FMLProxyPacket(payload, TwilightForestMod.ID);

		return pkt;
	}
	
	
	/**
	 * Make a FMLProxyPacket that contains the data we need to add a velocity to the player
	 */
	public static FMLProxyPacket makeThrowPlayerPacket(float motionX, float motionY, float motionZ) {
		
        PacketBuffer payload = new PacketBuffer(Unpooled.buffer());
        
        payload.writeByte(THROW_PLAYER); // discriminator byte
        payload.writeFloat(motionX);
        payload.writeFloat(motionY);
        payload.writeFloat(motionZ);
        
		FMLProxyPacket pkt = new FMLProxyPacket(payload, TwilightForestMod.ID);

		return pkt;
	}

	/**
	 * Make a FMLProxyPacket that contains the data we need to display area protection data
	 */
	public static FMLProxyPacket makeAreaProtectionPacket(StructureBoundingBox sbb, int blockX, int blockY, int blockZ) {
		
        PacketBuffer payload = new PacketBuffer(Unpooled.buffer());
        
        payload.writeByte(AREA_PROTECTION); // discriminator byte
        
        payload.writeInt(sbb.minX);
        payload.writeInt(sbb.minY);
        payload.writeInt(sbb.minZ);
        payload.writeInt(sbb.maxX);
        payload.writeInt(sbb.maxY);
        payload.writeInt(sbb.maxZ);
        
        payload.writeInt(blockX);
        payload.writeInt(blockY);
        payload.writeInt(blockZ);

		FMLProxyPacket pkt = new FMLProxyPacket(payload, TwilightForestMod.ID);

		return pkt;
	}

	/**
	 * Make a FMLProxyPacket that contains the data we need to display structure protection data
	 */
	public static FMLProxyPacket makeStructureProtectionPacket(StructureBoundingBox sbb) {
		
        PacketBuffer payload = new PacketBuffer(Unpooled.buffer());
        
        payload.writeByte(STRUCTURE_PROTECTION); // discriminator byte
        
        payload.writeInt(sbb.minX);
        payload.writeInt(sbb.minY);
        payload.writeInt(sbb.minZ);
        payload.writeInt(sbb.maxX);
        payload.writeInt(sbb.maxY);
        payload.writeInt(sbb.maxZ);

		FMLProxyPacket pkt = new FMLProxyPacket(payload, TwilightForestMod.ID);

		return pkt;
	}

	/**
	 * Make a FMLProxyPacket that clears structure protection data on the client
	 */
	public static FMLProxyPacket makeStructureProtectionClearPacket() {
		
        PacketBuffer payload = new PacketBuffer(Unpooled.buffer());
        
        payload.writeByte(STRUCTURE_PROTECTION_CLEAR); // discriminator byte

		FMLProxyPacket pkt = new FMLProxyPacket(payload, TwilightForestMod.ID);

		return pkt;
	}
	
	/**
	 * Make a FMLProxyPacket that sets progression enforcement on the client
	 */
	public static FMLProxyPacket makeEnforcedProgressionStatusPacket(boolean isEnforced) {
		
        PacketBuffer payload = new PacketBuffer(Unpooled.buffer());
        
        payload.writeByte(ENFORCED_PROGRESSION_STATUS); // discriminator byte
        
        payload.writeBoolean(isEnforced);

		FMLProxyPacket pkt = new FMLProxyPacket(payload, TwilightForestMod.ID);

		return pkt;
	}

	

	/**
	 * Make a FMLProxyPacket that contains a block to display annihilation effects for
	 */
	public static FMLProxyPacket makeAnnihilateBlockPacket(int blockX, int blockY, int blockZ) {
		
        PacketBuffer payload = new PacketBuffer(Unpooled.buffer());
        
        payload.writeByte(ANNIHILATE_BLOCK); // discriminator byte

        payload.writeInt(blockX);
        payload.writeInt(blockY);
        payload.writeInt(blockZ);

		FMLProxyPacket pkt = new FMLProxyPacket(payload, TwilightForestMod.ID);

		return pkt;
	}
}
