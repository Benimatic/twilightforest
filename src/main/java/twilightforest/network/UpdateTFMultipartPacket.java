package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.network.NetworkEvent;
import twilightforest.entity.TFPart;

import java.util.ArrayList;
import java.util.List;

public class UpdateTFMultipartPacket {

	private int id;
	private Entity entity;
	private int len;
	private final List<PartDataHolder> data = new ArrayList<>();

	public UpdateTFMultipartPacket(FriendlyByteBuf buf) {
		this.id = buf.readInt();
		this.len = buf.readInt();
		for (int i = 0; i < len; i++) {
			if (buf.readBoolean()) {
				data.add(PartDataHolder.decode(buf));
			}
		}
	}

	public UpdateTFMultipartPacket(Entity entity) {
		this.entity = entity;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(this.entity.getId());
		PartEntity<?>[] parts = this.entity.getParts();
		// We assume the client and server part arrays are identical, else everything will crash and burn. Don't even bother handling it.
		if (parts != null) {
			buf.writeInt(parts.length);
			for (PartEntity<?> part : parts) {
				if (part instanceof TFPart<?> tfPart) {
					buf.writeBoolean(true);
					tfPart.writeData().encode(buf);
				} else {
					buf.writeBoolean(false);
				}
			}
		} else {
			buf.writeInt(0);
		}
	}

	public static class Handler {

		@SuppressWarnings("Convert2Lambda")
		public static boolean onMessage(UpdateTFMultipartPacket message, NetworkEvent.Context ctx) {
			ctx.enqueueWork(new Runnable() {
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
						int index = 0;
						for (PartEntity<?> part : parts) {
							if (part instanceof TFPart<?> tfPart) {
								tfPart.readData(message.data.get(index));
								index++;
							}
						}
					}
				}
			});
			ctx.setPacketHandled(true);
			return true;
		}
	}

	public record PartDataHolder(double x,
								 double y,
								 double z,
								 float yRot,
								 float xRot,
								 float width,
								 float height,
								 boolean fixed,
								 boolean dirty,
								 List<SynchedEntityData.DataValue<?>> data) {


		public void encode(FriendlyByteBuf buffer) {
			buffer.writeDouble(this.x);
			buffer.writeDouble(this.y);
			buffer.writeDouble(this.z);
			buffer.writeFloat(this.yRot);
			buffer.writeFloat(this.xRot);
			buffer.writeFloat(this.width);
			buffer.writeFloat(this.height);
			buffer.writeBoolean(this.fixed);
			buffer.writeBoolean(this.dirty);
			if (this.dirty) {
				for(SynchedEntityData.DataValue<?> datavalue : this.data) {
					datavalue.write(buffer);
				}

				buffer.writeByte(255);
			}
		}

		static PartDataHolder decode(FriendlyByteBuf buffer) {
			boolean dirty;
			return new PartDataHolder(
					buffer.readDouble(),
					buffer.readDouble(),
					buffer.readDouble(),
					buffer.readFloat(),
					buffer.readFloat(),
					buffer.readFloat(),
					buffer.readFloat(),
					buffer.readBoolean(),
					dirty = buffer.readBoolean(),
					dirty ? unpack(buffer) : null
			);
		}

		private static List<SynchedEntityData.DataValue<?>> unpack(FriendlyByteBuf buf) {
			List<SynchedEntityData.DataValue<?>> list = new ArrayList<>();

			int i;
			while((i = buf.readUnsignedByte()) != 255) {
				list.add(SynchedEntityData.DataValue.read(buf, i));
			}

			return list;
		}

	}
}
