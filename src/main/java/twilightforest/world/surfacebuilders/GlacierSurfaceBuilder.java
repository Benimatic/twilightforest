package twilightforest.world.surfacebuilders;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;
import twilightforest.worldgen.BlockConstants;

import java.util.Random;

// TODO VanillaCopy and set up custom config for blocks and heights
public class GlacierSurfaceBuilder extends DefaultSurfaceBuilder {
	public GlacierSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> config) {
		super(config);
	}

	@Override
	public void apply(Random random, ChunkAccess chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int minSurfaceLevel, long seed, SurfaceBuilderBaseConfiguration config) {
		super.apply(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, minSurfaceLevel, seed, config);

		final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(x, Math.max(startHeight - 1, seaLevel), z);

		for (int y = 0; y < 30; y++) {
			chunkIn.setBlockState(mutablePos.move(0, 1, 0), BlockConstants.PACKED_ICE, false);
		}

		for (int y = 0; y < 2; y++) {
			chunkIn.setBlockState(mutablePos.move(0, 1, 0), BlockConstants.ICE, false);
		}
	}
}
