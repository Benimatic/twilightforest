package twilightforest.item;

import twilightforest.block.TFBlocks;
import twilightforest.world.WorldProviderTwilightForest;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemTFMagicBeans extends ItemTF {
	
	public ItemTFMagicBeans() {
		this.makeRare();
	}
	
	
    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		Block blockAt = world.getBlock(x, y, z);
		
		int minY = y + 1;
		int maxY = Math.max(y + 100, (int) (getCloudHeight(world) + 25));
		if (y < maxY && blockAt == TFBlocks.uberousSoil) {

			if (!world.isRemote) {
				makeHugeStalk(world, x, z, minY, maxY);
			}
			
			return true;
		} else {
			return false;
		}
    }

	/**
	 * Try to find the given world's cloud height
	 * @param world
	 * @return
	 */
	private float getCloudHeight(World world) {
		
		if (world.provider instanceof WorldProviderTwilightForest) {
			// WorldProviderTwilightForest has this method on both server and client
			return ((WorldProviderTwilightForest)world.provider).getCloudHeight();
		} else {
			// are we on a dedicated server
			//TODO: don't use exceptions for program flow?
			try {
				return world.provider.getCloudHeight();
			} catch (NoSuchMethodError nsme) {
				// this method exists even on a dedicated server
				return world.provider.terrainType.getCloudHeight();
			}
		}
	}


	private void makeHugeStalk(World world, int x, int z, int minY, int maxY) {
		int yOffset = world.rand.nextInt(100);
		
		float cScale = world.rand.nextFloat() * 0.25F + 0.125F; // spiral tightness scaling
		float rScale =  world.rand.nextFloat() * 0.25F + 0.125F; // radius change scaling
		
		// offset x and z to make stalk start at origin
		float radius = 4F + MathHelper.sin((minY + yOffset) * rScale) * 3F; // make radius a little wavy
		x -= MathHelper.sin((minY + yOffset) * cScale) * radius;
		z -= MathHelper.cos((minY + yOffset) * cScale) * radius;
		
		// leaves!
		int nextLeafY = minY + 10 + world.rand.nextInt(20);
		
		// make stalk
		boolean isClear = true;
		for (int dy = minY; dy < maxY && isClear; dy++) {
			// make radius a little wavy
			radius = 5F + MathHelper.sin((dy + yOffset) * rScale) * 2.5F; 
			
			// find center of stalk
			float cx = x + MathHelper.sin((dy + yOffset) * cScale) * radius;
			float cz = z + MathHelper.cos((dy + yOffset) * cScale) * radius;

			
			float stalkThickness = 2.5F;
			
			// reduce thickness near top
			if (maxY - dy < 5) {
				stalkThickness *= (maxY - dy) / 5F;
			}

			int minX = MathHelper.floor_float(x - radius - stalkThickness);
			int maxX = MathHelper.ceiling_float_int(x + radius + stalkThickness);
			int minZ = MathHelper.floor_float(z - radius - stalkThickness);
			int maxZ = MathHelper.ceiling_float_int(z + radius + stalkThickness);
			
			// generate stalk
			for (int dx = minX; dx < maxX; dx++) {
				for (int dz = minZ; dz < maxZ; dz++) {
					if ((dx - cx) * (dx - cx) + (dz - cz) * (dz - cz) < stalkThickness * stalkThickness) {
						isClear &= this.tryToPlaceStalk(world, dx, dy, dz);
					}
				}
			}
			
			// leaves?
			if (dy == nextLeafY) {
				// make leaf blob
				
				int lx = (int) (x + MathHelper.sin((dy + yOffset) * cScale) * (radius + stalkThickness));
				int lz = (int) (z + MathHelper.cos((dy + yOffset) * cScale) * (radius + stalkThickness));
				
				this.placeLeaves(world, lx, dy, lz);
				
				nextLeafY = dy + 5 + world.rand.nextInt(10);
			}
		}
	}

	private void placeLeaves(World world, int x, int y, int z) {
		// stalk at center
		world.setBlock(x, y, z, TFBlocks.hugeStalk);
		
		// small squares
		for (int dx = -1; dx <= 1; dx++) {
			for (int dz = -1; dz <= 1; dz++) {
				this.tryToPlaceLeaves(world, x + dx, y - 1, z + dz);
				this.tryToPlaceLeaves(world, x + dx, y + 1, z + dz);
			}
		}
		// larger square
		for (int dx = -2; dx <= 2; dx++) {
			for (int dz = -2; dz <= 2; dz++) {
				if (!((dx == 2 || dx == -2) && (dz == 2 || dz == -2))) {
					this.tryToPlaceLeaves(world, x + dx, y + 0, z + dz);
				}
			}
		}
	}


	/**
	 * Place the stalk block only if the destination is clear.  Return false if blocked.
	 */
	private boolean tryToPlaceStalk(World world, int x, int y, int z) {
		Block blockThere = world.getBlock(x, y, z);
		if (blockThere == Blocks.air || blockThere.isReplaceable(world, x, y, z) || blockThere.canBeReplacedByLeaves(world, x, y, z) || blockThere.isLeaves(world, x, y, z) || blockThere.canSustainLeaves(world, x, y, z)) {
			world.setBlock(x, y, z, TFBlocks.hugeStalk);
			return true;
		} else {
			return false;
		}
	}


	private void tryToPlaceLeaves(World world, int x, int y, int z) {
		Block blockThere = world.getBlock(x, y, z);
		if (blockThere == Blocks.air || blockThere.canBeReplacedByLeaves(world, x, y, z)) {
			world.setBlock(x, y, z, TFBlocks.leaves3, 1, 2);
		}
	}

}
