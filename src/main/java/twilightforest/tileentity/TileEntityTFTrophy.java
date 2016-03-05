package twilightforest.tileentity;

import net.minecraft.tileentity.TileEntitySkull;

public class TileEntityTFTrophy extends TileEntitySkull 
{
	
	public int ticksExisted;
	
	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
	 * ticks and creates a new spawn inside its implementation.
	 */
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		this.ticksExisted++;
	}

}
