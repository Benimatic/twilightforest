package twilightforest.world.components.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import twilightforest.init.TFBlocks;

public class UndergroundPlantFeature extends Feature<BlockStateConfiguration> {

	public UndergroundPlantFeature(Codec<BlockStateConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<BlockStateConfiguration> ctx) {
		WorldGenLevel world = ctx.level();
		BlockPos pos = ctx.origin();
		RandomSource random = ctx.random();

		int copyX = pos.getX();
		int copyZ = pos.getZ();

		for (; pos.getY() > world.getMinBuildHeight(); pos = pos.below()) {
			if (world.isEmptyBlock(pos) && random.nextInt(6) > 0) {
				if (ctx.config().state.canSurvive(ctx.level(), pos)) {
					if (ctx.config().state.is(TFBlocks.TROLLVIDR.get()) && random.nextInt(10) == 0) {
						world.setBlock(pos, TFBlocks.UNRIPE_TROLLBER.get().defaultBlockState(), 16 | 2);
					} else {
						world.setBlock(pos, ctx.config().state, 16 | 2);
					}
				}
			} else {
				pos = new BlockPos(
						copyX + random.nextInt(4) - random.nextInt(4),
						pos.getY(),
						copyZ + random.nextInt(4) - random.nextInt(4)
				);
			}
		}
		return true;
	}
}
