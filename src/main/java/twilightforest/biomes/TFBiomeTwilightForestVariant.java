package twilightforest.biomes;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import twilightforest.block.BlockTFPlant;
import twilightforest.block.TFBlocks;


public class TFBiomeTwilightForestVariant extends TFBiomeBase {

	public TFBiomeTwilightForestVariant(int i) {
		super(i);
		
		this.temperature = 0.7F;
		this.rainfall = 0.8F;
		
//		this.rootHeight = 0.15F;
//		this.heightVariation = 0.4F;
		
		getTFBiomeDecorator().setTreesPerChunk(25);
		getTFBiomeDecorator().setGrassPerChunk(15);
		getTFBiomeDecorator().setFlowersPerChunk(8);
	}

	
    /**
     * Occasional shrub, no birches
     */
    public WorldGenAbstractTree func_150567_a(Random random)
    {
        if(random.nextInt(5) == 0)
        {
        	return new WorldGenShrub(3, 0);
        }
        else if(random.nextInt(10) == 0)
        {
            return new WorldGenBigTree(false);
        } 
        else
        {
            return worldGeneratorTrees;
        }
    }
	
    /**
     * EVEN MOAR FERNZ!
     */
    public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
    {
        if (par1Random.nextInt(4) != 0)
        {
            return new WorldGenTallGrass(Blocks.tallgrass, 2);
        }
        else if (par1Random.nextBoolean())
        {
            return new WorldGenTallGrass(TFBlocks.plant, BlockTFPlant.META_MAYAPPLE);
        }
        else
        {
            return new WorldGenTallGrass(Blocks.tallgrass, 1);
        }
    }
    
    /**
     * Add tall ferns
     */
    public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
        genTallFlowers.func_150548_a(3);

        for (int i = 0; i < 7; ++i)
        {
            int rx = par3 + par2Random.nextInt(16) + 8;
            int rz = par4 + par2Random.nextInt(16) + 8;
            int ry = par2Random.nextInt(par1World.getHeightValue(rx, rz) + 32);
            genTallFlowers.generate(par1World, par2Random, rx, ry, rz);
        }

        super.decorate(par1World, par2Random, par3, par4);
    }


}
