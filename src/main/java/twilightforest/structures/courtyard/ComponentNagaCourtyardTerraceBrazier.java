package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class ComponentNagaCourtyardTerraceBrazier extends ComponentNagaCourtyardTerraceAbstract {
    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardTerraceBrazier(TemplateManager manager, CompoundNBT nbt) {
        super(NagaCourtyardPieces.TFNCTr, nbt, new ResourceLocation(TwilightForestMod.ID, "courtyard/terrace_fire"));
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardTerraceBrazier(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(NagaCourtyardPieces.TFNCTr, feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/terrace_fire"));
    }
}
