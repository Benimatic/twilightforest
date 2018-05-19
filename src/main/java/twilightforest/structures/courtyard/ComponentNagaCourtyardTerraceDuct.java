package twilightforest.structures.courtyard;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class ComponentNagaCourtyardTerraceDuct extends ComponentNagaCourtyardTerraceAbstract {
    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardTerraceDuct() {
        super(new ResourceLocation(TwilightForestMod.ID, "courtyard/terrace_fire"));
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardTerraceDuct(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/terrace_fire"));
    }
}
