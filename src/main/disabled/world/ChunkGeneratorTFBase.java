package twilightforest.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityClassification;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraftforge.event.ForgeEventFactory;
import twilightforest.TFFeature;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.block.TFBlocks;
import twilightforest.util.IntPair;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

// TODO: doc out all the vanilla copying
public abstract class ChunkGeneratorTFBase extends NoiseChunkGenerator<TFWorld> {

	protected final Random rand;

	private final OctavesNoiseGenerator minLimitPerlinNoise;
	private final OctavesNoiseGenerator maxLimitPerlinNoise;
	private final OctavesNoiseGenerator mainPerlinNoise;
	private final PerlinNoiseGenerator surfaceNoise;
	private final OctavesNoiseGenerator depthNoise;

	protected final World world;
	protected final WorldType terrainType;

	private double[] mainNoiseRegion;
	private double[] minLimitRegion;
	private double[] maxLimitRegion;
	private double[] depthRegion;

	protected double[] depthBuffer = new double[256];
	protected Biome[] biomesForGeneration;

	private final double[] heightMap;
	private final float[] biomeWeights;

	protected final MapGenTFHollowTree hollowTreeGenerator = new MapGenTFHollowTree();
	protected final Map<TFFeature, MapGenTFMajorFeature> featureGenerators = new EnumMap<>(TFFeature.class);
	protected final MapGenTFMajorFeature nothingGenerator = new MapGenTFMajorFeature();

	private final boolean shouldGenerateBedrock;

	protected static long getSeed(int x, int z) {
		return x * 0x4f9939f508L + z * 0x1ef1565bd5L;
	}

	// same as ChunkPrimer
	protected static int getIndex(int x, int y, int z) {
		return x << 12 | z << 8 | y;
	}

	public ChunkGeneratorTFBase(World world, long seed, boolean enableFeatures, boolean shouldGenerateBedrock) {

		this.world = world;
		this.terrainType = world.getWorldInfo().getGenerator();
		this.rand = new Random(seed);
		this.shouldGenerateBedrock = shouldGenerateBedrock;

		this.minLimitPerlinNoise = new OctavesNoiseGenerator(new SharedSeedRandom(seed), 15, 0);
		this.maxLimitPerlinNoise = new OctavesNoiseGenerator(new SharedSeedRandom(seed), 15, 0);
		this.mainPerlinNoise = new OctavesNoiseGenerator(new SharedSeedRandom(seed), 7, 0);
		this.surfaceNoise = new PerlinNoiseGenerator(new SharedSeedRandom(seed), 3, 0);
		this.depthNoise = new OctavesNoiseGenerator(new SharedSeedRandom(seed), 15, 0);

		this.heightMap = new double[825];
		this.biomeWeights = new float[25];

		for (int j = -2; j <= 2; ++j) {
			for (int k = -2; k <= 2; ++k) {
				float f = 10.0F / MathHelper.sqrt((float) (j * j + k * k) + 0.2F);
				this.biomeWeights[j + 2 + (k + 2) * 5] = f;
			}
		}

		for (TFFeature feature : TFFeature.values()) {
			MapGenTFMajorFeature generator = feature.createFeatureGenerator();
			if (generator != null) {
				featureGenerators.put(feature, generator);
			}
		}
	}

	protected final void generateFeatures(int x, int z, ChunkPrimer primer) {
		for (MapGenTFMajorFeature generator : featureGenerators.values()) {
			generator.generate(world, x, z, primer);
		}
	}

	protected final Chunk makeChunk(int x, int z, ChunkPrimer primer) {

		Chunk chunk = new Chunk(world, x, z);

		fillChunk(chunk, primer);

		// load in biomes, to prevent striping?!
		byte[] chunkBiomes = chunk.getBiomeArray();
		for (int i = 0; i < chunkBiomes.length; ++i) {
			chunkBiomes[i] = (byte) Biome.getIdForBiome(this.biomesForGeneration[i]);
		}

		chunk.generateSkylightMap();

		return chunk;
	}

