package twilightforest.structures.lichtower;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;

import java.util.Random;

public class TowerRoofSlabComponent extends TowerRoofComponent {

	public TowerRoofSlabComponent(ServerLevel level, CompoundTag nbt) {
		super(LichTowerPieces.TFLTRS, nbt);
	}

	public TowerRoofSlabComponent(StructurePieceType piece, CompoundTag nbt) {
		super(piece, nbt);
	}

	public TowerRoofSlabComponent(StructurePieceType piece, TFFeature feature, int i, TowerWingComponent wing) {
		super(piece, feature, i);

		// same alignment
		this.setOrientation(wing.getOrientation());
		// same size
		this.size = wing.size; // assuming only square towers and roofs right now.
		this.height = size / 2;

		// just hang out at the very top of the tower
		makeCapBB(wing);
	}

	/**
	 * Makes a flat, pyramid-shaped roof
	 */
	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		return makePyramidCap(world, Blocks.BIRCH_SLAB.defaultBlockState(), Blocks.BIRCH_PLANKS.defaultBlockState(), sbb);
	}

	protected boolean makePyramidCap(WorldGenLevel world, BlockState slabType, BlockState woodType, BoundingBox sbb) {
		for (int y = 0; y <= height; y++) {
			int min = 2 * y;
			int max = size - (2 * y) - 1;
			for (int x = min; x <= max; x++) {
				for (int z = min; z <= max; z++) {
					if (x == min || x == max || z == min || z == max) {

						placeBlock(world, slabType, x, y, z, sbb);
					} else {
						placeBlock(world, woodType, x, y, z, sbb);
					}
				}
			}
		}
		return true;
	}

	protected boolean makeConnectedCap(WorldGenLevel world, BlockState slabType, BlockState woodType, BoundingBox sbb) {
		for (int y = 0; y < height; y++) {
			int min = 2 * y;
			int max = size - (2 * y) - 1;
			for (int x = 0; x <= max; x++) {
				for (int z = min; z <= max; z++) {
					if (x == max || z == min || z == max) {
						placeBlock(world, slabType, x, y, z, sbb);
					} else {
						placeBlock(world, woodType, x, y, z, sbb);
					}
				}
			}
		}
		return true;
	}
}
