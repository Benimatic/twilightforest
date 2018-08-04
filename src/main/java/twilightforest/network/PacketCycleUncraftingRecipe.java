package twilightforest.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import twilightforest.inventory.ContainerTFUncrafting;

public class PacketCycleUncraftingRecipe implements IMessage {
    private boolean cycleUp;

    public PacketCycleUncraftingRecipe(boolean cycleUp) {
        this.cycleUp = cycleUp;
    }

    public PacketCycleUncraftingRecipe() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        cycleUp = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(cycleUp);
    }

    public static class Handler implements IMessageHandler<PacketCycleUncraftingRecipe, IMessage> {
        public Handler () {

        }

        @SuppressWarnings("Convert2Lambda")
        @Override
        public IMessage onMessage(PacketCycleUncraftingRecipe message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

            player.getServerWorld().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    Container container = player.openContainer;

                    if (container instanceof ContainerTFUncrafting) {
                        ContainerTFUncrafting uncrafting = (ContainerTFUncrafting) container;

                        if (message.cycleUp) {
                            uncrafting.recipeInCycle++;
                        } else {
                            uncrafting.recipeInCycle--;
                        }

                        uncrafting.onCraftMatrixChanged(uncrafting.tinkerInput);
                    }
                }
            });

            return null;
        }
    }
}
