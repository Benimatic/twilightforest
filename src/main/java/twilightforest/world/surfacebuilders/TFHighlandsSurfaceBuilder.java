package twilightforest.world.surfacebuilders;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class TFHighlandsSurfaceBuilder extends TFDefaultSurfaceBuilder {

	public TFHighlandsSurfaceBuilder(Codec<SurfaceBuilderConfig> config) {
		super(config);
	}

	@Override
	public void buildSurface(Random rand, IChunk primer, Biome biome, int x, int z, int startheight, double noiseVal, BlockState defaultBlock, BlockState defaultFluid, int sealevel, long seed, SurfaceBuilderConfig config) {
		BlockState topBlock = config.getTop();
		BlockState fillerBlock = config.getUnder();

		if (noiseVal > 1.75D) {
			topBlock = Blocks.COARSE_DIRT.getDefaultState();
		} else if (noiseVal > -0.95D) {
			topBlock = Blocks.PODZOL.getDefaultState();
		}

		this.genTwilightBiomeTerrain(rand, primer, biome, x, z, startheight, noiseVal, defaultBlock, defaultFluid, topBlock, fillerBlock, config.getUnderWaterMaterial(), sealevel);
	}
}
