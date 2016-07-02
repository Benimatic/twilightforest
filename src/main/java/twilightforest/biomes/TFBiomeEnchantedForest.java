package twilightforest.biomes;

import java.util.Random;

import net.minecraft.block.BlockFlower;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenVines;
import net.minecraft.world.gen.feature.WorldGenerator;
import twilightforest.block.BlockTFPlant;
import twilightforest.block.TFBlocks;
import twilightforest.world.TFGenLargeRainboak;
import twilightforest.world.TFGenSmallRainboak;
import twilightforest.world.TFWorld;



public class TFBiomeEnchantedForest extends TFBiomeBase {
	
	private final Random colorRNG;

	public TFBiomeEnchantedForest(BiomeProperties props) {
		super(props);
		colorRNG = new Random();
		

		getTFBiomeDecorator().setGrassPerChunk(12);
		getTFBiomeDecorator().setFlowersPerChunk(8);
	}
    
    @Override
    public int getBiomeGrassColor(int x, int y, int z)
    {
    	return (super.getBiomeGrassColor(x, y, z) & 0xFFFF00) + getEnchantedColor(x, z);
    }

    @Override
    public int getBiomeFoliageColor(int x, int y, int z)
    {
    	return (super.getBiomeFoliageColor(x, y, z) & 0xFFFF00) + getEnchantedColor(x, z);

        //return (ColorizerGrass.getGrassColor(colorRNG.nextFloat(), colorRNG.nextFloat()) & 0xFFFF00) + colorRNG.nextInt(256);
    }

    /**
     * Find a number between 0 and 255.  currently returns concentric circles from the biome center
     */
	private int getEnchantedColor(int x, int z) {
		// center of the biome is at % 256 - 8
    	int cx = 256 * Math.round((x - 8) / 256F) + 8; 
    	int cz = 256 * Math.round((z - 8) / 256F) + 8;
    	
    	int dist = (int) MathHelper.sqrt_float((cx - x) * (cx - x) + (cz - z) * (cz - z));
    	int color = dist * 64;
    	color %= 512;
    	
    	if (color > 255) {
    		color = 511 - color;
    	}
    	
    	color = 255 - color;
    	
    	int randomFlicker = colorRNG.nextInt(32) - 16;
    	
    	if (0 < color + randomFlicker && color + randomFlicker > 255) {
    		color += randomFlicker;
    	}
    	
		return color;
	}

    @Override
    public WorldGenAbstractTree genBigTreeChance(Random random)
    {
        if (random.nextInt(15) == 0)
        {
            return new TFGenSmallRainboak();
        }
        else if (random.nextInt(50) == 0)
        {
            return new TFGenLargeRainboak();
        }
        else if (random.nextInt(5) == 0)
        {
        	return birchGen;
        }
        else if (random.nextInt(10) == 0)
        {
            return new WorldGenBigTree(false);
        } 
        else
        {
            return worldGeneratorTrees;
        }
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
    {
        if (par1Random.nextInt(3) > 0) {
            return new WorldGenTallGrass(Blocks.TALLGRASS, 2);
        }
        else if (par1Random.nextInt(3) == 0) {
            return new WorldGenTallGrass(TFBlocks.plant, BlockTFPlant.META_FIDDLEHEAD);
        }
        else {
            return new WorldGenTallGrass(Blocks.TALLGRASS, 1);
        }
    }
    
    @Override
    public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
        WorldGenVines worldgenvines = new WorldGenVines();

        for (int i = 0; i < 20; i++)
        {
            int j = par3 + par2Random.nextInt(16) + 8;
            byte byte0 = (byte) TFWorld.SEALEVEL;
            int k = par4 + par2Random.nextInt(16) + 8;
            worldgenvines.generate(par1World, par2Random, j, byte0, k);
        }

        // tall ferns
        genTallFlowers.func_150548_a(3);

        for (int i = 0; i < 20; ++i) {
        	int rx = par3 + par2Random.nextInt(16) + 8;
        	int rz = par4 + par2Random.nextInt(16) + 8;
        	int ry = par2Random.nextInt(par1World.getHeightValue(rx, rz) + 32);
        	genTallFlowers.generate(par1World, par2Random, rx, ry, rz);
        }

        super.decorate(par1World, par2Random, par3, par4);
    }
    
    public String func_150572_a(Random p_150572_1_, int p_150572_2_, int p_150572_3_, int p_150572_4_)
    {
        return p_150572_1_.nextInt(3) > 0 ? BlockFlower.field_149859_a[1] : p_150572_1_.nextBoolean() ? BlockFlower.field_149859_a[2] : BlockFlower.field_149859_a[3];
    }
}
