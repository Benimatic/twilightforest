package twilightforest.structures.hollowtree;

import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.math.MutableBoundingBox;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;

import java.util.Random;
import java.util.function.Predicate;

public class ComponentTFHollowTreeRoot extends ComponentTFHollowTreeMedBranch {

	protected int groundLevel = -1;

	public ComponentTFHollowTreeRoot() {
		super();
	}

	public ComponentTFHollowTreeRoot(TFFeature feature, int i, int sx, int sy, int sz, double length, double angle, double tilt, boolean leafy) {
		super(feature, i, sx, sy, sz, length, angle, tilt, leafy);
		this.boundingBox = new MutableBoundingBox(src, dest);
	}

	@Override
	public boolean addComponentParts(World world, Random random, MutableBoundingBox sbb, boolean drawLeaves) {
		if (!drawLeaves) {
			// offset bounding box to average ground level
			if (this.groundLevel < 0) {
				this.groundLevel = this.findGroundLevel(world, sbb, 90, isGround); // is 90 like a good place to start? :)

				if (this.groundLevel < 0) {
					return true;
				}

				int dy = groundLevel + 5 - src.getY();
				src = src.add(0, dy, 0);
				dest = dest.add(0, dy, 0);
			}

			BlockPos rSrc = src.add(-boundingBox.minX, -boundingBox.minY, -boundingBox.minZ);
			BlockPos rDest = dest.add(-boundingBox.minX, -boundingBox.minY, -boundingBox.minZ);

			drawRootLine(world, sbb, rSrc.getX(), rSrc.getY(), rSrc.getZ(), rDest.getX(), rDest.getY(), rDest.getZ(), TFBlocks.root.get().getDefaultState());
			drawRootLine(world, sbb, rSrc.getX(), rSrc.getY() - 1, rSrc.getZ(), rDest.getX(), rDest.getY() - 1, rDest.getZ(), TFBlocks.root.get().getDefaultState());
		}

		return true;
	}

	/**
	 * Draws a line
	 */
	protected void drawRootLine(World world, MutableBoundingBox sbb, int x1, int y1, int z1, int x2, int y2, int z2, BlockState blockValue) {
		BlockPos lineCoords[] = TFGenerator.getBresehnamArrays(x1, y1, z1, x2, y2, z2);

		for (BlockPos coords : lineCoords) {
			BlockState block = this.getBlockStateFromPos(world, coords.getX(), coords.getY(), coords.getZ(), sbb);

			// three choices here
			if (!block.isNormalCube() || block.getBlock() != Blocks.AIR && block.getMaterial() == Material.ORGANIC) {

				// air, other non-solid, or grass, make wood block
				BlockState log = TFBlocks.oak_log.get().getDefaultState().with(LOG_AXIS, BlockLog.EnumAxis.NONE);
				this.setBlockState(world, log, coords.getX(), coords.getY(), coords.getZ(), sbb);
			} else if (block.getBlock() != Blocks.AIR && block.getMaterial() == Material.WOOD) {
				// wood, do nothing
			} else {
				// solid, make root block
				this.setBlockState(world, blockValue, coords.getX(), coords.getY(), coords.getZ(), sbb);
			}
		}
	}

	protected static final Predicate<BlockState> isGround = state -> {
		Material material = state.getMaterial();
		return material == Material.EARTH || material == Material.ROCK || material == Material.ORGANIC;
	};
}
