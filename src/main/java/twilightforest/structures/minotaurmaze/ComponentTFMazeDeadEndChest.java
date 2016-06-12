package twilightforest.structures.minotaurmaze;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.TFTreasure;
import twilightforest.block.TFBlocks;

public class ComponentTFMazeDeadEndChest extends ComponentTFMazeDeadEnd {

	public ComponentTFMazeDeadEndChest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFMazeDeadEndChest(int i, int x, int y, int z, int rotation) {
		super(i, x, y, z, rotation);
		
		// specify a non-existant high spawn list value to stop actual monster spawns
		this.spawnListIndex = Integer.MAX_VALUE;
	}
	
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {		
		//super.addComponentParts(world, rand, sbb);
		
		// dais
		this.placeBlockAtCurrentPosition(world, Blocks.PLANKS, 0, 2, 1, 4, sbb);
		this.placeBlockAtCurrentPosition(world, Blocks.PLANKS, 0, 3, 1, 4, sbb);
		this.placeBlockAtCurrentPosition(world, Blocks.OAK_STAIRS, getStairMeta(1), 2, 1, 3, sbb);
		this.placeBlockAtCurrentPosition(world, Blocks.OAK_STAIRS, getStairMeta(1), 3, 1, 3, sbb);
		
		// chest
		this.placeBlockAtCurrentPosition(world, Blocks.CHEST, 0, 2, 2, 4, sbb);
		this.placeTreasureAtCurrentPosition(world, rand, 3, 2, 4, TFTreasure.labyrinth_deadend, sbb);
		
//		// torches
//		this.placeBlockAtCurrentPosition(world, Blocks.TORCH, 0, 1, 3, 4, sbb);
//		this.placeBlockAtCurrentPosition(world, Blocks.TORCH, 0, 4, 3, 4, sbb);
		
		// doorway w/ bars
		this.fillWithMetadataBlocks(world, sbb, 1, 1, 0, 4, 3, 1, TFBlocks.mazestone, 2, Blocks.AIR, 0, false);
		this.fillWithMetadataBlocks(world, sbb, 1, 4, 0, 4, 4, 1, TFBlocks.mazestone, 3, Blocks.AIR, 0, false);
		this.fillWithBlocks(world, sbb, 2, 1, 0, 3, 3, 1, Blocks.IRON_BARS, Blocks.AIR, false);
		
		return true;
	}

}
