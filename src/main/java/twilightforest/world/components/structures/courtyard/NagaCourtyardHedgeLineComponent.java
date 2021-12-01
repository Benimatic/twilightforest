package twilightforest.world.components.structures.courtyard;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import twilightforest.world.registration.TFFeature;
import twilightforest.TwilightForestMod;

public class NagaCourtyardHedgeLineComponent extends NagaCourtyardHedgeAbstractComponent {
    public NagaCourtyardHedgeLineComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
        super(ctx, NagaCourtyardPieces.TFNCLn, nbt, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_line"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_line_big"));
    }

    public NagaCourtyardHedgeLineComponent(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(NagaCourtyardPieces.TFNCLn, feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_line"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_line_big"));
    }
}
