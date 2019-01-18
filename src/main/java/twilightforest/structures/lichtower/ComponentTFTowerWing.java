package twilightforest.structures.lichtower;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSlab;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityPainting.EnumArt;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.TFTreasure;
import twilightforest.block.TFBlocks;
import twilightforest.entity.TFCreatures;
import twilightforest.structures.StructureTFComponent;



public class ComponentTFTowerWing extends StructureTFComponent {
	
	public ComponentTFTowerWing() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int size;
	protected int height;
	protected Class<? extends ComponentTFTowerRoof> roofType;
	
	protected ArrayList<ChunkCoordinates> openings = new ArrayList<ChunkCoordinates>();
	protected int highestOpening;
	protected boolean [] openingTowards = new boolean[] {false, false, true, false};
	
	protected ComponentTFTowerWing(int i) {
		super(i);
		this.highestOpening = 0;
	}

	protected ComponentTFTowerWing(int i, int x, int y, int z, int pSize, int pHeight, int direction) {
		super(i);
		
		this.size = pSize;
		this.height = pHeight;
		this.setCoordBaseMode(direction);
		
		this.highestOpening = 0;

		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, size - 1, height - 1, size - 1, direction);
	}
	
	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		
        par1NBTTagCompound.setInteger("towerSize", this.size);
        par1NBTTagCompound.setInteger("towerHeight", this.height);
        
        par1NBTTagCompound.setIntArray("doorInts", this.getDoorsAsIntArray());
        
        par1NBTTagCompound.setInteger("highestOpening", this.highestOpening);
        par1NBTTagCompound.setBoolean("openingTowards0", this.openingTowards[0]);
        par1NBTTagCompound.setBoolean("openingTowards1", this.openingTowards[1]);
        par1NBTTagCompound.setBoolean("openingTowards2", this.openingTowards[2]);
        par1NBTTagCompound.setBoolean("openingTowards3", this.openingTowards[3]);

	}

	/**
	 * Turn the openings array into an array of ints.
	 */
	private int[] getDoorsAsIntArray() {
		IntBuffer ibuffer = IntBuffer.allocate(this.openings.size() * 3);
		
		for (ChunkCoordinates door : openings)
		{
			ibuffer.put(door.posX);
			ibuffer.put(door.posY);
			ibuffer.put(door.posZ);
		}
		
		return ibuffer.array();
	}

	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);
        this.size = par1NBTTagCompound.getInteger("towerSize");
        this.height = par1NBTTagCompound.getInteger("towerHeight");
        
        this.readOpeningsFromArray(par1NBTTagCompound.getIntArray("doorInts"));
        
        this.highestOpening = par1NBTTagCompound.getInteger("highestOpening");
        // too lazy to do this as a loop
        this.openingTowards[0] = par1NBTTagCompound.getBoolean("openingTowards0");
        this.openingTowards[1] = par1NBTTagCompound.getBoolean("openingTowards1");
        this.openingTowards[2] = par1NBTTagCompound.getBoolean("openingTowards2");
        this.openingTowards[3] = par1NBTTagCompound.getBoolean("openingTowards3");
	}

	/**
	 * Read in openings from int array
	 */
	private void readOpeningsFromArray(int[] intArray) {
		for (int i = 0; i < intArray.length; i += 3)
		{
			ChunkCoordinates door = new ChunkCoordinates(intArray[i], intArray[i + 1], intArray[i + 2]);
			
			this.openings.add(door);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void buildComponent(StructureComponent parent, List list, Random rand) {
		// we should have a door where we started
		addOpening(0, 1, size / 2, 2);

		// add a roof?
		makeARoof(parent, list, rand);
		
		// add a beard
		makeABeard(parent, list, rand);
		
		
		if (size > 4) {
			// sub towers
			for (int i = 0; i < 4; i++) {
				if (i == 2) {
					// no towers behind us
					continue;
				}
				int[] dest = getValidOpening(rand, i);
				if (!makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], size - 2, height - 4, i) && this.size > 8) {
					if (!makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], size - 4, height - 6, i)) {
						makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], size - 6, height - 12, i);
					}
				}
			}
		}
		
		
	}
	
	public boolean makeTowerWing(List<StructureComponent> list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, int rotation) {
		// kill too-small towers
		if (wingHeight < 6) {
			return false;
		}
		
		int direction = (getCoordBaseMode() + rotation) % 4;
		int[] dx = offsetTowerCoords(x, y, z, wingSize, direction);
		
		if (rand.nextInt(6) == 0) {
			return makeBridge(list, rand, index, x, y, z, wingSize, wingHeight, rotation);
			// or I don't know if we just want to continue instead if the bridge fails. 
			// I think there are very few circumstances where we can make a wing and not a bridge
		}
		
		ComponentTFTowerWing wing = new ComponentTFTowerWing(index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);
		// check to see if it intersects something already there
		StructureComponent intersect = StructureComponent.findIntersecting(list, wing.boundingBox);
		if (intersect == null || intersect == this) {
			list.add(wing);
			wing.buildComponent(this, list, rand);
			addOpening(x, y, z, rotation);
			return true;
		} else {
//			System.out.println("Planned wing intersects with " + intersect);
			if (rand.nextInt(3) > 0) {
				return makeBridge(list, rand, index, x, y, z, wingSize, wingHeight, rotation);
			}
			else {
				// I guess we're done for this branch
				return false;
			}
		}
	}



	protected boolean makeBridge(List<StructureComponent> list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, int rotation) {
		// bridges are size 3 always
		int direction = (getCoordBaseMode() + rotation) % 4;
		int[] dx = offsetTowerCoords(x, y, z, 3, direction);
		// adjust height for those stupid little things
		if (wingSize == 3 && wingHeight > 10) {
			wingHeight = 6 + rand.nextInt(5);
		}
		ComponentTFTowerBridge bridge = new ComponentTFTowerBridge(index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);
		// check to see if it intersects something already there
		StructureComponent intersect = StructureComponent.findIntersecting(list, bridge.boundingBox);
		if (intersect == null || intersect == this) {
			intersect = StructureComponent.findIntersecting(list, bridge.getWingBB());
		}
		else {
			return false;
		}
		// okay, I think we can actually make one, as long as we're not still intersecting something.
		if (intersect == null || intersect == this) {
			list.add(bridge);
			bridge.buildComponent(this, list, rand);
			addOpening(x, y, z, rotation);
			return true;
		}
		else {		
			return false;
		}
	}
	
	/**
	 * Add an opening to the outside (or another tower) in the specified direction.
	 */
	public void addOpening(int dx, int dy, int dz, int direction) {
		openingTowards[direction] = true;
		if (dy > highestOpening) {
			highestOpening = dy;
		}
		openings.add(new ChunkCoordinates(dx, dy, dz));
	}
	
	/**
	 * Add a beard to this structure.  There is only one type of beard.
	 */
	public void makeABeard(StructureComponent parent, List<StructureComponent> list, Random rand) {
		
		boolean attached = parent.getBoundingBox().minY < this.boundingBox.minY;
		
		int index = this.getComponentType();
		ComponentTFTowerBeard beard;
		if (attached) {
			beard = new ComponentTFTowerBeardAttached(index + 1, this);
		}
		else {
			beard = new ComponentTFTowerBeard(index + 1, this);
		}
		list.add(beard);
		beard.buildComponent(this, list, rand);
	}



	/**
	 * Attach a roof to this tower.
	 * 
	 * This function keeps trying roofs starting with the largest and fanciest, and then keeps trying smaller and plainer ones
	 */
	public void makeARoof(StructureComponent parent, List<StructureComponent> list, Random rand) {
		
		// we are attached if our parent is taller than we are
		boolean attached = parent.getBoundingBox().maxY > this.boundingBox.maxY;
		
		if (attached) {
			makeAttachedRoof(list, rand);
		}
		else {
			makeFreestandingRoof(list, rand);
		}

	}



	protected void makeAttachedRoof(List<StructureComponent> list, Random rand) {
		int index = this.getComponentType();
		ComponentTFTowerRoof roof;
		
		// this is our preferred roof type:
		if (roofType == null && rand.nextInt(32) != 0) {
			tryToFitRoof(list, rand, new ComponentTFTowerRoofGableForwards(index + 1, this));
		}
		
		// this is for roofs that don't fit.
		if (roofType == null && rand.nextInt(8) != 0) {
			tryToFitRoof(list, rand, new ComponentTFTowerRoofSlabForwards(index + 1, this));
		}
		
		// finally, if we're cramped for space, try this
		if (roofType == null && rand.nextInt(32) != 0) {
			// fall through to this next roof
			roof = new ComponentTFTowerRoofAttachedSlab(index + 1, this);
			tryToFitRoof(list, rand, roof);
		}
		
		// last resort
		if (roofType == null) {
			// fall through to this next roof
			roof = new ComponentTFTowerRoofFence(index + 1, this);
			tryToFitRoof(list, rand, roof);
		}
	}



	/**
	 * Check to see if this roof fits.  If it does:
	 * Add the specified roof to this tower and set the roofType variable.
	 * 
	 * @param list
	 * @param rand
	 * @param roof
	 */
	protected void tryToFitRoof(List<StructureComponent> list, Random rand, ComponentTFTowerRoof roof) {
		if (roof.fits(this, list, rand)) {
			list.add(roof);
			roof.buildComponent(this, list, rand);
			roofType = roof.getClass();
		}
	}

	protected void makeFreestandingRoof(List<StructureComponent> list, Random rand) {
		int index = this.getComponentType();
		ComponentTFTowerRoof roof;
		
		// most roofs that fit fancy roofs will be this
		if (roofType == null && rand.nextInt(8) != 0) {
			roof = new ComponentTFTowerRoofPointyOverhang(index + 1, this);
			tryToFitRoof(list, rand, roof);
		}
		
		// don't pass by this one if it fits
		if (roofType == null) {
			roof = new ComponentTFTowerRoofStairsOverhang(index + 1, this);
			tryToFitRoof(list, rand, roof);
		}
		
		// don't pass by this one if it fits
		if (roofType == null) {
			roof = new ComponentTFTowerRoofStairs(index + 1, this);
			tryToFitRoof(list, rand, roof);
		}
		
		if (roofType == null && rand.nextInt(53) != 0) {
			// fall through to this next roof
			roof = new ComponentTFTowerRoofSlab(index + 1, this);
			tryToFitRoof(list, rand, roof);
		}
		
		if (roofType == null) {
			// fall through to this next roof
			roof = new ComponentTFTowerRoofFence(index + 1, this);
			tryToFitRoof(list, rand, roof);
		}
	}




	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {

		// make walls
		fillWithRandomizedBlocks(world, sbb, 0, 0, 0, size - 1, height - 1, size - 1, false, rand, StructureTFComponent.getStrongholdStones());
		
		// clear inside
		fillWithAir(world, sbb, 1, 1, 1, size - 2, height - 2, size - 2);

		// sky light
		nullifySkyLightForBoundingBox(world);
        
        // marker blocks
//        placeBlockAtCurrentPosition(world, Blocks.wool, this.coordBaseMode, size / 2, 2, size / 2, sbb);
//        placeBlockAtCurrentPosition(world, Blocks.gold_block, 0, 0, 0, 0, sbb);
        
        // stairs!
        if (highestOpening > 1) {
        	makeStairs(world, rand, sbb);
        }
        
        // decorate?
     	decorateThisTower(world, rand, sbb);
        

        // windows
     	makeWindows(world, rand, sbb, size < 4);
        
        // throw a bunch of opening markers in there
//        makeOpeningMarkers(world, rand, 100, sbb);

        // openings
        makeOpenings(world, sbb);
        
//        // relight
//     	for (int lx = -1; lx < 3; lx++) {
//         	for (int lz = -1; lz < 3; lz++) {
//         		world.updateLightByType(EnumSkyBlock.Sky, sbb.minX + (lx * 16), sbb.maxY, sbb.minZ + (lx * 16));
//         	}
//     	}


		return true;
	}


	/**
	 * Puts some colorful markers by possible openings in this tower.  Debug only.
	 * 
	 * @param numMarkers How many markers to make
	 */
	protected void makeOpeningMarkers(World world, Random rand, int numMarkers, StructureBoundingBox sbb) {
		if (size > 4) {
	        for (int i = 0; i < numMarkers; i++) {
	        	int[] spot = getValidOpening(rand, 0);
	        	placeBlockAtCurrentPosition(world, Blocks.wool, 0, spot[0], spot[1], spot[2], sbb);
	        }
	        for (int i = 0; i < numMarkers; i++) {
	        	int[] spot = getValidOpening(rand, 1);
	        	placeBlockAtCurrentPosition(world, Blocks.wool, 1, spot[0], spot[1], spot[2], sbb);
	        }
	        for (int i = 0; i < numMarkers; i++) {
	        	int[] spot = getValidOpening(rand, 2);
	        	placeBlockAtCurrentPosition(world, Blocks.wool, 2, spot[0], spot[1], spot[2], sbb);
	        }
	        for (int i = 0; i < numMarkers; i++) {
	        	int[] spot = getValidOpening(rand, 3);
	        	placeBlockAtCurrentPosition(world, Blocks.wool, 3, spot[0], spot[1], spot[2], sbb);
	        }
        }
	}



	/**
	 * Add some appropriate decorations to this tower
	 * 
	 * @param world
	 * @param rand
	 * @param sbb
	 */
	protected void decorateThisTower(World world, Random rand, StructureBoundingBox sbb) {
		Random decoRNG = new org.bogdang.modifications.random.XSTR(world.getSeed() + (this.boundingBox.minX * 321534781) * (this.boundingBox.minZ * 756839));
		
		if (size > 3) {
			// only decorate towers with more than one available square inside.
			if (isDeadEnd()) {
				decorateDeadEnd(world, decoRNG, sbb);
			}
			else {
				// for now we'll just assume that any tower with more than one exit is a stair tower
				decorateStairTower(world, decoRNG, sbb);
			}
		}
	}
	

	/**
	 * Decorates a dead end tower.  These towers have no stairs, and will be the focus of our interior design.
	 */
	protected void decorateDeadEnd(World world, Random rand, StructureBoundingBox sbb) {
		int floors = (this.height - 1) / 5;

		// divide the tower into floors
		int floorHeight = this.height / floors;
		for (int i = 1; i < floors; i++) {
			// put down a floor
			for (int x = 1; x < size - 1; x++) {
				for (int z = 1; z < size - 1; z++) {
					placeBlockAtCurrentPosition(world, Blocks.planks, 2, x, (i * floorHeight), z, sbb);
				}
			}
		}
		
		
		if (floors > 1) {
			int ladderDir = 3;
			int downLadderDir = -1;

			// decorate bottom floor
			decorateFloor(world, rand, 0, 1, floorHeight, ladderDir, -1, sbb);

			// decorate middle floors
			for (int i = 1; i < floors - 1; i++) {
				int bottom = 1 + floorHeight * i;
				int top = floorHeight * (i + 1);

				downLadderDir = ladderDir;
				ladderDir++;
				ladderDir %= 4;

				decorateFloor(world, rand, i, bottom, top, ladderDir, downLadderDir, sbb);
			}

			// decorate top floor
			decorateFloor(world, rand, floors, 1 + floorHeight * (floors - 1), height - 1, -1, ladderDir, sbb);
		}
		else {
			// just one floor, decorate that, no ladders
			decorateFloor(world, rand, 0, 1, height - 1, -1, -1, sbb);
		}
	}
	
	/**
	 * Called to decorate each floor.  This is responsible for adding a ladder up, the stub of the ladder going down, then picking a theme for each floor and executing it.
	 * 
	 * @param floor
	 * @param bottom
	 * @param top
	 * @param ladderUpDir
	 * @param laddderDownDir
	 */
	protected void decorateFloor(World world, Random rand, int floor, int bottom, int top, int ladderUpDir, int ladderDownDir, StructureBoundingBox sbb) {
		
		if (ladderUpDir > -1) {
			// add ladder going up
			int meta = getLadderMeta(ladderUpDir);
			int dx = getLadderX(ladderUpDir);
			int dz = getLadderZ(ladderUpDir);
			for (int dy = bottom; dy < top; dy++) {
				placeBlockAtCurrentPosition(world, Blocks.ladder, meta, dx, dy, dz, sbb);
			}
		}
		
		if (ladderDownDir > -1) {
			// add ladder going down
			int meta = getLadderMeta(ladderDownDir);
			int dx = getLadderX(ladderDownDir);
			int dz = getLadderZ(ladderDownDir);
			for (int dy = bottom - 1; dy < bottom + 2; dy++) {
				placeBlockAtCurrentPosition(world, Blocks.ladder, meta, dx, dy, dz, sbb);
			}
		}
		
		// pick a decoration?
		if (rand.nextInt(7) == 0 && ladderDownDir == -1) {
			decorateWell(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
		}
		else if (rand.nextInt(7) == 0 && ladderDownDir == -1) {
			decorateSkeletonRoom(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
		}
		else if (rand.nextInt(6) == 0 && ladderDownDir == -1) {
			decorateZombieRoom(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
		}
		else if (rand.nextInt(5) == 0 && ladderDownDir == -1) {
			decorateCactusRoom(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
		}
		else if (rand.nextInt(4) == 0 && ladderDownDir > -1) {
			decorateTreasureChest(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
		} 
		else if (rand.nextInt(5) == 0) {
			decorateSpiderWebs(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
		} 
		else if (rand.nextInt(12) == 0 && ladderDownDir > -1) {
			// these are annoying
			decorateSolidRock(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
		} 
		else if (rand.nextInt(3) == 0) {
			decorateFullLibrary(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
		} 
		else {
			decorateLibrary(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
//			if (ladderDownDir == -1) {
//				// trap it!
//				decorateTrap(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
//			}
		}
	}




	/**
	 * Decorate this floor with a scenic well.
	 */
	protected void decorateWell(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, StructureBoundingBox sbb) {
		int cx = size / 2;
		int cz = cx;
		int cy = bottom;
		
		Block waterOrLava = rand.nextInt(4) == 0 ? Blocks.lava : Blocks.water;
		
		if (size > 5) {
			// actual well structure
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx - 1, cy + 0, cz - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 5, cx - 1, cy + 1, cz - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx + 0, cy + 0, cz - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx + 1, cy + 0, cz - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 5, cx + 1, cy + 1, cz - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx - 1, cy + 0, cz + 0, sbb);
			placeBlockAtCurrentPosition(world, waterOrLava, 0, cx + 0, cy + 0, cz + 0, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx + 1, cy + 0, cz + 0, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx - 1, cy + 0, cz + 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 5, cx - 1, cy + 1, cz + 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx + 0, cy + 0, cz + 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx + 1, cy + 0, cz + 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 5, cx + 1, cy + 1, cz + 1, sbb);
		}
		
		placeBlockAtCurrentPosition(world, waterOrLava, 0, cx + 0, cy - 1, cz + 0, sbb);

	}

	/**
	 * Add a skeleton spawner on this floor and decorate it in an appropriately scary manner.
	 */
	protected void decorateSkeletonRoom(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, StructureBoundingBox sbb) {
		// skeleton spawner
		placeSpawnerAtCurrentPosition(world, rand, size / 2, bottom + 2, size / 2, "Skeleton", sbb);

		// floor-to-ceiling chains
		ArrayList<ChunkCoordinates> chainList = new ArrayList<ChunkCoordinates>();
		chainList.add(new ChunkCoordinates(size / 2, bottom + 2, size / 2)); // don't block the spawner
		for (int i = 0; i < size + 2; i++) {
			ChunkCoordinates chain = new ChunkCoordinates(2 + rand.nextInt(size - (4)), height - 2, 2 + rand.nextInt(size - (4)));
			if (!chainCollides(chain, chainList)) {
				// if it doesn't collide, manufacture it and add it to the list
				for (int dy = bottom; dy < top; dy++) {
					placeBlockAtCurrentPosition(world, Blocks.iron_bars, 0, chain.posX, dy, chain.posZ, sbb);
				}
				chainList.add(chain);
			}
		}

		
		// spider webs in the corner
		for (int dx = 1; dx <= size - 2; dx++) {
			for (int dz = 1; dz <= size - 2; dz++) {
				if (dx == 1 || dx == size - 2 || dz == 1 || dz == size -2) {
					// side of the room
					if (!isWindowPos(dx, dz) && !isLadderPos(dx, dz, ladderUpDir, ladderDownDir)) {
						// not an occupied position
						placeBlockAtCurrentPosition(world, Blocks.web, 0, dx, top - 1, dz, sbb);
					}
				}
			}
		}

	}


	/**
	 * Add a zombie spawner on this floor and decorate it in an appropriately scary manner.
	 */
	protected void decorateZombieRoom(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, StructureBoundingBox sbb) {
		// zombie spawner
		placeSpawnerAtCurrentPosition(world, rand, size / 2, bottom + 2, size / 2, "Zombie", sbb);
		
		// random brown mushrooms
		for (int dx = 1; dx <= size - 2; dx++) {
			for (int dz = 1; dz <= size - 2; dz++) {
				if (!isWindowPos(dx, dz) && !isLadderPos(dx, dz, ladderUpDir, ladderDownDir)) {
					// not an occupied position
					if (rand.nextInt(5) == 0) {
						placeBlockAtCurrentPosition(world, Blocks.brown_mushroom, 0, dx, bottom, dz, sbb);
					}
				}
			}
		}

		// slab tables
		ArrayList<ChunkCoordinates> slabList = new ArrayList<ChunkCoordinates>();
		slabList.add(new ChunkCoordinates(size / 2, bottom + 2, size / 2)); // don't block the spawner
		for (int i = 0; i < size - 1; i++) {
			ChunkCoordinates slab = new ChunkCoordinates(2 + rand.nextInt(size - (4)), height - 2, 2 + rand.nextInt(size - (4)));
			if (!chainCollides(slab, slabList)) {
				// if it doesn't collide, manufacture it and add it to the list
				placeBlockAtCurrentPosition(world, Blocks.iron_bars, 0, slab.posX, bottom + 0, slab.posZ, sbb);
				placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 2, slab.posX, bottom + 1, slab.posZ, sbb);
				placeBlockAtCurrentPosition(world, Blocks.soul_sand, 0, slab.posX, bottom + 2, slab.posZ, sbb);
				slabList.add(slab);
			}
		}
	}

	/**
	 * Fill this room with sand and floor-to-ceiling cacti
	 */
	protected void decorateCactusRoom(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, StructureBoundingBox sbb) {
		// sand & random dead bush
		for (int dx = 1; dx <= size - 2; dx++) {
			for (int dz = 1; dz <= size - 2; dz++) {
				// sand
				placeBlockAtCurrentPosition(world, Blocks.sand, 0, dx, bottom - 1, dz, sbb);
				if (!isWindowPos(dx, dz) && !isLadderPos(dx, dz, ladderUpDir, ladderDownDir)) {
					// not an occupied position
					if (rand.nextInt(4) == 0) {
						placeBlockAtCurrentPosition(world, Blocks.deadbush, 0, dx, bottom, dz, sbb);
					}
				}
			}
		}

		// cacti
		ArrayList<ChunkCoordinates> cactusList = new ArrayList<ChunkCoordinates>();
		cactusList.add(new ChunkCoordinates(size / 2, bottom + 2, size / 2)); // don't block the spawner
		for (int i = 0; i < size + 12; i++) {
			ChunkCoordinates cactus = new ChunkCoordinates(2 + rand.nextInt(size - (4)), height - 2, 2 + rand.nextInt(size - (4)));
			if (!chainCollides(cactus, cactusList)) {
				// if it doesn't collide, manufacture it and add it to the list
				for (int dy = bottom; dy < top; dy++) {
					placeBlockAtCurrentPosition(world, Blocks.cactus, 0, cactus.posX, dy, cactus.posZ, sbb);
				}
				cactusList.add(cactus);
			}
		}
	}




	/**
	 * Decorate this floor with an enticing treasure chest.
	 */
	protected void decorateTreasureChest(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, StructureBoundingBox sbb) {
		int cx = size / 2;
		int cz = cx;
		
		// bottom decoration
		placeBlockAtCurrentPosition(world, Blocks.stone_brick_stairs, getStairMeta(1), cx + 0, bottom, cz - 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_brick_stairs, getStairMeta(0), cx - 1, bottom, cz + 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_brick_stairs, getStairMeta(2), cx + 1, bottom, cz + 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_brick_stairs, getStairMeta(3), cx + 0, bottom, cz + 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx + 0, bottom, cz + 0, sbb);

		if (size > 5)
		{
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx - 1, bottom, cz - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx + 1, bottom, cz - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx - 1, bottom, cz + 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx + 1, bottom, cz + 1, sbb);
		}
		
		// top decoration
		placeBlockAtCurrentPosition(world, Blocks.stone_brick_stairs, getStairMeta(1) + 4, cx + 0, top - 1, cz - 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_brick_stairs, getStairMeta(0) + 4, cx - 1, top - 1, cz + 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_brick_stairs, getStairMeta(2) + 4, cx + 1, top - 1, cz + 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_brick_stairs, getStairMeta(3) + 4, cx + 0, top - 1, cz + 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx + 0, top - 1, cz + 0, sbb);

		if (size > 5)
		{
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx - 1, top - 1, cz - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx + 1, top - 1, cz - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx - 1, top - 1, cz + 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx + 1, top - 1, cz + 1, sbb);
		}
		
		if (size > 5)
		{
			// pillars
			for (int cy = bottom + 1; cy < top - 1; cy++) {
				placeBlockAtCurrentPosition(world, Blocks.stonebrick, 5, cx - 1, cy, cz - 1, sbb);
				placeBlockAtCurrentPosition(world, Blocks.stonebrick, 5, cx + 1, cy, cz - 1, sbb);
				placeBlockAtCurrentPosition(world, Blocks.stonebrick, 5, cx - 1, cy, cz + 1, sbb);
				placeBlockAtCurrentPosition(world, Blocks.stonebrick, 5, cx + 1, cy, cz + 1, sbb);
			}
		}

		placeTreasureAtCurrentPosition(world, rand, cx + 0, bottom + 1, cz + 0, TFTreasure.tower_room, sbb);
		
		for (int i = 0; i < 4; i++) {
			//TODO if there is no ladder or window in the specified direction, put a painting
			
			// surrounded by torches
			
		}

	}


	/**
	 * Decorate this floor with a mass of messy spider webs.
	 */
	protected void decorateSpiderWebs(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, StructureBoundingBox sbb) {
		for (int dy = bottom; dy < top; dy++) {
			int chance = (top - dy + 2);
			for (int dx = 1; dx <= size - 2; dx++) {
				for (int dz = 1; dz <= size - 2; dz++) {
					if (!isLadderPos(dx, dz, ladderUpDir, ladderDownDir) && rand.nextInt(chance) == 0) {
						placeBlockAtCurrentPosition(world, Blocks.web, 0, dx, dy, dz, sbb);
					}
				}
			}
		}
		
		// 20% chance of a spider spawner!
		if (rand.nextInt(5) == 0) {
			String spiderName;
			switch(rand.nextInt(4)) {
			case 3:
				spiderName = "CaveSpider";
				break;
			case 2:
				spiderName = TFCreatures.getSpawnerNameFor("Swarm Spider");
				break;
			case 1:
				spiderName = TFCreatures.getSpawnerNameFor("Hedge Spider");
				break;
			case 0:
			default:
				spiderName = "Spider";
				break;
			}
			
			placeSpawnerAtCurrentPosition(world, rand, size / 2, bottom + 2, size / 2, spiderName, sbb);
			

		}
		else {
			decorateFurniture(world, rand, bottom, size - 2, sbb);
		}
	}


	/**
	 * Place some furniture around the room.  This should probably only be called on larger towers.
	 */
	protected void decorateFurniture(World world, Random rand, int bottom, int freeSpace, StructureBoundingBox sbb) {
		// 66% chance of a table
		if (rand.nextInt(3) > 0) {
			placeBlockAtCurrentPosition(world, Blocks.fence, 0, size / 2, bottom, size / 2, sbb);
			placeBlockAtCurrentPosition(world, Blocks.wooden_pressure_plate, 0, size / 2, bottom + 1, size / 2, sbb);
		}
		
		// chairs!
		if (rand.nextInt(3) == 0 && freeSpace > 1) {
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(0), size / 2 + 1, bottom, size / 2, sbb);
		}
		if (rand.nextInt(3) == 0 && freeSpace > 1) {
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(1), size / 2, bottom, size / 2 + 1, sbb);
		}
		if (rand.nextInt(3) == 0 && freeSpace > 1) {
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(2), size / 2 - 1, bottom, size / 2, sbb);
		}
		if (rand.nextInt(3) == 0 && freeSpace > 1) {
			placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(3), size / 2, bottom, size / 2 - 1, sbb);
		}
	}


	/**
	 * Decorate this floor with solid rock
	 */
	protected void decorateSolidRock(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, StructureBoundingBox sbb) {
		for (int dy = bottom; dy < top; dy++) {
			for (int dx = 1; dx <= size - 2; dx++) {
				for (int dz = 1; dz <= size - 2; dz++) {
					if (!isLadderPos(dx, dz, ladderUpDir, ladderDownDir) && rand.nextInt(9) != 0) {
						placeBlockAtCurrentPosition(world, Blocks.stone, 0, dx, dy, dz, sbb);
					}
				}
			}
		}
		
		//TODO: maybe seed a few ores in there.
	}



	/**
	 * Decorate this floor with an orderly library
	 */
	protected void decorateLibrary(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, StructureBoundingBox sbb) {
		// put some bookshelves around the room
		for (int dx = 1; dx <= size - 2; dx++) {
			for (int dz = 1; dz <= size - 2; dz++) {
				for (int dy = bottom; dy < top -1; dy++) {
					if (dx == 1 || dx == size - 2 || dz == 1 || dz == size -2) {
						// side of the room
						if (!isWindowPos(dx, dz) && !isLadderPos(dx, dz, ladderUpDir, ladderDownDir)) {
							// not an occupied position
							placeBlockAtCurrentPosition(world, Blocks.bookshelf, 0, dx, dy, dz, sbb);
						}
					}
				}
			}
		}
		// treasure?!?!
		if (rand.nextInt(2) == 0 && this.size > 5) {
			decorateLibraryTreasure(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
		}

		if (rand.nextInt(2) == 0 && this.size > 5) {
			decorateFurniture(world, rand, bottom, size - 2, sbb);
		}

	}



	/**
	 * Place a library treasure chest somewhere in the library
	 */
	protected void decorateLibraryTreasure(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, StructureBoundingBox sbb) {
		switch (rand.nextInt(4)) {
		case 0:
		default:
			if (!isLadderPos(2, 1, ladderUpDir, ladderDownDir)) {
				placeTreasureAtCurrentPosition(world, rand, 2, top - 2, 1, TFTreasure.tower_library, sbb);
				break;
			}
		case 1:
			if (!isLadderPos(size - 2, 2, ladderUpDir, ladderDownDir)) {
				placeTreasureAtCurrentPosition(world, rand, size - 2, top - 2, 2, TFTreasure.tower_library, sbb);
				break;
			}
		case 2:
			if (!isLadderPos(size - 3, size - 2, ladderUpDir, ladderDownDir)) {
				placeTreasureAtCurrentPosition(world, rand, size - 3, top - 2, size - 2, TFTreasure.tower_library, sbb);
				break;
			}
		case 3:
			if (!isLadderPos(1, size - 3, ladderUpDir, ladderDownDir)) {
				placeTreasureAtCurrentPosition(world, rand, 1, top - 2, size - 3, TFTreasure.tower_library, sbb);
				break;
			}
		}
	}

	/**
	 * Decorate this floor with an overflowing library
	 */
	protected void decorateFullLibrary(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, StructureBoundingBox sbb) {
		// put some bookshelves around the room
		for (int dx = 1; dx <= size - 2; dx++) {
			for (int dz = 1; dz <= size - 2; dz++) {
				for (int dy = bottom; dy < top; dy++) {
					if (dx % 2 != 0 && ((dz >= dx && dz <= size - dx - 1) || (dz >= size - dx - 1 && dz <= dx))
							|| dz % 2 != 0 && ((dx >= dz && dx <= size - dz - 1) || (dx >= size - dz - 1 && dx <= dz))) {
						// concentric rings
						if (!isWindowPos(dx, dy, dz) && !isOpeningPos(dx, dy, dz) && !isLadderPos(dx, dz, ladderUpDir, ladderDownDir)) {
							// not an occupied position
							placeBlockAtCurrentPosition(world, Blocks.bookshelf, 0, dx, dy, dz, sbb);
						}
					}
				}
			}
		}
		// treasure?!?!
		if (rand.nextInt(2) == 0 && this.size > 5) {
			decorateLibraryTreasure(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);
		}
	}
	
	/**
	 * "Decorate" with a lot of TNT.
	 * 
	 * This is not called at the moment, since I added monsters and the monsters set off the trap.  Perhaps I need a better way of activating it.
	 * 
	 */
	protected void decorateTrap(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, StructureBoundingBox sbb) {
		for (int dx = 2; dx <= size - 3; dx++) {
			for (int dz = 2; dz <= size - 3; dz++) {
				if (dx == 2 || dx == size - 3 || dz == 2 || dz == size -3) {
					placeBlockAtCurrentPosition(world, Blocks.tnt, 0, dx, -1, dz, sbb);
				}
			}
		}
		for (int dy = bottom - 2; dy < top - 2; dy++) {
			placeBlockAtCurrentPosition(world, Blocks.tnt, 0, 1, dy, 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.tnt, 0, 1, dy, size - 2, sbb);
			placeBlockAtCurrentPosition(world, Blocks.tnt, 0, size - 2, dy, 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.tnt, 0, size - 2, dy, size - 2, sbb);
		}
	}



	
	/**
	 * Checks if there is a window at the specified x and z.  Does not check the Y.
	 */
	protected boolean isWindowPos(int x, int z) {
		if (x == 1 && z == size / 2) {
			return true;
		}
		if (x == size - 2 && z == size / 2) {
			return true;
		}
		if (x == size / 2 && z == 1) {
			return true;
		}
		if (x == size / 2 && z == size - 2) {
			return true;
		}
		
		// okay, looks good
		return false;
	}

	/**
	 * Checks if there is a window at the specified x, y, and z.
	 */
	protected boolean isWindowPos(int x, int y, int z) {
		int checkYDir = -1;
		
		if (x == 1 && z == size / 2) {
			checkYDir = 2;
		}
		else if (x == size - 2 && z == size / 2) {
			checkYDir = 0;
		}
		else if (x == size / 2 && z == 1) {
			checkYDir = 3;
		}
		else if (x == size / 2 && z == size - 2) {
			checkYDir = 1;
		}
		
		if (checkYDir > -1) {
			// check if we are at one of the Y positions with a window.
			return !openingTowards[checkYDir] && (y == 2 || y == 3 || (height > 8 && (y == height - 3 || y == height - 4)));
		}
		else {
			// okay, looks good
			return false;
		}
	}

	/**
	 * Checks if putting a block at the specified x, y, and z would block an opening.
	 * TODO: this could be much smarter.  Although since there's usually only one opening, I guess it's not bad.
	 */
	protected boolean isOpeningPos(int x, int y, int z) {
		Iterator<ChunkCoordinates> itr = openings.iterator();
		while(itr.hasNext()){
			ChunkCoordinates door =  itr.next();
			// determine which wall we're at
			ChunkCoordinates inside = new ChunkCoordinates(door);
			if (inside.posX == 0) {
				inside.posX++;
			}
			else if (inside.posX == size - 1) {
				inside.posX--;
			}
			else if (inside.posZ == 0) {
				inside.posZ++;
			}
			else if (inside.posZ == size - 1) {
				inside.posZ--;
			}
			// check the block
			if (inside.posX == x && inside.posZ == z && (inside.posY == y || inside.posY + 1 == y)) {
				return true;
			}
		}
		// guess not!
		return false;
	}

	
	
	protected boolean isLadderPos(int x, int z, int ladderUpDir, int ladderDownDir) {
		if (x == getLadderX(ladderUpDir) && z == getLadderZ(ladderUpDir)) {
			return true;
		}
		if (x == getLadderX(ladderDownDir) && z == getLadderZ(ladderDownDir)) {
			return true;
		}
		
		// okay, looks good
		return false;
	}

	/**
	 * Gets the X coordinate of the ladder on the specified wall.
	 * 
	 * @param ladderDir
	 * @return
	 */
	protected int getLadderX(int ladderDir) {
		switch (ladderDir) {
		case 0:
			return size - 2;
		case 1:
			return size / 2 + 1;
		case 2:
			return 1;
		case 3:
			return size / 2 - 1;
		default:
			return size / 2;
		}
	}
	
	/**
	 * Gets the Z coordinate of the ladder on the specified wall.
	 * 
	 * @param ladderDir
	 * @return
	 */
	protected int getLadderZ(int ladderDir) {
		
		switch (ladderDir) {
		case 0:
			return size / 2 - 1;
		case 1:
			return size - 2;
		case 2:
			return size / 2 + 1;
		case 3:
			return 1;
		default:
			return size / 2;
		}
	}

	/**
	 * Decorate a tower with stairs.
	 * 
	 * We have two schemes here.  We can either decorate the whole tower with a 
	 * decoration that rises the entire height of the tower (such as a pillar)
	 * or we can divide the tower into the "stair" section on the bottom and the 
	 * "attic" section at the top and decorate those seperately.
	 * 
	 */
	protected void decorateStairTower(World world, Random rand, StructureBoundingBox sbb) {
		// if it's tall enough, consider adding extra floors onto the top.
		if (height - highestOpening > 8) {
			int base = highestOpening + 3;
			int floors = (this.height - base) / 5;

			// divide the tower into floors
			int floorHeight = (this.height - base) / floors;
			for (int i = 0; i < floors; i++) {
				// put down a floor
				for (int x = 1; x < size - 1; x++) {
					for (int z = 1; z < size - 1; z++) {
						placeBlockAtCurrentPosition(world, Blocks.planks, 2, x, (i * floorHeight + base), z, sbb);
					}
				}
			}


			int ladderDir = 3;
			int downLadderDir = -1;
			
			// place a ladder going up
			//TODO: make this ladder connect better to the stairs
			int meta = getLadderMeta(ladderDir);
			int dx = getLadderX(ladderDir);
			int dz = getLadderZ(ladderDir);
			for (int dy = 1; dy < 3; dy++) {
				placeBlockAtCurrentPosition(world, Blocks.ladder, meta, dx, base - dy, dz, sbb);
			}

			// decorate middle floors
			for (int i = 0; i < floors - 1; i++) {
				int bottom = base + 1 + floorHeight * i;
				int top = base + floorHeight * (i + 1);

				downLadderDir = ladderDir;
				ladderDir++;
				ladderDir %= 4;

				decorateFloor(world, rand, i, bottom, top, ladderDir, downLadderDir, sbb);
			}

			// decorate top floor
			decorateFloor(world, rand, floors, base + 1 + floorHeight * (floors - 1), height - 1, -1, ladderDir, sbb);
			
			// decorate below the bottom floor, into the stairs
			if (base > 8)
			{
				switch (rand.nextInt(4)) {
				case 0:
					decorateChandelier(world, rand, base + 1, sbb);
					break;
				case 1:
					decorateHangingChains(world, rand, base + 1, sbb);
					break;
				case 2:
					decorateFloatingBooks(world, rand, base + 1, sbb);
					break;
				case 3:
					decorateFloatingVines(world, rand, base + 1, sbb);
					break;
				}
			}
		}
		else {
			// decorate the top normally
			if (size > 5) {
				switch (rand.nextInt(4)) {
				case 0:
					decorateChandelier(world, rand, height, sbb);
					break;
				case 1:
					decorateHangingChains(world, rand, height, sbb);
					break;
				case 2:
					decorateFloatingBooks(world, rand, height, sbb);
					break;
				case 3:
					decorateFloatingVines(world, rand, height, sbb);
					break;
				}
			}
			else if (size > 3)
			{
				switch (rand.nextInt(3)) {
				case 0:
					decorateHangingChains(world, rand, height, sbb);
					break;
				case 1:
					decorateFloatingBooks(world, rand, height, sbb);
					break;
				case 2:
					decorateFloatingVines(world, rand, height, sbb);
					break;
				}
			}
		}
		
		decorateStairFloor(world, rand, sbb);
	}


	/**
	 * Decorate the bottom floor of this tower.
	 * 
	 * This is for towers with stairs at the bottom, not towers divided into floors
	 */
	protected void decorateStairFloor(World world, Random rand, StructureBoundingBox sbb) {
		// decorate the bottom
		if (size > 5) {
			if (rand.nextInt(3) == 0) {
				decorateStairWell(world, rand, sbb);
			}
			else if (rand.nextInt(3) > 0 || this.size >= 15) {
				// a few empty bottoms
				decoratePlanter(world, rand, sbb);
			}
		}
	}





	/**
	 * Make a chandelier.  The chandelier hangs down a random amount between the top of the tower and the highest opening.
	 */
	protected void decorateChandelier(World world, Random rand, int decoTop, StructureBoundingBox sbb) {
		if (decoTop < 8 || size < 8)
		{
			//System.out.println("Trying to put a chandelier in a size " + decoTop + " space.  FAIL!");
			return;
		}
		
		int cx = size / 2;
		int cy = decoTop - rand.nextInt(decoTop - 7) - 2;
		int cz = size / 2;
	
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, cx + 0, cy + 0, cz + 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, cx - 1, cy + 0, cz + 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, cx + 1, cy + 0, cz + 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, cx + 0, cy + 0, cz - 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, cx + 0, cy + 0, cz + 1, sbb);
	
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, cx + 0, cy + 1, cz + 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, cx - 1, cy + 1, cz + 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, cx + 1, cy + 1, cz + 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, cx + 0, cy + 1, cz - 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, cx + 0, cy + 1, cz + 1, sbb);
		
		for (int y = cy; y < decoTop - 1; y++) {
			placeBlockAtCurrentPosition(world, Blocks.fence, 0, cx + 0, y, cz + 0, sbb);
		}
	}



	/**
	 * Decorates a tower with chains hanging down.
	 * 
	 * The chains go from the ceiling to just above the highest doorway.
	 * @param decoTop 
	 */
	protected void decorateHangingChains(World world, Random rand, int decoTop, StructureBoundingBox sbb) {
		// a list of existing chains
		ArrayList<ChunkCoordinates> chainList = new ArrayList<ChunkCoordinates>();
		// try size + 2 times to find a chain that does not collide
		for (int i = 0; i < size + 2; i++) {
			int filled = size < 15 ? 2 : 4;
			ChunkCoordinates chain = new ChunkCoordinates(filled + rand.nextInt(size - (filled * 2)), decoTop - 2, filled + rand.nextInt(size - (filled * 2)));
			if (!chainCollides(chain, chainList)) {
				// if it doesn't collide, manufacture it and add it to the list
				int length = 1 + rand.nextInt(decoTop - 7);
				decorateOneChain(world, rand, chain.posX, decoTop, length, chain.posZ, sbb);
				chainList.add(chain);
			}
		}

		
	}
	
	/**
	 * Return true if the specified coords are orthogonally adjacent to any other coords on the list.
	 */
	protected boolean chainCollides(ChunkCoordinates coords, List<ChunkCoordinates> list) {
	    Iterator<ChunkCoordinates> itr = list.iterator();
	    while (itr.hasNext()) {
	    	ChunkCoordinates existing = itr.next();
	    	// if x is within 1 and z is equal, we collide
	    	if (coords.posZ == existing.posZ && Math.abs(coords.posX - existing.posX) <= 1) {
	    		return true;
	    	}
	    	// similarly, if z is within 1 and x is equal, we collide
	    	if (coords.posX == existing.posX && Math.abs(coords.posZ - existing.posZ) <= 1) {
	    		return true;
	    	}
	    }
	    // we're good
	    return false;
	}
	
	/**
	 * Puts one chain in a tower at the specified coordinates.
	 *
	 * @param dx
	 * @param length
	 * @param dz
	 * @param posZ 
	 */ 
	protected void decorateOneChain(World world, Random rand, int dx, int decoTop, int length, int dz, StructureBoundingBox sbb) {
		for (int y = 1; y <= length; y++) {
			placeBlockAtCurrentPosition(world, Blocks.iron_bars, 0, dx, decoTop - y - 1, dz, sbb);
		}		
		// make the "ball" at the end.
		Block ballBlock;
		int ballMeta;
		switch (rand.nextInt(10)) {
		case 0:
			ballBlock = Blocks.iron_block;
			ballMeta = 0;
			break;
		case 1:
			ballBlock = Blocks.bookshelf;
			ballMeta = 0;
			break;
		case 2:
			ballBlock = Blocks.netherrack;
			ballMeta = 0;
			break;
		case 3:
			ballBlock = Blocks.soul_sand;
			ballMeta = 0;
			break;
		case 4:
			ballBlock = Blocks.glass;
			ballMeta = 0;
			break;
		case 5:
			ballBlock = Blocks.lapis_block;
			ballMeta = 0;
			break;
		case 6:
			ballBlock = Blocks.monster_egg;
			ballMeta = 2;
			break;
		case 7:
		default:
			ballBlock = Blocks.glowstone;
			ballMeta = 0;
			break;
		}
		placeBlockAtCurrentPosition(world, ballBlock, ballMeta, dx, decoTop - length - 2, dz, sbb);
	}

	/**
	 * Decorates a tower with an array of floating bookshelves.
	 * @param decoTop 
	 */
	protected void decorateFloatingBooks(World world, Random rand, int decoTop, StructureBoundingBox sbb) {
		// a list of existing bookshelves
		ArrayList<ChunkCoordinates> shelfList = new ArrayList<ChunkCoordinates>();
		// try size + 2 times to find a shelf that does not collide
		for (int i = 0; i < size + 2; i++) {
			int filled = size < 15 ? 2 : 4;
			ChunkCoordinates shelf = new ChunkCoordinates(filled + rand.nextInt(size - (filled * 2)), decoTop - 2, filled + rand.nextInt(size - (filled * 2)));
			if (!chainCollides(shelf, shelfList)) {
				// if it doesn't collide, manufacture it and add it to the list
				int bottom = 2 + rand.nextInt(decoTop - 7);
				int top = rand.nextInt(bottom - 1) + 2;
				for (int y = top; y <= bottom; y++) {
					placeBlockAtCurrentPosition(world, Blocks.bookshelf, 0, shelf.posX, decoTop - y, shelf.posZ, sbb);
				}		
				shelfList.add(shelf);
			}
		}
	}
	
	
	/**
	 * Decorates a tower with an array of floating vines, attached to mossy cobblestone.
	 * @param decoTop 
	 */
	protected void decorateFloatingVines(World world, Random rand, int decoTop, StructureBoundingBox sbb) {
		// a list of existing blocks
		ArrayList<ChunkCoordinates> mossList = new ArrayList<ChunkCoordinates>();
		// try size + 2 times to find a rock pillar that does not collide
		for (int i = 0; i < size + 2; i++) {
			int filled = size < 15 ? 2 : 4;
			ChunkCoordinates moss = new ChunkCoordinates(filled + rand.nextInt(size - (filled * 2)), decoTop - 2, filled + rand.nextInt(size - (filled * 2)));
			if (!chainCollides(moss, mossList)) {
				// if it doesn't collide, manufacture it and add it to the list
				int bottom = 2 + rand.nextInt(decoTop - 7);
				int top = rand.nextInt(bottom - 1) + 2;
				for (int y = top; y <= bottom; y++) {
					placeBlockAtCurrentPosition(world, Blocks.mossy_cobblestone, 0, moss.posX, decoTop - y, moss.posZ, sbb);
					// surround it with vines
					placeBlockAtCurrentPosition(world, Blocks.vine, getVineMeta(2), moss.posX + 1, decoTop - y, moss.posZ + 0, sbb);
					placeBlockAtCurrentPosition(world, Blocks.vine, getVineMeta(0), moss.posX - 1, decoTop - y, moss.posZ + 0, sbb);
					placeBlockAtCurrentPosition(world, Blocks.vine, getVineMeta(3), moss.posX + 0, decoTop - y, moss.posZ + 1, sbb);
					placeBlockAtCurrentPosition(world, Blocks.vine, getVineMeta(1), moss.posX + 0, decoTop - y, moss.posZ - 1, sbb);
				}		
				mossList.add(moss);
			}
		}
		
		// put vines on the sides of the tower.
		for (int y = highestOpening + 3; y < decoTop - 1; y++) {
			for (int x = 1; x < size - 1; x++) {
				if (rand.nextInt(3) == 0) {
					placeBlockAtCurrentPosition(world, Blocks.vine, getVineMeta(3), x, y, 1, sbb);
				}
				if (rand.nextInt(3) == 0) {
					placeBlockAtCurrentPosition(world, Blocks.vine, getVineMeta(1), x, y, size - 2, sbb);
				}
			}
			for (int z = 1; z < size - 1; z++) {
				if (rand.nextInt(3) == 0) {
					placeBlockAtCurrentPosition(world, Blocks.vine, getVineMeta(2), 1, y, z, sbb);
				}
				if (rand.nextInt(3) == 0) {
					placeBlockAtCurrentPosition(world, Blocks.vine, getVineMeta(0), size - 2, y, z, sbb);
				}
			}
		}
	}
	
	
	/**
	 * Gets the metadata necessary to stick the vines on the specified wall.
	 * 
	 * @param vineDir
	 * @return
	 */
	protected int getVineMeta(int vineDir) {
		switch ((this.getCoordBaseMode() + vineDir) % 4) {
		case 0:
			return 8;
		case 1:
			return 1;
		case 2:
			return 2;
		case 3:
			return 4;
		default:
			return -1; // this is impossible
		}
	}
	

	
	/**
	 * Makes a planter.  Depending on the situation, it can be filled with trees, flowers, or crops
	 */
	protected void decoratePlanter(World world, Random rand, StructureBoundingBox sbb) {
		int cx = size / 2;
		int cz = cx;
		
		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, cx + 0, 1, cz + 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, cx + 0, 1, cz - 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, cx + 1, 1, cz + 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, cx - 1, 1, cz + 0, sbb);

		if (size > 7)
		{
			placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, cx - 1, 1, cz - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, cx + 1, 1, cz - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, cx + 1, 1, cz + 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, cx - 1, 1, cz + 1, sbb);
		}

		// place a cute planted thing
		placeBlockAtCurrentPosition(world, Blocks.grass, 0, cx + 0, 1, cz + 0, sbb);

		Block planterBlock;
		int planterMeta;
		switch (rand.nextInt(6)) {
		case 0:
			planterBlock = Blocks.sapling;
			planterMeta = 0;
			break;
		case 1:
			planterBlock = Blocks.sapling;
			planterMeta = 1;
			break;
		case 2:
			planterBlock = Blocks.sapling;
			planterMeta = 2;
			break;
		case 3:
			planterBlock = Blocks.sapling;
			planterMeta = 3;
			break;
		case 4:
			planterBlock = Blocks.brown_mushroom;
			planterMeta = 0;
			break;
		case 5:
		default:
			planterBlock = Blocks.red_mushroom;
			planterMeta = 0;
			break;
		}
		placeBlockAtCurrentPosition(world, planterBlock, planterMeta, cx + 0, 2, cz + 0, sbb);
		
		// try to grow a tree
		if (planterBlock == Blocks.sapling) {
	        int wx = getXWithOffset(cx, cz);
	        int wy = getYWithOffset(2);
	        int wz = getZWithOffset(cx, cz);
	        ((BlockSapling)Blocks.sapling).func_149878_d(world, wx, wy, wz, world.rand);
		}
		// or a mushroom
		if (planterBlock == Blocks.brown_mushroom || planterBlock == Blocks.red_mushroom) {
	        int wx = getXWithOffset(cx, cz);
	        int wy = getYWithOffset(2);
	        int wz = getZWithOffset(cx, cz);
	        ((BlockMushroom)planterBlock).updateTick(world, wx, wy, wz, world.rand);
		}
		
		// otherwise, place the block into a flowerpot
		Block whatHappened = this.getBlockAtCurrentPosition(world, cx + 0, 2, cz + 0, sbb);
		if (whatHappened == planterBlock || whatHappened == Blocks.air)
		{
			int potMeta = 0;//BlockFlowerPot.getMetaForPlant(new ItemStack(planterBlock, 1, planterMeta));
			placeBlockAtCurrentPosition(world, Blocks.flower_pot, potMeta, cx + 0, 2, cz + 0, sbb);
		}
	}
	
	/**
	 * Decorate the floor of this stair tower with a scenic well.
	 */
	protected void decorateStairWell(World world, Random rand, StructureBoundingBox sbb) {
		int cx = size / 2;
		int cz = cx;
		int cy = 1;
		
		Block waterOrLava = rand.nextInt(4) == 0 ? Blocks.lava : Blocks.water;
		
		if (size > 7) {
			// actual well structure
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx - 1, cy + 0, cz - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 5, cx - 1, cy + 1, cz - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx + 0, cy + 0, cz - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx + 1, cy + 0, cz - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 5, cx + 1, cy + 1, cz - 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx - 1, cy + 0, cz + 0, sbb);
			placeBlockAtCurrentPosition(world, waterOrLava, 0, cx + 0, cy + 0, cz + 0, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx + 1, cy + 0, cz + 0, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx - 1, cy + 0, cz + 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 5, cx - 1, cy + 1, cz + 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx + 0, cy + 0, cz + 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, cx + 1, cy + 0, cz + 1, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stone_slab, 5, cx + 1, cy + 1, cz + 1, sbb);
		}
		
		placeBlockAtCurrentPosition(world, waterOrLava, 0, cx + 0, cy - 1, cz + 0, sbb);

	}




	/**
	 * Returns true if this tower has only one exit.
	 */
	public boolean isDeadEnd() {
		return openings.size() == 1;
	}

	/**
	 * Returns true if this tower has four exits.
	 */
	public boolean hasExitsOnAllWalls() {
		int exits = 0;
		for (int i = 0; i < 4; i++) {
			exits += this.openingTowards[i] ? 1 : 0;
		}
		
		return exits == 4;
	}
	
	/**
	 * Returns true if this tower has stairs
	 */
	public boolean hasStairs() {
		return highestOpening > 1;
	}

	/**
	 * Iterate through the openings on our list and add them to the tower
	 */
	protected void makeOpenings(World world, StructureBoundingBox sbb) {
		for (ChunkCoordinates door : openings)
		{
			makeDoorOpening(world, door.posX, door.posY, door.posZ, sbb);
		}
	}




	/**
	 * Make an opening in this tower for a door.  This now only makes one opening, so you need two
	 */
	protected void makeDoorOpening(World world, int dx, int dy, int dz, StructureBoundingBox sbb) {
        // try to add blocks outside this door
//		if (dx == 0) {
//			placeBlockAtCurrentPosition(world, Blocks.stone, 0, dx - 1, dy + 0, dz, sbb);
//	        placeBlockAtCurrentPosition(world, Blocks.stone, 0, dx - 1, dy + 1, dz, sbb);
//		}
//		if (dx == size - 1) {
//			placeBlockAtCurrentPosition(world, Blocks.stone, 0, dx + 1, dy + 0, dz, sbb);
//	        placeBlockAtCurrentPosition(world, Blocks.stone, 0, dx + 1, dy + 1, dz, sbb);
//		}
//		if (dz == 0) {
//			placeBlockAtCurrentPosition(world, Blocks.stone, 0, dx, dy + 0, dz - 1, sbb);
//	        placeBlockAtCurrentPosition(world, Blocks.stone, 0, dx, dy + 1, dz - 1, sbb);
//		}
//		if (dz == size - 1) {
//			placeBlockAtCurrentPosition(world, Blocks.stone, 0, dx, dy + 0, dz + 1, sbb);
//	        placeBlockAtCurrentPosition(world, Blocks.stone, 0, dx, dy + 1, dz + 1, sbb);
//		}
//		
		
		placeBlockAtCurrentPosition(world, Blocks.air, 0, dx, dy + 0, dz, sbb);
        placeBlockAtCurrentPosition(world, Blocks.air, 0, dx, dy + 1, dz, sbb);
//        updateLight(world, dx, dy + 0, dz);
//        updateLight(world, dx, dy + 1, dz);
        
        if (getBlockAtCurrentPosition(world, dx, dy + 2, dz, sbb) != Blocks.air) {
        	placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, dx, dy + 2, dz, sbb);
        }
        
        // clear the door
		if (dx == 0) {
//			placeBlockAtCurrentPosition(world, Blocks.air, 0, dx - 1, dy + 0, dz, sbb);
//	        placeBlockAtCurrentPosition(world, Blocks.air, 0, dx - 1, dy + 1, dz, sbb);
	        updateLight(world, dx - 1, dy + 0, dz);
	        updateLight(world, dx - 1, dy + 1, dz);
		}
		if (dx == size - 1) {
//			placeBlockAtCurrentPosition(world, Blocks.air, 0, dx + 1, dy + 0, dz, sbb);
//	        placeBlockAtCurrentPosition(world, Blocks.air, 0, dx + 1, dy + 1, dz, sbb);
	        updateLight(world, dx + 1, dy + 0, dz);
	        updateLight(world, dx + 1, dy + 1, dz);
		}
		if (dz == 0) {
//			placeBlockAtCurrentPosition(world, Blocks.air, 0, dx, dy + 0, dz - 1, sbb);
//	        placeBlockAtCurrentPosition(world, Blocks.air, 0, dx, dy + 1, dz - 1, sbb);
	        updateLight(world, dx, dy + 0, dz - 1);
	        updateLight(world, dx, dy + 1, dz - 1);
		}
		if (dz == size - 1) {
//			placeBlockAtCurrentPosition(world, Blocks.air, 0, dx, dy + 0, dz + 1, sbb);
//	        placeBlockAtCurrentPosition(world, Blocks.air, 0, dx, dy + 1, dz + 1, sbb);
	        updateLight(world, dx, dy + 0, dz + 1);
	        updateLight(world, dx, dy + 1, dz + 1);
		}
	}
	
	public void updateLight(World world, int dx, int dy, int dz) {
		//world.updateAllLightTypes(getXWithOffset(dx, dz), getYWithOffset(dy), getZWithOffset(dx, dz));
	}
	
	/**
	 * Gets a random position in the specified direction that connects to stairs currently in the tower.
	 */
	public int[] getValidOpening(Random rand, int direction) {
		// variables!
		int wLength = size - 2; // wall length
		int offset = 1; // wall thickness
		
		// size 15 towers have funny landings, so don't generate on the very edge
		if (this.size == 15) {
			wLength = 11;
			offset = 2;
		}
		
		// for directions 0 or 2, the wall lies along the z axis
		if (direction == 0 || direction == 2) {
			int rx = direction == 0 ? size - 1 : 0;
			int rz = offset + rand.nextInt(wLength);
			int ry = getYByStairs(rz, rand, direction);
			
			return new int[] {rx, ry, rz};
		}
		
		// for directions 1 or 3, the wall lies along the x axis
		if (direction == 1 || direction == 3) {
			int rx = offset + rand.nextInt(wLength);
			int rz = direction == 1 ? size - 1 : 0;
			int ry = getYByStairs(rx, rand, direction);
			
			return new int[] {rx, ry, rz};
		}
		
		
		return new int[] {0, 0, 0};
	}



	/**
	 * Gets a Y value where the stairs meet the specified X coordinate.
	 * Also works for Z coordinates.
	 */
	protected int getYByStairs(int rx, Random rand, int direction) {
		// initialize some variables
		int rise = 1;
		int base = 0;
		
		if (size == 15) {
			rise = 10;
			// we lie a little here to get the towers off the ground
			base = (direction == 0 || direction == 2) ? 23 : 28;
		}
		if (size == 9) {
			rise = 6;
			base = (direction == 0 || direction == 2) ? 2 : 5;
		}
		if (size == 7) {
			rise = 4;
			base = (direction == 0 || direction == 2) ? 2 : 4;
		}
		if (size == 5) {
			rise = 4;
			// bleh, a switch.
			switch (direction) {
			case 0:
				base = 3;
				break;
			case 1:
				base = 2;
				break;
			case 2:
				base = 5;
				break;
			case 3:
				base = 4;
				break;
			}
		}
		
		int flights = ((height - 6 - base) / rise) + 1;
		
		if (base > 0 && flights > 0) {
			// pick a flight of stairs to be on
			int flightChosen = rand.nextInt(flights);
			// calculate where we would be if rx = 0
			int dy = (flightChosen * rise) + base;
			// the staircase (a/de)scends across the room.
			if (size == 15) {
				// blech, another dumb kludge here
				dy -= (direction == 0 || direction == 3) ? (rx - 2) / 2 : (size - rx - 3) / 2;
			}
			else {
				// the rest are fairly normal
				dy -= (direction == 0 || direction == 3) ? (rx - 1) / 2 : (size - rx - 2) / 2;
			}
//			// even xs can be one higher if they want.
//			if (rx % 2 == 0 && size != 15) {
//				dy += rand.nextInt(2);
//			}
			// don't go through the floor
			if (dy < 1) {
				dy = 1;
			}

			return dy;
		}

		return 0;
	}



	/**
	 * Makes 3 windows outside this tower.
	 * 
	 * The function currently looks "outside" to see if the window will be blocked, but it can't see into the future, so a tower built after this one may block it.
	 * 
	 * Maybe this could eventually have access to the list of bounding boxes for better accuracy?
	 */
	protected void makeWindows(World world, Random rand, StructureBoundingBox sbb, boolean real) {
		
		
		for (int i = 0; i < 4; i++) {
			boolean realWindows = real && !openingTowards[i];
			makeWindowBlock(world, size - 1, 2, size / 2, i, sbb, realWindows);
			makeWindowBlock(world, size - 1, 3, size / 2, i, sbb, realWindows);
			makeWindowBase(world, size - 1, 1, size / 2, i, sbb);
			if (height > 8) {
				makeWindowBlock(world, size - 1, height - 3, size / 2, i, sbb, realWindows);
				makeWindowBlock(world, size - 1, height - 4, size / 2, i, sbb, realWindows);
				makeWindowBase(world, size - 1, height - 5, size / 2, i, sbb);
			}
		}
	}



	/**
	 * Makes a window block.  Specify a point in a wall, and this function checks to 
	 * see if it is blocked on the inside or outside, and if not, adds a pane of glass.
	 */
	protected void makeWindowBlock(World world, int x, int y, int z, int rotation, StructureBoundingBox sbb, boolean realWindows) {
		int temp = this.getCoordBaseMode();
		this.setCoordBaseMode((this.getCoordBaseMode() + rotation) % 4);
		
		// look outside
		Block outside = getBlockAtCurrentPosition(world, x + 1, y, z, sbb);
		
		// look inside
		Block inside = getBlockAtCurrentPosition(world, x - 1, y, z, sbb);
		
		// make a window!
		if (realWindows && inside == Blocks.air && outside == Blocks.air) {
			placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, x, y, z, sbb);
		}
		else {
			// cobblestone where the window might have been
			placeBlockAtCurrentPosition(world, Blocks.cobblestone, 0, x, y, z, sbb);
		}
		
		this.setCoordBaseMode(temp);
				
	}
	
	/**
	 * Makes a window base
	 */
	protected void makeWindowBase(World world, int x, int y, int z, int rotation, StructureBoundingBox sbb) {
		int temp = this.getCoordBaseMode();
		this.setCoordBaseMode((this.getCoordBaseMode() + rotation) % 4);
		placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, x, y, z, sbb);
		this.setCoordBaseMode(temp);
				
	}
	
	/**
	 * Add stairs to this tower.
	 */
	protected boolean makeStairs(World world, Random rand, StructureBoundingBox sbb) {
		if (this.size == 15) {
			return makeStairs15(world, rand, sbb);
		}
		if (this.size == 9) {
			return makeStairs9(world, rand, sbb);
		}
		if (this.size == 7) {
			return makeStairs7(world, rand, sbb);
		}
		if (this.size == 5) {
			return makeStairs5(world, rand, sbb);
		}

		return false;
	}

	/**
	 * Stair maker for a size 5 tower
	 */
	protected boolean makeStairs5(World world, Random rand, StructureBoundingBox sbb) {
		// staircases rotating around the tower
		int rise = 1;
//		int numFlights = ((this.height - 3) / rise) - 1;
		int numFlights = (highestOpening / rise);
		for (int i = 0; i < numFlights; i++) {
			makeStairs5flight(world, rand, sbb, i * rise, 0 + (i * 3), 2);
		}

		return true;
	}
	
	/**
	 * Function called by makeStairs5 to place stair blocks
	 * 
	 * @param height
	 * @param rotation
	 */
	protected void makeStairs5flight(World world, Random rand, StructureBoundingBox sbb, int height, int rotation, int meta) {
		int temp = this.getCoordBaseMode();
		
		this.setCoordBaseMode((this.getCoordBaseMode() + rotation) % 4);
		
		BlockSlab singleSlabBlock = meta == 0 ? Blocks.stone_slab : Blocks.wooden_slab;
		Block doubleSlabBlock = meta == 0 ? Blocks.double_stone_slab : Blocks.planks;
		
		placeBlockAtCurrentPosition(world, singleSlabBlock, meta, 2, 1 + height, 3, sbb);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 3, 1 + height, 3, sbb);
		
		this.setCoordBaseMode(temp);
	}

	/**
	 * Stair maker for a size 7 tower
	 */
	protected boolean makeStairs7(World world, Random rand, StructureBoundingBox sbb) {
		// foot of stairs
		placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 2, 1, 1, 4, sbb);
		placeBlockAtCurrentPosition(world, Blocks.planks, 2, 1, 1, 5, sbb);
		
		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, 5, 1, 2, sbb);
		placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 5, 1, 1, sbb);
		
		// staircases rotating around the tower
		int rise = 2;
