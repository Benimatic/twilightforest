package twilightforest.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.BaseDiskFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class TFGenMyceliumBlob extends BaseDiskFeature {

	public TFGenMyceliumBlob(Codec<DiskConfiguration> configIn) {
		super(configIn);
	}

	@Override
	public boolean place(FeaturePlaceContext<DiskConfiguration> ctx) {
		WorldGenLevel world = ctx.level();
		BlockPos pos = ctx.origin();
		Random random = ctx.random();
		DiskConfiguration config = ctx.config();

		boolean flag = false;
		int i = config.radius.sample(random);

		for(int j = pos.getX() - i; j <= pos.getX() + i; ++j) {
			for(int k = pos.getZ() - i; k <= pos.getZ() + i; ++k) {
				int l = j - pos.getX();
				int i1 = k - pos.getZ();
				if (l * l + i1 * i1 <= i * i) {
					for(int j1 = pos.getY() - config.halfHeight; j1 <= pos.getY() + config.halfHeight; ++j1) {
						BlockPos blockpos = new BlockPos(j, j1, k);
						Block block = world.getBlockState(blockpos).getBlock();

						for(BlockState blockstate : config.targets) {
							if (blockstate.is(block) && world.isEmptyBlock(pos.above())) {
								world.setBlock(blockpos, config.state, 2);
								flag = true;
								break;
							}
						}
					}
				}
			}
		}

		return flag;
	}
}
