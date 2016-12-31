package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TwilightForestMod;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.entity.ai.EntityAITFThrowRider;
import twilightforest.item.TFItems;

public class EntityTFYeti extends EntityMob
{

	
    private static final int ANGER_FLAG = 16;

	public EntityTFYeti(World par1World)
    {
        super(par1World);
        this.setSize(1.4F, 2.4F);

        
        this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(1, new EntityAITFThrowRider(this, 1.0F));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0F));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true, false));
    }
	

    /**
     * Returns true if the newer Entity AI code should be run
     */
    @Override
	protected boolean isAIEnabled()
    {
        return true;
    }
    
	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D*1.5+twilightforest.TwilightForestMod.Scatter.nextInt(10)-twilightforest.TwilightForestMod.Scatter.nextInt(10)); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.38D*1.5); // movement speed
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.0D+2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(4.0D*1.5);
    }
	
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(ANGER_FLAG, (byte)0);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
	public void onLivingUpdate()
    {
    	if (this.riddenByEntity != null)
    	{
    		this.setSize(1.4F, 2.4F);
    		
            // stop player sneaking so that they can't dismount!
            if (this.riddenByEntity.isSneaking())
            {
            	//System.out.println("Pinch beetle sneaking detected!");
            	
            	this.riddenByEntity.setSneaking(false);
            }
    	}
    	else
    	{
    		this.setSize(1.4F, 2.4F);

    	}
    	
    	super.onLivingUpdate();

    	// look at things in our jaws
    	if (this.riddenByEntity != null)
    	{
            this.getLookHelper().setLookPositionWithEntity(riddenByEntity, 100F, 100F);
    		//this.faceEntity(riddenByEntity, 100F, 100F);

            
            // push out of user in wall
            Vec3 riderPos = this.getRiderPosition();
            this.func_145771_j(riderPos.xCoord, riderPos.yCoord, riderPos.zCoord); // push out of block
            

    	}
    }
    

	/**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    @Override
	public boolean interact(EntityPlayer par1EntityPlayer)
    {
        if (super.interact(par1EntityPlayer))
        {
            return true;
        }
//        else if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == par1EntityPlayer))
//        {
//            par1EntityPlayer.mountEntity(this);
//            return true;
//        }
        else
        {
            return false;
        }
    }
	
	/**
	 * Pick up things we attack!
	 */
    @Override
	public boolean attackEntityAsMob(Entity par1Entity) 
    {
    	if (this.riddenByEntity == null && par1Entity.ridingEntity == null)
    	{
    		par1Entity.mountEntity(this);
    	}
    	
		return super.attackEntityAsMob(par1Entity);
	}
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
    	if (par1DamageSource.getSourceOfDamage() != null) {
    		// become angry
    		this.setAngry(true);
    	}
    	
    	return super.attackEntityFrom(par1DamageSource, par2);
    }


    
    /**
     * Determines whether this yeti is angry or not.
     */
    public boolean isAngry() {
        return (this.dataWatcher.getWatchableObjectByte(ANGER_FLAG) & 2) != 0;
    }

    /**
     * Sets whether this yeti is angry or not.
     */
    public void setAngry(boolean anger) {
        byte b0 = this.dataWatcher.getWatchableObjectByte(ANGER_FLAG);

        if (anger) {
            this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
            this.dataWatcher.updateObject(ANGER_FLAG, ((byte)(b0 | 2)));
        } else {
            this.dataWatcher.updateObject(ANGER_FLAG, ((byte)(b0 & -3)));
        }
    }
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Angry", this.isAngry());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setAngry(par1NBTTagCompound.getBoolean("Angry"));
    }

	/**
     * Put the player out in front of where we are
     */
    @Override
	public void updateRiderPosition()
    {
        if (this.riddenByEntity != null)
        {
        	Vec3 riderPos = this.getRiderPosition();
        	
            this.riddenByEntity.setPosition(riderPos.xCoord, riderPos.yCoord, riderPos.zCoord);
        }
    }
    
    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    @Override
	public double getMountedYOffset()
    {
        return 2.25D;
    }
    
    /**
     * Used to both get a rider position and to push out of blocks
     */
    public Vec3 getRiderPosition()
    {
    	if (this.riddenByEntity != null)
    	{
    		float distance = 0.4F;

    		double var1 = org.bogdang.modifications.math.MathHelperLite.cos((this.rotationYaw + 90) * Math.PI / 180.0D) * distance;
    		double var3 = org.bogdang.modifications.math.MathHelperLite.sin((this.rotationYaw + 90) * Math.PI / 180.0D) * distance;

    		return Vec3.createVectorHelper(this.posX + var1, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + var3);
    	}
    	else
    	{
    		return Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
    	}
    }
    
    /**
     * If a rider of this entity can interact with this entity. Should return true on the
     * ridden entity if so.
     *
     * @return if the entity can be interacted with from a rider
     */
    public boolean canRiderInteract()
    {
        return true;
    }
    
    /**
     * Trigger achievement when killed
     */
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (worldObj.provider.dimensionId == TwilightForestMod.dimensionID && par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).triggerAchievement(TFAchievementPage.twilightHunter);
		}
	}
	
	/**
	 * We're allowed to spawn in bright light only in sniw
	 */
	@Override
	public boolean getCanSpawnHere()
    {
		// are we in the snow
		if (worldObj.getBiomeGenForCoords(MathHelper.floor_double(posX), MathHelper.floor_double(posZ)) == TFBiomeBase.tfSnow) {
			// don't check light level
	        return worldObj.checkNoEntityCollision(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0;
		}
		else {
			// normal EntityMob spawn check, checks light level
			return super.getCanSpawnHere();
		}
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
	@Override
	protected boolean isValidLightLevel() {
        int x = MathHelper.floor_double(this.posX);
        int z = MathHelper.floor_double(this.posZ);
        
		if (worldObj.getBiomeGenForCoords(x, z) == TFBiomeBase.tfSnow) {
			return true;
		} else {
			return super.isValidLightLevel();
		}
	}
	
    protected Item getDropItem()
    {
        return TFItems.arcticFur;
    }

	
}
