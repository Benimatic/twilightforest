package twilightforest.structures.minotaurmaze;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;
import twilightforest.structures.TFStructureComponentOld;
import twilightforest.world.TFGenerationSettings;

import java.util.List;
import java.util.Random;

public class MazeEntranceShaftComponent extends TFStructureComponentOld {

	public MazeEntranceShaftComponent(StructureManager manager, CompoundTag nbt) {
		super(MinotaurMazePieces.TFMMES, nbt);
	}

	private int averageGroundLevel = -1;

	public MazeEntranceShaftComponent(TFFeature feature, int i, Random rand, int x, int y, int z) {
		super(MinotaurMazePieces.TFMMES, feature, i);
		this.setOrientation(Direction.Plane.HORIZONTAL.getRandomDirection(rand));

		this.boundingBox = new BoundingBox(x, y, z, x + 6 - 1, y + 14, z + 6 - 1);
	}

	/**
	 * Initiates construction of the Structure Component picked, at the current Location of StructGen
	 */
	@Override
	public void addChildren(StructurePiece structurecomponent, List<StructurePiece> list, Random random) {
		// NO-OP
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		if (this.averageGroundLevel < 0) {
			this.averageGroundLevel = this.getAverageGroundLevel(world, generator, sbb);

			if (this.averageGroundLevel < 0) {
				return true;
			}

			this.boundingBox.y1 = this.averageGroundLevel;
			this.boundingBox.y0 = TFGenerationSettings.SEALEVEL - 10;
		}

		this.generateBox(world, sbb, 0, 0, 0, 5, this.boundingBox.getYSpan(), 5, TFBlocks.maze_stone_brick.get().defaultBlockState(), AIR, true);
		this.generateAirBox(world, sbb, 1, 0, 1, 4, this.boundingBox.getYSpan(), 4);

		return true;
	}

	/**
	 * Discover the y coordinate that will serve as the ground level of the supplied BoundingBox. (A median of all the
	 * levels in the BB's horizontal rectangle).
	 */
	@Override
	protected int getAverageGroundLevel(WorldGenLevel world, ChunkGenerator generator, BoundingBox boundingBox) {
		int yTotal = 0;
		int count = 0;

		for (int z = this.boundingBox.z0; z <= this.boundingBox.z1; ++z) {
			for (int x = this.boundingBox.x0; x <= this.boundingBox.x1; ++x) {
				BlockPos pos = new BlockPos(x, 64, z);
				if (boundingBox.isInside(pos)) {
					final BlockPos topBlock = world.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos);
					yTotal += Math.max(topBlock.getY(), generator.getSpawnHeight());
					++count;
				}
			}
		}

		if (count == 0) {
			return -1;
		} else {
			return yTotal / count;
		}
	}
}
