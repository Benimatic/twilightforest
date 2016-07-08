package twilightforest.tileentity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import twilightforest.block.BlockTFTowerTranslucent;
import twilightforest.block.TFBlocks;
import twilightforest.entity.EntityTFMiniGhast;

public class TileEntityTFCReactorActive extends TileEntity implements ITickable {
	
	private int counter = 0;
	
	private int secX, secY, secZ;
	private int terX, terY, terZ;
	
	
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
    				worldObj.setBlock(this.pos.getX() + 1, this.pos.getY() + 1, this.pos.getZ() + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.pos.getX() + 1, this.pos.getY() + 1, this.pos.getZ() - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.pos.getX() - 1, this.pos.getY() + 1, this.pos.getZ() + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.pos.getX() - 1, this.pos.getY() + 1, this.pos.getZ() - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				
    				worldObj.setBlock(this.pos.getX() + 0, this.pos.getY() + 1, this.pos.getZ() + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.pos.getX() + 0, this.pos.getY() + 1, this.pos.getZ() - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.pos.getX() + 1, this.pos.getY() + 1, this.pos.getZ() + 0, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.pos.getX() - 1, this.pos.getY() + 1, this.pos.getZ() + 0, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				

    				worldObj.setBlock(this.pos.getX() + 1, this.pos.getY() + 0, this.pos.getZ() + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.pos.getX() + 1, this.pos.getY() + 0, this.pos.getZ() - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.pos.getX() - 1, this.pos.getY() + 0, this.pos.getZ() + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.pos.getX() - 1, this.pos.getY() + 0, this.pos.getZ() - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				
    				worldObj.setBlock(this.pos.getX() + 0, this.pos.getY() + 0, this.pos.getZ() + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.pos.getX() + 0, this.pos.getY() + 0, this.pos.getZ() - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.pos.getX() + 1, this.pos.getY() + 0, this.pos.getZ() + 0, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.pos.getX() - 1, this.pos.getY() + 0, this.pos.getZ() + 0, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				
    				
    				worldObj.setBlock(this.pos.getX() + 1, this.pos.getY() - 1, this.pos.getZ() + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.pos.getX() + 1, this.pos.getY() - 1, this.pos.getZ() - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.pos.getX() - 1, this.pos.getY() - 1, this.pos.getZ() + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				worldObj.setBlock(this.pos.getX() - 1, this.pos.getY() - 1, this.pos.getZ() - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_DIAMOND, 2);
    				
    				worldObj.setBlock(this.pos.getX() + 0, this.pos.getY() - 1, this.pos.getZ() + 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.pos.getX() + 0, this.pos.getY() - 1, this.pos.getZ() - 1, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.pos.getX() + 1, this.pos.getY() - 1, this.pos.getZ() + 0, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				worldObj.setBlock(this.pos.getX() - 1, this.pos.getY() - 1, this.pos.getZ() + 0, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_FAKE_GOLD, 2);
    				
    			}
    			
    			
    			// primary burst
    			int primary = counter - 80;

    			if (primary >= offset && primary <= 249)
    			{
    				drawBlob(this.pos.getX(), this.pos.getY(), this.pos.getZ(), (primary - offset) / 40, Blocks.AIR, 0, primary - offset, false);
    			}
    			if (primary <= 200)
    			{
    				drawBlob(this.pos.getX(), this.pos.getY(), this.pos.getZ(), primary / 40, TFBlocks.towerTranslucent, BlockTFTowerTranslucent.META_REACTOR_DEBRIS, counter, false);
    			}
    			
    			// secondary burst
    			int secondary = counter - 120;
    			
    			if (secondary >= offset && secondary <= 129)
    			{
    				drawBlob(this.pos.getX() + secX, this.pos.getY() + secY, this.pos.getZ() + secZ, (secondary - offset) / 40, Blocks.AIR, 0, secondary - offset, false);
    			}
    			if (secondary >= 0 && secondary <= 160)
    			{
    				drawBlob(this.pos.getX() + secX, this.pos.getY() + secY, this.pos.getZ() + secZ, secondary / 40, Blocks.AIR, 0, secondary, true);
    			}
    			
    			// tertiary burst
    			int tertiary = counter - 160;
    			
    			if (tertiary >= offset && tertiary <= 129)
    			{
    				drawBlob(this.pos.getX() + terX, this.pos.getY() + terY, this.pos.getZ() + terZ, (tertiary - offset) / 40, Blocks.AIR, 0, tertiary - offset, false);
    			}
    			if (tertiary >= 0 && tertiary <= 160)
    			{
    				drawBlob(this.pos.getX() + terX, this.pos.getY() + terY, this.pos.getZ() + terZ, tertiary / 40, Blocks.AIR, 0, tertiary, true);
    			}

    		}
    		
    		
    		
    		
			if (counter >= 350)
			{
				// spawn mini ghasts near the secondary & tertiary points
				for (int i = 0; i < 3; i++)
				{
					spawnGhastNear(this.pos.getX() + secX, this.pos.getY() + secY, this.pos.getZ() + secZ);
					spawnGhastNear(this.pos.getX() + terX, this.pos.getY() + terY, this.pos.getZ() + terZ);
				}
				
				// deactivate & explode
		        worldObj.createExplosion(null, this.pos.getX(), this.pos.getY(), this.pos.getZ(), 2.0F, true);
				worldObj.setBlockToAir(pos);
			}
    		
    	}
		else
		{
			if (counter % 5 == 0 && counter <= 250)
			{
				// sound
				worldObj.playSound(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, "portal.portal", counter / 100F, counter / 100F, false);
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
	protected void transformBlock(BlockPos pos, IBlockState state, int fuzz, boolean netherTransform)
	{
		IBlockState stateThere = worldObj.getBlockState(pos);

		if (stateThere.getBlock() != Blocks.AIR && stateThere.getBlockHardness(worldObj, pos) == -1)
		{
			// don't destroy unbreakable stuff
			return;
		}
		
		if (fuzz == 0 && stateThere.getBlock() != Blocks.AIR)
		{
			// make pop thing for original block
			worldObj.playEvent(2001, pos, Block.getStateId(stateThere));
		}
		
		if (netherTransform && stateThere.getBlock() != Blocks.AIR)
		{
			worldObj.setBlockState(pos, Blocks.NETHERRACK.getDefaultState(), 3);
			// fire on top?
			if (worldObj.isAirBlock(pos.up()) && fuzz % 3 == 0)
			{
				worldObj.setBlockState(pos.up(), Blocks.FIRE.getDefaultState(), 3);
			}
		}
		else
		{
			worldObj.setBlockState(pos, state, 3);
		}
	}

}
