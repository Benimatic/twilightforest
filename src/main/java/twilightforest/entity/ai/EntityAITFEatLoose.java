package twilightforest.entity.ai;

import java.util.List;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import twilightforest.entity.passive.EntityTFQuestRam;

/**
 * This is an AI task for the quest ram.  When one of the items it wants comes within a close distance, i
 * 
 * @author Ben
 *
 */
public class EntityAITFEatLoose extends EntityAIBase {
	
    /** The entity using this AI that is tempted by the player. */
    private EntityTFQuestRam temptedQuestRam;
    
    private Item temptID;

	private int delayTemptCounter;

	private EntityItem temptingItem;
	

	public EntityAITFEatLoose(EntityTFQuestRam entityTFQuestRam, Item blockID) {
		this.temptedQuestRam = entityTFQuestRam;
		this.temptID = blockID;
		this.setMutexBits(0);
	}

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @SuppressWarnings("unchecked")
	@Override
	public boolean shouldExecute()
    {
        if (this.delayTemptCounter > 0)
        {
            --this.delayTemptCounter;
            return false;
        }
        else
        {
        	this.temptingItem = null;
        	
            List<EntityItem> nearbyItems = this.temptedQuestRam.worldObj.getEntitiesWithinAABB(EntityItem.class, this.temptedQuestRam.boundingBox.expand(2.0D, 2.0D, 2.0D));
            
            for (EntityItem itemNearby : nearbyItems) {
            	if (itemNearby.getEntityItem().getItem() == temptID && !temptedQuestRam.isColorPresent(itemNearby.getEntityItem().getItemDamage()) && itemNearby.isEntityAlive()) { // is a wool block really "alive"?
            		this.temptingItem = itemNearby;
            		break;
            	}
            }
            
            if (this.temptingItem == null) {
            	return false;
            }
            else {
            	return true;
            }
        }
    }

    
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
	public boolean continueExecuting()
    {
        return this.shouldExecute();
    }
    
    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
	public void startExecuting()
    {
    	;
    }

    /**
     * Resets the task
     */
    @Override
	public void resetTask()
    {
        this.temptingItem = null;
        this.temptedQuestRam.getNavigator().clearPathEntity();
        this.delayTemptCounter = 100;
    }

    /**
     * Updates the task
     */
    @Override
	public void updateTask()
    {
        this.temptedQuestRam.getLookHelper().setLookPositionWithEntity(this.temptingItem, 30.0F, this.temptedQuestRam.getVerticalFaceSpeed());

        if (this.temptedQuestRam.getDistanceSqToEntity(this.temptingItem) < 6.25D)
        {
        	if (!temptedQuestRam.isColorPresent(temptingItem.getEntityItem().getItemDamage())) { // we did technically already check this, but why not check again
                // EAT IT!
            	this.temptingItem.setDead();
            	this.temptedQuestRam.playLivingSound();
            	this.temptedQuestRam.setColorPresent(temptingItem.getEntityItem().getItemDamage());
            	this.temptedQuestRam.animateAddColor(temptingItem.getEntityItem().getItemDamage(), 50); // TODO: find a better place for this?  refactor?
            	//System.out.println("yum");
        	}
        }
    }

}
