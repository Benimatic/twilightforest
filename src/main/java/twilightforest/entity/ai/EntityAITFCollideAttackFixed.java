package twilightforest.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * This is a copy of EntityAIAttackOnCollide modified to change a few hardcoded behaviors
 * 
 * @author Mojang
 *
 */
public class EntityAITFCollideAttackFixed extends EntityAIBase
{
    World worldObj;
    EntityCreature attacker;
    /**
     * An amount of decrementing ticks that allows the entity to attack once the tick reaches 0.
     */
    int attackTick;
    /** The speed with which the mob will approach the target */
    double speedTowardsTarget;
    /**
     * When true, the mob will continue chasing its target, even if it can't find a path to them right now.
     */
    boolean longMemory;
    /** The PathEntity of our entity. */
    PathEntity entityPathEntity;
	Class<? extends EntityLivingBase> classTarget;
    private int delayTicks;
    private double pathX;
    private double pathY;
    private double pathZ;
    private int failedPathFindingPenalty;

	public EntityAITFCollideAttackFixed(EntityCreature par1EntityCreature, Class<? extends EntityLivingBase> par2Class, double par3, boolean par5)
    {
        this(par1EntityCreature, par3, par5);
        this.classTarget = par2Class;
    }

    public EntityAITFCollideAttackFixed(EntityCreature par1EntityCreature, double par2, boolean par4)
    {
        this.attacker = par1EntityCreature;
        this.worldObj = par1EntityCreature.worldObj;
        this.speedTowardsTarget = par2;
        this.longMemory = par4;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
	public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else if (!entitylivingbase.isEntityAlive())
        {
            return false;
        }
        else if (this.classTarget != null && !this.classTarget.isAssignableFrom(entitylivingbase.getClass()))
        {
            return false;
        }
        else
        {
            if (-- this.delayTicks <= 0)
            {
                this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
               this.delayTicks = 4 + this.attacker.getRNG().nextInt(7);
                return this.entityPathEntity != null;
            }
            else
            {
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        return entitylivingbase == null ? false : (!entitylivingbase.isEntityAlive() ? false : (!this.longMemory ? !this.attacker.getNavigator().noPath() : this.attacker.isWithinHomeDistance(MathHelper.floor_double(entitylivingbase.posX), MathHelper.floor_double(entitylivingbase.posY), MathHelper.floor_double(entitylivingbase.posZ))));
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        this.delayTicks = 0;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.attacker.getNavigator().clearPathEntity();
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
        
        double distanceToAttacker = this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.boundingBox.minY, entitylivingbase.posZ);
        double attackRange = (double)(this.attacker.width * 2.0F * this.attacker.width * 2.0F + entitylivingbase.width);
        
        --this.delayTicks;

        if ((this.longMemory || this.attacker.getEntitySenses().canSee(entitylivingbase)) && this.delayTicks <= 0 && (this.pathX == 0.0D && this.pathY == 0.0D && this.pathZ == 0.0D || entitylivingbase.getDistanceSq(this.pathX, this.pathY, this.pathZ) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F))
        {
            this.pathX = entitylivingbase.posX;
            this.pathY = entitylivingbase.boundingBox.minY;
            this.pathZ = entitylivingbase.posZ;
            this.delayTicks = failedPathFindingPenalty + 4 + this.attacker.getRNG().nextInt(7);

            if (this.attacker.getNavigator().getPath() != null)
            {
                PathPoint finalPathPoint = this.attacker.getNavigator().getPath().getFinalPathPoint();
                if (finalPathPoint != null && entitylivingbase.getDistanceSq(finalPathPoint.xCoord, finalPathPoint.yCoord, finalPathPoint.zCoord) < 1)
                {
                    failedPathFindingPenalty = 0;
                }
                else
                {
                    failedPathFindingPenalty += 10;
                }
            }
            else
            {
                failedPathFindingPenalty += 10;
            }

            if (distanceToAttacker > 1024.0D)
            {
                this.delayTicks += 10;
            }
            else if (distanceToAttacker > 256.0D)
            {
                this.delayTicks += 5;
            }

            if (!this.attacker.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget))
            {
                this.delayTicks += 15;
            }
        }

        this.attackTick = Math.max(this.attackTick - 1, 0);

        // fix bug removing any delay from attacks
        if (distanceToAttacker <= attackRange && this.attackTick <= 0)
        {
        	// double attack timeout
            this.attackTick = 20;

            if (this.attacker.getHeldItem() != null)
            {
                this.attacker.swingItem();
            }

            this.attacker.attackEntityAsMob(entitylivingbase);
        }
    }
}