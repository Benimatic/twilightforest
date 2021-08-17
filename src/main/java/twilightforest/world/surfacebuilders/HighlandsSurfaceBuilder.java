package twilightforest.world.surfacebuilders;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;

import java.util.Random;

public class HighlandsSurfaceBuilder extends DefaultSurfaceBuilder {

	public HighlandsSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> config) {
		super(config);
	}

	@Override
	public void apply(Random rand, ChunkAccess primer, Biome biome, int x, int z, int startheight, double noiseVal, BlockState defaultBlock, BlockState defaultFluid, int sealevel, int minSurfaceLevel, long seed, SurfaceBuilderBaseConfiguration config) {
		BlockState topBlock = config.getTopMaterial();

		if (noiseVal > 1.75D) {
			topBlock = Blocks.COARSE_DIRT.defaultBlockState();
		} else if (noiseVal > -0.95D) {
			topBlock = Blocks.PODZOL.defaultBlockState();
		}

		this.apply(rand, primer, biome, x, z, startheight, noiseVal, defaultBlock, defaultFluid, topBlock, config.getUnderMaterial(), config.getUnderwaterMaterial(), sealevel, minSurfaceLevel);
	}
}
