package twilightforest.network;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.client.DimensionSpecialEffectsManager;
import net.minecraftforge.network.NetworkEvent;
import twilightforest.TwilightForestMod;
import twilightforest.client.TwilightForestRenderInfo;
import twilightforest.client.renderer.TFWeatherRenderer;

import java.util.function.Supplier;

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
		public static boolean onMessage(StructureProtectionPacket message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				DimensionSpecialEffects info = DimensionSpecialEffectsManager.getForType(TwilightForestMod.prefix("renderer"));

				// add weather box if needed
				if (info instanceof TwilightForestRenderInfo) {
					TFWeatherRenderer.setProtectedBox(message.sbb);
				}
			});

			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