//		int numFlights = ((this.height - 3) / rise) - 1;
		int numFlights = (highestOpening / rise);
		for (int i = 0; i < numFlights; i++) {
			makeStairs7flight(world, rand, sbb, 1 + i * rise, 0 + (i * 3), 2);
			makeStairs7flight(world, rand, sbb, 1 + i * rise, 2 + (i * 3), 0);
		}

		return true;
	}
	
	/**
	 * Function called by makeStairs7 to place stair blocks
	 * 
	 * @param height
	 * @param rotation
	 */
	protected void makeStairs7flight(World world, Random rand, StructureBoundingBox sbb, int height, int rotation, int meta) {
		int temp = this.getCoordBaseMode();
		
		this.setCoordBaseMode((this.getCoordBaseMode() + rotation) % 4);
		
		BlockSlab singleSlabBlock = meta == 0 ? Blocks.stone_slab : Blocks.wooden_slab;
		Block doubleSlabBlock = meta == 0 ? Blocks.double_stone_slab : Blocks.planks;
		
		placeBlockAtCurrentPosition(world, singleSlabBlock, meta, 2, 1 + height, 5, sbb);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 3, 1 + height, 5, sbb);
		placeBlockAtCurrentPosition(world, singleSlabBlock, meta, 4, 2 + height, 5, sbb);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 5, 2 + height, 5, sbb);
		
		this.setCoordBaseMode(temp);
	}

	/**
	 * Stair maker for a size 9 tower
	 */
	protected boolean makeStairs9(World world, Random rand, StructureBoundingBox sbb) {
		// foot of stairs
		placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 2, 1, 1, 6, sbb);
		placeBlockAtCurrentPosition(world, Blocks.planks, 2, 1, 1, 7, sbb);

		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, 7, 1, 2, sbb);
		placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 7, 1, 1, sbb);
		
		// staircases rotating around the tower
		int rise = 3;
