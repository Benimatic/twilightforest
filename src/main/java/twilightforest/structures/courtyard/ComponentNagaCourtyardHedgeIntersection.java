package twilightforest.structures.courtyard;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class ComponentNagaCourtyardHedgeIntersection extends ComponentNagaCourtyardHedgeAbstract {
    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardHedgeIntersection() {
        super(new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_intersection"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_intersection_big"));
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardHedgeIntersection(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_intersection"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_intersection_big"));
    }
}
