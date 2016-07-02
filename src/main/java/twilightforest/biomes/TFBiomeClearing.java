package twilightforest.biomes;

import java.util.Random;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;


public class TFBiomeClearing extends TFBiomeBase {

	public TFBiomeClearing(BiomeProperties props) {
		super(props);

//        this.rootHeight = 0.01F;
//        this.heightVariation = 0F;

        getTFBiomeDecorator().canopyPerChunk = -999;
		getTFBiomeDecorator().setTreesPerChunk(-999);
        
		getTFBiomeDecorator().setFlowersPerChunk(4);
		getTFBiomeDecorator().setGrassPerChunk(10);
	}

	@Override
    public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
    {
        return new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
    }

}