//		int numFlights = ((this.height - 3) / rise) - 1;
		int numFlights = (highestOpening / rise);
		for (int i = 0; i < numFlights; i++) {
			makeStairs9flight(world, rand, sbb, 1 + i * rise, 0 + (i * 3), 2);
			makeStairs9flight(world, rand, sbb, 1 + i * rise, 2 + (i * 3), 0);
		}
		
		return true;
	}
	
	/**
	 * Function called by makeStairs7 to place stair blocks
	 * 
	 * @param height
	 * @param rotation
	 */
	protected void makeStairs9flight(World world, Random rand, StructureBoundingBox sbb, int height, int rotation, int meta) {
		int temp = this.getCoordBaseMode();
		
		this.setCoordBaseMode((this.getCoordBaseMode() + rotation) % 4);
		
		Block singleSlabBlock = meta == 0 ? Blocks.stone_slab : Blocks.wooden_slab;
		Block doubleSlabBlock = meta == 0 ? Blocks.double_stone_slab : Blocks.planks;
		
		placeBlockAtCurrentPosition(world, singleSlabBlock, meta, 2, 1 + height, 7, sbb);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 3, 1 + height, 7, sbb);
		placeBlockAtCurrentPosition(world, singleSlabBlock, meta, 4, 2 + height, 7, sbb);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 5, 2 + height, 7, sbb);
		placeBlockAtCurrentPosition(world, singleSlabBlock, meta, 6, 3 + height, 7, sbb);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 7, 3 + height, 7, sbb);
		
		this.setCoordBaseMode(temp);
	}

	/**
	 * Stair maker for a size 15 tower
	 */
	protected boolean makeStairs15(World world, Random rand, StructureBoundingBox sbb) {
		// foot of stairs
		placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 2, 1, 1, 9, sbb);
		placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 2, 2, 1, 9, sbb);
		placeBlockAtCurrentPosition(world, Blocks.planks, 2, 1, 1, 10, sbb);
		placeBlockAtCurrentPosition(world, Blocks.planks, 2, 2, 1, 10, sbb);
		placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 2, 1, 2, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 2, 2, 2, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.planks, 2, 1, 2, 12, sbb);
		placeBlockAtCurrentPosition(world, Blocks.planks, 2, 2, 2, 12, sbb);
		placeBlockAtCurrentPosition(world, Blocks.planks, 2, 1, 2, 13, sbb);
		placeBlockAtCurrentPosition(world, Blocks.planks, 2, 2, 2, 13, sbb);
		
		placeBlockAtCurrentPosition(world, Blocks.planks, 2, 3, 2, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 3, 3, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 3, 4, 11, sbb);
