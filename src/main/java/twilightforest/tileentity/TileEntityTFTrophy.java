package twilightforest.tileentity;

import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityTFTrophy extends TileEntitySkull 
{
	
	public int ticksExisted;
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
