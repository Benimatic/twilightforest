package twilightforest.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import twilightforest.block.BlockTFTrollRoot;
import twilightforest.block.TFBlocks;

import java.util.Random;
import java.util.function.Function;

public class TFGenTrollRoots<T extends NoFeatureConfig> extends Feature<T> {

	public TFGenTrollRoots(Function<Dynamic<?>, T> config) {
		super(config);
	}

	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos pos, T config) {
		int copyX = pos.getX();
		int copyZ = pos.getZ();

		for (; pos.getY() > 5; pos = pos.down()) {
			if (world.isAirBlock(pos) && BlockTFTrollRoot.canPlaceRootBelow(world, pos.up()) && random.nextInt(6) > 0) {
				if (random.nextInt(10) == 0) {
					world.setBlockState(pos, TFBlocks.unripe_trollber.get().getDefaultState(), 16 | 2);
				} else {
					world.setBlockState(pos, TFBlocks.trollvidr.get().getDefaultState(), 16 | 2);
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
