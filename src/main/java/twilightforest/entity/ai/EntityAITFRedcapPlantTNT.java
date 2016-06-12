package twilightforest.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import twilightforest.entity.EntityTFRedcap;

public class EntityAITFRedcapPlantTNT extends EntityAITFRedcapBase {

	public EntityAITFRedcapPlantTNT(EntityTFRedcap entityTFRedcap) 
	{
		this.entityObj = entityTFRedcap;
	}
	
	@Override
	public boolean shouldExecute() {
		EntityLivingBase attackTarget = this.entityObj.getAttackTarget();
        
        if (attackTarget != null && entityObj.getTntLeft() > 0 && entityObj.getDistanceSqToEntity(attackTarget) < 25 && !isTargetLookingAtMe(attackTarget) 
        		&& !isLitTNTNearby(8) && findBlockTNTNearby(5) == null)
        {
        	//System.out.println("Redcap can plant TNT");
        	return true;
        }
        else
        {
    		return false;
        }
	}
    
    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
	public void startExecuting()
    {
        int entityPosX = MathHelper.floor_double(this.entityObj.posX);
        int entityPosY = MathHelper.floor_double(this.entityObj.posY);
        int entityPosZ = MathHelper.floor_double(this.entityObj.posZ);
        
        //System.out.println("Redcap trying to plant TNT");
        
    	this.entityObj.setCurrentItemOrArmor(0, EntityTFRedcap.heldTNT);

    	if (this.entityObj.worldObj.isAirBlock(entityPosX, entityPosY, entityPosZ))
    	{
    		entityObj.setTntLeft(entityObj.getTntLeft() - 1);
    		entityObj.playLivingSound();
    		entityObj.worldObj.setBlock(entityPosX, entityPosY, entityPosZ, Blocks.TNT, 0, 3);
    	}
    }
    

    /**
     * Resets the task
     */
    @Override
	public void resetTask()
    {
    	this.entityObj.setCurrentItemOrArmor(0, entityObj.getPick());
    }
}
