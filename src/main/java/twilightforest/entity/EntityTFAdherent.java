package twilightforest.entity;

import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import twilightforest.TFAchievementPage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;


public class EntityTFAdherent  extends EntityMob implements IRangedAttackMob, ITFCharger {

	private static final DataParameter<Boolean> CHARGE_FLAG = EntityDataManager.createKey(EntityTFAdherent.class, DataSerializers.BOOLEAN);

	public EntityTFAdherent(World world) {
		super(world);
		this.setSize(0.8F, 2.2F);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIRestrictSun(this));
		this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
		this.tasks.addTask(4, new EntityAIAttackRanged(this, 1.0D, 60, 10.0F));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, false, null));
	}
	
	@Override
    protected void entityInit()
    {
        super.entityInit();
        dataManager.register(CHARGE_FLAG, false);
    }
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
		}
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase attackTarget, float extraDamage) {
		EntityTFNatureBolt natureBolt = new EntityTFNatureBolt(this.world, this);
		playSound(SoundEvents.ENTITY_GHAST_SHOOT, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
		
		double tx = attackTarget.posX - this.posX;
		double ty = attackTarget.posY + attackTarget.getEyeHeight() - 2.699999988079071D - this.posY;
		double tz = attackTarget.posZ - this.posZ;
		float heightOffset = MathHelper.sqrt(tx * tx + tz * tz) * 0.2F;
		natureBolt.setThrowableHeading(tx, ty + heightOffset, tz, 0.6F, 6.0F);
		this.world.spawnEntity(natureBolt);

	}
	
    /**
     * In this case we're not charging like a bull, but charging up a ranged attack
     */
    @Override
	public boolean isCharging()
    {
        return dataManager.get(CHARGE_FLAG);
    }

    @Override
	public void setCharging(boolean flag)
    {
		dataManager.set(CHARGE_FLAG, flag);
    }
    

}
