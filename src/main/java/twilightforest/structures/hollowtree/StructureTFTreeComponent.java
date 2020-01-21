package twilightforest.structures.hollowtree;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.TFFeature;
import twilightforest.structures.StructureTFComponentOld;

import java.util.List;
import java.util.Random;

public abstract class StructureTFTreeComponent extends StructureTFComponentOld
{
	public StructureTFTreeComponent() {}

	public StructureTFTreeComponent(TFFeature feature, int i) {
		super(feature, i);
	}

	public abstract boolean addComponentParts(World world, ChunkGenerator<?> generator, Random random, MutableBoundingBox sbb, boolean drawLeaves);

	/**
	 * Checks a potential branch bounding box to see if it intersects a leaf dungeon
	 */
	protected boolean branchIntersectsDungeon(StructureTFTreeComponent branch, List<StructurePiece> list) {
		for (StructurePiece component : list) {
			if (component instanceof ComponentTFHollowTreeLeafDungeon && component.getBoundingBox().intersectsWith(branch.getBoundingBox())) {
				return true;
			}
		}
		// did not find intersecting dungeon
		return false;
	}

	/**
	 * Puts a block only if leaves can go there.
	 */
	protected void placeLeafBlock(World world, BlockState blockState, int x, int y, int z, MutableBoundingBox sbb) {
		final BlockPos pos = getBlockPosWithOffset(x, y, z);

		if (sbb.isVecInside(pos)) {
			BlockState whatsThere = world.getBlockState(pos);

			if (whatsThere.getBlock().canBeReplacedByLeaves(whatsThere, world, pos) && whatsThere.getBlock() != blockState.getBlock()) {
				world.setBlockState(pos, blockState, 2);
			}
		}
	}
}
