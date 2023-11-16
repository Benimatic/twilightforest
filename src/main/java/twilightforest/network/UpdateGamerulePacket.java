package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;
import twilightforest.TwilightForestMod;

public class UpdateGamerulePacket {

	private final boolean enforced;

	public UpdateGamerulePacket(boolean enforced) {
		this.enforced = enforced;
	}

	public UpdateGamerulePacket(FriendlyByteBuf buf) {
		this.enforced = buf.readBoolean();
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeBoolean(this.enforced);
	}

	public static class Handler {
		public static boolean onMessage(UpdateGamerulePacket packet, NetworkEvent.Context ctx) {
			ctx.enqueueWork(() -> {
				if (Minecraft.getInstance().level != null)
					Minecraft.getInstance().level.getGameRules().getRule(TwilightForestMod.ENFORCED_PROGRESSION_RULE).set(packet.enforced, null);
			});

			ctx.setPacketHandled(true);
			return true;
		}
	}
}
