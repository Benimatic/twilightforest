package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TwilightForestMod;
import twilightforest.biomes.TFBiomeBase;

public class EntityTFMosquitoSwarm extends EntityMob {

	public EntityTFMosquitoSwarm(World par1World) {
		super(par1World);

        setSize(.7F, 1.9F);
        this.stepHeight = 2.1f;
        
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
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
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0D); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D); // movement speed
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D); // attack damage
    }
    

	
    @Override
	protected String getLivingSound()
    {
        return TwilightForestMod.ID + ":mob.mosquito.mosquito";
    }


    /**
     * Like cave spiders, but we add the hunger effect
     */
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        if (super.attackEntityAsMob(par1Entity))
        {
            if (par1Entity instanceof EntityLivingBase)
            {
                byte duration = 7;

                if (this.worldObj.difficultySetting != EnumDifficulty.EASY)
                {
                    if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL)
                    {
                        duration = 15;
                    }
                    else if (this.worldObj.difficultySetting == EnumDifficulty.HARD)
                    {
                        duration = 30;
                    }
                }

                if (duration > 0)
                {
                    ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.HUNGER.id, duration * 20, 0));
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    
	
	/**
	 * We're allowed to spawn in bright light only in swamps
	 */
	@Override
	public boolean getCanSpawnHere()
    {
		// are we in the swamp
		if (worldObj.getBiomeGenForCoords(MathHelper.floor_double(posX), MathHelper.floor_double(posZ)) == TFBiomeBase.tfSwamp) {
			// don't check light level
	        return worldObj.checkNoEntityCollision(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0;
		}
		else {
			// normal EntityMob spawn check, checks light level
			return super.getCanSpawnHere();
		}
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
	public int getMaxSpawnedInChunk()
    {
    	// we are solitary creatures
        return 1;
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

}
