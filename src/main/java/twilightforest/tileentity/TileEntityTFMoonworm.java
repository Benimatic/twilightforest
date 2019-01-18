package twilightforest.tileentity;

import net.minecraft.util.AxisAlignedBB;


public class TileEntityTFMoonworm extends TileEntityTFCritter {

	
    public int yawDelay;
    public int currentYaw;
    public int desiredYaw;

	public TileEntityTFMoonworm() {
		super();
		
		currentYaw = -1;
		yawDelay = 0;
		desiredYaw = 0;
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
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    @Override
	public void updateEntity()
    {
    	super.updateEntity();
    	
    	if (currentYaw == -1)
    	{
    		currentYaw = worldObj.rand.nextInt(4) * 90;
    	}
    	
        if(yawDelay > 0)
        {
        	yawDelay--;
        } 
        else 
        {
        	if (desiredYaw == 0)
        	{
        		// make it rotate!
        		yawDelay = 200 + worldObj.rand.nextInt(200);
        		desiredYaw = worldObj.rand.nextInt(4) * 90;
        	}
        	
        	currentYaw++;
        	
        	if (currentYaw > 360)
        	{
        		currentYaw = 0;
        	}

        	if (currentYaw == desiredYaw)
        	{
        		desiredYaw = 0;
        	}
        }
     }
  
}
