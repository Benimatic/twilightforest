package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.entity.ai.EntityAITFBreathAttack;
import twilightforest.item.TFItems;

public class EntityTFWinterWolf extends EntityTFHostileWolf  implements IBreathAttacker {

	private static final DataParameter<Boolean> BREATH_FLAG = EntityDataManager.createKey(EntityTFWinterWolf.class, DataSerializers.BOOLEAN);

	public EntityTFWinterWolf(World world) {
		super(world);
        this.setSize(1.4F, 1.9F);
        
        this.tasks.taskEntries.clear();
        this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAITFBreathAttack(this, 1.0F, 5F, 30, 0.1F));
        this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0F, false));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0F));
 
        this.targetTasks.taskEntries.clear();
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true, false, null));
	}

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
    }

	@Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(BREATH_FLAG, false);
    }

    /**
     * Returns the amount of damage a mob should deal.
     */
    public int getAttackStrength(Entity par1Entity)
    {
        return 6;
    }
    
    @Override
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
    		Vec3d look = this.getLookVec();

    		double dist = 0.5;
    		double px = this.posX + look.xCoord * dist;
    		double py = this.posY + 1.25 + look.yCoord * dist;
    		double pz = this.posZ + look.zCoord * dist;

    		for (int i = 0; i < 10; i++)
    		{
    			double dx = look.xCoord;
    			double dy = look.yCoord;
    			double dz = look.zCoord;

    			double spread = 5 + this.getRNG().nextDouble() * 2.5;
    			double velocity = 3.0 + this.getRNG().nextDouble() * 0.15;

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
    
	private void playBreathSound() {
		worldObj.playSoundEffect(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "mob.ghast.fireball", rand.nextFloat() * 0.5F, rand.nextFloat() * 0.5F);
	}

    @Override
	protected float getSoundPitch()
    {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.6F;
    }

	@Override
	public boolean isBreathing() {
        return this.dataManager.get(BREATH_FLAG);

	}

	@Override
	public void setBreathing(boolean flag) {
        this.dataManager.set(BREATH_FLAG, flag);
	}

	@Override
	public void doBreathAttack(Entity target) {
//		if (!target.isImmuneToFire() && target.attackEntityFrom(DamageSource.inFire, BREATH_DAMAGE))
//    	{
//    		target.setFire(BREATH_DURATION);
//    	}
	}

	@Override
	protected boolean isValidLightLevel() {
		if (worldObj.getBiomeGenForCoords(new BlockPos(this)) == TFBiomeBase.tfSnow) {
			return true;
		} else {
			return super.isValidLightLevel();
		}
	}
	
	@Override
    protected Item getDropItem()
    {
        return TFItems.arcticFur;
    }

}
