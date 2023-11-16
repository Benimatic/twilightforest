package twilightforest.network;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.neoforged.neoforge.client.DimensionSpecialEffectsManager;
import net.neoforged.neoforge.network.NetworkEvent;
import twilightforest.TwilightForestMod;
import twilightforest.client.TwilightForestRenderInfo;
import twilightforest.client.renderer.TFWeatherRenderer;

public class StructureProtectionPacket {

	private final BoundingBox sbb;

	public StructureProtectionPacket(BoundingBox sbb) {
		this.sbb = sbb;
	}

	public StructureProtectionPacket(FriendlyByteBuf buf) {
		this.sbb = new BoundingBox(
				buf.readInt(), buf.readInt(), buf.readInt(),
				buf.readInt(), buf.readInt(), buf.readInt()
		);
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(this.sbb.minX());
		buf.writeInt(this.sbb.minY());
		buf.writeInt(this.sbb.minZ());
		buf.writeInt(this.sbb.maxX());
		buf.writeInt(this.sbb.maxY());
		buf.writeInt(this.sbb.maxZ());
	}

	public static class Handler {
		public static boolean onMessage(StructureProtectionPacket message, NetworkEvent.Context ctx) {
			ctx.enqueueWork(() -> {
				DimensionSpecialEffects info = DimensionSpecialEffectsManager.getForType(TwilightForestMod.prefix("renderer"));

				// add weather box if needed
				if (info instanceof TwilightForestRenderInfo) {
					TFWeatherRenderer.setProtectedBox(message.sbb);
				}
			});

			ctx.setPacketHandled(true);
			return true;
		}
	}
}
