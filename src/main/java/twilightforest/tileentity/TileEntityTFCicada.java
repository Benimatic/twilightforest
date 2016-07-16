package twilightforest.tileentity;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import twilightforest.TFSounds;
import twilightforest.TwilightForestMod;


public class TileEntityTFCicada extends TileEntityTFCritter {
    private int yawDelay;
    public int currentYaw;
    private int desiredYaw;
    
    private int singDuration;
    private boolean singing;
    private int singDelay;
    
    @Override
	public void update()
    {
    	super.update();
    	
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

    private void doSingAnimation()
    {
    	if (worldObj.rand.nextInt(5) == 0)
    	{
    		double rx = pos.getX() + worldObj.rand.nextFloat();
    		double ry = pos.getY() + worldObj.rand.nextFloat();
    		double rz = pos.getZ() + worldObj.rand.nextFloat();
    		worldObj.spawnParticle(EnumParticleTypes.NOTE, rx, ry, rz, 0.0D, 0.0D, 0.0D);
    	}
    }
    
    private void playSong()
    {
    	if (!TwilightForestMod.silentCicadas)
    	{
    		worldObj.playSound(pos.getX(), pos.getY(), pos.getZ(), TFSounds.CICADA, SoundCategory.NEUTRAL, 1.0f, (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F + 1.0F, false);
    	}
    }
}
