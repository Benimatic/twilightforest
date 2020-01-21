package twilightforest.structures.hollowtree;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ChunkGenerator;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class ComponentTFHollowTreeSmallBranch extends ComponentTFHollowTreeMedBranch {

	public ComponentTFHollowTreeSmallBranch() {
		super();
	}

	protected ComponentTFHollowTreeSmallBranch(TFFeature feature, int i, int sx, int sy, int sz, double length, double angle, double tilt, boolean leafy) {
		super(feature, i, sx, sy, sz, length, angle, tilt, leafy);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random random, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		return this.addComponentParts(world.getWorld(), generator, random, sbb, false);
	}

	@Override
	public boolean addComponentParts(World world, ChunkGenerator<?> generator, Random random, MutableBoundingBox sbb, boolean drawLeaves) {

		BlockPos rSrc = src.add(-boundingBox.minX, -boundingBox.minY, -boundingBox.minZ);
		BlockPos rDest = dest.add(-boundingBox.minX, -boundingBox.minY, -boundingBox.minZ);

		if (!drawLeaves)
		{
			BlockState log = TFBlocks.oak_log.get().getDefaultState().with(LOG_AXIS, BlockLog.EnumAxis.NONE);
			drawBresehnam(world, sbb, rSrc.getX(), rSrc.getY(), rSrc.getZ(), rDest.getX(), rDest.getY(), rDest.getZ(), log);
		} else {
			int leafRad = random.nextInt(2) + 1;
			makeLeafBlob(world, sbb, rDest.getX(), rDest.getY(), rDest.getZ(), leafRad);
		}
		return true;
	}


}
