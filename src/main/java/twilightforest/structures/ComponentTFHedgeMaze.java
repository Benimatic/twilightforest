package twilightforest.structures;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.TFTreasure;
import twilightforest.block.TFBlocks;
import twilightforest.entity.TFCreatures;



public class ComponentTFHedgeMaze extends StructureTFComponent {
	
	static int MSIZE = 16;
	static int RADIUS = (MSIZE / 2 * 3) + 1;
	static int DIAMETER = 2 * RADIUS;
	
	static int FLOOR_LEVEL = 3;

	public ComponentTFHedgeMaze()
	{
		;
	}

	public ComponentTFHedgeMaze(World world, Random rand, int i, int x, int y, int z) {
		super(i);
		
		this.setCoordBaseMode(0);
		
		// the maze is 50 x 50 for now
		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, -RADIUS, -3, -RADIUS, RADIUS * 2, 10, RADIUS * 2, 0);

	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		
		TFMaze maze = new TFMaze(MSIZE, MSIZE);
		
		maze.oddBias = 2;
		maze.torchBlockID = TFBlocks.firefly;
		maze.wallBlockID = TFBlocks.hedge;
		maze.wallBlockMeta = 0;
		maze.type = 4;
		maze.tall = 3;
		maze.roots = 3;
		
		// set the seed to a fixed value based on this maze's x and z
		maze.setSeed(world.getSeed() + this.boundingBox.minX * this.boundingBox.minZ);

		// just add grass below the maze for now
		// grass underneath
		for (int fx = 0; fx <= DIAMETER; fx++) {
			for (int fz = 0; fz <= DIAMETER; fz++) {
				placeBlockAtCurrentPosition(world, Blocks.grass, 0, fx, FLOOR_LEVEL - 1, fz, sbb);
			}
		}

		// plunk down some jack-o-lanterns outside for decoration
		placeBlockAtCurrentPosition(world, Blocks.lit_pumpkin, 1, 0, FLOOR_LEVEL, 24, sbb);
		placeBlockAtCurrentPosition(world, Blocks.lit_pumpkin, 1, 0, FLOOR_LEVEL, 29, sbb);
		placeBlockAtCurrentPosition(world, Blocks.lit_pumpkin, 3, 50, FLOOR_LEVEL, 24, sbb);
		placeBlockAtCurrentPosition(world, Blocks.lit_pumpkin, 3, 50, FLOOR_LEVEL, 29, sbb);
		
		placeBlockAtCurrentPosition(world, Blocks.lit_pumpkin, 2, 24, FLOOR_LEVEL, 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.lit_pumpkin, 2, 29, FLOOR_LEVEL, 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.lit_pumpkin, 0, 24, FLOOR_LEVEL, 50, sbb);
		placeBlockAtCurrentPosition(world, Blocks.lit_pumpkin, 0, 29, FLOOR_LEVEL, 50, sbb);
		
		
		int nrooms = MSIZE / 3;
		int rcoords[] = new int[nrooms * 2];

		for (int i = 0; i < nrooms; i++)
		{
			int rx, rz;
			do {
				rx = maze.rand.nextInt(MSIZE - 2) + 1;
				rz = maze.rand.nextInt(MSIZE - 2) + 1;
			} while(isNearRoom(rx, rz, rcoords));

			maze.carveRoom1(rx, rz);

			rcoords[i * 2] = rx;
			rcoords[i * 2 + 1] = rz;
		}
		
		maze.generateRecursiveBacktracker(0, 0);
		
		maze.add4Exits();
		
		maze.copyToStructure(world, 1, FLOOR_LEVEL, 1, this, sbb);
		
		decorate3x3Rooms(world, rcoords, sbb);
		
		
		
