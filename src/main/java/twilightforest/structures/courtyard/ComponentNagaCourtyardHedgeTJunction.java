package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;

public class ComponentNagaCourtyardHedgeTJunction extends ComponentNagaCourtyardHedgeAbstract {
    @SuppressWarnings({"WeakerAccess", "unused"})
    public ComponentNagaCourtyardHedgeTJunction(TemplateManager manager, CompoundNBT nbt) {
        super(NagaCourtyardPieces.TFNCT, nbt, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_t"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_t_big"));
    }

    @SuppressWarnings("WeakerAccess")
    public ComponentNagaCourtyardHedgeTJunction(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_t"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_t_big"));
    }
}