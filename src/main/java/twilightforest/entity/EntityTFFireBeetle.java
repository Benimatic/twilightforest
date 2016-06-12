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
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.entity.ai.EntityAITFBreathAttack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class EntityTFFireBeetle extends EntityMob implements IBreathAttacker 
{
	
    public static final int BREATH_DURATION = 10;
    public static final int BREATH_DAMAGE = 2;


    public EntityTFFireBeetle(World world)
    {
        super(world);
        //texture = TwilightForestMod.MODEL_DIR + "firebeetle.png";
        //moveSpeed = 0.23F;
        setSize(1.1F, .75F);

        this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAITFBreathAttack(this, 1.0F, 5F, 30, 0.1F));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0F, false));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0F));
        //this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        //this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));

    }
    
    public EntityTFFireBeetle(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x, y, z);
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

	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D); // movement speed
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D); // attack damage
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


    @Override
	protected Item getDropItem()
    {
        return Items.GUNPOWDER;
    }
    
    /* (non-Javadoc)
	 * @see twilightforest.entity.IBreathAttacker#isBreathing()
	 */
    @Override
	public boolean isBreathing()
    {
        return dataWatcher.getWatchableObjectByte(17) != 0;
    }

    /* (non-Javadoc)
	 * @see twilightforest.entity.IBreathAttacker#setBreathing(boolean)
	 */
    @Override
	public void setBreathing(boolean flag)
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

    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
	public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	
    	// when breathing fire, spew particles
    	if (isBreathing())
    	{
    		Vec3d look = this.getLookVec();

    		double dist = 0.9;
    		double px = this.posX + look.xCoord * dist;
    		double py = this.posY + 0.25 + look.yCoord * dist;
    		double pz = this.posZ + look.zCoord * dist;

    		for (int i = 0; i < 2; i++)
    		{
    			double dx = look.xCoord;
    			double dy = look.yCoord;
    			double dz = look.zCoord;

    			double spread = 5 + this.getRNG().nextDouble() * 2.5;
    			double velocity = 0.15 + this.getRNG().nextDouble() * 0.15;

    			// spread flame
    			dx += this.getRNG().nextGaussian() * 0.007499999832361937D * spread;
    			dy += this.getRNG().nextGaussian() * 0.007499999832361937D * spread;
    			dz += this.getRNG().nextGaussian() * 0.007499999832361937D * spread;
    			dx *= velocity;
    			dy *= velocity;
    			dz *= velocity;

    			worldObj.spawnParticle(getFlameParticle(), px, py, pz, dx, dy, dz);
    		}
    		
			playBreathSound();
    	}

    }

    public String getFlameParticle() {
		return "flame";
	}

	public void playBreathSound() {
		worldObj.playSoundEffect(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "mob.ghast.fireball", rand.nextFloat() * 0.5F, rand.nextFloat() * 0.5F);
	}
    
	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float par1) {
		if (isBreathing())
		{
			return 15728880;
		}
		else
		{
			return super.getBrightnessForRender(par1);
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
	 * Rather than speed, this seems to control how far up or down the heads can tilt?
	 */
	@Override
    public int getVerticalFaceSpeed()
    {
        return 500;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() 
	{
		return 1.1F;
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

	/* (non-Javadoc)
	 * @see twilightforest.entity.IBreathAttacker#doBreathAttack(net.minecraft.entity.Entity)
	 */
	@Override
	public void doBreathAttack(Entity target) 
	{
		if (!target.isImmuneToFire() && target.attackEntityFrom(DamageSource.inFire, BREATH_DAMAGE))
    	{
    		target.setFire(BREATH_DURATION);
    	}
	}

}
