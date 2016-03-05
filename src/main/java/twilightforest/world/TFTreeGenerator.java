package twilightforest.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import twilightforest.block.BlockTFRoots;
import twilightforest.block.TFBlocks;

public abstract class TFTreeGenerator extends WorldGenAbstractTree {

	protected Block treeBlock = TFBlocks.log;
	protected int treeMeta = 3;
	protected int branchMeta = 15;
	protected Block leafBlock = TFBlocks.hedge;
	protected int leafMeta = 1;
	protected Block rootBlock = TFBlocks.root;
	protected int rootMeta = BlockTFRoots.ROOT_META;


	public TFTreeGenerator() {
		this(false);
	}

	public TFTreeGenerator(boolean par1) {
		super(par1);
	}

	/**
	 * Build a root, but don't let it stick out too far into thin air because that's weird
	 */
	protected void buildRoot(World world, int x, int y, int z, double offset, int b) {
		ChunkCoordinates dest = translateCoords(x, y - b - 2, z, 5, 0.3 * b + offset, 0.8);

		// go through block by block and stop drawing when we head too far into open air
		ChunkCoordinates[] lineArray = getBresehnamArrayCoords(x, y - b - 2, z, dest.posX, dest.posY, dest.posZ);
		for (ChunkCoordinates coord : lineArray) 
		{
			this.placeRootBlock(world, coord.posX, coord.posY, coord.posZ, rootBlock, rootMeta);
		}
	}
	
	/**
	 * Function used to actually place root blocks if they're not going to break anything important
	 */
	protected void placeRootBlock(World world, int x, int y, int z, Block rootBlock2, int meta) {
		if (canRootGrowIn(world, x, y, z))
		{
			this.setBlockAndMetadata(world, x, y, z, rootBlock2, meta);
		}
	}

	public static boolean canRootGrowIn(World world, int x, int y, int z) {
		Block blockID = world.getBlock(x, y, z);
		
		if (blockID == Blocks.air) {
			// roots can grow through air if they are near a solid block
			return isNearSolid(world, x, y, z);
		}
		else
		{
			return blockID != Blocks.bedrock && blockID != Blocks.obsidian && blockID != TFBlocks.shield;
		}
	}
	
	
	/**
	 * Moves distance along the vector.
	 * 
	 * This goofy function takes a float between 0 and 1 for the angle, where 0 is 0 degrees, .5 is 180 degrees and 1 and 360 degrees.
	 * For the tilt, it takes a float between 0 and 1 where 0 is straight up, 0.5 is straight out and 1 is straight down. 
	 */
	public static int[] translate(int sx, int sy, int sz, double distance, double angle, double tilt) {
		return TFGenerator.translate(sx, sy, sz, distance, angle, tilt);
	}

	protected static ChunkCoordinates translateCoords(int sx, int sy, int sz, double length, double angle, double tilt) {
		return TFGenerator.translateCoords(sx, sy, sz, length, angle, tilt);
	}

	/**
	 * Get an array of values that represent a line from point A to point B
	 */
	public static ChunkCoordinates[] getBresehnamArrayCoords(ChunkCoordinates src, ChunkCoordinates dest) {
		return TFGenerator.getBresehnamArrayCoords(src.posX, src.posY, src.posZ, dest.posX, dest.posY, dest.posZ);
	}
	
	/**
	 * Get an array of values that represent a line from point A to point B
	 */
	public static ChunkCoordinates[] getBresehnamArrayCoords(int x1, int y1, int z1, int x2, int y2, int z2) {
		return TFGenerator.getBresehnamArrayCoords(x1, y1, z1, x2, y2, z2);
	}

	
	protected static boolean isNearSolid(IBlockAccess world, int bx, int by, int bz) {
		return TFGenerator.isNearSolid(world, bx, by, bz);
	}
	
	protected static boolean hasAirAround(IBlockAccess world, int bx, int by, int bz) {
		return TFGenerator.hasAirAround(world, bx, by, bz);
	}
	
	/**
	 * Temporary override
	 */
	protected void setBlock(World world, int x, int y, int z, Block block) {
		this.func_150515_a(world, x, y, z, block);
	}
	/**
	 * Temporary override
	 */
	protected void setBlockAndMetadata(World world, int x, int y, int z, Block block, int meta) {
		this.setBlockAndNotifyAdequately(world, x, y, z, block, meta);
	}
	
	
	/**
	 * Draw a flat blob (circle) of leaves
	 */
	public void makeLeafCircle(World world, int sx, int sy, int sz, int rad, Block blockValue, int metaValue)
	{
		this.makeLeafCircle(world, sx, sy, sz, rad, blockValue, metaValue, false);
	}
	
	
	/*
	 * Fully duplicated code from TFGenerator below.  Where is my multiple inheritance?
	 */
	
	
	/**
	 * Draws a line from {x1, y1, z1} to {x2, y2, z2}
	 */
	protected void drawBresehnam(World world, int x1, int y1, int z1, int x2, int y2, int z2, Block blockValue, int metaValue)
	{
		ChunkCoordinates[] lineArray = getBresehnamArrayCoords(x1, y1, z1, x2, y2, z2);
		for (ChunkCoordinates pixel : lineArray) 
		{
			setBlockAndMetadata(world, pixel.posX, pixel.posY, pixel.posZ, blockValue, metaValue);
		}
	}
	
