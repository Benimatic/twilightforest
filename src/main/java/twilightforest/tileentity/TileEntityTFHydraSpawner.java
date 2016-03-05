package twilightforest.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import twilightforest.entity.TFCreatures;


public class TileEntityTFHydraSpawner extends TileEntityTFBossSpawner {

	public TileEntityTFHydraSpawner() {
		this.mobID = TFCreatures.getSpawnerNameFor("Hydra");
	}

	/**
	 * Get a temporary copy of the creature we're going to summon for display purposes
	 */
	public Entity getDisplayEntity()
	{
		if (this.displayCreature == null)
		{
			this.displayCreature = EntityList.createEntityByName("HydraHead", worldObj);
		}
		
		return this.displayCreature;
	}
}
