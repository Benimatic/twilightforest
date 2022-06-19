package twilightforest.data.tags;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.ExistingFileHelper;
import twilightforest.TwilightForestMod;

import org.jetbrains.annotations.Nullable;

public class FluidTagGenerator extends FluidTagsProvider {

    public static final TagKey<Fluid> FIRE_JET_FUEL = FluidTags.create(TwilightForestMod.prefix("fire_jet_fuel"));

    public FluidTagGenerator(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, TwilightForestMod.ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(FIRE_JET_FUEL).addTag(FluidTags.LAVA);
    }

    @Override
    public String getName() {
        return "Twilight Forest Fluid Tags";
    }
}
