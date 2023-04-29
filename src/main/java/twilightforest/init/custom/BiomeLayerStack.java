package twilightforest.init.custom;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.DeferredRegister;
import twilightforest.TwilightForestMod;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerType;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerFactory;


public class BiomeLayerStack {
    public static final ResourceKey<Registry<BiomeLayerFactory>> BIOME_STACK_KEY = ResourceKey.createRegistryKey(TwilightForestMod.namedRegistry("biome_layer_stack"));
    public static final DeferredRegister<BiomeLayerFactory> BIOME_LAYER_STACKS = DeferredRegister.create(BIOME_STACK_KEY, TwilightForestMod.ID);
    public static final Codec<BiomeLayerFactory> DISPATCH_CODEC = BiomeLayerTypes.CODEC.dispatch("layer_type", BiomeLayerFactory::getType, BiomeLayerType::getCodec);
    public static final Codec<Holder<BiomeLayerFactory>> CODEC = RegistryFileCodec.create(BiomeLayerStack.BIOME_STACK_KEY, BiomeLayerStack.DISPATCH_CODEC, true);

    //public static final ResourceKey<BiomeLayerFactory> RANDOM_BIOMES = registerKey("random_biome_starter");
    //public static final ResourceKey<BiomeLayerFactory> RIVER_BORDERS = registerKey("river_borders");

    public static ResourceKey<BiomeLayerFactory> registerKey(String name) {
        return ResourceKey.create(BIOME_STACK_KEY, TwilightForestMod.prefix(name));
    }
}
