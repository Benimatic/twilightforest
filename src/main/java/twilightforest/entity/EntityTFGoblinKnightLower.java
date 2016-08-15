package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import twilightforest.entity.ai.EntityAITFRiderSpearAttack;
import twilightforest.item.TFItems;

public class EntityTFGoblinKnightLower extends EntityMob {
	
	private static final int DATA_EQUIP = 17;

	public EntityTFGoblinKnightLower(World par1World) {
		super(par1World);
        //texture = TwilightForestMod.MODEL_DIR + "doublegoblin.png";
        //moveSpeed = 0.28F;
        setSize(0.7F, 1.1F);	
        
        this.tasks.addTask(0, new EntityAITFRiderSpearAttack(this));
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false));

		this.setHasArmor(true);
    }


	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D); // movement speed
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D); // attack damage
    }
	
    /**
     * Returns true if the newer Entity AI code should be run
     */
    @Override
	protected boolean isAIEnabled()
    {
        return true;
    }
    
	@Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(DATA_EQUIP, (byte)0);
    }
	
    public boolean hasArmor()
    {
        return (dataWatcher.getWatchableObjectByte(DATA_EQUIP) & 1) > 0;
    }

    public void setHasArmor(boolean flag)
    {
    	byte otherFlags = dataWatcher.getWatchableObjectByte(DATA_EQUIP);
    	otherFlags &= 126;
    	
        if (flag)
        {
            dataWatcher.updateObject(DATA_EQUIP, ((byte) (otherFlags | 1)));
        }
        else
        {
            dataWatcher.updateObject(DATA_EQUIP, ((byte)otherFlags));
        }
    }
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("hasArmor", this.hasArmor());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
	@Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setHasArmor(par1NBTTagCompound.getBoolean("hasArmor"));
    }

    
    /**
     * Initialize this creature.
     */
    public void initCreature()
    {
    	// we start with the upper guy riding us
    	EntityTFGoblinKnightUpper upper = new EntityTFGoblinKnightUpper(this.worldObj);
        upper.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        upper.onSpawnWithEgg((IEntityLivingData)null);
        this.worldObj.spawnEntityInWorld(upper);
        upper.mountEntity(this);
    }
    
    /**
     * Init creature with mount
     */
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
    {
        Object par1EntityLivingData1 = super.onSpawnWithEgg(par1EntityLivingData);

    	// we start with the upper guy riding us
    	EntityTFGoblinKnightUpper upper = new EntityTFGoblinKnightUpper(this.worldObj);
        upper.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        upper.onSpawnWithEgg((IEntityLivingData)null);
        this.worldObj.spawnEntityInWorld(upper);
        upper.mountEntity(this);

        return (IEntityLivingData)par1EntityLivingData1;
    }

    
    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset()
    {
        return 1.0D;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (this.isEntityAlive())
        {
            // synch target with lower goblin
        	if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLiving && this.getAttackTarget() == null)
        	{
        		this.setAttackTarget(((EntityLiving) this.riddenByEntity).getAttackTarget());
        	}
        }

        super.onUpdate();
    }
    
	/**
	 * Do not attack if we have a rider
	 */
	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		
		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLiving)
		{
			return ((EntityLiving)this.riddenByEntity).attackEntityAsMob(par1Entity);
		}
		else
		{
			return super.attackEntityAsMob(par1Entity);
		}

	}
	
    /**
     * Called when the entity is attacked.
     */
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float damageAmount) {
		// check the angle of attack, if applicable
    	Entity attacker = null;
        if (par1DamageSource.getSourceOfDamage() != null)
        {
        	attacker = par1DamageSource.getSourceOfDamage();
        }

        if (par1DamageSource.getEntity() != null)
        {
        	attacker = par1DamageSource.getEntity();
        }
        
        if (attacker != null)
        {
        	// determine angle
        	
	    	double dx = this.posX - attacker.posX;
	    	double dz = this.posZ - attacker.posZ;
	    	float angle = (float)((Math.atan2(dz, dx) * 180D) / Math.PI) - 90F;
	
	    	float difference = MathHelper.abs((this.renderYawOffset - angle) % 360);
	    	
	    	//System.out.println("Difference in angle of approach is " + difference);
	
	    	// shield?
	    	EntityTFGoblinKnightUpper upper = null;
	    	
	    	if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityTFGoblinKnightUpper)
	    	{
	    		upper = (EntityTFGoblinKnightUpper) this.riddenByEntity;
	    	}
	    	
	    	if (upper != null && upper.hasShield() && difference > 150 && difference < 230)
	    	{
		    	if (upper.takeHitOnShield(par1DamageSource, damageAmount))
		    	{
		    		return false;
		    	}
	    	}
	    	
	    	// break armor?
	    	if (this.hasArmor() && (difference > 300 || difference < 60))
	    	{
		    	breakArmor();
	    	}
        }
        
        
        boolean attackSuccess = super.attackEntityFrom(par1DamageSource, damageAmount);
        
		// I think we're done
		return attackSuccess;
	}
	
	/**
	 * Break our armor
	 */
	public void breakArmor() {
		this.renderBrokenItemStack(new ItemStack(Items.iron_chestplate));
		this.renderBrokenItemStack(new ItemStack(Items.iron_chestplate));
		this.renderBrokenItemStack(new ItemStack(Items.iron_chestplate));
		
		this.setHasArmor(false);
	}
	
    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    public int getTotalArmorValue()
    {
        int armor = super.getTotalArmorValue();
        
        if (this.hasArmor())
        {
        	armor += 17;
        }

        if (armor > 20)
        {
            armor = 20;
        }

        return armor;
    }

    
    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected Item getDropItemId()
    {
        return TFItems.armorShard;
    }
    
}
