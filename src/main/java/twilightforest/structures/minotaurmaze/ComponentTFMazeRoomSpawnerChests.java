package twilightforest.structures.minotaurmaze;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.TFTreasure;
import twilightforest.block.TFBlocks;
import twilightforest.entity.TFCreatures;

public class ComponentTFMazeRoomSpawnerChests extends ComponentTFMazeRoom {

	public ComponentTFMazeRoomSpawnerChests() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFMazeRoomSpawnerChests(int i, Random rand, int x, int y, int z) {
		super(i, rand, x, y, z);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		super.addComponentParts(world, rand, sbb);

		// 4 pillar enclosures
		placePillarEnclosure(world, sbb, 3, 3);
		placePillarEnclosure(world, sbb, 10, 3);
		placePillarEnclosure(world, sbb, 3, 10);
		placePillarEnclosure(world, sbb, 10, 10);

		// spawner
		placeSpawnerAtCurrentPosition(world, rand, 4, 2, 4, TFCreatures.getSpawnerNameFor("Minotaur"), sbb);
		
		// treasure
		this.placeTreasureAtCurrentPosition(world, rand, 4, 2, 11, TFTreasure.labyrinth_room, sbb);
		
		// treasure
		this.placeTreasureAtCurrentPosition(world, rand, 11, 2, 4, TFTreasure.labyrinth_room, sbb);
		
		// trap
		placeBlockAtCurrentPosition(world, Blocks.wooden_pressure_plate, 0, 11, 1, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.tnt, 0, 10, 0, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.tnt, 0, 11, 0, 10, sbb);
		placeBlockAtCurrentPosition(world, Blocks.tnt, 0, 11, 0, 12, sbb);
		placeBlockAtCurrentPosition(world, Blocks.tnt, 0, 12, 0, 11, sbb);

		return true;
	}

	private void placePillarEnclosure(World world, StructureBoundingBox sbb,
			int dx, int dz) {
		for (int y = 1; y < 5; y++)
		{
			placeBlockAtCurrentPosition(world, TFBlocks.mazestone, 2, dx + 0, y, dz + 0, sbb);
			placeBlockAtCurrentPosition(world, TFBlocks.mazestone, 2, dx + 2, y, dz + 0, sbb);
			placeBlockAtCurrentPosition(world, TFBlocks.mazestone, 2, dx + 0, y, dz + 2, sbb);
			placeBlockAtCurrentPosition(world, TFBlocks.mazestone, 2, dx + 2, y, dz + 2, sbb);
		}
		placeBlockAtCurrentPosition(world, Blocks.planks, 0, dx + 1, 1, dz + 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.planks, 0, dx + 1, 4, dz + 1, sbb);
		
		placeBlockAtCurrentPosition(world, Blocks.oak_stairs, getStairMeta(1), dx + 1, 1, dz + 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.oak_stairs, getStairMeta(0), dx + 0, 1, dz + 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.oak_stairs, getStairMeta(2), dx + 2, 1, dz + 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.oak_stairs, getStairMeta(3), dx + 1, 1, dz + 2, sbb);

		placeBlockAtCurrentPosition(world, Blocks.oak_stairs, getStairMeta(1) + 4, dx + 1, 4, dz + 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.oak_stairs, getStairMeta(0) + 4, dx + 0, 4, dz + 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.oak_stairs, getStairMeta(2) + 4, dx + 2, 4, dz + 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.oak_stairs, getStairMeta(3) + 4, dx + 1, 4, dz + 2, sbb);

		placeBlockAtCurrentPosition(world, Blocks.iron_bars, 0, dx + 1, 2, dz + 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.iron_bars, 0, dx + 0, 2, dz + 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.iron_bars, 0, dx + 2, 2, dz + 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.iron_bars, 0, dx + 1, 2, dz + 2, sbb);
		placeBlockAtCurrentPosition(world, Blocks.iron_bars, 0, dx + 1, 3, dz + 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.iron_bars, 0, dx + 0, 3, dz + 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.iron_bars, 0, dx + 2, 3, dz + 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.iron_bars, 0, dx + 1, 3, dz + 2, sbb);

		
	}

}
