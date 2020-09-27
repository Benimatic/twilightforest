package twilightforest.structures.courtyard;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class ComponentNagaCourtyardWall extends ComponentNagaCourtyardWallAbstract {
    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardWall() {
        super(new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall"), new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_decayed"));
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardWall(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall"), new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_decayed"));
    }
}
