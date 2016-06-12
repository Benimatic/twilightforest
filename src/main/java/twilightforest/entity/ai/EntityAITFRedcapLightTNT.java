package twilightforest.entity.ai;

import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import twilightforest.entity.EntityTFRedcap;

public class EntityAITFRedcapLightTNT extends EntityAITFRedcapBase {

	
	private float pursueSpeed;
	private int delayTemptCounter;
	private int tntX;
	private int tntY;
	private int tntZ;

	public EntityAITFRedcapLightTNT(EntityTFRedcap hostEntity, float speed) {
		this.entityObj = hostEntity;
		this.pursueSpeed = speed;
        this.setMutexBits(3);
	}
	
	/**
	 * Is there an unlit TNT block nearby?
	 */
	@Override
	public boolean shouldExecute() {

		ChunkCoordinates nearbyTNT = this.findBlockTNTNearby(8);
		
        if (this.delayTemptCounter > 0)
        {
            --this.delayTemptCounter;
            return false;
        }
		
		if (nearbyTNT != null)
		{
			this.tntX = nearbyTNT.posX;
			this.tntY = nearbyTNT.posY;
			this.tntZ = nearbyTNT.posZ;
			
			return true;
		}
		
		return false;
	}

	
	 
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
	public boolean continueExecuting()
    {
        return entityObj.worldObj.getBlock(tntX, tntY, tntZ) == Blocks.TNT;
    }
    
    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
	public void startExecuting()
    {
    	this.entityObj.setCurrentItemOrArmor(0, EntityTFRedcap.heldFlint);
    }

    /**
     * Resets the task
     */
    @Override
	public void resetTask()
    {
        this.entityObj.getNavigator().clearPathEntity();
    	this.entityObj.setCurrentItemOrArmor(0, entityObj.getPick());
        this.delayTemptCounter = 20;
    }

    /**
     * Updates the task
     */
    @Override
	public void updateTask()
    {
        this.entityObj.getLookHelper().setLookPosition(tntX, tntY, tntZ, 30.0F, this.entityObj.getVerticalFaceSpeed());

        if (this.entityObj.getDistance(tntX, tntY, tntZ) < 2.4D)
        {
        	// light it!
        	entityObj.playLivingSound();
        	
        	Blocks.TNT.onBlockDestroyedByPlayer(entityObj.worldObj, tntX, tntY, tntZ, 1);
        	entityObj.worldObj.setBlock(tntX, tntY, tntZ, Blocks.AIR, 0, 2);
            this.entityObj.getNavigator().clearPathEntity();
        }
        else
        {
            this.entityObj.getNavigator().tryMoveToXYZ(tntX, tntY, tntZ, this.pursueSpeed);
        }
    }

}
