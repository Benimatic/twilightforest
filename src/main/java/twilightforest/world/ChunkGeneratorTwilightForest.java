package twilightforest.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureFeatureManager;
import twilightforest.TFConfig;
import twilightforest.TFFeature;
import twilightforest.worldgen.biomes.BiomeKeys;
import twilightforest.block.TFBlocks;
import twilightforest.util.IntPair;

import java.util.function.Supplier;

// TODO: doc out all the vanilla copying
public class ChunkGeneratorTwilightForest extends ChunkGeneratorTwilightBase {
	public static final Codec<ChunkGeneratorTwilightForest> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			BiomeSource.CODEC.fieldOf("biome_source").forGetter(ChunkGenerator::getBiomeSource),
			Codec.LONG.fieldOf("seed").stable().forGetter((obj) -> obj.seed),
			NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(ChunkGeneratorTwilightForest::getDimensionSettings)
	).apply(instance, instance.stable(ChunkGeneratorTwilightForest::new)));

	private long seed;

	public ChunkGeneratorTwilightForest(BiomeSource provider, long seed, Supplier<NoiseGeneratorSettings> settings) {
		super(provider, seed, settings, true);
		this.seed = seed;
	}

	@Override
	protected Codec<? extends ChunkGenerator> codec() {
		return CODEC;
	}

	@Override
	public ChunkGenerator withSeed(long l) {
		return new ChunkGeneratorTwilightForest(this.biomeSource.withSeed(l), l, this.dimensionSettings);
	}

	private Supplier<NoiseGeneratorSettings> getDimensionSettings() {
		return this.dimensionSettings;
	}

	@Override
	public void createReferences(WorldGenLevel world, StructureFeatureManager manager, ChunkAccess chunk) {
		super.createReferences(world, manager, chunk);

		if(!(world instanceof WorldGenRegion))
			return;

		WorldGenRegion primer = (WorldGenRegion) world;

		// Dark Forest canopy uses the different scaled biomesForGeneration value already set in setBlocksInChunk
		addDarkForestCanopy(primer);

		// Can we use Beardifier instead
		deformTerrainForFeature(primer);
	}

	/**
	 * Adds dark forest canopy.  This version uses the "unzoomed" array of biomes used in land generation to determine how many of the nearby blocks are dark forest
	 */
	// Currently this is too sophisicated to be made into a SurfaceBuilder, it looks like
	private void addDarkForestCanopy(WorldGenRegion primer) {
		BlockPos blockpos = getPos(primer).getWorldPosition();
		int[] thicks = new int[5 * 5];
		boolean biomeFound = false;

		for (int z = 0; z < 5; z++) {
			for (int x = 0; x < 5; x++) {

				for (int bx = -1; bx <= 1; bx++) {
					for (int bz = -1; bz <= 1; bz++) {
						BlockPos p = blockpos.offset((x + bx) << 2, 0, (z + bz) << 2);
						Biome biome = biomeSource.getNoiseBiome(p.getX() >> 2, 0, p.getZ() >> 2);
						if (BiomeKeys.DARK_FOREST.location().equals(biome.getRegistryName()) || BiomeKeys.DARK_FOREST_CENTER.location().equals(biome.getRegistryName())) {
							thicks[x + z * 5]++;
							biomeFound = true;
						}
					}
				}
			}
		}

		if (!biomeFound) return;

		IntPair nearCenter = new IntPair();
		TFFeature nearFeature = TFFeature.getNearestFeature(getPos(primer).x, getPos(primer).z, primer, nearCenter);

		double d = 0.03125D;
		//depthBuffer = noiseGen4.generateNoiseOctaves(depthBuffer, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, d * 2D, d * 2D, d * 2D);

		for (int z = 0; z < 16; z++) {
			for (int x = 0; x < 16; x++) {

				int qx = x / 4;
				int qz = z / 4;

				float xweight = (x % 4) * 0.25F + 0.125F;
				float zweight = (z % 4) * 0.25F + 0.125F;

				float thickness = 0;

				thickness += thicks[qx + (qz) * 5] * (1F - xweight) * (1F - zweight);
				thickness += thicks[qx + 1 + (qz) * 5] * (xweight) * (1F - zweight);
				thickness += thicks[qx + (qz + 1) * 5] * (1F - xweight) * (zweight);
				thickness += thicks[qx + 1 + (qz + 1) * 5] * (xweight) * (zweight);

				thickness -= 4;

				//int thickness = thicks[qz + (qz) * 5];

				// make sure we're not too close to the tower
				if (nearFeature == TFFeature.DARK_TOWER) {

					int hx = nearCenter.x;
					int hz = nearCenter.z;

					int rx = x - hx;
					int rz = z - hz;
					int dist = (int) Math.sqrt(rx * rx + rz * rz);

					if (dist < 24) {
						thickness -= (24 - dist);
					}
				}

				if (thickness > 1) {
					// find the (current) top block
					int topLevel = -1;
					for (int y = 127; y >= 0; y--) {
						Block currentBlock = primer.getBlockState(withY(getPos(primer).getWorldPosition().offset(x, 0, z), y)).getBlock();
						if (currentBlock == Blocks.WATER) {
							// don't generate over water
							break;
						}
						if (currentBlock == Blocks.STONE) {
							topLevel = y;
							break;
						}
					}

					if (topLevel != -1) {
						// just use the same noise generator as the terrain uses for stones
						//int noise = Math.min(3, (int) (depthBuffer[z & 15 | (x & 15) << 4] / 1.25f));
						int noise = Math.min(3, (int) (surfaceNoise.getSurfaceNoiseValue((blockpos.getX() + x) * 0.0625D, (blockpos.getZ() + z) * 0.0625D, 0.0625D, x * 0.0625D) * 15F / 1.25F));

						// manipulate top and bottom
						int treeBottom = topLevel + 12 - (int) (thickness * 0.5F);
						int treeTop = treeBottom + (int) (thickness * 1.5F);

						treeBottom -= noise;

						BlockState darkLeaves = TFBlocks.dark_leaves.get().defaultBlockState();
						for (int y = treeBottom; y < treeTop; y++) {
							primer.setBlock(withY(getPos(primer).getWorldPosition().offset(x, 0, z), y), darkLeaves, 3);
						}
					}
				}
			}
		}
	}
}
