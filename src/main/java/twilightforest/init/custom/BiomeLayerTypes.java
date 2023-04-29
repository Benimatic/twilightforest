package twilightforest.init.custom;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.TwilightForestMod;
import twilightforest.util.WoodPalette;
import twilightforest.world.components.biomesources.TFBiomeProvider;
import twilightforest.world.components.layer.GenLayerTFBiomes;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerType;

import java.util.function.Supplier;

public class BiomeLayerTypes {
    public static final ResourceKey<Registry<BiomeLayerType>> BIOME_LAYER_TYPE_KEY = ResourceKey.createRegistryKey(TwilightForestMod.namedRegistry("biome_layer_type"));
    public static final DeferredRegister<BiomeLayerType> BIOME_LAYER_TYPES = DeferredRegister.create(BIOME_LAYER_TYPE_KEY, TwilightForestMod.ID);
    public static final Supplier<IForgeRegistry<BiomeLayerType>> REGISTRY = BIOME_LAYER_TYPES.makeRegistry(() -> new RegistryBuilder<BiomeLayerType>().allowModification().disableSync());
    public static final Codec<BiomeLayerType> CODEC = ExtraCodecs.lazyInitializedCodec(() -> REGISTRY.get().getCodec());

    public static final RegistryObject<BiomeLayerType> LEGACY = registerType("legacy", () -> () -> TFBiomeProvider.LEGACY_CODEC);
    public static final RegistryObject<BiomeLayerType> RANDOM_TWILIGHT_BIOME = registerType("random_twilight_biome", () -> () -> GenLayerTFBiomes.Layer.CODEC);

    private static RegistryObject<BiomeLayerType> registerType(String name, Supplier<BiomeLayerType> factory) {
        return BIOME_LAYER_TYPES.register(name, factory);
    }
}
