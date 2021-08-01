package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class NagaCourtyardHedgeCapPillarComponent extends NagaCourtyardHedgeAbstractComponent {
    public NagaCourtyardHedgeCapPillarComponent(ServerLevel level, CompoundTag nbt) {
        super(level, NagaCourtyardPieces.TFNCCpP, nbt, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end_pillar"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end_pillar_big"));
    }

    public NagaCourtyardHedgeCapPillarComponent(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(NagaCourtyardPieces.TFNCCpP, feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end_pillar"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_end_pillar_big"));
    }
}