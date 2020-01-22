package twilightforest.structures.lichtower;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import twilightforest.TFFeature;

import java.util.Random;

public class ComponentTFTowerRoofGableForwards extends ComponentTFTowerRoof {

	public ComponentTFTowerRoofGableForwards() {
		super();
	}

	public ComponentTFTowerRoofGableForwards(TFFeature feature, int i, ComponentTFTowerWing wing) {
		super(feature, i, wing);

		// same facing
		this.setCoordBaseMode(wing.getCoordBaseMode());

		this.size = wing.size + 2; // assuming only square towers and roofs right now.
		this.height = size;

		// just hang out at the very top of the tower
		this.makeAttachedOverhangBB(wing);
	}

	/**
	 * Makes a pointy roof out of stuff
	 */
	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		BlockState birchSlab = Blocks.BIRCH_SLAB.getDefaultState();
		BlockState birchPlanks = Blocks.BIRCH_PLANKS.getDefaultState();

		int slopeChange = slopeChangeForSize(size);
		for (int y = 0; y <= height; y++) {
			int min, max;
			if (y < slopeChange) {
				min = y;
				max = size - y - 1;
			} else {
				min = (y + slopeChange) / 2;
				max = size - ((y + slopeChange) / 2) - 1;
			}
			for (int x = 0; x <= size - 2; x++) {
				for (int z = min; z <= max; z++) {
					if (z == min || z == max) {
						setBlockState(world, birchPlanks, x, y, z, sbb);
					} else if (x < size - 2) {
						setBlockState(world, birchPlanks, x, y, z, sbb);
					}
				}
			}
		}

		// put on the little figurehead-like "cap"

		// where is even the top of our roof?
		int top = (size + 1) - slopeChange;
		int zMid = size / 2;

		setBlockState(world, birchSlab.with(SlabBlock.TYPE, SlabType.TOP), size - 1, top - 1, zMid, sbb);
		setBlockState(world, birchSlab, 0, top, zMid, sbb);
		setBlockState(world, birchSlab, size - 3, top, zMid, sbb);
		setBlockState(world, birchPlanks, size - 2, top, zMid, sbb);
		setBlockState(world, birchPlanks, size - 1, top, zMid, sbb);
		setBlockState(world, birchPlanks, size - 1, top + 1, zMid, sbb);

		return true;
	}

	public int slopeChangeForSize(int pSize) {
		if (size > 10) {
			return 3;
		} else if (size > 6) {
			return 2;
		} else {
			return 1;
		}
	}
}
