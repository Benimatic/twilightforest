package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class NagaCourtyardHedgeTJunctionComponent extends NagaCourtyardHedgeAbstractComponent {
    public NagaCourtyardHedgeTJunctionComponent(StructureManager manager, CompoundTag nbt) {
        super(manager, NagaCourtyardPieces.TFNCT, nbt, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_t"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_t_big"));
    }

    public NagaCourtyardHedgeTJunctionComponent(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(NagaCourtyardPieces.TFNCT, feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_t"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_t_big"));
    }
}