//		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 3, 5, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.planks, 2, 3, 1, 10, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 3, 2, 10, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 3, 3, 10, sbb);
		placeBlockAtCurrentPosition(world, Blocks.planks, 2, 3, 1, 9, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 3, 2, 9, sbb);

		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, 13, 1, 5, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, 12, 1, 5, sbb);
		placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 13, 1, 4, sbb);
		placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 12, 1, 4, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, 13, 2, 3, sbb);
		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, 12, 2, 3, sbb);
		placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 13, 2, 2, sbb);
		placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 12, 2, 2, sbb);
		placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 13, 2, 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 12, 2, 1, sbb);
//		placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, 7, 1, 2, sbb);
//		placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 7, 1, 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 11, 2, 3, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 11, 3, 3, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 11, 4, 3, sbb);
//		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 11, 5, 3, sbb);
		placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 11, 1, 4, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 11, 2, 4, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 11, 3, 4, sbb);
		placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 11, 1, 5, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 11, 2, 5, sbb);
		
		
		// staircases rotating around the tower
		int rise = 5;
//		int numFlights = ((this.height - 3) / rise) - 1;
		int numFlights = (highestOpening / rise);
		for (int i = 0; i < numFlights; i++) {
			makeStairs15flight(world, rand, sbb, 2 + i * rise, 0 + (i * 3), 2);
			makeStairs15flight(world, rand, sbb, 2 + i * rise, 2 + (i * 3), 0);
		}
		
		return true;
	}
	
	/**
	 * Function called by makeStairs7 to place stair blocks
	 * 
	 * @param height
	 * @param rotation
	 */
	protected void makeStairs15flight(World world, Random rand, StructureBoundingBox sbb, int height, int rotation, int meta) {
		int temp = this.getCoordBaseMode();
		
		this.setCoordBaseMode((this.getCoordBaseMode() + rotation) % 4);
		
		Block singleSlabBlock = meta == 0 ? Blocks.stone_slab : Blocks.wooden_slab;
		Block doubleSlabBlock = meta == 0 ? Blocks.double_stone_slab : Blocks.planks;
		
		placeBlockAtCurrentPosition(world, singleSlabBlock, meta, 3, 1 + height, 13, sbb);
		randomlyPlaceBlock(world, sbb, rand, 0.9F, 4, 1 + height, 13, doubleSlabBlock, meta);
		placeBlockAtCurrentPosition(world, singleSlabBlock, meta, 5, 2 + height, 13, sbb);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 6, 2 + height, 13, sbb);
		placeBlockAtCurrentPosition(world, singleSlabBlock, meta, 7, 3 + height, 13, sbb);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 8, 3 + height, 13, sbb);
		placeBlockAtCurrentPosition(world, singleSlabBlock, meta, 9, 4 + height, 13, sbb);
		randomlyPlaceBlock(world, sbb, rand, 0.9F, 10, 4 + height, 13, doubleSlabBlock, meta);
		randomlyPlaceBlock(world, sbb, rand, 0.9F, 11, 5 + height, 13, singleSlabBlock, meta);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 12, 5 + height, 13, sbb);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 13, 5 + height, 13, sbb);

		randomlyPlaceBlock(world, sbb, rand, 0.9F, 3, 1 + height, 12, singleSlabBlock, meta);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 4, 1 + height, 12, sbb);
		placeBlockAtCurrentPosition(world, singleSlabBlock, meta, 5, 2 + height, 12, sbb);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 6, 2 + height, 12, sbb);
		randomlyPlaceBlock(world, sbb, rand, 0.9F, 7, 3 + height, 12, singleSlabBlock, meta);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 8, 3 + height, 12, sbb);
		placeBlockAtCurrentPosition(world, singleSlabBlock, meta, 9, 4 + height, 12, sbb);
		randomlyPlaceBlock(world, sbb, rand, 0.9F, 10, 4 + height, 12, doubleSlabBlock, meta);
		placeBlockAtCurrentPosition(world, singleSlabBlock, meta, 11, 5 + height, 12, sbb);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 12, 5 + height, 12, sbb);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 13, 5 + height, 12, sbb);

		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 4, 1 + height, 11, sbb);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 5, 2 + height, 11, sbb);
		randomlyPlaceBlock(world, sbb, rand, 0.9F, 6, 2 + height, 11, doubleSlabBlock, meta);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 7, 3 + height, 11, sbb);
		randomlyPlaceBlock(world, sbb, rand, 0.9F, 8, 3 + height, 11, doubleSlabBlock, meta);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 9, 4 + height, 11, sbb);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 10, 4 + height, 11, sbb);
		placeBlockAtCurrentPosition(world, doubleSlabBlock, meta, 11, 5 + height, 11, sbb);

		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4, 2 + height, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 5, 3 + height, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 6, 3 + height, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 7, 4 + height, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 8, 4 + height, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 9, 5 + height, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 10, 5 + height, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 11, 6 + height, 11, sbb);

		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4, 3 + height, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 6, 4 + height, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 8, 5 + height, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 10, 6 + height, 11, sbb);
		placeBlockAtCurrentPosition(world, Blocks.fence, 0, 11, 7 + height, 11, sbb);

