package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class NagaCourtyardWallCornerComponent extends NagaCourtyardWallAbstractComponent {
    public NagaCourtyardWallCornerComponent(StructureManager manager, CompoundTag nbt) {
        super(manager, NagaCourtyardPieces.TFNCWC, nbt, new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner"), new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner_decayed"));
    }

    public NagaCourtyardWallCornerComponent(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(NagaCourtyardPieces.TFNCWC, feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner"), new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner_decayed"));
    }
}
