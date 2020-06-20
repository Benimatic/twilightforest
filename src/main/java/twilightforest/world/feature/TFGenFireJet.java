package twilightforest.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.material.Material;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class TFGenFireJet extends Feature<BlockStateFeatureConfig> {

	public TFGenFireJet(Function<Dynamic<?>, BlockStateFeatureConfig> configIn) {
		super(configIn);
	}

	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, BlockStateFeatureConfig config) {
		for (int i = 0; i < 4; ++i) {
			BlockPos dPos = pos.add(
					rand.nextInt(8) - rand.nextInt(8),
					rand.nextInt(4) - rand.nextInt(4),
					rand.nextInt(8) - rand.nextInt(8)
			);

			if (world.isAirBlock(dPos) && world.canBlockSeeSky(dPos) && world.getBlockState(dPos.down()).getMaterial() == Material.ORGANIC
					&& world.getBlockState(dPos.east().down()).getMaterial() == Material.ORGANIC && world.getBlockState(dPos.west().down()).getMaterial() == Material.ORGANIC
					&& world.getBlockState(dPos.south().down()).getMaterial() == Material.ORGANIC && world.getBlockState(dPos.north().down()).getMaterial() == Material.ORGANIC) {
				// jet
				world.setBlockState(dPos.down(), config.state, 0);

				// create reservoir with stone walls
				for (int rx = -2; rx <= 2; rx++) {
					for (int rz = -2; rz <= 2; rz++) {
						BlockPos dPos2 = dPos.add(rx, -2, rz);
						if ((rx == 1 || rx == 0 || rx == -1) && (rz == 1 || rz == 0 || rz == -1)) {
							// lava reservoir
							world.setBlockState(dPos2, Blocks.LAVA.getDefaultState(), 0);
						} else if (world.getBlockState(dPos2).getMaterial() != Material.LAVA) {
							// only stone where there is no lava
							world.setBlockState(dPos2, Blocks.STONE.getDefaultState(), 0);
						}
						world.setBlockState(dPos2.down(), Blocks.STONE.getDefaultState(), 0);
					}
				}
			}
		}

		return true;
	}
}
