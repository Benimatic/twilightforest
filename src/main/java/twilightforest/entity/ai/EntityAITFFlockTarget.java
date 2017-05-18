package twilightforest.entity.ai;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAITFFlockTarget extends EntityAITarget
{
	private EntityLivingBase flockCreature;
	private EntityLivingBase flockTarget;

    public EntityAITFFlockTarget(EntityCreature par1EntityLiving, boolean b)
    {
        super(par1EntityLiving, false);
        this.flockCreature = par1EntityLiving;
        this.setMutexBits(1);
    }

	@Override
	public boolean shouldExecute()
    {
    	List<EntityLivingBase> flockList = this.flockCreature.world.getEntitiesWithinAABB(this.flockCreature.getClass(), this.flockCreature.getEntityBoundingBox().expand(16.0D, 4.0D, 16.0D));
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
			this.flockTarget = targetList.get(this.flockCreature.world.rand.nextInt(targetList.size()));
	        return this.isSuitableTarget(this.flockTarget, true);
    	}
    }

    @Override
	public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.flockTarget);
        super.startExecuting();
    }
}
