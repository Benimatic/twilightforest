package twilightforest.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import twilightforest.block.TFPlantBlock;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class TFGenTorchBerries extends Feature<NoneFeatureConfiguration> {

	public TFGenTorchBerries(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator generator, Random random, BlockPos pos, NoneFeatureConfiguration config) {
		int copyX = pos.getX();
		int copyZ = pos.getZ();

		for (; pos.getY() > 5; pos = pos.below()) {
			if (world.isEmptyBlock(pos) && TFPlantBlock.canPlaceRootAt(world, pos) && random.nextInt(6) > 0) {
				world.setBlock(pos, TFBlocks.torchberry_plant.get().defaultBlockState(), 16 | 2);
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
