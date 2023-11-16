package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.NetworkEvent;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.fan.FeatherFanCapabilityHandler;
import twilightforest.capabilities.fan.FeatherFanFallCapability;

public class UpdateFeatherFanFallPacket {
	private final int entityID;
	private final boolean falling;

	public UpdateFeatherFanFallPacket(int id, FeatherFanFallCapability cap) {
		this.entityID = id;
		this.falling = cap.getFalling();
	}

	public UpdateFeatherFanFallPacket(Entity entity, FeatherFanCapabilityHandler cap) {
		this(entity.getId(), cap);
	}

	public UpdateFeatherFanFallPacket(FriendlyByteBuf buf) {
		this.entityID = buf.readInt();
		this.falling = buf.readBoolean();
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(this.entityID);
		buf.writeBoolean(this.falling);
	}

	public static class Handler {

		public static boolean onMessage(UpdateFeatherFanFallPacket message, NetworkEvent.Context ctx) {
			ctx.enqueueWork(() -> {
				Entity entity = Minecraft.getInstance().level.getEntity(message.entityID);
				if (entity instanceof LivingEntity) {
					entity.getCapability(CapabilityList.FEATHER_FAN_FALLING).ifPresent(cap -> {
						cap.setFalling(message.falling);
					});
				}
			});

			ctx.setPacketHandled(true);
			return true;
		}
	}
}
