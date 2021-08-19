package twilightforest.world.components.structures.courtyard;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Rotation;
import twilightforest.world.registration.TFFeature;
import twilightforest.TwilightForestMod;

public class NagaCourtyardWallPadderComponent extends NagaCourtyardWallAbstractComponent {
    public NagaCourtyardWallPadderComponent(ServerLevel level, CompoundTag nbt) {
        super(level, NagaCourtyardPieces.TFNCWP, nbt, new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_padding"), new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_padding_decayed"));
    }

    public NagaCourtyardWallPadderComponent(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(NagaCourtyardPieces.TFNCWP, feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_padding"), new ResourceLocation(TwilightForestMod.ID, "courtyard/courtyard_wall_padding_decayed"));
    }
}
