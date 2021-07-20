package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class NagaCourtyardWallCornerComponent extends NagaCourtyardWallAbstractComponent {
    public NagaCourtyardWallCornerComponent(TemplateManager manager, CompoundNBT nbt) {
        super(manager, NagaCourtyardPieces.TFNCWC, nbt, new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner"), new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner_decayed"));
    }

    public NagaCourtyardWallCornerComponent(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(NagaCourtyardPieces.TFNCWC, feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner"), new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner_decayed"));
    }
}
