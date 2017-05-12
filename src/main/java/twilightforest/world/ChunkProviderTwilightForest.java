// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package twilightforest.world;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import twilightforest.TFFeature;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.block.TFBlocks;
import cpw.mods.fml.common.eventhandler.Event.Result;

// Referenced classes of package net.minecraft.src:
//            IChunkProvider, MapGenCaves, MapGenStronghold, MapGenVillage, 
//            MapGenMineshaft, MapGenRavine, NoiseGeneratorOctaves, World, 
//            WorldChunkManager, Block, BiomeGenBase, Chunk, 
//            MapGenBase, MathHelper, BlockSand, WorldGenLakes, 
//            WorldGenDungeons, SpawnerAnimals, IProgressUpdate

public class ChunkProviderTwilightForest implements IChunkProvider {
	private Random rand;
	//private NoiseGeneratorOctaves noiseGen1;
	//private NoiseGeneratorOctaves noiseGen2;
	//private NoiseGeneratorOctaves noiseGen3;
	private NoiseGeneratorOctaves noiseGen4;
	public NoiseGeneratorOctaves noiseGen5;
	public NoiseGeneratorOctaves noiseGen6;
	public NoiseGeneratorOctaves mobSpawnerNoise;
	private World worldObj;
	private double stoneNoise[];
	private TFGenCaves caveGenerator;
	private TFGenRavine ravineGenerator;
	private BiomeGenBase biomesForGeneration[];
	double noise3[];
	double noise1[];
	double noise2[];
	double noise5[];
	double noise6[];
	float squareTable[];
	int unusedIntArray32x32[][];
	
	
    private WorldType field_147435_p;

    private NoiseGeneratorOctaves field_147431_j;
    private NoiseGeneratorOctaves field_147432_k;
    private NoiseGeneratorOctaves field_147429_l;
    private NoiseGeneratorPerlin field_147430_m;
	
    private final double[] terrainCalcs;
    private final float[] parabolicField;
	
    double[] field_147427_d;
    double[] field_147428_e;
    double[] field_147425_f;
    double[] field_147426_g;
    int[][] field_73219_j = new int[32][32];

	private MapGenTFMajorFeature majorFeatureGenerator;
	private MapGenTFHollowTree hollowTreeGenerator;

	public ChunkProviderTwilightForest(World world, long l, boolean flag) {
		stoneNoise = new double[256];
		caveGenerator = new TFGenCaves();
	
		majorFeatureGenerator = new MapGenTFMajorFeature();
		hollowTreeGenerator = new MapGenTFHollowTree();
	
		ravineGenerator = new TFGenRavine();
		unusedIntArray32x32 = new int[32][32];
		worldObj = world;
		rand = new Random(l);
		//noiseGen1 = new NoiseGeneratorOctaves(rand, 16);
		//noiseGen2 = new NoiseGeneratorOctaves(rand, 16);
		//noiseGen3 = new NoiseGeneratorOctaves(rand, 8);
		noiseGen4 = new NoiseGeneratorOctaves(rand, 4);
		noiseGen5 = new NoiseGeneratorOctaves(rand, 10);
		noiseGen6 = new NoiseGeneratorOctaves(rand, 16);
		mobSpawnerNoise = new NoiseGeneratorOctaves(rand, 8);
		
		
        this.field_147435_p = world.getWorldInfo().getTerrainType();
        this.field_147431_j = new NoiseGeneratorOctaves(this.rand, 16);
        this.field_147432_k = new NoiseGeneratorOctaves(this.rand, 16);
        this.field_147429_l = new NoiseGeneratorOctaves(this.rand, 8);
        this.field_147430_m = new NoiseGeneratorPerlin(this.rand, 4);
		
        this.terrainCalcs = new double[825];
        this.parabolicField = new float[25];

        for (int j = -2; j <= 2; ++j)
        {
            for (int k = -2; k <= 2; ++k)
            {
                float f = 10.0F / MathHelper.sqrt_float((float)(j * j + k * k) + 0.2F);
                this.parabolicField[j + 2 + (k + 2) * 5] = f;
            }
        }
	}

	@Override
	public Chunk provideChunk(int cx, int cz) {
		rand.setSeed(cx * 0x4f9939f508L + cz * 0x1ef1565bd5L);
		Block blockStorage[] = new Block[16 * 16 * TFWorld.CHUNKHEIGHT];
		byte metaStorage[] = new byte[16 * 16 * TFWorld.CHUNKHEIGHT];
		generateTerrain2(cx, cz, blockStorage);
		
		squishTerrain(blockStorage);
		
		addDarkForestCanopy2(cx, cz, blockStorage, metaStorage);
		biomesForGeneration = worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, cx * 16, cz * 16, 16, 16);
		addGlaciers(cx, cz, blockStorage, metaStorage, biomesForGeneration);
		deformTerrainForFeature(cx, cz, blockStorage, metaStorage);
		replaceBlocksForBiome(cx, cz, blockStorage, metaStorage, biomesForGeneration);
		caveGenerator.func_151539_a(this, worldObj, cx, cz, blockStorage);
		ravineGenerator.func_151539_a(this, worldObj, cx, cz, blockStorage);
		// fake byte array
		Block[] fake = new Block[0];

