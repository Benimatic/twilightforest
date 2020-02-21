package twilightforest.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import twilightforest.util.FeatureUtil;

import java.util.Random;
import java.util.function.Function;

public class TFGenStoneCircle<T extends NoFeatureConfig> extends Feature<T> {

	public TFGenStoneCircle(Function<Dynamic<?>, T> configIn) {
		super(configIn);
	}

	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, T config) {
		if (!FeatureUtil.isAreaSuitable(world, rand, pos.add(-3, 0, -3), 6, 4, 6)) {
			return false;
		}

		BlockState mossyCobble = Blocks.MOSSY_COBBLESTONE.getDefaultState();

		// okay!  circle!
		for (int cy = 0; cy <= 2; cy++) {
			world.setBlockState(pos.add(-3, cy, 0), mossyCobble, 3);
			world.setBlockState(pos.add(3, cy, 0), mossyCobble, 3);
			world.setBlockState(pos.add(0, cy, -3), mossyCobble, 3);
			world.setBlockState(pos.add(0, cy, 3), mossyCobble, 3);
			world.setBlockState(pos.add(-2, cy, -2), mossyCobble, 3);
			world.setBlockState(pos.add(2, cy, -2), mossyCobble, 3);
			world.setBlockState(pos.add(-2, cy, 2), mossyCobble, 3);
			world.setBlockState(pos.add(2, cy, 2), mossyCobble, 3);
		}

		return true;
	}
}
