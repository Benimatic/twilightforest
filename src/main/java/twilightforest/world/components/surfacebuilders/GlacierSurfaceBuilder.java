package twilightforest.world.components.surfacebuilders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;

import java.util.Random;
import java.util.function.Supplier;

public class GlacierSurfaceBuilder extends DelegatingSurfaceBuilder<GlacierSurfaceBuilder.GlacierSurfaceConfig> {
	public GlacierSurfaceBuilder(Codec<GlacierSurfaceBuilder.GlacierSurfaceConfig> config) {
		super(config);
	}

	@Override
	public void apply(Random random, ChunkAccess chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int minSurfaceLevel, long seed, GlacierSurfaceBuilder.GlacierSurfaceConfig config) {
		config.surfaceBuilder.apply(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, minSurfaceLevel, seed);

		final int oceanFloorHeight = chunkIn.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z);
		final int depthStart = Math.min(startHeight, oceanFloorHeight);
		final int targetHeight = Math.max(seaLevel, oceanFloorHeight) + config.mainHeight;

		final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(x, depthStart, z);

		for (int y = depthStart; y < targetHeight; y++) {
			chunkIn.setBlockState(mutablePos.setY(y), config.mainState, false);
		}

		for (int y = 0; y < config.topHeight; y++) {
			chunkIn.setBlockState(mutablePos.move(0, 1, 0), config.topState, false);
		}
	}

	public static class GlacierSurfaceConfig extends DelegatingSurfaceBuilder.DelegatingConfig {
		public static Codec<GlacierSurfaceConfig> CODEC = RecordCodecBuilder.create(instance -> DelegatingSurfaceBuilder.DelegatingConfig.startCodec(instance).and(instance.group(
				Codec.intRange(1, 64).fieldOf("main_thickness").forGetter(o -> o.mainHeight),
				Codec.intRange(1, 16).fieldOf("top_thickness").forGetter(o -> o.topHeight),
				BlockState.CODEC.fieldOf("main_state").forGetter(o -> o.mainState),
				BlockState.CODEC.fieldOf("top_state").forGetter(o -> o.topState)
		)).apply(instance, GlacierSurfaceConfig::new));

		private final int mainHeight, topHeight;
		private final BlockState mainState, topState;

		public GlacierSurfaceConfig(Supplier<ConfiguredSurfaceBuilder<?>> configuredSurfaceBuilderSupplier, int mainHeight, int topHeight, BlockState mainState, BlockState topState) {
			super(configuredSurfaceBuilderSupplier);
			this.mainHeight = mainHeight;
			this.topHeight = topHeight;
			this.mainState = mainState;
			this.topState = topState;
		}
	}
}