		majorFeatureGenerator.func_151539_a(this, worldObj, cx, cz, fake);
		hollowTreeGenerator.func_151539_a(this, worldObj, cx, cz, fake);
	
		Chunk chunk = new Chunk(worldObj, blockStorage, metaStorage, cx, cz);
	
		// load in biomes, to prevent striping?!
		byte[] chunkBiomes = chunk.getBiomeArray();
		for (int i = 0; i < chunkBiomes.length; ++i) {
			chunkBiomes[i] = (byte) this.biomesForGeneration[i].biomeID;
		}
	
		chunk.generateSkylightMap();
	
		return chunk;
	}

	public void generateTerrain2(int chunkX, int chunkZ, Block[] blockStorage)
    {
        byte seaLevel = 63;
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, chunkX * 4 - 2, chunkZ * 4 - 2, 10, 10);
        this.makeLandPerBiome2(chunkX * 4, 0, chunkZ * 4);

        for (int k = 0; k < 4; ++k)
        {
            int l = k * 5;
            int i1 = (k + 1) * 5;

            for (int j1 = 0; j1 < 4; ++j1)
            {
                int k1 = (l + j1) * 33;
                int l1 = (l + j1 + 1) * 33;
                int i2 = (i1 + j1) * 33;
                int j2 = (i1 + j1 + 1) * 33;

                for (int k2 = 0; k2 < 32; ++k2)
                {
                    double d0 = 0.125D;
                    double d1 = this.terrainCalcs[k1 + k2];
                    double d2 = this.terrainCalcs[l1 + k2];
                    double d3 = this.terrainCalcs[i2 + k2];
                    double d4 = this.terrainCalcs[j2 + k2];
                    double d5 = (this.terrainCalcs[k1 + k2 + 1] - d1) * d0;
                    double d6 = (this.terrainCalcs[l1 + k2 + 1] - d2) * d0;
                    double d7 = (this.terrainCalcs[i2 + k2 + 1] - d3) * d0;
                    double d8 = (this.terrainCalcs[j2 + k2 + 1] - d4) * d0;

                    for (int l2 = 0; l2 < 8; ++l2)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i3 = 0; i3 < 4; ++i3)
                        {
                            int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
                            short short1 = 256;
                            j3 -= short1;
                            double d14 = 0.25D;
                            double d16 = (d11 - d10) * d14;
                            double d15 = d10 - d16;

                            for (int k3 = 0; k3 < 4; ++k3)
                            {
                                if ((d15 += d16) > 0.0D)
                                {
                                    blockStorage[j3 += short1] = Blocks.stone;
                                }
                                else if (k2 * 8 + l2 < seaLevel)
                                {
                                    blockStorage[j3 += short1] = Blocks.water;
                                }
                                else
                                {
                                    blockStorage[j3 += short1] = null;
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

    private void makeLandPerBiome2(int x, int zero, int z)
    {

        this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, x, z, 5, 5, 200.0D, 200.0D, 0.5D);
        this.field_147427_d = this.field_147429_l.generateNoiseOctaves(this.field_147427_d, x, zero, z, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
        this.field_147428_e = this.field_147431_j.generateNoiseOctaves(this.field_147428_e, x, zero, z, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        this.field_147425_f = this.field_147432_k.generateNoiseOctaves(this.field_147425_f, x, zero, z, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        int terrainIndex = 0;
        int noiseIndex = 0;
 
        for (int ax = 0; ax < 5; ++ax)
        {
            for (int az = 0; az < 5; ++az)
            {
                float totalVariation = 0.0F;
                float totalHeight = 0.0F;
                float totalFactor = 0.0F;
                byte two = 2;
                BiomeGenBase biomegenbase = this.biomesForGeneration[ax + 2 + (az + 2) * 10];

                for (int ox = -two; ox <= two; ++ox)
                {
                    for (int oz = -two; oz <= two; ++oz)
                    {
                        BiomeGenBase biomegenbase1 = this.biomesForGeneration[ax + ox + 2 + (az + oz + 2) * 10];
                        float rootHeight = biomegenbase1.rootHeight;
                        float heightVariation = biomegenbase1.heightVariation;

                        if (this.field_147435_p == WorldType.AMPLIFIED && rootHeight > 0.0F)
                        {
                            rootHeight = 1.0F + rootHeight * 2.0F;
                            heightVariation = 1.0F + heightVariation * 4.0F;
                        }

                        float heightFactor = this.parabolicField[ox + 2 + (oz + 2) * 5] / (rootHeight + 2.0F);

                        if (biomegenbase1.rootHeight > biomegenbase.rootHeight)
                        {
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
                double terrainNoise = this.field_147426_g[noiseIndex] / 8000.0D;

                if (terrainNoise < 0.0D)
                {
                    terrainNoise = -terrainNoise * 0.3D;
                }

                terrainNoise = terrainNoise * 3.0D - 2.0D;

                if (terrainNoise < 0.0D)
                {
                    terrainNoise /= 2.0D;

                    if (terrainNoise < -1.0D)
                    {
                        terrainNoise = -1.0D;
                    }

                    terrainNoise /= 1.4D;
                    terrainNoise /= 2.0D;
                }
                else
                {
                    if (terrainNoise > 1.0D)
                    {
                        terrainNoise = 1.0D;
                    }

                    terrainNoise /= 8.0D;
                }

                ++noiseIndex;
                double heightCalc = (double)totalHeight;
                double variationCalc = (double)totalVariation;
                heightCalc += terrainNoise * 0.2D;
                heightCalc = heightCalc * 8.5D / 8.0D;
                double d5 = 8.5D + heightCalc * 4.0D;

                for (int ay = 0; ay < 33; ++ay)
                {
                    double d6 = ((double)ay - d5) * 12.0D * 128.0D / 256.0D / variationCalc;

                    if (d6 < 0.0D)
                    {
                        d6 *= 4.0D;
                    }

                    double d7 = this.field_147428_e[terrainIndex] / 512.0D;
                    double d8 = this.field_147425_f[terrainIndex] / 512.0D;
                    double d9 = (this.field_147427_d[terrainIndex] / 10.0D + 1.0D) / 2.0D;
                    double terrainCalc = MathHelper.denormalizeClamp(d7, d8, d9) - d6;

                    if (ay > 29)
                    {
                        double d11 = (double)((float)(ay - 29) / 3.0F);
                        terrainCalc = terrainCalc * (1.0D - d11) + -10.0D * d11;
                    }

                    this.terrainCalcs[terrainIndex] = terrainCalc;
                    ++terrainIndex;
                }
            }
        }
    }

    

	/**
	 * Crush the terrain to half the height
	 */
	private void squishTerrain(Block[] blockStorage) {
		int squishHeight = TFWorld.MAXHEIGHT / 2;
		
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < TFWorld.CHUNKHEIGHT; y++) {
		            int index = x * TFWorld.CHUNKHEIGHT * 16 | z * TFWorld.CHUNKHEIGHT | y;

					if (y < squishHeight) {
						int twiceIndex = x * TFWorld.CHUNKHEIGHT * 16 | z * TFWorld.CHUNKHEIGHT | (y * 2 + 1);
						blockStorage[index] = blockStorage[twiceIndex];
					} else {
						blockStorage[index] = Blocks.air;
					}
				}
			}
		}
	}

	/**
	 * Replaces the stone that was placed in with blocks that match the biome
	 */
    public void replaceBlocksForBiome(int chunkX, int chunkZ, Block[] blockStorage, byte[] metaStorage, BiomeGenBase[] biomes)
    {
        ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, chunkX, chunkZ, blockStorage, biomes);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY) return;

        double d0 = 0.03125D;
        this.stoneNoise = this.field_147430_m.func_151599_a(this.stoneNoise, (double)(chunkX * 16), (double)(chunkZ * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

        for (int z = 0; z < 16; ++z)
        {
            for (int x = 0; x < 16; ++x)
            {
                BiomeGenBase biomegenbase = biomes[x + z * 16];
                biomegenbase.genTerrainBlocks(this.worldObj, this.rand, blockStorage, metaStorage, chunkX * 16 + z, chunkZ * 16 + x, this.stoneNoise[x + z * 16]);
            }
        }
    }

	@Override
	public Chunk loadChunk(int i, int j) {
		return provideChunk(i, j);
	}

	/**
	 * Raises up and hollows out the hollow hills.
	 * 
	 * @param cx
	 * @param cz
	 * @param blockStorage
	 * @param metaStorage 
	 */
	public void deformTerrainForFeature(int cx, int cz, Block[] blockStorage, byte[] metaStorage) {
		// what feature are we near?
		TFFeature nearFeature = TFFeature.getNearestFeature(cx, cz, worldObj);
		if (!nearFeature.isTerrainAltered) {
			// well that was easy.
			return;
		}

		int[] nearCenter = TFFeature.getNearestCenter(cx, cz, worldObj);

		int hx = nearCenter[0];
		int hz = nearCenter[1];
		
		if (nearFeature == TFFeature.trollCave) {
			// troll cloud, more like
			deformTerrainForTrollCloud2(blockStorage, metaStorage, nearFeature, cx, cz, hx, hz);
		}

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int dx = x - hx;
				int dz = z - hz;

				if (nearFeature == TFFeature.hill1 || nearFeature == TFFeature.hill2 || nearFeature == TFFeature.hill3 || nearFeature == TFFeature.hydraLair) {
					// hollow hills
					int hdiam = ((nearFeature.size * 2 + 1) * 16);
					int dist = (int) Math.sqrt(dx * dx + dz * dz);
					int hheight = (int) (Math.cos((float)dist / (float)hdiam * Math.PI) * ((float)hdiam / 3F));
					
					raiseHills(blockStorage, nearFeature, hdiam, x, z, dx, dz, hheight);
				} else if (nearFeature == TFFeature.hedgeMaze || nearFeature == TFFeature.nagaCourtyard || nearFeature == TFFeature.questGrove) {
					// hedge mazes, naga arena
					flattenTerrainForFeature(blockStorage, nearFeature, x, z, dx, dz);
				} else if (nearFeature == TFFeature.yetiCave) {
					// yeti lairs are square
					deformTerrainForYetiLair(blockStorage, nearFeature, x, z, dx, dz);
				} else if (nearFeature == TFFeature.trollCave) {
					// troll cloud, more like
					//deformTerrainForTrollCloud(blockStorage, metaStorage, nearFeature, x, z, dx, dz);
				}
			}
		}

		// done!
	}

	private void raiseHills(Block[] storage, TFFeature nearFeature, int hdiam, int x, int z, int dx, int dz, int hillHeight) {
		int newGround = -1;
		boolean foundGroundLevel = false;

		// raise the hill
		for (int y = 0; y < TFWorld.CHUNKHEIGHT; y++) {
            int index = x * TFWorld.CHUNKHEIGHT * 16 | z * TFWorld.CHUNKHEIGHT | y;
			Block currentTerrain = storage[index];
			if (currentTerrain != Blocks.stone && !foundGroundLevel) {
				// we found the top of the stone layer
				newGround = y + hillHeight;

				foundGroundLevel = true;
			}
			if (foundGroundLevel && y <= newGround) {
				storage[index] = Blocks.stone;
			}
		}
		// add the hollow part. Also turn water into stone below that
		int hollow = hillHeight - 4 - nearFeature.size;

		// hydra lair has a piece missing
		if (nearFeature == TFFeature.hydraLair) {
			int mx = dx + 16;
			int mz = dz + 16;
			int mdist = (int) Math.sqrt(mx * mx + mz * mz);
			int mheight = (int) (Math.cos(mdist / (hdiam / 1.5) * Math.PI) * (hdiam / 1.5));

			hollow = Math.max(mheight - 4, hollow);
		}

		if (hollow < 0) {
			hollow = 0;
		}

		for (int y = 0; y < TFWorld.CHUNKHEIGHT; y++) {
            int index = x * TFWorld.CHUNKHEIGHT * 16 | z * TFWorld.CHUNKHEIGHT | y;
			// put a base on hills that go over open space or water
			if (hillHeight > 0 && y < TFWorld.SEALEVEL && storage[index] != Blocks.stone) {
				storage[index] = Blocks.stone;
			}
			// hollow out the hollow parts
			int hollowFloor = TFWorld.SEALEVEL - 3 - (hollow / 8);
			if (nearFeature == TFFeature.hydraLair) {
				// different floor
				hollowFloor = TFWorld.SEALEVEL;
			}
			if (y > hollowFloor && y < hollowFloor + hollow) {
				storage[index] = Blocks.air;
			}
		}
	}

	private void flattenTerrainForFeature(Block[] storage, TFFeature nearFeature, int x, int z, int dx, int dz) {
		int oldGround;
		int newGround;
		float squishfactor = 0;
		int mazeheight = TFWorld.SEALEVEL + 1;
		int FEATUREBOUNDRY = (nearFeature.size * 2 + 1) * 8 - 8;

		if (dx <= -FEATUREBOUNDRY) {
			squishfactor = (-dx - FEATUREBOUNDRY) / 8.0f;
		}

		if (dx >= FEATUREBOUNDRY) {
			squishfactor = (dx - FEATUREBOUNDRY) / 8.0f;
		}
		if (dz <= -FEATUREBOUNDRY) {
			squishfactor = Math.max(squishfactor, (-dz - FEATUREBOUNDRY) / 8.0f);
		}

		if (dz >= FEATUREBOUNDRY) {
			squishfactor = Math.max(squishfactor, (dz - FEATUREBOUNDRY) / 8.0f);
		}

		if (squishfactor > 0) {
			// blend the old terrain height to arena height
			newGround = -1;

			for (int y = 0; y <= 127; y++) {
				int index = (x * 16 + z) * TFWorld.CHUNKHEIGHT + y;
				Block currentTerrain = storage[index];
				if (currentTerrain == Blocks.stone) {
					// we're still in ground
					continue;
				} else {
					if (newGround == -1) {
						// we found the lowest chunk of earth
						oldGround = y;
						mazeheight += ((oldGround - mazeheight) * squishfactor);

						newGround = oldGround;
					}
				}
			}
		}

		// sets the groundlevel to the mazeheight
		for (int y = 0; y <= 127; y++) {
			int index = (x * 16 + z) * TFWorld.CHUNKHEIGHT + y;
			if (y < mazeheight && (storage[index] == Blocks.air || storage[index] == Blocks.water)) {
				storage[index] = Blocks.stone;
			}
			if (y >= mazeheight && storage[index] != Blocks.water) {
				storage[index] = Blocks.air;
			}
		}
	}

	private void deformTerrainForYetiLair(Block[] storage, TFFeature nearFeature, int x, int z, int dx, int dz) {
		int oldGround;
		int newGround;
		float squishfactor = 0;
		int topHeight = TFWorld.SEALEVEL + 24;
		int outerBoundry = (nearFeature.size * 2 + 1) * 8 - 8;
	
		// outer boundry
		if (dx <= -outerBoundry) {
			squishfactor = (-dx - outerBoundry) / 8.0f;
		}
	
		if (dx >= outerBoundry) {
			squishfactor = (dx - outerBoundry) / 8.0f;
		}
		if (dz <= -outerBoundry) {
			squishfactor = Math.max(squishfactor, (-dz - outerBoundry) / 8.0f);
		}
	
		if (dz >= outerBoundry) {
			squishfactor = Math.max(squishfactor, (dz - outerBoundry) / 8.0f);
		}

		// inner boundry 
		int caveBoundry = (nearFeature.size * 2) * 8 - 8;
		int hollowCeiling = TFWorld.SEALEVEL + 16;

		int offset = Math.min(Math.abs(dx), Math.abs(dz));
		hollowCeiling = (TFWorld.SEALEVEL + 40) - (offset * 4);

		// center square cave
		if (dx >= -caveBoundry && dz >= -caveBoundry && dx <= caveBoundry && dz <= caveBoundry) {
			hollowCeiling = TFWorld.SEALEVEL + 16;
		}
		
		// slope ceiling slightly
		hollowCeiling -= (offset / 6);
		
		// max out ceiling 8 blocks from roof
		hollowCeiling = Math.min(hollowCeiling, TFWorld.SEALEVEL + 16);

		// floor, also with slight slope
		int hollowFloor = TFWorld.SEALEVEL - 1 + (offset / 6);
	
		if (squishfactor > 0) {
			// blend the old terrain height to arena height
			newGround = -1;
	
			for (int y = 0; y <= 127; y++) {
				int index = (x * 16 + z) * TFWorld.CHUNKHEIGHT + y;
				Block currentTerrain = storage[index];
				if (currentTerrain == Blocks.stone) {
					// we're still in ground
					continue;
				} else {
					if (newGround == -1) {
						// we found the lowest chunk of earth
						oldGround = y;
						topHeight += ((oldGround - topHeight) * squishfactor);
	
						hollowFloor += ((oldGround - hollowFloor) * squishfactor);
						
						newGround = oldGround;
					}
				}
			}
		}
			
		// carve the cave into the stone
		for (int y = 0; y <= 127; y++) {
			int index = (x * 16 + z) * TFWorld.CHUNKHEIGHT + y;
			
			// add stone
			if (y < topHeight && (storage[index] == null || storage[index] == Blocks.air || storage[index] == Blocks.water)) {
				storage[index] = Blocks.stone;
			}
			
			// hollow out inside
			if (y > hollowFloor && y < hollowCeiling) {
				storage[index] = Blocks.air;
			}
			
			// ice floor
			if (y == hollowFloor && y < hollowCeiling && y < TFWorld.SEALEVEL + 3) {
				storage[index] = Blocks.packed_ice;
			}
		}
	}
	
	private void deformTerrainForTrollCloud(Block[] storage, byte[] metaStorage, TFFeature nearFeature, int x, int z, int dx, int dz) {
		int y = 164;
		
		int index = (x * 16 + z) * TFWorld.CHUNKHEIGHT + y;


		// make 4x4 blocks of cloud

		int bx = dx >> 2;
		int bz = dz >> 2;


		double dist = Math.sqrt(bx * bx + bz * bz);
		
		float pr = pseudoRand(x >> 2, z >> 2);
		
		//System.out.println("pr = " + pr + ", /18 = " + (dist / 18F));
		System.out.println("pr = " + pr + ", dist = " + dist + "dx = " + dx + " dz = " + dz);

		double cv = (dist - 9F) - (pr * 4F);
		
		if (dist < 9 || cv < 0.05F) {
			storage[index] = Blocks.stained_glass;
			storage[index - 1] = Blocks.quartz_block;
			storage[index - 2] = Blocks.quartz_block;
			storage[index - 3] = Blocks.quartz_block;
			storage[index - 4] = Blocks.stained_glass;
		} else if (dist < 10 || cv < 1F) {
			storage[index - 1] = Blocks.stained_glass;
			storage[index - 2] = Blocks.stained_glass;
			storage[index - 3] = Blocks.stained_glass;
		}

	}


	private void deformTerrainForTrollCloud2(Block[] storage, byte[] metaStorage, TFFeature nearFeature, int cx, int cz, int hx, int hz) {
		for (int bx = 0; bx < 4; bx++) {
			for (int bz = 0; bz < 4; bz++) {
				int dx = (bx * 4) - hx - 2;
				int dz = (bz * 4) - hz - 2;
				
				// generate several centers for other clouds
		    	int regionX = (cx + 8) >> 4;
		    	int regionZ = (cx + 8) >> 4;

			    long seed = (long)(regionX * 3129871) ^ (long)regionZ * 116129781L;
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
				
				// make a random float
				float pr = worldObj.rand.nextFloat();
				double cv = (dist - 7F) - (pr * 3.0F);

				//System.out.println("pr = " + pr + ", dist = " + dist + ", dx = " + dx + ", dz = " + dz);

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
						int index = ((bx * 4 + sx) * 16 + (bz * 4 + sz)) * TFWorld.CHUNKHEIGHT + y;
						
						if (dist < 7 || cv < 0.05F) {
							storage[index] = TFBlocks.wispyCloud;
							for (int d = 1; d < depth; d++) {
								storage[index - d] = TFBlocks.fluffyCloud;
							}
							storage[index - depth] = TFBlocks.wispyCloud;
						} else if (dist < 8 || cv < 1F) {
							for (int d = 1; d < depth; d++) {
								storage[index - d] = TFBlocks.wispyCloud;
							}
						}					
					}
				}
			}
		}
	}

	private float pseudoRand(int bx, int bz) {
		Random rand = new Random(this.worldObj.getSeed() + (bx * 321534781) ^ (bz * 756839));
		rand.setSeed(rand.nextLong());
		return rand.nextFloat();
	}

	/**
	 * Adds glaciers onto the map
	 * 
	 * @param chunkX
	 * @param chunkZ
	 * @param blocks
	 */
	public void addGlaciers(int chunkX, int chunkZ, Block blocks[], byte meta[], BiomeGenBase biomes[]) {
		for (int z = 0; z < 16; z++) {
			for (int x = 0; x < 16; x++) {
				BiomeGenBase biome = biomes[x & 15 | (z & 15) << 4];
				if (biome == TFBiomeBase.glacier) {
					// find the (current) top block
					int topLevel = -1;
					for (int y = 127; y >= 0; y--) {
						// look at where we are
			            int index = x * TFWorld.CHUNKHEIGHT * 16 | z * TFWorld.CHUNKHEIGHT | y;
						Block currentBlock = blocks[index];
						if (currentBlock == Blocks.stone) {
							topLevel = y;
							// make that block gravel
							blocks[index] = Blocks.gravel;
							break;
						}
					}

					// raise the glacier from that top block
					int gHeight = 32;
					int gTop = topLevel + gHeight + 1;

					for (int y = topLevel + 1; y <= gTop && y < 128; y++) {
			            int index = x * TFWorld.CHUNKHEIGHT * 16 | z * TFWorld.CHUNKHEIGHT | y;
						blocks[index] = Blocks.ice;
					}
				}
			}
		}
	}

	/**
	 * Adds dark forest canopy.  This version uses the "unzoomed" array of biomes used in land generation to determine how many of the nearby blocks are dark forest
	 */
	public void addDarkForestCanopy2(int chunkX, int chunkZ, Block blocks[], byte meta[]) {
		

		int[] thicks = new int[5 * 5];
		
		for (int z = 0; z < 5; z++) {
			for (int x = 0; x < 5; x++) {

				for (int bx = -1 ; bx <= 1; bx++) {
					for (int bz = -1; bz <= 1; bz++) {
						BiomeGenBase biome = biomesForGeneration[x + bx + 2 + (z + bz + 2) * (10)];
						
						if (biome == TFBiomeBase.darkForest || biome == TFBiomeBase.darkForestCenter) {
							thicks[x + z * 5]++;
						}
					}
				}
			}
		}

		
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
				TFFeature nearFeature = TFFeature.getNearestFeature(chunkX, chunkZ, worldObj);
				if (nearFeature == TFFeature.darkTower) {
					// check for closeness
					int[] nearCenter = TFFeature.getNearestCenter(chunkX, chunkZ, worldObj);
					int hx = nearCenter[0];
					int hz = nearCenter[1];

					int dx = x - hx;
					int dz = z - hz;
					int dist = (int) Math.sqrt(dx * dx + dz * dz);

					if (dist < 24) {
						
						thickness -= (24 - dist);
					}
				}
				
				boolean generateForest = thickness > 1;

				
				if (generateForest) {
					double d = 0.03125D;
					stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, d * 2D, d * 2D, d * 2D);

					// find the (current) top block
					int topLevel = -1;
					for (int y = 127; y >= 0; y--) {
						// look at where we are
			            int index = x * TFWorld.CHUNKHEIGHT * 16 | z * TFWorld.CHUNKHEIGHT | y;
						// int index = (x * 16 + z) * 128 + y;
						Block currentBlock = blocks[index];
						if (currentBlock == Blocks.water) {
							// don't generate over water
							break;
						}
						if (currentBlock == Blocks.stone) {
							topLevel = y;
							break;
						}
					}

					if (topLevel != -1) {
						// just use the same noise generator as the terrain uses
						// for stones
						int noise = Math.min(3, (int) (stoneNoise[z & 15 | (x & 15) << 4] / 1.25f));

						// manipulate top and bottom
						int treeBottom = topLevel + 12 - (int)(thickness * 0.5F);
						int treeTop = treeBottom + (int)(thickness * 1.5F);
						
						treeBottom -= noise;

						for (int y = treeBottom; y < treeTop; y++) {
				            int index = x * TFWorld.CHUNKHEIGHT * 16 | z * TFWorld.CHUNKHEIGHT | y;
							blocks[index] = TFBlocks.darkleaves;
							meta[index] = (byte) 0;
						}
					}
				}
			}
		}
	}

	@Override
	public boolean chunkExists(int i, int j) {
		return true;
	}

	// int chunksGenerated = 0;
	// long totalTime = 0;

	@Override
	public void populate(IChunkProvider ichunkprovider, int chunkX, int chunkZ) {
		//long startTime = System.nanoTime();

		// Allow the other mods to generate stuff in our biomes.
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(ichunkprovider, worldObj, rand, chunkX, chunkZ, false));
		
		BlockFalling.fallInstantly = true;
		int mapX = chunkX * 16;
		int mapY = chunkZ * 16;

		BiomeGenBase biomeGen = worldObj.getBiomeGenForCoords(mapX + 16, mapY + 16);

		rand.setSeed(worldObj.getSeed());
		long l1 = (rand.nextLong() / 2L) * 2L + 1L;
		long l2 = (rand.nextLong() / 2L) * 2L + 1L;
		rand.setSeed(chunkX * l1 + chunkZ * l2 ^ worldObj.getSeed());

		boolean disableFeatures = false;

		disableFeatures |= this.majorFeatureGenerator.generateStructuresInChunk(worldObj, rand, chunkX, chunkZ);
		disableFeatures |= !TFFeature.getNearestFeature(chunkX, chunkZ, worldObj).areChunkDecorationsEnabled;
		
		hollowTreeGenerator.generateStructuresInChunk(worldObj, rand, chunkX, chunkZ);

		if (!disableFeatures && rand.nextInt(4) == 0 && biomeGen.theBiomeDecorator.generateLakes) {
			int i1 = mapX + rand.nextInt(16) + 8;
			int i2 = rand.nextInt(TFWorld.CHUNKHEIGHT);
			int i3 = mapY + rand.nextInt(16) + 8;
			(new WorldGenLakes(Blocks.water)).generate(worldObj, rand, i1, i2, i3);
		}
		
		if (!disableFeatures && rand.nextInt(32) == 0) // reduced from 8
		{
			int j1 = mapX + rand.nextInt(16) + 8;
			int j2 = rand.nextInt(rand.nextInt(TFWorld.CHUNKHEIGHT - 8) + 8);
			int j3 = mapY + rand.nextInt(16) + 8;
			if (j2 < TFWorld.SEALEVEL || rand.nextInt(10) == 0) {
				(new WorldGenLakes(Blocks.lava)).generate(worldObj, rand, j1, j2, j3);
			}
		}
		for (int k1 = 0; k1 < 8; k1++) {
			int k2 = mapX + rand.nextInt(16) + 8;
			int k3 = rand.nextInt(TFWorld.CHUNKHEIGHT);
			int l3 = mapY + rand.nextInt(16) + 8;
			(new WorldGenDungeons()).generate(worldObj, rand, k2, k3, l3);
		}

		biomeGen.decorate(worldObj, rand, mapX, mapY);
		SpawnerAnimals.performWorldGenSpawning(worldObj, biomeGen, mapX + 8, mapY + 8, 16, 16, rand);
		mapX += 8;
		mapY += 8;
		for (int i2 = 0; i2 < 16; i2++) {
			for (int j3 = 0; j3 < 16; j3++) {
				int j4 = worldObj.getPrecipitationHeight(mapX + i2, mapY + j3);
				if (worldObj.isBlockFreezable(i2 + mapX, j4 - 1, j3 + mapY)) {
					worldObj.setBlock(i2 + mapX, j4 - 1, j3 + mapY, Blocks.ice, 0, 2);
				}
				if (worldObj.func_147478_e(i2 + mapX, j4, j3 + mapY, true)) {
					worldObj.setBlock(i2 + mapX, j4, j3 + mapY, Blocks.snow_layer, 0, 2);
				}
			}
		}

		BlockFalling.fallInstantly = false;

		// long endTime = System.nanoTime();
		// long chunkTime = (endTime - startTime);
		//
		// System.out.println("Generated a chunk in " + chunkTime +
		// " nanoseconds.");
		//
		// this.totalTime += chunkTime;
		// this.chunksGenerated++;
		//
		// System.out.println("Generated " + chunksGenerated +
		// " chunks in avg. " + (totalTime / chunksGenerated) + " nanos or " +
		// ((int)( (totalTime / chunksGenerated / 1000000))) + " millis.");
	}

	@Override
	public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
		return true;
	}

	@Override
	public boolean canSave() {
		return true;
	}

	@Override
	public String makeString() {
		return "TwilightLevelSource";
	}

	/**
	 * Returns a list of creatures of the specified type that can spawn at the
	 * given location.
	 * 
	 * Twilight Forest varient! First check features, then only if we're not in
	 * a feature, check the biome.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, int mapX, int mapY, int mapZ) {
		// are the specified coordinates precicely in a feature?
		TFFeature nearestFeature = TFFeature.getFeatureForRegion(mapX >> 4, mapZ >> 4, worldObj);

		if (nearestFeature != TFFeature.nothing) {
			// if the feature is already conquered, no spawns
			if (this.isStructureConquered(mapX, mapY, mapZ)) {
				return null;
			}

			// check the precise coords.
			int spawnListIndex = this.majorFeatureGenerator.getSpawnListIndexAt(mapX, mapY, mapZ);
			if (spawnListIndex >= 0) {
				return nearestFeature.getSpawnableList(creatureType, spawnListIndex);
			}
		}

		BiomeGenBase biome = worldObj.getBiomeGenForCoords(mapX, mapZ);

		if (biome == null) {
			return null;
		} else if (mapY < TFWorld.SEALEVEL && creatureType == EnumCreatureType.monster && biome instanceof TFBiomeBase) {
			// cave monsters!
			return ((TFBiomeBase)biome).getUndergroundSpawnableList();
		} else {
			return biome.getSpawnableList(creatureType);
		}
	}
	
	/**
	 * Is the block specified part of a structure bounding box?
	 */
	public boolean isBlockInStructureBB(int mapX, int mapY, int mapZ) {
		return this.majorFeatureGenerator.hasStructureAt(mapX, mapY, mapZ);
	}

	/**
	 * Find the structure at x, y, z, and set the "isConquered" flag
	 */
	public void setStructureConquered(int mapX, int mapY, int mapZ, boolean flag) {
		this.majorFeatureGenerator.setStructureConquered(mapX, mapY, mapZ, flag);
	}
	
	public StructureBoundingBox getSBBAt(int mapX, int mapY, int mapZ) {
		return this.majorFeatureGenerator.getSBBAt(mapX, mapY, mapZ);
	}

	public boolean isBlockProtected(int x, int y, int z) {
		return this.majorFeatureGenerator.isBlockProtectedAt(x, y, z);
	}

	public boolean isStructureConquered(int mapX, int mapY, int mapZ) {
		return this.majorFeatureGenerator.isStructureConquered(mapX, mapY, mapZ);
	}
	
	public boolean isStructureLocked(int mapX, int mapY, int mapZ, int lockIndex) {
		return this.majorFeatureGenerator.isStructureLocked(mapX, mapY, mapZ, lockIndex);
	}
	

	public boolean isBlockInFullStructure(int x, int z) {
		return this.majorFeatureGenerator.isBlockInFullStructure(x, z);
	}
	
	public boolean isBlockNearFullStructure(int x, int z, int range) {
		return this.majorFeatureGenerator.isBlockNearFullStructure(x, z, range);
	}
	
	public StructureBoundingBox getFullSBBAt(int mapX, int mapZ) {
		return this.majorFeatureGenerator.getFullSBBAt(mapX, mapZ);
	}


	public StructureBoundingBox getFullSBBNear(int mapX, int mapZ, int range) {
		return this.majorFeatureGenerator.getFullSBBNear(mapX, mapZ, range);

	}

	@Override
	public int getLoadedChunkCount() {
		return 0;
	}

	@Override
	public void recreateStructures(int var1, int var2) {
		majorFeatureGenerator.func_151539_a(this, worldObj, var1, var2, (Block[]) null);
		hollowTreeGenerator.func_151539_a(this, worldObj, var1, var2, (Block[]) null);
	}

	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}

	@Override
	public void saveExtraData() {
	}

	@Override
	public ChunkPosition func_147416_a(World var1, String var2, int var3,
			int var4, int var5) {
		return null;
	}

}
