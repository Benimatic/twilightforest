package twilightforest.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import twilightforest.TFFeature;

public class EntityTFTowerGhast extends EntityGhast
{

	private static final int AGGRO_STATUS = 16;
	protected EntityLivingBase targetedEntity;
	protected boolean isAggressive;
	protected int aggroCooldown;
	protected int explosionPower;
	protected int aggroCounter;
	
	protected float aggroRange;
	protected float stareRange;
	
	protected float wanderFactor;
	
	protected int inTrapCounter;

    private BlockPos homePosition = BlockPos.ORIGIN;
    /** If -1 there is no maximum distance */
    private float maximumHomeDistance = -1.0F;

	public EntityTFTowerGhast(World par1World) {
		super(par1World);
        //this.texture = TwilightForestMod.MODEL_DIR + "towerghast.png";
        
        this.setSize(4.0F, 6.0F);

        this.aggroRange = 64.0F;
    	this.stareRange = 32.0F;

    	this.wanderFactor = 16.0F;
    	
    	this.inTrapCounter = 0;
	}
//	
//    public int getMaxHealth()
//    {
//        return 30;
//    }

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.5F;
    }
    
    @Override
    public int getTalkInterval()
    {
        return 160;
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 8;
    }
    
    @Override
	public void onUpdate() 
    {
		super.onUpdate();
        //byte aggroStatus = this.dataWatcher.getWatchableObjectByte(AGGRO_STATUS);

//        switch (aggroStatus)
//        {
//        case 0:
//        default:
//        	this.texture = TwilightForestMod.MODEL_DIR + "towerghast.png";
//        	break;
//        case 1:
//        	this.texture = TwilightForestMod.MODEL_DIR + "towerghast_openeyes.png";
//        	break;
//        case 2:
//        	this.texture = TwilightForestMod.MODEL_DIR + "towerghast_fire.png";
//        	break;
//        }
	}
    
    public int getAttackStatus() {
    	return this.dataWatcher.getWatchableObjectByte(AGGRO_STATUS);
    }

	@Override
    public void onLivingUpdate()
    {
    	// age when in light, like mobs
        float var1 = this.getBrightness(1.0F);

        if (var1 > 0.5F)
        {
            this.entityAge += 2;
        }
        
        if (this.rand.nextBoolean())
        {
            this.world.spawnParticle(EnumParticleTypes.REDSTONE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0, 0, 0);
        }

        super.onLivingUpdate();
    }

    @Override
    protected void updateAITasks()
    {
        if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
            this.setDead();
        }

        this.despawnEntity();
        
        // tower home
        checkForTowerHome();
        
        // check if in trap
        if (this.inTrapCounter > 0)
        {
        	this.inTrapCounter--;
        	this.targetedEntity = null;
        	return; // do nothing if in trap;
        }
        
        this.prevAttackCounter = this.attackCounter;
        
        // target
        if (this.targetedEntity != null && this.targetedEntity.isDead)
        {
            this.targetedEntity = null;
        }

        if (this.targetedEntity == null)
        {
            this.targetedEntity = findPlayerInRange();
        }
        else if (!this.isAggressive && this.targetedEntity instanceof EntityPlayer)
        {
        	checkToIncreaseAggro((EntityPlayer) this.targetedEntity);
        }
        
        // check if we are at our waypoint target
        double offsetX = this.waypointX - this.posX;
        double offsetY = this.waypointY - this.posY;
        double offsetZ = this.waypointZ - this.posZ;
        double distanceDesired = offsetX * offsetX + offsetY * offsetY + offsetZ * offsetZ;

        if ((distanceDesired < 1.0D || distanceDesired > 3600.0D) && this.wanderFactor > 0)
        {
            this.waypointX = this.posX + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * wanderFactor);
            this.waypointY = this.posY + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * wanderFactor);
            this.waypointZ = this.posZ + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * wanderFactor);
        }
        
        
        
        // move?
        if (this.targetedEntity == null && this.wanderFactor > 0)
        {
        	if (this.courseChangeCooldown-- <= 0)
        	{
        		this.courseChangeCooldown += this.rand.nextInt(20) + 20;
        		distanceDesired = (double)MathHelper.sqrt(distanceDesired);
        		
        	    if (!this.isWithinHomeDistance(MathHelper.floor(waypointX), MathHelper.floor(waypointY), MathHelper.floor(waypointZ)))
        	    {
        	    	// change waypoint to be more towards home
        	        BlockPos cc = TFFeature.getNearestCenterXYZ(MathHelper.floor(this.posX), MathHelper.floor(this.posZ), world);
        	    	
        	        Vec3d homeVector = new Vec3d(cc.posX - this.posX, cc.posY + 128 - this.posY, cc.posZ - this.posZ);
        	        homeVector = homeVector.normalize();
        	        
        	    	this.waypointX = this.posX + homeVector.xCoord * 16.0F + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
        	    	this.waypointY = this.posY + homeVector.yCoord * 16.0F + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
        	    	this.waypointZ = this.posZ + homeVector.zCoord * 16.0F + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
        	    	
        	    	//System.out.println("Setting tower ghast on a course towards home: " + this.waypointX + ", " + this.waypointY + ", " + this.waypointZ);
        	    } 
        		
        		
        		if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, distanceDesired))
        		{
        			this.motionX += offsetX / distanceDesired * 0.1D;
        			this.motionY += offsetY / distanceDesired * 0.1D;
        			this.motionZ += offsetZ / distanceDesired * 0.1D;
        		}
        		else
        		{
        			this.waypointX = this.posX;
        			this.waypointY = this.posY;
        			this.waypointZ = this.posZ;
        		}
        	}
        }
        else
        {
        	this.motionX *= 0.75;
        	this.motionY *= 0.75;
        	this.motionZ *= 0.75;
        }

        // check if our target is still within range
		double targetRange = (this.aggroCounter > 0 || this.isAggressive) ? aggroRange : stareRange;

        if (this.targetedEntity != null && this.targetedEntity.getDistanceSqToEntity(this) < targetRange * targetRange && this.canEntityBeSeen(this.targetedEntity))
        {
            // turn towards target
        	this.faceEntity(this.targetedEntity, 10F, this.getVerticalFaceSpeed());
        	
        	// attack if aggressive
            if (this.isAggressive)
            {
                if (this.attackCounter == 10)
                {
                    this.world.playAuxSFXAtEntity((EntityPlayer)null, 1007, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
                }

                ++this.attackCounter;

                if (this.attackCounter == 20)
                {
                	spitFireball();
            		this.attackCounter = -40;
                }
            }
         }
        else
        {
        	// ignore player, move normally
            this.isAggressive = false;
        	this.targetedEntity = null;
        	this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float)Math.PI;
        	
        	// changing the pitch with movement looks goofy and un-ghast-like
        	//double dist = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ *  this.motionZ);
        	this.rotationPitch = 0;//(float) (-((Math.atan2(this.motionY, dist) * 180D) / Math.PI));;

        }

        if (this.attackCounter > 0 && !this.isAggressive)
        {
            --this.attackCounter;
        }

        // set aggro status
        byte currentAggroStatus = this.dataWatcher.getWatchableObjectByte(AGGRO_STATUS);
        byte newAggroStatus = (byte)(this.attackCounter > 10 ? 2 : (this.aggroCounter > 0 || this.isAggressive) ? 1 : 0);

        if (currentAggroStatus != newAggroStatus)
        {
        	this.dataWatcher.updateObject(AGGRO_STATUS, Byte.valueOf(newAggroStatus));
        }
    }
    
	
	/**
	 * Something is deeply wrong with the calculations based off of this value, so let's set it high enough that it's ignored.
	 */
	@Override
    public int getVerticalFaceSpeed()
    {
        return 500;
    }

    /**
     * Spit a fireball
     */
	protected void spitFireball() {
		double offsetX = this.targetedEntity.posX - this.posX;
		double offsetY = this.targetedEntity.getEntityBoundingBox().minY + (double)(this.targetedEntity.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
		double offsetZ = this.targetedEntity.posZ - this.posZ;
		
		// fireball sound effect
		this.world.playAuxSFXAtEntity((EntityPlayer)null, 1008, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
		
		
		EntityLargeFireball entityFireball = new EntityLargeFireball(this.world, this, offsetX, offsetY, offsetZ);
		//var17.field_92012_e = this.explosionPower;
		double shotSpawnDistance = 0.5D;
		Vec3d lookVec = this.getLook(1.0F);
		entityFireball.posX = this.posX + lookVec.xCoord * shotSpawnDistance;
		entityFireball.posY = this.posY + (double)(this.height / 2.0F) + lookVec.yCoord * shotSpawnDistance;
		entityFireball.posZ = this.posZ + lookVec.zCoord * shotSpawnDistance;
		this.world.spawnEntity(entityFireball);
		
		// when we attack, there is a 1-in-6 chance we decide to stop attacking
		if (rand.nextInt(6) == 0)
		{
			this.isAggressive = false;
		}
	}
    
    /**
     * Find a player in our aggro range.  If we feel the need to become aggressive towards that 
     * player, or it is within our stare range, return
     * 
     * @return
     */
    protected EntityLivingBase findPlayerInRange() {
    	EntityPlayer closest = this.world.getNearestAttackablePlayer(this, aggroRange, aggroRange);
    	
    	if (closest != null)
    	{
    		float range = this.getDistanceToEntity(closest);
    		if (range < this.stareRange || this.shouldAttackPlayer(closest))
    		{
    			return closest;
    		}
    	}
    	return null;
	}

    /**
     * Check if we should become aggressive towards the player.
     * 
     * @param par1EntityPlayer
     */
    protected void checkToIncreaseAggro(EntityPlayer par1EntityPlayer)
    {
    	if (this.shouldAttackPlayer(par1EntityPlayer))
    	{
    		if (this.aggroCounter == 0)
    		{
    			this.world.playSoundAtEntity(this, "mob.ghast.moan", 1.0F, 1.0F);
    		}

    		if (this.aggroCounter++ >= 20)
    		{
    			this.aggroCounter = 0;
    			this.isAggressive = true;
    		}
    	}
    	else
    	{
     		this.aggroCounter = 0;
    	}    
    }


    
    /**
     * Checks to see if this tower ghast should be attacking this player.  
     * Tower ghasts attack if the block above the player is not Tower wood
     */
    protected boolean shouldAttackPlayer(EntityPlayer par1EntityPlayer)
    {
        return world.canSeeSky(new BlockPos(par1EntityPlayer)) && par1EntityPlayer.canEntityBeSeen(this);
    }



    /**
     * True if the ghast has an unobstructed line of travel to the waypoint.
     */
    protected boolean isCourseTraversable(double par1, double par3, double par5, double par7)
    {
        double var9 = (this.waypointX - this.posX) / par7;
        double var11 = (this.waypointY - this.posY) / par7;
        double var13 = (this.waypointZ - this.posZ) / par7;
        AxisAlignedBB var15 = this.boundingBox.copy();

        for (int var16 = 1; (double)var16 < par7; ++var16)
        {
            var15.offset(var9, var11, var13);

            if (!this.world.getCollidingBoundingBoxes(this, var15).isEmpty())
            {
                return false;
            }
        }

        return true;
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
    	boolean wasAttacked = super.attackEntityFrom(par1DamageSource, par2);
    	
        if (wasAttacked && par1DamageSource.getSourceOfDamage() instanceof EntityLivingBase)
        {
        	this.targetedEntity = (EntityLivingBase)par1DamageSource.getSourceOfDamage();
        	
        	this.isAggressive = true;
        			
            return true;
        }
        else
        {
        	return wasAttacked;
        }
    }
    
    @Override
    public boolean getCanSpawnHere()
    {
        return this.world.checkNoEntityCollision(getEntityBoundingBox())
        		&& this.world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty()
        		&& !this.world.containsAnyLiquid(getEntityBoundingBox())
        		&& this.world.getDifficulty() != EnumDifficulty.PEACEFUL
        		&& this.isValidLightLevel();
    }
    
    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        return true;
    }

    /**
     * Check for our tower home and if we find it, set it
     */
    protected void checkForTowerHome() 
    {
    	if (!this.hasHome())
    	{
    		// check if we're near a dark forest tower and if so, set that as home
    		int chunkX = MathHelper.floor(this.posX) >> 4;
    		int chunkZ = MathHelper.floor(this.posZ) >> 4;

    		TFFeature nearFeature = TFFeature.getFeatureForRegion(chunkX, chunkZ, this.world);

    		if (nearFeature != TFFeature.darkTower)
    		{
    			this.detachHome();

    			this.entityAge += 5;

    			//System.out.println("Tower ghast is lost");

    		}
    		else
    		{
    			// set our home position to the center of the tower
    			BlockPos cc = TFFeature.getNearestCenterXYZ(MathHelper.floor(this.posX), MathHelper.floor(this.posZ), world);
    			this.setHomeArea(cc.up(128), 64);

//                System.out.println("Ghast is at  " + this.posX + ", " + this.posY + ", " + this.posZ);
//    			System.out.println("Set home area to " + cc.posX + ", " + (cc.posY + 128) + ", " + cc.posZ);
    		}	
    	}
    }
    
    /**
     * Towers are so large, a simple radius doesn't really work, so we make it more of a cylinder
     */
    public boolean isWithinHomeDistance(int x, int y, int z)
    {
    	if (this.getMaximumHomeDistance() == -1.0F)
    	{
    		return true;
    	}
    	else
    	{
    		BlockPos home = this.getHomePosition();
    		
//    		System.out.println("Checking home for " + x + ", " + y + ", " + z + " and home is " + home.posX + ", " + home.posY + ", " + home.posZ);
//    		System.out.println("home.getDistanceSquared(x, home.posY, z) =  "  + home.getDistanceSquared(x, home.posY, z) + " compared to " + (this.getMaximumHomeDistance() * this.getMaximumHomeDistance()));
    		
    		return y > 64 && y < 210 && home.distanceSq(x, home.getY(), z) < this.getMaximumHomeDistance() * this.getMaximumHomeDistance();
    	}
    }
    
    public void setInTrap()
    {
    	this.inTrapCounter = 10;
    }
    
    public void setHomeArea(BlockPos pos, int par4)
    {
        this.homePosition = pos;
        this.maximumHomeDistance = (float)par4;
    }

    public BlockPos getHomePosition()
    {
        return this.homePosition;
    }

    public float getMaximumHomeDistance()
    {
        return this.maximumHomeDistance;
    }

    public void detachHome()
    {
        this.maximumHomeDistance = -1.0F;
    }

    public boolean hasHome()
    {
        return this.maximumHomeDistance != -1.0F;
    }
}

