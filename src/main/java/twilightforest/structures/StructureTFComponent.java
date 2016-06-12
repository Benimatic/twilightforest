package twilightforest.structures;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.TFTreasure;


public abstract class StructureTFComponent extends StructureComponent {

    private static final StructureTFStrongholdStones strongholdStones = new StructureTFStrongholdStones();
    
    public StructureTFDecorator deco = null;
    public int spawnListIndex = 0;

    public StructureTFComponent() {}
    
	public StructureTFComponent(int i) {
		super(i);
	}

	/**
	 * Save to NBT
	 */
	@Override
    protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setInteger("si", this.spawnListIndex);
        par1NBTTagCompound.setString("deco", StructureTFDecorator.getDecoString(this.deco));
    }
	
	/**
	 * Load from NBT
	 */
	@Override
    protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
    {
        this.spawnListIndex = par1NBTTagCompound.getInteger("si");
        this.deco = StructureTFDecorator.getDecoFor(par1NBTTagCompound.getString("deco"));
    }
    
    public static StructureBoundingBox getComponentToAddBoundingBox(int x, int y, int z, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int dir)
    {
        switch(dir)
        {
        default:
            return new StructureBoundingBox(x + minX, y + minY, z + minZ, x + maxX + minX, y + maxY + minY, z + maxZ + minZ);

        case 0: // '\0'
            return new StructureBoundingBox(x + minX, y + minY, z + minZ, x + maxX + minX, y + maxY + minY, z + maxZ + minZ);

        case 1: // '\001'
            return new StructureBoundingBox(x - maxZ + minZ, y + minY, z + minX, x + minZ, y + maxY + minY, z + maxX + minX);

        case 2: // '\002'
            return new StructureBoundingBox(x - maxX - minX, y + minY, z - maxZ - minZ, x - minX, y + maxY + minY, z - minZ);

        case 3: // '\003'
            return new StructureBoundingBox(x + minZ, y + minY, z - maxX, x + maxZ + minZ, y + maxY + minY, z + minX);
        }
    }    
    
    /**
     * Fixed a bug with direction 1 and -z values, but I'm not sure if it'll break other things
     */
    public static StructureBoundingBox getComponentToAddBoundingBox2(int x, int y, int z, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int dir)
    {
        switch(dir)
        {
        default:
            return new StructureBoundingBox(x + minX, y + minY, z + minZ, x + maxX + minX, y + maxY + minY, z + maxZ + minZ);

        case 0: // '\0'
            return new StructureBoundingBox(x + minX, y + minY, z + minZ, x + maxX + minX, y + maxY + minY, z + maxZ + minZ);

        case 1: // '\001'
            return new StructureBoundingBox(x - maxZ - minZ, y + minY, z + minX, x - minZ, y + maxY + minY, z + maxX + minX);

        case 2: // '\002'
            return new StructureBoundingBox(x - maxX - minX, y + minY, z - maxZ - minZ, x - minX, y + maxY + minY, z - minZ);

        case 3: // '\003'
            return new StructureBoundingBox(x + minZ, y + minY, z - maxX, x + maxZ + minZ, y + maxY + minY, z - minX);
        }
    }
    
    /**
     * Place a monster spawner at the specified coordinates
     * 
     * @param monsterID
     */
    protected TileEntityMobSpawner placeSpawnerAtCurrentPosition(World world, Random rand, int x, int y, int z, String monsterID, StructureBoundingBox sbb)
    {
    	TileEntityMobSpawner tileEntitySpawner = null;
    	
        int dx = getXWithOffset(x, z);
        int dy = getYWithOffset(y);
        int dz = getZWithOffset(x, z);
        if(sbb.isVecInside(dx, dy, dz) && world.getBlock(dx, dy, dz) != Blocks.MOB_SPAWNER)
        {
            world.setBlock(dx, dy, dz, Blocks.MOB_SPAWNER, 0, 2);
            tileEntitySpawner = (TileEntityMobSpawner)world.getTileEntity(dx, dy, dz);
            if(tileEntitySpawner != null)
            {
            	tileEntitySpawner.func_145881_a().setEntityName(monsterID);
            }
        }
        
        return tileEntitySpawner;
    }

    /**
     * Place a monster spawner at the specified coordinates, as if rotated
     * 
     * @param monsterID
     */
    protected TileEntityMobSpawner placeSpawnerRotated(World world, int x, int y, int z, int rotation, String monsterID, StructureBoundingBox sbb)
    {
    	TileEntityMobSpawner tileEntitySpawner = null;
    	
        int dx = getXWithOffsetAsIfRotated(x, z, rotation);
        int dy = getYWithOffset(y);
        int dz = getZWithOffsetAsIfRotated(x, z, rotation);
        if(sbb.isVecInside(dx, dy, dz) && world.getBlock(dx, dy, dz) != Blocks.MOB_SPAWNER)
        {
            world.setBlock(dx, dy, dz, Blocks.MOB_SPAWNER, 0, 2);
            tileEntitySpawner = (TileEntityMobSpawner)world.getTileEntity(dx, dy, dz);
            if(tileEntitySpawner != null)
            {
            	tileEntitySpawner.func_145881_a().setEntityName(monsterID);
            }
        }
        
        return tileEntitySpawner;
    }


    /**
     * Place a treasure chest at the specified coordinates
     * 
     * @param treasureType
     */
    protected void placeTreasureAtCurrentPosition(World world, Random rand, int x, int y, int z, TFTreasure treasureType, StructureBoundingBox sbb)
    {
        this.placeTreasureAtCurrentPosition(world, rand, x, y, z, treasureType, false, sbb);
    }

    /**
     * Place a treasure chest at the specified coordinates
     * 
     * @param treasureType
     */
    protected void placeTreasureAtCurrentPosition(World world, Random rand, int x, int y, int z, TFTreasure treasureType, boolean trapped, StructureBoundingBox sbb)
    {
        int dx = getXWithOffset(x, z);
        int dy = getYWithOffset(y);
        int dz = getZWithOffset(x, z);
        if(sbb.isVecInside(dx, dy, dz) && world.getBlock(dx, dy, dz) != Blocks.CHEST)
        {
            treasureType.generate(world, rand, dx, dy, dz, trapped ? Blocks.TRAPPED_CHEST : Blocks.CHEST);
        }
    }

    /**
     * Place a treasure chest at the specified coordinates
     * 
     * @param treasureType
     */
    protected void placeTreasureRotated(World world, int x, int y, int z, int rotation, TFTreasure treasureType, StructureBoundingBox sbb)
    {
        this.placeTreasureRotated(world, x, y, z, rotation, treasureType, false, sbb);
    }

    /**
     * Place a treasure chest at the specified coordinates
     * 
     * @param treasureType
     */
    protected void placeTreasureRotated(World world, int x, int y, int z, int rotation, TFTreasure treasureType, boolean trapped, StructureBoundingBox sbb)
    {
        int dx = getXWithOffsetAsIfRotated(x, z, rotation);
        int dy = getYWithOffset(y);
        int dz = getZWithOffsetAsIfRotated(x, z, rotation);
        if(sbb.isVecInside(dx, dy, dz) && world.getBlock(dx, dy, dz) != Blocks.CHEST)
        {
            treasureType.generate(world, null, dx, dy, dz, trapped ? Blocks.TRAPPED_CHEST : Blocks.CHEST);
        }
    }
    
    protected void placeSignAtCurrentPosition(World world, int x, int y, int z, String string0, String string1, StructureBoundingBox sbb) {
        int dx = getXWithOffset(x, z);
        int dy = getYWithOffset(y);
        int dz = getZWithOffset(x, z);
        if (sbb.isVecInside(dx, dy, dz) && world.getBlock(dx, dy, dz) != Blocks.STANDING_SIGN)
        {
            world.setBlock(dx, dy, dz, Blocks.STANDING_SIGN, (this.coordBaseMode * 2), 2);
            
            TileEntitySign teSign = (TileEntitySign)world.getTileEntity(dx, dy, dz);
            if (teSign != null)
            {
            	teSign.signText[1] = string0;
            	teSign.signText[2] = string1;
            }
        }
	}


