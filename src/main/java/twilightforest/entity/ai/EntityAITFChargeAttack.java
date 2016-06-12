package twilightforest.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import twilightforest.entity.ITFCharger;

public class EntityAITFChargeAttack extends EntityAIBase {

	protected static final double MIN_RANGE_SQ = 16.0D;
	protected static final double MAX_RANGE_SQ =  64.0D;
	protected static final int FREQ = 1;
	
	protected EntityCreature charger;
	protected EntityLivingBase chargeTarget;
	protected double chargeX;
	protected double chargeY;
	protected double chargeZ;

	protected float speed;
	
	protected int windup;
	
	protected boolean hasAttacked;

	public EntityAITFChargeAttack(EntityCreature entityLiving, float f) {
		this.charger = entityLiving;
		this.speed = f;
		this.windup = 0;
		this.hasAttacked = false;
		this.setMutexBits(3);
	}

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
	@Override
	public boolean shouldExecute() {
        this.chargeTarget = this.charger.getAttackTarget();

        if (this.chargeTarget == null)
        {
            return false;
        }
        else
        {
            double distance = this.charger.getDistanceSqToEntity(this.chargeTarget);
            if (distance < MIN_RANGE_SQ || distance > MAX_RANGE_SQ) {
            	return false;
            }
            else if (!this.charger.onGround) {
            	return false;
            }
            else {
            	Vec3d chargePos = findChargePoint(charger, chargeTarget, 2.1);
            	boolean canSeeTargetFromDest = chargeTarget.worldObj.rayTraceBlocks(new Vec3d(chargeTarget.posX, chargeTarget.posY + chargeTarget.getEyeHeight(), chargeTarget.posZ), chargePos) == null;
            	if (chargePos == null || !canSeeTargetFromDest) 
            	{
            		return false;
            	}
            	else
            	{
            		chargeX = chargePos.xCoord;
            		chargeY = chargePos.yCoord;
            		chargeZ = chargePos.zCoord;
            		
    	            //System.out.println("Setting charge position to " + chargePos.xCoord + ", "  + chargePos.yCoord + ", " + chargePos.zCoord);

            		return this.charger.getRNG().nextInt(FREQ) == 0;
            	}
            }
            
        }
	}

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
	public void startExecuting()
    {
    	this.windup = 15 + this.charger.getRNG().nextInt(30);
    }


    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
	public boolean continueExecuting()
    {
        return windup > 0 || !this.charger.getNavigator().noPath();
    }
    
    /**
     * Updates the task
     */
    @Override
	public void updateTask()
    {
    	// look where we're going
    	this.charger.getLookHelper().setLookPosition(chargeX, chargeY - 1, chargeZ, 10.0F, this.charger.getVerticalFaceSpeed());
    	
    	if (windup > 0) 
    	{
    		if (--windup == 0) 
    		{
	    		// actually charge!
    			
	    		this.charger.getNavigator().tryMoveToXYZ(chargeX, chargeY, chargeZ, this.speed);
    		}
    		else 
    		{
    			//System.out.println("charging in " + windup);
    			this.charger.limbSwingAmount += 0.8;
    			
    			if (this.charger instanceof ITFCharger)
    			{
    				((ITFCharger)charger).setCharging(true);
    			}
    		}
    	}
    	
    	// attack the target when we get in range
        double var1 = this.charger.width * 2.1F * this.charger.width * 2.1F;

        if (this.charger.getDistanceSq(this.chargeTarget.posX, this.chargeTarget.boundingBox.minY, this.chargeTarget.posZ) <= var1)
        {
            if (!this.hasAttacked)
            {
            	this.hasAttacked = true;
                this.charger.attackEntityAsMob(this.chargeTarget);
            }
        }

    }


    /**
     * Resets the task
     */
    @Override
	public void resetTask()
    {
    	//System.out.println("Resetting charge task, we're done");
    	
        this.windup = 0;
        this.chargeTarget = null;
        this.hasAttacked = false;
        
		if (this.charger instanceof ITFCharger)
		{
			((ITFCharger)charger).setCharging(false);
		}

    }

    
	/**
     * Finds a point that is overshoot blocks "beyond" the target from our position.
     */
    protected Vec3d findChargePoint(Entity attacker, Entity target, double overshoot) {
 
    	// compute angle
        double vecx = target.posX - attacker.posX;
        double vecz = target.posZ - attacker.posZ;
        float rangle = (float)(Math.atan2(vecz, vecx));
        
        double distance = MathHelper.sqrt_double(vecx * vecx + vecz * vecz);
        
        // figure out where we're headed from the target angle
        double dx = MathHelper.cos(rangle) * (distance + overshoot);
        double dz = MathHelper.sin(rangle) * (distance + overshoot);
        
//        System.out.println("Charge position is " + (attacker.posX + dx) + ", " + (attacker.posZ + dz));
//        System.out.println("Target position is " + target.posX + ", " + target.posZ);

        // add that to the target entity's position, and we have our destination
    	return new Vec3d(attacker.posX + dx, target.posY, attacker.posZ + dz);
    }


}
