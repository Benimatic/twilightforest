package twilightforest.world.components.biomesources;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import twilightforest.util.Codecs;
import twilightforest.util.WorldUtil;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public record TwilightBiomePalette(Supplier<Biome> river, Climate.ParameterList<Supplier<Biome>> regularBiomes, List<Supplier<Biome>> specialBiomes, List<LandmarkGroup> landmarks, List<Supplier<Biome>> allBiomes) {
    public static final Codec<TwilightBiomePalette> CODEC = Util.make(() -> {
        Codec<TwilightBiomePalette> codec = RecordCodecBuilder.create(instance -> instance.group(
                Biome.CODEC.fieldOf("river").forGetter(TwilightBiomePalette::river),
                Codecs.CLIMATE_SYSTEM.fieldOf("common").forGetter(TwilightBiomePalette::regularBiomes),
                Biome.LIST_CODEC.fieldOf("special").forGetter(TwilightBiomePalette::specialBiomes),
                LandmarkGroup.CODEC.listOf().comapFlatMap(l -> Util.fixedSize(l, 4), Function.identity()).fieldOf("landmarks").forGetter(TwilightBiomePalette::landmarks)
        ).apply(instance, TwilightBiomePalette::create));

        // Very important
        return codec.orElseGet(TwilightBiomePalette::create);
    });

    public static record LandmarkGroup(Supplier<Biome> surround, Supplier<Biome> center) {
        // TODO Make this use linear snapping than this current binary snapping
        private static final Codec<LandmarkGroup> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Biome.CODEC.fieldOf("surround").forGetter(LandmarkGroup::surround),
                Biome.CODEC.fieldOf("center").forGetter(LandmarkGroup::center)
        ).apply(instance, LandmarkGroup::new));
    }

    private static TwilightBiomePalette create() {
        // FIXME populate correct biomes
        return create(WorldUtil::voidFallback, new Climate.ParameterList<>(Collections.emptyList()), Collections.emptyList(), Collections.emptyList());
    }

    private static TwilightBiomePalette create(Supplier<Biome> river, Climate.ParameterList<Supplier<Biome>> common, List<Supplier<Biome>> rare, List<LandmarkGroup> clusters) {
        ImmutableList.Builder<Supplier<Biome>> biomes = ImmutableList.builder();

        biomes.add(river).addAll(common.values().stream().map(Pair::getSecond).iterator()).addAll(rare);

        clusters.forEach(cluster -> biomes.add(cluster.surround).add(cluster.center));

        return new TwilightBiomePalette(river, common, rare, clusters, biomes.build());
    }
}
