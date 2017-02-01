package twilightforest.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
        BlockPos entityPos = new BlockPos(entityObj);
        
        //System.out.println("Redcap trying to plant TNT");
        
    	this.entityObj.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, EntityTFRedcap.heldTNT);

    	if (this.entityObj.world.isAirBlock(entityPos))
    	{
    		entityObj.setTntLeft(entityObj.getTntLeft() - 1);
    		entityObj.playLivingSound();
    		entityObj.world.setBlockState(entityPos, Blocks.TNT.getDefaultState(), 3);
    	}
    }
    
    @Override
	public void resetTask()
    {
    	this.entityObj.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, entityObj.getPick());
    }
}