		return true;
	}
	
	/**
	 * @return true if the specified dx and dz are within 3 of a room specified in rcoords
	 */
	protected boolean isNearRoom(int dx, int dz, int[] rcoords) {
		// if proposed coordinates are covering the origin, return true to stop the room from causing the maze to fail
		if (dx == 1 && dz == 1) {
			return true;
		}
		
		for (int i = 0; i < rcoords.length / 2; i++)
		{
			int rx = rcoords[i * 2];
			int rz = rcoords[i * 2 + 1];
			
			if (rx == 0 && rz == 0) {
				continue;
			}
			
			if (Math.abs(dx - rx) < 3 && Math.abs(dz - rz) < 3) {
				return true;
			}
		}
		return false;
	}


	/**
	 * 
	 * @param rand 
	 * @param world 
	 * @param rcoords
	 * @param sbb 
	 */
	void decorate3x3Rooms(World world, int[] rcoords, StructureBoundingBox sbb)
	{
		for (int i = 0; i < rcoords.length / 2; i++)
		{
			int dx = rcoords[i * 2];
			int dz = rcoords[i * 2 + 1];
			
			// MAGIC NUMBERS!!! convert the maze coordinates into coordinates for our structure
			dx = dx * 3 + 3;
			dz = dz * 3 + 3;

			decorate3x3Room(world, dx, dz, sbb);
		}
	}

	/**
	 * Decorates a room in the maze.  Makes assumptions that the room is 3x3 cells and thus 11x11 blocks large.
	 * @param rand 
	 * @param world 
	 * @param sbb 
	 * 
	 * @param dx
	 * @param dy
	 */
	void decorate3x3Room(World world, int x, int z, StructureBoundingBox sbb)
	{
		// make a new RNG for this room!
		Random roomRNG = new org.bogdang.modifications.random.XSTR(world.getSeed() ^ x + z);
		
		// a few jack-o-lanterns
		roomJackO(world, roomRNG, x, z, 8, sbb);
		if(roomRNG.nextInt(4) == 0) {
			roomJackO(world, roomRNG, x, z, 8, sbb);
		}

		// all rooms should have 1 spawner
		roomSpawner(world, roomRNG, x, z, 8, sbb);

		// and 1-2 chests
		roomTreasure(world, roomRNG, x, z, 8, sbb);
		if (roomRNG.nextInt(4) == 0) {
			roomTreasure(world, roomRNG, x, z, 8, sbb);
		}
	}

	/**
	 * Place a spawner within diameter / 2 squares of the specified x and z coordinates
	 */
	private void roomSpawner(World world, Random rand, int x, int z, int diameter, StructureBoundingBox sbb) {
		int rx = x + rand.nextInt(diameter) - (diameter / 2);
		int rz = z + rand.nextInt(diameter) - (diameter / 2);

		String mobID; 

		switch (rand.nextInt(3)) {
		case 1 :
			mobID = TFCreatures.getSpawnerNameFor("Swarm Spider");
			break;
		case 2 :
			mobID = TFCreatures.getSpawnerNameFor("Hostile Wolf");
			break;
		case 0 : 
		default:
			mobID = TFCreatures.getSpawnerNameFor("Hedge Spider");
		}

		placeSpawnerAtCurrentPosition(world, rand, rx, FLOOR_LEVEL, rz, mobID, sbb);
	}

	/**
	 * Place a treasure chest within diameter / 2 squares of the specified x and z coordinates
	 */
	private void roomTreasure(World world, Random rand, int x, int z, int diameter, StructureBoundingBox sbb) {
		int rx = x + rand.nextInt(diameter) - (diameter / 2);
		int rz = z + rand.nextInt(diameter) - (diameter / 2);

		placeTreasureAtCurrentPosition(world, rand, rx, FLOOR_LEVEL, rz, TFTreasure.hedgemaze, sbb);
	}


	/**
	 * Place a lit pumpkin lantern within diameter / 2 squares of the specified x and z coordinates
	 */
	private void roomJackO(World world, Random rand, int x, int z, int diameter, StructureBoundingBox sbb) {
		int rx = x + rand.nextInt(diameter) - (diameter / 2);
		int rz = z + rand.nextInt(diameter) - (diameter / 2);

		placeBlockAtCurrentPosition(world, Blocks.lit_pumpkin, rand.nextInt(4), rx, FLOOR_LEVEL, rz, sbb);
	}


}
