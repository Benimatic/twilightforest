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
    	EntityPlayer closestPlayer = worldObj.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 9D, false);
    	
        return closestPlayer != null && closestPlayer.posY > pos.getY() - 4;
    }

	@Override
	protected void spawnMyBoss() {
		// spawn creature
		EntityLiving myCreature = makeMyCreature();

		double rx = pos.getX() + 0.5D;
		double ry = pos.getY() + 0.5D;
		double rz = pos.getZ() + 0.5D;
		myCreature.setLocationAndAngles(rx, ry, rz, worldObj.rand.nextFloat() * 360F, 0.0F);

		// set creature's home to this
		initializeCreature(myCreature);

		// spawn it
		worldObj.spawnEntity(myCreature);
	}
	
}
