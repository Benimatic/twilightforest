package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureAttribute;
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
import twilightforest.item.TFItems;


public class EntityTFHelmetCrab extends EntityMob {
	

    public EntityTFHelmetCrab(World world)
    {
        super(world);
        //texture = TwilightForestMod.MODEL_DIR + "helmetcrab.png";
        //moveSpeed = 0.28F;
        setSize(0.8F, 1.1F);
      
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.28F));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));

    }
    
    public EntityTFHelmetCrab(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x, y, z);
    }
	
	@Override
    protected void entityInit()
    {
        super.entityInit();
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
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(13.0D); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D); // movement speed
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D); // attack damage
    }
    


    @Override
	protected String getLivingSound()
    {
        return null;
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.spider.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.spider.death";
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void func_145780_a(int par1, int par2, int par3, Block par4)
    {
        this.playSound("mob.spider.step", 0.15F, 1.0F);
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected Item getDropItem()
    {
        return TFItems.armorShard;
    }
    
    @Override
    protected void dropFewItems(boolean flag, int i)
    {
    	super.dropFewItems(flag, i);
    	
        if (rand.nextInt(2) == 0)
        {
            this.dropItem(Items.FISH, 1 + i);
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
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
	public int getMaxSpawnedInChunk()
    {
        return 8;
    }


    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    
    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    public int getTotalArmorValue()
    {
        int i = super.getTotalArmorValue() + 6;

        if (i > 20)
        {
            i = 20;
        }

        return i;
    }

}
