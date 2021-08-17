package twilightforest.world.surfacebuilders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;

import java.util.Random;

// [VanillaCopy] of DefaultSurfaceBuilder
public class FillingSurfaceBuilder extends SurfaceBuilder<FillingSurfaceBuilder.FillingSurfaceBuilderConfig> {
	public FillingSurfaceBuilder(Codec<FillingSurfaceBuilderConfig> config) {
		super(config);
	}

	@Override
	public void apply(Random random, ChunkAccess chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int minSurfaceLevel, long seed, FillingSurfaceBuilderConfig config) {
		final BlockState topState = config.getTopMaterial();
		final BlockState midState = config.getUnderMaterial();
		final BlockState underWaterState = config.getUnderwaterMaterial();
		final BlockState filler = config.underFiller;

		final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

		int noiseHeight = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
		if (noiseHeight == 0) {
			boolean flag = false;

			// Scan the whole chunk from top to bottom instead of top to min level to fill strata with Deadrock
			//  minSurfaceLevel -> chunkIn.getMinBuildHeight()
			for(int y = startHeight; y >= chunkIn.getMinBuildHeight(); --y) {
				mutablePos.set(x, y, z);
				BlockState priorState = chunkIn.getBlockState(mutablePos);
				if (priorState.isAir()) {
					flag = false;
				} else if (priorState.is(defaultBlock.getBlock())) {
					if (!flag) {
						BlockState newState;
						if (y >= seaLevel) {
							newState = Blocks.AIR.defaultBlockState();
						} else if (y == seaLevel - 1) {
							newState = biomeIn.getTemperature(mutablePos) < 0.15F ? Blocks.ICE.defaultBlockState() : defaultFluid;
						} else if (y >= seaLevel - (7 + noiseHeight)) {
							newState = filler;
						} else {
							newState = underWaterState;
						}

						chunkIn.setBlockState(mutablePos, newState, false);
					}

					flag = true;
				}
			}
		} else {
			BlockState newMidState = midState;
			int heightFromSurface = -1;

			for(int y = startHeight; y >= minSurfaceLevel; --y) {
				mutablePos.set(x, y, z);
				BlockState priorState = chunkIn.getBlockState(mutablePos);
				if (priorState.isAir()) {
					heightFromSurface = -1;
				} else if (priorState.is(defaultBlock.getBlock())) {
					if (heightFromSurface == -1) {
						heightFromSurface = noiseHeight;
						BlockState newTopState;
						if (y >= seaLevel + 2) {
							newTopState = topState;
						} else if (y >= seaLevel - 1) {
							newMidState = midState;
							newTopState = topState;
						} else if (y >= seaLevel - 4) {
							newMidState = midState;
							newTopState = midState;
						} else if (y >= seaLevel - (7 + noiseHeight)) {
							newTopState = newMidState;
						} else {
							newMidState = filler;
							newTopState = underWaterState;
						}

						chunkIn.setBlockState(mutablePos, newTopState, false);
					} else if (heightFromSurface > 0) {
						--heightFromSurface;
						chunkIn.setBlockState(mutablePos, newMidState, false);
						/*if (heightFromSurface == 0 && newMidState.is(Blocks.SAND) && noiseHeight > 1) {
							heightFromSurface = random.nextInt(4) + Math.max(0, y - seaLevel);
							newMidState = newMidState.is(Blocks.RED_SAND) ? Blocks.RED_SANDSTONE.defaultBlockState() : Blocks.SANDSTONE.defaultBlockState();
						}*/
					}
					// Added, for replacing the default block downwards
					else {
						chunkIn.setBlockState(mutablePos, filler, false);
					}
				}
			}
		}
	}

	public static class FillingSurfaceBuilderConfig extends SurfaceBuilderBaseConfiguration {
		public static final Codec<FillingSurfaceBuilderConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				BlockState.CODEC.fieldOf("top_material").forGetter(SurfaceBuilderBaseConfiguration::getTopMaterial),
				BlockState.CODEC.fieldOf("under_material").forGetter(SurfaceBuilderBaseConfiguration::getUnderMaterial),
				BlockState.CODEC.fieldOf("underwater_material").forGetter(SurfaceBuilderBaseConfiguration::getUnderwaterMaterial),
				BlockState.CODEC.fieldOf("ground_filler").forGetter(conf -> conf.underFiller)
		).apply(instance, FillingSurfaceBuilderConfig::new));

		private final BlockState underFiller;

		public FillingSurfaceBuilderConfig(BlockState topMaterial, BlockState underMaterial, BlockState underwaterMaterial, BlockState underFiller) {
			super(topMaterial, underMaterial, underwaterMaterial);
			this.underFiller = underFiller;
		}
	}
}
