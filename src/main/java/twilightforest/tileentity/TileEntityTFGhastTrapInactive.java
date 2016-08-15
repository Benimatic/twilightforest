package twilightforest.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import twilightforest.TwilightForestMod;
import twilightforest.block.BlockTFTowerDevice;
import twilightforest.block.TFBlocks;
import twilightforest.entity.EntityTFMiniGhast;

public class TileEntityTFGhastTrapInactive extends TileEntity {
	
	int counter;
	Random rand = new org.bogdang.modifications.random.XSTR();

	ArrayList<EntityTFMiniGhast> dyingGhasts = new ArrayList<EntityTFMiniGhast>();
	
    /**
     * Determines if this TileEntity requires update calls.
     * @return True if you want updateEntity() to be called, false if not
     */
	@Override
	public boolean canUpdate() {
		return true;
	}

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
	@Override
	public void updateEntity() 
	{
		// check to see if there are any dying mini ghasts within our scan range
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 1), (double)(this.zCoord + 1)).expand(10D, 16D, 10D);

		List<EntityTFMiniGhast> nearbyGhasts = worldObj.getEntitiesWithinAABB(EntityTFMiniGhast.class, aabb);

		for (EntityTFMiniGhast ghast : nearbyGhasts)
		{
			if (ghast.deathTime > 0)
			{
				this.makeParticlesTo(ghast);

				if (!dyingGhasts.contains(ghast))
				{
					dyingGhasts.add(ghast);
				}
			}
		}

		// display charge level, up to 3
		int chargeLevel = Math.min(3, dyingGhasts.size());

		counter++;

		if (this.worldObj.isRemote)
		{
			// occasionally make a redstone line to a mini ghast
			if (this.counter % 20 == 0 && nearbyGhasts.size() > 0)
			{
				EntityTFMiniGhast highlight = nearbyGhasts.get(rand.nextInt(nearbyGhasts.size()));
				
				this.makeParticlesTo(highlight);
			}

			if (chargeLevel >= 1 && counter % 10 == 0)
			{
				((BlockTFTowerDevice)TFBlocks.towerDevice).sparkle(worldObj, this.xCoord, this.yCoord, this.zCoord, worldObj.rand);

				worldObj.playSound(this.xCoord + 0.5D, this.yCoord + 1.5D, this.zCoord + 0.5D, "note.harp", 1.0F, 1.0F, false);
			}
			if (chargeLevel >= 2)
			{
				worldObj.spawnParticle("smoke", this.xCoord + 0.1 + rand.nextFloat() * 0.8, this.yCoord + 1.05, this.zCoord + 0.1 + rand.nextFloat() * 0.8, (rand.nextFloat() - rand.nextFloat()) * 0.05, 0.00, (rand.nextFloat() - rand.nextFloat()) * 0.05);
				if (counter % 10 == 0)
				{
					worldObj.playSound(this.xCoord + 0.5D, this.yCoord + 1.5D, this.zCoord + 0.5D, "note.harp", 1.2F, 0.8F, false);
				}
			}
			if (chargeLevel >= 3)
			{
				worldObj.spawnParticle("largesmoke", this.xCoord + 0.1 + rand.nextFloat() * 0.8, this.yCoord + 1.05, this.zCoord + 0.1 + rand.nextFloat() * 0.8, (rand.nextFloat() - rand.nextFloat()) * 0.05, 0.05, (rand.nextFloat() - rand.nextFloat()) * 0.05);
				((BlockTFTowerDevice)TFBlocks.towerDevice).sparkle(worldObj, this.xCoord, this.yCoord, this.zCoord, worldObj.rand);
				if (counter % 5 == 0)
				{
					worldObj.playSound(this.xCoord + 0.5D, this.yCoord + 1.5D, this.zCoord + 0.5D, "note.harp", 1.5F, 2F, false);
				}
			}
		}
	}
	
	private void makeParticlesTo(Entity highlight) {
		double sx = this.xCoord + 0.5D;
		double sy = this.yCoord + 1.0D;
		double sz = this.zCoord + 0.5D;
		
		double dx = sx - highlight.posX;
		double dy = sy - highlight.posY - highlight.getEyeHeight();
		double dz = sz - highlight.posZ;
		
		for (int i = 0; i < 5; i++)
		{
			TwilightForestMod.proxy.spawnParticle(this.worldObj, "ghasttrap", sx, sy, sz, -dx, -dy, -dz);
		}

	}

	public boolean isCharged()
	{
		return dyingGhasts.size() >= 3;
	}

}
