package twilightforest.structures.courtyard;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class ComponentNagaCourtyardWallCorner extends ComponentNagaCourtyardWallAbstract {
    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardWallCorner() {
        super(new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner"), new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner_decayed"));
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardWallCorner(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner"), new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner_decayed"));
    }
}
