package twilightforest.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class SetSkylightEnabledPacket {

    private final boolean enabled;

    public SetSkylightEnabledPacket(boolean enabled) {
        this.enabled = enabled;
    }

    public SetSkylightEnabledPacket(FriendlyByteBuf buf) {
        enabled = buf.readBoolean();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(enabled);
    }

    public static class Handler {
        public static boolean onMessage(SetSkylightEnabledPacket message, Supplier<NetworkEvent.Context> ctx) {
            // FIXME UNUSED AND CLEAR ALL
            // TwilightForestDimension.setSkylightEnabled(message.enabled);
            return true;
        }
    }
}
