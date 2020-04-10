package twilightforest.structures.lichtower;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;

import java.util.Random;

public class ComponentTFTowerRoofStairsOverhang extends ComponentTFTowerRoof {

	public ComponentTFTowerRoofStairsOverhang(TemplateManager manager, CompoundNBT nbt) {
		super(TFLichTowerPieces.TFLTRStO, nbt);
	}

	public ComponentTFTowerRoofStairsOverhang(TFFeature feature, int i, ComponentTFTowerWing wing) {
		super(TFLichTowerPieces.TFLTRStO, feature, i, wing);

		// always facing = 0.  This roof cannot rotate, due to stair facing issues.
		this.setCoordBaseMode(Direction.SOUTH);

		this.size = wing.size + 2; // assuming only square towers and roofs right now.
		this.height = size / 2;

		// just hang out at the very top of the tower
		this.boundingBox = new MutableBoundingBox(wing.getBoundingBox().minX - 1, wing.getBoundingBox().maxY, wing.getBoundingBox().minZ - 1, wing.getBoundingBox().maxX + 1, wing.getBoundingBox().maxY + this.height - 1, wing.getBoundingBox().maxZ + 1);
	}

	/**
	 * Makes a pyramid-shaped roof out of stairs
	 */
	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		BlockState woodenSlab = Blocks.BIRCH_SLAB.getDefaultState();
		BlockState woodenPlanks = Blocks.BIRCH_PLANKS.getDefaultState();

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
							setBlockState(world, woodenSlab, x, y, z, sbb);
						} else {
							setBlockState(world, birchStairsWest, x, y, z, sbb);
						}
					} else if (x == max) {
						if (z == min || z == max) {
							setBlockState(world, woodenSlab, x, y, z, sbb);
						} else {
							setBlockState(world, birchStairsEast, x, y, z, sbb);
						}
					} else if (z == max) {
						setBlockState(world, birchStairsSouth, x, y, z, sbb);
					} else if (z == min) {
						setBlockState(world, birchStairsNorth, x, y, z, sbb);
					} else {
						setBlockState(world, woodenPlanks, x, y, z, sbb);
					}
				}
			}
		}
		return true;
	}
}
