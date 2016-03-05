package twilightforest.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import twilightforest.entity.passive.EntityTFTinyBird;

public class EntityAITFBirdFly extends EntityAIBase {

    private EntityTFTinyBird entity;

    public EntityAITFBirdFly(EntityTFTinyBird par1EntityCreature)
    {
        this.entity = par1EntityCreature;
        this.setMutexBits(5);
    }
	
    /**
     * Returns whether the EntityAIBase should begin execution.
     */
	@Override
	public boolean shouldExecute() {
       return !entity.isBirdLanded();
	}
	
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
	public boolean continueExecuting()
    {
        return !entity.isBirdLanded();
    }

}
