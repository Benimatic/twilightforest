package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

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

		public static boolean onMessage(ThrowPlayerPacket message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() ->
					Minecraft.getInstance().player.push(message.motionX, message.motionY, message.motionZ));
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
