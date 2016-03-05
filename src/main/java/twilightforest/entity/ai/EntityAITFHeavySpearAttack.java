package twilightforest.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import twilightforest.entity.EntityTFGoblinKnightUpper;

public class EntityAITFHeavySpearAttack extends EntityAIBase {

    private EntityTFGoblinKnightUpper entity;

    public EntityAITFHeavySpearAttack(EntityTFGoblinKnightUpper par1EntityCreature)
    {
        this.entity = par1EntityCreature;
        this.setMutexBits(7);
    }
	
    /**
     * Returns whether the EntityAIBase should begin execution.
     */
	@Override
	public boolean shouldExecute() {
       return entity.heavySpearTimer > 0 && entity.heavySpearTimer < 50;
	}
	
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
	public boolean continueExecuting()
    {
        return entity.heavySpearTimer > 0 && entity.heavySpearTimer < 50;
    }

}
