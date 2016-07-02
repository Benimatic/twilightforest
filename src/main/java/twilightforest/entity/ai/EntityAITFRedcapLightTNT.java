package twilightforest.entity.ai;

import net.minecraft.block.BlockTNT;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.BlockPos;
import twilightforest.entity.EntityTFRedcap;

public class EntityAITFRedcapLightTNT extends EntityAITFRedcapBase {

	
	private float pursueSpeed;
	private int delayTemptCounter;
	private BlockPos tntPos = BlockPos.ORIGIN;

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

		BlockPos nearbyTNT = this.findBlockTNTNearby(8);
		
        if (this.delayTemptCounter > 0)
        {
            --this.delayTemptCounter;
            return false;
        }
		
		if (nearbyTNT != null)
		{
			this.tntPos = nearbyTNT;
			return true;
		}
		
		return false;
	}

    @Override
	public boolean continueExecuting()
    {
        return entityObj.worldObj.getBlockState(tntPos).getBlock() == Blocks.TNT;
    }
    
    @Override
	public void startExecuting()
    {
    	this.entityObj.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, EntityTFRedcap.heldFlint);
    }

    @Override
	public void resetTask()
    {
        this.entityObj.getNavigator().clearPathEntity();
    	this.entityObj.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, entityObj.getPick());
        this.delayTemptCounter = 20;
    }

    @Override
	public void updateTask()
    {
        this.entityObj.getLookHelper().setLookPosition(tntPos.getX(), tntPos.getY(), tntPos.getZ(), 30.0F, this.entityObj.getVerticalFaceSpeed());

        if (this.entityObj.getDistanceSq(tntPos) < 2.4D * 2.4D)
        {
        	// light it!
        	entityObj.playLivingSound();
        	
        	Blocks.TNT.onBlockDestroyedByPlayer(entityObj.worldObj, tntPos, Blocks.TNT.getDefaultState().withProperty(BlockTNT.EXPLODE, true));
        	entityObj.worldObj.setBlockState(tntPos, Blocks.AIR.getDefaultState(), 2);
            this.entityObj.getNavigator().clearPathEntity();
        }
        else
        {
            this.entityObj.getNavigator().tryMoveToXYZ(tntPos.getX(), tntPos.getY(), tntPos.getZ(), this.pursueSpeed);
        }
    }

}
