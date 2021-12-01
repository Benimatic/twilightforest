package twilightforest.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;
import twilightforest.inventory.UncraftingContainer;

import java.util.function.Supplier;

public class UncraftingGuiPacket {
    private final int type;

    public UncraftingGuiPacket(int type) {
        this.type = type;
    }

    public UncraftingGuiPacket(FriendlyByteBuf buf) {
        type = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(type);
    }

    public static class Handler {

        @SuppressWarnings("Convert2Lambda")
        public static boolean onMessage(UncraftingGuiPacket message, Supplier<NetworkEvent.Context> ctx) {
            ServerPlayer player = ctx.get().getSender();

            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    AbstractContainerMenu container = player.containerMenu;

                    if (container instanceof UncraftingContainer) {
                        UncraftingContainer uncrafting = (UncraftingContainer) container;

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
                }
            });

            return true;
        }
    }
}
