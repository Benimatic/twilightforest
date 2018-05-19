package twilightforest.structures.courtyard;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class ComponentNagaCourtyardHedgePadder extends ComponentNagaCourtyardHedgeAbstract {
    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardHedgePadder() {
        super(new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_between"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_between_big"));
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardHedgePadder(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_between"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_between_big"));
    }
}
