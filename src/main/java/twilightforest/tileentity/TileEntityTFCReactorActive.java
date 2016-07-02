package twilightforest.tileentity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import twilightforest.block.BlockTFTowerTranslucent;
import twilightforest.block.TFBlocks;
import twilightforest.entity.EntityTFMiniGhast;

public class TileEntityTFCReactorActive extends TileEntity implements ITickable {
	
	int counter = 0;
	
	int secX, secY, secZ;
	int terX, terY, terZ;
	
	
    public TileEntityTFCReactorActive() {
    	Random rand = new Random();
    	
    	// determine the two smaller bursts
    	this.secX = 3 * (rand.nextBoolean() ? 1 : -1);
    	this.secY = 3 * (rand.nextBoolean() ? 1 : -1);
    	this.secZ = 3 * (rand.nextBoolean() ? 1 : -1);
    	
    	this.terX = 3 * (rand.nextBoolean() ? 1 : -1);
    	this.terY = 3 * (rand.nextBoolean() ? 1 : -1);
    	this.terZ = 3 * (rand.nextBoolean() ? 1 : -1);
    	
    	if (secX == terX && secY == terY && secZ == terZ)
    	{
    		terX = -terX;
    		terY = -terY;
    		terZ = -terZ;
    	}
	}

    @Override
	public void update()
    {
    	counter++;
    	
    	if (!worldObj.isRemote)
    	{
    		
    		// every 2 seconds for 10 seconds, destroy a new radius
			int offset = 10;
    		
    		if (counter % 5 == 0)
    		{
    			if (counter == 5)
    			{
    				// transformation!
    				worldObj.setBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				
    				worldObj.setBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 0, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 0, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				

    				worldObj.setBlock(this.xCoord + 1, this.yCoord + 0, this.zCoord + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.xCoord + 1, this.yCoord + 0, this.zCoord - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.xCoord - 1, this.yCoord + 0, this.zCoord + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.xCoord - 1, this.yCoord + 0, this.zCoord - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				
    				worldObj.setBlock(this.xCoord + 0, this.yCoord + 0, this.zCoord + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.xCoord + 0, this.yCoord + 0, this.zCoord - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.xCoord + 1, this.yCoord + 0, this.zCoord + 0, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.xCoord - 1, this.yCoord + 0, this.zCoord + 0, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				
    				
    				worldObj.setBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				
    				worldObj.setBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 0, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 0, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				
    			}
    			
    			
    			// primary burst
    			int primary = counter - 80;

    			if (primary >= offset && primary <= 249)
    			{
    				drawBlob(this.xCoord, this.yCoord, this.zCoord, (primary - offset) / 40, Blocks.AIR, 0, primary - offset, false);
    			}
    			if (primary <= 200)
    			{
    				drawBlob(this.xCoord, this.yCoord, this.zCoord, primary / 40, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_REACTOR_DEBRIS, counter, false);
    			}
    			
    			// secondary burst
    			int secondary = counter - 120;
    			
    			if (secondary >= offset && secondary <= 129)
    			{
    				drawBlob(this.xCoord + secX, this.yCoord + secY, this.zCoord + secZ, (secondary - offset) / 40, Blocks.AIR, 0, secondary - offset, false);
    			}
    			if (secondary >= 0 && secondary <= 160)
    			{
    				drawBlob(this.xCoord + secX, this.yCoord + secY, this.zCoord + secZ, secondary / 40, Blocks.AIR, 0, secondary, true);
    			}
    			
    			// tertiary burst
    			int tertiary = counter - 160;
    			
    			if (tertiary >= offset && tertiary <= 129)
    			{
    				drawBlob(this.xCoord + terX, this.yCoord + terY, this.zCoord + terZ, (tertiary - offset) / 40, Blocks.AIR, 0, tertiary - offset, false);
    			}
    			if (tertiary >= 0 && tertiary <= 160)
    			{
    				drawBlob(this.xCoord + terX, this.yCoord + terY, this.zCoord + terZ, tertiary / 40, Blocks.AIR, 0, tertiary, true);
    			}

    		}
    		
    		
    		
    		
			if (counter >= 350)
			{
				// spawn mini ghasts near the secondary & tertiary points
				for (int i = 0; i < 3; i++)
				{
					spawnGhastNear(this.xCoord + secX, this.yCoord + secY, this.zCoord + secZ);
					spawnGhastNear(this.xCoord + terX, this.yCoord + terY, this.zCoord + terZ);
				}
				
				// deactivate & explode
		        worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 2.0F, true);
		        
				worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.AIR, 0, 3);
			}
    		
    	}
		else
		{
			if (counter % 5 == 0 && counter <= 250)
			{
				// sound
				worldObj.playSound(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "portal.portal", counter / 100F, counter / 100F, false);
			}
		}

    }

    
	private void spawnGhastNear(int x, int y, int z) {
		EntityTFMiniGhast ghast = new EntityTFMiniGhast(worldObj);
		ghast.setLocationAndAngles(x - 1.5 + worldObj.rand.nextFloat() * 3.0, y - 1.5 + worldObj.rand.nextFloat() * 3.0, z - 1.5 + worldObj.rand.nextFloat() * 3.0, worldObj.rand.nextFloat() * 360F, 0.0F);
		worldObj.spawnEntityInWorld(ghast);
	}


