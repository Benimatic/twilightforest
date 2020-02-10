package twilightforest.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

/**
 * This class fixes the vanilla WorldGenVines, which appears to be nonfunctional in 1.11
 */
public class TFGenVines<T extends NoFeatureConfig> extends Feature<T> {

	public TFGenVines(Function<Dynamic<?>, T> config) {
		super(config);
	}

	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos position, T config) {

		BlockPos original = position;

		for (; position.getY() > generator.getSeaLevel(); position = position.down()) {

			if (world.isAirBlock(position)) {

				Set<Direction> facings = EnumSet.noneOf(Direction.class);

				for (Direction facing : Direction.Plane.HORIZONTAL.facings()) {
					if (Blocks.VINE.canPlaceBlockOnSide(world, position, facing.getOpposite())) {
						facings.add(facing);
					}
				}

				if (!facings.isEmpty()) {

					BlockState vine = Blocks.VINE.getDefaultState();
					for (Direction facing : facings) {
						vine = vine.with(VineBlock.getPropertyFor(facing), true);
					}

					BlockPos down = position;
					do {
						world.setBlockState(down, vine, 16 | 2);
						down = down.down();
					} while (down.getY() >= 0 && world.isAirBlock(down));

					return true;
				}

			} else {
				position = position.add(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
				if (isOutsideBounds(7, original, position)) {
					break;
				}
			}
		}

		return false;
	}

	private static boolean isOutsideBounds(int radius, BlockPos original, BlockPos pos) {
		return Math.abs(original.getX() - pos.getX()) > radius || Math.abs(original.getZ() - pos.getZ()) > radius;
	}
}
