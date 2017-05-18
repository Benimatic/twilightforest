package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import twilightforest.TwilightForestMod;

public class EntityTFSlimeBeetle extends EntityMob implements IRangedAttackMob
{
    public static final ResourceLocation LOOT_TABLE = new ResourceLocation(TwilightForestMod.ID, "entities/slime_beetle");

	public EntityTFSlimeBeetle(World world) {
		super(world);
		setSize(0.9F, 1.75F);
	}

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        //this.tasks.addTask(2, new EntityAITFFireBreath(this, this.moveSpeed, 5F, 30, 0.1F));
        this.tasks.addTask(2, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 3.0F, 1.25F, 2.0F));
        this.tasks.addTask(3, new EntityAIAttackRanged(this, 1, 30, 10));
        //this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, false, null));
    }

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4);
    }

    @Override
	protected SoundEvent getHurtSound()
    {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }

    @Override
	protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SPIDER_DEATH;
    }

    @Override
	protected void playStepSound(BlockPos pos, Block var4)
    {
        playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
    }

    @Override
    public void onDeath(DamageSource par1DamageSource) {
    	super.onDeath(par1DamageSource);
    	if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
    		((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
    	}
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

    @Override
	public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    public ResourceLocation getLootTable() {
        return LOOT_TABLE;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float p_82196_2_) {
        EntityThrowable projectile = new EntityTFSlimeProjectile(this.world, this);
        playSound(SoundEvents.ENTITY_SMALL_SLIME_SQUISH, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        double tx = target.posX - this.posX;
        double ty = target.posY + target.getEyeHeight() - 1.100000023841858D - projectile.posY;
        double tz = target.posZ - this.posZ;
        float heightOffset = MathHelper.sqrt(tx * tx + tz * tz) * 0.2F;
        projectile.setThrowableHeading(tx, ty + heightOffset, tz, 0.6F, 6.0F);
        this.world.spawnEntity(projectile);
    }
}
