package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.entity.ai.EntityAITFChargeAttack;
import twilightforest.entity.ai.EntityAITFKidnapRider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityTFPinchBeetle extends EntityMob
{
	public EntityTFPinchBeetle(World world) {
		super(world);
		//texture = TwilightForestMod.MODEL_DIR + "pinchbeetle.png";
		//moveSpeed = 0.23F;
		setSize(1.2F, 1.1F);

		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAITFKidnapRider(this, 2.0F));
		this.tasks.addTask(2, new EntityAITFChargeAttack(this, 2.0F));
		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		//this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
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
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D); // movement speed
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D); // attack damage
    }

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    @Override
	public int getTotalArmorValue()
    {
        int var1 = super.getTotalArmorValue() + 2;

        if (var1 > 20)
        {
            var1 = 20;
        }

        return var1;
    }
	
    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
	protected String getLivingSound()
    {
        return null;
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
	protected String getHurtSound()
    {
        return "mob.spider.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
	protected String getDeathSound()
    {
        return "mob.spider.death";
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    @Override
	protected void func_145780_a(int var1, int var2, int var3, Block var4)
    {
        this.worldObj.playSoundAtEntity(this, "mob.spider.step", 0.15F, 1.0F);
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
    		this.setSize(1.9F, 2.0F);
    		
            // stop player sneaking so that they can't dismount!
            if (this.riddenByEntity.isSneaking())
            {
            	//System.out.println("Pinch beetle sneaking detected!");
            	
            	this.riddenByEntity.setSneaking(false);
            }
    	}
    	else
    	{
    		this.setSize(1.2F, 1.1F);

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
     * Trigger achievement when killed
     */
    @Override
    public void onDeath(DamageSource par1DamageSource) {
    	super.onDeath(par1DamageSource);
    	if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
    		((EntityPlayer)par1DamageSource.getSourceOfDamage()).triggerAchievement(TFAchievementPage.twilightHunter);
    	}
    }

    /**
     * Attack strength
     */
    public int getAttackStrength(Entity par1Entity)
    {
        return 8;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() 
	{
		return 1.1F;
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
    
	@Override
	public float getEyeHeight() {
		return 0.25F;
	}

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
	public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
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
        return 0.75D;
    }
    
    /**
     * Used to both get a rider position and to push out of blocks
     */
    public Vec3 getRiderPosition()
    {
    	if (this.riddenByEntity != null)
    	{
    		float distance = 0.9F;

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
}
