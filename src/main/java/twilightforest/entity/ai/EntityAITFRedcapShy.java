package twilightforest.entity.ai;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import twilightforest.entity.EntityTFRedcap;

public class EntityAITFRedcapShy extends EntityAITFRedcapBase {

	EntityLivingBase entityTarget;
	float speed;
    boolean lefty;
    double xPosition;
    double yPosition;
    double zPosition;
    
    double minDistance = 3.0;
    double maxDistance = 6.0;

	public EntityAITFRedcapShy(EntityTFRedcap entityTFRedcap, float moveSpeed) {
		this.entityObj = entityTFRedcap;
		this.speed = moveSpeed;
		this.lefty = (new org.bogdang.modifications.random.XSTR()).nextBoolean(); 
        this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		EntityLivingBase attackTarget = this.entityObj.getAttackTarget();
        
        if (attackTarget == null || !this.entityObj.isShy() || attackTarget.getDistanceToEntity(entityObj) > maxDistance 
        		|| attackTarget.getDistanceToEntity(entityObj) < minDistance || !isTargetLookingAtMe(attackTarget)) {
        	return false;
        }
        else
        {
        	this.entityTarget = attackTarget;
        	Vec3 avoidPos = findCirclePoint(entityObj, entityTarget, 5, lefty ? 1 : -1);
        	
            if (avoidPos == null)
            {
                return false;
            }
            else
            {
                this.xPosition = avoidPos.xCoord;
                this.yPosition = avoidPos.yCoord;
                this.zPosition = avoidPos.zCoord;
                return true;
            }
        }

	}
	
    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
	public void startExecuting()
    {
        //System.out.println("avoid ai starting");
        this.entityObj.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }

	
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
	public boolean continueExecuting()
    {
    	EntityLivingBase attackTarget = this.entityObj.getAttackTarget();
        
        if (attackTarget == null) {
        	return false;
        }
        else if (!this.entityTarget.isEntityAlive()) {
        	return false;
        }
        else if (this.entityObj.getNavigator().noPath()) {
        	return false;
        }
        
        boolean shouldContinue = entityObj.isShy() && attackTarget.getDistanceToEntity(entityObj) < maxDistance && attackTarget.getDistanceToEntity(entityObj) > minDistance && isTargetLookingAtMe(attackTarget);
        
        //System.out.println("ai evaluating should continue to " + shouldContinue);
        
        return shouldContinue;
     }
    
    /**
     * Updates the task
     */
    @Override
	public void updateTask()
    {
        this.entityObj.getLookHelper().setLookPositionWithEntity(this.entityTarget, 30.0F, 30.0F);
    }

	
    /**
     * Resets the task
     */
    @Override
	public void resetTask()
    {
        this.entityTarget = null;
        this.entityObj.getNavigator().clearPathEntity();
    }
    
	
	/**
     * Finds a point that allows us to circle the player clockwise.
     */
    protected Vec3 findCirclePoint(Entity circler, Entity toCircle, double radius, double rotation) {
 
    	// compute angle
        float vecx = (float)(circler.posX - toCircle.posX);
        float vecz = (float)(circler.posZ - toCircle.posZ);
        float rangle = org.bogdang.modifications.math.TrigMath2.atan2(vecz, vecx);

        // add a little, so he circles
        rangle += rotation;

        // figure out where we're headed from the target angle
        double dx = MathHelper.cos(rangle) * radius;
        double dz = MathHelper.sin(rangle) * radius;

        // add that to the target entity's position, and we have our destination
    	return Vec3.createVectorHelper(toCircle.posX + dx, circler.boundingBox.minY, toCircle.posZ + dz);
    }
    
}
