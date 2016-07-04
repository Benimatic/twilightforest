package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class EntityTFLoyalZombie extends EntityTameable {

	public EntityTFLoyalZombie(World par1World) {
        super(par1World);
        //this.texture = "/mob/zombie.png";
        //this.moveSpeed = 0.3F;
        this.setSize(0.6F, 1.8F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityMob.class, 0, true, false, null));

	}

	@Override
	public EntityAnimal createChild(EntityAgeable entityanimal)
	{
		return null;
	}

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(3.0D);
    }
	
	/**
	 * Rawr!
	 */
    @Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        int attackpower = 7;
        boolean success = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), attackpower);
        
        // throw some enemies around!
        if (success)
        {
            par1Entity.motionY += 0.2000000059604645D;
        }
        
        return success;
    }

    @Override
	public void onLivingUpdate()
    {
    	// once our damage boost effect wears out, start to burn
    	// the effect here is that we die shortly after our 60 second lifespan
        if (!this.worldObj.isRemote && this.getActivePotionEffect(MobEffects.STRENGTH) == null)
        {
            this.setFire(100);
        }

        super.onLivingUpdate();
    }

    @Override
	protected boolean canDespawn()
    {
        return !this.isTamed();
    }

    @Override
	protected String getAmbientSound()
    {
        return "mob.zombie.say";
    }

    @Override
	protected String getHurtSound()
    {
        return "mob.zombie.hurt";
    }

    @Override
	protected String getDeathSound()
    {
        return "mob.zombie.death";
    }

    @Override
	protected void playStepSound(BlockPos pos, Block par4)
    {
        this.worldObj.playSoundAtEntity(this, "mob.zombie.step", 0.15F, 1.0F);
    }

    @Override
	protected Item getDropItem()
    {
        return Item.getItemById(0);
    }

    @Override
	public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
}
