package twilightforest.tileentity;

import net.minecraft.tileentity.TileEntity;
import twilightforest.TwilightForestMod;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityTFSmoker extends TileEntity {
	
	public long counter = 0;
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
    	if (++counter % 4 == 0) {
    		TwilightForestMod.proxy.spawnParticle(this.worldObj, "hugesmoke", this.xCoord + 0.5, this.yCoord + 0.95, this.zCoord + 0.5, 
    				org.bogdang.modifications.math.MathHelperLite.cos(counter / 10.0) * 0.05, 0.25D, org.bogdang.modifications.math.MathHelperLite.sin(counter / 10.0) * 0.05);
    	}
    }
}
