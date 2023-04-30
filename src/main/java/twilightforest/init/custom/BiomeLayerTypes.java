package twilightforest.init.custom;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.TwilightForestMod;
import twilightforest.world.components.layer.*;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerType;
import twilightforest.world.components.layer.vanillalegacy.SmoothLayer;
import twilightforest.world.components.layer.vanillalegacy.ZoomLayer;

import java.util.function.Supplier;

public class BiomeLayerTypes {
    public static final ResourceKey<Registry<BiomeLayerType>> BIOME_LAYER_TYPE_KEY = ResourceKey.createRegistryKey(TwilightForestMod.namedRegistry("biome_layer_type"));
    public static final DeferredRegister<BiomeLayerType> BIOME_LAYER_TYPES = DeferredRegister.create(BIOME_LAYER_TYPE_KEY, TwilightForestMod.ID);
    public static final Supplier<IForgeRegistry<BiomeLayerType>> REGISTRY = BIOME_LAYER_TYPES.makeRegistry(() -> new RegistryBuilder<BiomeLayerType>().allowModification().disableSync());
    public static final Codec<BiomeLayerType> CODEC = ExtraCodecs.lazyInitializedCodec(() -> REGISTRY.get().getCodec());

    public static final RegistryObject<BiomeLayerType> RANDOM_BIOMES = registerType("random_biomes", () -> () -> RandomBiomeLayer.Factory.CODEC);
    public static final RegistryObject<BiomeLayerType> KEY_BIOMES = registerType("key_biomes", () -> () -> KeyBiomes.Factory.CODEC);
    public static final RegistryObject<BiomeLayerType> COMPANION_BIOMES = registerType("companion_biomes", () -> () -> CompanionBiomes.Factory.CODEC);
    public static final RegistryObject<BiomeLayerType> ZOOM = registerType("zoom", () -> () -> ZoomLayer.Factory.CODEC);
    public static final RegistryObject<BiomeLayerType> STABILIZE = registerType("stabilize", () -> () -> GenLayerTFBiomeStabilize.Factory.CODEC);
    public static final RegistryObject<BiomeLayerType> BORDER = registerType("border", () -> () -> BorderLayer.Factory.CODEC);
    public static final RegistryObject<BiomeLayerType> STREAM = registerType("partitioning", () -> () -> PartitioningBiomeLayer.Factory.CODEC);
    public static final RegistryObject<BiomeLayerType> SMOOTH = registerType("smooth", () -> () -> SmoothLayer.Factory.CODEC);
    public static final RegistryObject<BiomeLayerType> FILTERED = registerType("filtered", () -> () -> FilteredBiomeLayer.Factory.CODEC);
    public static final RegistryObject<BiomeLayerType> MEDIAN = registerType("median", () -> () -> GenLayerTFMedian.Factory.CODEC);

    private static RegistryObject<BiomeLayerType> registerType(String name, Supplier<BiomeLayerType> factory) {
        return BIOME_LAYER_TYPES.register(name, factory);
    }
}