//		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 11, 8 + height, 11, sbb);

		this.setCoordBaseMode(temp);
	}
	





	/**
	 * Makes paintings of the minimum size or larger on the specified wall
	 */
	protected void generatePaintingsOnWall(World world, Random rand, int howMany, int floorLevel, int direction, int minSize, StructureBoundingBox sbb) {
		for (int i = 0; i < howMany; i++) {
			// get some random coordinates on the wall in the chunk
			ChunkCoordinates pCoords = getRandomWallSpot(rand, floorLevel, direction, sbb);
			
			// initialize a painting object
			EntityPainting painting = new EntityPainting(world, pCoords.posX, pCoords.posY, pCoords.posZ, direction); 
			painting.art = getPaintingOfSize(rand, minSize);
			painting.setDirection(direction);
			
			// temporary function, make a real bounding box for the painting
			
			// check if we can fit a painting there
			if (checkPainting(world, painting, sbb)) {
				// place the painting
				//System.out.println("Painting success " + painting.art.title + " at " + painting.xPosition + " , " + painting.yPosition + ", " + painting.zPosition + " : " + painting.field_82332_a);
				world.spawnEntityInWorld(painting);
			}
			else {
				//System.out.println("Painting fail!! " + painting.art.title + " at " + painting.xPosition + " , " + painting.yPosition + ", " + painting.zPosition + " : " + painting.field_82332_a);
			}
		}
	}
	
	/**
	 * At least one of the painting's parameters must be the specified size or greater
	 */
	protected EnumArt getPaintingOfSize(Random rand, int minSize) {
        ArrayList<EnumArt> valid = new ArrayList<EnumArt>();

        for (EnumArt art : EnumArt.values()) {
            if (art.sizeX >= minSize || art.sizeY >= minSize)
            {
                valid.add(art);
            }
        }

        if (valid.size() > 0)
        {
            return valid.get(rand.nextInt(valid.size()));
        }
        else {
        	return null;
        }
	}

	/**
	 * This is similar to EntityPainting.isOnValidSurface, except that it does not check for a solid wall behind the painting.
	 */
	@SuppressWarnings("unchecked")
	protected boolean checkPainting(World world, EntityPainting painting, StructureBoundingBox sbb) {
		
		if (painting == null) {
			return false;
		}
		
		//TODO: this is a temporary workaround to fix the bad painting bounding boxes
		
		AxisAlignedBB largerBox;
		if (painting.hangingDirection == 0 || painting.hangingDirection == 2) {
			largerBox = painting.boundingBox.expand(1, 1, 0.06);
		}
		else {
			largerBox = painting.boundingBox.expand(0.06, 1, 1);
		}
		
        if (world.getCollidingBoundingBoxes(painting, largerBox).size() > 0)
        {
            return false;
        }
        
        List<Entity> collidingEntities = world.getEntitiesWithinAABBExcludingEntity(painting, largerBox);

        for (Entity entityOnList : collidingEntities)
        {
            if (entityOnList instanceof EntityHanging)
            {
                return false;
            }
        }

        return true;


	}

	/**
	 * This returns the real-world coordinates of a possible painting or torch spot on the specified wall of this tower.
	 */
	protected ChunkCoordinates getRandomWallSpot(Random rand, int floorLevel, int direction, StructureBoundingBox sbb) {
		int minX = this.boundingBox.minX + 2;
		int maxX = this.boundingBox.maxX - 2;

		int minY = this.boundingBox.minY + floorLevel + 2;
		int maxY = this.boundingBox.maxY - 2;
		
		int minZ = this.boundingBox.minZ + 2;
		int maxZ = this.boundingBox.maxZ - 2;

		// constrain the paintings to one wall
		// these directions correspond to painting facing directions, not necessarily to the structure orienting directions
		if (direction == 0) {
			minZ = this.boundingBox.minZ;
			maxZ = this.boundingBox.minZ;
		}
		if (direction == 1) {
			maxX = this.boundingBox.maxX;
			minX = this.boundingBox.maxX;
		}
		if (direction == 2) {
			maxZ = this.boundingBox.maxZ;
			minZ = this.boundingBox.maxZ;
		}
		if (direction == 3) {
			minX = this.boundingBox.minX;
			maxX = this.boundingBox.minX;
		}
		
		// try 30 times to get a proper result
		for (int i = 0; i < 30; i++) {
			int cx = minX + (maxX > minX ? rand.nextInt(maxX - minX) : 0);
			int cy = minY + (maxY > minY ? rand.nextInt(maxY - minY) : 0);
			int cz = minZ + (maxZ > minZ ? rand.nextInt(maxZ - minZ) : 0);
			
			if (sbb.isVecInside(cx, cy, cz)) {
//				System.out.println("Found a valid random spot on the wall.  It's " + cx + ", " + cy + ", " + cz );
				return new ChunkCoordinates(cx, cy, cz);
			}
		}
		
		// I guess we didn't get one
//		System.out.println("We didn't find a valid random spot on the wall.");
		return null;
	}

	/**
	 * This method is for final castle towers actually.  
	 * We need to break up this class into a more abstract tower class and a concrete lich tower class one day
	 */
	protected void makeGlyphBranches(World world, Random rand, int meta, StructureBoundingBox sbb) {
		// pick a random side of the tower
		int rotation = rand.nextInt(4);
		
		// start somewhere in the lower part
		int startHeight = rand.nextInt((int) (this.height * 0.66F));
		
		// near the middle
		int startZ = 3 + rand.nextInt(this.size - 6);
		
		// make a line all the way down to the foundation
		int dx = this.getXWithOffsetAsIfRotated(0, startZ, rotation);
		int dz = this.getZWithOffsetAsIfRotated(0, startZ, rotation);
		if (sbb.isVecInside(dx, this.boundingBox.minY + 1, dz)) {
			for (int dy = this.getYWithOffset(startHeight); dy > 0; dy--) {
				if (world.getBlock(dx, dy, dz) == TFBlocks.castleBlock) {
					//System.out.println("placing glyph brick over " + world.getBlock(dx, dy, dz));
					world.setBlock(dx, dy, dz, TFBlocks.castleMagic, meta, 2);
				} else {
					//System.out.println("Stopping glyphs because block is " + world.getBlock(dx, dy, dz));
					//System.out.println("dy = " + dy + " because startHeight = " + startHeight + " and startZ = " + startZ);
					break;
				}
			}
		}
		
		// go left a little
		int leftOffset = startZ - (1 + rand.nextInt(3));
		int leftHeight = rand.nextInt(this.height - startHeight);
		if (leftOffset >= 0) {
			for (int z = startZ; z > leftOffset; z--) {
				this.placeBlockRotated(world, TFBlocks.castleMagic, meta, 0, startHeight, z, rotation, sbb);
			}
			for (int y = startHeight; y < (startHeight + leftHeight); y++) {
				this.placeBlockRotated(world, TFBlocks.castleMagic, meta, 0, y, leftOffset, rotation, sbb);
			}

		}
		
		// go right a little
		int rightOffset = startZ + (1 + rand.nextInt(3));
		int rightHeight = rand.nextInt(this.height - startHeight);
		if (rightOffset < this.size - 1) {
			for (int z = startZ; z < rightOffset; z++) {
				this.placeBlockRotated(world, TFBlocks.castleMagic, meta, 0, startHeight, z, rotation, sbb);
			}
			for (int y = startHeight; y < (startHeight + rightHeight); y++) {
				this.placeBlockRotated(world, TFBlocks.castleMagic, meta, 0, y, rightOffset, rotation, sbb);
			}

		}
	}

}
