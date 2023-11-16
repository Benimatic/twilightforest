package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;
import twilightforest.TwilightForestMod;

public class EnforceProgressionStatusPacket {

	private final boolean enforce;

	public EnforceProgressionStatusPacket(FriendlyByteBuf buf) {
		this.enforce = buf.readBoolean();
	}

	public EnforceProgressionStatusPacket(boolean enforce) {
		this.enforce = enforce;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeBoolean(this.enforce);
	}

	public static class Handler {

		public static boolean onMessage(EnforceProgressionStatusPacket message, NetworkEvent.Context ctx) {
			ctx.enqueueWork(() ->
					Minecraft.getInstance().level.getGameRules().getRule(TwilightForestMod.ENFORCED_PROGRESSION_RULE).set(message.enforce, null));
			ctx.setPacketHandled(true);
			return true;
		}
	}
}
