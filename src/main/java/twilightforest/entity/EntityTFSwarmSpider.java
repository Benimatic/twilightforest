package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TwilightForestMod;
import twilightforest.TFFeature;


public class EntityTFSwarmSpider extends EntitySpider {
	
	protected boolean shouldSpawn = false;
	
	public EntityTFSwarmSpider(World world) {
		this(world, true);
	}
	
	public EntityTFSwarmSpider(World world, boolean spawnMore) {
		super(world);
		
		setSize(0.8F, 0.4F);
		setSpawnMore(spawnMore);
		//texture = TwilightForestMod.MODEL_DIR + "swarmspider.png";
		experienceValue = 2; // XP value
	}
	
    public EntityTFSwarmSpider(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x, y, z);
    }

	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(3.0D*4.0D+twilightforest.TwilightForestMod.Scatter.nextInt(4)-twilightforest.TwilightForestMod.Scatter.nextInt(4)); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.699999988079071D*1.5D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D*1.5); // attack damage
    }
	

    /**
     * How large the spider should be scaled.
     */
	//@Override
    public float spiderScaleAmount()
    {
        return 0.5F;
    }

	/**
	 * Actually only used for the shadow
	 */
	@Override
	public float getRenderSizeModifier() {
		 return 0.5F;
	}

	/**
	 * Spawn more if that flag is set.
	 */
	@Override
	public void onUpdate() {
		if (shouldSpawnMore()) {
			// don't spawn if we're connected in multiplayer 
			if (!worldObj.isRemote) {
				int more = 1 + rand.nextInt(2);
				for (int i = 0; i < more; i++) {
					// try twice to spawn
					if (!spawnAnother()) {
						spawnAnother();
					}
				}
			}
			setSpawnMore(false);
		}

		super.onUpdate();
	}
    
	/**
	 * Only have the spider do damage a fraction of the time
	 */
    protected void attackEntity(Entity entity, float f)
    {
    	if (this.attackTime <= 0 && (!this.isAirBorne || rand.nextInt(4) != 0))
        {
            this.attackTime = 20;
        }
    	else
    	{
    		super.attackEntity(entity, f);
    	}

    }
    
    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    @Override
	protected Entity findPlayerToAttack()
    {
    	// kill at all times!
    	double var2 = 16.0D;
    	return this.worldObj.getClosestVulnerablePlayerToEntity(this, var2);
    }

	/**
	 * Spawn another spider!
	 * 
	 * @return
	 */
	protected boolean spawnAnother() {
		EntityTFSwarmSpider another = new EntityTFSwarmSpider(worldObj, false);


		double sx = posX + (rand.nextBoolean() ? 0.9 : -0.9);
		double sy = posY;
		double sz = posZ + (rand.nextBoolean() ? 0.9 : -0.9);
		another.setLocationAndAngles(sx, sy, sz, rand.nextFloat() * 360F, 0.0F);
		if(!another.getCanSpawnHere())
		{
			another.setDead();
			return false;
		}
		worldObj.spawnEntityInWorld(another);
		
		return true;
	}

	/**
     * Checks to make sure the light is not too bright where the mob is spawning
	 */
	@Override
    protected boolean isValidLightLevel()
    {
		int chunkX = MathHelper.floor_double(posX) >> 4;
		int chunkZ = MathHelper.floor_double(posZ) >> 4;
		// We're allowed to spawn in bright light only in hedge mazes.
		if (TFFeature.getNearestFeature(chunkX, chunkZ, worldObj) == TFFeature.hedgeMaze) 
		{
			return true;
		}
		else
		{
			return super.isValidLightLevel();
		}
    }
	
    public boolean shouldSpawnMore()
    {
    	return shouldSpawn;
    }

    public void setSpawnMore(boolean flag)
    {
    	this.shouldSpawn = flag;
    }

    @Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("SpawnMore", shouldSpawnMore());
    }

    @Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setSpawnMore(nbttagcompound.getBoolean("SpawnMore"));
    }

    /**
     * Trigger achievement when killed
     */
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (worldObj.provider.dimensionId == TwilightForestMod.dimensionID && par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).triggerAchievement(TFAchievementPage.twilightHunter);
			// are we in a hedge maze?
			int chunkX = MathHelper.floor_double(posX) >> 4;
			int chunkZ = MathHelper.floor_double(posZ) >> 4;
			if (TFFeature.getNearestFeature(chunkX, chunkZ, worldObj) == TFFeature.hedgeMaze) {
				// award hedge maze cheevo
				((EntityPlayer)par1DamageSource.getSourceOfDamage()).triggerAchievement(TFAchievementPage.twilightHedge);
			}
		}
	}
	
    /**
     * Gets the pitch of living sounds in living entities.
     */
    @Override
	protected float getSoundPitch()
    {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F;
    }
	
    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 16;
    }

}
