package twilightforest.world.components.biomesources;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LandmarkBiomeSource extends BiomeSource {
    public static final Codec<LandmarkBiomeSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.LONG.fieldOf("seed").forGetter(o -> o.seed),
            SpecialBiomePalette.CODEC.fieldOf("palette").forGetter(o -> o.palette)
    ).apply(instance, LandmarkBiomeSource::new));

    // A Landmark group is made of major biomes (Swamps, Dark Forest, Snowy Forest + Glacier, Final Plateau)
    private static final int LANDMARK_POW_2 = 9;
    private static final int LANDMARK_SCALE = 2 << LANDMARK_POW_2;
    private static final int LANDMARK_HALF_SCALE = LANDMARK_SCALE >> 2;
    // Maps show a total of 4 (all) major landmark biome groups for progression
    private static final int MAP_POW_2 = LANDMARK_POW_2 + 1;
    private static final int MAP_SCALE = 2 << MAP_POW_2;

    private final long seed;
    private final SpecialBiomePalette palette;

    protected LandmarkBiomeSource(long seed, SpecialBiomePalette palette) {
        super(palette.allBiomes().stream().map(Supplier::get).collect(Collectors.toList()));

        this.seed = seed;
        this.palette = palette;
    }

    @Override
    protected Codec<LandmarkBiomeSource> codec() {
        return LandmarkBiomeSource.CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        return new LandmarkBiomeSource(seed, this.palette);
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z, Climate.Sampler climateSampler) {
        // TODO Fractal Voronoi coordinate filtering

        int mapX = x >> MAP_POW_2;
        int mapZ = z >> MAP_POW_2;
        long cellSeed = Mth.getSeed(mapX, (int) this.seed, mapZ);
        int landmarkIndex = landmarkIndex(x, z);

        // TODO Set up mild offsets for the centers of each landmark group

        // Test if we're in range of returning a biome from a landmark group
        float distance = (float) Mth.length(((x) % LANDMARK_SCALE) - LANDMARK_HALF_SCALE, ((z) % LANDMARK_SCALE) - LANDMARK_HALF_SCALE);
        var biome = this.palette.getNearestLandmark(distance * 0.66f, landmarkIndex, cellSeed);
        if (biome != null) return biome.get();

        // We're good to place something else now!

        // TODO Determine how we're going to place a special biome (Rainbow Grove, Dense Mushroom Forest, Spooky Forest)

        // Fallback to common biomes
        return this.getNoiseBiome(climateSampler.sample(x, y, z));
        // TODO Use nonzero negative offset value in the config's Climate.ParameterList to put Twilight Underground Biome
    }

    public Biome getNoiseBiome(Climate.TargetPoint climateSample) {
        return this.palette.regularBiomes().findValue(climateSample, this.palette.river()).get();
    }

    private static int landmarkIndex(int x, int z) {
        return ((z >> (LANDMARK_POW_2 - 1)) & 2) + ((x >> LANDMARK_POW_2) & 1);
    }
}
