package twilightforest.world.components.biomesources;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.floats.Float2ObjectAVLTreeMap;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import twilightforest.util.Codecs;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public record SpecialBiomePalette(Holder<Biome> river, Climate.ParameterList<Holder<Biome>> regularBiomes, HolderSet<Biome> specialBiomes, List<Float2ObjectAVLTreeMap<Holder<Biome>>> landmarkGradients, List<Holder<Biome>> allBiomes) {
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
        return create(Holder.direct(OverworldBiomes.theVoid()), new Climate.ParameterList<>(Collections.emptyList()), HolderSet.direct(), Collections.emptyList());
    }

    private static SpecialBiomePalette create(Holder<Biome> river, Climate.ParameterList<Holder<Biome>> common, HolderSet<Biome> rare, List<Float2ObjectAVLTreeMap<Holder<Biome>>> clusters) {
        return new SpecialBiomePalette(river, common, rare, clusters, ImmutableList.<Holder<Biome>>builder().add(river).addAll(common.values().stream().map(Pair::getSecond).iterator()).addAll(rare).addAll(clusters.stream().flatMap(tree -> tree.float2ObjectEntrySet().stream()).map(Map.Entry::getValue).iterator()).build());
    }

    @Nullable
    public Holder<Biome> getNearestLandmark(float distanceRelative, int landmarkIndex, long permutation) {
        if (distanceRelative > 1) return null; // Outside the gradient

        return this.getNearestLandmark(landmarkIndex, permutation).get(distanceRelative);
    }

    private Float2ObjectAVLTreeMap<Holder<Biome>> getNearestLandmark(int landmarkIndex, long permutation) {
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
