package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class ComponentNagaCourtyardHedgeCorner extends ComponentNagaCourtyardHedgeAbstract {
    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardHedgeCorner(TemplateManager manager, CompoundNBT nbt) {
        super(NagaCourtyardPieces.TFNCCr, nbt, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_corner"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_corner_big"));
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardHedgeCorner(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_corner"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_corner_big"));
    }
}
