package twilightforest.world.components.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import twilightforest.util.FeatureUtil;

public class TFGenStoneCircle extends Feature<NoneFeatureConfiguration> {

	public TFGenStoneCircle(Codec<NoneFeatureConfiguration> configIn) {
		super(configIn);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
		WorldGenLevel world = ctx.level();
		BlockPos pos = ctx.origin();

		if (!FeatureUtil.isAreaSuitable(world, pos.offset(-3, 0, -3), 6, 4, 6)) {
			return false;
		}

		BlockState mossyCobble = Blocks.MOSSY_COBBLESTONE.defaultBlockState();

		// okay!  circle!
		for (int cy = 0; cy <= 2; cy++) {
			world.setBlock(pos.offset(-3, cy, 0), mossyCobble, 3);
			world.setBlock(pos.offset(3, cy, 0), mossyCobble, 3);
			world.setBlock(pos.offset(0, cy, -3), mossyCobble, 3);
			world.setBlock(pos.offset(0, cy, 3), mossyCobble, 3);
			world.setBlock(pos.offset(-2, cy, -2), mossyCobble, 3);
			world.setBlock(pos.offset(2, cy, -2), mossyCobble, 3);
			world.setBlock(pos.offset(-2, cy, 2), mossyCobble, 3);
			world.setBlock(pos.offset(2, cy, 2), mossyCobble, 3);
		}

		return true;
	}
}
