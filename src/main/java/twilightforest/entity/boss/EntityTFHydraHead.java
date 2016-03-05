package twilightforest.entity.boss;

import net.minecraft.world.World;

public class EntityTFHydraHead extends EntityTFHydraPart {
	
    public EntityTFHydraHead(World world)
    {
    	super(world);
		//texture = TwilightForestMod.MODEL_DIR + "hydra4.png";
		
		// the necks draw with the head, so we just draw the head at all times, sorry
		this.ignoreFrustumCheck = true;
    }

	public EntityTFHydraHead(EntityTFHydra hydra, String s, float f, float f1) {
		super(hydra, s, f, f1);
	}
	
	
	/**
	 * Rather than speed, this seems to control how far up or down the heads can tilt?
	 */
	@Override
    public int getVerticalFaceSpeed()
    {
        return 500;
    }
	
	/**
	 * We have our own custom death here.  In any case, we don't actually ever die, despawn and spew out XP orbs, so don't do that 
	 */
	@Override
	protected void onDeathUpdate() {
        ++this.deathTime;
	}

	@Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(18, Byte.valueOf((byte)0));
        dataWatcher.addObject(19, Byte.valueOf((byte)0));
    }
	
	
    public float getMouthOpen()
    {
        return (dataWatcher.getWatchableObjectByte(18) & 0xFF) / 255.0F;
    }

    public int getState()
    {
        return (dataWatcher.getWatchableObjectByte(19) & 0xFF);
    }

    public void setMouthOpen(float openness)
    {
    	// bounds
    	if (openness < 0.0F)
    	{
    		openness = 0.0F;
    	}
    	if (openness > 1.0F)
    	{
    		openness = 1.0F;
    	}
    	
    	int openByte = Math.round(openness * 255);
    	
    	openByte &= 0xFF;
        dataWatcher.updateObject(18, Byte.valueOf((byte)openByte));
    }

    public void setState(int state)
    {
    	state &= 0xFF;
        dataWatcher.updateObject(19, Byte.valueOf((byte)state));
    }

}
