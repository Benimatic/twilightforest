package twilightforest.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import twilightforest.util.FeatureUtil;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

import java.util.Random;
import java.util.function.Function;

/**
 * A stump from a hollow tree
 *
 * @author Ben
 */
public class TFGenHollowStump<T extends TFTreeFeatureConfig> extends TFGenHollowTree<T> {

	public TFGenHollowStump(Function<Dynamic<?>, T> config) {
		super(config);
	}

	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, T config) {
		int radius = rand.nextInt(2) + 2;

		if (!FeatureUtil.isAreaSuitable(world, rand, pos.add(-radius, 0, -radius), 2 * radius, 6, 2 * radius)) {
			return false;
		}

		buildTrunk(world, rand, pos, radius, 6);

		// 3-5 roots at the bottom
		buildBranchRing(world, rand, pos, radius, 3, 2, 6, 0, 0.75D, 0, 3, 5, 3, false);

		// several more taproots
		buildBranchRing(world, rand, pos, radius, 1, 2, 8, 0, 0.9D, 0, 3, 5, 3, false);

		return true;
	}

	@Override
	protected void buildTrunk(World world, Random random, BlockPos pos, int diameter, int maxHeight) {

		int hollow = diameter / 2;

		// go down 4 squares and fill in extra trunk as needed, in case we're on uneven terrain
		for (int dx = -diameter; dx <= diameter; dx++) {
			for (int dz = -diameter; dz <= diameter; dz++) {
				for (int dy = -4; dy < 0; dy++) {
					// determine how far we are from the center.
					int ax = Math.abs(dx);
					int az = Math.abs(dz);
					int dist = (int) (Math.max(ax, az) + (Math.min(ax, az) * 0.5));

					if (dist <= diameter) {
						BlockPos dPos = pos.add(dx, dy, dz);
						if (FeatureUtil.hasAirAround(world, dPos)) {
							this.setBlockAndNotifyAdequately(world, dPos, dist > hollow ? treeState : branchState);
						} else {
							this.setBlockAndNotifyAdequately(world, dPos, rootState);
						}
					}
				}
			}
		}

		// build the trunk upwards
		for (int dx = -diameter; dx <= diameter; dx++) {
			for (int dz = -diameter; dz <= diameter; dz++) {
				int height = 2 + random.nextInt(3) + random.nextInt(2);

				for (int dy = 0; dy <= height; dy++) {
					// determine how far we are from the center.
					int ax = Math.abs(dx);
					int az = Math.abs(dz);
					int dist = (int) (Math.max(ax, az) + (Math.min(ax, az) * 0.5));

					// make a trunk!
					if (dist <= diameter && dist > hollow) {
						setBlockAndNotifyAdequately(world, pos.add(dx, dy, dz), treeState);
					}
				}
			}
		}
	}
}

