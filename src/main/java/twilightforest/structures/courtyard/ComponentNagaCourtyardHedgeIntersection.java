package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class ComponentNagaCourtyardHedgeIntersection extends ComponentNagaCourtyardHedgeAbstract {
    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardHedgeIntersection(TemplateManager manager, CompoundNBT nbt) {
        super(NagaCourtyardPieces.TFNCIs, nbt, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_intersection"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_intersection_big"));
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardHedgeIntersection(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(NagaCourtyardPieces.TFNCIs, feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_intersection"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_intersection_big"));
    }
}
