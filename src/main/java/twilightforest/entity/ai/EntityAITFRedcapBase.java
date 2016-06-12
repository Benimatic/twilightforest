package twilightforest.entity.ai;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.math.MathHelper;
import twilightforest.entity.EntityTFRedcap;

public abstract class EntityAITFRedcapBase extends EntityAIBase
{

	protected EntityTFRedcap entityObj;

	/**
	 * Fairly straightforward.  Returns true in a 120 degree arc in front of the target's view.
	 * @return
	 */
	public boolean isTargetLookingAtMe(EntityLivingBase attackTarget) {
	    	// find angle of approach
	    	double dx = entityObj.posX - attackTarget.posX;
	    	double dz = entityObj.posZ - attackTarget.posZ;
	    	float angle = (float)((Math.atan2(dz, dx) * 180D) / Math.PI) - 90F;
	
	    	float difference = MathHelper.abs((attackTarget.rotationYaw - angle) % 360);
	    	
	//    	System.out.println("Difference in angle of approach is " + difference);
	
	    	return difference < 60 || difference > 300;
	    }

	/**
	 * Check within the specified range to see if any of the blocks nearby are TNT
	 * 
	 * @param range
	 * @return
	 */
	public ChunkCoordinates findBlockTNTNearby(int range) {
	    int entityPosX = MathHelper.floor_double(this.entityObj.posX);
	    int entityPosY = MathHelper.floor_double(this.entityObj.posY);
	    int entityPosZ = MathHelper.floor_double(this.entityObj.posZ);
	    
	    for (int x = -range; x <= range; x++)
	    {
	        for (int y = -range; y <= range; y++)
	        {
	            for (int z = -range; z <= range; z++)
	            {
	            	if (entityObj.worldObj.getBlock(entityPosX + x, entityPosY + y, entityPosZ + z) == Blocks.TNT)
	            	{
	            		return new ChunkCoordinates(entityPosX + x, entityPosY + y, entityPosZ + z);
	            	}
	            }
	        }
	    }
	    
	    return null;
	}

	/**
	 * Check within the specified range to see if any of the blocks nearby are TNT
	 * 
	 * @param range
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isLitTNTNearby(int range) 
	{
		AxisAlignedBB expandedBox = entityObj.boundingBox.expand(range, range, range);
		
	    List<Entity> nearbyTNT = entityObj.worldObj.getEntitiesWithinAABB(EntityTNTPrimed.class, expandedBox);
	    
	    return nearbyTNT.size() > 0;
	}

}
