package twilightforest.entity.ai;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAITFFlockTarget extends EntityAITarget
{
	EntityLivingBase flockCreature;
	EntityLivingBase flockTarget;

    public EntityAITFFlockTarget(EntityCreature par1EntityLiving, boolean b)
    {
        super(par1EntityLiving, false);
        this.flockCreature = par1EntityLiving;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @SuppressWarnings("unchecked")
	@Override
	public boolean shouldExecute()
    {
    	List<EntityLivingBase> flockList = this.flockCreature.worldObj.getEntitiesWithinAABB(this.flockCreature.getClass(), this.flockCreature.boundingBox.expand(16.0D, 4.0D, 16.0D));
    	List<EntityLivingBase> targetList = new ArrayList<EntityLivingBase>();
    	
    	for (EntityLivingBase flocker : flockList)
    	{
    		if (flocker.getAITarget() != null) {
    			targetList.add(flocker.getAITarget());
    		}
    	}
    	
    	if (targetList.isEmpty()) {
    		return false;
    	}
    	else
    	{
    		// hmm, just pick a random target?
    		EntityLivingBase randomTarget = targetList.get(this.flockCreature.worldObj.rand.nextInt(targetList.size()));
    		
    		System.out.println("randomTarget = " + randomTarget);
    		
	        this.flockTarget = randomTarget;
	        return this.isSuitableTarget(this.flockTarget, true);
    	}
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
	public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.flockTarget);
        super.startExecuting();
    }
}
