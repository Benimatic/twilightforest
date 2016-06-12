package twilightforest.entity.boss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import twilightforest.world.ChunkProviderTwilightForest;
import twilightforest.world.TFWorldChunkManager;
import twilightforest.world.WorldProviderTwilightForest;



public class EntityTFHydra extends EntityLiving implements IBossDisplayData, IEntityMultiPart, IMob
{

	private static int TICKS_BEFORE_HEALING = 1000;
	private static int HEAD_RESPAWN_TICKS = 100;
	private static int HEAD_MAX_DAMAGE = 120;
	private static float ARMOR_MULTIPLIER = 8.0F;
	private static int MAX_HEALTH = 360;
	private static float HEADS_ACTIVITY_FACTOR = 0.3F;

	private static int SECONDARY_FLAME_CHANCE = 10;
	private static int SECONDARY_MORTAR_CHANCE = 16;
	
	private static final int DATA_SPAWNHEADS = 17;
	private static final int DATA_BOSSHEALTH = 18;
	
	public Entity partArray[];
	public EntityDragonPart body;
	
	public HydraHeadContainer[] hc;
	public int numHeads = 7;
	
	public EntityDragonPart leftLeg;
	public EntityDragonPart rightLeg;
	public EntityDragonPart tail;

	Entity currentTarget = null;

	public int ticksSinceDamaged = 0;

	public EntityTFHydra(World world) {
		super(world);

		partArray = (new Entity[]
				{
				body = new EntityDragonPart(this, "body", 4F, 4F), leftLeg = new EntityDragonPart(this, "leg", 2F, 3F), rightLeg = new EntityDragonPart(this, "leg", 2F, 3F), tail = new EntityDragonPart(this, "tail", 4F, 4F)
				});
		
		// head array
		this.hc = new HydraHeadContainer[this.numHeads];
		for (int i = 0; i < numHeads; i++)
		{
			hc[i] = new HydraHeadContainer(this, i, i < 3);
		}

		// re-do partArray
		ArrayList<Entity> partList = new ArrayList<Entity>();
		Collections.addAll(partList, partArray);
		
		for (int i = 0; i < numHeads; i++)
		{
			Collections.addAll(partList, hc[i].getNeckArray());
		}
		
		partArray = partList.toArray(partArray);
		
		setSize(16F, 12F);
		this.ignoreFrustumCheck = true;

		//texture = TwilightForestMod.MODEL_DIR + "hydra4.png";
		isImmuneToFire = true;
		
		this.experienceValue = 511;
		
		setSpawnHeads(true);
	}
	
