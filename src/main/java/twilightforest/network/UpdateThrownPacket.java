package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.thrown.YetiThrowCapability;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class UpdateThrownPacket {

	private final int entityID;
	private final boolean thrown;
	private int thrower = 0;

	public UpdateThrownPacket(int id, YetiThrowCapability cap) {
		this.entityID = id;
		this.thrown = cap.getThrown();
		if(cap.getThrower() != null) {
			this.thrower = cap.getThrower().getId();
		}
	}

	public UpdateThrownPacket(Entity entity, YetiThrowCapability cap) {
		this(entity.getId(), cap);
	}

	public UpdateThrownPacket(FriendlyByteBuf buf) {
		this.entityID = buf.readInt();
		this.thrown = buf.readBoolean();
		this.thrower = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(this.entityID);
		buf.writeBoolean(this.thrown);
		buf.writeInt(this.thrower);
	}

	public static class Handler {

		public static boolean onMessage(UpdateThrownPacket message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				Entity entity = Minecraft.getInstance().level.getEntity(message.entityID);
				if (entity instanceof LivingEntity) {
					entity.getCapability(CapabilityList.YETI_THROWN).ifPresent(cap -> {
						LivingEntity thrower = message.thrower != 0 ? (LivingEntity) Minecraft.getInstance().level.getEntity(message.thrower) : null;
						cap.setThrown(message.thrown, thrower);
					});
				}
			});

			return true;
		}
	}
}
