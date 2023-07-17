package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import twilightforest.client.MissingAdvancementToast;

import java.util.function.Supplier;

public class MissingAdvancementToastPacket {
	private final Component title;
	private final ItemStack icon;

	public MissingAdvancementToastPacket(Component title, ItemStack icon) {
		this.title = title;
		this.icon = icon;
	}

	public MissingAdvancementToastPacket(FriendlyByteBuf buf) {
		this.title = buf.readComponent();
		this.icon = buf.readItem();
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeComponent(this.title);
		buf.writeItem(this.icon);
	}

	public static class Handler {
		@SuppressWarnings("Convert2Lambda")
		public static boolean onMessage(MissingAdvancementToastPacket packet, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(new Runnable() {
				@Override
				public void run() {
					Minecraft.getInstance().getToasts().addToast(new MissingAdvancementToast(packet.title, packet.icon));
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
