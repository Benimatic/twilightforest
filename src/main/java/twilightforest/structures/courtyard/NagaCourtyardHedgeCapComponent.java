package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class NagaCourtyardHedgeCapComponent extends NagaCourtyardHedgeAbstractComponent {

    public NagaCourtyardHedgeCapComponent(TemplateManager manager, CompoundNBT nbt) {
        super(manager, NagaCourtyardPieces.TFNCCp, nbt, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end_big"));
    }

    @SuppressWarnings("WeakerAccess")
    public NagaCourtyardHedgeCapComponent(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(NagaCourtyardPieces.TFNCCp, feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end_big"));
    }
}