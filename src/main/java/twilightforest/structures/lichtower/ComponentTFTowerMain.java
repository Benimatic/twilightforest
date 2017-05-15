package twilightforest.structures.lichtower;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;
import twilightforest.entity.TFCreatures;
import twilightforest.structures.StructureTFComponent;


public class ComponentTFTowerMain extends ComponentTFTowerWing {

	public ComponentTFTowerMain() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFTowerMain(World world, Random rand, int index, int x, int y, int z) {
		// some of these are subject to change if the ground level is > 30.
		super(index, x, y, z, 15, 55 + rand.nextInt(32), 0);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void buildComponent(StructureComponent parent, List list, Random rand) {
		// add a roof?
		makeARoof(parent, list, rand);

		// sub towers
		for (int i = 0; i < 4; i++) {
			int[] dest = getValidOpening(rand, i);
			
			// adjust height if we're too low at this point
			if (dest[1] < height / 2) {
				dest[1] += 20;
			}
			
			int childHeight = Math.min(21 + rand.nextInt(10), this.height - dest[1] - 3);
			
			if (!makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 9, childHeight, i)) {
				makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 7, childHeight, i);
			}
		}

		// try one more time for large towers
		for (int i = 0; i < 4; i++) {
			int[] dest = getValidOpening(rand, i);
			
			// adjust height if we're too low at this point
			if (dest[1] < height / 2) {
				dest[1] += 10;
			}
			
			int childHeight = Math.min(21 + rand.nextInt(10), this.height - dest[1] - 3);
			
			if (!makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 9, childHeight, i)) {
				makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 7, childHeight, i);
			}
		}

		// another set, if possible
		for (int i = 0; i < 4; i++) {
			int[] dest = getValidOpening(rand, i);
			
			int childHeight = Math.min(7 + rand.nextInt(6), this.height - dest[1] - 3);
			
			if (!makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 5, childHeight, i)) {
				makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 3, childHeight, i);
			}
		}
		
		// outbuildings
		for (int i = 0; i < 4; i++) {
			int[] dest = getOutbuildingOpening(rand, i);
			
			int childHeight = 11 + rand.nextInt(10);
			int childSize = 7 + (rand.nextInt(2) * 2);
			
			makeTowerOutbuilding(list, rand, 1, dest[0], dest[1], dest[2], childSize, childHeight, i);
		}
		
		// TINY TOWERS!
		for (int i = 0; i < 16; i++) {
			int[] dest = getValidOpening(rand, i % 4);
			int childHeight = 6 + rand.nextInt(5);
			if (rand.nextInt(3) == 0 || !makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 5, childHeight, i % 4)) {
				makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], 3, childHeight, i % 4);
			}
		}
		
	}
	
	/**
	 * Gets a random position in the specified direction that we can make an outbuilding at
	 */
	public int[] getOutbuildingOpening(Random rand, int rotation) {
		
		int rx = 0;
		int ry = 1;
		int rz = 0;
		
		switch (rotation) {
		case 0:
			// for directions 0 or 2, the wall lies along the z axis
			rx = size -1;
			rz = 6 + rand.nextInt(8);
			break;
		case 1:
			// for directions 1 or 3, the wall lies along the x axis
			rx = 1 + rand.nextInt(11);
			rz = size - 1;
			break;
		case 2:
			rx = 0;
			rz = 1 + rand.nextInt(8);
			break;
		case 3:
			rx = 3 + rand.nextInt(11);
			rz = 0;
			break;
		}

		return new int[] {rx, ry, rz};
	}

	
	public boolean makeTowerOutbuilding(List<StructureComponent> list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, int rotation) {
		EnumFacing direction = getStructureRelativeRotation(rotation);
		int[] dx = offsetTowerCoords(x, y, z, wingSize, direction);
		ComponentTFTowerOutbuilding outbuilding = new ComponentTFTowerOutbuilding(index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);
		// check to see if it intersects something already there
		StructureComponent intersect = StructureComponent.findIntersecting(list, outbuilding.getBoundingBox());
		if (intersect == null || intersect == this) {
			list.add(outbuilding);
			outbuilding.buildComponent(this, list, rand);
			addOpening(x, y, z, rotation);
			return true;
		} else {
//			System.out.println("Planned outbuilding intersects with " + intersect);
			return false;
		}
	}


	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		// make walls
		fillWithRandomizedBlocks(world, sbb, 0, 0, 0, size - 1, height - 1, size - 1, false, rand, StructureTFComponent.getStrongholdStones());
		
		// clear inside
		fillWithAir(world, sbb, 1, 1, 1, size - 2, height - 2, size - 2);
		
		// stone to ground
		for (int x = 0; x < this.size; x++)
		{
			for (int z = 0; z < this.size; z++)
			{
				this.func_151554_b(world, Blocks.COBBLESTONE, 0, x, -1, z, sbb);
			}

		}
		
     	// nullify sky light
		nullifySkyLightForBoundingBox(world);
     	
     	// fix highestOpening parameter so we don't get a ginormous lich room
     	if ((height - highestOpening) > 15) {
     		highestOpening = height - 15;
     	}
     	

		// stairs!
		makeStairs(world, rand, sbb);

		// throw a bunch of opening markers in there
