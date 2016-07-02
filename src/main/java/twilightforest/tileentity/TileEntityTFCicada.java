package twilightforest.tileentity;

import net.minecraft.util.EnumParticleTypes;
import twilightforest.TwilightForestMod;


public class TileEntityTFCicada extends TileEntityTFCritter {

	
    public int yawDelay;
    public int currentYaw;
    public int desiredYaw;
    
    public int singDuration;
    public boolean singing;
    public int singDelay;
    
    @Override
	public void update()
    {
    	super.update();
    	
//    	if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 1) {
//    		//System.out.println("Cicada tile entity block has invalid metadata, fixing");
//    		worldObj.setBlockMetadata(xCoord, yCoord, zCoord, 1);
//    	}
    	
        if(yawDelay > 0)
        {
        	yawDelay--;
        } else {
        	if (currentYaw == 0 && desiredYaw == 0)
        	{
        		// make it rotate!
        		yawDelay = 200 + worldObj.rand.nextInt(200);
        		desiredYaw = worldObj.rand.nextInt(15) - worldObj.rand.nextInt(15);
        	}

        	if (currentYaw < desiredYaw)
        	{
        		currentYaw++;
        	}
        	if (currentYaw > desiredYaw)
        	{
        		currentYaw--;
        	}
        	if (currentYaw == desiredYaw)
        	{
        		desiredYaw = 0;
        	}
        }
        
        if (singDelay > 0)
        {
        	singDelay--;
        } else  {
        	if (singing && singDuration == 0) {
        		playSong();
        	}
        	if (singing && singDuration >= 100)
        	{
        		singing = false;
        		singDuration = 0;
        	}
        	if (singing && singDuration < 100)
        	{
        		singDuration++;
        		doSingAnimation();
        	}
        	if (!singing && singDuration <= 0)
        	{
        		singing = true;
        		singDelay = 100 + worldObj.rand.nextInt(100);
        	}
        }
    }
    
//    /**
//     * There seems to be a bug in the chunk placing routines which sets the tileentity metadta to 0 even if the tile shifts metadatas when placed.
//     * Thus, if the metadata is 0, re-check it
//     */
//    @Override
//    public int getBlockMetadata() {
//    	if (super.getBlockMetadata() == 0)
//        {
//    		this.blockMetadata = -1;
//        }
//    	return super.getBlockMetadata();
//    }

    
    public void doSingAnimation()
    {
    	if (worldObj.rand.nextInt(5) == 0)
    	{
    		double rx = pos.getX() + worldObj.rand.nextFloat();
    		double ry = pos.getY() + worldObj.rand.nextFloat();
    		double rz = pos.getZ() + worldObj.rand.nextFloat();
    		worldObj.spawnParticle(EnumParticleTypes.NOTE, rx, ry, rz, 0.0D, 0.0D, 0.0D);
    	}
    }
    
    public void playSong()
    {
    	if (!TwilightForestMod.silentCicadas)
    	{
    		worldObj.playSoundEffect(xCoord, yCoord, zCoord, TwilightForestMod.ID + ":mob.cicada", 1.0f, (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F + 1.0F);
    	}
    }
}