//	
//	public boolean makeTowerWing2(List list, Random rand, int index, int x, int y, int z, int size, int height, int direction) {
//		
//		ComponentTFTowerWing wing = new ComponentTFTowerWing(index, x, y, z, size, height, direction);
//		// check to see if it intersects something already there
//		StructureComponent intersect = StructureComponent.getIntersectingStructureComponent(list, wing.boundingBox);
//		if (intersect == null || intersect == this) {
//			list.add(wing);
//			wing.buildComponent(this, list, rand);
//			return true;
//		} else {
//			System.out.println("Planned wing intersects with " + intersect);
//			return false;
//		}
//	}
	
	/**
	 * Provides coordinates to make a tower such that it will open into the parent tower at the provided coordinates.
	 */
	protected int[] offsetTowerCoords(int x, int y, int z, int towerSize, int direction) {
		
		int dx = getXWithOffset(x, z);
		int dy = getYWithOffset(y);
		int dz = getZWithOffset(x, z);
		
		if (direction == 0) {
			return new int[] {dx + 1, dy - 1, dz - towerSize / 2};
		}
		else if (direction == 1) {
			return new int[] {dx + towerSize / 2, dy - 1, dz + 1};
		}
		else if (direction == 2) {
			return new int[] {dx - 1, dy - 1, dz + towerSize / 2};
		}
		else if (direction == 3) {
			return new int[] {dx - towerSize / 2, dy - 1, dz - 1};
		}
		
		
		// ugh?
		return new int[] {x, y, z};
	}

	/**
	 * Provides coordinates to make a tower such that it will open into the parent tower at the provided coordinates.
	 */
	protected ChunkCoordinates offsetTowerCCoords(int x, int y, int z, int towerSize, int direction) {
		
		int dx = getXWithOffset(x, z);
		int dy = getYWithOffset(y);
		int dz = getZWithOffset(x, z);
		
		if (direction == 0) {
			return new ChunkCoordinates(dx + 1, dy - 1, dz - towerSize / 2);
		} else if (direction == 1) {
			return new ChunkCoordinates(dx + towerSize / 2, dy - 1, dz + 1);
		} else if (direction == 2) {
			return new ChunkCoordinates(dx - 1, dy - 1, dz + towerSize / 2);
		} else if (direction == 3) {
			return new ChunkCoordinates(dx - towerSize / 2, dy - 1, dz - 1);
		}
		
		
		// ugh?
		return new ChunkCoordinates(x, y, z);
	}


	public int[] getOffsetAsIfRotated(int src[], int rotation) {
		int temp = this.getCoordBaseMode();
		int[] dest = new int[3];
		this.setCoordBaseMode(rotation);
		
		dest[0] = getXWithOffset(src[0], src[2]);
		dest[1] = getYWithOffset(src[1]);
		dest[2] = getZWithOffset(src[0], src[2]);
		
		this.setCoordBaseMode(temp);
		return dest;
	}


	protected int getXWithOffset(int x, int z)
    {
        switch(getCoordBaseMode())
        {
        case 0: // '\0'
            return boundingBox.minX + x;
        case 1: // '\001'
            return boundingBox.maxX - z;
        case 2: // '\002'
            return boundingBox.maxX - x;
        case 3: // '\003'
            return boundingBox.minX + z;
        }
        return x;
    }
	
    protected int getYWithOffset(int par1)
    {
        return super.getYWithOffset(par1);
    }

    protected int getZWithOffset(int x, int z)
    {
        switch(getCoordBaseMode())
        {
        case 0: // '\0'
            return boundingBox.minZ + z;
        case 1: // '\001'
            return boundingBox.minZ + x;
        case 2: // '\002'
            return boundingBox.maxZ - z;
        case 3: // '\003'
            return boundingBox.maxZ - x;
        }
        return z;
    }

	/**
	 * Pretend as though the structure is rotated beyond what it already is
	 */
	protected int getXWithOffsetAsIfRotated(int x, int z, int rotation)
	{
		if (coordBaseMode < 0)
		{
			return x;
		}
		
	    switch((coordBaseMode + rotation) % 4)
	    {
	    case 0:
	        return boundingBox.minX + x;
	    case 1:
	        return boundingBox.maxX - z;
	    case 2:
	        return boundingBox.maxX - x;
	    case 3:
	        return boundingBox.minX + z;
	    }
	    return x;
	}

    protected int getZWithOffsetAsIfRotated(int x, int z, int rotation)
    {
		if (coordBaseMode < 0)
		{
			return x;
		}
		else
		{
			switch((coordBaseMode + rotation) % 4)
			{
			case 0:
				return boundingBox.minZ + z;
			case 1:
				return boundingBox.minZ + x;
			case 2:
				return boundingBox.maxZ - z;
			case 3:
				return boundingBox.maxZ - x;
			}
			return z;
		}
    }

	public int getCoordBaseMode() {
		return coordBaseMode;
	}

	public void setCoordBaseMode(int coordBaseMode) {
		this.coordBaseMode = coordBaseMode;
	}
	
    protected Block getBlockAtCurrentPosition(World par1World, int par2, int par3, int par4, StructureBoundingBox par5StructureBoundingBox) {
    	return super.getBlockAtCurrentPosition(par1World, par2, par3, par4, par5StructureBoundingBox);
    }
    
    /**
     * current Position depends on currently set Coordinates mode, is computed here
     */
    protected void placeBlockAtCurrentPosition(World par1World, Block par2, int par3, int par4, int par5, int par6, StructureBoundingBox par7StructureBoundingBox) {
    	super.placeBlockAtCurrentPosition(par1World, par2, par3, par4, par5, par6, par7StructureBoundingBox);
    }
    
    /**
     * Place a block as though the entire structure were rotated the specified amount beyond what it already is
     */
    protected void placeBlockRotated(World world, Block blockID, int meta, int x, int y, int z, int rotation, StructureBoundingBox sbb)
    {
        int dx = this.getXWithOffsetAsIfRotated(x, z, rotation);
        int dy = this.getYWithOffset(y);
        int dz = this.getZWithOffsetAsIfRotated(x, z, rotation);

        if (sbb.isVecInside(dx, dy, dz))
        {
            world.setBlock(dx, dy, dz, blockID, meta, 2);
        }
    }
    
    /**
     * Get a block ID as though the entire structure were rotated the specified amount beyond what it already is
     */
    protected Block getBlockIDRotated(World world, int x, int y, int z, int rotation, StructureBoundingBox sbb)
    {
        int dx = this.getXWithOffsetAsIfRotated(x, z, rotation);
        int dy = this.getYWithOffset(y);
        int dz = this.getZWithOffsetAsIfRotated(x, z, rotation);

        if (sbb.isVecInside(dx, dy, dz))
        {
            return world.getBlock(dx, dy, dz);
        }
        else
        {
        	return Blocks.AIR;
        }
    }
    
    /**
     * Fill with blocks as though rotated
     */
    protected void fillBlocksRotated(World world, StructureBoundingBox sbb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block blockID, int meta, int rotation)
    {
        for (int dx = minY; dx <= maxY; ++dx)
        {
            for (int dy = minX; dy <= maxX; ++dy)
            {
                for (int dz = minZ; dz <= maxZ; ++dz)
                {
                    this.placeBlockRotated(world, blockID, meta, dy, dx, dz, rotation, sbb);
                }
            }
        }
    }

    /**
     * Fill with blocks as though rotated
     */
    protected void randomlyFillBlocksRotated(World world, StructureBoundingBox sbb, Random rand, float chance, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block blockID, int meta, Block blockID2, int meta2, int rotation)
    {
        for (int dx = minY; dx <= maxY; ++dx)
        {
            for (int dy = minX; dy <= maxX; ++dy)
            {
                for (int dz = minZ; dz <= maxZ; ++dz)
                {
                	if (rand.nextFloat() < chance) {
                        this.placeBlockRotated(world, blockID, meta, dy, dx, dz, rotation, sbb);
                	} else {
                        this.placeBlockRotated(world, blockID2, meta2, dy, dx, dz, rotation, sbb);
                	}
                }
            }
        }
    }
    

	public void fillToGroundRotated(World world, Block stonebrick, int meta, int x, int y, int z, int rotation, StructureBoundingBox sbb) {
        int dx = this.getXWithOffsetAsIfRotated(x, z, rotation);
        int dy = this.getYWithOffset(y);
        int dz = this.getZWithOffsetAsIfRotated(x, z, rotation);

        if (sbb.isVecInside(dx, dy, dz)) {
            while ((world.isAirBlock(dx, dy, dz) || world.getBlock(dx, dy, dz).getMaterial().isLiquid()) && dy > 1) {
            	world.setBlock(dx, dy, dz, stonebrick, meta, 2);
                --dy;
            }
        }
	}


    /**
     * Fill with blocks as though rotated
     */
    protected void fillAirRotated(World world, StructureBoundingBox sbb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int rotation)
    {
        this.fillBlocksRotated(world, sbb, minX, minY, minZ, maxX, maxY, maxZ, Blocks.AIR, 0, rotation);
    }

    public static StructureComponent.BlockSelector getStrongholdStones() {
    	return strongholdStones;
    }

    
	
	/**
	 * Gets the metadata necessary to make stairs facing the proper direction.
	 * 
	 * @param dir
	 * @return
	 */
	protected int getStairMeta(int dir) {
		switch ((this.getCoordBaseMode() + dir) % 4) {
		case 0:
			return 0;
		case 1:
			return 2;
		case 2:
			return 1;
		case 3:
			return 3;
		default:
			return -1; // this is impossible
		}
	}
	
	/**
	 * Gets the metadata necessary to stick the ladder on the specified wall.
	 * 
	 * @param ladderDir
	 * @return
	 */
	protected int getLadderMeta(int ladderDir) {
		// ladder data values are... dumb.
		switch ((this.getCoordBaseMode() + ladderDir) % 4) {
		case 0:
			return 4;
		case 1:
			return 2;
		case 2:
			return 5;
		case 3:
			return 3;
		default:
			return -1; // this is impossible
		}
	}

	/**
	 * Gets the metadata necessary to stick the ladder on the specified wall.
	 */
	protected int getLadderMeta(int ladderDir, int rotation) {
		return getLadderMeta(ladderDir + rotation);
	}

	/**
	 * Nullify all the sky light in this component bounding box
	 */
	public void nullifySkyLightForBoundingBox(World world) {
		this.nullifySkyLight(world, boundingBox.minX - 1, boundingBox.minY - 1, boundingBox.minZ - 1, boundingBox.maxX + 1, boundingBox.maxY + 1, boundingBox.maxZ + 1);
	}

	/**
	 * Nullify all the sky light at the specified positions, using local coordinates
	 */
	public void nullifySkyLightAtCurrentPosition(World world, int sx, int sy, int sz, int dx, int dy, int dz) 
	{
		// resolve all variables to their actual in-world positions
		nullifySkyLight(world, getXWithOffset(sx, sz), getYWithOffset(sy), getZWithOffset(sx, sz), getXWithOffset(dx, dz), getYWithOffset(dy), getZWithOffset(dx, dz));
	}
	
	/**
	 * Nullify all the sky light at the specified positions, using world coordinates
	 */
	public void nullifySkyLight(World world, int sx, int sy, int sz, int dx, int dy, int dz) 
	{
		for (int x = sx; x <= dx; x++) 
		{
         	for (int z = sz; z <= dz; z++) 
         	{
             	for (int y = sy; y <= dy; y++) 
             	{
             		world.setLightValue(EnumSkyBlock.Sky, x, y, z, 0);
             	}
         	}
     	}
	}

	/**
	 * Discover the y coordinate that will serve as the ground level of the supplied BoundingBox. (A median of all the
	 * levels in the BB's horizontal rectangle).
	 * 
	 * This is basically copied from ComponentVillage
	 */
	protected int getAverageGroundLevel(World world, StructureBoundingBox sbb) {
	    int totalHeight = 0;
	    int heightCount = 0;
	
	    for (int bz = this.boundingBox.minZ; bz <= this.boundingBox.maxZ; ++bz)
	    {
	        for (int by = this.boundingBox.minX; by <= this.boundingBox.maxX; ++by)
	        {
	            if (sbb.isVecInside(by, 64, bz))
	            {
	                totalHeight += Math.max(world.getTopSolidOrLiquidBlock(by, bz), world.provider.getAverageGroundLevel());
	                ++heightCount;
	            }
	        }
	    }
	
	    if (heightCount == 0)
	    {
	        return -1;
	    }
	    else
	    {
	        return totalHeight / heightCount;
	    }
	}
	
	/**
	 * Find what y level the dirt/grass/stone is.  Just check the center of the chunk we're given
	 */
	protected int getSampledDirtLevel(World world, StructureBoundingBox sbb) {
	    int dirtLevel = 256;
	    
	    for (int y = 90; y > 0; y--) // is 90 like a good place to start? :)
	    {
	    	int cx = sbb.getCenterX();
	    	int cz = sbb.getCenterZ();
	    	
	    	Material material = world.getBlock(cx, y, cz).getMaterial();
	    	if (material == Material.ground || material == Material.rock || material == Material.grass)
	    	{
	    		dirtLevel = y;
	    		break;
	    	}
	    }
	    
	    return dirtLevel;
	}

	/**
	 * Temporary replacement
	 */
	protected void randomlyPlaceBlock(World world, StructureBoundingBox sbb, Random rand, float chance, int x, int y, int z, Block blockPlaced, int meta) {
		this.func_151552_a(world, sbb, rand, chance, x, y, z, blockPlaced, meta);
	}

	/**
	 * Does this component fall under block protection when progression is turned on, normally true
	 */
	public boolean isComponentProtected() {
		return true;
	}
	
	
    /**
     * Discover if bounding box can fit within the current bounding box object.
     */
    public static StructureComponent findIntersectingExcluding(List list, StructureBoundingBox toCheck, StructureComponent exclude)
    {
        Iterator iterator = list.iterator();
        StructureComponent structurecomponent;

        do
        {
            if (!iterator.hasNext())
            {
                return null;
            }

            structurecomponent = (StructureComponent)iterator.next();
        }
        while (structurecomponent == exclude || structurecomponent.getBoundingBox() == null || !structurecomponent.getBoundingBox().intersectsWith(toCheck));

        return structurecomponent;
    }

    

}