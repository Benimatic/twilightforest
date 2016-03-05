package twilightforest.tileentity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import twilightforest.entity.TFCreatures;

public class TileEntityTFSnowQueenSpawner extends TileEntityTFBossSpawner {

	public TileEntityTFSnowQueenSpawner() {
		this.mobID = TFCreatures.getSpawnerNameFor("Snow Queen");
	}
	
    @Override
	public boolean anyPlayerInRange()
    {
    	EntityPlayer closestPlayer = worldObj.getClosestPlayer(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, 9D);
    	
        return closestPlayer != null && closestPlayer.posY > yCoord - 4;
    }

	protected void spawnMyBoss() {
		// spawn creature
		EntityLiving myCreature = makeMyCreature();

		double rx = xCoord + 0.5D;
		double ry = yCoord + 0.5D;
		double rz = zCoord + 0.5D;
		myCreature.setLocationAndAngles(rx, ry, rz, worldObj.rand.nextFloat() * 360F, 0.0F);

		// set creature's home to this
		initializeCreature(myCreature);

		// spawn it
		worldObj.spawnEntityInWorld(myCreature);
	}
	
}
