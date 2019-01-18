package twilightforest.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import twilightforest.entity.TFCreatures;
import net.minecraft.util.AxisAlignedBB;


public class TileEntityTFHydraSpawner extends TileEntityTFBossSpawner {

	public TileEntityTFHydraSpawner() {
		this.mobID = TFCreatures.getSpawnerNameFor("Hydra");
	}
    protected AxisAlignedBB aabb;

    @Override
    public void validate() {
    	aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
    	return aabb;
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