//      makeOpeningMarkers(world, rand, 100, sbb);

		// openings
		makeOpenings(world, sbb);

        // decorate?
		decorateStairFloor(world, rand, sbb);
		
		// stairway crossings
		makeStairwayCrossings(world, rand, sbb);
		
		// LICH TIME
		makeLichRoom(world, rand, sbb);
		
		// extra paintings in main tower
		makeTowerPaintings(world, rand, sbb);



		return true;
	}

	/**
	 * Make 1-2 platforms joining the stairways
	 */
	protected void makeStairwayCrossings(World world, Random rand, StructureBoundingBox sbb) {
		int flights = (this.highestOpening / 5) - 2;
		
		for (int i = 2 + rand.nextInt(2); i < flights; i += 1 + rand.nextInt(5)) {
			makeStairCrossing(world, rand, i, sbb);
		}
	}

	protected void makeStairCrossing(World world, Random rand, int flight, StructureBoundingBox sbb) {
		int temp = this.getCoordBaseMode();
		if (flight % 2 == 0) {
			this.setCoordBaseMode((getCoordBaseMode() + 1) % 4);
		}
		
		int floorMeta = rand.nextInt(2) == 0 ? 0 : 2;
		int floorLevel = 0 + flight * 5;
		
		// place platform
		for (int dx = 6; dx <= 8; dx++) {
			for (int dz = 4; dz <= 10; dz++) {
				setBlockState(world, Blocks.DOUBLE_STONE_SLAB, floorMeta, dx, floorLevel, dz, sbb);
			}
		}
		
		// put fences
		floorLevel++;
		int dx = 6;
		for (int dz = 3; dz <= 11; dz++) {
			setBlockState(world, Blocks.FENCE, 0, dx, floorLevel, dz, sbb);
		}
		dx++;
		for (int dz = 3; dz <= 11; dz++) {
			setBlockState(world, AIR, dx, floorLevel, dz, sbb);
		}
		dx++;
		for (int dz = 3; dz <= 11; dz++) {
			setBlockState(world, Blocks.FENCE, 0, dx, floorLevel, dz, sbb);
		}

		// we need 2 extra blocks and 2 extra fences to look good
		setBlockState(world, Blocks.DOUBLE_STONE_SLAB, floorMeta, 6, floorLevel - 1, 11, sbb);
		setBlockState(world, Blocks.DOUBLE_STONE_SLAB, floorMeta, 8, floorLevel - 1, 3, sbb);
		
		setBlockState(world, Blocks.FENCE, 0, 5, floorLevel, 11, sbb);
		setBlockState(world, Blocks.FENCE, 0, 9, floorLevel, 3, sbb);
		
		// place spawner in the middle
		String mobID = "Skeleton";
		switch (rand.nextInt(4))
		{
		case 0:
		case 1:
			mobID = "Skeleton";
			break;
		case 2:
			mobID = "Zombie";
			break;
		case 3:
			mobID = TFCreatures.getSpawnerNameFor("Swarm Spider");
			break;
		}
		setSpawner(world, rand, 7, floorLevel + 2, 7, mobID, sbb);
		
		// make a fence arch support for the spawner
		setBlockState(world, Blocks.FENCE, 0, 6, floorLevel + 1, 7, sbb);
		setBlockState(world, Blocks.FENCE, 0, 8, floorLevel + 1, 7, sbb);
		setBlockState(world, Blocks.FENCE, 0, 6, floorLevel + 2, 7, sbb);
		setBlockState(world, Blocks.FENCE, 0, 8, floorLevel + 2, 7, sbb);
		
		
		this.setCoordBaseMode(temp);
	}

	/**
	 * Make a neat little room for the lich to fight in
	 */
	protected void makeLichRoom(World world, Random rand, StructureBoundingBox sbb) {
		// figure out where the stairs end
		int floorLevel = 2 + (this.highestOpening / 5) * 5;
		// we need a floor
		makeLichFloor(world, floorLevel, (this.highestOpening / 5) % 2,  sbb);
		
		// now a chandelier
		decorateLichChandelier(world, floorLevel, sbb);
		
		// make paintings
		decoratePaintings(world, rand, floorLevel, sbb);
		
		// and wall torches
		decorateTorches(world, rand, floorLevel, sbb);
		
		// seems like we should have a spawner
		setBlockState(world, TFBlocks.bossSpawner, 1, size / 2, floorLevel + 2, size / 2, sbb);
	}


	protected void makeTowerPaintings(World world, Random rand, StructureBoundingBox sbb) {
		int howMany = 10;

		// do wall 0.
		generatePaintingsOnWall(world, rand, howMany, 0, 0, 48, sbb);
		generatePaintingsOnWall(world, rand, howMany, 0, 0, 32, sbb);
		generatePaintingsOnWall(world, rand, howMany, 0, 0, 0, sbb);
	
		// do wall 1.
		generatePaintingsOnWall(world, rand, howMany, 0, 1, 48, sbb);
		generatePaintingsOnWall(world, rand, howMany, 0, 1, 32, sbb);
		generatePaintingsOnWall(world, rand, howMany, 0, 1, 0, sbb);
		// do wall 2.
		generatePaintingsOnWall(world, rand, howMany, 0, 2, 48, sbb);
		generatePaintingsOnWall(world, rand, howMany, 0, 2, 32, sbb);
		generatePaintingsOnWall(world, rand, howMany, 0, 2, 0, sbb);
		// do wall 3.
		generatePaintingsOnWall(world, rand, howMany, 0, 3, 48, sbb);
		generatePaintingsOnWall(world, rand, howMany, 0, 3, 32, sbb);
		generatePaintingsOnWall(world, rand, howMany, 0, 3, 0, sbb);
	}

	/**
	 * Make the floor for the liches room
	 */
	protected void makeLichFloor(World world, int floorLevel, int rotation, StructureBoundingBox sbb) {
		int temp = this.getCoordBaseMode();
		this.setCoordBaseMode((this.getCoordBaseMode() + rotation) % 4);

		// place a platform there
		for (int fx = 1; fx < 14; fx++) {
			for (int fz = 1; fz < 14; fz++) {
				if ((fx == 1 || fx == 2) && (fz >= 6 && fz <= 12)) {
					// blank, leave room for stairs
					if (fz == 6) {
						// upside down plank slabs
						setBlockState(world, Blocks.WOODEN_SLAB, 10, fx, floorLevel, fz, sbb);
					}
				}
				else if ((fx == 12 || fx == 13) && (fz >= 3 && fz <= 8)) {
					// blank, leave room for stairs
					if (fz == 8) {
						// upside down plank slabs
						setBlockState(world, Blocks.WOODEN_SLAB, 10, fx, floorLevel, fz, sbb);
					}
				}
				else if ((fx >= 4 && fx <= 10) && (fz >= 4 && fz <= 10)) {
					// glass floor in center, aside from 2 corners
					if ((fx == 4 && fz == 4) || (fx == 10 && fz == 10)) {
						setBlockState(world, Blocks.PLANKS, 2, fx, floorLevel, fz, sbb);
					}
					else {
						setBlockState(world, Blocks.GLASS, 0, fx, floorLevel, fz, sbb);
					}
				}
				else if ((fx == 2 || fx == 3) && (fz == 2 || fz == 3)) {
					// glass blocks in the corners
					setBlockState(world, Blocks.GLASS, 0, fx, floorLevel, fz, sbb);
				}
				else if ((fx == 11 || fx == 12) && (fz == 11 || fz == 12)) {
					// glass blocks in the corners
					setBlockState(world, Blocks.GLASS, 0, fx, floorLevel, fz, sbb);
				}
				else {
					setBlockState(world, Blocks.PLANKS, 2, fx, floorLevel, fz, sbb);
				}
			}
		}
		
		// eliminate the railings
		setBlockState(world, AIR, 3, floorLevel + 1, 11, sbb);
		setBlockState(world, AIR, 3, floorLevel + 1, 10, sbb);
		setBlockState(world, AIR, 3, floorLevel + 2, 11, sbb);
		
		setBlockState(world, AIR, 11, floorLevel + 1, 3, sbb);
		setBlockState(world, AIR, 11, floorLevel + 1, 4, sbb);
		setBlockState(world, AIR, 11, floorLevel + 2, 3, sbb);
		
		this.setCoordBaseMode(temp);

	}
	
	/**
	 * Make a fancy chandelier for the lich's room
	 */
	protected void decorateLichChandelier(World world, int floorLevel, StructureBoundingBox sbb) {
		int cx = size / 2;
		int cy = floorLevel + 4;
		int cz = size / 2;

		setBlockState(world, Blocks.FENCE, 0, cx + 1, cy, cz + 0, sbb);
		setBlockState(world, Blocks.FENCE, 0, cx + 2, cy, cz + 0, sbb);
		setBlockState(world, Blocks.FENCE, 0, cx + 1, cy, cz + 1, sbb);
		setBlockState(world, Blocks.FENCE, 0, cx + 0, cy, cz + 1, sbb);
		setBlockState(world, Blocks.FENCE, 0, cx + 0, cy, cz + 2, sbb);
		setBlockState(world, Blocks.FENCE, 0, cx - 1, cy, cz + 1, sbb);
		setBlockState(world, Blocks.FENCE, 0, cx - 1, cy, cz + 0, sbb);
		setBlockState(world, Blocks.FENCE, 0, cx - 2, cy, cz + 0, sbb);
		setBlockState(world, Blocks.FENCE, 0, cx - 1, cy, cz - 1, sbb);
		setBlockState(world, Blocks.FENCE, 0, cx + 0, cy, cz - 1, sbb);
		setBlockState(world, Blocks.FENCE, 0, cx + 0, cy, cz - 2, sbb);
		setBlockState(world, Blocks.FENCE, 0, cx + 1, cy, cz - 1, sbb);
		
		cy++;
		setBlockState(world, Blocks.FENCE, 0, cx + 1, cy, cz + 0, sbb);
		setBlockState(world, Blocks.TORCH, 0, cx + 2, cy, cz + 0, sbb);
		setBlockState(world, Blocks.TORCH, 0, cx + 1, cy, cz + 1, sbb);
		setBlockState(world, Blocks.FENCE, 0, cx + 0, cy, cz + 1, sbb);
		setBlockState(world, Blocks.TORCH, 0, cx + 0, cy, cz + 2, sbb);
		setBlockState(world, Blocks.TORCH, 0, cx - 1, cy, cz + 1, sbb);
		setBlockState(world, Blocks.FENCE, 0, cx - 1, cy, cz + 0, sbb);
		setBlockState(world, Blocks.TORCH, 0, cx - 2, cy, cz + 0, sbb);
		setBlockState(world, Blocks.TORCH, 0, cx - 1, cy, cz - 1, sbb);
		setBlockState(world, Blocks.FENCE, 0, cx + 0, cy, cz - 1, sbb);
		setBlockState(world, Blocks.TORCH, 0, cx + 0, cy, cz - 2, sbb);
		setBlockState(world, Blocks.TORCH, 0, cx + 1, cy, cz - 1, sbb);
		
		cy++;
		setBlockState(world, Blocks.TORCH, 0, cx + 1, cy, cz + 0, sbb);
		setBlockState(world, Blocks.TORCH, 0, cx + 0, cy, cz + 1, sbb);
		setBlockState(world, Blocks.TORCH, 0, cx - 1, cy, cz + 0, sbb);
		setBlockState(world, Blocks.TORCH, 0, cx + 0, cy, cz - 1, sbb);
		
		for (int y = floorLevel + 5; y < height - 1; y++) {
			setBlockState(world, Blocks.FENCE, 0, cx + 0, y, cz + 0, sbb);
		}
	}

	/**
	 * Cover the walls in the lich's room with paintings.  How is this going to work, chunk by chunk?
	 */
	protected void decoratePaintings(World world, Random rand, int floorLevel, StructureBoundingBox sbb) {
		int howMany = 100;

		// do wall 0.
		generatePaintingsOnWall(world, rand, howMany, floorLevel, 0, 48, sbb);
		generatePaintingsOnWall(world, rand, howMany, floorLevel, 0, 32, sbb);
		generatePaintingsOnWall(world, rand, howMany, floorLevel, 0, 0, sbb);

		// do wall 1.
		generatePaintingsOnWall(world, rand, howMany, floorLevel, 1, 48, sbb);
		generatePaintingsOnWall(world, rand, howMany, floorLevel, 1, 32, sbb);
		generatePaintingsOnWall(world, rand, howMany, floorLevel, 1, 0, sbb);
		// do wall 2.
		generatePaintingsOnWall(world, rand, howMany, floorLevel, 2, 48, sbb);
		generatePaintingsOnWall(world, rand, howMany, floorLevel, 2, 32, sbb);
		generatePaintingsOnWall(world, rand, howMany, floorLevel, 2, 0, sbb);
		// do wall 3.
		generatePaintingsOnWall(world, rand, howMany, floorLevel, 3, 48, sbb);
		generatePaintingsOnWall(world, rand, howMany, floorLevel, 3, 32, sbb);
		generatePaintingsOnWall(world, rand, howMany, floorLevel, 3, 0, sbb);

	}
	
	/**
	 * Put torches on each wall
	 */
	protected void decorateTorches(World world, Random rand, int floorLevel, StructureBoundingBox sbb) {
		generateTorchesOnWall(world, rand, floorLevel, 0, sbb);
		generateTorchesOnWall(world, rand, floorLevel, 1, sbb);
		generateTorchesOnWall(world, rand, floorLevel, 2, sbb);
		generateTorchesOnWall(world, rand, floorLevel, 3, sbb);
	}

	/**
	 * Place up to 10 torches (with fence holders) on the wall, checking that they don't overlap any paintings or other torches
	 */
	protected void generateTorchesOnWall(World world, Random rand,
			int floorLevel, int direction, StructureBoundingBox sbb) {
		for (int i = 0; i < 10; i++) {
			// get some random coordinates on the wall in the chunk
			BlockPos wCoords = getRandomWallSpot(rand, floorLevel, direction, sbb);

			// offset to see where the fence should be
			BlockPos tCoords = new BlockPos(wCoords);
			if (direction == 0) {
				tCoords.posZ++;
			}
			if (direction == 1) {
				tCoords.posX--;
			}
			if (direction == 2) {
				tCoords.posZ--;
			}
			if (direction == 3) {
				tCoords.posX++;
			}


			// is there a painting or another torch there?
			AxisAlignedBB torchBox = new AxisAlignedBB(tCoords.posX, tCoords.posY, tCoords.posZ, tCoords.posX + 1.0, tCoords.posY + 2.0, tCoords.posZ + 1.0);
	        if (world.getBlock(tCoords.posX, tCoords.posY, tCoords.posZ) == Blocks.AIR && world.getBlock(tCoords.posX, tCoords.posY + 1, tCoords.posZ) == Blocks.AIR && world.getEntitiesWithinAABBExcludingEntity(null, torchBox).size() == 0)
	        {
	        	// if not, place a torch
				world.setBlock(tCoords.posX, tCoords.posY, tCoords.posZ, Blocks.FENCE, 0, 2);
				world.setBlock(tCoords.posX, tCoords.posY + 1, tCoords.posZ, Blocks.TORCH, 5, 2);
			}
		}
	}



}
