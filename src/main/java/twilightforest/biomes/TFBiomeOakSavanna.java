package twilightforest.biomes;

import java.util.Random;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import twilightforest.block.BlockTFPlant;
import twilightforest.block.TFBlocks;
import twilightforest.block.enums.PlantVariant;
import twilightforest.world.TFGenCanopyOak;
import twilightforest.world.TFGenNoTree;
import twilightforest.world.TFGenTallGrass;

public class TFBiomeOakSavanna extends TFBiomeBase {

	public TFBiomeOakSavanna(BiomeProperties props) {
		super(props);
		
		getTFBiomeDecorator().canopyTreeGen = new TFGenCanopyOak();
        getTFBiomeDecorator().alternateCanopyChance = 0.8F;
        getTFBiomeDecorator().alternateCanopyGen = new TFGenNoTree();
		
        this.theBiomeDecorator.treesPerChunk = 1;
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.theBiomeDecorator.grassPerChunk = 20;
	}

    @Override
    public WorldGenAbstractTree genBigTreeChance(Random random)
    {
        if(random.nextInt(10) == 0)
        {
            return new WorldGenBigTree(false);
        } else
        {
            return TREE_FEATURE;
        }
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
    {
        if (par1Random.nextInt(10) == 0)
        {
            return new WorldGenTallGrass(BlockTallGrass.EnumType.FERN);
        }
        else if (par1Random.nextInt(10) == 0)
        {
            return new TFGenTallGrass(TFBlocks.plant.getDefaultState().withProperty(BlockTFPlant.VARIANT, PlantVariant.MAYAPPLE));
        }
        else
        {
            return new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
        }
    }
    
    /**
     * Multi-color flowers!
     */
    public String func_150572_a(Random p_150572_1_, int p_150572_2_, int p_150572_3_, int p_150572_4_)
    {
        double d0 = plantNoise.func_151601_a((double)p_150572_2_ / 200.0D, (double)p_150572_4_ / 200.0D);
        int l;

        if (d0 < -0.8D)
        {
            l = p_150572_1_.nextInt(4);
            return BlockFlower.field_149859_a[4 + l];
        }
        else if (p_150572_1_.nextInt(3) > 0)
        {
            l = p_150572_1_.nextInt(3);
            return l == 0 ? BlockFlower.field_149859_a[0] : (l == 1 ? BlockFlower.field_149859_a[3] : BlockFlower.field_149859_a[8]);
        }
        else
        {
            return BlockFlower.field_149858_b[0];
        }
    }

    @Override
    public void decorate(World par1World, Random par2Random, BlockPos pos)
    {
        DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);

        for (int k = 0; k < 7; ++k)
        {
            int l = pos.getX() + par2Random.nextInt(16) + 8;
            int i1 = pos.getZ() + par2Random.nextInt(16) + 8;
            int j1 = par2Random.nextInt(par1World.getHeight(new BlockPos(l, 0, i1)).getY() + 32);
            DOUBLE_PLANT_GENERATOR.generate(par1World, par2Random, new BlockPos(l, j1, i1));
        }

        super.decorate(par1World, par2Random, pos);
    }
}
