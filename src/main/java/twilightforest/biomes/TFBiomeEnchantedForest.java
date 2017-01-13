package twilightforest.biomes;

import java.util.Random;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenVines;
import net.minecraft.world.gen.feature.WorldGenerator;
import twilightforest.block.BlockTFPlant;
import twilightforest.block.TFBlocks;
import twilightforest.block.enums.PlantVariant;
import twilightforest.world.TFGenLargeRainboak;
import twilightforest.world.TFGenSmallRainboak;
import twilightforest.world.TFGenTallGrass;
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
    public int getGrassColorAtPos(BlockPos pos)
    {
    	return (super.getGrassColorAtPos(pos) & 0xFFFF00) + getEnchantedColor(pos.getX(), pos.getZ());
    }

    @Override
    public int getFoliageColorAtPos(BlockPos pos)
    {
    	return (super.getFoliageColorAtPos(pos) & 0xFFFF00) + getEnchantedColor(pos.getX(), pos.getZ());
    }

    /**
     * Find a number between 0 and 255.  currently returns concentric circles from the biome center
     */
	private int getEnchantedColor(int x, int z) {
		// center of the biome is at % 256 - 8
    	int cx = 256 * Math.round((x - 8) / 256F) + 8; 
    	int cz = 256 * Math.round((z - 8) / 256F) + 8;
    	
    	int dist = (int) MathHelper.sqrt((cx - x) * (cx - x) + (cz - z) * (cz - z));
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
            return TREE_FEATURE;
        }
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
    {
        if (par1Random.nextInt(3) > 0) {
            return new WorldGenTallGrass(BlockTallGrass.EnumType.FERN);
        }
        else if (par1Random.nextInt(3) == 0) {
            return new TFGenTallGrass(TFBlocks.plant.getDefaultState().withProperty(BlockTFPlant.VARIANT, PlantVariant.FIDDLEHEAD));
        }
        else {
            return new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
        }
    }
    
    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        WorldGenVines worldgenvines = new WorldGenVines();

        BlockPos.MutableBlockPos mutPos = new BlockPos.MutableBlockPos(0, 0, 0);

        for (int i = 0; i < 20; i++)
        {
            int x = pos.getX() + rand.nextInt(16) + 8;
            int y = TFWorld.SEALEVEL;
            int z = pos.getZ() + rand.nextInt(16) + 8;
            mutPos.setPos(x, y, z);
            worldgenvines.generate(world, rand, mutPos);
        }

        // tall ferns
        DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.FERN);

        for (int i = 0; i < 20; ++i) {
        	int rx = pos.getX() + rand.nextInt(16) + 8;
        	int rz = pos.getZ() + rand.nextInt(16) + 8;
            mutPos.setPos(rx, 0, rz);
        	int ry = rand.nextInt(world.getHeight(mutPos).getY() + 32);
            mutPos.setPos(rx, ry, rz);
        	DOUBLE_PLANT_GENERATOR.generate(world, rand, mutPos);
        }

        super.decorate(world, rand, pos);
    }

    /**
     * Every color flower!
     */
    public String func_150572_a(Random p_150572_1_, int p_150572_2_, int p_150572_3_, int p_150572_4_)
    {
    	double flowerVar = MathHelper.clamp_double((1.0D + plantNoise.func_151601_a((double)p_150572_2_ / 48.0D, (double)p_150572_4_ / 48.0D)) / 2.0D, 0.0D, 0.9999D);
        int flowerIndex = (int)(flowerVar * (double)BlockFlower.field_149859_a.length);
        if (flowerIndex == 1)  { flowerIndex = 0; }
        return BlockFlower.field_149859_a[flowerIndex];
    }
}
