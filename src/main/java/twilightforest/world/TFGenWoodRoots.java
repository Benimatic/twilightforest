package twilightforest.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import twilightforest.block.BlockTFRoots;
import twilightforest.block.TFBlocks;


public class TFGenWoodRoots extends TFGenerator {
	
	private Block rootBlock = TFBlocks.root;
	private int rootMeta = BlockTFRoots.ROOT_META;
	private Block oreBlock = TFBlocks.root;
	private int oreMeta = BlockTFRoots.OREROOT_META;

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		// start must be in stone
		if (world.getBlock(x, y, z) != Blocks.STONE) {
			return false;
		}
		
		float length = rand.nextFloat() * 6.0F + rand.nextFloat() * 6.0F + 4.0F;
		if (length > y) {
			length = y;
		}
		
		// tilt between 0.6 and 0.9
		float tilt = 0.6F + rand.nextFloat() * 0.3F;
		
		return drawRoot(world, rand, x, y, z, length, rand.nextFloat(), tilt);
	}

	private boolean drawRoot(World world, Random rand, int x, int y, int z, float length, float angle, float tilt) {
		// put origin at where we start
		return this.drawRoot(world, rand, x, y, z, x, y, z, length, angle, tilt);
	}

	
	private boolean drawRoot(World world, Random rand, int ox, int oy, int oz, int x, int y, int z, float length, float angle, float tilt) {
		// generate a direction and a length
		int[] dest = translate(x, y, z, length, angle, tilt);
		
		// restrict x and z to within 7
		int limit = 6;
		if (ox + limit < dest[0])
		{
			dest[0] = ox + limit;
		}
		if (ox - limit > dest[0])
		{
			dest[0] = ox - limit;
		}
		if (oz + limit < dest[2])
		{
			dest[2] = oz + limit;
		}
		if (oz - limit > dest[2])
		{
			dest[2] = oz - limit;
		}

		// end must be in stone
		if (world.getBlock(dest[0], dest[1], dest[2]) != Blocks.STONE) {
			return false;
		}
		
		// if both the start and the end are in stone, put a root there
		ChunkCoordinates[] lineArray = getBresehnamArrayCoords(x, y, z, dest[0], dest[1], dest[2]);
		for (ChunkCoordinates coord : lineArray) {
			this.placeRootBlock(world, coord.posX, coord.posY, coord.posZ, rootBlock, rootMeta);
		}

		
		// if we are long enough, make either another root or an oreball
		if (length > 8) {
			if (rand.nextInt(3) > 0) {
				// length > 8, usually split off into another root half as long
				int[] nextSrc = translate(x, y, z, length / 2, angle, tilt);
				float nextAngle = (angle + 0.25F + (rand.nextFloat() * 0.5F)) % 1.0F;
				float nextTilt = 0.6F + rand.nextFloat() * 0.3F;
				drawRoot(world, rand, ox, oy, oz, nextSrc[0], nextSrc[1], nextSrc[2], length / 2.0F, nextAngle, nextTilt);


			}
		}
		
		if (length > 6) {
			if (rand.nextInt(4) == 0) {
				// length > 6, potentially make oreball
				int[] ballSrc = translate(x, y, z, length / 2, angle, tilt);
				int[] ballDest = translate(ballSrc[0], ballSrc[1], ballSrc[2], 1.5, (angle + 0.5F) % 1.0F, 0.75);
				
				this.placeRootBlock(world, ballSrc[0], ballSrc[1], ballSrc[2], oreBlock, oreMeta);
				this.placeRootBlock(world, ballSrc[0], ballSrc[1], ballDest[2], oreBlock, oreMeta);
				this.placeRootBlock(world, ballDest[0], ballSrc[1], ballSrc[2], oreBlock, oreMeta);
				this.placeRootBlock(world, ballDest[0], ballSrc[1], ballDest[2], oreBlock, oreMeta);
				this.placeRootBlock(world, ballSrc[0], ballDest[1], ballSrc[2], oreBlock, oreMeta);
				this.placeRootBlock(world, ballSrc[0], ballDest[1], ballDest[2], oreBlock, oreMeta);
				this.placeRootBlock(world, ballDest[0], ballDest[1], ballSrc[2], oreBlock, oreMeta);
				this.placeRootBlock(world, ballDest[0], ballDest[1], ballDest[2], oreBlock, oreMeta);
			}
		}
		
		return true;
	}
	
	/**
	 * Function used to actually place root blocks if they're not going to break anything important
	 */
	protected void placeRootBlock(World world, int x, int y, int z, Block rootBlock2, int meta) {
		if (TFTreeGenerator.canRootGrowIn(world, x, y, z))
		{
			this.setBlockAndMetadata(world, x, y, z, rootBlock2, meta);
		}
	}

}
