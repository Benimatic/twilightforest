package twilightforest.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import twilightforest.block.TFBlocks;

public class TFGenThorns extends TFGenerator {

	private static final int MAX_SPREAD = 7;
	private static final int CHANCE_OF_BRANCH = 3;
	private static final int CHANCE_OF_LEAF = 3;
	private static final int CHANCE_LEAF_IS_ROSE = 50;

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		
		// make a 3-5 long stack going up
		int nextLength = 2 + rand.nextInt(4);
		int maxLength = 2  + rand.nextInt(4) + rand.nextInt(4) + rand.nextInt(4);
		
		placeThorns(world, rand, x, y, z, nextLength, ForgeDirection.UP, maxLength, x, y, z);
		
		
		return true;
	}

	private void placeThorns(World world, Random rand, int x, int y, int z, int length, ForgeDirection dir, int maxLength, int ox, int oy, int oz) {
		boolean complete = false;
		for (int i = 0; i < length; i++) {
			int dx = x + (dir.offsetX * i);
			int dy = y + (dir.offsetY * i);
			int dz = z + (dir.offsetZ * i);
			
			if (Math.abs(dx - ox) < MAX_SPREAD && Math.abs(dz - oz) < MAX_SPREAD && canPlaceThorns(world, dx, dy, dz)) {
				this.setBlockAndMetadata(world, dx, dy, dz, TFBlocks.thorns, getMetaFor(dir));
				
				// did we make it to the end?
				if (i == length - 1) {
					complete = true;
					// maybe a leaf?  or a rose?
					if (rand.nextInt(CHANCE_OF_LEAF) == 0 && world.isAirBlock(dx + dir.offsetX, dy + dir.offsetY, dz + dir.offsetZ)) {
						if (rand.nextInt(CHANCE_LEAF_IS_ROSE) > 0) {
							// leaf
							this.setBlockAndMetadata(world, dx + dir.offsetX, dy + dir.offsetY, dz + dir.offsetZ, TFBlocks.leaves3, 0);
						} else {
							// rose
							this.setBlock(world, dx + dir.offsetX, dy + dir.offsetY, dz + dir.offsetZ, TFBlocks.thornRose);
						}
					}
				}
			} else {
				break;
			}
		}
		
		// add another off the end
		if (complete && maxLength > 1) {
			
			ForgeDirection nextDir = ForgeDirection.VALID_DIRECTIONS[rand.nextInt(ForgeDirection.VALID_DIRECTIONS.length)];
			
			int nextX = x + (dir.offsetX * (length - 1)) + nextDir.offsetX;
			int nextY = y + (dir.offsetY * (length - 1)) + nextDir.offsetY;
			int nextZ = z + (dir.offsetZ * (length - 1)) + nextDir.offsetZ;
			
			int nextLength = 1 + rand.nextInt(maxLength);
			
			this.placeThorns(world, rand, nextX, nextY, nextZ, nextLength, nextDir, maxLength - 1, ox, oy, oz);
		}

		// maybe another branch off the middle
		if (complete && length > 3 && rand.nextInt(CHANCE_OF_BRANCH) == 0) {
			
			int middle = rand.nextInt(length);
			
			ForgeDirection nextDir = ForgeDirection.VALID_DIRECTIONS[rand.nextInt(ForgeDirection.VALID_DIRECTIONS.length)];
			
			int nextX = x + (dir.offsetX * middle) + nextDir.offsetX;
			int nextY = y + (dir.offsetY * middle) + nextDir.offsetY;
			int nextZ = z + (dir.offsetZ * middle) + nextDir.offsetZ;
			
			int nextLength = 1 + rand.nextInt(maxLength);
			
			this.placeThorns(world, rand, nextX, nextY, nextZ, nextLength, nextDir, maxLength - 1, ox, oy, oz);
		}
		
		// maybe a leaf
		if (complete && length > 3 && rand.nextInt(CHANCE_OF_LEAF) == 0) {
			
			int middle = rand.nextInt(length);
			
			ForgeDirection nextDir = ForgeDirection.VALID_DIRECTIONS[rand.nextInt(ForgeDirection.VALID_DIRECTIONS.length)];
			
			int nextX = x + (dir.offsetX * middle) + nextDir.offsetX;
			int nextY = y + (dir.offsetY * middle) + nextDir.offsetY;
			int nextZ = z + (dir.offsetZ * middle) + nextDir.offsetZ;
			
			if (world.isAirBlock(nextX, nextY, nextZ)) {
				this.setBlockAndMetadata(world, nextX, nextY, nextZ, TFBlocks.leaves3, 0);
			}
		}
		
	}

	private boolean canPlaceThorns(World world, int dx, int dy, int dz) {
		return world.isAirBlock(dx, dy, dz) || world.getBlock(dx, dy, dz).isLeaves(world, dx, dy, dz);
	}

	private int getMetaFor(ForgeDirection dir) {
		switch (dir) {
		case UNKNOWN:
		default:
		case UP:
		case DOWN:
			return 0;
		case EAST:
		case WEST:
			return 4;
		case NORTH:
		case SOUTH:
			return 8;
		}
	}

}
