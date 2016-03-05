package twilightforest.tileentity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import twilightforest.entity.TFCreatures;

public class TileEntityTFNagaSpawner extends TileEntityTFBossSpawner {
	
	public TileEntityTFNagaSpawner() {
		this.mobID = TFCreatures.getSpawnerNameFor("Naga");
	}
	
	public boolean anyPlayerInRange()
    {
        return worldObj.getClosestPlayer(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, 50D) != null;
    }
    
	/**
	 * Any post-creation initialization goes here
	 */
	protected void initializeCreature(EntityLiving myCreature) {
		
		if (myCreature instanceof EntityCreature)
		{
			((EntityCreature) myCreature).setHomeArea(xCoord, yCoord, zCoord, 46);
		}
	}

	/**
	 * Range?
	 */
	protected int getRange() {
		return 50;
	}
}
