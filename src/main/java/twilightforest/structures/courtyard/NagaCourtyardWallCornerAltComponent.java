package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class NagaCourtyardWallCornerAltComponent extends NagaCourtyardWallAbstractComponent {
    public NagaCourtyardWallCornerAltComponent(StructureManager manager, CompoundTag nbt) {
        super(manager, NagaCourtyardPieces.TFNCWA, nbt, new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner_inner"), new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner_inner_decayed"));
    }

    public NagaCourtyardWallCornerAltComponent(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(NagaCourtyardPieces.TFNCWA, feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner_inner"), new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_corner_inner_decayed"));
    }
}
