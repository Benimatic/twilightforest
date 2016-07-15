package twilightforest;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class TFSounds {

    public static final SoundEvent asd = ;

    public static void init() {

    }

    private static SoundEvent registerSound(String sound) {
        ResourceLocation name = new ResourceLocation(TwilightForestMod.ID, sound)
        SoundEvent evt = new SoundEvent(name);
        GameRegistry.register(evt, name);
        return evt;
    }

}
