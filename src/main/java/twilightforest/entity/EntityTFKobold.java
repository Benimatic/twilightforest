package twilightforest.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TwilightForestMod;
import twilightforest.entity.ai.EntityAITFFlockToSameKind;
import twilightforest.entity.ai.EntityAITFPanicOnFlockDeath;


public class EntityTFKobold extends EntityMob {
	

	private boolean shy;

    public EntityTFKobold(World world)
    {
        super(world);
        //texture = TwilightForestMod.MODEL_DIR + "kobold.png";
        //moveSpeed = 0.28F;
        setSize(0.8F, 1.1F);

        shy = true;
        
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAITFPanicOnFlockDeath(this, 2.0F));
        this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.3F));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(4, new EntityAITFFlockToSameKind(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));

    }
    
    public EntityTFKobold(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x, y, z);
    }
	
	@Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(17, (byte)0);
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
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(13.0D*1.5D+twilightforest.TwilightForestMod.Scatter.nextInt(6)-twilightforest.TwilightForestMod.Scatter.nextInt(6)); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D*1.5D); // movement speed
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D*1.5D); // attack damage
    }
    


    @Override
	protected String getLivingSound()
    {
        return TwilightForestMod.ID + ":mob.kobold.kobold";
    }

    @Override
	protected String getHurtSound()
    {
        return TwilightForestMod.ID + ":mob.kobold.hurt";
    }

    @Override
	protected String getDeathSound()
    {
        return TwilightForestMod.ID + ":mob.kobold.die";
    }

    @Override
	protected Item getDropItem()
    {
        return Items.wheat;
    }
    
    @Override
    protected void dropFewItems(boolean flag, int i)
    {
    	super.dropFewItems(flag, i);
    	
        if (rand.nextInt(2) == 0)
        {
            this.dropItem(Items.gold_nugget, 1 + i);
        }
    }
 
    public boolean isShy() {
    	return shy && this.recentlyHit <= 0;
    }
    
    public boolean isPanicked()
    {
        return dataWatcher.getWatchableObjectByte(17) != 0;
    }

    public void setPanicked(boolean flag)
    {
        if (flag)
        {
            dataWatcher.updateObject(17, ((byte)127));
        }
        else
        {
            dataWatcher.updateObject(17, (byte)0);
        }
    }

    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
	public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	
    	//when panicked, spawn tears/sweat
    	if (isPanicked())
    	{
    		for (int i = 0; i < 2; i++)
    		{
    			this.worldObj.spawnParticle("splash", this.posX + (this.rand.nextFloat() - 0.5D) * this.width * 0.5, this.posY + this.getEyeHeight(), this.posZ + (this.rand.nextFloat() - 0.5D) * this.width * 0.5, 0, 0, 0);
    		}
    	}

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
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
	public int getMaxSpawnedInChunk()
    {
        return 8;
    }
    

}
