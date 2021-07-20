package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class NagaCourtyardWallComponent extends NagaCourtyardWallAbstractComponent {
    public NagaCourtyardWallComponent(TemplateManager manager, CompoundNBT nbt) {
        super(manager, NagaCourtyardPieces.TFNCWl, nbt, new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall"), new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_decayed"));
    }

    public NagaCourtyardWallComponent(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(NagaCourtyardPieces.TFNCWl, feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall"), new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_decayed"));
    }
}
