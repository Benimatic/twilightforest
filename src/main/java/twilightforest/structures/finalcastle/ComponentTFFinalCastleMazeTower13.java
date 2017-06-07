package twilightforest.structures.finalcastle;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponent;
import twilightforest.structures.lichtower.ComponentTFTowerWing;
import java.util.List;
import java.util.Random;

public class ComponentTFFinalCastleMazeTower13 extends ComponentTFTowerWing
{

    public static final int LOWEST_DOOR = 144;
    public static final int HIGHEST_DOOR = 222;

    public int type;

	public ComponentTFFinalCastleMazeTower13(Random rand, int i, int x, int y, int z, int type, EnumFacing direction) {
		super(i);
		this.setCoordBaseMode(direction);
		this.type = type;
		this.size = 13;

		// decide a number of floors, 2-5
		int floors = rand.nextInt(3) + 2;

		this.height = floors * 8 + 1;

		// entrance should be on a random floor
		int entranceFloor = rand.nextInt(floors);

		// rationalize entrance floor if the tower is going to be too low, put the entrance floor at bottom.  Too high, put it at top
		if ((y - (entranceFloor * 8)) < LOWEST_DOOR) {
			//System.out.println("Tower at " + x + ", " + z + " is getting too low, setting entrance to bottom floor.");
			entranceFloor = 0;
		}
		if ((y + ((floors - entranceFloor) * 8)) > HIGHEST_DOOR) {
			//System.out.println("Tower at " + x + ", " + z + " is getting too high, setting entrance to floor " +  (floors - 1) + ".");
			entranceFloor = floors - 1;
		}

		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, -6, 0 - (entranceFloor * 8), -6, this.size - 1, this.height, this.size - 1, EnumFacing.SOUTH);

