package twilightforest.world.components.chunkgenerators;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public abstract class ChunkGeneratorWrapper extends ChunkGenerator {
    public final ChunkGenerator delegate;

    public ChunkGeneratorWrapper(ChunkGenerator delegate) {
        //FIXME 1st and 2nd parameters are definitely wrong
        super(null, Optional.empty(), delegate.getBiomeSource(), delegate.getBiomeSource(), delegate instanceof NoiseBasedChunkGenerator noiseGen ? noiseGen.seed : delegate.ringPlacementSeed);

        this.delegate = delegate;
    }

    @Override // Force reimplementation with seed override instead of delegating
    public abstract ChunkGenerator withSeed(long seed);

    @Override
    public CompletableFuture<ChunkAccess> createBiomes(Registry<Biome> biomes, Executor executor, Blender blender, StructureFeatureManager manager, ChunkAccess chunkAccess) {
        return this.delegate.createBiomes(biomes, executor, blender, manager, chunkAccess);
    }

    @Override
    public void applyCarvers(WorldGenRegion region, long seed, BiomeManager biomeManager, StructureFeatureManager manager, ChunkAccess chunkAccess, GenerationStep.Carving carving) {
        this.delegate.applyCarvers(region, seed, biomeManager, manager, chunkAccess, carving);
    }

    // Runtime will not care about public -> protected overrides, only compiletime will
    // I'd rather cut our losses than to attempt delegating this method because reflection would likely end up being
    // worse than accepting the potential problems of not delegating this method
    //@Override
    //public Aquifer createAquifer(ChunkAccess chunkAccess) {
    //    return this.delegate.createAquifer(chunkAccess);
    //}

    @Override
    @Nullable
    public Pair<BlockPos, Holder<ConfiguredStructureFeature<?, ?>>> findNearestMapFeature(ServerLevel level,  HolderSet<ConfiguredStructureFeature<?, ?>> structure, BlockPos pos, int searchRadius, boolean skipKnownStructures) {
        return this.delegate.findNearestMapFeature(level, structure, pos, searchRadius, skipKnownStructures);
    }

    @Override
    public void applyBiomeDecoration(WorldGenLevel level, ChunkAccess access, StructureFeatureManager structureManager) {
        this.delegate.applyBiomeDecoration(level, access, structureManager);
    }

    @Override
    public void buildSurface(WorldGenRegion level, StructureFeatureManager manager, ChunkAccess chunkAccess) {
        this.delegate.buildSurface(level, manager, chunkAccess);
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion region) {
        this.delegate.spawnOriginalMobs(region);
    }

    //FIXME probably gone now, verify
//    @Override
//    public StructureSettings getSettings() {
//        return this.delegate.getSettings();
//    }

    @Override
    public int getSpawnHeight(LevelHeightAccessor level) {
        return this.delegate.getSpawnHeight(level);
    }

    @Override
    public BiomeSource getBiomeSource() {
        return this.delegate.getBiomeSource();
    }

    @Override
    public int getGenDepth() {
        return this.delegate.getGenDepth();
    }

    @Override
    public WeightedRandomList<MobSpawnSettings.SpawnerData> getMobsAt(Holder<Biome> biome, StructureFeatureManager structureManager, MobCategory mobCategory, BlockPos pos) {
        return this.delegate.getMobsAt(biome, structureManager, mobCategory, pos);
    }

    @Override
    public void createStructures(RegistryAccess registry, StructureFeatureManager structureManager, ChunkAccess chunkAccess, StructureManager templateManager, long pSeed) {
        this.delegate.createStructures(registry, structureManager, chunkAccess, templateManager, pSeed);
    }

    @Override
    public void createReferences(WorldGenLevel level, StructureFeatureManager structureManager, ChunkAccess chunkAccess) {
        this.delegate.createReferences(level, structureManager, chunkAccess);
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, StructureFeatureManager structureManager, ChunkAccess chunkAccess) {
        return this.delegate.fillFromNoise(executor, blender, structureManager, chunkAccess);
    }

    @Override
    public int getSeaLevel() {
        return this.delegate.getSeaLevel();
    }

    @Override
    public int getMinY() {
        return this.delegate.getMinY();
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types heightMap, LevelHeightAccessor level) {
        return this.delegate.getBaseHeight(x, z, heightMap, level);
    }

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor level) {
        return this.delegate.getBaseColumn(x, z, level);
    }

    @Override
    public int getFirstFreeHeight(int x, int z, Heightmap.Types heightMapType, LevelHeightAccessor level) {
        return this.delegate.getFirstFreeHeight(x, z, heightMapType, level);
    }

    @Override
    public int getFirstOccupiedHeight(int x, int z, Heightmap.Types heightMapType, LevelHeightAccessor level) {
        return this.delegate.getFirstOccupiedHeight(x, z, heightMapType, level);
    }

    @Override
    public Climate.Sampler climateSampler() {
        return this.delegate.climateSampler();
    }
}