    public EntityTFHydra(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x, y, z);
    }


	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(MAX_HEALTH); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D); // movement speed
    }
	
	
	@SuppressWarnings("unchecked")
	@Override
    public void onLivingUpdate()
    {
		if (hc[0].headEntity == null || hc[1].headEntity == null || hc[2].headEntity == null)
		{
			// don't spawn if we're connected in multiplayer 
			if (shouldSpawnHeads() && !worldObj.isRemote) {
				for (int i = 0; i < numHeads; i++)
				{
					hc[i].headEntity = new EntityTFHydraHead(this, "head" + i, 3F, 3F);
					hc[i].headEntity.setPosition(this.posX, this.posY, this.posZ);
					worldObj.spawnEntityInWorld(hc[i].headEntity);
				}

				setSpawnHeads(false);
				
				//System.out.println("Server is spawning heads");
			}
			else
			{
				// I think the head containers should attempt connection during the update call below
			}
		}
		
		body.onUpdate();
		
		// update all heads (maybe we should change to only active ones
		for (int i = 0; i < numHeads; i++)
		{
			hc[i].onUpdate();
		}		
		
		// update health
        if (!this.worldObj.isRemote)
        {
            this.dataWatcher.updateObject(DATA_BOSSHEALTH, Integer.valueOf((int)this.getHealth()));
        }
        else
        {
//        	if (this.getBossHealth() != this.getHealth())
//        	{
//        		this.setEntityHealth(this.getBossHealth());
//        	}
        	if (this.getHealth() > 0)
        	{
        		// this seems to get set off during creation at some point
        		this.deathTime = 0;
        	}
        }

        if (this.hurtTime > 0)
        {
        	for (int i = 0; i < numHeads; i++)
        	{
        		hc[i].setHurtTime(this.hurtTime);
        	}		
        }
        
        this.ticksSinceDamaged++;
        
        if (!this.worldObj.isRemote && this.ticksSinceDamaged > TICKS_BEFORE_HEALING && this.ticksSinceDamaged % 5 == 0)
        {
        	this.heal(1);
        }
        
        // update fight variables for difficulty setting
        setDifficultyVariables();

        if (this.newPosRotationIncrements > 0)
        {
            double var1 = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
            double var3 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
            double var5 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
            double var7 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw);
            this.rotationYaw = (float)(this.rotationYaw + var7 / this.newPosRotationIncrements);
            this.rotationPitch = (float)(this.rotationPitch + (this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements);
            --this.newPosRotationIncrements;
            this.setPosition(var1, var3, var5);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }

        if (Math.abs(this.motionX) < 0.005D)
        {
            this.motionX = 0.0D;
        }

        if (Math.abs(this.motionY) < 0.005D)
        {
            this.motionY = 0.0D;
        }

        if (Math.abs(this.motionZ) < 0.005D)
        {
            this.motionZ = 0.0D;
        }

        this.worldObj.theProfiler.startSection("ai");

        if (this.isMovementBlocked())
        {
            this.isJumping = false;
            this.moveStrafing = 0.0F;
            this.moveForward = 0.0F;
            this.randomYawVelocity = 0.0F;
        }
        else if (this.isClientWorld())
        {
        	this.worldObj.theProfiler.startSection("oldAi");
        	this.updateEntityActionState();
        	this.worldObj.theProfiler.endSection();
        	this.rotationYawHead = this.rotationYaw;
        }

        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("jump");

        if (this.isJumping)
        {
            if (!this.isInWater() && !this.handleLavaMovement())
            {
                if (this.onGround)
                {
                    this.jump();
                }
            }
            else
            {
                this.motionY += 0.03999999910593033D;
            }
        }

        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("travel");
        this.moveStrafing *= 0.98F;
        this.moveForward *= 0.98F;
        this.randomYawVelocity *= 0.9F;
//        float var9 = this.landMovementFactor;
//        this.landMovementFactor *= this.getSpeedModifier();
        this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
//        this.landMovementFactor = var9;
        this.worldObj.theProfiler.endSection();

        body.width = body.height = 6.0F;
        tail.width = 6.0F; tail.height = 2.0F;

    	// set body part positions
    	float angle;
    	double dx, dy, dz;

    	// body goes behind the actual position of the hydra
    	angle = (((renderYawOffset + 180) * 3.141593F) / 180F);

    	dx = posX - MathHelper.sin(angle) * 3.0;
    	dy = posY + 0.1;
    	dz = posZ + MathHelper.cos(angle) * 3.0;
    	body.setPosition(dx, dy, dz);

    	dx = posX - MathHelper.sin(angle) * 10.5;
    	dy = posY + 0.1;
    	dz = posZ + MathHelper.cos(angle) * 10.5;
    	tail.setPosition(dx, dy, dz);

    	//worldObj.spawnParticle("mobSpell", body.posX, body.posY, body.posZ, 0.2, 0.2, 0.2);

        this.worldObj.theProfiler.startSection("push");

        if (!this.worldObj.isRemote && this.hurtTime == 0)
        {
            this.collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.body.boundingBox), this.body);
            this.collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.tail.boundingBox), this.tail);
        }
        
        this.worldObj.theProfiler.endSection(); 

        // destroy blocks
        if (!this.worldObj.isRemote)
        {
            this.destroyBlocksInAABB(this.body.boundingBox);
            this.destroyBlocksInAABB(this.tail.boundingBox);
            
        	for (int i = 0; i < numHeads; i++)
        	{
        		if (hc[i].headEntity != null && hc[i].isActive())
        		{
        			this.destroyBlocksInAABB(this.hc[i].headEntity.boundingBox);
        		}
        	}
        	
        	// smash blocks beneath us too
        	if (this.ticksExisted % 20 == 0)
        	{
        		//
        		if (isUnsteadySurfaceBeneath())
        		{
        			this.destroyBlocksInAABB(this.boundingBox.offset(0, -1, 0));

        		}
        	}
        }

    }

	
	@Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(DATA_SPAWNHEADS, Byte.valueOf((byte)0));
        this.dataWatcher.addObject(DATA_BOSSHEALTH, new Integer(MAX_HEALTH));
    }
    
	
    public boolean shouldSpawnHeads()
    {
        return dataWatcher.getWatchableObjectByte(DATA_SPAWNHEADS) != 0;
    }

    public void setSpawnHeads(boolean flag)
    {
        if (flag)
        {
            dataWatcher.updateObject(DATA_SPAWNHEADS, Byte.valueOf((byte)127));
        }
        else
        {
            dataWatcher.updateObject(DATA_SPAWNHEADS, Byte.valueOf((byte)0));
        }
    }
    
    /**
     * Save to disk.
     */
    @Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("SpawnHeads", shouldSpawnHeads());
        nbttagcompound.setByte("NumHeads", (byte) countActiveHeads());
    }

    /**
     * Load from disk.  Return state to roughly where we saved it.
     */
    @Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setSpawnHeads(nbttagcompound.getBoolean("SpawnHeads"));
        activateNumberOfHeads(nbttagcompound.getByte("NumHeads"));
    }



	@Override
	protected void updateEntityActionState()
	{
//		System.out.println("Calling updateEntityActionState");
//		System.out.println("Current target = " + currentTarget);
		
		entityAge++;
		despawnEntity();
		moveStrafing = 0.0F;
		moveForward = 0.0F;
		float f = 48F;
		
		// kill heads that have taken too much damage
		for (int i = 0; i < numHeads; i++)
		{
			if (hc[i].isActive() && hc[i].getDamageTaken() > HEAD_MAX_DAMAGE)
			{
				hc[i].setNextState(HydraHeadContainer.STATE_DYING);
				hc[i].endCurrentAction();
				
				// set this head and a random dead head to respawn
				hc[i].setRespawnCounter(HEAD_RESPAWN_TICKS);
				int otherHead = getRandomDeadHead();
				if (otherHead != -1)
				{
					hc[otherHead].setRespawnCounter(HEAD_RESPAWN_TICKS);
				}
			}
		}

		if (rand.nextFloat() < 0.7F)
		{
			EntityPlayer entityplayer1 = worldObj.getClosestVulnerablePlayerToEntity(this, f);
			//EntityPlayer entityplayer1 = worldObj.getClosestPlayerToEntity(this, f);

			if (entityplayer1 != null)
			{
				currentTarget = entityplayer1;
				numTicksToChaseTarget = 100 + rand.nextInt(20);
			}
			else
			{
				randomYawVelocity = (rand.nextFloat() - 0.5F) * 20F;
			}
		}

		if (currentTarget != null)
		{
			faceEntity(currentTarget, 10F, getVerticalFaceSpeed());
			
			// have any heads not currently attacking switch to the primary target
        	for (int i = 0; i < numHeads; i++)
        	{
        		if (!isHeadAttacking(hc[i]) && !hc[i].isSecondaryAttacking)
        		{
        			hc[i].setTargetEntity(currentTarget);
        		}
        	}
	
			// let's pick an attack
			if (this.currentTarget.isEntityAlive())
	        {
	            float distance = this.currentTarget.getDistanceToEntity(this);

	            if (this.canEntityBeSeen(this.currentTarget))
	            {
	                this.attackEntity(this.currentTarget, distance);
	            }
	        }
			
			if (numTicksToChaseTarget-- <= 0 || currentTarget.isDead || currentTarget.getDistanceSqToEntity(this) > (double)(f * f))
			{
				currentTarget = null;
			}
		}
		else
		{
			if (rand.nextFloat() < 0.05F)
			{
				randomYawVelocity = (rand.nextFloat() - 0.5F) * 20F;
			}

			rotationYaw += randomYawVelocity;
			rotationPitch = defaultPitch;
			
			// TODO: while we are idle, consider having the heads breathe fire on passive mobs
			
			// set idle heads to no target
        	for (int i = 0; i < numHeads; i++)
        	{
        		if (hc[i].currentState == HydraHeadContainer.STATE_IDLE)
        		{
        			hc[i].setTargetEntity(null);
        		}
        	}
		}
		
		// heads that are free at this point may consider attacking secondary targets
		this.secondaryAttacks();

		boolean flag = isInWater();
		boolean flag1 = handleLavaMovement();

		if (flag || flag1)
		{
			isJumping = rand.nextFloat() < 0.8F;
		}
	}

	/**
	 * Make some of the mechanics harder on hard mode
	 */
	private void setDifficultyVariables() {
		if (worldObj.difficultySetting != EnumDifficulty.HARD)
		{
			EntityTFHydra.HEADS_ACTIVITY_FACTOR = 0.3F;
		}
		else
		{
			// hard mode!
			EntityTFHydra.HEADS_ACTIVITY_FACTOR = 0.5F;  // higher is harder
		}

	}

	/**
	 * Find a random head to respawn
	 * 
	 * TODO: make random
	 */
	private int getRandomDeadHead() {
		for (int i = 0; i < numHeads; i++)
		{
			if (hc[i].currentState == HydraHeadContainer.STATE_DEAD && hc[i].respawnCounter == -1)
			{
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Used when re-loading from save.  Assumes three heads are active and activates more if necessary. 
	 * 
	 * @param howMany
	 */
	private void activateNumberOfHeads(int howMany)
	{
		int moreHeads = howMany - this.countActiveHeads();
		
		//System.out.println("Reactivating " + moreHeads + " more heads.");
		
		for (int i = 0; i < moreHeads; i++)
		{
			int otherHead = getRandomDeadHead();
			if (otherHead != -1)
			{
				// move directly into not dead
				hc[otherHead].currentState =  HydraHeadContainer.STATE_IDLE;
				hc[otherHead].setNextState(HydraHeadContainer.STATE_IDLE);
				hc[otherHead].endCurrentAction();
			}
		}
	}

	/**
	 * Count timers, and pick an attack against the entity if our timer says go
	 */
	private void attackEntity(Entity target, float distance) {

		int BITE_CHANCE = 10;
		int FLAME_CHANCE = 100;
		int MORTAR_CHANCE = 160;
		
		boolean targetAbove = target.boundingBox.minY > this.boundingBox.maxY;

		// three main heads can do these kinds of attacks
		for (int i = 0; i < 3; i++)
		{
			if (hc[i].currentState == HydraHeadContainer.STATE_IDLE && !areTooManyHeadsAttacking(target, i))
			{
				if (distance > 4 && distance < 10 && rand.nextInt(BITE_CHANCE) == 0 && this.countActiveHeads() > 2 && !areOtherHeadsBiting(target, i))
				{
					hc[i].setNextState(HydraHeadContainer.STATE_BITE_BEGINNING);
				}
				else if (distance > 0 && distance < 20 && rand.nextInt(FLAME_CHANCE) == 0)
				{
					hc[i].setNextState(HydraHeadContainer.STATE_FLAME_BEGINNING);
				}
				else if (distance > 8 && distance < 32 && !targetAbove && rand.nextInt(MORTAR_CHANCE) == 0)
				{
					hc[i].setNextState(HydraHeadContainer.STATE_MORTAR_BEGINNING);
				}
			}
		}

		// heads 4-7 can do everything but bite
		for (int i = 3; i < numHeads; i++)
		{
			if (hc[i].currentState == HydraHeadContainer.STATE_IDLE && !areTooManyHeadsAttacking(target, i))
			{
				if (distance > 0 && distance < 20 && rand.nextInt(FLAME_CHANCE) == 0)
				{
					hc[i].setNextState(HydraHeadContainer.STATE_FLAME_BEGINNING);
				}
				else if (distance > 8 && distance < 32 && !targetAbove && rand.nextInt(MORTAR_CHANCE) == 0)
				{
					hc[i].setNextState(HydraHeadContainer.STATE_MORTAR_BEGINNING);
				}
			}
		}
	}

	protected boolean areTooManyHeadsAttacking(Entity target, int testHead) {
		int otherAttacks = 0;

		for (int i = 0; i < numHeads; i++)
		{
			if (i != testHead && isHeadAttacking(hc[i]))
			{
				otherAttacks++;
				
				// biting heads count triple
				if (isHeadBiting(hc[i]))
				{
					otherAttacks += 2;
				}
			}
		}
		
		return otherAttacks >= 1 + (countActiveHeads() * HEADS_ACTIVITY_FACTOR);
	}
	
	/**
	 * How many active heads do we have?
	 */
	public int countActiveHeads()
	{
		int count = 0;
		
		for (int i = 0; i < numHeads; i++)
		{
			if (hc[i].isActive())
			{
				count++;
			}
		}
		
		return count;
	}

	private boolean isHeadAttacking(HydraHeadContainer head) {
		return head.currentState == HydraHeadContainer.STATE_BITE_BEGINNING || head.currentState == HydraHeadContainer.STATE_BITE_READY 
				|| head.currentState == HydraHeadContainer.STATE_BITE_BITING || head.currentState == HydraHeadContainer.STATE_FLAME_BEGINNING
						|| head.currentState == HydraHeadContainer.STATE_FLAME_BREATHING || head.currentState == HydraHeadContainer.STATE_MORTAR_BEGINNING 
								 || head.currentState == HydraHeadContainer.STATE_MORTAR_LAUNCH;

	}

	protected boolean areOtherHeadsBiting(Entity target, int testHead)
	{
		for (int i = 0; i < numHeads; i++)
		{
			if (i != testHead && isHeadBiting(hc[i]))
			{
				return true;
			}
		}
		
		return false;
	}

	protected boolean isHeadBiting(HydraHeadContainer head)
	{
		return head.currentState == HydraHeadContainer.STATE_BITE_BEGINNING || head.currentState == HydraHeadContainer.STATE_BITE_READY 
				|| head.currentState == HydraHeadContainer.STATE_BITE_BITING || head.nextState == HydraHeadContainer.STATE_BITE_BEGINNING;
	}
	
	/**
	 * Called sometime after the main attackEntity routine.  Finds a valid secondary target and has an unoccupied head start an attack against it.
	 * 
	 * The center head (head 0) does not make secondary attacks 
	 */
	private void secondaryAttacks() {
		for (int i = 0; i < numHeads; i++)
		{
			if (hc[i].headEntity == null)
			{
				return;
			}
		}
		
		// find entities nearby that none of the heads are targeting right now
		EntityLivingBase secondaryTarget = findSecondaryTarget(20); 
		
		if (secondaryTarget != null)
		{
            float distance = secondaryTarget.getDistanceToEntity(this);
            
            //System.out.println("Found a valid secondary target " + distance + " squares away.");
            //System.out.println("Secondary target is " + secondaryTarget);

            for (int i = 1; i < numHeads; i++)
            {
            	if (hc[i].isActive() && hc[i].currentState == HydraHeadContainer.STATE_IDLE &&  isTargetOnThisSide(i, secondaryTarget))
            	{
            		if (distance > 0 && distance < 20 && rand.nextInt(SECONDARY_FLAME_CHANCE) == 0)
            		{
            			hc[i].setTargetEntity(secondaryTarget);
            			hc[i].isSecondaryAttacking = true;
            			hc[i].setNextState(HydraHeadContainer.STATE_FLAME_BEGINNING);
            		}
            		else if (distance > 8 && distance < 32 && rand.nextInt(SECONDARY_MORTAR_CHANCE) == 0)
            		{
            			hc[i].setTargetEntity(secondaryTarget);
            			hc[i].isSecondaryAttacking = true;
            			hc[i].setNextState(HydraHeadContainer.STATE_MORTAR_BEGINNING);
            		}
            	}

            }

		}
	}
	
	/**
	 * Used to make sure heads don't attack across the whole body
	 */
	public boolean isTargetOnThisSide(int headNum, Entity target) 
	{
		double headDist = distanceSqXZ(hc[headNum].headEntity, target);
		double middleDist = distanceSqXZ(this, target);
		
		return headDist < middleDist;
	}
	

	/**
	 * Square of distance between two entities with y not a factor, just x and z
	 */
    private double distanceSqXZ(Entity headEntity, Entity target) {
        double distX = headEntity.posX - target.posX;
        double distZ = headEntity.posZ - target.posZ;
        return distX * distX + distZ * distZ;	
    }

	/**
     * Find an EntityLiving within the specified range.  Exclude our primary target and any head's target.
     * 
     * Right now just finds the closest living entity that is not exluded by our criteria
     */
    @SuppressWarnings("unchecked")
	public EntityLivingBase findSecondaryTarget(double range)
    {
        double closestRange = -1.0D;
        EntityLivingBase closestEntity = null;

		List<EntityLiving> nearbyEntities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX + 1, this.posY + 1, this.posZ + 1).expand(range, range, range));

		for (EntityLivingBase nearbyLiving : nearbyEntities)
		{
			// exclude me
			if (nearbyLiving instanceof EntityTFHydra || nearbyLiving instanceof EntityTFHydraPart)
			{
				continue;
			}
			
			// exclude current target and any head's target
			if (nearbyLiving == currentTarget || isAnyHeadTargeting(nearbyLiving))
			{
				continue;
			}
			
			// only things we can see
			if (!this.canEntityBeSeen(nearbyLiving))
			{
				continue;
			}
			
			double curDist = nearbyLiving.getDistanceSq(this.posX, this.posY, this.posZ);

			if ((range < 0.0D || curDist < range * range) && (closestRange == -1.0D || curDist < closestRange))
			{
				closestRange = curDist;
				closestEntity = nearbyLiving;
			}

		}

        return closestEntity;
    }
    
    boolean isAnyHeadTargeting(Entity targetEntity)
    {
    	for (int i = 0; i < numHeads; i++)
    	{
    		if (hc[i].targetEntity != null && hc[i].targetEntity.equals(targetEntity)) 
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }
    

    /**
     * Pushes all entities inside the list away from the enderdragon.
     */
    private void collideWithEntities(List<Entity> par1List, Entity part)
    {
    	double pushPower = 4.0D;
    	
        double centerX = (part.boundingBox.minX + part.boundingBox.maxX) / 2.0D;
        double centerY = (part.boundingBox.minZ + part.boundingBox.maxZ) / 2.0D;
        
        for (Entity entity : par1List)
        {
            if (entity instanceof EntityLivingBase)
            {
                double distX = entity.posX - centerX;
                double distZ = entity.posZ - centerY;
                double sqDist = distX * distX + distZ * distZ;
                entity.addVelocity(distX / sqDist * pushPower, 0.20000000298023224D, distZ / sqDist * pushPower);
            }
        }
    }

    /**
     * Check the surface immediately beneath us, if it is less than 80% solid
     * @return
     */
    private boolean isUnsteadySurfaceBeneath() {
    	
        int minX = MathHelper.floor_double(this.boundingBox.minX);
        int minZ = MathHelper.floor_double(this.boundingBox.minZ);
        int maxX = MathHelper.floor_double(this.boundingBox.maxX);
        int maxZ = MathHelper.floor_double(this.boundingBox.maxZ);
        int minY = MathHelper.floor_double(this.boundingBox.minY);
        
        int solid = 0;
        int total = 0;
        
        int dy = minY - 1;

        for (int dx = minX; dx <= maxX; ++dx)
        {
        	for (int dz = minZ; dz <= maxZ; ++dz)
        	{
        		total++;
        		if (this.worldObj.getBlock(dx, dy, dz).getMaterial().isSolid())
        		{
        			solid++;
        		}
        	}
        }

        //System.out.println("Checking solidness.  total = " + total + ", solid = " + solid + ", ratio = " + ((float)solid / (float)total));
    	
		return ((float)solid / (float)total) < 0.6F;
	}

	/**
     * Destroys all blocks that aren't associated with 'The End' inside the given bounding box.
     */
    private boolean destroyBlocksInAABB(AxisAlignedBB par1AxisAlignedBB)
    {
    	//System.out.println("Destroying blocks in " + par1AxisAlignedBB);
    	
        int minX = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int minY = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int minZ = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int maxX = MathHelper.floor_double(par1AxisAlignedBB.maxX);
        int maxY = MathHelper.floor_double(par1AxisAlignedBB.maxY);
        int maxZ = MathHelper.floor_double(par1AxisAlignedBB.maxZ);
        boolean wasBlocked = false;
        for (int dx = minX; dx <= maxX; ++dx)
        {
            for (int dy = minY; dy <= maxY; ++dy)
            {
                for (int dz = minZ; dz <= maxZ; ++dz)
                {
                    Block currentID = this.worldObj.getBlock(dx, dy, dz);
                    
                    if (currentID != Blocks.AIR)
                    {
                    	int currentMeta = this.worldObj.getBlockMetadata(dx, dy, dz);
                    	
                    	if (currentID != Blocks.OBSIDIAN && currentID != Blocks.END_STONE && currentID != Blocks.BEDROCK)
                        {
                            this.worldObj.setBlock(dx, dy, dz, Blocks.AIR, 0, 2);
                            
                            // here, this effect will have to do
                			worldObj.playAuxSFX(2001, dx, dy, dz, Block.getIdFromBlock(currentID) + (currentMeta << 12));
                        }
                        else
                        {
                            wasBlocked = true;
                        }
                    }
                }
            }
        }

        return wasBlocked;
    }

	/**
	 * Rather than speed, this seems to control how far up or down the heads can tilt?
	 */
	@Override
    public int getVerticalFaceSpeed()
    {
        return 500;
    }
	
	
    
    @Override
    public boolean attackEntityFromPart(EntityDragonPart dragonpart, DamageSource damagesource, float i)
    {
    	// I think only the body parts are EntityDragonPart.
        //System.out.println("Taking an attack on part " + dragonpart.name + " of type " + damagesource.damageType);
    	
        // try to find range that damage is coming from
        double range = calculateRange(damagesource);
        
        //System.out.println("We decided the range is " + range);
        
        if (range > 400)
        {
        	//System.out.println("Ignoring attack from out of range");
        	return false;
        }
    	
        return superAttackFrom(damagesource, Math.round(i / 8.0F));
    }
    
    protected boolean superAttackFrom(DamageSource par1DamageSource, float par2)
    {
        return super.attackEntityFrom(par1DamageSource, par2);
    }
    
    public boolean attackEntityFromPart(EntityTFHydraPart part, DamageSource damagesource, float damageAmount)
    {
        //System.out.println("Taking an attack on part " + part.getPartName() + " of type " + damagesource.damageType);
        
        // if we're in a wall, kill that wall
        if (!worldObj.isRemote && damagesource == DamageSource.inWall && part.getBoundingBox() != null)
        {
        	destroyBlocksInAABB(part.getBoundingBox());
        }
       
        HydraHeadContainer headCon = null;
        
        for (int i = 0; i < numHeads; i++)
        {
        	if (hc[i].headEntity == part)
        	{
        		headCon = hc[i];
        	}
        }
        
        // try to find range that damage is coming from
        double range = calculateRange(damagesource);
        
        //System.out.println("We decided the range is " + range);
        
        if (range > 400)
        {
        	//System.out.println("Ignoring attack from out of range");
        	return false;
        }

        // ignore hits on dying heads, it's weird
        if (headCon != null && !headCon.isActive())
        {
        	return false;
        }
        	
        boolean tookDamage;
        if (headCon != null && headCon.getCurrentMouthOpen() > 0.5)
        {
        	tookDamage = superAttackFrom(damagesource, damageAmount);
            headCon.addDamage(damageAmount);
        }
        else
        {
        	int armoredDamage = Math.round(damageAmount / ARMOR_MULTIPLIER);
        	tookDamage = superAttackFrom(damagesource, armoredDamage);

        	if (headCon != null)
        	{
        		headCon.addDamage(armoredDamage);
        	}
        }
        
        if (tookDamage)
        {
        	this.ticksSinceDamaged = 0;
        }

        return tookDamage;
    }

	protected double calculateRange(DamageSource damagesource) {
		double range = -1;
        
        if (damagesource.getSourceOfDamage() != null)
        {
        	range = this.getDistanceSqToEntity(damagesource.getSourceOfDamage());
        }

        if (damagesource.getEntity() != null)
        {
        	range = this.getDistanceSqToEntity(damagesource.getEntity());
        }
		return range;
	}
    
    /**
     * Called when the entity is attacked.
     */
    @Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
//        System.out.println("Taking a direct attack of type " + par1DamageSource.damageType);
//
//        return superAttackFrom(par1DamageSource, Math.round(par2 / 8.0F));
    	return false;
    }

    
    /**
     * We need to do this for the bounding boxes on the parts to become active
     */
    @Override
    public Entity[] getParts()
    {
        return partArray;
    }

    /**
     * This is set as off for the hydra, which has an enormous bounding box, but set as on for the parts.
     */
    @Override
	public boolean canBeCollidedWith()
    {
        return false;
    }

    /**
     * If this is on, the player pushes us based on our bounding box rather than it going by parts
     */
	@Override
	public boolean canBePushed() {
		return false;
	}
	
	/**
	 * Don't get knocked back
	 */
    @Override
    public void knockBack(Entity entity, float i, double d, double d1)
    {
        // do nothing?
    }
    

    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
	protected String getLivingSound()
    {
        return TwilightForestMod.ID + ":mob.hydra.growl";
    }    

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
	protected String getHurtSound()
    {
        return TwilightForestMod.ID + ":mob.hydra.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
	protected String getDeathSound()
    {
        return TwilightForestMod.ID + ":mob.hydra.death";
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    @Override
	protected float getSoundVolume()
    {
        return 2F;
    }

    /**
     * Trigger achievement when killed
     */
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).triggerAchievement(TFAchievementPage.twilightHunter);
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).triggerAchievement(TFAchievementPage.twilightKillHydra);
		}
		
		// mark the lair as defeated
		if (!worldObj.isRemote && worldObj.provider instanceof WorldProviderTwilightForest) {
			int dx = MathHelper.floor_double(this.posX);
			int dy = MathHelper.floor_double(this.posY);
			int dz = MathHelper.floor_double(this.posZ);
			
			ChunkProviderTwilightForest chunkProvider = ((WorldProviderTwilightForest)worldObj.provider).getChunkProvider();
			TFFeature nearbyFeature = ((TFWorldChunkManager)worldObj.provider.worldChunkMgr).getFeatureAt(dx, dz, worldObj);
			
			if (nearbyFeature == TFFeature.hydraLair) {
				chunkProvider.setStructureConquered(dx, dy, dz, true);
			}
		}
	}
	
	/**
	 * EPIC LOOTZ!
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2) {
		
		// chops
        int totalDrops = this.rand.nextInt(3 + par2) + 5;
        for (int i = 0; i < totalDrops; ++i)
        {
            this.dropItem(TFItems.hydraChop, 5);
        }

        // blood
        totalDrops = this.rand.nextInt(4 + par2) + 7;
        for (int i = 0; i < totalDrops; ++i)
        {
            this.dropItem(TFItems.fieryBlood, 1);
        }

        // trophy
        this.dropItem(TFItems.trophy, 1);
	}

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    @Override
	protected boolean canDespawn()
    {
        return false;
    }
    
    /**
     * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
     */
    @Override
	public boolean isBurning()
    {
    	// never burn
    	return false;
    }

    /**
     * handles entity death timer, experience orb and particle creation
     */
	@Override
    protected void onDeathUpdate()
    {
        ++this.deathTime;
        
        // stop any head actions on death
        if (deathTime == 1)
        {
        	for (int i = 0; i < numHeads; i++)
        	{
        		hc[i].setRespawnCounter(-1);
        		if (hc[i].isActive())
        		{
        			hc[i].setNextState(HydraHeadContainer.STATE_IDLE);
        			hc[i].endCurrentAction();
        			hc[i].setHurtTime(200);
        		}
        	}
        }
        
        // heads die off one by one
        if (this.deathTime <= 140 && this.deathTime % 20 == 0)
        {
        	int headToDie = (this.deathTime / 20) - 1;
        	
        	if (hc[headToDie].isActive())
        	{
				hc[headToDie].setNextState(HydraHeadContainer.STATE_DYING);
				hc[headToDie].endCurrentAction();
			}
        }

        if (this.deathTime == 200)
        {
            if (!this.worldObj.isRemote && (this.recentlyHit > 0 || this.isPlayer()) && !this.isChild())
            {
            	int var1 = this.getExperiencePoints(this.attackingPlayer);

                while (var1 > 0)
                {
                    int var2 = EntityXPOrb.getXPSplit(var1);
                    var1 -= var2;
                    this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var2));
                }
            }

            this.setDead();

        }
        
        for (int var1 = 0; var1 < 20; ++var1)
        {
            double var8 = this.rand.nextGaussian() * 0.02D;
            double var4 = this.rand.nextGaussian() * 0.02D;
            double var6 = this.rand.nextGaussian() * 0.02D;
            String particle = rand.nextInt(2) == 0 ? "largeexplode" : "explode";
            this.worldObj.spawnParticle(particle, this.posX + this.rand.nextFloat() * this.body.width * 2.0F - this.body.width, this.posY + this.rand.nextFloat() * this.body.height, this.posZ + this.rand.nextFloat() * this.body.width * 2.0F - this.body.width, var8, var4, var6);
        }
    }

	@Override
	public World func_82194_d() {
		return this.worldObj;
	}

//	@Override
//	public int getBossHealth() {
//        return this.dataWatcher.getWatchableObjectInt(EntityTFHydra.DATA_BOSSHEALTH);
//	}
    
    
}
