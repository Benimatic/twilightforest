package twilightforest.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class TFGenLampposts extends Feature<BlockStateFeatureConfig> {

	private static final Rotation[] ROTATIONS = Rotation.values();
	//private final BlockState lamp;

	public TFGenLampposts(Function<Dynamic<?>, BlockStateFeatureConfig> configIn) {
		super(configIn);
	}

	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, BlockStateFeatureConfig config) {
		// we should start on a grass block
		if (world.getBlockState(pos.down()).getBlock() != Blocks.GRASS) {
			return false;
		}

		// generate a height
		int height = 1 + rand.nextInt(4);

		// is it air or replaceable above our grass block
		for (int dy = 0; dy <= height; dy++) {
			BlockState state = world.getBlockState(pos.up(dy));
			if (!state.getBlock().isAir(state, world, pos.up(dy)) && !state.getMaterial().isReplaceable()) {
				return false;
			}
		}

		// generate lamp
		for (int dy = 0; dy < height; dy++) {
			world.setBlockState(pos.up(dy), Blocks.OAK_FENCE.getDefaultState(), 16 | 2);
		}
		world.setBlockState(pos.up(height), config.state.rotate(ROTATIONS[rand.nextInt(ROTATIONS.length)]), 16 | 2);
		return true;
	}
}
