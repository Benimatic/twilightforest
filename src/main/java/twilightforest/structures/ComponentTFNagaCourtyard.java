package twilightforest.structures;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.block.TFBlocks;


public class ComponentTFNagaCourtyard extends StructureTFComponent {

	static int RADIUS = 46;
	static int DIAMETER = 2 * RADIUS + 1;

	
	public ComponentTFNagaCourtyard() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFNagaCourtyard(World world, Random rand, int i, int x, int y, int z) {
		super(i);
		
		this.setCoordBaseMode(0);
		
		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, -RADIUS, -1, -RADIUS, RADIUS * 2, 10, RADIUS * 2, 0);

	}

	/**
	 * At the moment, this just draws the whole courtyard as a giant 93x93 block every time.  We may break this up properly later.
	 * 
	 * TODO: what I just said
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		// add a broken up courtyard
		for (int fx = 0; fx <= DIAMETER; fx++) {
			for (int fz = 0; fz <= DIAMETER; fz++) {
				if (rand.nextInt(3) == 0) {
					placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, fx, 0, fz, sbb);
					
					// put some half slabs around
					if (rand.nextInt(20) == 0) {
						placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, fx, 1, fz, sbb);
					}
					else {
						placeBlockAtCurrentPosition(world, Blocks.air, 0, fx, 1, fz, sbb);  // clear out grass or flowers
					}
				}
				else {
					placeBlockAtCurrentPosition(world, Blocks.grass, 0, fx, 0, fz, sbb);
				}
			}
		}
		
		// fence!  except not a fence, more of a wall
		for (int fx = 0; fx <= DIAMETER; fx++) {
			randomBrick(world, rand, fx, 0, DIAMETER, sbb);
			randomBrick(world, rand, fx, 0, 0, sbb);
			randomBrick(world, rand, fx, 1, DIAMETER, sbb);
			randomBrick(world, rand, fx, 1, 0, sbb);
			randomBrick(world, rand, fx, 2, DIAMETER, sbb);
			randomBrick(world, rand, fx, 2, 0, sbb);
			randomBrick(world, rand, fx, 3, DIAMETER, sbb);
			randomBrick(world, rand, fx, 3, 0, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 5, fx, 4, DIAMETER, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 5, fx, 4, 0, sbb);


			// make nagastone pattern!
			switch (fx % 23)
			{
			case 2 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  7, fx, 3, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  7, fx, 3, 0, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, fx, 2, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, fx, 2, 0, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 11, fx, 1, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 11, fx, 1, 0, sbb);
				break;
			case 3 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  3, fx, 3, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  3, fx, 3, 0, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 12, fx, 1, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 12, fx, 1, 0, sbb);
				break;
			case 4 :
			case 8 :
			case 16 :
			case 20 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 12, fx, 1, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 12, fx, 1, 0, sbb);
				break;
			case 5 :
			case 9 :
			case 17 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  7, fx, 3, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  7, fx, 3, 0, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, fx, 2, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, fx, 2, 0, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 10, fx, 1, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 10, fx, 1, 0, sbb);
				break;
			case 6 :
			case 10 :
			case 14 :
			case 18 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 12, fx, 3, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 12, fx, 3, 0, sbb);
				break;
			case 7 :
			case 15 :
			case 19 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  6, fx, 3, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  6, fx, 3, 0, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, fx, 2, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, fx, 2, 0, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 11, fx, 1, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 11, fx, 1, 0, sbb);
				break;
			case 11 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  6, fx, 3, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  6, fx, 3, 0, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, fx, 2, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, fx, 2, 0, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, fx, 1, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, fx, 1, 0, sbb);
				break;
			case 13 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  7, fx, 3, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  7, fx, 3, 0, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, fx, 2, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, fx, 2, 0, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, fx, 1, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, fx, 1, 0, sbb);
				break;
			case 21 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  2, fx, 3, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  2, fx, 3, 0, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 12, fx, 1, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 12, fx, 1, 0, sbb);
				break;
			case 22 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  6, fx, 3, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  6, fx, 3, 0, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, fx, 2, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, fx, 2, 0, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 10, fx, 1, DIAMETER, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 10, fx, 1, 0, sbb);
				break;
			}
			
//			if (fx % 2 == 0) {
//				placeBlockAtCurrentPosition(world, Blocks.stone_slab, 5, fx, 4, DIAMETER, sbb);
//				placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, fx, 4, 0, sbb);
//			}
//			else
//			{
//				placeBlockAtCurrentPosition(world, Blocks.stone_slab, 5, fx, 4, 0, sbb);
//				placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, fx, 4, DIAMETER, sbb);
//			}
		}
		
		for (int fz = 0; fz <= DIAMETER; fz++) {
			randomBrick(world, rand, DIAMETER, 0, fz, sbb);
			randomBrick(world, rand, 0, 0, fz, sbb);
			randomBrick(world, rand, DIAMETER, 1, fz, sbb);
			randomBrick(world, rand, 0, 1, fz, sbb);
			randomBrick(world, rand, DIAMETER, 2, fz, sbb);
			randomBrick(world, rand, 0, 2, fz, sbb);
			randomBrick(world, rand, DIAMETER, 3, fz, sbb);
			randomBrick(world, rand, 0, 3, fz, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 5, DIAMETER, 4, fz, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 5, 0, 4, fz, sbb);

			// make nagastone pattern!
			switch (fz % 23)
			{
			case 2 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  5, DIAMETER, 3, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  5, 0, 3, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, DIAMETER, 2, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, 0, 2, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  9, DIAMETER, 1, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  9, 0, 1, fz, sbb);
				break;
			case 3 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  1, DIAMETER, 3, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  1, 0, 3, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 13, DIAMETER, 1, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 13, 0, 1, fz, sbb);
				break;
			case 4 :
			case 8 :
			case 16 :
			case 20 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 13, DIAMETER, 1, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 13, 0, 1, fz, sbb);
				break;
			case 5 :
			case 9 :
			case 17 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  5, DIAMETER, 3, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  5, 0, 3, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, DIAMETER, 2, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, 0, 2, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  8, DIAMETER, 1, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  8, 0, 1, fz, sbb);
				break;
			case 6 :
			case 10 :
			case 14 :
			case 18 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 13, DIAMETER, 3, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 13, 0, 3, fz, sbb);
				break;
			case 7 :
			case 15 :
			case 19 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  4, DIAMETER, 3, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  4, 0, 3, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, DIAMETER, 2, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, 0, 2, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  9, DIAMETER, 1, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  9, 0, 1, fz, sbb);
				break;
			case 11 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  4, DIAMETER, 3, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  4, 0, 3, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, DIAMETER, 2, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, 0, 2, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, DIAMETER, 1, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, 0, 1, fz, sbb);
				break;
			case 13 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  5, DIAMETER, 3, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  5, 0, 3, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, DIAMETER, 2, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, 0, 2, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, DIAMETER, 1, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, 0, 1, fz, sbb);
				break;
			case 21 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  0, DIAMETER, 3, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  0, 0, 3, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 13, DIAMETER, 1, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 13, 0, 1, fz, sbb);
				break;
			case 22 :
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  4, DIAMETER, 3, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  4, 0, 3, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, DIAMETER, 2, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone, 14, 0, 2, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  8, DIAMETER, 1, fz, sbb);
				placeBlockAtCurrentPosition(world, TFBlocks.nagastone,  8, 0, 1, fz, sbb);
				break;
			}
			
//			if (fz % 2 == 0) {
//				placeBlockAtCurrentPosition(world, Blocks.stone_slab, 5, DIAMETER, 4, fz, sbb);
//				placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, 0, 4, fz, sbb);
//			}
//			else
//			{
//				placeBlockAtCurrentPosition(world, Blocks.stone_slab, 5, 0, 4, fz, sbb);
//				placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, DIAMETER, 4, fz, sbb);
//			}
		}
		
		// make a new rand here because we keep getting different results and this actually matters... or should the pillars be different StructureComponents?
		Random pillarRand = new Random(world.getSeed() + this.boundingBox.minX * this.boundingBox.minZ);
		
        // pick a few spots and make pillars
        for (int i = 0; i < 20; i++) {
        	int rx = 2 + pillarRand.nextInt(DIAMETER - 4);
        	int rz = 2 + pillarRand.nextInt(DIAMETER - 4);
        	
        	makePillar(world, pillarRand, rx, 1, rz, sbb);
        }

        

		// naga spawner seems important
		placeBlockAtCurrentPosition(world, TFBlocks.bossSpawner, 0, RADIUS + 1, 2, RADIUS + 1, sbb);

		return true;
	}

	/**
	 * Make a pillar out of stone brick
	 */
	public boolean makePillar(World world, Random rand, int x, int y, int z, StructureBoundingBox sbb) {
		int height = 8;
		
		// make the base
		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, x - 1, y + 0, z - 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, x + 0, y + 0, z - 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, x + 1, y + 0, z - 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, x - 1, y + 0, z + 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, x + 1, y + 0, z + 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, x - 1, y + 0, z + 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, x + 0, y + 0, z + 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, x + 1, y + 0, z + 1, sbb);
		
		// make the pillar
		for (int i = 0; i < height; i++) {
			randomBrick(world, rand, x, y + i, z, sbb);
			if (i > 0 && rand.nextInt(2) == 0) {
				// vines?
				switch (rand.nextInt(4))
				{
				case 0:
					placeBlockAtCurrentPosition(world, Blocks.vine, 8, x - 1, y + i, z + 0, sbb);
					break;
				case 1:
					placeBlockAtCurrentPosition(world, Blocks.vine, 2, x + 1, y + i, z + 0, sbb);
					break;
				case 2:
					placeBlockAtCurrentPosition(world, Blocks.vine, 4, x + 0, y + i, z + 1, sbb);
					break;
				case 3:
					placeBlockAtCurrentPosition(world, Blocks.vine, 1, x + 0, y + i, z - 1, sbb);
					break;
				}
			}
			else if (i > 0 && rand.nextInt(4) == 0) {
				// fireflies!
				switch (rand.nextInt(4))
				{
				case 0:
					placeBlockAtCurrentPosition(world, TFBlocks.firefly, 0, x - 1, y + i, z + 0, sbb);
					break;
				case 1:
					placeBlockAtCurrentPosition(world, TFBlocks.firefly, 0, x + 1, y + i, z + 0, sbb);
					break;
				case 2:
					placeBlockAtCurrentPosition(world, TFBlocks.firefly, 0, x + 0, y + i, z + 1, sbb);
					break;
				case 3:
					placeBlockAtCurrentPosition(world, TFBlocks.firefly, 0, x + 0, y + i, z - 1, sbb);
					break;
				}
			}
		}
		
		// top?
		if (height == 8) {
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, x - 1, y + 8, z - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, x + 0, y + 8, z - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, x + 1, y + 8, z - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, x - 1, y + 8, z + 0, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 5, x + 0, y + 8, z + 0, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, x + 1, y + 8, z + 0, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, x - 1, y + 8, z + 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, x + 0, y + 8, z + 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, x + 1, y + 8, z + 1, sbb);
			
			
		}
		
		return true;
	}
	
	/**
	 * Places a random stone brick at the specified location
	 */
	public void randomBrick(World world, Random rand, int x, int y, int z, StructureBoundingBox sbb) {
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, rand.nextInt(3), x, y, z, sbb);
	}
	

}