	/**
	 * Draw a flat blob (circle) of leaves
	 */
	public void makeLeafCircle(World world, int sx, int sy, int sz, int rad, Block blockValue, int metaValue, boolean useHack)
	{
		// trace out a quadrant
		for (byte dx = 0; dx <= rad; dx++)
		{
			for (byte dz = 0; dz <= rad; dz++)
			{
				int dist = Math.max(dx, dz) + (Math.min(dx, dz) >> 1);

				//hack!  I keep getting failing leaves at a certain position.
				if (useHack && dx == 3 && dz == 3) {
					dist = 6;
				}
				
				// if we're inside the blob, fill it
				if (dist <= rad) {
					// do four at a time for easiness!
					putLeafBlock(world, sx + dx, sy, sz + dz, blockValue, metaValue);
					putLeafBlock(world, sx + dx, sy, sz - dz, blockValue, metaValue);
					putLeafBlock(world, sx - dx, sy, sz + dz, blockValue, metaValue);
					putLeafBlock(world, sx - dx, sy, sz - dz, blockValue, metaValue);
				}
			}
		}
	}
	
	/**
	 * Draw a flat blob (circle) of leaves.  This one makes it offset to surround a 2x2 area instead of a 1 block area
	 */
	public void makeLeafCircle2(World world, int sx, int sy, int sz, int rad, Block blockValue, int metaValue, boolean useHack)
	{
		// trace out a quadrant
		for (byte dx = 0; dx <= rad; dx++)
		{
			for (byte dz = 0; dz <= rad; dz++)
			{
//				int dist = Math.max(dx, dz) + (int)(Math.min(dx, dz) * 0.6F);
//
//				//hack!  I keep getting failing leaves at a certain position.
//				if (useHack && dx == 3 && dz == 3) {
//					dist = 6;
//				}
				
				// if we're inside the blob, fill it
				if (dx * dx + dz * dz <= rad * rad) {
					// do four at a time for easiness!
					putLeafBlock(world, sx + 1 + dx, sy, sz + 1 + dz, blockValue, metaValue);
					putLeafBlock(world, sx + 1 + dx, sy, sz - dz, blockValue, metaValue);
					putLeafBlock(world, sx - dx, sy, sz + 1 + dz, blockValue, metaValue);
					putLeafBlock(world, sx - dx, sy, sz - dz, blockValue, metaValue);
				}
			}
		}
	}
	
	/**
	 * Draw a giant blob of leaves.
	 */
	public void drawLeafBlob(World world, int sx, int sy, int sz, int rad, Block blockValue, int metaValue) {
		// then trace out a quadrant
		for (byte dx = 0; dx <= rad; dx++)
		{
			for (byte dy = 0; dy <= rad; dy++)
			{
				for (byte dz = 0; dz <= rad; dz++)
				{
					// determine how far we are from the center.
					int dist = 0;
					if (dx >= dy && dx >= dz) {
						dist = dx + (Math.max(dy, dz) >> 1) + (Math.min(dy, dz) >> 2);
					} else if (dy >= dx && dy >= dz)
					{
						dist = dy + (Math.max(dx, dz) >> 1) + (Math.min(dx, dz) >> 2);
					} else {
						dist = dz + (Math.max(dx, dy) >> 1) + (Math.min(dx, dy) >> 2);
					}


					// if we're inside the blob, fill it
					if (dist <= rad) {
						// do eight at a time for easiness!
						putLeafBlock(world, sx + dx, sy + dy, sz + dz, blockValue, metaValue);
						putLeafBlock(world, sx + dx, sy + dy, sz - dz, blockValue, metaValue);
						putLeafBlock(world, sx - dx, sy + dy, sz + dz, blockValue, metaValue);
						putLeafBlock(world, sx - dx, sy + dy, sz - dz, blockValue, metaValue);
						putLeafBlock(world, sx + dx, sy - dy, sz + dz, blockValue, metaValue);
						putLeafBlock(world, sx + dx, sy - dy, sz - dz, blockValue, metaValue);
						putLeafBlock(world, sx - dx, sy - dy, sz + dz, blockValue, metaValue);
						putLeafBlock(world, sx - dx, sy - dy, sz - dz, blockValue, metaValue);
					}
				}
			}
		}
	}
	
	/**
	 * Put a leaf only in spots where leaves can go!
	 */
	public void putLeafBlock(World world, int x, int y, int z, Block blockValue, int metaValue) {
        Block whatsThere = world.getBlock(x, y, z);
        Block block = whatsThere;

        if (block == null || block.canBeReplacedByLeaves(world, x, y, z))
        {
            this.setBlockAndMetadata(world, x, y, z, blockValue, metaValue);
        }
	}
}