	/**
	 * Draw a giant blob of whatevs (okay, it's going to be leaves).
	 */
	public void drawBlob(int sx, int sy, int sz, int rad, Block blockValue, int metaValue, int fuzz, boolean netherTransform) {
		// then trace out a quadrant
		for (byte dx = 0; dx <= rad; dx++)
		{
			// transform fuzz
			int fuzzX = (fuzz + dx) % 8;

			for (byte dy = 0; dy <= rad; dy++)
			{
				int fuzzY = (fuzz + dy) % 8;
				
				for (byte dz = 0; dz <= rad; dz++)
				{
					// determine how far we are from the center.
					byte dist = 0;
					if (dx >= dy && dx >= dz) 
					{
						dist = (byte) (dx + (byte)((Math.max(dy, dz) * 0.5) + (Math.min(dy, dz) * 0.25)));
					} 
					else if (dy >= dx && dy >= dz)
					{
						dist = (byte) (dy + (byte)((Math.max(dx, dz) * 0.5) + (Math.min(dx, dz) * 0.25)));
					} 
					else 
					{
						dist = (byte) (dz + (byte)((Math.max(dx, dy) * 0.5) + (Math.min(dx, dy) * 0.25)));
					}
					
					// if we're inside the blob, fill it
					if (dist == rad && !(dx == 0 && dy == 0 && dz == 0)) {
						// do eight at a time for easiness!
						switch (fuzzX)
						{
						case 0:
							transformBlock(sx + dx, sy + dy, sz + dz, blockValue, metaValue, fuzzY, netherTransform);
							break;
						case 1:
							transformBlock(sx + dx, sy + dy, sz - dz, blockValue, metaValue, fuzzY, netherTransform);
							break;
						case 2:
							transformBlock(sx - dx, sy + dy, sz + dz, blockValue, metaValue, fuzzY, netherTransform);
							break;
						case 3:
							transformBlock(sx - dx, sy + dy, sz - dz, blockValue, metaValue, fuzzY, netherTransform);
							break;
						case 4:
							transformBlock(sx + dx, sy - dy, sz + dz, blockValue, metaValue, fuzzY, netherTransform);
							break;
						case 5:
							transformBlock(sx + dx, sy - dy, sz - dz, blockValue, metaValue, fuzzY, netherTransform);
							break;
						case 6:
							transformBlock(sx - dx, sy - dy, sz + dz, blockValue, metaValue, fuzzY, netherTransform);
							break;
						case 7:
							transformBlock(sx - dx, sy - dy, sz - dz, blockValue, metaValue, fuzzY, netherTransform);
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 */
	protected void transformBlock(int x, int y, int z, Block blockValue, int metaValue, int fuzz, boolean netherTransform)
	{
		Block whatsThere = worldObj.getBlock(x, y, z);
		int whatsMeta = worldObj.getBlockMetadata(x, y, z);
		
		if (whatsThere != Blocks.AIR && whatsThere.getBlockHardness(worldObj, x, y, z) == -1)
		{
			// don't destroy unbreakable stuff
			return;
		}
		
		if (fuzz == 0 && whatsThere != Blocks.AIR)
		{
			// make pop thing for original block
			worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(whatsThere) + (whatsMeta << 12));
		}
		
		if (netherTransform && whatsThere != Blocks.AIR)
		{
			worldObj.setBlock(x, y, z, Blocks.NETHERRACK, 0, 3);
			// fire on top?
			if (worldObj.getBlock(x, y + 1, z) == Blocks.AIR && fuzz % 3 == 0)
			{
				worldObj.setBlock(x, y + 1, z, Blocks.FIRE, 0, 3);
			}
		}
		else
		{
			worldObj.setBlock(x, y, z, blockValue, metaValue, 3);
		}
	}

}
