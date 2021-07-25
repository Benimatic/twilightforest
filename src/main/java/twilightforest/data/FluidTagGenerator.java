package twilightforest.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraftforge.common.data.ExistingFileHelper;
import twilightforest.TwilightForestMod;

import javax.annotation.Nullable;

public class FluidTagGenerator extends FluidTagsProvider {

    public static final Tag.Named<Fluid> FIRE_JET_FUEL = FluidTags.bind(TwilightForestMod.prefix("fire_jet_fuel").toString());
    public static final Tag.Named<Fluid> PORTAL_FLUID = FluidTags.bind(TwilightForestMod.prefix("portal_fluid").toString());

    public FluidTagGenerator(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, TwilightForestMod.ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(FIRE_JET_FUEL).addTag(FluidTags.LAVA);
        tag(PORTAL_FLUID).addTag(FluidTags.WATER);
    }
}
