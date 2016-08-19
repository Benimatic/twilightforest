package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.entity.ai.EntityAITFBreathAttack;
import twilightforest.item.TFItems;

public class EntityTFWinterWolf extends EntityTFHostileWolf  implements IBreathAttacker {

	private static final int BREATH_FLAG = 21;
    public static final int BREATH_DURATION = 10;
    public static final int BREATH_DAMAGE = 2;

	public EntityTFWinterWolf(World world) {
		super(world);
        this.setSize(1.4F, 1.9F);
        
        this.tasks.taskEntries.clear();
        this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAITFBreathAttack(this, 1.0F, 5F, 30, 0.1F));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0F, false));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0F));
 
        this.targetTasks.taskEntries.clear();
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D); // max health
    }
	
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(BREATH_FLAG, (byte)0);
    }

    /**
     * Returns the amount of damage a mob should deal.
     */
    public int getAttackStrength(Entity par1Entity)
    {
        return 6;
    }
    
    public boolean attackEntityAsMob(Entity par1Entity)
    {
    	int damage = this.getAttackStrength(par1Entity);
        if (par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage))
        {
            return true;
        }
        else
        {
            return false;
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
    		Vec3 look = this.getLookVec();

    		double dist = 0.5;
    		double px = this.posX + look.xCoord * dist;
    		double py = this.posY + 1.25 + look.yCoord * dist;
    		double pz = this.posZ + look.zCoord * dist;

    		for (int i = 0; i < 10; i++)
    		{
    			double dx = look.xCoord;
    			double dy = look.yCoord;
    			double dz = look.zCoord;

    			double spread = 5 + this.getRNG().nextFloat() * 2.5;
    			double velocity = 3.0 + this.getRNG().nextFloat() * 0.15;

    			// spread flame
    			dx += this.getRNG().nextGaussian() * 0.007499999832361937D * spread;
    			dy += this.getRNG().nextGaussian() * 0.007499999832361937D * spread;
    			dz += this.getRNG().nextGaussian() * 0.007499999832361937D * spread;
    			dx *= velocity;
    			dy *= velocity;
    			dz *= velocity;

    			TwilightForestMod.proxy.spawnParticle(this.worldObj, "snowstuff", px, py, pz, dx, dy, dz);
    			//worldObj.spawnParticle(getFlameParticle(), px, py, pz, dx, dy, dz);
    		}
    		
			playBreathSound();
    	}

    }
    
	public void playBreathSound() {
		worldObj.playSoundEffect(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "mob.ghast.fireball", rand.nextFloat() * 0.5F, rand.nextFloat() * 0.5F);
	}

	/**
     * Gets the pitch of living sounds in living entities.
     */
    protected float getSoundPitch()
    {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.6F;
    }

	@Override
	public boolean isBreathing() {
        return this.getDataWatcher().getWatchableObjectByte(BREATH_FLAG) == 1;

	}

	@Override
	public void setBreathing(boolean flag) {
        this.getDataWatcher().updateObject(BREATH_FLAG, ((byte)(flag ? 1 : 0)));
	}

	@Override
	public void doBreathAttack(Entity target) {
//		if (!target.isImmuneToFire() && target.attackEntityFrom(DamageSource.inFire, BREATH_DAMAGE))
//    	{
//    		target.setFire(BREATH_DURATION);
//    	}
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
