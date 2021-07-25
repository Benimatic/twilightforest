package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class NagaCourtyardTerraceBrazierComponent extends NagaCourtyardTerraceAbstractComponent {
    public NagaCourtyardTerraceBrazierComponent(StructureManager manager, CompoundTag nbt) {
        super(manager, NagaCourtyardPieces.TFNCTr, nbt, new ResourceLocation(TwilightForestMod.ID, "courtyard/terrace_fire"));
    }

    public NagaCourtyardTerraceBrazierComponent(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(NagaCourtyardPieces.TFNCTr, feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/terrace_fire"));
    }
}
