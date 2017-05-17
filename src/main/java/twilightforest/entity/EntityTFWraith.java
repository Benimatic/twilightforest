package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.TFSounds;
import twilightforest.TwilightForestMod;


public class EntityTFWraith extends EntityFlying implements IMob {

    public EntityTFWraith(World world)
    {
        super(world);
    }
    
    public EntityTFWraith(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x, y, z);
    }
    
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        
        // need to initialize damage since we're not an EntityMob
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    }

    @Override
	public void onLivingUpdate()
    {
        if(world.isDaytime())
        {
            float f = getBrightness(1.0F);
            if(f > 0.5F && world.canSeeSky(new BlockPos(this)) && rand.nextFloat() * 30F < (f - 0.4F) * 2.0F)
            {
//                fire = 300;
            }
        }
        super.onLivingUpdate();
    }
    
    @Override
    public boolean canTriggerWalking()
    {
        return false;
    }
    
    /**
     * Adapted from the ghast code.  The ghost wanders randomly until it targets a player, at which point it moves towards the player.
     * 
     * TODO: pathfinding!
     */
    @Override
	protected void updateAITasks()
    {
        if(!world.isRemote && world.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
        	setDead();
        }
        despawnEntity();
        prevAttackCounter = attackCounter;
        double d = waypointX - posX;
        double d1 = waypointY - posY;
        double d2 = waypointZ - posZ;
        double d3 = MathHelper.sqrt(d * d + d1 * d1 + d2 * d2);
        if(d3 < 1.0D || d3 > 60D)
        {
            waypointX = posX + (rand.nextFloat() * 2.0F - 1.0F) * 16F;
            waypointY = posY + (rand.nextFloat() * 2.0F - 1.0F) * 16F;
            waypointZ = posZ + (rand.nextFloat() * 2.0F - 1.0F) * 16F;
        }
        if(courseChangeCooldown-- <= 0)
        {
            courseChangeCooldown += rand.nextInt(5) + 2;
            if(isCourseTraversable(waypointX, waypointY, waypointZ, d3))
            {
                motionX += (d / d3) * 0.10000000000000001D;
                motionY += (d1 / d3) * 0.10000000000000001D;
                motionZ += (d2 / d3) * 0.10000000000000001D;
            } else
            {
                waypointX = posX;
                waypointY = posY;
                waypointZ = posZ;
                
                // clear target?
                targetedEntity = null;
            }
        }
        if(targetedEntity != null && targetedEntity.isDead)
        {
            targetedEntity = null;
        }
        if(targetedEntity == null || aggroCooldown-- <= 0)
        {
            targetedEntity = findPlayerToAttack();
            if(targetedEntity != null)
            {
                aggroCooldown = 20;
            }
        } else {
             float f1 = targetedEntity.getDistanceToEntity(this);
             if(getEntitySenses().canSee(targetedEntity))
             {
                 attackEntity(targetedEntity, f1);
             } else
             {
                 attackBlockedEntity(targetedEntity, f1);
             }
        }
        double d4 = 64D;
        if(targetedEntity != null && targetedEntity.getDistanceSqToEntity(this) < d4 * d4)
        {
            double d5 = targetedEntity.posX - posX;
            //double d6 = (targetedEntity.boundingBox.minY + (double)(targetedEntity.height / 2.0F)) - (posY + height / 2.0F);
            double d7 = targetedEntity.posZ - posZ;
            renderYawOffset = rotationYaw = (-(float)Math.atan2(d5, d7) * 180F) / 3.141593F;
            if(getEntitySenses().canSee(targetedEntity))
            {
                if(attackCounter == 10)
                {
                    //world.playSoundAtEntity(this, "mob.ghast.charge", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                }
                attackCounter++;
                if(attackCounter == 20)
                {
                    waypointX = targetedEntity.posX;
                    waypointY = targetedEntity.posY - targetedEntity.height + 0.5;
                    waypointZ = targetedEntity.posZ;
                	
                	
                    attackCounter = -40;
                }
            } else
            if(attackCounter > 0)
            {
                attackCounter--;
            }
        } else
        {
            renderYawOffset = rotationYaw = (-(float)Math.atan2(motionX, motionZ) * 180F) / 3.141593F;
            if(attackCounter > 0)
            {
                attackCounter--;
            }
        }
    }
    
    protected void attackEntity(Entity entity, float f)
    {
        if(attackTime <= 0 && f < 2.0F && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY)
        {
            attackTime = 20;
            
            float damage = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getBaseValue();

            entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
        }
    }
    
    protected void attackBlockedEntity(Entity entity, float f)
    {
    }

    
    /**
     * Adapted from EntityMob
     * @return
     */
 
    @Override
	public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        if(super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();
            if(getRidingEntity() == entity || getPassengers().contains(entity))
            {
                return true;
            }
            if(entity != this)
            {
                targetedEntity = entity;
            }
            return true;
        } else
        {
            return false;
        }
    }
    
    /**
     * Copied from EntityMob
     * @return
     */
    protected Entity findPlayerToAttack()
    {
        EntityPlayer entityplayer = world.getClosestVulnerablePlayerToEntity(this, 16D);
        if(entityplayer != null && getEntitySenses().canSee(entityplayer))
        {
            return entityplayer;
        } else
        {
            return null;
        }
    }

    private boolean isCourseTraversable(double d, double d1, double d2, double d3)
    {
        double d4 = (waypointX - posX) / d3;
        double d5 = (waypointY - posY) / d3;
        double d6 = (waypointZ - posZ) / d3;
        AxisAlignedBB axisalignedbb = boundingBox.copy();
        for(int i = 1; i < d3; i++)
        {
            axisalignedbb.offset(d4, d5, d6);
            if(world.getCollidingBoundingBoxes(this, axisalignedbb).size() > 0)
            {
                return false;
            }
        }

        return true;
    }

    @Override
	protected SoundEvent getAmbientSound()
    {
        return TFSounds.WRAITH;
    }

    @Override
	protected SoundEvent getHurtSound()
    {
        return TFSounds.WRAITH;
    }

    @Override
	protected SoundEvent getDeathSound()
    {
        return TFSounds.WRAITH;
    }

    @Override
	protected Item getDropItem()
    {
        return Items.GLOWSTONE_DUST;
    }
    
    public int courseChangeCooldown;
    public double waypointX;
    public double waypointY;
    public double waypointZ;
    private Entity targetedEntity;
    private int aggroCooldown;
    public int prevAttackCounter;
    public int attackCounter;
    
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
			// are we in a level 3 hill?
			int chunkX = MathHelper.floor(posX) >> 4;
			int chunkZ = MathHelper.floor(posZ) >> 4;
			if (TFFeature.getNearestFeature(chunkX, chunkZ, world) == TFFeature.hill3) {
				// award level 3 hill cheevo
				((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHill3);
			}
		}
	}
	
    // [VanillaCopy] Direct copy of EntityMob.isValidLightLevel
    protected boolean isValidLightLevel()
    {
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

        if (this.world.getLightFor(EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32))
        {
            return false;
        }
        else
        {
            int i = this.world.getLightFromNeighbors(blockpos);

            if (this.world.isThundering())
            {
                int j = this.world.getSkylightSubtracted();
                this.world.setSkylightSubtracted(10);
                i = this.world.getLightFromNeighbors(blockpos);
                this.world.setSkylightSubtracted(j);
            }

            return i <= this.rand.nextInt(8);
        }
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.isValidLightLevel() && super.getCanSpawnHere();
    }
}
