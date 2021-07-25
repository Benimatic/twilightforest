package twilightforest.structures.minotaurmaze;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.structures.TFStructureComponentOld;

import java.util.List;
import java.util.Random;

/**
 * This component is the base for the maze/ruins combo feature.  There are village-like ruins above and a maze underneath.
 *
 * @author Ben
 */
public class MazeRuinsComponent extends TFStructureComponentOld {

	public MazeRuinsComponent(StructureManager manager, CompoundTag nbt) {
		super(MinotaurMazePieces.TFMMRuins, nbt);
	}

	public MazeRuinsComponent(TFFeature feature, int i, int x, int y, int z) {
		super(MinotaurMazePieces.TFMMRuins, feature, i);
		this.setOrientation(Direction.SOUTH);

		// I have no bounding box
		this.boundingBox = feature.getComponentToAddBoundingBox(x, y - 2, z, 0, 0, 0, 0, 0, 0, Direction.SOUTH);
	}

	/**
	 * Initiates construction of the Structure Component picked, at the current Location of StructGen
	 */
	@Override
	public void addChildren(StructurePiece structurecomponent, List<StructurePiece> list, Random random) {
		super.addChildren(structurecomponent, list, random);

		// add a maze
		MinotaurMazeComponent maze = new MinotaurMazeComponent(getFeatureType(), 1, boundingBox.x0, boundingBox.y0 - 14, boundingBox.z0, 1);
		list.add(maze);
		maze.addChildren(this, list, random);

		// add maze entrance shaft
		MazeEntranceShaftComponent mazeEnter = new MazeEntranceShaftComponent(getFeatureType(), 2, random, boundingBox.x0 + 1, boundingBox.y0, boundingBox.z0 + 1);
		list.add(mazeEnter);
		mazeEnter.addChildren(this, list, random);

		// add aboveground maze entrance building
		MazeMoundComponent mazeAbove = new MazeMoundComponent(getFeatureType(), 2, random, boundingBox.x0 - 14, boundingBox.y0, boundingBox.z0 - 14);
		list.add(mazeAbove);
		mazeAbove.addChildren(this, list, random);
	}

	/**
	 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
	 * the end, it adds Fences...
	 */
	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		// I have no components
		return true;
	}
}