	// [VanillaCopy] Extended Chunk constructor, material check replaced with block check
	private void fillChunk(Chunk chunk, ChunkPrimer primer) {

		int i = 256;
		boolean flag = world.dimension.hasSkyLight();
		ExtendedBlockStorage[] storageArrays = chunk.getBlockStorageArray();

		for (int j = 0; j < 16; ++j) {
			for (int k = 0; k < 16; ++k) {
				for (int l = 0; l < 256; ++l) {

					BlockState iblockstate = primer.getBlockState(j, l, k);

					if (iblockstate.getBlock() != Blocks.AIR) {

						int i1 = l >> 4;

						if (storageArrays[i1] == Chunk.NULL_BLOCK_STORAGE) {
							storageArrays[i1] = new ExtendedBlockStorage(i1 << 4, flag);
						}

						storageArrays[i1].set(j, l & 15, k, iblockstate);
					}
				}
			}
		}
	}

	// note: ChunkPrimer changed to a BitSet marking 'solid' blocks
	// this allows for some post-processing before populating the primer
	protected final void setBlocksInChunk(int x, int z, ChunkBitArray data) {

		byte seaLevel = 63;
		this.biomesForGeneration = this.world.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
		this.generateHeightmap(x * 4, 0, z * 4);

		for (int k = 0; k < 4; ++k) {
			int l = k * 5;
			int i1 = (k + 1) * 5;

			for (int j1 = 0; j1 < 4; ++j1) {
				int k1 = (l + j1) * 33;
				int l1 = (l + j1 + 1) * 33;
				int i2 = (i1 + j1) * 33;
				int j2 = (i1 + j1 + 1) * 33;

				for (int k2 = 0; k2 < 32; ++k2) {
					double d0 = 0.125D;
					double d1 = this.heightMap[k1 + k2];
					double d2 = this.heightMap[l1 + k2];
					double d3 = this.heightMap[i2 + k2];
					double d4 = this.heightMap[j2 + k2];
					double d5 = (this.heightMap[k1 + k2 + 1] - d1) * d0;
					double d6 = (this.heightMap[l1 + k2 + 1] - d2) * d0;
					double d7 = (this.heightMap[i2 + k2 + 1] - d3) * d0;
					double d8 = (this.heightMap[j2 + k2 + 1] - d4) * d0;

					for (int l2 = 0; l2 < 8; ++l2) {
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int i3 = 0; i3 < 4; ++i3) {
							double d14 = 0.25D;
							double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;

							for (int k3 = 0; k3 < 4; ++k3) {
								if ((d15 += d16) > 0.0D) {
									// stone here
									data.set(getIndex(k * 4 + i3, k2 * 8 + l2, j1 * 4 + k3));
								} /* else if (k2 * 8 + l2 < seaLevel) */ {
									// water below sea level left until later
								}
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}

	private void generateHeightmap(int x, int zero, int z) {

		this.depthRegion = this.depthNoise.generateNoiseOctaves(this.depthRegion, x, z, 5, 5, 200.0D, 200.0D, 0.5D);
		this.mainNoiseRegion = this.mainPerlinNoise.generateNoiseOctaves(this.mainNoiseRegion, x, zero, z, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
		this.minLimitRegion = this.minLimitPerlinNoise.generateNoiseOctaves(this.minLimitRegion, x, zero, z, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		this.maxLimitRegion = this.maxLimitPerlinNoise.generateNoiseOctaves(this.maxLimitRegion, x, zero, z, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		int terrainIndex = 0;
		int noiseIndex = 0;

		for (int ax = 0; ax < 5; ++ax) {
			for (int az = 0; az < 5; ++az) {
				float totalVariation = 0.0F;
				float totalHeight = 0.0F;
				float totalFactor = 0.0F;
				byte two = 2;
				Biome biome = this.biomesForGeneration[ax + 2 + (az + 2) * 10];

				for (int ox = -two; ox <= two; ++ox) {
					for (int oz = -two; oz <= two; ++oz) {
						Biome biome1 = this.biomesForGeneration[ax + ox + 2 + (az + oz + 2) * 10];
						float rootHeight = biome1.getBaseHeight();
						float heightVariation = biome1.getHeightVariation();

						if (this.terrainType == WorldType.AMPLIFIED && rootHeight > 0.0F) {
							rootHeight = 1.0F + rootHeight * 2.0F;
							heightVariation = 1.0F + heightVariation * 4.0F;
						}

						float heightFactor = this.biomeWeights[ox + 2 + (oz + 2) * 5] / (rootHeight + 2.0F);

						if (biome1.getBaseHeight() > biome.getBaseHeight()) {
							heightFactor /= 2.0F;
						}

						totalVariation += heightVariation * heightFactor;
						totalHeight += rootHeight * heightFactor;
						totalFactor += heightFactor;
					}
				}

				totalVariation /= totalFactor;
				totalHeight /= totalFactor;
				totalVariation = totalVariation * 0.9F + 0.1F;
				totalHeight = (totalHeight * 4.0F - 1.0F) / 8.0F;
				double terrainNoise = this.depthRegion[noiseIndex] / 8000.0D;

				if (terrainNoise < 0.0D) {
					terrainNoise = -terrainNoise * 0.3D;
				}

				terrainNoise = terrainNoise * 3.0D - 2.0D;

				if (terrainNoise < 0.0D) {
					terrainNoise /= 2.0D;

					if (terrainNoise < -1.0D) {
						terrainNoise = -1.0D;
					}

					terrainNoise /= 1.4D;
					terrainNoise /= 2.0D;
				} else {
					if (terrainNoise > 1.0D) {
						terrainNoise = 1.0D;
					}

					terrainNoise /= 8.0D;
				}

				++noiseIndex;
				double heightCalc = (double) totalHeight;
				double variationCalc = (double) totalVariation;
				heightCalc += terrainNoise * 0.2D;
				heightCalc = heightCalc * 8.5D / 8.0D;
				double d5 = 8.5D + heightCalc * 4.0D;

				for (int ay = 0; ay < 33; ++ay) {
					double d6 = ((double) ay - d5) * 12.0D * 128.0D / 256.0D / variationCalc;

					if (d6 < 0.0D) {
						d6 *= 4.0D;
					}

					double d7 = this.minLimitRegion[terrainIndex] / 512.0D;
					double d8 = this.maxLimitRegion[terrainIndex] / 512.0D;
					double d9 = (this.mainNoiseRegion[terrainIndex] / 10.0D + 1.0D) / 2.0D;
					double terrainCalc = MathHelper.clampedLerp(d7, d8, d9) - d6;

					if (ay > 29) {
						double d11 = (double) ((float) (ay - 29) / 3.0F);
						terrainCalc = terrainCalc * (1.0D - d11) + -10.0D * d11;
					}

					this.heightMap[terrainIndex] = terrainCalc;
					++terrainIndex;
				}
			}
		}
	}

	/**
	 * Crush the terrain to half the height
	 */
	protected final void squishTerrain(ChunkBitArray data) {
		int squishHeight = TFWorld.MAXHEIGHT / 2;
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < squishHeight; y++) {
					data.set(getIndex(x, y, z), data.get(getIndex(x, y * 2 + 1, z)));
				}
				for (int y = squishHeight; y < TFWorld.CHUNKHEIGHT; y++) {
					data.clear(getIndex(x, y, z));
				}
			}
		}
	}

	protected abstract void initPrimer(ChunkPrimer primer, ChunkBitArray data);

	// [VanillaCopy] Exact, ChunkGeneratorOverworld.replaceBiomeBlocks
	public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {

		if (!ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, primer, this.world)) return;
		double d0 = 0.03125D;
		this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, (double)(x * 16), (double)(z * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);

		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				Biome biome = biomesIn[j + i * 16];
				biome.genTerrainBlocks(this.world, this.rand, primer, x * 16 + i, z * 16 + j, this.depthBuffer[j + i * 16]);
			}
		}
	}

	protected final void deformTerrainForFeature(int cx, int cz, ChunkPrimer primer) {

		IntPair nearCenter = new IntPair();
		TFFeature nearFeature = TFFeature.getNearestFeature(cx, cz, world, nearCenter);

		if (!nearFeature.isTerrainAltered) {
			return;
		}

		int hx = nearCenter.x;
		int hz = nearCenter.z;

		if (nearFeature == TFFeature.TROLL_CAVE) {
			// troll cloud, more like
			deformTerrainForTrollCloud2(primer, nearFeature, cx, cz, hx, hz);
		}

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {

				int dx = x - hx;
				int dz = z - hz;

				if (nearFeature == TFFeature.SMALL_HILL || nearFeature == TFFeature.MEDIUM_HILL || nearFeature == TFFeature.LARGE_HILL || nearFeature == TFFeature.HYDRA_LAIR) {
					// hollow hills
					int hdiam = ((nearFeature.size * 2 + 1) * 16);
					int dist = (int) Math.sqrt(dx * dx + dz * dz);
					int hheight = (int) (Math.cos((float) dist / (float) hdiam * Math.PI) * ((float) hdiam / 3F));

					raiseHills(primer, nearFeature, hdiam, x, z, dx, dz, hheight);

				} else if (nearFeature == TFFeature.HEDGE_MAZE || nearFeature == TFFeature.NAGA_COURTYARD || nearFeature == TFFeature.QUEST_GROVE) {
					// hedge mazes, naga arena
					flattenTerrainForFeature(primer, nearFeature, x, z, dx, dz);

				} else if (nearFeature == TFFeature.YETI_CAVE) {
					// yeti lairs are square
					deformTerrainForYetiLair(primer, nearFeature, x, z, dx, dz);

				} else if (nearFeature == TFFeature.TROLL_CAVE) {
					deformTerrainForTrollCaves(primer, nearFeature, x, z, dx, dz);
				}
				//else if (nearFeature != TFFeature.NOTHING) {
				//	// hedge mazes, naga arena
				//	flattenTerrainForFeature(primer, nearFeature, x, z, dx, dz);
				//}
			}
		}

		// done!
	}

	/**
	 * Raises up and hollows out the hollow hills.
	 */
	private void raiseHills(ChunkPrimer primer, TFFeature nearFeature, int hdiam, int x, int z, int dx, int dz, int hillHeight) {

		int oldGround = -1;
		int newGround = -1;
		boolean foundGroundLevel = false;

		// raise the hill
		for (int y = TFWorld.SEALEVEL; y < TFWorld.CHUNKHEIGHT; y++) {
			Block currentTerrain = primer.getBlockState(new BlockPos(x, y, z)).getBlock();
			if (currentTerrain != Blocks.STONE) {
				// we found the top of the stone layer
				oldGround = y;
				newGround = y + hillHeight;
				foundGroundLevel = true;
				break;
			}
		}

		if (foundGroundLevel) {
			for (int y = oldGround; y <= newGround; y++) {
				primer.setBlockState(new BlockPos(x, y, z), Blocks.STONE.getDefaultState(), false);
			}
		}

		// add the hollow part. Also turn water into stone below that
		int hollow = hillHeight - 4 - nearFeature.size;

		// hydra lair has a piece missing
		if (nearFeature == TFFeature.HYDRA_LAIR) {
			int mx = dx + 16;
			int mz = dz + 16;
			int mdist = (int) Math.sqrt(mx * mx + mz * mz);
			int mheight = (int) (Math.cos(mdist / (hdiam / 1.5) * Math.PI) * (hdiam / 1.5));

			hollow = Math.max(mheight - 4, hollow);
		}

		if (hollow < 0) {
			hollow = 0;
		}

		// hollow out the hollow parts
		int hollowFloor = TFWorld.SEALEVEL - 3 - (hollow / 8);
		if (nearFeature == TFFeature.HYDRA_LAIR) {
			// different floor
			hollowFloor = TFWorld.SEALEVEL;
		}

		if (hillHeight > 0) {
			// put a base on hills that go over open space or water
			for (int y = 0; y < TFWorld.SEALEVEL; y++) {
				if (primer.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.STONE) {
					primer.setBlockState(new BlockPos(x, y, z), Blocks.STONE.getDefaultState(), false);
				}
			}
		}

		for (int y = hollowFloor + 1; y < hollowFloor + hollow; y++) {
			primer.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), false);
		}
	}

	private void flattenTerrainForFeature(ChunkPrimer primer, TFFeature nearFeature, int x, int z, int dx, int dz) {

		float squishFactor = 0f;
		int mazeHeight = TFWorld.SEALEVEL + 1;
		final int FEATURE_BOUNDARY = (nearFeature.size * 2 + 1) * 8 - 8;

		if (dx <= -FEATURE_BOUNDARY) {
			squishFactor = (-dx - FEATURE_BOUNDARY) / 8.0f;
		} else if (dx >= FEATURE_BOUNDARY) {
			squishFactor = (dx - FEATURE_BOUNDARY) / 8.0f;
		}

		if (dz <= -FEATURE_BOUNDARY) {
			squishFactor = Math.max(squishFactor, (-dz - FEATURE_BOUNDARY) / 8.0f);
		} else if (dz >= FEATURE_BOUNDARY) {
			squishFactor = Math.max(squishFactor, (dz - FEATURE_BOUNDARY) / 8.0f);
		}

		if (squishFactor > 0f) {
			// blend the old terrain height to arena height
			for (int y = 0; y <= 127; y++) {
				Block currentTerrain = primer.getBlockState(new BlockPos(x, y, z)).getBlock();
				// we're still in ground
				if (currentTerrain != Blocks.STONE) {
					// we found the lowest chunk of earth
					mazeHeight += ((y - mazeHeight) * squishFactor);
					break;
				}
			}
		}

		// sets the ground level to the maze height
		for (int y = 0; y < mazeHeight; y++) {
			Block b = primer.getBlockState(new BlockPos(x, y, z)).getBlock();
			if (b == Blocks.AIR || b == Blocks.WATER) {
				primer.setBlockState(new BlockPos(x, y, z), Blocks.STONE.getDefaultState(), false);
			}
		}
		for (int y = mazeHeight; y <= 127; y++) {
			Block b = primer.getBlockState(new BlockPos(x, y, z)).getBlock();
			if (b != Blocks.AIR && b != Blocks.WATER) {
				primer.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), false);
			}
		}
	}

	private void deformTerrainForYetiLair(ChunkPrimer primer, TFFeature nearFeature, int x, int z, int dx, int dz) {

		float squishFactor = 0f;
		int topHeight = TFWorld.SEALEVEL + 24;
		int outerBoundary = (nearFeature.size * 2 + 1) * 8 - 8;

		// outer boundary
		if (dx <= -outerBoundary) {
			squishFactor = (-dx - outerBoundary) / 8.0f;
		} else if (dx >= outerBoundary) {
			squishFactor = (dx - outerBoundary) / 8.0f;
		}

		if (dz <= -outerBoundary) {
			squishFactor = Math.max(squishFactor, (-dz - outerBoundary) / 8.0f);
		} else if (dz >= outerBoundary) {
			squishFactor = Math.max(squishFactor, (dz - outerBoundary) / 8.0f);
		}

		// inner boundary
		int caveBoundary = (nearFeature.size * 2) * 8 - 8;
		int hollowCeiling = TFWorld.SEALEVEL + 16;

		int offset = Math.min(Math.abs(dx), Math.abs(dz));
		hollowCeiling = (TFWorld.SEALEVEL + 40) - (offset * 4);

		// center square cave
		if (dx >= -caveBoundary && dz >= -caveBoundary && dx <= caveBoundary && dz <= caveBoundary) {
			hollowCeiling = TFWorld.SEALEVEL + 16;
		}

		// slope ceiling slightly
		hollowCeiling -= (offset / 6);

		// max out ceiling 8 blocks from roof
		hollowCeiling = Math.min(hollowCeiling, TFWorld.SEALEVEL + 16);

		// floor, also with slight slope
		int hollowFloor = TFWorld.SEALEVEL - 1 + (offset / 6);

		if (squishFactor > 0f) {
			// blend the old terrain height to arena height
			for (int y = 0; y <= 127; y++) {
				Block currentTerrain = primer.getBlockState(new BlockPos(x, y, z)).getBlock();
				if (currentTerrain != Blocks.STONE) {
					// we found the lowest chunk of earth
					topHeight += ((y - topHeight) * squishFactor);
					hollowFloor += ((y - hollowFloor) * squishFactor);
					break;
				}
			}
		}

		// carve the cave into the stone

		// add stone
		for (int y = 0; y < topHeight; y++) {
			Block b = primer.getBlockState(new BlockPos(x, y, z)).getBlock();
			if (b == Blocks.AIR || b == Blocks.WATER) {
				primer.setBlockState(new BlockPos(x, y, z), Blocks.STONE.getDefaultState(), false);
			}
		}

		// hollow out inside
		for (int y = hollowFloor + 1; y < hollowCeiling; ++y) {
			primer.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), false);
		}

		// ice floor
		if (hollowFloor < hollowCeiling && hollowFloor < TFWorld.SEALEVEL + 3) {
			primer.setBlockState(new BlockPos(x, hollowFloor, z), Blocks.PACKED_ICE.getDefaultState(), false);
		}
	}

	protected void deformTerrainForTrollCaves(ChunkPrimer primer, TFFeature nearFeature, int x, int z, int dx, int dz) {}

	//TODO: Parameter "nearFeature" is unused. Remove?
	private void deformTerrainForTrollCloud2(ChunkPrimer primer, TFFeature nearFeature, int cx, int cz, int hx, int hz) {
		for (int bx = 0; bx < 4; bx++) {
			for (int bz = 0; bz < 4; bz++) {
				int dx = (bx * 4) - hx - 2;
				int dz = (bz * 4) - hz - 2;

				// generate several centers for other clouds
				int regionX = (cx + 8) >> 4;
				int regionZ = (cz + 8) >> 4;

				long seed = (long) (regionX * 3129871) ^ (long) regionZ * 116129781L;
				seed = seed * seed * 42317861L + seed * 7L;

				int num0 = (int) (seed >> 12 & 3L);
				int num1 = (int) (seed >> 15 & 3L);
				int num2 = (int) (seed >> 18 & 3L);
				int num3 = (int) (seed >> 21 & 3L);
				int num4 = (int) (seed >> 9 & 3L);
				int num5 = (int) (seed >> 6 & 3L);
				int num6 = (int) (seed >> 3 & 3L);
				int num7 = (int) (seed >> 0 & 3L);

				int dx2 = dx + (num0 * 5) - (num1 * 4);
				int dz2 = dz + (num2 * 4) - (num3 * 5);
				int dx3 = dx + (num4 * 5) - (num5 * 4);
				int dz3 = dz + (num6 * 4) - (num7 * 5);

				// take the minimum distance to any center
				double dist0 = Math.sqrt(dx * dx + dz * dz) / 4.0;
				double dist2 = Math.sqrt(dx2 * dx2 + dz2 * dz2) / 3.5;
				double dist3 = Math.sqrt(dx3 * dx3 + dz3 * dz3) / 4.5;

				double dist = Math.min(dist0, Math.min(dist2, dist3));

				float pr = world.rand.nextFloat();
				double cv = (dist - 7F) - (pr * 3.0F);

				// randomize depth and height
				int y = 166;
				int depth = 4;

				if (pr < 0.1F) {
					y++;
				}
				if (pr > 0.6F) {
					depth++;
				}
				if (pr > 0.9F) {
					depth++;
				}

				// generate cloud
				for (int sx = 0; sx < 4; sx++) {
					for (int sz = 0; sz < 4; sz++) {
						int lx = bx * 4 + sx;
						int lz = bz * 4 + sz;

						if (dist < 7 || cv < 0.05F) {

							primer.setBlockState(new BlockPos(lx, y, lz), TFBlocks.wispy_cloud.get().getDefaultState(), false);
							for (int d = 1; d < depth; d++) {
								primer.setBlockState(new BlockPos(lx, y - d, lz), TFBlocks.fluffy_cloud.get().getDefaultState(), false);
							}
							primer.setBlockState(new BlockPos(lx, y - depth, lz), TFBlocks.wispy_cloud.get().getDefaultState(), false);
						} else if (dist < 8 || cv < 1F) {
							for (int d = 1; d < depth; d++) {
								primer.setBlockState(new BlockPos(lx, y - d, lz), TFBlocks.fluffy_cloud.get().getDefaultState(), false);
							}
						}
					}
				}
			}
		}
	}

