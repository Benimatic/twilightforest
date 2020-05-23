package twilightforest.world.surfacebuilders;

import net.minecraft.block.Blocks;
import net.minecraft.util.LazyValue;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;

public class TFSurfaceBuilders {

	public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = new DeferredRegister<>(ForgeRegistries.SURFACE_BUILDERS, TwilightForestMod.ID);

	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> DEFAULT_TF = SURFACE_BUILDERS.register("default_tf", () -> new TFDefaultSurfaceBuilder(SurfaceBuilderConfig::deserialize));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> HIGHLANDS = SURFACE_BUILDERS.register("highlands", () -> new TFHighlandsSurfaceBuilder(SurfaceBuilderConfig::deserialize));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> PLATEAU = SURFACE_BUILDERS.register("plateau", () -> new TFPlateauSurfaceBuilder(SurfaceBuilderConfig::deserialize));

	public static final LazyValue<SurfaceBuilderConfig> FINAL_PLATEAU = new LazyValue<>(() -> new SurfaceBuilderConfig(TFBlocks.deadrock_weathered.get().getDefaultState(), TFBlocks.deadrock_cracked.get().getDefaultState(), Blocks.GRAVEL.getDefaultState()));
}
