package twilightforest.structures.lichtower;

import net.minecraft.block.StairsBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.MutableBoundingBox;
import twilightforest.TFFeature;

import java.util.Random;


public class ComponentTFTowerRoofStairs extends ComponentTFTowerRoof {

	public ComponentTFTowerRoofStairs() {
		super();
	}

	public ComponentTFTowerRoofStairs(TFFeature feature, int i, ComponentTFTowerWing wing) {
		super(feature, i, wing);

		// always facing = 0.  This roof cannot rotate, due to stair facing issues.
		this.setCoordBaseMode(Direction.SOUTH);

		this.size = wing.size; // assuming only square towers and roofs right now.
		this.height = size / 2;

		// just hang out at the very top of the tower
		makeCapBB(wing);
	}

	/**
	 * Makes a pyramid-shaped roof out of stairs
	 */
	@Override
	public boolean addComponentParts(IWorld world, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		BlockState birchSlab = Blocks.BIRCH_SLAB.getDefaultState();
		BlockState birchPlanks = Blocks.BIRCH_PLANKS.getDefaultState();

		BlockState birchStairsNorth = Blocks.BIRCH_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);
		BlockState birchStairsSouth = Blocks.BIRCH_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);
		BlockState birchStairsEast = Blocks.BIRCH_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST);
		BlockState birchStairsWest = Blocks.BIRCH_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST);

		for (int y = 0; y <= height; y++) {
			int min = y;
			int max = size - y - 1;
			for (int x = min; x <= max; x++) {
				for (int z = min; z <= max; z++) {
					if (x == min) {
						if (z == min || z == max) {
							setBlockState(world, birchSlab, x, y, z, sbb);
						} else {
							setBlockState(world, birchStairsWest, x, y, z, sbb);
						}
					} else if (x == max) {
						if (z == min || z == max) {
							setBlockState(world, birchSlab, x, y, z, sbb);
						} else {
							setBlockState(world, birchStairsEast, x, y, z, sbb);
						}
					} else if (z == max) {
						setBlockState(world, birchStairsSouth, x, y, z, sbb);
					} else if (z == min) {
						setBlockState(world, birchStairsNorth, x, y, z, sbb);
					} else {
						setBlockState(world, birchPlanks, x, y, z, sbb);
					}
				}
			}
		}
		return true;
	}
}
