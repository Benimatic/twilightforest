package twilightforest.init.custom;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.DeferredRegister;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFBiomes;
import twilightforest.world.components.layer.*;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerFactory;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerType;
import twilightforest.world.components.layer.vanillalegacy.SmoothLayer;
import twilightforest.world.components.layer.vanillalegacy.ZoomLayer;


public class BiomeLayerStack {
    public static final ResourceKey<Registry<BiomeLayerFactory>> BIOME_STACK_KEY = ResourceKey.createRegistryKey(TwilightForestMod.namedRegistry("biome_layer_stack"));
    public static final DeferredRegister<BiomeLayerFactory> BIOME_LAYER_STACKS = DeferredRegister.create(BIOME_STACK_KEY, TwilightForestMod.ID);
    public static final Codec<BiomeLayerFactory> DISPATCH_CODEC = BiomeLayerTypes.CODEC.dispatch("layer_type", BiomeLayerFactory::getType, BiomeLayerType::getCodec);
    public static final Codec<Holder<BiomeLayerFactory>> HOLDER_CODEC = RegistryFileCodec.create(BiomeLayerStack.BIOME_STACK_KEY, BiomeLayerStack.DISPATCH_CODEC, true);

    public static final ResourceKey<BiomeLayerFactory> RANDOM_FOREST_BIOMES = registerKey("random_forest_biomes");
    public static final ResourceKey<BiomeLayerFactory> BIOMES_ALONG_STREAMS = registerKey("biomes_along_streams");

    public static ResourceKey<BiomeLayerFactory> registerKey(String name) {
        return ResourceKey.create(BIOME_STACK_KEY, TwilightForestMod.prefix(name));
    }

    public static void bootstrap(BootstapContext<BiomeLayerFactory> context) {
        BiomeLayerFactory biomes = new GenLayerTFBiomes.Factory(1L);
        biomes = new GenLayerTFKeyBiomes.Factory(1000L, Holder.direct(biomes));
        biomes = new GenLayerTFCompanionBiomes.Factory(1000L, Holder.direct(biomes));

        biomes = new ZoomLayer.Factory(1000L, false, Holder.direct(biomes));
        biomes = new ZoomLayer.Factory(1001L, false, Holder.direct(biomes));

        biomes = new GenLayerTFBiomeStabilize.Factory(700L, Holder.direct(biomes));

        biomes = new GenLayerTFThornBorder.Factory(500L, Holder.direct(biomes));

        biomes = new ZoomLayer.Factory(1002L, false, Holder.direct(biomes));
        biomes = new ZoomLayer.Factory(1003L, false, Holder.direct(biomes));
        biomes = new ZoomLayer.Factory(1004L, false, Holder.direct(biomes));
        biomes = new ZoomLayer.Factory(1005L, false, Holder.direct(biomes));

        Holder.Reference<BiomeLayerFactory> randomBiomes = context.register(RANDOM_FOREST_BIOMES, biomes);

        BiomeLayerFactory riverLayer = new GenLayerTFStream.Factory(1L, randomBiomes);
        riverLayer = new SmoothLayer.Factory(7000L, Holder.direct(riverLayer));

        context.register(BIOMES_ALONG_STREAMS, new FilteredBiomeLayer.Factory(100L, TFBiomes.STREAM, Holder.direct(riverLayer), randomBiomes));
    }
}
