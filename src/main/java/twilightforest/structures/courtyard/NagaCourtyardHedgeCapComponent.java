package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class NagaCourtyardHedgeCapComponent extends NagaCourtyardHedgeAbstractComponent {

    public NagaCourtyardHedgeCapComponent(StructureManager manager, CompoundTag nbt) {
        super(manager, NagaCourtyardPieces.TFNCCp, nbt, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end_big"));
    }

    @SuppressWarnings("WeakerAccess")
    public NagaCourtyardHedgeCapComponent(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(NagaCourtyardPieces.TFNCCp, feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end_big"));
    }
}