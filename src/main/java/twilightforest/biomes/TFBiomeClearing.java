package twilightforest.biomes;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;


public class TFBiomeClearing extends TFBiomeBase {

	public TFBiomeClearing(BiomeProperties props) {
		super(props);

		getTFBiomeDecorator().canopyPerChunk = -999;
		getTFBiomeDecorator().setTreesPerChunk(-999);

		getTFBiomeDecorator().setFlowersPerChunk(4);
		getTFBiomeDecorator().setGrassPerChunk(10);
	}

	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random par1Random) {
		return new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
	}

}
