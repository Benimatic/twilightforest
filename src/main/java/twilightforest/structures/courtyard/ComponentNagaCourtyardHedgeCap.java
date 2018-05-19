package twilightforest.structures.courtyard;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class ComponentNagaCourtyardHedgeCap extends ComponentNagaCourtyardHedgeAbstract {
    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardHedgeCap() {
        super(new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end_big"));
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardHedgeCap(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end_big"));
    }
}