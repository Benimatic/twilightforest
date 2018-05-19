package twilightforest.structures.courtyard;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class ComponentNagaCourtyardHedgeCapPillar extends ComponentNagaCourtyardHedgeAbstract {
    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardHedgeCapPillar() {
        super(new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end_pillar"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end_pillar_big"));
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardHedgeCapPillar(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end_pillar"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end_big_pillar"));
    }
}