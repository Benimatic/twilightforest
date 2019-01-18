package twilightforest.entity.ai;

import java.util.List;

import twilightforest.entity.boss.EntityTFSnowQueen;
import twilightforest.entity.boss.EntityTFSnowQueen.Phase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class EntityAITFHoverBeam extends EntityAIBase {

    private static final float HOVER_HEIGHT = 3F;
	private static final float HOVER_RADIUS = 4F;
	private Class<? extends EntityLivingBase> classTarget;
	private EntityTFSnowQueen attacker;
	
    private double hoverPosX;
    private double hoverPosY;
    private double hoverPosZ;
	private int hoverTimer;
	private int beamTimer;
	private int maxHoverTime;
	private int maxBeamTime;
	private int seekTimer;
	private int maxSeekTime;
	private double beamY;
	private boolean isInPosition;
	
	public EntityAITFHoverBeam(EntityTFSnowQueen entityTFSnowQueen, Class<EntityPlayer> class1, int hoverTime, int dropTime) {
		this.attacker = entityTFSnowQueen;
		this.classTarget = class1;
        this.setMutexBits(3);
        this.maxHoverTime = hoverTime;
        this.maxSeekTime = hoverTime;
        this.maxBeamTime = dropTime;

		this.hoverTimer = 0;
		this.isInPosition = false;
	}

	/**
     * Returns whether the EntityAIBase should begin execution.
     */
	@Override
	public boolean shouldExecute() {
        EntityLivingBase target = this.attacker.getAttackTarget();

        if (target == null) {
            return false;
        } else if (!target.isEntityAlive()) {
            return false;
        } else if (this.classTarget != null && !this.classTarget.isAssignableFrom(target.getClass())) {
            return false;
        } else if (this.attacker.getCurrentPhase() != Phase.BEAM) {
    		return false;
    	} else {
    		//System.out.println("We can hover at target!");

            return true;//attacker.canEntityBeSeen(target);
        }

	}

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
	@Override
	public boolean continueExecuting() {
		EntityLivingBase target = this.attacker.getAttackTarget();

		if (target == null || !target.isEntityAlive()) {
			return false;
		} else if (this.attacker.getCurrentPhase() != Phase.BEAM) {
    		return false;
    	}  else if (this.seekTimer >= this.maxSeekTime) {
			return false;
		} else if (this.beamTimer >= this.maxBeamTime) {
			return false;
		} else {
			return true;
		}
	}

    /**
     * Execute a one shot task or start executing a continuous task
     */
	@Override
	public void startExecuting() {
        EntityLivingBase target = this.attacker.getAttackTarget();

        if (target != null) {
        	// find a spot above the player 
        	makeNewHoverSpot(target);
        }
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		this.seekTimer = 0;
		this.hoverTimer = 0;
		this.beamTimer = 0;
		this.isInPosition = false;
		
		this.attacker.setBreathing(false);
	}

    /**
     * Updates the task
     */
	@Override
	public void updateTask() {
		
		// check if we're in position
		if (this.attacker.getDistanceSq(hoverPosX, hoverPosY, hoverPosZ) <= 1.0F) {
			this.isInPosition = true;
		}
		
		// update timers
		if (this.isInPosition) {
			this.hoverTimer++;
		} else {
			this.seekTimer++;
		}
		
		// drop once the hover timer runs out
		if (this.hoverTimer >= this.maxHoverTime) {
			this.beamTimer++;
			this.attacker.setBreathing(true);
			
			// anyhoo, deal damage
	        Entity target = getHeadLookTarget();
	        if (target != null) {
				attacker.doBreathAttack(target);
	        }
	        
	        // descend
			this.hoverPosY -= 0.05F;
			
			if (this.hoverPosY < this.beamY) {
				this.hoverPosY = this.beamY;
			}
		}
			
		// check if we are at our waypoint target
		double offsetX = this.hoverPosX - this.attacker.posX;
		double offsetY = this.hoverPosY - this.attacker.posY;
		double offsetZ = this.hoverPosZ - this.attacker.posZ;

		double distanceDesired = offsetX * offsetX + offsetY * offsetY + offsetZ * offsetZ;

		distanceDesired = (double)MathHelper.sqrt_double(distanceDesired);

		if (distanceDesired > 0.5) {

			// add velocity
			double velX = offsetX / distanceDesired * 0.05D;
			double velY = offsetY / distanceDesired * 0.1D;
			double velZ = offsetZ / distanceDesired * 0.05D;

			// gravity offset
			velY += 0.02F;


			this.attacker.addVelocity(velX, velY, velZ);
			//System.out.println("Just set hover velocity, velY = " + velY + " result y = " + this.attacker.motionY);

		}


		// look at target
		EntityLivingBase target = this.attacker.getAttackTarget();
		if (target != null) {
			float tracking = this.isInPosition ? 1F: 20.0F;
			
			this.attacker.faceEntity(target, tracking, tracking);
			this.attacker.getLookHelper().setLookPositionWithEntity(target, tracking, tracking);
		}

		//System.out.println("Hovering!");
	}
	
	/**
	 * What, if anything, is the head currently looking at?
	 */
	@SuppressWarnings("unchecked")
	private Entity getHeadLookTarget() {
		Entity pointedEntity = null;
		double range = 30.0D;
        Vec3 srcVec = Vec3.createVectorHelper(this.attacker.posX, this.attacker.posY + 0.25, this.attacker.posZ);
        Vec3 lookVec = this.attacker.getLook(1.0F);
        Vec3 destVec = srcVec.addVector(lookVec.xCoord * range, lookVec.yCoord * range, lookVec.zCoord * range);
        float var9 = 3.0F;
        List<Entity> possibleList = this.attacker.worldObj.getEntitiesWithinAABBExcludingEntity(this.attacker, this.attacker.boundingBox.addCoord(lookVec.xCoord * range, lookVec.yCoord * range, lookVec.zCoord * range).expand(var9, var9, var9));
        double hitDist = 0;

        for (Entity possibleEntity : possibleList)
        {
            if (possibleEntity.canBeCollidedWith() && possibleEntity != this.attacker)
            {
                float borderSize = possibleEntity.getCollisionBorderSize();
                AxisAlignedBB collisionBB = possibleEntity.boundingBox.expand((double)borderSize, (double)borderSize, (double)borderSize);
                MovingObjectPosition interceptPos = collisionBB.calculateIntercept(srcVec, destVec);

                if (collisionBB.isVecInside(srcVec))
                {
                    if (0.0D < hitDist || hitDist == 0.0D)
                    {
                        pointedEntity = possibleEntity;
                        hitDist = 0.0D;
                    }
                }
                else if (interceptPos != null)
                {
                    double possibleDist = srcVec.distanceTo(interceptPos.hitVec);

                    if (possibleDist < hitDist || hitDist == 0.0D)
                    {
                        pointedEntity = possibleEntity;
                        hitDist = possibleDist;
                    }
                }
            }
        }
		return pointedEntity;
	}


	/**
	 * Make a new spot to hover at!
	 */
	private void makeNewHoverSpot(EntityLivingBase target) {
		double hx = 0, hy = 0, hz = 0;
		
		int tries = 100;
		
		for (int i = 0; i < tries; i++) {
			hx = target.posX + (this.attacker.getRNG().nextFloat() - this.attacker.getRNG().nextFloat()) * HOVER_RADIUS;
			hy = target.posY + HOVER_HEIGHT;
			hz = target.posZ + (this.attacker.getRNG().nextFloat() - this.attacker.getRNG().nextFloat()) * HOVER_RADIUS;
			
			if (!isPositionOccupied(hx, hy, hz) && this.canEntitySee(this.attacker, hx, hy, hz) && this.canEntitySee(target, hx, hy, hz)) {
				break;
			}
		}
		
        /*if (tries == 99) {
            //System.out.println("Found no spots, giving up");
        }*/
		
		this.hoverPosX = hx;
		this.hoverPosY = hy;
		this.hoverPosZ = hz;
		
		this.beamY = target.posY;
		
		// reset seek timer
		this.seekTimer = 0;
	}

	private boolean isPositionOccupied(double hx, double hy, double hz) {
		float radius = this.attacker.width / 2F;
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(hx - radius, hy, hz - radius, hx + radius, hy + this.attacker.height, hz + radius);
		
		boolean isOccupied = this.attacker.worldObj.getCollidingBoundingBoxes(attacker, aabb).isEmpty();
		
		return isOccupied;
	}
	
	/**
     * Can the specified entity see the specified location?
     */
    protected boolean canEntitySee(Entity entity, double dx, double dy, double dz) {
        return entity.worldObj.rayTraceBlocks(Vec3.createVectorHelper(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ), Vec3.createVectorHelper(dx, dy, dz)) == null;

    }
}
