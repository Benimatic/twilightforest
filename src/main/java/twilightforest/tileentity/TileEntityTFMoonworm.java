package twilightforest.tileentity;



public class TileEntityTFMoonworm extends TileEntityTFCritter {

	
    public int yawDelay;
    public int currentYaw;
    public int desiredYaw;

	public TileEntityTFMoonworm() {
		currentYaw = -1;
		yawDelay = 0;
		desiredYaw = 0;
	}
    
    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    @Override
	public void update()
    {
    	super.update();
    	
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
