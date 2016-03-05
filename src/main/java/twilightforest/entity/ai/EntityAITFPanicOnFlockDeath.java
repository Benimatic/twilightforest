package twilightforest.entity.ai;

import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;
import twilightforest.entity.EntityTFKobold;

public class EntityAITFPanicOnFlockDeath extends EntityAIBase
{
    private EntityCreature flockCreature;
    private float speed;
    private double fleeX;
    private double fleeY;
    private double fleeZ;
    
    int fleeTimer;

    public EntityAITFPanicOnFlockDeath(EntityCreature par1EntityCreature, float par2)
    {
        this.flockCreature = par1EntityCreature;
        this.speed = par2;
        this.setMutexBits(1);
        this.fleeTimer = 0;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @SuppressWarnings("unchecked")
	@Override
	public boolean shouldExecute()
    {
    	boolean yikes = fleeTimer > 0;
    	
    	// check if any of us is dead within 4 squares
    	List<EntityLiving> flockList = this.flockCreature.worldObj.getEntitiesWithinAABB(this.flockCreature.getClass(), this.flockCreature.boundingBox.expand(4.0D, 2.0D, 4.0D));
    	for (EntityLiving flocker : flockList)
    	{
    		if (flocker.deathTime > 0) {
    			yikes = true;
    			break;
    		}
    	}
    	
        if (!yikes)
        {
            return false;
        }
        else
        {
            Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.flockCreature, 5, 4);

            if (var1 == null)
            {
                return false;
            }
            else
            {
                this.fleeX = var1.xCoord;
                this.fleeY = var1.yCoord;
                this.fleeZ = var1.zCoord;
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
    	this.fleeTimer = 40;
        this.flockCreature.getNavigator().tryMoveToXYZ(this.fleeX, this.fleeY, this.fleeZ, this.speed);
        
    	// panic flag for kobold animations
    	if (flockCreature instanceof EntityTFKobold)
    	{
    		((EntityTFKobold)flockCreature).setPanicked(true);
    	}
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
	public boolean continueExecuting()
    {
        return fleeTimer > 0 && !this.flockCreature.getNavigator().noPath();
    }

    /**
     * Updates the task
     */
	@Override
	public void updateTask() {
		fleeTimer--;
	}

    /**
     * Resets the task
     */
    @Override
	public void resetTask() {
    	fleeTimer -= 20;
    	
    	// panic flag for kobold animations
    	if (flockCreature instanceof EntityTFKobold)
    	{
    		((EntityTFKobold)flockCreature).setPanicked(false);
    	}
    }
    
    
}
