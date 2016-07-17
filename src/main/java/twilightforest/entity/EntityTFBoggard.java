package twilightforest.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.TFSounds;
import twilightforest.TwilightForestMod;
import twilightforest.entity.ai.EntityAITFChargeAttack;
import twilightforest.item.TFItems;


public class EntityTFBoggard extends EntityMob {
	private boolean shy;

    public EntityTFBoggard(World world)
    {
        super(world);
        //texture = "/mob/pigzombie.png";
        setSize(0.8F, 1.1F);

        shy = true;
    }
    
    public EntityTFBoggard(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x, y, z);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
//        this.tasks.addTask(1, new EntityAITFRedcapShy(this, this.moveSpeed));
        this.tasks.addTask(2, new EntityAITFChargeAttack(this, 2.0F));
        this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0F, false));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, false, false, null));
    }

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(14.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
	protected SoundEvent getAmbientSound()
    {
        return TFSounds.REDCAP_AMBIENT;
    }

    @Override
	protected SoundEvent getHurtSound()
    {
        return TFSounds.REDCAP_HURT;
    }

    @Override
	protected SoundEvent getDeathSound()
    {
        return TFSounds.REDCAP_AMBIENT;
    }

    @Override
	protected Item getDropItem()
    {
        return Items.IRON_BOOTS;
    }
    
    @Override
    protected void dropFewItems(boolean flag, int i)
    {
        if (rand.nextInt(5) == 0)
        {
            this.dropItem(TFItems.mazeMapFocus, 1 + i);
        }
        if (rand.nextInt(6) == 0)
        {
            this.dropItem(Items.IRON_BOOTS, 1 + i);
        }
        if (rand.nextInt(9) == 0)
        {
        	this.dropItem(Items.IRON_PICKAXE, 1 + i);
        }
    }

    public boolean isShy() {
    	return shy && this.recentlyHit <= 0;
    }
    
    /**
     * Fairly straightforward.  Returns true in a 120 degree arc in front of the player's view.
     * @return
     */
    public boolean isTargetLookingAtMe() {
    	// find angle of approach
    	double dx = posX - getAttackTarget().posX;
    	double dz = posZ - getAttackTarget().posZ;
    	float angle = (float)((Math.atan2(dz, dx) * 180D) / 3.1415927410125732D) - 90F;

    	float difference = MathHelper.abs((getAttackTarget().rotationYaw - angle) % 360);
    	
//    	System.out.println("Difference in angle of approach is " + difference);

    	return difference < 60 || difference > 300;
    }
    
  
    
//    @Override
//	public boolean attackEntityFrom(Entity entity, int i) {
//    	
//    	if (entityToAttack != null && entityToAttack == entity) {
//    		shy = false;
//    		// possibly notify others?
//    	}
//    	
//		return super.attackEntityFrom(entity, i);
//	}

    /**
     * Trigger achievement when killed
     */
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
			// are we in a level 1 hill?
			int chunkX = MathHelper.floor_double(posX) >> 4;
			int chunkZ = MathHelper.floor_double(posZ) >> 4;
			if (TFFeature.getNearestFeature(chunkX, chunkZ, worldObj) == TFFeature.hill1) {
				// award level 1 hill cheevo
				((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHill1);
			}

		}
	}

	@Override
	public void moveEntityWithHeading(float par1, float par2) {
		super.moveEntityWithHeading(par1, par2);
		
//		System.out.println("prevLegYaw = " + this.prevLimbYaw);
//		System.out.println("legYaw = " + this.limbYaw);

	}
}
