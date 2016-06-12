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
        return worldObj.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 50D, false) != null;
    }
    
	/**
	 * Any post-creation initialization goes here
	 */
	protected void initializeCreature(EntityLiving myCreature) {
		
		if (myCreature instanceof EntityCreature)
		{
			((EntityCreature) myCreature).setHomePosAndDistance(pos, 46);
		}
	}

	/**
	 * Range?
	 */
	protected int getRange() {
		return 50;
	}
}