//	protected final boolean allowSurfaceLakes(Biome biome) {
//		if (biome.decorator instanceof TFBiomeDecorator) {
//			return !((TFBiomeDecorator) biome.decorator).hasCanopy;
//		}
//		return true;
//	}

	public final boolean shouldGenerateBedrock() {
		return shouldGenerateBedrock;
	}

	//TODO: If we handled this, it is now done via Biome
//	@Override
//	public boolean generateStructures(Chunk chunk, int x, int z) {
//		return false;
//	}

	/**
	 * Returns a list of creatures of the specified type that can spawn at the
	 * given location.
	 * <p>
	 * Twilight Forest variant! First check features, then only if we're not in
	 * a feature, check the biome.
	 */
	@Override
	public List<SpawnListEntry> getPossibleCreatures(EntityClassification creatureType, BlockPos pos) {
		// are the specified coordinates precisely in a feature?
		TFFeature nearestFeature = TFFeature.getFeatureForRegionPos(pos.getX(), pos.getZ(), world);

		List<SpawnListEntry> featureList = getFeatureGenerator(nearestFeature).getPossibleCreatures(creatureType, pos);
		if (featureList != null) {
			return featureList;
		}

		Biome biome = world.getBiome(pos);

		if (pos.getY() < TFWorld.SEALEVEL && biome instanceof TFBiomeBase) {
			// cave monsters!
			return ((TFBiomeBase) biome).getUndergroundSpawnableList(creatureType);
		} else {
			return biome.getSpawns(creatureType);
		}
	}

	@Nullable
	@Override
	public BlockPos findNearestStructure(World world, String structureName, BlockPos position, int range, boolean findUnexplored) {
		if (structureName.equalsIgnoreCase(hollowTreeGenerator.getStructureName())) {
			return hollowTreeGenerator.findNearest(world, this, position, range, findUnexplored);
		}
		TFFeature feature = TFFeature.getFeatureByName(new ResourceLocation(structureName));
		if (feature != TFFeature.NOTHING) {
			return TFFeature.findNearestFeaturePosBySpacing(world, feature, position, 20, 11, 10387313, true, 100, findUnexplored);
		}
		return null;
	}

	protected final MapGenTFMajorFeature getFeatureGenerator(TFFeature feature) {
		return featureGenerators.getOrDefault(feature, nothingGenerator);
	}

	public void setStructureConquered(BlockPos pos, boolean flag) {
		getFeatureGenerator(TFFeature.getFeatureForRegionPos(pos.getX(), pos.getZ(), world)).setStructureConquered(pos, flag);
	}

	public boolean isStructureLocked(BlockPos pos, int lockIndex) {
		return getFeatureGenerator(TFFeature.getFeatureForRegionPos(pos.getX(), pos.getZ(), world)).isStructureLocked(pos, lockIndex);
	}

	//TODO: isInsideStructure doesn't exist
	public boolean isBlockInStructureBB(BlockPos pos) {
		return getFeatureGenerator(TFFeature.getFeatureForRegionPos(pos.getX(), pos.getZ(), world)).isInsideStructure(pos);
	}

	@Nullable
	public MutableBoundingBox getSBBAt(BlockPos pos) {
		return getFeatureGenerator(TFFeature.getFeatureForRegionPos(pos.getX(), pos.getZ(), world)).getSBBAt(pos);
	}

	public boolean isBlockProtected(BlockPos pos) {
		return getFeatureGenerator(TFFeature.getFeatureForRegionPos(pos.getX(), pos.getZ(), world)).isBlockProtectedAt(pos);
	}

	public boolean isStructureConquered(BlockPos pos) {
		return getFeatureGenerator(TFFeature.getFeatureForRegionPos(pos.getX(), pos.getZ(), world)).isStructureConquered(pos);
	}

	public boolean isBlockInFullStructure(int x, int z) {
		return getFeatureGenerator(TFFeature.getFeatureForRegionPos(x, z, world)).isBlockInFullStructure(x, z);
	}

	//TODO: Unused
	public boolean isBlockNearFullStructure(int x, int z, int range) {
		return getFeatureGenerator(TFFeature.getFeatureForRegionPos(x, z, world)).isBlockNearFullStructure(x, z, range);
	}

	//public StructureBoundingBox getFullSBBAt(int mapX, int mapZ) {
	//	getFeatureGenerator(TFFeature.getFeatureAt(mapX, mapZ, world)).getFullSBBAt(mapX, mapZ);
	//}

	@Nullable
	public MutableBoundingBox getFullSBBNear(int mapX, int mapZ, int range) {
		return getFeatureGenerator(TFFeature.getFeatureForRegionPos(mapX, mapZ, world)).getFullSBBNear(mapX, mapZ, range);
	}

	//TODO: Unused
	public TFFeature getFeatureAt(BlockPos pos) {
		return getFeatureGenerator(TFFeature.getFeatureForRegionPos(pos.getX(), pos.getZ(), world)).getFeatureAt(pos);
	}

	//FIXME: Method does not exist
//	@Override
//	public void recreateStructures(Chunk chunk, int x, int z) {
//		for (MapGenTFMajorFeature generator : featureGenerators.values()) {
//			generator.generate(world, x, z, null);
//		}
//	}

	//FIXME: Method does not exist
//	@Override
//	public boolean isInsideStructure(World world, String structureName, BlockPos pos) {
//		if (structureName.equalsIgnoreCase(hollowTreeGenerator.getStructureName())) {
//			return hollowTreeGenerator.isInsideStructure(pos);
//		}
//		TFFeature feature = TFFeature.getFeatureByName(new ResourceLocation(structureName));
//		return feature != TFFeature.NOTHING && getFeatureGenerator(feature).isInsideStructure(pos);
//	}
}
