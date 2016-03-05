package twilightforest.structures.icetower;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.TFTreasure;
import twilightforest.structures.StructureTFComponent;
import twilightforest.structures.lichtower.ComponentTFTowerWing;

public class ComponentTFIceTowerWing extends ComponentTFTowerWing 
{

	protected static final int SIZE = 11;
	private static final int RANGE = (int) (SIZE * 1.6F);
	
	boolean hasBase = false;
	protected int treasureFloor = -1; 

	public ComponentTFIceTowerWing() {
		super();
	}

	protected ComponentTFIceTowerWing(int i, int x, int y, int z, int pSize, int pHeight, int direction) {
		super(i, x, y, z, pSize, pHeight, direction);
	}
	
	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		
        par1NBTTagCompound.setBoolean("hasBase", this.hasBase);
        par1NBTTagCompound.setInteger("treasureFloor", this.treasureFloor);
	}

	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);
        this.hasBase = par1NBTTagCompound.getBoolean("hasBase");
        this.treasureFloor = par1NBTTagCompound.getInteger("treasureFloor");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void buildComponent(StructureComponent parent, List list, Random rand) {
		if (parent != null && parent instanceof StructureTFComponent)
		{
			this.deco = ((StructureTFComponent)parent).deco;
		}
		
		// we should have a door where we started
		addOpening(0, 1, size / 2, 2);
		
		// should we build a base
		this.hasBase = this.shouldHaveBase(rand);
		
		// limit sprawl to a reasonable amount
		if (this.getComponentType() < 5) {
			
			int dirOffset = rand.nextInt(4);
			
			// make sub towers
			for (int i = 0; i < 4; i++) {
				
				int dir = (dirOffset + i) % 4;
				
//				if (rand.nextInt(6) < this.getComponentType())
//				{
//					continue;
//				}
				
				int[] dest = getValidOpening(rand, dir);

				if (this.getComponentType() == 4 && (parent instanceof ComponentTFIceTowerMain) && !((ComponentTFIceTowerMain)parent).hasBossWing) {
					//System.out.println("Making boss tower");
					boolean hasBoss = makeBossTowerWing(list, rand, this.getComponentType() + 1, dest[0], dest[1], dest[2], 15, 41, dir);
					((ComponentTFIceTowerMain)parent).hasBossWing = hasBoss;
				} else {
					int childHeight = (rand.nextInt(2) + rand.nextInt(2) + 2) * 10 + 1;
					makeTowerWing(list, rand, this.getComponentType() + 1, dest[0], dest[1], dest[2], SIZE, childHeight, dir);
				}
			}
		}
		
		// figure out where the treasure goes
		int floors = this.height / 10;
		
		// set treasure to a floor if we need to
		if (this.treasureFloor == -1 && floors > 1) {
			this.treasureFloor = rand.nextInt(floors - 1);
		}
	
		// add a roof?
		makeARoof(parent, list, rand);
	
		// beard?
		if (!this.hasBase) {
			makeABeard(parent, list, rand);
		}

	}

	protected boolean shouldHaveBase(Random rand) {
		return this.getComponentType() == 0 || rand.nextBoolean();
	}

	/**
	 * Have we strayed more than range blocks away from the center?
	 */
	private boolean isOutOfRange(StructureComponent parent, int nx, int ny, int nz, int range) {
			
//		System.out.println("x range is " + Math.abs(nx - parent.getBoundingBox().minX));
//		System.out.println("z range is " + Math.abs(nz - parent.getBoundingBox().minZ));
//		System.out.println("nz is " + nz + ", parent.minz is " + parent.getBoundingBox().minZ);
		
		return Math.abs(nx - parent.getBoundingBox().getCenterX()) > range
				|| Math.abs(nz - parent.getBoundingBox().getCenterZ()) > range;
	}

	/**
	 * Make a new wing
	 */
	public boolean makeTowerWing(List<StructureComponent> list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, int rotation) {
		int direction = (getCoordBaseMode() + rotation) % 4;
		int[] dx = offsetTowerCoords(x, y, z, wingSize, direction);
		
		//System.out.println("Making tower, index = " + index + ", list.size() = " + list.size());

		// stop if out of range
		if (isOutOfRange((StructureComponent) list.get(0), dx[0], dx[1], dx[2], RANGE))
		{
			return false;
		}

		ComponentTFIceTowerWing wing = new ComponentTFIceTowerWing(index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);
		// check to see if it intersects something already there
		StructureComponent intersect = StructureComponent.findIntersecting(list, wing.getBoundingBox());
		if (intersect == null || intersect == this) {
			list.add(wing);
			wing.buildComponent(list.get(0), list, rand);
			addOpening(x, y, z, rotation);
			return true;
		}
		else
		{
			return false;
		}

	}

	/**
	 * Make a new wing
	 */
	public boolean makeBossTowerWing(List<StructureComponent> list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, int rotation) {

		int direction = (getCoordBaseMode() + rotation) % 4;
		int[] dx = offsetTowerCoords(x, y, z, wingSize, direction);

		ComponentTFIceTowerWing wing = new ComponentTFIceTowerBossWing(index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);
		// check to see if it intersects something already there
		StructureComponent intersect = StructureComponent.findIntersecting(list, wing.getBoundingBox());
		if (intersect == null || intersect == this) {
			list.add(wing);
			wing.buildComponent(list.get(0), list, rand);
			addOpening(x, y, z, rotation);
			return true;
		}
		else
		{
			return false;
		}

	}


	/**
	 * Gets a Y value where the stairs meet the specified X coordinate.
	 * Also works for Z coordinates.
	 */
	protected int getYByStairs(int rx, Random rand, int direction) {
		
		int floors = this.height / 10;
	
		return 11 + (rand.nextInt(floors - 1) * 10);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));
	
		// make walls
		//fillWithMetadataBlocks(world, sbb, 0, 0, 0, size - 1, height - 1, size - 1, deco.blockID, deco.blockMeta, Blocks.air, 0, false);
		fillWithRandomizedBlocks(world, sbb, 0, 0, 0, size - 1, height - 1, size - 1, false, rand, deco.randomBlocks);

		// clear inside
		fillWithAir(world, sbb, 1, 1, 1, size - 2, height - 2, size - 2);
	
		if (this.hasBase)
		{
			// deco to ground
			for (int x = 0; x < this.size; x++)
			{
				for (int z = 0; z < this.size; z++)
				{
					this.func_151554_b(world, deco.blockID, deco.blockMeta, x, -1, z, sbb);
				}
			}
		}
		
	 	// nullify sky light
		this.nullifySkyLightForBoundingBox(world);
		
		// make floors
		makeFloorsForTower(world, decoRNG, sbb);
	 	
	 	// openings
	 	makeOpenings(world, sbb);
	
	
	 	return true;
	}
	
	/**
	 * Nullify all the sky light in this component bounding box
	 */
	public void nullifySkyLightForBoundingBox(World world) {
		this.nullifySkyLight(world, boundingBox.minX + 1, boundingBox.minY + 1, boundingBox.minZ + 1, boundingBox.maxX - 1, boundingBox.maxY - 1, boundingBox.maxZ - 1);
	}

	protected void makeFloorsForTower(World world, Random decoRNG, StructureBoundingBox sbb) {
		int floors = this.height / 10;

		int ladderDir = 3;
		int downLadderDir = -1;

		// divide the tower into floors
		int floorHeight = 10;
		for (int i = 0; i < floors - 1; i++) {
			// put down a ceiling
			placeFloor(world, decoRNG, sbb, floorHeight, i);

			downLadderDir = ladderDir;
			ladderDir++;
			ladderDir %= 4;
			
			decorateFloor(world, decoRNG, i, (i * floorHeight), (i * floorHeight) + floorHeight, ladderDir, downLadderDir, sbb);

		}
		
		int topFloor = floors - 1;
		decorateTopFloor(world, decoRNG, topFloor, (topFloor * floorHeight), (topFloor * floorHeight) + floorHeight, ladderDir, downLadderDir, sbb);

	}

	/**
	 * Put down planks or whatevs for a floor
	 */
	protected void placeFloor(World world, Random rand, StructureBoundingBox sbb, int floorHeight, int floor) {
		for (int x = 1; x < size - 1; x++) {
			for (int z = 1; z < size - 1; z++) {
				placeBlockAtCurrentPosition(world, deco.floorID, deco.floorMeta, x, (floor * floorHeight) + floorHeight, z, sbb);
			}
		}
	}
	
	/**
	 * Make an opening in this tower for a door.  This now only makes one opening, so you need two
	 */
	protected void makeDoorOpening(World world, int dx, int dy, int dz, StructureBoundingBox sbb) {
		super.makeDoorOpening(world, dx, dy, dz, sbb);
        
        if (getBlockAtCurrentPosition(world, dx, dy + 2, dz, sbb) != Blocks.air) {
        	placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, dx, dy + 2, dz, sbb);
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
		boolean hasTreasure = (this.treasureFloor == floor);
		
		switch (rand.nextInt(8)) {
		case 0:
			//this.fillBlocksRotated(world, sbb, 9, bottom + 5, 1, 10, top + 1, 7, Blocks.wool, ladderUpDir, ladderUpDir);
			if (isNoDoorAreaRotated(9, bottom + 5, 1, 10, top + 1, 7, ladderUpDir)) {
				decorateWraparoundWallSteps(world, rand, bottom, top, ladderUpDir, ladderDownDir, hasTreasure, sbb);
				break;
			} // fall through otherwise
		case 1:
			//this.fillBlocksRotated(world, sbb, 7, bottom, 0, 10, top + 1, 10, Blocks.wool, ladderUpDir, ladderUpDir);
			if (isNoDoorAreaRotated(7, bottom, 0, 10, top + 1, 10, ladderUpDir)) {
				decorateFarWallSteps(world, rand, bottom, top, ladderUpDir, ladderDownDir, hasTreasure, sbb);
				break;
			} // fall through otherwise
		case 2:
			if (isNoDoorAreaRotated(9, bottom + 5, 1, 10, top + 1, 7, ladderUpDir)) {
				decorateWraparoundWallStepsPillars(world, rand, bottom, top, ladderUpDir, ladderDownDir, hasTreasure, sbb);
				break;
			} // fall through otherwise
		case 3:
			decoratePlatform(world, rand, bottom, top, ladderUpDir, ladderDownDir, hasTreasure, sbb);
			break;
		case 4:
			decoratePillarParkour(world, rand, bottom, top, ladderUpDir, ladderDownDir, hasTreasure, sbb);
			break;
		case 5:
			decoratePillarPlatforms(world, rand, bottom, top, ladderUpDir, ladderDownDir, hasTreasure, sbb);
			break;
		case 6:
			decoratePillarPlatformsOutside(world, rand, bottom, top, ladderUpDir, ladderDownDir, hasTreasure, sbb);
			break;
		case 7:
		default:
			decorateQuadPillarStairs(world, rand, bottom, top, ladderUpDir, ladderDownDir, hasTreasure, sbb);
			break;
		}

	}

	private boolean isNoDoorAreaRotated(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int rotation) {
		boolean isClear = true;
        // make a bounding box of the area
		StructureBoundingBox exclusionBox;
		switch (rotation) {
		case 0:
		default:
			exclusionBox = new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
			break;
		case 1:
			exclusionBox = new StructureBoundingBox(this.size - 1 - maxZ, minY, minX, this.size - 1 - minZ, maxY, maxX);
			break;
		case 2:
			exclusionBox = new StructureBoundingBox(this.size - 1 - maxX, minY, this.size - 1 - maxZ, this.size - 1 - minX, maxY, this.size - 1 - minZ);
			break;
		case 3:
			exclusionBox = new StructureBoundingBox(minZ, minY, this.size - 1 - maxX, maxZ, maxY, this.size - 1 - minX);
			break;
		}
		
		for (ChunkCoordinates door : this.openings) {
			if (exclusionBox.isVecInside(door.posX, door.posY, door.posZ)) {
				isClear = false;
				
				//System.out.println("Found door in exclusion box, door = " + door);
			}
		}
		
		return isClear;
	}

	protected void decorateTopFloor(World world, Random rand, int floor, int bottom, int top, int ladderUpDir, int ladderDownDir, StructureBoundingBox sbb) {
		if (rand.nextBoolean()) {
			decoratePillarsCorners(world, rand, bottom, top, ladderDownDir, sbb);
		} else {
			decoratePillarsGrid(world, rand, bottom, top, ladderDownDir, sbb);
		}
		
		// generate treasure last so it doesn't get deleted
		if (this.isDeadEnd()) {
			decorateTopFloorTreasure(world, rand, bottom, top, ladderDownDir, sbb);
		}
		
	}

	private void decorateTopFloorTreasure(World world, Random rand, int bottom, int top, int rotation, StructureBoundingBox sbb) {
		this.fillBlocksRotated(world, sbb, 5, bottom + 1, 5, 5, bottom + 4, 5, deco.pillarID, deco.pillarMeta, rotation);

		this.placeTreasureAtCurrentPosition(world, null, 5, bottom + 5, 5, TFTreasure.aurora_room, sbb);
	}

	private void decoratePillars(World world, Random rand, int bottom, int top, int rotation, StructureBoundingBox sbb) {
		this.fillBlocksRotated(world, sbb, 3, bottom + 1, 3, 3, top - 1, 3, deco.pillarID, deco.pillarMeta, rotation);
		this.fillBlocksRotated(world, sbb, 7, bottom + 1, 3, 7, top - 1, 3, deco.pillarID, deco.pillarMeta, rotation);
		this.fillBlocksRotated(world, sbb, 3, bottom + 1, 7, 3, top - 1, 7, deco.pillarID, deco.pillarMeta, rotation);
		this.fillBlocksRotated(world, sbb, 7, bottom + 1, 7, 7, top - 1, 7, deco.pillarID, deco.pillarMeta, rotation);
	}

	private void decoratePillarsGrid(World world, Random rand, int bottom, int top, int rotation, StructureBoundingBox sbb) {
		int beamMetaNS = ((this.coordBaseMode + rotation) % 2 == 0) ? 4 : 8;
		int beamMetaEW = (beamMetaNS == 4) ? 8 : 4;
		
		this.fillBlocksRotated(world, sbb, 3, bottom + 5, 1, 3, bottom + 5, 9, deco.pillarID, deco.pillarMeta + beamMetaEW, rotation);
		this.fillBlocksRotated(world, sbb, 7, bottom + 5, 1, 7, bottom + 5, 9, deco.pillarID, deco.pillarMeta + beamMetaEW, rotation);
		this.fillBlocksRotated(world, sbb, 1, bottom + 5, 3, 9, bottom + 5, 3, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);
		this.fillBlocksRotated(world, sbb, 1, bottom + 5, 7, 9, bottom + 5, 7, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);
		
		this.decoratePillars(world, rand, bottom, top, rotation, sbb);
	}

	private void decoratePillarsCorners(World world, Random rand, int bottom, int top, int rotation, StructureBoundingBox sbb) {
		int beamMetaNS = ((this.coordBaseMode + rotation) % 2 == 0) ? 4 : 8;
		int beamMetaEW = (beamMetaNS == 4) ? 8 : 4;
		
		this.fillBlocksRotated(world, sbb, 3, bottom + 5, 1, 3, bottom + 5, 9, deco.pillarID, deco.pillarMeta + beamMetaEW, rotation);
		this.fillBlocksRotated(world, sbb, 7, bottom + 5, 1, 7, bottom + 5, 9, deco.pillarID, deco.pillarMeta + beamMetaEW, rotation);
		this.fillBlocksRotated(world, sbb, 1, bottom + 5, 3, 9, bottom + 5, 3, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);
		this.fillBlocksRotated(world, sbb, 1, bottom + 5, 7, 9, bottom + 5, 7, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);
		
		this.fillAirRotated(world, sbb, 3, bottom + 5, 3, 7, bottom + 5, 7, rotation);
		
		this.decoratePillars(world, rand, bottom, top, rotation, sbb);
	}

	private void decorateFarWallSteps(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, boolean hasTreasure, StructureBoundingBox sbb) {
		
		// far set of stairs
		for (int z = 1; z < 10; z++)
		{
			int y = bottom + 10 - (z / 2);
			this.placeBlockRotated(world, (z % 2 == 0) ? deco.pillarID : deco.platformID, (z % 2 == 0) ? deco.pillarMeta : deco.platformMeta, 9, y, z, ladderUpDir, sbb);
			for (int by = bottom + 1; by < y; by++)
			{
				this.placeBlockRotated(world, deco.pillarID, deco.pillarMeta, 9, by, z, ladderUpDir, sbb);
			}
		}
		
		// near set of stairs
		for (int z = 1; z < 10; z++)
		{
			int y = bottom + 1 + (z / 2);
			this.placeBlockRotated(world, (z % 2 == 0) ? deco.platformID : deco.pillarID, (z % 2 == 0) ? deco.platformMeta : deco.pillarMeta, 8, y, z, ladderUpDir, sbb);
			for (int by = bottom + 1; by < y; by++)
			{
				this.placeBlockRotated(world, deco.pillarID, deco.pillarMeta, 8, by, z, ladderUpDir, sbb);
			}
		}
		
		// entry stair
		this.placeBlockRotated(world, deco.platformID, deco.platformMeta, 7, bottom + 1, 1, ladderUpDir, sbb);

		
		// clear floor above
		for (int z = 2; z < 7; z++)
		{
			this.placeBlockRotated(world, Blocks.air, 0, 9, top, z, ladderUpDir, sbb);
		}
		
		// treasure!
		if (hasTreasure) {
			this.placeTreasureRotated(world, 1, bottom + 8, 5, ladderUpDir, TFTreasure.aurora_cache, false, sbb);
			int beamMetaNS = ((this.coordBaseMode + ladderUpDir) % 2 == 0) ? 4 : 8;
			this.placeBlockRotated(world, deco.pillarID, deco.pillarMeta + beamMetaNS, 1, bottom + 7, 5, ladderUpDir, sbb);
		}
	}

	private void decorateWraparoundWallSteps(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, boolean hasTreasure, StructureBoundingBox sbb) {
		
		// far set of stairs
		for (int z = 1; z < 10; z++)
		{
			int y = bottom + 10 - (z / 2);
			this.placeBlockRotated(world, deco.platformID, deco.platformMeta + ((z % 2 == 0) ? 8 : 0), 9, y, z, ladderUpDir, sbb);
		}
		
		// right set of stairs
		for (int x = 1; x < 9; x++)
		{
			int y = bottom + 2 + ((x - 1) / 2);
			this.placeBlockRotated(world, deco.platformID, deco.platformMeta + ((x % 2 == 0) ? 8 : 0), x, y, 9, ladderUpDir, sbb);
		}
		
		// entry stairs
		this.placeBlockRotated(world, deco.platformID, deco.platformMeta + 8, 1, bottom + 1, 8, ladderUpDir, sbb);
		this.placeBlockRotated(world, deco.platformID, deco.platformMeta, 1, bottom + 1, 7, ladderUpDir, sbb);

		
		// clear floor above
		for (int z = 2; z < 7; z++)
		{
			this.placeBlockRotated(world, Blocks.air, 0, 9, top, z, ladderUpDir, sbb);
		}
		
		// treasure!
		if (hasTreasure) {
			this.placeTreasureRotated(world, 1, bottom + 5, 5, ladderUpDir, TFTreasure.aurora_cache, false, sbb);
			int beamMetaNS = ((this.coordBaseMode + ladderUpDir) % 2 == 0) ? 4 : 8;
			this.placeBlockRotated(world, deco.pillarID, deco.pillarMeta + beamMetaNS, 1, bottom + 4, 5, ladderUpDir, sbb);
		}
	}
	
	private void decorateWraparoundWallStepsPillars(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, boolean hasTreasure, StructureBoundingBox sbb) {
		int rotation = ladderDownDir;
		int beamMetaNS = ((this.coordBaseMode + rotation) % 2 == 0) ? 4 : 8;
		int beamMetaEW = (beamMetaNS == 4) ? 8 : 4;
		
		this.decorateWraparoundWallSteps(world, rand, bottom, top, ladderUpDir, ladderDownDir, false, sbb);
		this.decoratePillars(world, rand, bottom, top, rotation, sbb);
		
		this.fillBlocksRotated(world, sbb, 3, bottom + 5, 1, 3, bottom + 5, 2, deco.pillarID, deco.pillarMeta + beamMetaEW, rotation);
		this.fillBlocksRotated(world, sbb, 7, bottom + 5, 1, 7, bottom + 5, 2, deco.pillarID, deco.pillarMeta + beamMetaEW, rotation);
		this.fillBlocksRotated(world, sbb, 8, bottom + 5, 3, 9, bottom + 5, 3, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);
		this.fillBlocksRotated(world, sbb, 8, bottom + 5, 7, 9, bottom + 5, 7, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);

		this.fillBlocksRotated(world, sbb, 1, bottom + 2, 3, 2, bottom + 2, 3, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);
		this.fillBlocksRotated(world, sbb, 1, bottom + 6, 3, 2, bottom + 6, 3, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);
		this.fillBlocksRotated(world, sbb, 1, bottom + 4, 7, 2, bottom + 4, 7, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);
		this.fillBlocksRotated(world, sbb, 1, bottom + 8, 7, 2, bottom + 8, 7, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);
		this.fillBlocksRotated(world, sbb, 3, bottom + 6, 8, 3, bottom + 6, 9, deco.pillarID, deco.pillarMeta + beamMetaEW, rotation);
		this.fillBlocksRotated(world, sbb, 7, bottom + 8, 8, 7, bottom + 8, 9, deco.pillarID, deco.pillarMeta + beamMetaEW, rotation);

		// treasure!
		if (hasTreasure) {
			this.placeTreasureRotated(world, 7, bottom + 6, 1, ladderUpDir, TFTreasure.aurora_cache, false, sbb);
		}
	}
	
	private void decoratePlatform(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, boolean hasTreasure, StructureBoundingBox sbb) {
		this.decoratePillars(world, rand, bottom, top, ladderDownDir, sbb);
		this.fillBlocksRotated(world, sbb, 3, bottom + 5, 3, 7, bottom + 5, 7, deco.floorID, deco.floorMeta, ladderDownDir);

		// one flight
		for (int z = 6; z < 10; z++) {
			int y = bottom - 2 + (z / 2);
			this.placeBlockRotated(world, deco.platformID, deco.platformMeta + ((z % 2 == 1) ? 8 : 0), 1, y, z, ladderDownDir, sbb);
		}
		// two flight
		for (int x = 2; x < 6; x++)
		{
			int y = bottom + 2 + (x / 2);
			this.placeBlockRotated(world, deco.platformID, deco.platformMeta + ((x % 2 == 1) ? 8 : 0), x, y, 9, ladderDownDir, sbb);
		}
		// connector
		this.placeBlockRotated(world, deco.platformID, deco.platformMeta, 5, bottom + 5, 8, ladderDownDir, sbb);
	
		// connector
		this.placeBlockRotated(world, deco.platformID, deco.platformMeta, 5, bottom + 6, 2, ladderUpDir, sbb);
		// two flight
		for (int x = 5; x < 10; x++)
		{
			int y = bottom + 4 + (x / 2);
			this.placeBlockRotated(world, deco.platformID, deco.platformMeta + ((x % 2 == 1) ? 8 : 0), x, y, 1, ladderUpDir, sbb);
			if (x > 6) {
				this.placeBlockRotated(world, Blocks.air, 0, x, top, 1, ladderUpDir, sbb);
			}
		}
		// one flight
		for (int z = 2; z < 5; z++) {
			int y = bottom + 8 + (z / 2);
			this.placeBlockRotated(world, Blocks.air, 0, 9, top, z, ladderUpDir, sbb);
			this.placeBlockRotated(world, deco.platformID, deco.platformMeta + ((z % 2 == 1) ? 8 : 0), 9, y, z, ladderUpDir, sbb);
		}
		
		// treasure!
		if (hasTreasure) {
			this.placeTreasureRotated(world, 3, bottom + 6, 5, ladderDownDir, TFTreasure.aurora_cache, false, sbb);
		}
	}
	
	private void decorateQuadPillarStairs(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, boolean hasTreasure, StructureBoundingBox sbb) {
		this.decoratePillars(world, rand, bottom, top, ladderDownDir, sbb);

		// one flight
		for (int z = 6; z < 9; z++) {
			int y = bottom - 2 + (z / 2);
			this.placeBlockRotated(world, deco.platformID, deco.platformMeta + ((z % 2 == 1) ? 8 : 0), 2, y, z, ladderDownDir, sbb);
		}
		// two flight
		for (int x = 3; x < 9; x++)
		{
			int y = bottom + 1 + (x / 2);
			this.placeBlockRotated(world, deco.platformID, deco.platformMeta + ((x % 2 == 1) ? 8 : 0), x, y, 8, ladderDownDir, sbb);
		}
		// three flight
		for (int z = 7; z > 1; z--) {
			int y = top - 2 - ((z - 1) / 2);
			if (z < 4) {
				this.placeBlockRotated(world, Blocks.air, 0, 8, top, z, ladderDownDir, sbb);
			}
			this.placeBlockRotated(world, deco.platformID, deco.platformMeta + ((z % 2 == 1) ? 8 : 0), 8, y, z, ladderDownDir, sbb);
		}
		// last flight
		for (int x = 7; x > 3; x--) {
			int y = top + 1 - ((x - 1) / 2);
			this.placeBlockRotated(world, Blocks.air, 0, x, top, 2, ladderDownDir, sbb);
			this.placeBlockRotated(world, deco.platformID, deco.platformMeta + ((x % 2 == 1) ? 8 : 0), x, y, 2, ladderDownDir, sbb);
		}
		
		// treasure!
		if (hasTreasure) {
			this.placeTreasureRotated(world, 3, bottom + 7, 7, ladderUpDir, TFTreasure.aurora_cache, false, sbb);
		}
	}
	
	private void decoratePillarPlatforms(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, boolean hasTreasure, StructureBoundingBox sbb) {
		// platforms
		for (int i = 1; i < 10; i++) {
			int rotation = (ladderUpDir + i) % 4;
			this.fillBlocksRotated(world, sbb, 2, bottom + i, 2, 4, bottom + i, 4, deco.floorID, deco.floorMeta, rotation);
		}
		
		// clear
		this.fillAirRotated(world, sbb, 2, top, 2, 8, top, 4, ladderUpDir);
		this.fillAirRotated(world, sbb, 2, top, 2, 4, top, 6, ladderUpDir);
		
		// extra pillar tops
		this.placeBlockRotated(world, deco.pillarID, deco.pillarMeta, 7, top, 3, ladderUpDir, sbb);
		this.placeBlockRotated(world, deco.pillarID, deco.pillarMeta, 3, top, 3, ladderUpDir, sbb);
		
		this.decoratePillars(world, rand, bottom, top, ladderUpDir, sbb);
		
		// treasure!
		if (hasTreasure) {
			this.placeTreasureRotated(world, 3, bottom + 5, 2, ladderUpDir, TFTreasure.aurora_cache, false, sbb);
		}
	}
	
	private void decoratePillarPlatformsOutside(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, boolean hasTreasure, StructureBoundingBox sbb) {
		// platforms
		for (int i = 1; i < 8; i++) {
			int rotation = (ladderUpDir + i) % 4;
			this.fillBlocksRotated(world, sbb, 1, bottom + i, 1, 3, bottom + i, 3, deco.platformID, deco.platformMeta, rotation);
			this.fillBlocksRotated(world, sbb, 4, bottom + i, 1, 6, bottom + i, 3, deco.floorID, deco.floorMeta, rotation);
		}
		
		// stairs
		int rotation = (ladderUpDir + 2) % 4;

		this.fillAirRotated(world, sbb, 5, top, 8, 9, top, 9, rotation);
		this.fillAirRotated(world, sbb, 8, top, 6, 9, top, 9, rotation);
		
		this.fillBlocksRotated(world, sbb, 8, top - 2, 7, 9, top - 2, 7, deco.platformID, deco.platformMeta, rotation);
		this.fillBlocksRotated(world, sbb, 8, top - 2, 8, 9, top - 2, 9, deco.floorID, deco.floorMeta, rotation);
		this.fillBlocksRotated(world, sbb, 7, top - 1, 8, 7, top - 1, 9, deco.platformID, deco.platformMeta, rotation);
		this.fillBlocksRotated(world, sbb, 6, top - 1, 8, 6, top - 1, 9, deco.platformID, deco.platformMeta | 8, rotation);
		this.fillBlocksRotated(world, sbb, 5, top - 0, 8, 5, top - 0, 9, deco.platformID, deco.platformMeta, rotation);

		this.decoratePillars(world, rand, bottom, top, ladderUpDir, sbb);
		
		// treasure!
		if (hasTreasure) {
			this.placeTreasureRotated(world, 3, bottom + 5, 2, ladderUpDir, TFTreasure.aurora_cache, false, sbb);
		}
	}
	

	private void decoratePillarParkour(World world, Random rand, int bottom, int top, int ladderUpDir, int ladderDownDir, boolean hasTreasure, StructureBoundingBox sbb) {
		int rotation = ladderDownDir;
		int beamMetaNS = ((this.coordBaseMode + rotation) % 2 == 0) ? 4 : 8;
		int beamMetaEW = (beamMetaNS == 4) ? 8 : 4;
		
		// 4 pillars
		this.decoratePillars(world, rand, bottom, top, rotation, sbb);

		// center pillar
		this.placeBlockRotated(world, deco.pillarID, deco.pillarMeta, 5, bottom + 1, 5, rotation, sbb);
		
		// pillar 2
		this.fillBlocksRotated(world, sbb, 5, bottom + 2, 7, 5, bottom + 2, 9, deco.pillarID, deco.pillarMeta + beamMetaEW, rotation);
		
		// gap 3
		this.fillBlocksRotated(world, sbb, 1, bottom + 3, 7, 2, bottom + 3, 7, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);
		this.fillBlocksRotated(world, sbb, 3, bottom + 3, 8, 3, bottom + 3, 9, deco.pillarID, deco.pillarMeta + beamMetaEW, rotation);
		this.fillBlocksRotated(world, sbb, 1, bottom + 7, 7, 2, bottom + 7, 7, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);
		this.fillBlocksRotated(world, sbb, 3, bottom + 7, 8, 3, bottom + 7, 9, deco.pillarID, deco.pillarMeta + beamMetaEW, rotation);
		this.fillAirRotated(world, sbb, 3, bottom + 4, 7, 3, bottom + 6, 7, rotation);
		
		// pillar 4
		this.fillBlocksRotated(world, sbb, 1, bottom + 4, 5, 2, bottom + 4, 5, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);

		// gap 5
		this.fillBlocksRotated(world, sbb, 3, bottom + 5, 1, 3, bottom + 5, 2, deco.pillarID, deco.pillarMeta + beamMetaEW, rotation);
		this.fillBlocksRotated(world, sbb, 1, bottom + 5, 3, 2, bottom + 5, 3, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);
		this.fillAirRotated(world, sbb, 3, bottom + 6, 3, 3, bottom + 8, 3, rotation);

		// pillar 6
		this.fillBlocksRotated(world, sbb, 5, bottom + 6, 1, 5, bottom + 6, 2, deco.pillarID, deco.pillarMeta + beamMetaEW, rotation);

		// gap 7
		this.fillAirRotated(world, sbb, 7, bottom + 8, 3, 7, bottom + 10, 3, rotation);
		this.fillBlocksRotated(world, sbb, 7, bottom + 7, 1, 7, bottom + 7, 2, deco.pillarID, deco.pillarMeta + beamMetaEW, rotation);
		this.fillBlocksRotated(world, sbb, 8, bottom + 7, 3, 9, bottom + 7, 3, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);
		
		// pillar 8
		this.fillBlocksRotated(world, sbb, 8, bottom + 8, 5, 9, bottom + 8, 5, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);
		
		// gap 9 (no gap?)
		//this.fillAirRotated(world, sbb, 7, bottom + 10, 7, 7, bottom + 10, 7, rotation);
		this.fillBlocksRotated(world, sbb, 8, bottom + 9, 7, 9, bottom + 9, 7, deco.pillarID, deco.pillarMeta + beamMetaNS, rotation);
		this.fillBlocksRotated(world, sbb, 7, bottom + 9, 8, 7, bottom + 9, 9, deco.pillarID, deco.pillarMeta + beamMetaEW, rotation);

		// holes in ceiling
		this.fillAirRotated(world, sbb, 2, top, 2, 8, top, 4, ladderUpDir);
		this.fillAirRotated(world, sbb, 2, top, 2, 4, top, 6, ladderUpDir);
		
		// treasure!
		if (hasTreasure) {
			this.placeTreasureRotated(world, 8, bottom + 8, 7, ladderUpDir, TFTreasure.aurora_cache, false, sbb);
		}
	}

	/**
	 * Attach a roof to this tower.
	 * 
	 * This function keeps trying roofs starting with the largest and fanciest, and then keeps trying smaller and plainer ones
	 */
	public void makeARoof(StructureComponent parent, List<StructureComponent> list, Random rand) {
		int index = this.getComponentType();
		tryToFitRoof(list, rand, new ComponentTFIceTowerRoof(index + 1, this));
	}

	/**
	 * Add a beard to this structure.  There is only one type of beard.
	 */
	public void makeABeard(StructureComponent parent, List<StructureComponent> list, Random rand) {
		int index = this.getComponentType();
		ComponentTFIceTowerBeard beard;
		beard = new ComponentTFIceTowerBeard(index + 1, this);
		list.add(beard);
		beard.buildComponent(this, list, rand);
	}

}
