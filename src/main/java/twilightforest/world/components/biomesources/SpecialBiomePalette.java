package twilightforest.world.components.biomesources;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.floats.Float2ObjectAVLTreeMap;
import net.minecraft.Util;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import twilightforest.util.Codecs;
import twilightforest.util.WorldUtil;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public record SpecialBiomePalette(Supplier<Biome> river, Climate.ParameterList<Supplier<Biome>> regularBiomes, List<Supplier<Biome>> specialBiomes, List<Float2ObjectAVLTreeMap<Supplier<Biome>>> landmarkGradients, List<Supplier<Biome>> allBiomes) {
    public static final Codec<SpecialBiomePalette> CODEC = Util.make(() -> {
        Codec<SpecialBiomePalette> codec = RecordCodecBuilder.create(instance -> instance.group(
                Biome.CODEC.fieldOf("river").forGetter(SpecialBiomePalette::river),
                Codecs.CLIMATE_SYSTEM.fieldOf("common").forGetter(SpecialBiomePalette::regularBiomes),
                Biome.LIST_CODEC.fieldOf("special").forGetter(SpecialBiomePalette::specialBiomes),
                Codecs.floatTreeCodec(Biome.CODEC).listOf().comapFlatMap(l -> Util.fixedSize(l, 4), Function.identity()).fieldOf("landmarks").forGetter(SpecialBiomePalette::landmarkGradients)
        ).apply(instance, SpecialBiomePalette::create));

        // Very important
        return codec.orElseGet(SpecialBiomePalette::create);
    });

    private static SpecialBiomePalette create() {
        // FIXME populate correct biomes
        return create(WorldUtil::voidFallback, new Climate.ParameterList<>(Collections.emptyList()), Collections.emptyList(), Collections.emptyList());
    }

    private static SpecialBiomePalette create(Supplier<Biome> river, Climate.ParameterList<Supplier<Biome>> common, List<Supplier<Biome>> rare, List<Float2ObjectAVLTreeMap<Supplier<Biome>>> clusters) {
        return new SpecialBiomePalette(river, common, rare, clusters, ImmutableList.<Supplier<Biome>>builder().add(river).addAll(common.values().stream().map(Pair::getSecond).iterator()).addAll(rare).addAll(clusters.stream().flatMap(tree -> tree.float2ObjectEntrySet().stream()).map(Map.Entry::getValue).iterator()).build());
    }

    @Nullable
    public Supplier<Biome> getNearestLandmark(float distanceRelative, int landmarkIndex, long permutation) {
        if (distanceRelative > 1) return null; // Outside the gradient

        return this.getNearestLandmark(landmarkIndex, permutation).get(distanceRelative);
    }

    private Float2ObjectAVLTreeMap<Supplier<Biome>> getNearestLandmark(int landmarkIndex, long permutation) {
        return this.landmarkGradients().get(BINARY_GRID_PERMUTATIONS[(int) (permutation % 24)][landmarkIndex]);
    }

    // Precomputed cache of every single possible permutation of a binary 2x2 grid
    private static final int[][] BINARY_GRID_PERMUTATIONS = new int[][] {
            new int[] { 0, 1, 2, 3 },
            new int[] { 0, 1, 3, 2 },
            new int[] { 0, 2, 1, 3 },
            new int[] { 0, 2, 3, 1 },
            new int[] { 0, 3, 1, 2 },
            new int[] { 0, 3, 2, 1 },
            new int[] { 1, 0, 2, 3 },
            new int[] { 1, 0, 3, 2 },
            new int[] { 1, 2, 0, 3 },
            new int[] { 1, 2, 3, 0 },
            new int[] { 1, 3, 0, 2 },
            new int[] { 1, 3, 2, 0 },
            new int[] { 2, 0, 1, 3 },
            new int[] { 2, 0, 3, 1 },
            new int[] { 2, 1, 0, 3 },
            new int[] { 2, 1, 3, 0 },
            new int[] { 2, 3, 0, 1 },
            new int[] { 2, 3, 1, 0 },
            new int[] { 3, 0, 1, 2 },
            new int[] { 3, 0, 2, 1 },
            new int[] { 3, 1, 0, 2 },
            new int[] { 3, 1, 2, 0 },
            new int[] { 3, 2, 0, 1 },
            new int[] { 3, 2, 1, 0 }
    };
}
