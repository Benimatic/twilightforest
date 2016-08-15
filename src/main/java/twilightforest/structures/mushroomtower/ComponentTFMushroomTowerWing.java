package twilightforest.structures.mushroomtower;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.structures.StructureTFComponent;
import twilightforest.structures.lichtower.ComponentTFTowerRoof;
import twilightforest.structures.lichtower.ComponentTFTowerWing;

public class ComponentTFMushroomTowerWing extends ComponentTFTowerWing 
{

	private static final int RANGE = 200;
	protected static final int FLOOR_HEIGHT = 4;
	protected static final int MAIN_SIZE = 15;

	boolean hasBase = false;
	public boolean isAscender = false;

	public ComponentTFMushroomTowerWing() {
		super();
	}

	protected ComponentTFMushroomTowerWing(int i, int x, int y, int z, int pSize, int pHeight, int direction) {
		super(i, x, y, z, pSize, pHeight, direction);
	}
	
	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		
        par1NBTTagCompound.setBoolean("hasBase", this.hasBase);
        par1NBTTagCompound.setBoolean("isAscender", this.isAscender);
	}

	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);
        this.hasBase = par1NBTTagCompound.getBoolean("hasBase");
        this.isAscender = par1NBTTagCompound.getBoolean("isAscender");
	}

	@Override
	public void buildComponent(StructureComponent parent, List list, Random rand) {
		if (parent != null && parent instanceof StructureTFComponent)
		{
			this.deco = ((StructureTFComponent)parent).deco;
		}
		
		// we should have a door where we started
		addOpening(0, 1, size / 2, 2);
		
		// should we build a base?
		this.hasBase = size > 3;
		
		// if we are an acender, build the destination tower
		if (this.isAscender)
		{
			int[] dest = getValidOpening(rand, 2);
			
			dest[1] = this.height - 3;
			
			int childHeight = (rand.nextInt(3) + rand.nextInt(3) + 2) * FLOOR_HEIGHT + 1;
			
			
			boolean madeIt = makeMainBridge(list, rand, this.getComponentType() + 1, dest[0], dest[1], dest[2], size + 4, childHeight, 2);
			
			if (!madeIt)
			{
				System.out.println("Did not make bridge back to new main");
			}
			else
			{
				System.out.println("Made bridge back to new main");
			}
		}
		
		// limit sprawl to a reasonable amount
		if (this.getComponentType() < 5 && this.size > 6)
		{
			// make sub towers
			for (int i = 0; i < 4; i++) {
				
				if (this.size < MAIN_SIZE && i == 2)
				{
					continue;
				}

				int[] dest = getValidOpening(rand, i);
	
				int childHeight = (rand.nextInt(2) + rand.nextInt(2) + 2) * FLOOR_HEIGHT + 1;
				
				makeBridge(list, rand, this.getComponentType() + 1, dest[0], dest[1], dest[2], size - 4, childHeight, i);
			}
		}
		
		if (this.isHighest(this.boundingBox, this.size, list) || !this.hasBase)
		{
			// add a roof?
			makeARoof(parent, list, rand);
		}
	
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
	public boolean makeTowerWing(List list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, int rotation) {

		int direction = (getCoordBaseMode() + rotation) % 4;
		int[] dx = offsetTowerCoords(x, y, z, wingSize, direction);
		
		// stop if out of range
		if (isOutOfRange((StructureComponent) list.get(0), dx[0], dx[1], dx[2], RANGE))
		{
			return false;
		}
		
		// adjust coordinates to fit an existing tower
		if (wingSize > 3)
		{
			dx = adjustCoordinates(dx[0], dx[1], dx[2], wingSize, direction, list);
		}

		ComponentTFMushroomTowerWing wing = new ComponentTFMushroomTowerWing(index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);
		// check to see if it intersects something already there
		StructureComponent intersect = StructureComponent.findIntersecting(list, wing.getBoundingBox());
		if (intersect == null || intersect == this || intersect instanceof ComponentTFTowerRoofMushroom) {
			
			// if we are coming from an ascender bridge, mark the destination component
			if (this instanceof ComponentTFMushroomTowerBridge && this.isAscender)
			{
				wing.isAscender = true;
			}
			
			list.add(wing);
			wing.buildComponent((StructureComponent) list.get(0), list, rand);
			addOpening(x, y, z, rotation);

			return true;
		}
		else
		{
			return false;
		}

	}
	
	/**
	 * Adjust the coordinates for this tower to link up with any others within 3
	 */
	protected int[] adjustCoordinates(int x, int y, int z, int wingSize, int direction, List list) {
		
		// go through list.  if there are any same size towers within wingSize, return their xyz instead
		
		for (Object obj : list)
		{
			if (obj instanceof ComponentTFTowerWing && !(obj instanceof ComponentTFMushroomTowerBridge))
			{
				ComponentTFTowerWing otherWing = (ComponentTFTowerWing)obj;
				
				if (wingSize == otherWing.size && otherWing.getBoundingBox().intersectsWith(x - 3, z - 3, x + 3, z + 3))
				{
//					System.out.println("I found an intersecting thing : " + otherWing + ", size = " + wingSize + " direction = " + direction);
//					System.out.println("x = " + x + ", z = " + z + " other.minX = " + otherWing.getBoundingBox().minX + " other.maxX = " + otherWing.getBoundingBox().maxX + " other.minZ = " + otherWing.getBoundingBox().minZ + " other.maxZ = " + otherWing.getBoundingBox().maxZ);
					
					switch (direction)
					{
					case 0:
						return new int[] {otherWing.getBoundingBox().minX, y, otherWing.getBoundingBox().minZ};
					case 1:
						return new int[] {otherWing.getBoundingBox().maxX, y, otherWing.getBoundingBox().minZ};
					case 2:
						return new int[] {otherWing.getBoundingBox().maxX, y, otherWing.getBoundingBox().maxZ};
					case 3:
						return new int[] {otherWing.getBoundingBox().minX, y, otherWing.getBoundingBox().maxZ};
					}
				}
			}
		}

		

		return new int[] {x, y, z};
	}
	
	/**
	 * Are there (not) any other towers below this bounding box?
	 */
	private boolean isHighest(StructureBoundingBox boundingBox, int size, List list) {
		// go through list.  if there are any same size towers within wingSize, return their xyz instead
		
		StructureBoundingBox boxAbove = new StructureBoundingBox(boundingBox);
		
		boxAbove.maxY = 256;
		
		for (Object obj : list)
		{
			if (this != obj && obj instanceof ComponentTFTowerWing && !(obj instanceof ComponentTFMushroomTowerBridge))
			{
				ComponentTFTowerWing otherWing = (ComponentTFTowerWing)obj;
				
				if (size == otherWing.size && otherWing.getBoundingBox().intersectsWith(boxAbove))
				{
					//System.out.println("This tower (" + boundingBox + ") is not the highest, there is " + otherWing + " at " + otherWing.getBoundingBox());
					
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * Make a mushroom roof!
	 */
	@Override
	public void makeARoof(StructureComponent parent, List list, Random rand) {
		
		ComponentTFTowerRoof roof = new ComponentTFTowerRoofMushroom(this.getComponentType() + 1, this, 1.6F);
		if (StructureComponent.findIntersecting(list, roof.getBoundingBox()) instanceof ComponentTFTowerRoofMushroom)
		{
			list.add(roof);
			roof.buildComponent(this, list, rand);
		}
		else
		{
			roof = new ComponentTFTowerRoofMushroom(this.getComponentType() + 1, this, 1.0F);
			if (StructureComponent.findIntersecting(list, roof.getBoundingBox()) instanceof ComponentTFTowerRoofMushroom)
			{
				list.add(roof);
				roof.buildComponent(this, list, rand);
			}
			else
			{
				roof = new ComponentTFTowerRoofMushroom(this.getComponentType() + 1, this, 0.6F);
				list.add(roof);
				roof.buildComponent(this, list, rand);
			}

		}
	}


	protected boolean makeBridge(List list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, int rotation) {
		return this.makeBridge(list, rand, index, x, y, z, wingSize, wingHeight, rotation, false);
	}
	
	protected boolean makeBridge(List list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, int rotation, boolean ascender) {
		// bridges are size  always
		int direction = (getCoordBaseMode() + rotation) % 4;
		int[] dx = offsetTowerCoords(x, y, z, 3, direction);
		
		// adjust height for those stupid little things
		if (wingSize == 3) {
			wingHeight = 4;
		}
		
		ComponentTFMushroomTowerBridge bridge = new ComponentTFMushroomTowerBridge(index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);
		bridge.isAscender = ascender;
		// check to see if it intersects something already there
		StructureComponent intersect = StructureComponent.findIntersecting(list, bridge.getBoundingBox());
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
	
	private boolean makeMainBridge(List list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, int rotation) {

		int direction = (getCoordBaseMode() + rotation) % 4;
		int[] dx = offsetTowerCoords(x, y, z, 3, direction);

		ComponentTFMushroomTowerMainBridge bridge = new ComponentTFMushroomTowerMainBridge(index, dx[0], dx[1], dx[2], wingSize, wingHeight, direction);

		list.add(bridge);
		bridge.buildComponent(this, list, rand);
		addOpening(x, y, z, rotation);
		return true;

	}

	/**
	 * Gets a random position in the specified direction that connects to stairs currently in the tower.
	 */
	public int[] getValidOpening(Random rand, int direction) {
		// variables!
		int wLength = Math.min(size / 3, 3); // wall length
		int offset = (size - wLength) / 2; // wall thickness
		
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
		
		int floors = this.height / FLOOR_HEIGHT;
	
		return FLOOR_HEIGHT + 1 + (rand.nextInt(floors - 1) * FLOOR_HEIGHT);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		Random decoRNG = new org.bogdang.modifications.random.XSTR(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));
	
//		// clear inside
//		fillWithAir(world, sbb, 1, 1, 1, size - 2, height - 2, size - 2);
		
		makeTrunk(world, sbb);
		
		
		// make floors
		makeFloorsForTower(world, decoRNG, sbb);


	 	// nullify sky light
		this.nullifySkyLightForBoundingBox(world);


	 	
	 	// openings
	 	makeOpenings(world, sbb);
	
	
	 	return true;
	}

	private void makeTrunk(World world, StructureBoundingBox sbb) {
		int diameter = this.size / 2;
		int hollow = (int)(diameter * 0.8);
		
		int cx = diameter;
		int cz = diameter;

		// build the trunk upwards
		for (int dx = -diameter; dx <= diameter; dx++)
		{
			for (int dz = -diameter; dz <= diameter; dz++)
			{
				// determine how far we are from the center.
				int ax = Math.abs(dx);
				int az = Math.abs(dz);
				int dist = (int)(Math.max(ax, az) + (Math.min(ax, az) * 0.4));

				// make a trunk!
				if (dist <= diameter) 
				{
					// make floor/ceiling
					placeBlockAtCurrentPosition(world, deco.floorID, deco.floorMeta, dx + cx, 0, dz + cz, sbb);
					placeBlockAtCurrentPosition(world, deco.floorID, deco.floorMeta, dx + cx, height, dz + cz, sbb);

					// make walls
					if (dist > hollow)
					{
						for (int dy = 0; dy <= height; dy++)
						{
							placeBlockAtCurrentPosition(world, deco.blockID, deco.blockMeta, dx + cx, dy, dz + cz, sbb);
						}
					}
					else
					{
						for (int dy = 1; dy <= height - 1; dy++)
						{
							placeBlockAtCurrentPosition(world, Blocks.air, 0, dx + cx, dy, dz + cz, sbb);
						}
					}

					// make base
					if (this.hasBase) 
					{
						this.func_151554_b(world, deco.blockID, deco.blockMeta, dx + cx, -1, dz + cz, sbb);
					}
				}
			}
		}
	}

	private void makeFloorsForTower(World world, Random decoRNG, StructureBoundingBox sbb) {
		int floors = this.height / FLOOR_HEIGHT;

		int ladderDir = 3;
		int downLadderDir = -1;

		// divide the tower into floors
		for (int i = 0; i < floors; i++) {

			
			placeFloor(world, i * FLOOR_HEIGHT, sbb);

			

			downLadderDir = ladderDir;
			ladderDir++;
			ladderDir %= 4;
			
//			decorateFloor(world, decoRNG, i, (i * floorHeight), (i * floorHeight) + floorHeight, ladderDir, downLadderDir, sbb);

		}
		
		
	}

	private void placeFloor(World world, int dy, StructureBoundingBox sbb) {
		int diameter = this.size / 2;
		int hollow = (int)(diameter * 0.8);
		
		int cx = diameter;
		int cz = diameter;

		// build the trunk upwards
		for (int dx = -diameter; dx <= diameter; dx++)
		{
			for (int dz = -diameter; dz <= diameter; dz++)
			{
				// determine how far we are from the center.
				int ax = Math.abs(dx);
				int az = Math.abs(dz);
				int dist = (int)(Math.max(ax, az) + (Math.min(ax, az) * 0.4));

				// make a floor!
				if (dist <= hollow) 
				{
					{
						placeBlockAtCurrentPosition(world, deco.floorID, this.isAscender ? 3 : deco.floorMeta, dx + cx, dy, dz + cz, sbb);
					}
				}
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
		//decorateWraparoundWallSteps(world, rand, bottom, top, ladderUpDir, ladderDownDir, sbb);

	}



}
