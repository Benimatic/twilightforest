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
	
	@Override
	public boolean shouldExecute() {
       return entity.heavySpearTimer > 0 && entity.heavySpearTimer < 50;
	}
	
    @Override
	public boolean shouldContinueExecuting()
    {
        return entity.heavySpearTimer > 0 && entity.heavySpearTimer < 50;
    }

}
