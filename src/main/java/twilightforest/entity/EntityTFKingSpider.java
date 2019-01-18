package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityTFKingSpider extends EntitySpider {

	public EntityTFKingSpider(World world) {
		super(world);
		//texture = TwilightForestMod.MODEL_DIR + "kingspider.png";
        this.setSize(1.6F, 1.6F);
        //this.moveSpeed = 0.35F;
        
        this.getNavigator().setAvoidsWater(true);
		//this.tasks.addTask(1, new EntityAITFChargeAttack(this, 0.4F));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.3F));
        this.tasks.addTask(6, new EntityAIWander(this, 0.2F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
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
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D*2.5D+twilightforest.TwilightForestMod.Scatter.nextInt(15)-twilightforest.TwilightForestMod.Scatter.nextInt(15)); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D*1.5D); // movement speed
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D*1.5D); // attack damage
    }

	
    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    @Override
	protected Entity findPlayerToAttack()
    {
    	// kill at all times!
    	double var2 = 16.0D;
    	return this.worldObj.getClosestVulnerablePlayerToEntity(this, var2);
    }
	
    /**
     * How large the spider should be scaled.
     */
	//@Override
    public float spiderScaleAmount()
    {
        return 1.9F;
    }

	/**
	 * Actually only used for the shadow
	 */
	@Override
	public float getRenderSizeModifier() {
		 return 2.0F;
	}

    
    /**
     * returns true if this entity is by a ladder, false otherwise
     */
    public boolean isOnLadder()
    {
    	// let's not climb
        return false;
    }
    
    /**
     * Init creature with mount
     */
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
    {
        Object par1EntityLivingData1 = super.onSpawnWithEgg(par1EntityLivingData);

    	// always a spider jockey
        EntityTFSkeletonDruid druid = new EntityTFSkeletonDruid(this.worldObj);
        druid.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        druid.onSpawnWithEgg((IEntityLivingData)null);
        this.worldObj.spawnEntityInWorld(druid);
        druid.mountEntity(this);
        
        return (IEntityLivingData)par1EntityLivingData1;
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset()
    {
        return (double)this.height * 0.5D;
    }

}
