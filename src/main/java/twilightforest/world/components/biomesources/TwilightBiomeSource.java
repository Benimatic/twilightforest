package twilightforest.world.components.biomesources;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TwilightBiomeSource extends BiomeSource {
    public static final Codec<TwilightBiomeSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.LONG.fieldOf("seed").forGetter(o -> o.seed),
            TwilightBiomePalette.CODEC.fieldOf("biome_palette").forGetter(o -> o.config)
    ).apply(instance, TwilightBiomeSource::new));

    // A Landmark group is made of major biomes (Swamps, Dark Forest, Snowy Forest + Glacier, Final Plateau)
    private static final int LANDMARK_POW_2 = 9;
    private static final int LANDMARK_SCALE = 2 << LANDMARK_POW_2;
    // Maps show a total of 4 (all) major landmark biome groups for progression
    private static final int MAP_POW_2 = LANDMARK_POW_2 + 1;
    private static final int MAP_SCALE = 2 << MAP_POW_2;

    private final long seed;
    private final TwilightBiomePalette config;

    protected TwilightBiomeSource(long seed, TwilightBiomePalette config) {
        super(config.allBiomes().stream().map(Supplier::get).collect(Collectors.toList()));

        this.seed = seed;
        this.config = config;
    }

    @Override
    protected Codec<TwilightBiomeSource> codec() {
        return TwilightBiomeSource.CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        return new TwilightBiomeSource(seed, this.config);
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z, Climate.Sampler climateSampler) {
        // TODO Fractal Voronoi coordinate filtering

        // Unsure if this will be needed (Maybe not!)
        //if (!(climateSampler instanceof NoiseSampler sampler)) return this.getNoiseBiome(climateSampler.sample(x, y, z)); // Unable to get additional noise data, fail

        int mapX = x >> MAP_POW_2;
        int mapZ = z >> MAP_POW_2;
        long cellSeed = Mth.getSeed(mapX, (int) this.seed, mapZ);

        TwilightBiomePalette.LandmarkGroup group = this.getNearestLandmarkGroup(x, z, cellSeed);

        // TODO Set up mild offsets for the centers of each landmark group

        // TODO Test if we're in range of returning a biome from the landmark group or if we're good to place something else

        // TODO Determine how we're going to place a special biome (Rainbow Grove, Dense Mushroom Forest, Spooky Forest)

        // Fallback for common biomes
        return this.getNoiseBiome(climateSampler.sample(x, y, z));
        // TODO Use nonzero negative offset value in the config's Climate.ParameterList to put Twilight Underground Biome
    }

    public Biome getNoiseBiome(Climate.TargetPoint climateSample) {
        return this.config.regularBiomes().findValue(climateSample, this.config.river()).get();
    }

    private TwilightBiomePalette.LandmarkGroup getNearestLandmarkGroup(int x, int z, long permutation) {
        return this.config.landmarks().get(BINARY_GRID_PERMUTATIONS[(int) (permutation % 24)][((z >> (LANDMARK_POW_2 - 1)) & 2) + ((x >> LANDMARK_POW_2) & 1)]);
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
