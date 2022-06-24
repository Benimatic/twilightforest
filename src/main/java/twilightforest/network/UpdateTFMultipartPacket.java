package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.network.NetworkEvent;
import twilightforest.entity.TFPart;

import java.util.List;
import java.util.function.Supplier;

public class UpdateTFMultipartPacket {

	private int id;
	private FriendlyByteBuf buffer;
	private Entity entity;

	public UpdateTFMultipartPacket(FriendlyByteBuf buf) {
		this.id = buf.readInt();
		this.buffer = buf;
	}

	public UpdateTFMultipartPacket(Entity entity) {
		this.entity = entity;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(this.entity.getId());
		PartEntity<?>[] parts = this.entity.getParts();
		// We assume the client and server part arrays are identical, else everything will crash and burn. Don't even bother handling it.
		if (parts != null) {
			for (PartEntity<?> part : parts) {
				if (part instanceof TFPart<?> tfPart) {
					tfPart.writeData(buf);
					boolean dirty = tfPart.getEntityData().isDirty();
					buf.writeBoolean(dirty);
					if (dirty)
						SynchedEntityData.pack(tfPart.getEntityData().packDirty(), buf);
				}
			}
		}
	}

	public static class Handler {

		@SuppressWarnings("Convert2Lambda")
		public static boolean onMessage(UpdateTFMultipartPacket message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(new Runnable() {
				@Override
				public void run() {
					Level world = Minecraft.getInstance().level;
					if (world == null)
						return;
					Entity ent = world.getEntity(message.id);
					if (ent != null && ent.isMultipartEntity()) {
						PartEntity<?>[] parts = ent.getParts();
						if (parts == null)
							return;
						for (PartEntity<?> part : parts) {
							if (part instanceof TFPart<?> tfPart) {
								tfPart.readData(message.buffer);
								if (message.buffer.readBoolean()) {
									List<SynchedEntityData.DataItem<?>> data = SynchedEntityData.unpack(message.buffer);
									if (data != null)
										tfPart.getEntityData().assignValues(data);
								}
							}
						}
					}
				}
			});
			return true;
		}
	}
}
