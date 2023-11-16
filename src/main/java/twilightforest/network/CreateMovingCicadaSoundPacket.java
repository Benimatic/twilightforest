package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.NetworkEvent;
import twilightforest.client.MovingCicadaSoundInstance;

public class CreateMovingCicadaSoundPacket {

	private final int entityID;

	public CreateMovingCicadaSoundPacket(int id) {
		this.entityID = id;
	}

	public CreateMovingCicadaSoundPacket(FriendlyByteBuf buf) {
		this.entityID = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(this.entityID);
	}

	public static class Handler {

		@SuppressWarnings("Convert2Lambda")
		public static boolean onMessage(CreateMovingCicadaSoundPacket message, NetworkEvent.Context ctx) {
			ctx.enqueueWork(new Runnable() {
				@Override
				public void run() {
					Entity entity = Minecraft.getInstance().level.getEntity(message.entityID);
					if (entity instanceof LivingEntity living) {
						Minecraft.getInstance().getSoundManager().queueTickingSound(new MovingCicadaSoundInstance(living));
					}
				}
			});

			ctx.setPacketHandled(true);
			return true;
		}
	}
}
