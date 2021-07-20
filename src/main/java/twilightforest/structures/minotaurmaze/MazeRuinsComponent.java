package twilightforest.structures.minotaurmaze;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
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

	public MazeRuinsComponent(TemplateManager manager, CompoundNBT nbt) {
		super(MinotaurMazePieces.TFMMRuins, nbt);
	}

	public MazeRuinsComponent(TFFeature feature, int i, int x, int y, int z) {
		super(MinotaurMazePieces.TFMMRuins, feature, i);
		this.setCoordBaseMode(Direction.SOUTH);

		// I have no bounding box
		this.boundingBox = feature.getComponentToAddBoundingBox(x, y - 2, z, 0, 0, 0, 0, 0, 0, Direction.SOUTH);
	}

	/**
	 * Initiates construction of the Structure Component picked, at the current Location of StructGen
	 */
	@Override
	public void buildComponent(StructurePiece structurecomponent, List<StructurePiece> list, Random random) {
		super.buildComponent(structurecomponent, list, random);

		// add a maze
		MinotaurMazeComponent maze = new MinotaurMazeComponent(getFeatureType(), 1, boundingBox.minX, boundingBox.minY - 14, boundingBox.minZ, 1);
		list.add(maze);
		maze.buildComponent(this, list, random);

		// add maze entrance shaft
		MazeEntranceShaftComponent mazeEnter = new MazeEntranceShaftComponent(getFeatureType(), 2, random, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ + 1);
		list.add(mazeEnter);
		mazeEnter.buildComponent(this, list, random);

		// add aboveground maze entrance building
		MazeMoundComponent mazeAbove = new MazeMoundComponent(getFeatureType(), 2, random, boundingBox.minX - 14, boundingBox.minY, boundingBox.minZ - 14);
		list.add(mazeAbove);
		mazeAbove.buildComponent(this, list, random);
	}

	/**
	 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
	 * the end, it adds Fences...
	 */
	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		// I have no components
		return true;
	}
}
