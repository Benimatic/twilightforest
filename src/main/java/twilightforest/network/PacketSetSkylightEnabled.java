package twilightforest.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import twilightforest.world.TwilightForestDimension;

import java.util.function.Supplier;

public class PacketSetSkylightEnabled {

    private final boolean enabled;

    public PacketSetSkylightEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public PacketSetSkylightEnabled(PacketBuffer buf) {
        enabled = buf.readBoolean();
    }

    public void encode(PacketBuffer buf) {
        buf.writeBoolean(enabled);
    }

    public static class Handler {
        public static boolean onMessage(PacketSetSkylightEnabled message, Supplier<NetworkEvent.Context> ctx) {
            TwilightForestDimension.setSkylightEnabled(message.enabled);
            return true;
        }
    }
}
