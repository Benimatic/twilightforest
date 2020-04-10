package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class ComponentNagaCourtyardTerraceDuct extends ComponentNagaCourtyardTerraceAbstract {
    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardTerraceDuct(TemplateManager manager, CompoundNBT nbt) {
        super(NagaCourtyardPieces.TFNCDu, nbt, new ResourceLocation(TwilightForestMod.ID, "courtyard/terrace_duct"));
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardTerraceDuct(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(NagaCourtyardPieces.TFNCDu, feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/terrace_duct"));
    }
}
