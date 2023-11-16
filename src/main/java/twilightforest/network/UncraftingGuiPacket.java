package twilightforest.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.NetworkEvent;
import twilightforest.inventory.UncraftingMenu;

public class UncraftingGuiPacket {
	private final int type;

	public UncraftingGuiPacket(int type) {
		this.type = type;
	}

	public UncraftingGuiPacket(FriendlyByteBuf buf) {
		this.type = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(this.type);
	}

	public static class Handler {

		public static boolean onMessage(UncraftingGuiPacket message, NetworkEvent.Context ctx) {
			ServerPlayer player = ctx.getSender();

			ctx.enqueueWork(() -> {
				AbstractContainerMenu container = player.containerMenu;

				if (container instanceof UncraftingMenu uncrafting) {
					switch (message.type) {
						case 0 -> uncrafting.unrecipeInCycle++;
						case 1 -> uncrafting.unrecipeInCycle--;
						case 2 -> uncrafting.ingredientsInCycle++;
						case 3 -> uncrafting.ingredientsInCycle--;
						case 4 -> uncrafting.recipeInCycle++;
						case 5 -> uncrafting.recipeInCycle--;
					}

					if (message.type < 4)
						uncrafting.slotsChanged(uncrafting.tinkerInput);

					if (message.type >= 4)
						uncrafting.slotsChanged(uncrafting.assemblyMatrix);
				}
			});

			ctx.setPacketHandled(true);
			return true;
		}
	}
}
