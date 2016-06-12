package twilightforest.structures.stronghold;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.structures.StructureTFComponent;

public abstract class StructureTFStrongholdComponent extends StructureTFComponent {

	public List<BlockPos> doors = new ArrayList<BlockPos>();

	public StructureTFStrongholdComponent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StructureTFStrongholdComponent(int i, int facing, int x, int y, int z) {
		super(i);
		this.boundingBox = generateBoundingBox(facing, x, y, z);
		this.setCoordBaseMode(facing);
	}

	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);

        par1NBTTagCompound.setIntArray("doorInts", this.getDoorsAsIntArray());
	}

	/**
	 * Turn the openings array into an array of ints.
	 */
	private int[] getDoorsAsIntArray() {
		IntBuffer ibuffer = IntBuffer.allocate(this.doors.size() * 3);
		
		for (BlockPos door : doors)
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
		
		// init doors
        this.readOpeningsFromArray(par1NBTTagCompound.getIntArray("doorInts"));
	}

	/**
	 * Read in openings from int array
	 */
	private void readOpeningsFromArray(int[] intArray) {
		for (int i = 0; i < intArray.length; i += 3)
		{
			BlockPos door = new BlockPos(intArray[i], intArray[i + 1], intArray[i + 2]);
			
			this.doors.add(door);
		}
	}
	
	public abstract StructureBoundingBox generateBoundingBox(int facing, int x, int y, int z);
	
    /**
     * used to project a possible new component Bounding Box - to check if it would cut anything already spawned
     */
    public static StructureBoundingBox getComponentToAddBoundingBox(int x, int y, int z, int xOff, int yOff, int zOff, int xSize, int ySize, int zSize, int facing)
    {
        switch (facing)
        {
            case 0:
                return new StructureBoundingBox(x + xOff, y + yOff, z + zOff, x + xSize - 1 + xOff, y + ySize - 1 + yOff, z + zSize - 1 + zOff);
            case 1:
                return new StructureBoundingBox(x - zSize + 1 + zOff, y + yOff, z + xOff, x + zOff, y + ySize - 1 + yOff, z + xSize - 1 + xOff);
            case 2:
                return new StructureBoundingBox(x - xSize + 1 - xOff, y + yOff, z - zSize + 1 + zOff, x - xOff, y + ySize - 1 + yOff, z + zOff);
            case 3:
                return new StructureBoundingBox(x + zOff, y + yOff, z - xSize + 1 - xOff, x + zSize - 1 + zOff, y + ySize - 1 + yOff, z - xOff);
            default:
                return new StructureBoundingBox(x + xOff, y + yOff, z + zOff, x + xSize - 1 + xOff, y + ySize - 1 + yOff, z + zSize - 1 + zOff);
        }
    }
    
	@SuppressWarnings("rawtypes")
	@Override
	public void buildComponent(StructureComponent parent, List list, Random rand) 
	{
		if (parent != null && parent instanceof StructureTFComponent)
		{
			this.deco = ((StructureTFComponent)parent).deco;
		}
	}



	/**
	 * Add a new component in the specified direction
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addNewComponent(StructureComponent entrance, List list, Random random, int facing, int x, int y, int z) 
	{
		int index = this.componentType + 1;
		int nFacing = (this.coordBaseMode + facing) % 4;
		int nx = this.getXWithOffset(x, z);
		int ny = this.getYWithOffset(y);
		int nz = this.getZWithOffset(x, z);
		
		// limit sprawl to a reasonable amount
		if (index > 50 || isOutOfRange(entrance, nx, ny, nz, 112))
		{
			return;
		}
		
		// are we looking at a point we can possibly break in to?
		StructureTFStrongholdComponent breakIn = this.findBreakInComponent(list, nx, ny, nz);
		if (breakIn != null && breakIn.attemptToBreakIn(nx, ny, nz))
		{
			// success!
			this.addDoorwayTo(x, y, z, facing);
			return;
		}
		
		TFStrongholdPieces pieceList = ((ComponentTFStrongholdEntrance)entrance).lowerPieces;
		
		StructureTFStrongholdComponent nextComponent = pieceList.getNextComponent(entrance, list, random, index, nFacing, nx, ny, nz);
		
		// is it clear?
		if (nextComponent != null)
		{
			// if so, add it
			list.add(nextComponent);
			nextComponent.buildComponent(entrance, list, random);
			this.addDoorwayTo(x, y, z, facing);
		}
	}
	
	/**
	 * Check the list for components we can break in to at the specified point
	 */
	protected StructureTFStrongholdComponent findBreakInComponent(List<StructureTFStrongholdComponent> list, int x, int y, int z)
	{
		for (StructureTFStrongholdComponent component : list)
		{
			if (component.boundingBox != null && component.boundingBox.isVecInside(x, y, z))
			{
				return component;
			}
		}
		
		return null;
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addNewUpperComponent(StructureComponent parent, List list, Random random, int facing, int x, int y, int z) 
	{
		StructureTFStrongholdComponent attempted = null;
		
		int index = this.componentType + 1;
		int nFacing = (this.coordBaseMode + facing) % 4;
		int nx = this.getXWithOffset(x, z);
		int ny = this.getYWithOffset(y);
		int nz = this.getZWithOffset(x, z);
		
		// limit sprawl to a reasonable amount
		if (index > 100 || isOutOfRange(parent, nx, ny, nz, 48))
		{
			return;
		}
		
		// find a new component
		switch (random.nextInt(5))
		{
		case 0:
		default :
			attempted = new ComponentTFStrongholdUpperTIntersection(index, nFacing, nx, ny, nz);
			break;
		case 1:
			attempted = new ComponentTFStrongholdUpperLeftTurn(index, nFacing, nx, ny, nz);
			break;
		case 2:
			attempted = new ComponentTFStrongholdUpperRightTurn(index, nFacing, nx, ny, nz);
			break;
		case 3:
			attempted = new ComponentTFStrongholdUpperCorridor(index, nFacing, nx, ny, nz);
			break;
		case 4:
			attempted = new ComponentTFStrongholdUpperAscender(index, nFacing, nx, ny, nz);
			break;
		}
		
		
		// is it clear?
		if (attempted != null && StructureComponent.findIntersecting(list, attempted.getBoundingBox()) == null)
		{
			// if so, add it
			list.add(attempted);
			attempted.buildComponent(parent, list, random);
			
		}

		
	}
	
	/**
	 * Have we strayed more than range blocks away from the center?
	 */
	private boolean isOutOfRange(StructureComponent parent, int nx, int ny, int nz, int range) {
			
		return Math.abs(nx - parent.getBoundingBox().minX) > range
				|| Math.abs(nz - parent.getBoundingBox().minZ) > range;
	}

	/**
	 * Make a doorway
	 */
	protected void placeDoorwayAt(World world, Random rand, int x, int y, int z, StructureBoundingBox sbb) {
		if (x == 0 || x == this.getXSize())
		{
			this.fillWithMetadataBlocks(world, sbb, x, y, z - 2, x, y + 3, z + 2, deco.fenceID, deco.fenceMeta, Blocks.AIR, 0, false);
			this.fillWithAir(world, sbb, x, y, z - 1, x, y + 3, z + 1);
		}
		else
		{
			this.fillWithMetadataBlocks(world, sbb, x - 2, y, z, x + 2, y + 3, z, deco.fenceID, deco.fenceMeta, Blocks.AIR, 0, false);
			this.fillWithAir(world, sbb, x - 1, y, z, x + 1, y + 3, z);
		}
		
		//this.placeBlockAtCurrentPosition(world, Blocks.WOOL, this.coordBaseMode, x, y, z, sbb);
	}

	protected int getXSize() {
		switch (this.getCoordBaseMode())
		{
		default:
		case 0:
		case 2:
			return this.boundingBox.getXSize() - 1;
		case 1:
		case 3:
			return this.boundingBox.getZSize() - 1;
		}
	}

	/**
	 * Make a smaller doorway
	 */
	protected void placeSmallDoorwayAt(World world, Random rand, int facing, int x, int y, int z, StructureBoundingBox sbb) {
		if (facing == 0 || facing == 2)
		{
			this.fillWithMetadataBlocks(world, sbb, x - 1, y, z, x + 1, y + 1, z, Blocks.COBBLESTONE_WALL, 0, Blocks.AIR, 0, true);
			this.fillWithAir(world, sbb, x, y, z, x, y + 1, z);
		}
		else
		{
			this.fillWithMetadataBlocks(world, sbb, x, y, z - 1, x, y + 1, z + 1, Blocks.COBBLESTONE_WALL, 0, Blocks.AIR, 0, true);
			this.fillWithAir(world, sbb, x, y, z, x, y + 1, z);
		}
		
		//this.placeBlockAtCurrentPosition(world, Blocks.WOOL, this.coordBaseMode, x, y, z, sbb);
	}

	/**
	 * Generate a statue in the corner
	 */
	public void placeCornerStatue(World world, int x, int y, int z, int facing, StructureBoundingBox sbb) {

		// set offsets and stair metas
		int ox = 1;
		int oz = 1;
		int smx = 2;
		int smz = 3;


		switch (facing)
		{
		case 0:
			// already set up
			break;
		case 1:
			oz = -1;
			smz = 1;
			break;
		case 2 :
			ox = -1;
			smx = 0;
			break;
		case 3:
			ox = -1;
			oz = -1;
			smx = 0;
			smz = 1;
			break;			
		}

		// the center is always the same
		for (int sy = 0; sy < 5; sy++)
		{
			this.placeBlockAtCurrentPosition(world, deco.pillarID, deco.pillarMeta, x, y + sy, z, sbb);
		}

		// antlers
		this.placeBlockAtCurrentPosition(world, Blocks.FENCE, 0, x + 0, y + 4, z + oz, sbb);
		this.placeBlockAtCurrentPosition(world, Blocks.FENCE, 0, x + ox, y + 4, z + 0, sbb);

		// arms
		this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(smz), x + 0, y + 3, z + oz, sbb);
		this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(smx), x + ox, y + 3, z + 0, sbb);
		this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(smz) + 4, x + 0, y + 2, z + oz, sbb);
		this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(smx) + 4, x + ox, y + 2, z + 0, sbb);
		this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(smx) + 4, x + ox, y + 2, z + oz, sbb);

		// sword
		this.placeBlockAtCurrentPosition(world, Blocks.COBBLESTONE_WALL, 0, x + ox, y + 0, z + oz, sbb);
		this.placeBlockAtCurrentPosition(world, Blocks.COBBLESTONE_WALL, 0, x + ox, y + 1, z + oz, sbb);

		// feet
		this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(smz), x + 0, y + 0, z + oz, sbb);
		this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(smx), x + ox, y + 0, z + 0, sbb);

	}
	
	/**
	 * Make a statue that faces out from a wall
	 */
	public void placeWallStatue(World world, int x, int y, int z, int facing, StructureBoundingBox sbb) {
		int ox = 1;
		int oz = 1;
		
		// the center is always the same
		for (int sy = 0; sy < 5; sy++)
		{
			this.placeBlockAtCurrentPosition(world, deco.pillarID, deco.pillarMeta, x, y + sy, z, sbb);
		}
		
		if (facing == 0 || facing == 2)
		{
			if (facing == 2)
			{
				ox = -ox;
				oz = -oz;
			}
			
			// antlers
			this.placeBlockAtCurrentPosition(world, Blocks.FENCE, 0, x - ox, y + 4, z, sbb);
			this.placeBlockAtCurrentPosition(world, Blocks.FENCE, 0, x + ox, y + 4, z, sbb);
	
			// arms
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(0 + facing), x - ox, y + 3, z, sbb);
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(2 + facing), x + ox, y + 3, z, sbb);
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(1 + facing), x - ox, y + 3, z - oz, sbb);
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(1 + facing), x + ox, y + 3, z - oz, sbb);

			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(0 + facing) + 4, x - ox, y + 2, z, sbb);
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(2 + facing) + 4, x + ox, y + 2, z, sbb);
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(1 + facing) + 4, x + 0, y + 2, z - oz, sbb);
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(1 + facing) + 4, x - ox, y + 2, z - oz, sbb);
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(1 + facing) + 4, x + ox, y + 2, z - oz, sbb);
			
			// sword
			this.placeBlockAtCurrentPosition(world, Blocks.COBBLESTONE_WALL, 0, x, y + 0, z - oz, sbb);
			this.placeBlockAtCurrentPosition(world, Blocks.COBBLESTONE_WALL, 0, x, y + 1, z - oz, sbb);
			
			// feet
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(0 + facing), x - ox, y + 0, z + 0, sbb);
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(2 + facing), x + ox, y + 0, z + 0, sbb);
		}
		else
		{
			if (facing == 3)
			{
				oz = -oz;
				ox = -ox;
			}
			
			// antlers
			this.placeBlockAtCurrentPosition(world, Blocks.FENCE, 0, x, y + 4, z - oz, sbb);
			this.placeBlockAtCurrentPosition(world, Blocks.FENCE, 0, x, y + 4, z + oz, sbb);
	
			// arms
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(0 + facing), x, y + 3, z - oz, sbb);
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(2 + facing), x, y + 3, z + oz, sbb);
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(1 + facing), x + ox, y + 3, z - oz, sbb);
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(1 + facing), x + ox, y + 3, z + oz, sbb);

			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(0 + facing) + 4, x, y + 2, z - oz, sbb);
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(2 + facing) + 4, x, y + 2, z + oz, sbb);
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(1 + facing) + 4, x + oz, y + 2, z + 0, sbb);
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(1 + facing) + 4, x + ox, y + 2, z - oz, sbb);
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(1 + facing) + 4, x + ox, y + 2, z + oz, sbb);
			
			// sword
			this.placeBlockAtCurrentPosition(world, Blocks.COBBLESTONE_WALL, 0, x + ox, y + 0, z, sbb);
			this.placeBlockAtCurrentPosition(world, Blocks.COBBLESTONE_WALL, 0, x + ox, y + 1, z, sbb);
			
			// feet
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(0 + facing), x, y + 0, z - ox, sbb);
			this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(2 + facing), x, y + 0, z + ox, sbb);
		}

	}
	
	/**
	 * Called curing construction.  If an attempted component collides with this one, try "breaking in".
	 */
	public boolean attemptToBreakIn(int wx, int wy, int wz)
	{
		if (!isValidBreakInPoint(wx, wy, wz))
		{
			//System.out.println("Break in failed because the point is invalid");
			
			//System.out.printf("Break point is %d, %d, %d, bounding sides are %d-%d x %d-%d\n", wx, wy, wz, this.boundingBox.minX, this.boundingBox.maxX, this.boundingBox.minZ, this.boundingBox.maxZ);

			
			return false;
		}
		else
		{
			int dx = this.getRelativeX(wx, wz);
			int dy = this.getRelativeY(wy);
			int dz = this.getRelativeZ(wx, wz);
			
			addDoor(dx, dy, dz);
			
			//System.out.printf("Break in success for component %s, at %d, %d, %d!  Facing is %d\n", this, dx, dy, dz, this.getCoordBaseMode());
			
			return true;
		}
	}

	/**
	 * Add a door to our list
	 */
	public void addDoorwayTo(int dx, int dy, int dz, int facing) {
		switch (facing)
		{
		case 0:
			addDoor(dx, dy, dz - 1);
			break;
		case 1:
			addDoor(dx + 1, dy, dz);
			break;
		case 2:
			addDoor(dx, dy, dz + 1);
			break;
		case 3:
			addDoor(dx - 1, dy, dz);
			break;
		}
	}
	
	/**
	 * Add a door to our list
	 */
	public void addDoor(int dx, int dy, int dz) {
		this.doors.add(new BlockPos(dx, dy, dz));
	}

	/**
	 * Is the specified point a valid spot to break in?
	 */
	protected boolean isValidBreakInPoint(int wx, int wy, int wz) {
		if (wy < this.boundingBox.minY || wy > this.boundingBox.maxY)
		{
			return false;
		}
		else if (wx == this.boundingBox.minX || wx == this.boundingBox.maxX)
		{
			return wz > this.boundingBox.minZ && wz < this.boundingBox.maxZ;
		}
		else if (wz == this.boundingBox.minZ || wz == this.boundingBox.maxZ)
		{
			return wx > this.boundingBox.minX && wx < this.boundingBox.maxX;
		}
		else
		{
			return false;
		}
	}

	protected int getRelativeX(int x, int z)
    {
		//this.getXWithOffset(x, z);

        switch(getCoordBaseMode())
        {
        case 0:
            return x - boundingBox.minX;
        case 2:
            return boundingBox.maxX - x;
        case 1:
            return z - boundingBox.minZ;
        case 3:
            return boundingBox.maxZ - z;
        }
        return x;
    }
	
    protected int getRelativeY(int par1)
    {
        return par1 - this.boundingBox.minY;
    }

    protected int getRelativeZ(int x, int z)
    {
        switch(getCoordBaseMode())
        {
        case 0:
            return z - boundingBox.minZ;
        case 2:
            return boundingBox.maxZ - z;
        case 1:
            return boundingBox.maxX - x;
        case 3:
            return x - boundingBox.minX;
        }
        return z;
    }
    
    /**
     * Place any doors on our list
     */
    public void placeDoors(World world, Random rand, StructureBoundingBox sbb)
    {
		if (this.doors != null)
		{
			for (BlockPos doorCoords : doors)
			{
				this.placeDoorwayAt(world, rand, doorCoords.posX, doorCoords.posY, doorCoords.posZ, sbb);
				
				//this.placeBlockAtCurrentPosition(world, Blocks.WOOL, doorCoords.posX, doorCoords.posX, doorCoords.posY + 2, doorCoords.posZ, sbb);
			}
		}
    }

    /**
     * Place stronghold walls in every position except those filled with dirt.
     */
    protected void placeStrongholdWalls(World world, StructureBoundingBox sbb, int sx, int sy, int sz, int dx, int dy, int dz, Random rand, StructureComponent.BlockSelector randomBlocks) {
    	for (int y = sy; y <= dy; ++y)
    	{
    		for (int x = sx; x <= dx; ++x)
    		{
    			for (int z = sz; z <= dz; ++z)
    			{
    				boolean wall = y == sy || y == dy || x == sx || x == dx || z == sz || z == dz;
    				Block blockID = this.getBlockAtCurrentPosition(world, x, y, z, sbb);

    				if (blockID == Blocks.AIR)
    				{
    					// cobblestone to "fill in holes"
    					if (wall)
    					{
    						this.placeBlockAtCurrentPosition(world, Blocks.COBBLESTONE, 0, x, y, z, sbb);
    					}
    				}
    				else if (y == sy || y == dy)
    				{
						// do stronghold bricks for floor/ceiling
    					StructureComponent.BlockSelector strongBlocks = StructureTFComponent.getStrongholdStones();
    					strongBlocks.selectBlocks(rand, x, y, z, wall);
    					this.placeBlockAtCurrentPosition(world, strongBlocks.func_151561_a(), strongBlocks.getSelectedBlockMetaData(), x, y, z, sbb);
    					
    				}
    				else if (!wall || blockID != Blocks.DIRT)  // leave dirt there
    				{
						// and use decorator (with presumably underbricks) for walls
    					randomBlocks.selectBlocks(rand, x, y, z, wall);
    					this.placeBlockAtCurrentPosition(world, randomBlocks.func_151561_a(), randomBlocks.getSelectedBlockMetaData(), x, y, z, sbb);
    				}
    			}
    		}
    	}	
    }


    /**
     * Place stronghold walls on dirt/grass/stone
     */
    protected void placeUpperStrongholdWalls(World world, StructureBoundingBox sbb, int sx, int sy, int sz, int dx, int dy, int dz, Random rand, StructureComponent.BlockSelector randomBlocks) {
    	for (int y = sy; y <= dy; ++y)
    	{
    		for (int x = sx; x <= dx; ++x)
    		{
    			for (int z = sz; z <= dz; ++z)
    			{
    				boolean wall = y == sy || y == dy || x == sx || x == dx || z == sz || z == dz;
    				Block blockID = this.getBlockAtCurrentPosition(world, x, y, z, sbb);

    				if ((blockID != Blocks.AIR && (blockID.getMaterial() == Material.ROCK || blockID.getMaterial() == Material.GRASS || blockID.getMaterial() == Material.GROUND))
    						|| (blockID == Blocks.AIR && rand.nextInt(3) == 0) && this.getBlockAtCurrentPosition(world, x, y - 1, z, sbb) == Blocks.STONEBRICK)
    				{
    					if (y == sy || y == dy)
        				{
    						// do stronghold bricks for floor/ceiling
        					StructureComponent.BlockSelector strongBlocks = StructureTFComponent.getStrongholdStones();
        					strongBlocks.selectBlocks(rand, x, y, z, wall);
        					this.placeBlockAtCurrentPosition(world, strongBlocks.func_151561_a(), strongBlocks.getSelectedBlockMetaData(), x, y, z, sbb);
        					
        				}
    					else
    					{
    						// and use decorator (with presumably underbricks) for walls
    						randomBlocks.selectBlocks(rand, x, y, z, wall);
    						this.placeBlockAtCurrentPosition(world, randomBlocks.func_151561_a(), randomBlocks.getSelectedBlockMetaData(), x, y, z, sbb);
    					}
    				}
    			}
    		}
    	}	
    }


}