	    // we should have a door where we started
	    addOpening(0, entranceFloor * 8 + 1, size / 2, Rotation.CLOCKWISE_180);

	}

	public ComponentTFFinalCastleMazeTower13(Random rand, int i, int x, int y, int z, int floors, int entranceFloor, int type, EnumFacing direction) {
		super(i);
		this.setCoordBaseMode(direction);
		this.type = type;
		this.size = 13;
		this.height = floors * 8 + 1;
		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, -6, 0 - (entranceFloor * 8), -6, this.size - 1, this.height, this.size - 1, EnumFacing.SOUTH);
	    addOpening(0, entranceFloor * 8 + 1, size / 2, Rotation.CLOCKWISE_180);
    }

    @Override
    public void buildComponent(StructureComponent parent, List<StructureComponent> list, Random rand) {
	    if (parent != null && parent instanceof StructureTFComponent) {
		    this.deco = ((StructureTFComponent)parent).deco;
	    }

	    // add foundation
	    ComponentTFFinalCastleFoundation13 foundation = new ComponentTFFinalCastleFoundation13(rand, 4, this);
	    list.add(foundation);
	    foundation.buildComponent(this, list, rand);

	    // add roof
	    StructureTFComponent roof = rand.nextBoolean() ? new ComponentTFFinalCastleRoof13Conical(rand, 4, this) :  new ComponentTFFinalCastleRoof13Crenellated(rand, 4, this);
	    list.add(roof);
	    roof.buildComponent(this, list, rand);
    }

    /**
     * Build more components towards the destination
     */
    public void buildTowards(StructureComponent parent, List list, Random rand, BlockPos dest) {
	    // regular building first, adds roof/foundation
	    this.buildComponent(parent, list, rand);

	    if (this.getComponentType() < 15) {

		    // are we there?
		    if (this.isWithinRange(dest.getX(), dest.getZ(), this.boundingBox.minX + 6, this.boundingBox.minZ + 6, 30)) {
			    //System.out.println("We are within range of our destination, building final tower");
			    int howFar = 20;
			    if (!buildEndTowerTowards(list, rand, dest, this.findBestDirectionTowards(dest), howFar)) {
			        if (!buildEndTowerTowards(list, rand, dest, this.findSecondDirectionTowards(dest), howFar)) {
			            if (!buildEndTowerTowards(list, rand, dest, this.findThirdDirectionTowards(dest), howFar)) {
			                //System.out.println("Cound not build final tower");
			            }
			        }
			    }
		    } else {

			    int howFar = 14 + rand.nextInt(24);
			    int direction = this.findBestDirectionTowards(dest);

			    // build left or right, not straight if we can help it
			    if (direction == 0 || !buildContinueTowerTowards(list, rand, dest, direction, howFar)) {
				    direction = this.findSecondDirectionTowards(dest);
				    if (direction == 0 || !buildContinueTowerTowards(list, rand, dest, direction, howFar)) {
					    direction = this.findThirdDirectionTowards(dest);
					    if (direction == 0 || !buildContinueTowerTowards(list, rand, dest, direction, howFar)) {
						    // fine, just go straight
						    if (!buildContinueTowerTowards(list, rand, dest, 0, howFar)) {
							    //System.out.println("Could not build tower randomly");
						    }
					    }
				    }
			    }
		    }
	    }

	    // finally, now that the critical path is built, let's add some other towers for atmosphere and complication
	    this.buildNonCriticalTowers(parent, list, rand);

    }

	protected void buildNonCriticalTowers(StructureComponent parent, List list, Random rand) {
		// pick a random direction
		int dir = rand.nextInt(4);

		// if there isn't something in that direction, check if we can add a wrecked tower
		if (this.openingTowards[dir] == false) {
			if (!buildDamagedTower(list, rand, dir)) {
				dir = (dir + rand.nextInt(4)) % 4;
				if (!buildDamagedTower(list, rand, dir)) {
				// maybe just a balcony?
				//buildBalconyTowards(list, rand, dir);
				}
			}
		}


	}

	private int findBestDirectionTowards(BlockPos dest) {

		// center of tower
		int cx = this.boundingBox.minX + 6;
		int cz = this.boundingBox.minZ + 6;

		// difference
		int dx = cx - dest.getX();
		int dz = cz - dest.getZ();

		int absoluteDir = 0;
		if (Math.abs(dx) > Math.abs(dz)) {
			absoluteDir = (dx >= 0) ? 2 : 0;
		} else {
			absoluteDir = (dz >= 0) ? 3 : 1;
		}

		int relativeDir = (absoluteDir + 4 - this.coordBaseMode) % 4;

		//System.out.println("Determining best direction!  center is at " + cx + ", " + cz + " and dest is at " + dest + " offset is " + dx + ", " + dz + " so the best absolute direction is " + absoluteDir + " and relative dir is " + relativeDir);

		// return relative dir
		return relativeDir;
	}

	private int findSecondDirectionTowards(BlockPos dest) {

		// center of tower
		int cx = this.boundingBox.minX + 6;
		int cz = this.boundingBox.minZ + 6;

		// difference
		int dx = cx - dest.getX();
		int dz = cz - dest.getZ();

		int absoluteDir = 0;
		if (Math.abs(dx) < Math.abs(dz)) {  // reversed from findBestDirectionTowards
			absoluteDir = (dx >= 0) ? 2 : 0;
		} else {
			absoluteDir = (dz >= 0) ? 3 : 1;
		}
		int relativeDir = (absoluteDir + 4 - this.coordBaseMode) % 4;


		//System.out.println("Determining second direction!  center is at " + cx + ", " + cz + " and dest is at " + dest + " offset is " + dx + ", " + dz + " so the best absolute direction is " + absoluteDir + " and relative dir is " + relativeDir);

		// return offset dir
		return relativeDir;
	}

	private int findThirdDirectionTowards(BlockPos dest) {
		int first = this.findBestDirectionTowards(dest);
		int second = this.findSecondDirectionTowards(dest);

		if (first == 0 && second == 1) {
			return 3;
		} else if (first == 1 && second == 3) {
			return 0;
		} else {
			return 1;
		}
	}

	private boolean buildContinueTowerTowards(List list, Random rand, BlockPos dest, Rotation direction, int howFar) {
		BlockPos opening = this.getValidOpeningCC(rand, direction);

		// adjust opening towards dest.getY() if we are getting close to dest
		int adjustmentRange = 60;
		if (this.isWithinRange(dest.getX(), dest.getZ(), this.boundingBox.minX + 6, this.boundingBox.minZ + 6, adjustmentRange)) {
			opening.getY() = this.adjustOpening(opening.getY(), dest);
		}

		//System.out.println("original direction is " + direction);


		direction += this.coordBaseMode;
		direction %= 4;

		// build towards
		BlockPos tc = this.offsetTowerCCoords(opening.getX(), opening.getY(), opening.getZ(), howFar, direction);

		//System.out.println("Our coord mode is " + this.getCoordBaseMode() + ", and direction is " + direction + ", so our door is going to be at " + opening + " and the new tower will appear at " + tc);

		// find start
		StructureComponent start = (StructureComponent)list.get(0);

		//System.out.println("Testing range, uncorrected center is at " + centerX + ", " + centerZ);

		int centerX = ((start.getBoundingBox().minX + 128) >> 8) << 8;
		int centerZ = ((start.getBoundingBox().minZ + 128) >> 8) << 8;

		if (isWithinRange(centerX, centerZ, tc.getX(), tc.getZ(), 128)) {

			ComponentTFFinalCastleMazeTower13 sTower = new ComponentTFFinalCastleMazeTower13(rand, this.getComponentType() + 1, tc.getX(), tc.getY(), tc.getZ(), this.type, direction);

			StructureBoundingBox largerBB = new StructureBoundingBox(sTower.getBoundingBox());

			largerBB.minX -= 6;
			largerBB.minZ -= 6;
			largerBB.maxX += 6;
			largerBB.maxZ += 6;
			largerBB.minY = 0;
			largerBB.maxY = 255;

			StructureComponent intersect = StructureComponent.findIntersecting(list, largerBB);

			if (intersect == null) {
				//System.out.println("tower success!");
				list.add(sTower);
				sTower.buildTowards(this, list, rand, dest);

				// add bridge
				BlockPos bc = this.offsetTowerCCoords(opening.getX(), opening.getY(), opening.getZ(), 1, direction);
				ComponentTFFinalCastleBridge bridge = new ComponentTFFinalCastleBridge(this.getComponentType() + 1, bc.getX(), bc.getY(), bc.getZ(), howFar - 7, direction);
				list.add(bridge);
				bridge.buildComponent(this, list, rand);

				// opening
			    addOpening(opening.getX(), opening.getY() + 1, opening.getZ(), direction);

				return true;
			} else {
				//System.out.println("tower blocked");
				return false;
			}
		} else {
			//System.out.println("tower out of range");
			return false;
		}
	}


	protected boolean buildDamagedTower(List list, Random rand, Rotation direction) {
		BlockPos opening = this.getValidOpeningCC(rand, direction);

		direction += this.coordBaseMode;
		direction %= 4;

		int howFar = 14 + rand.nextInt(24);
		// build towards
		BlockPos tc = this.offsetTowerCCoords(opening.getX(), opening.getY(), opening.getZ(), howFar, direction);

		// what type of tower?
		ComponentTFFinalCastleMazeTower13 eTower = makeNewDamagedTower(rand, direction, tc);

		StructureBoundingBox largerBB = new StructureBoundingBox(eTower.getBoundingBox());

		largerBB.minX -= 6;
		largerBB.minZ -= 6;
		largerBB.maxX += 6;
		largerBB.maxZ += 6;

		StructureComponent intersect = StructureComponent.findIntersecting(list, largerBB);

		if (intersect == null) {
			//System.out.println("wreck tower success!  tower is at " + tc.getX() + ", " + tc.getY() + ", " + tc.getZ());
			list.add(eTower);
			eTower.buildComponent(this, list, rand);
			// add bridge
			BlockPos bc = this.offsetTowerCCoords(opening.getX(), opening.getY(), opening.getZ(), 1, direction);
			ComponentTFFinalCastleBridge bridge = new ComponentTFFinalCastleBridge(this.getComponentType() + 1, bc.getX(), bc.getY(), bc.getZ(), howFar - 7, direction);
			list.add(bridge);
			bridge.buildComponent(this, list, rand);

			// opening
		    addOpening(opening.getX(), opening.getY() + 1, opening.getZ(), direction);

			return true;
		} else {
			//System.out.println("damaged tower blocked");
			return false;
		}
	}

	protected ComponentTFFinalCastleMazeTower13 makeNewDamagedTower(Random rand, Rotation direction, BlockPos tc) {
		return new ComponentTFFinalCastleDamagedTower(rand, this.getComponentType() + 1, tc.getX(), tc.getY(), tc.getZ(), direction);
	}

	private int adjustOpening(int posY, BlockPos dest) {
		int openY = posY;

		int realOpeningY = this.getYWithOffset(openY);
		if (realOpeningY - dest.getY() < 12) {
			// if it is too low, move it to the top floor
			openY = this.height - 9;
		} else if (dest.getY() - realOpeningY < 12) {
			// if the opening is too high, move it to the bottom floor
			openY = 0;
		}

		return openY;
	}

	private boolean buildEndTowerTowards(List list, Random rand, BlockPos dest, Rotation direction, int howFar) {
		BlockPos opening = this.getValidOpeningCC(rand, direction);

		// adjust opening towards dest.getY()
		opening.getY() = this.adjustOpening(opening.getY(), dest);

		direction += this.coordBaseMode;
		direction %= 4;

		// build towards
		BlockPos tc = this.offsetTowerCCoords(opening.getX(), opening.getY(), opening.getZ(), howFar, direction);

		//System.out.println("Our coord mode is " + this.getCoordBaseMode() + ", and direction is " + direction + ", so our door is going to be at " + opening + " and the new tower will appear at " + tc);

		// what type of tower?
		ComponentTFFinalCastleMazeTower13 eTower;
		if (this.type == 0) {
			eTower = new ComponentTFFinalCastleEntranceTower(rand, this.getComponentType() + 1, tc.getX(), tc.getY(), tc.getZ(), direction);
		} else {
			eTower = new ComponentTFFinalCastleBellTower21(rand, this.getComponentType() + 1, tc.getX(), tc.getY(), tc.getZ(), direction);
		}

		StructureBoundingBox largerBB = new StructureBoundingBox(eTower.getBoundingBox());

		largerBB.minX -= 6;
		largerBB.minZ -= 6;
		largerBB.maxX += 6;
		largerBB.maxZ += 6;

		StructureComponent intersect = StructureComponent.findIntersecting(list, largerBB);

		if (intersect == null) {
			//System.out.println("entrance tower success!  tower is at " + tc.getX() + ", " + tc.getY() + ", " + tc.getZ() + " and dest is " + dest.getX() + ", " + dest.getY() + ", " + dest.getZ());
			list.add(eTower);
			eTower.buildComponent(this, list, rand);
			// add bridge
			BlockPos bc = this.offsetTowerCCoords(opening.getX(), opening.getY(), opening.getZ(), 1, direction);
			ComponentTFFinalCastleBridge bridge = new ComponentTFFinalCastleBridge(this.getComponentType() + 1, bc.getX(), bc.getY(), bc.getZ(), howFar - 7, direction);
			list.add(bridge);
			bridge.buildComponent(this, list, rand);

			// opening
		    addOpening(opening.getX(), opening.getY() + 1, opening.getZ(), direction);

			return true;
		} else {
			//System.out.println("end tower blocked");
			return false;
		}

	}

	private boolean isWithinRange(int centerX, int centerZ, int posX, int posZ, int range) {
		boolean inRange = Math.abs(centerX - posX) < range && Math.abs(centerZ - posZ) < range;

		if (!inRange) {
//				System.out.println("Tested range, center is at " + centerX + ", " + centerZ + " and tower is " + posX + ", " + posZ + " so distance is " + Math.max(Math.abs(centerX - posX),  Math.abs(centerZ - posZ)));
		}

		return inRange;
	}

	/**
	 * Gets a random position in the specified direction that connects to a floor currently in the tower.
	 */
	public BlockPos getValidOpeningCC(Random rand, Rotation direction) {
		int floors = (this.height / 8);

		// for directions 0 or 2, the wall lies along the z axis
		if (direction == 0 || direction == 2) {
			int rx = direction == 0 ? 12 : 0;
			int rz = 6;
			int ry = rand.nextInt(floors) * 8;

			return new BlockPos(rx, ry, rz);
		}

		// for directions 1 or 3, the wall lies along the x axis
		if (direction == 1 || direction == 3) {
			int rx = 6;
			int rz = direction == 1 ? 12 : 0;
			int ry = rand.nextInt(floors) * 8;

			return new BlockPos(rx, ry, rz);
		}


		return new BlockPos(0, 0, 0);
	}

	/**
	 * Provides coordinates to make a tower such that it will open into the parent tower at the provided coordinates.
	 */
	@Override
	protected BlockPos offsetTowerCCoords(int x, int y, int z, int howFar, EnumFacing direction) {

		int dx = getXWithOffset(x, z);
		int dy = getYWithOffset(y);
		int dz = getZWithOffset(x, z);

		switch (direction) {
			case SOUTH:
			dx += howFar;
			break;
			case WEST:
			dz += howFar;
			break;
			case NORTH:
			dx -= howFar;
			break;
			case EAST:
			dz -= howFar;
			break;
		}

		// ugh?
		return new BlockPos(dx, dy, dz);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));

		// walls
		fillWithRandomizedBlocks(world, sbb, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1, false, rand, deco.randomBlocks);

		// stone to ground
		for (int x = 0; x < this.size; x++)
		{
			for (int z = 0; z < this.size; z++)
			{
				this.func_151554_b(world, deco.blockState, x, -1, z, sbb);
			}

		}

        // add branching runes
		int numBranches = 2 + decoRNG.nextInt(4) +  + decoRNG.nextInt(3);
		for (int i = 0; i < numBranches; i++) {
			makeGlyphBranches(world, decoRNG, this.getGlyphMeta(), sbb);
		}

		// floors
		addFloors(world, decoRNG, sbb);

        // openings
        makeOpenings(world, sbb);


		return true;
	}

	public int getGlyphMeta() {
		return type;
	}

	private void addFloors(World world, Random rand, StructureBoundingBox sbb) {
		// only add floors up to highest opening
		int floors = (this.highestOpening / 8) + 1;

		for (int i = 1; i < floors; i++) {
			this.fillWithBlocks(world, sbb, 1, i * 8, 1, 11, i * 8, 11, deco.blockState, deco.blockState, false);

			// stairs
			addStairsDown(world, sbb, (i + 2) % 4, i * 8);
		}

		if (hasAccessibleRoof()) {
			// add stairs to roof
			addStairsDown(world, sbb, (floors + 2) % 4, this.height - 1);
		}

	}

	protected boolean hasAccessibleRoof() {
		return this.height - this.highestOpening < 9;
	}

	private void addStairsDown(World world, StructureBoundingBox sbb, int rotation, int y) {
		// top flight
		for (int i = 0; i < 4; i++) {
			int sx = 8 - i;
			int sy = y - i;
			int sz = 9;

			this.setBlockStateRotated(world, deco.stairID, getStairMeta(0 + rotation), sx, sy, sz, rotation, sbb);
			this.setBlockStateRotated(world, deco.blockState, sx, sy - 1, sz, rotation, sbb);
			this.setBlockStateRotated(world, deco.stairID, getStairMeta(0 + rotation), sx, sy, sz - 1, rotation, sbb);
			this.setBlockStateRotated(world, deco.blockState, sx, sy - 1, sz - 1, rotation, sbb);
			this.fillAirRotated(world, sbb, sx, sy + 1, sz - 1, sx, sy + 3, sz, rotation);
		}
		// landing
		this.fillBlocksRotated(world, sbb, 3, y - 4, 8, 4, y - 4, 9, deco.blockState, rotation);


		// bottom flight
		for (int i = 0; i < 4; i++) {
			int sx = 4;
			int sy = y - i - 4;
			int sz = 7 - i;

			this.setBlockStateRotated(world, deco.stairID, getStairMeta(1 + rotation), sx, sy, sz, rotation, sbb);
			this.setBlockStateRotated(world, deco.blockState, sx, sy - 1, sz, rotation, sbb);
			this.setBlockStateRotated(world, deco.stairID, getStairMeta(1 + rotation), sx - 1, sy, sz, rotation, sbb);
			this.setBlockStateRotated(world, deco.blockState, sx - 1, sy - 1, sz, rotation, sbb);
			this.fillAirRotated(world, sbb, sx, sy + 1, sz, sx - 1, sy + 3, sz, rotation);
		}

	}

	/**
	 * Make an opening in this tower for a door.
	 */
	@Override
	protected void makeDoorOpening(World world, int dx, int dy, int dz, StructureBoundingBox sbb) {
	     // nullify sky light
	     //nullifySkyLightAtCurrentPosition(world, dx - 3, dy - 1, dz - 3, dx + 3, dy + 3, dz + 3);

        // clear the door
		if (dx == 0 || dx == size - 1)
		{
			this.fillWithBlocks(world, sbb, dx, dy - 1, dz - 2, dx, dy + 4, dz + 2, deco.accentID, deco.accentMeta, Blocks.AIR.getDefaultState(), false);
			//this.fillWithAir(world, sbb, dx, dy, dz - 1, dx, dy + 3, dz + 1);
			this.fillWithBlocks(world, sbb, dx, dy, dz - 1, dx, dy + 3, dz + 1, TFBlocks.castleDoor, this.getGlyphMeta(), Blocks.AIR.getDefaultState(), false);
		}
		if (dz == 0 || dz == size - 1)
		{
			this.fillWithBlocks(world, sbb, dx - 2, dy - 1, dz, dx + 2, dy + 4, dz, deco.accentID, deco.accentMeta, Blocks.AIR.getDefaultState(), false);
			//this.fillWithAir(world, sbb, dx - 1, dy, dz, dx + 1, dy + 3, dz);
			this.fillWithBlocks(world, sbb, dx - 1, dy, dz, dx + 1, dy + 3, dz, TFBlocks.castleDoor, this.getGlyphMeta(), Blocks.AIR.getDefaultState(), false);
		}
	}
}
