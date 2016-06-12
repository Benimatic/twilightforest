package twilightforest.biomes;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import twilightforest.block.BlockTFPlant;
import twilightforest.block.TFBlocks;
import twilightforest.world.TFGenHangingLamps;
import twilightforest.world.TFGenLampposts;
import twilightforest.world.TFWorld;

public class TFBiomeFireflyForest extends TFBiomeTwilightForest {
	
	private static final int LAMPPOST_CHANCE = 4;
	TFGenHangingLamps tfGenHangingLamps;
	TFGenLampposts tfGenLampposts;
	WorldGenTallGrass worldGenMushgloom;
	
	public TFBiomeFireflyForest(int i) {
		super(i);

		this.worldGenMushgloom = new WorldGenTallGrass(TFBlocks.plant, BlockTFPlant.META_MUSHGLOOM);
		this.tfGenHangingLamps = new TFGenHangingLamps();
		this.tfGenLampposts = new TFGenLampposts();

		this.temperature = 0.5F;
        this.rainfall = 1.0F;
        
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.theBiomeDecorator.grassPerChunk = 1;

        this.getTFBiomeDecorator().setTreesPerChunk(2);

	}

	
    /**
     * Decorate this biome.  This is stolen from the flower forest code
     */
    @Override
    public void decorate(World world, Random rand, int mapX, int mapZ)
    {
        int flowerCycles = rand.nextInt(3) - 1;

        int successfulFlowers = 0;

        while (successfulFlowers < flowerCycles)
        {
            int flowerType = rand.nextInt(3);

            if (flowerType == 0)
            {
                genTallFlowers.func_150548_a(1);
            }
            else if (flowerType == 1)
            {
                genTallFlowers.func_150548_a(4);
            }
            else if (flowerType == 2)
            {
                genTallFlowers.func_150548_a(5);
            }

            int tallFlowerTries = 0;

            while (true)
            {
                if (tallFlowerTries < 1)
                {
                	int k1 = mapX + rand.nextInt(16) + 8;
                    int i2 = mapZ + rand.nextInt(16) + 8;
                    int l1 = rand.nextInt(world.getHeightValue(k1, i2) + 32);

                    if (!genTallFlowers.generate(world, rand, k1, l1, i2))
                    {
                        ++tallFlowerTries;
                        continue;
                    }
                }

                ++successfulFlowers;
                break;
            }
        }
    	
        super.decorate(world, rand, mapX, mapZ);

        if (rand.nextInt(24) == 0) {
			int rx = mapX + rand.nextInt(16) + 8;
			int rz = mapZ + rand.nextInt(16) + 8;
			int ry = getGroundLevel(world, rx, rz);
			// mushglooms
			this.worldGenMushgloom.generate(world, rand, rx, ry, rz);
		}
        
        // hanging lamps
        for (int i = 0; i < 30; i++) {
			int rx = mapX + rand.nextInt(16) + 8;
			int rz = mapZ + rand.nextInt(16) + 8;
			int ry = TFWorld.SEALEVEL + rand.nextInt(TFWorld.CHUNKHEIGHT - TFWorld.SEALEVEL);

			this.tfGenHangingLamps.generate(world, rand, rx, ry, rz);
        } 
        
        if (rand.nextInt(LAMPPOST_CHANCE) == 0) {
			int rx = mapX + rand.nextInt(16) + 8;
			int rz = mapZ + rand.nextInt(16) + 8;
			int ry = getGroundLevel(world, rx, rz);

			this.tfGenLampposts.generate(world, rand, rx, ry, rz);
        } 
        
		// extra pumpkins (should they be lit?)
		if (rand.nextInt(32) == 0) {
			int rx = mapX + rand.nextInt(16) + 8;
			int rz = mapZ + rand.nextInt(16) + 8;
			int ry = getGroundLevel(world, rx, rz);
			(new WorldGenPumpkin()).generate(world, rand, rx, ry, rz);
		}
        
    }
    
	public int getGroundLevel(World world, int x, int z) {
		// go from sea level up.  If we get grass, return that, otherwise return the last dirt, stone or gravel we got
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		int lastDirt = TFWorld.SEALEVEL;
		for (int y = TFWorld.SEALEVEL; y < TFWorld.CHUNKHEIGHT - 1; y++) {
			Block blockID = chunk.getBlock(x & 15, y, z & 15);
			// grass = return immediately
			if (blockID == Blocks.GRASS) {
				return y + 1;
			}
			else if (blockID == Blocks.DIRT || blockID == Blocks.STONE || blockID == Blocks.GRAVEL) {
				lastDirt = y + 1;
			}
		}
		
		return lastDirt;
	}
	
	/**
	 * Every color flower!
	 */
	public String func_150572_a(Random p_150572_1_, int p_150572_2_, int p_150572_3_, int p_150572_4_)
	{
		double flowerVar = MathHelper.clamp_double((1.0D + plantNoise.func_151601_a((double)p_150572_2_ / 48.0D, (double)p_150572_4_ / 48.0D)) / 2.0D, 0.0D, 0.9999D);
		int flowerIndex = (int)(flowerVar * (double)BlockFlower.field_149859_a.length);

		if (flowerIndex == 1)
		{
			flowerIndex = 0;
		}

		return BlockFlower.field_149859_a[flowerIndex];
	}
}
