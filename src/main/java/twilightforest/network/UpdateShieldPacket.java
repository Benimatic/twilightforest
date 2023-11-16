package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.NetworkEvent;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.shield.IShieldCapability;

public class UpdateShieldPacket {

	private final int entityID;
	private final int temporaryShields;
	private final int permanentShields;

	public UpdateShieldPacket(int id, IShieldCapability cap) {
		this.entityID = id;
		this.temporaryShields = cap.temporaryShieldsLeft();
		this.permanentShields = cap.permanentShieldsLeft();
	}

	public UpdateShieldPacket(Entity entity, IShieldCapability cap) {
		this(entity.getId(), cap);
	}

	public UpdateShieldPacket(FriendlyByteBuf buf) {
		this.entityID = buf.readInt();
		this.temporaryShields = buf.readInt();
		this.permanentShields = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(this.entityID);
		buf.writeInt(this.temporaryShields);
		buf.writeInt(this.permanentShields);
	}

	public static class Handler {

		public static boolean onMessage(UpdateShieldPacket message, NetworkEvent.Context ctx) {
			ctx.enqueueWork(() -> {
				Entity entity = Minecraft.getInstance().level.getEntity(message.entityID);
				if (entity instanceof LivingEntity) {
					entity.getCapability(CapabilityList.SHIELDS).ifPresent(cap -> {
						cap.setShields(message.temporaryShields, true);
						cap.setShields(message.permanentShields, false);
					});
				}
			});

			ctx.setPacketHandled(true);
			return true;
		}
	}
}
