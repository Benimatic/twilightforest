package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.entity.ai.EntityAITFChargeAttack;
import twilightforest.item.TFItems;

public class EntityTFMinotaur extends EntityMob implements ITFCharger {
	
	public EntityTFMinotaur(World par1World) {
		super(par1World);
		//this.texture = TwilightForestMod.MODEL_DIR + "minotaur.png";
        //this.moveSpeed = 0.25F;
        
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAITFChargeAttack(this, 2.0F));
		this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false));

        this.setCurrentItemOrArmor(0, new ItemStack(Items.GOLDEN_AXE));
	}

	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D); // movement speed
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D); // attack damage
    }
    



	@Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(17, Byte.valueOf((byte)0));
    }
	
    /**
     * Returns true if the newer Entity AI code should be run
     */
    @Override
	protected boolean isAIEnabled()
    {
        return true;
    }
    
    public boolean isCharging()
    {
        return dataWatcher.getWatchableObjectByte(17) != 0;
    }

    public void setCharging(boolean flag)
    {
        if (flag)
        {
            dataWatcher.updateObject(17, Byte.valueOf((byte)127));
        }
        else
        {
            dataWatcher.updateObject(17, Byte.valueOf((byte)0));
        }
    }
    
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        boolean success = super.attackEntityAsMob(par1Entity);

        if (success && this.isCharging())
        {
            par1Entity.motionY += 0.4000000059604645D;
            this.worldObj.playSoundAtEntity(this, "mob.irongolem.throw", 1.0F, 1.0F);
        }

        return success;
    }

    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
	public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	
    	//when charging, move legs fast
    	if (isCharging())
    	{
			this.limbSwingAmount += 0.6;
    	}

    }
    
    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
	protected String getLivingSound()
    {
        return "mob.cow.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
	protected String getHurtSound()
    {
        return "mob.cow.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
	protected String getDeathSound()
    {
        return "mob.cow.hurt";
    }
    
    /**
     * Plays step sound at given x, y, z for the entity
     */
    @Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4)
    {
        this.worldObj.playSoundAtEntity(this, "mob.cow.step", 0.15F, 0.8F);
    }

    /**
     * Gets the pitch of living sounds in living entities.
     */
    @Override
	protected float getSoundPitch()
    {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.7F;
    }
    
    /**
     * Returns the item ID for the item the mob drops on death.
     */
    @Override
	protected Item getDropItem()
    {
        return TFItems.meefRaw;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    @Override
	protected void dropFewItems(boolean par1, int par2)
    {
        int numDrops = this.rand.nextInt(2) + this.rand.nextInt(1 + par2);

        for (int i = 0; i < numDrops; ++i)
        {
            if (this.isBurning())
            {
                this.dropItem(TFItems.meefSteak, 1);
            }
            else
            {
                this.dropItem(TFItems.meefRaw, 1);
            }
        }
    }
    
    @Override
	protected void dropRareDrop(int par1)
    {
        this.dropItem(TFItems.mazeMapFocus, 1);
    }

    
//    /**
//     * Initialize this creature.
//     */
//    @Override
//	public void initCreature()
//    {
//    	//this.func_82164_bB();
//        this.func_82162_bC();
//    }

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

}
