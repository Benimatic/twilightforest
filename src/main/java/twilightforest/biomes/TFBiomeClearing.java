package twilightforest.biomes;

import java.util.Random;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;


public class TFBiomeClearing extends TFBiomeBase {

	public TFBiomeClearing(int i) {
		super(i);


        this.temperature = 0.8F;
        this.rainfall = 0.4F;
        
//        this.rootHeight = 0.01F;
//        this.heightVariation = 0F;

        getTFBiomeDecorator().canopyPerChunk = -999;
		getTFBiomeDecorator().setTreesPerChunk(-999);
        
		getTFBiomeDecorator().setFlowersPerChunk(4);
		getTFBiomeDecorator().setGrassPerChunk(10);
	}

	/**
	 * No ferns
	 */
    public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
    {
        return new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
    }

}
