package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;

public class ThrowPlayerPacket {
	private final double motionX;
	private final double motionY;
	private final double motionZ;

	public ThrowPlayerPacket(double motionX, double motionY, double motionZ) {
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}

	public ThrowPlayerPacket(FriendlyByteBuf buf) {
		this.motionX = buf.readDouble();
		this.motionY = buf.readDouble();
		this.motionZ = buf.readDouble();
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeDouble(this.motionX);
		buf.writeDouble(this.motionY);
		buf.writeDouble(this.motionZ);
	}

	public static class Handler {

		public static boolean onMessage(ThrowPlayerPacket message, NetworkEvent.Context ctx) {
			ctx.enqueueWork(() ->
					Minecraft.getInstance().player.push(message.motionX, message.motionY, message.motionZ));
			ctx.setPacketHandled(true);
			return true;
		}
	}
}
