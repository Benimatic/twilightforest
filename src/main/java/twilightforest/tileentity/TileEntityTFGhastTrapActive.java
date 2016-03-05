package twilightforest.tileentity;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import twilightforest.TwilightForestMod;
import twilightforest.block.BlockTFTowerDevice;
import twilightforest.block.TFBlocks;
import twilightforest.entity.EntityTFTowerGhast;
import twilightforest.entity.boss.EntityTFUrGhast;

public class TileEntityTFGhastTrapActive extends TileEntity {
	
	public int counter = 0;
	
	public Random rand = new Random();

	
	public boolean canUpdate()
	{
		return true;
	}
	
    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
	@Override
	public void updateEntity()
	{    	

		++counter;

		if (worldObj.isRemote)
		{
			// smoke when done
			if (counter > 100 && counter % 4 == 0) {
				TwilightForestMod.proxy.spawnParticle(this.worldObj, "hugesmoke", this.xCoord + 0.5, this.yCoord + 0.95, this.zCoord + 0.5, Math.cos(counter / 10.0) * 0.05, 0.25D, Math.sin(counter / 10.0) * 0.05);
			}
			else if (counter < 100) 
			{
				double dx = Math.cos(counter / 10.0) * 2.5;
				double dy = 20D;
				double dz = Math.sin(counter / 10.0) * 2.5;


				TwilightForestMod.proxy.spawnParticle(this.worldObj, "ghasttrap", this.xCoord + 0.5D, this.yCoord + 1.0D, this.zCoord + 0.5D, dx, dy, dz);
				TwilightForestMod.proxy.spawnParticle(this.worldObj, "ghasttrap", this.xCoord + 0.5D, this.yCoord + 1.0D, this.zCoord + 0.5D, -dx, dy, -dz);
				TwilightForestMod.proxy.spawnParticle(this.worldObj, "ghasttrap", this.xCoord + 0.5D, this.yCoord + 1.0D, this.zCoord + 0.5D, -dx, dy / 2, dz);
				TwilightForestMod.proxy.spawnParticle(this.worldObj, "ghasttrap", this.xCoord + 0.5D, this.yCoord + 1.0D, this.zCoord + 0.5D, dx, dy / 2, -dz);
				TwilightForestMod.proxy.spawnParticle(this.worldObj, "ghasttrap", this.xCoord + 0.5D, this.yCoord + 1.0D, this.zCoord + 0.5D, dx / 2, dy / 4, dz / 2);
				TwilightForestMod.proxy.spawnParticle(this.worldObj, "ghasttrap", this.xCoord + 0.5D, this.yCoord + 1.0D, this.zCoord + 0.5D, -dx / 2, dy / 4, -dz / 2);
			}

			// appropriate sound
			if (counter < 30)
			{
				worldObj.playSound(this.xCoord + 0.5D, this.yCoord + 1.5D, this.zCoord + 0.5D, TwilightForestMod.ID + ":mob.urghast.trapwarmup", 1.0F, 4.0F, false);
			}
			else if (counter < 80)
			{
				worldObj.playSound(this.xCoord + 0.5D, this.yCoord + 1.5D, this.zCoord + 0.5D, TwilightForestMod.ID + ":mob.urghast.trapon", 1.0F, 4.0F, false);
			}
			else
			{
				worldObj.playSound(this.xCoord + 0.5D, this.yCoord + 1.5D, this.zCoord + 0.5D, TwilightForestMod.ID + ":mob.urghast.trapspindown", 1.0F, 4.0F, false);
			}
		}

		if (!worldObj.isRemote)
		{
			// trap nearby ghasts
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox((double)this.xCoord, (double)this.yCoord + 16, (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 16 + 1), (double)(this.zCoord + 1)).expand(6D, 16D, 6D);

			List<EntityGhast> nearbyGhasts = worldObj.getEntitiesWithinAABB(EntityGhast.class, aabb);

			for (EntityGhast ghast : nearbyGhasts)
			{
				//stop boss tantrum
				if (ghast instanceof EntityTFUrGhast)
				{
					((EntityTFUrGhast)ghast).stopTantrum();

					// move boss to this point
					ghast.motionX = (ghast.posX - this.xCoord - 0.5) * -0.1;
					ghast.motionY = (ghast.posY - this.yCoord - 2.5) * -0.1;
					ghast.motionZ = (ghast.posZ - this.zCoord - 0.5) * -0.1;

					if (rand.nextInt(10) == 0)
					{
						ghast.attackEntityFrom(DamageSource.generic, 3);
					}

				}
				else
				{
					// move ghasts to this point
					ghast.motionX = (ghast.posX - this.xCoord - 0.5) * -0.1;
					ghast.motionY = (ghast.posY - this.yCoord - 1.5) * -0.1;
					ghast.motionZ = (ghast.posZ - this.zCoord - 0.5) * -0.1;

					if (rand.nextInt(10) == 0)
					{
						ghast.attackEntityFrom(DamageSource.generic, 10);
					}
				}
				
				if (ghast instanceof EntityTFTowerGhast)
				{
					((EntityTFTowerGhast)ghast).setInTrap();
				}

			}
			



			if (counter >= 120)
			{
				// deactivate
				worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, TFBlocks.towerDevice, BlockTFTowerDevice.META_GHASTTRAP_INACTIVE, 3);
				//worldObj.scheduleBlockUpdate(this.xCoord, this.yCoord, this.zCoord, TFBlocks.towerDevice, 4);
			}
		}
	}
}
