package twilightforest.entity.boss;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.entity.IBreathAttacker;
import twilightforest.entity.ai.EntityAITFHoverBeam;
import twilightforest.entity.ai.EntityAITFHoverSummon;
import twilightforest.entity.ai.EntityAITFHoverThenDrop;
import twilightforest.item.TFItems;
import twilightforest.world.ChunkProviderTwilightForest;
import twilightforest.world.TFWorldChunkManager;
import twilightforest.world.WorldProviderTwilightForest;

public class EntityTFSnowQueen extends EntityMob implements IBossDisplayData, IEntityMultiPart, IBreathAttacker {
	
	private static final int MAX_SUMMONS = 6;
	private static final int BEAM_FLAG = 21;
	private static final int PHASE_FLAG = 22;
	private static final int MAX_DAMAGE_WHILE_BEAMING = 25;
	private static final float BREATH_DAMAGE = 4.0F;


	public enum Phase { SUMMON, DROP, BEAM };

	public Entity[] iceArray;
	
	private int summonsRemaining = 0;
	private int successfulDrops;
	private int maxDrops;
	private int damageWhileBeaming;
	
	public EntityTFSnowQueen(World par1World) {
		super(par1World);
		
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAITFHoverSummon(this, EntityPlayer.class, 1.0D));
        this.tasks.addTask(2, new EntityAITFHoverThenDrop(this, EntityPlayer.class, 80, 20));
        this.tasks.addTask(3, new EntityAITFHoverBeam(this, EntityPlayer.class, 80, 100));
        this.tasks.addTask(6, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, true));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.setSize(0.7F, 2.2F);
        
        this.iceArray = new Entity[7];
        for (int i = 0; i < this.iceArray.length; i++) {
        	this.iceArray[i] = new EntityTFSnowQueenIceShield(this);
        }
        
        this.setCurrentPhase(Phase.SUMMON);
        
        this.isImmuneToFire = true;
        this.experienceValue = 317;

	}

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return false;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(BEAM_FLAG, Byte.valueOf((byte)0));
        this.dataWatcher.addObject(PHASE_FLAG, Byte.valueOf((byte)0));
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean isAIEnabled()
    {
        return true;
    }
    
    @Override
    protected String getLivingSound()
    {
    	return TwilightForestMod.ID + ":mob.ice.noise";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
    	return TwilightForestMod.ID + ":mob.ice.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
    	return TwilightForestMod.ID + ":mob.ice.death";
    }
    
    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected Item getDropItem()
    {
        return Items.SNOWBALL;
    }
    
    /**
     * Enchants the entity's armor and held item based on difficulty
     */
    protected void enchantEquipment()
    {
        super.enchantEquipment();
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
    {
    	IEntityLivingData data = super.onSpawnWithEgg(par1EntityLivingData);
        
        return data;

    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	// make snow particles
    	for (int i = 0; i < 3; i++) {
	    	float px = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F;
	    	float py = this.getEyeHeight() + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F;
	    	float pz = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F;
	    	
			TwilightForestMod.proxy.spawnParticle(this.worldObj, "snowguardian", this.lastTickPosX + px, this.lastTickPosY + py, this.lastTickPosZ + pz, 0, 0, 0);
    	}
    	
    	// during drop phase, all the ice blocks should make particles
    	if (this.getCurrentPhase() == Phase.DROP) {
    		for (int i = 0; i < this.iceArray.length; i++) {
    			float px = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F;
    			float py = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F;
    			float pz = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F;

    			TwilightForestMod.proxy.spawnParticle(this.worldObj, "snowwarning", this.iceArray[i].lastTickPosX + px, this.iceArray[i].lastTickPosY + py, this.iceArray[i].lastTickPosZ + pz, 0, 0, 0);
    		}
    	}
    	
    	// when ice beaming, spew particles
    	if (isBreathing() && this.isEntityAlive())
    	{
    		Vec3 look = this.getLookVec();

    		double dist = 0.5;
    		double px = this.posX + look.xCoord * dist;
    		double py = this.posY + 1.7F + look.yCoord * dist;
    		double pz = this.posZ + look.zCoord * dist;

    		for (int i = 0; i < 10; i++)
    		{
    			double dx = look.xCoord;
    			double dy = 0;//look.yCoord;
    			double dz = look.zCoord;

    			double spread = 2 + this.getRNG().nextDouble() * 2.5;
    			double velocity = 2.0 + this.getRNG().nextDouble() * 0.15;

    			// beeeam
    			dx += this.getRNG().nextGaussian() * 0.0075D * spread;
    			dy += this.getRNG().nextGaussian() * 0.0075D * spread;
    			dz += this.getRNG().nextGaussian() * 0.0075D * spread;
    			dx *= velocity;
    			dy *= velocity;
    			dz *= velocity;

    			TwilightForestMod.proxy.spawnParticle(this.worldObj, "icebeam", px, py, pz, dx, dy, dz);
    			//worldObj.spawnParticle(getFlameParticle(), px, py, pz, dx, dy, dz);
    		}
    		
			//playBreathSound();
    	}

//    	// am I in a block?!?
//    	int fx = MathHelper.floor_double(this.posX);
//    	int fy = MathHelper.floor_double(this.posY);
//    	int fz = MathHelper.floor_double(this.posZ);
//    	
//    	if (this.worldObj.getBlock(fx, fy, fz) != Blocks.AIR) {
//    		System.out.println("I am in a block!  World =" + this.worldObj);
//    		this.posY += 1D;
//    	}

    }
    
    /**
     * Keep ice shield position updated
     */
    @Override
	public void onUpdate() {
		
		super.onUpdate();
		
		for (int i = 0; i < this.iceArray.length; i++) {
			
			this.iceArray[i].onUpdate();
			
			if (i < this.iceArray.length - 1) {
		        // set block position
				Vec3 blockPos = this.getIceShieldPosition(i);
				
				//System.out.println("Got position for block " + i + " and it is" + blockPos);
				
				this.iceArray[i].setPosition(blockPos.xCoord, blockPos.yCoord, blockPos.zCoord);
				this.iceArray[i].rotationYaw = this.getIceShieldAngle(i);
			} else {
				// last block beneath
				this.iceArray[i].setPosition(this.posX, this.posY - 1, this.posZ);
				this.iceArray[i].rotationYaw = this.getIceShieldAngle(i);			
			}
			
			// collide things with the block
	        if (!worldObj.isRemote) {
	        	this.applyShieldCollisions(this.iceArray[i]);
	        }
		}
		
		// death animation
		if (deathTime > 0) {
            for(int k = 0; k < 5; k++)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                String explosionType = rand.nextBoolean() ?  "hugeexplosion" : "explode";
                
                worldObj.spawnParticle(explosionType, (posX + rand.nextFloat() * width * 2.0F) - width, posY + rand.nextFloat() * height, (posZ + rand.nextFloat() * width * 2.0F) - width, d, d1, d2);
            }
		}
    }

    
	@Override
	protected void dropFewItems(boolean par1, int par2) {
    	dropBow();

        // ice cubes
        int totalDrops = this.rand.nextInt(4 + par2) + 1;
        for (int i = 0; i < totalDrops; ++i)
        {
            this.dropItem(Item.getItemFromBlock(Blocks.PACKED_ICE), 7);
        }

        // snowballs
        totalDrops = this.rand.nextInt(5 + par2) + 5;
        for (int i = 0; i < totalDrops; ++i)
        {
            this.dropItem(Items.SNOWBALL, 16);
        }
        
        // trophy
        this.entityDropItem(new ItemStack(TFItems.trophy, 1, 4), 0);
	}
	
	private void dropBow() {
		int bowType = rand.nextInt(2);
		if (bowType == 0) {
			this.entityDropItem(new ItemStack(TFItems.tripleBow), 0);
		} else {
			this.entityDropItem(new ItemStack(TFItems.seekerBow), 0);
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
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).triggerAchievement(TFAchievementPage.twilightProgressGlacier);
			
		}

		// mark the tower as defeated
		if (!worldObj.isRemote) {
			int dx = MathHelper.floor_double(this.posX);
			int dy = MathHelper.floor_double(this.posY);
			int dz = MathHelper.floor_double(this.posZ);
			
			if (worldObj.provider instanceof WorldProviderTwilightForest){
				ChunkProviderTwilightForest chunkProvider = ((WorldProviderTwilightForest)worldObj.provider).getChunkProvider();
				TFFeature nearbyFeature = ((TFWorldChunkManager)worldObj.provider.worldChunkMgr).getFeatureAt(dx, dz, worldObj);

				if (nearbyFeature == TFFeature.lichTower) {
					chunkProvider.setStructureConquered(dx, dy, dz, true);
				}
			}
		}
	}
    
    @SuppressWarnings("unchecked")
	private void applyShieldCollisions(Entity collider) {
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(collider, collider.boundingBox.expand(-0.2F, -0.2F, -0.2F));
        
        for (Entity collided : list) {
        	
        	if (collided.canBePushed()) {
                applyShieldCollision(collider, collided);
            }
        }
	}
    
    /**
     * Do the effect where the shield hits something
     */
    protected void applyShieldCollision(Entity collider, Entity collided)
    {
		if (collided != this) {
	    	collided.applyEntityCollision(collider);
			if (collided instanceof EntityLivingBase) {
				//FMLLog.info("Spike ball collided with entity %s", collided);
				
				// do hit and throw
		        boolean attackSuccess = super.attackEntityAsMob(collided);

		        if (attackSuccess) {
		        	collided.motionY += 0.4000000059604645D;
			        this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
		            
		            //System.out.println("Spike ball attack success");

		        }

			}
		}
    }



	protected void updateAITasks() {
        super.updateAITasks();
        
        // switch phases
        if (this.getCurrentPhase() == Phase.SUMMON && this.getSummonsRemaining() == 0 && this.countMyMinions() <= 0) {
        	this.setCurrentPhase(Phase.DROP);
        }
        if (this.getCurrentPhase() == Phase.DROP && this.successfulDrops >= this.maxDrops) {
        	this.setCurrentPhase(Phase.BEAM);
        }
        if (this.getCurrentPhase() == Phase.BEAM && this.damageWhileBeaming >= MAX_DAMAGE_WHILE_BEAMING) {
        	this.setCurrentPhase(Phase.SUMMON);
        }
    }
    
    /**
     * Called when we get attacked.
     */
    @Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float damage) {
    	boolean result = super.attackEntityFrom(par1DamageSource, damage);
    	
    	if (result && this.getCurrentPhase() == Phase.BEAM) {
    		this.damageWhileBeaming += damage;
    	}
    	
		return result;
    	
    }
    
	
    private Vec3 getIceShieldPosition(int i) {
    	return this.getIceShieldPosition(getIceShieldAngle(i), 1F);
	}


	private float getIceShieldAngle(int i) {
		//System.out.println("Getting angle for shield " + i + " and it is " + (((float)Math.PI / 3F) * (float)i));

		
		return 60F * i + (this.ticksExisted * 5F);
		
	}


	/**
     * Get the ice shield position
     */
    public Vec3 getIceShieldPosition(float angle, float distance)
    {
		double var1 = Math.cos((angle) * Math.PI / 180.0D) * distance;
		double var3 = Math.sin((angle) * Math.PI / 180.0D) * distance;

		return Vec3.createVectorHelper(this.posX + var1, this.posY + this.getShieldYOffset(), this.posZ + var3);
    }
    
    
    /**
     * How high is the shield
     */
	public double getShieldYOffset()
    {
        return 0.1F;
    }
    
    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
	@Override
	protected void fall(float par1) {
		; // no falling
	}


	@Override
	public World func_82194_d() {
		return this.worldObj;
	}

	@Override
	public boolean attackEntityFromPart(EntityDragonPart entitydragonpart, DamageSource damagesource, float i) {
		return false;
	}
	
    
    /**
     * We need to do this for the bounding boxes on the parts to become active
     */
    @Override
    public Entity[] getParts() {
        return iceArray;
    }
    
	/**
     * Destroys all ice related blocks in the AABB
     */
    public boolean destroyBlocksInAABB(AxisAlignedBB par1AxisAlignedBB) {
    	//System.out.println("Destroying blocks in " + par1AxisAlignedBB);
    	
        int minX = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int minY = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int minZ = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int maxX = MathHelper.floor_double(par1AxisAlignedBB.maxX);
        int maxY = MathHelper.floor_double(par1AxisAlignedBB.maxY);
        int maxZ = MathHelper.floor_double(par1AxisAlignedBB.maxZ);
        boolean wasBlocked = false;
        for (int dx = minX; dx <= maxX; ++dx) {
            for (int dy = minY; dy <= maxY; ++dy) {
                for (int dz = minZ; dz <= maxZ; ++dz) {
                    Block block = this.worldObj.getBlock(dx, dy, dz);
                    
                    if (block != Blocks.AIR) {
                    	int currentMeta = this.worldObj.getBlockMetadata(dx, dy, dz);
                    	
                    	if (block == Blocks.ICE || block == Blocks.PACKED_ICE) {
                            this.worldObj.setBlock(dx, dy, dz, Blocks.AIR, 0, 2);
                            
                            // here, this effect will have to do
                			worldObj.playAuxSFX(2001, dx, dy, dz, Block.getIdFromBlock(block) + (currentMeta << 12));
                        } else {
                            wasBlocked = true;
                        }
                    }
                }
            }
        }

        return wasBlocked;
    }

	public boolean isBreathing() {
        return this.getDataWatcher().getWatchableObjectByte(BEAM_FLAG) == 1;

	}

	public void setBreathing(boolean flag) {
        this.getDataWatcher().updateObject(BEAM_FLAG, Byte.valueOf((byte)(flag ? 1 : 0)));
	}


	public Phase getCurrentPhase() {
		return Phase.values()[this.getDataWatcher().getWatchableObjectByte(PHASE_FLAG)];
	}


	public void setCurrentPhase(Phase currentPhase) {
		this.getDataWatcher().updateObject(PHASE_FLAG, Byte.valueOf((byte) currentPhase.ordinal()));
		
		// set variables for current phase
		if (currentPhase == Phase.SUMMON) {
			this.setSummonsRemaining(MAX_SUMMONS);
		}
		if (currentPhase == Phase.DROP) {
			this.successfulDrops = 0;
			this.maxDrops = 2 + this.rand.nextInt(3);
		}
		if (currentPhase == Phase.BEAM) {
			this.damageWhileBeaming = 0;
		}
	}


	public int getSummonsRemaining() {
		return summonsRemaining;
	}


	public void setSummonsRemaining(int summonsRemaining) {
		this.summonsRemaining = summonsRemaining;
	}
	
	public void summonMinionAt(EntityLivingBase targetedEntity) {
		
		// find a good spot
		Vec3 minionSpot = findVecInLOSOf(targetedEntity);
		
		// put a minion there
		EntityTFIceCrystal minion = new EntityTFIceCrystal(worldObj);
		minion.setPosition(minionSpot.xCoord, minionSpot.yCoord, minionSpot.zCoord);
		worldObj.spawnEntityInWorld(minion);
		
		minion.setAttackTarget(targetedEntity);
		minion.setToDieIn30Seconds(); // don't stick around
		
		// reduce summons
		this.summonsRemaining--;
	}


    /**
     * Returns coords that would be good to teleport to.
     * 
     * Returns null if we can't find anything
     */
    protected Vec3 findVecInLOSOf(Entity targetEntity) 
    {
    	// for some reason we occasionally get null here
    	if (targetEntity == null)
    	{
    		return null;
    	}
    	
        double tx = 0, ty = 0, tz = 0;
        int tries = 100;
        for (int i = 0; i < tries; i++) {
        	tx = targetEntity.posX + rand.nextGaussian() * 16D;
        	ty = targetEntity.posY + rand.nextGaussian() * 8D;
        	tz = targetEntity.posZ + rand.nextGaussian() * 16D;
        	
        	// put the y on something solid
        	boolean groundFlag = false;
        	// we need to get the integer coordinates for this calculation
        	int bx = MathHelper.floor_double(tx);
        	int by = MathHelper.floor_double(ty);
        	int bz = MathHelper.floor_double(tz);
        	while (!groundFlag && ty > 0) 
        	{
                Block whatsThere = worldObj.getBlock(bx, by - 1, bz);
                if (whatsThere == Blocks.AIR || !whatsThere.getMaterial().isSolid())
                {
                    ty--;
                    by--;
                }
                else
                {
                	groundFlag = true;
                }
        	}
        	
        	// did we not find anything at all to stand on?
        	if (by == 0) {
//        		System.out.println("teleport find failed to find a block to stand on");
        		continue;
        	}
        	
        	// 
        	if (!canEntitySee(targetEntity, tx, ty, tz)) {
//        		System.out.println("teleport find failed because of lack of LOS");
//        		System.out.println("ty = " + ty);
        		continue;
        	}
        	
        	// check that we're not colliding and not in liquid
        	float halfWidth = this.width / 2.0F;
        	AxisAlignedBB destBox = AxisAlignedBB.getBoundingBox(tx - halfWidth, ty - yOffset + ySize, tz - halfWidth, tx + halfWidth, ty - yOffset + ySize + height, tz + halfWidth);
        	if (worldObj.getCollidingBoundingBoxes(this, destBox).size() > 0)
            {
//        		System.out.println("teleport find failed because of collision");
        		continue;
            }
        	
        	if (worldObj.isAnyLiquid(destBox)) {
//        		System.out.println("teleport find failed because of liquid at destination");
        		continue;
        	}
        	
        	// if we made it this far, we win!
        	break;
        }
        
        if (tries == 99) {
            //System.out.println("Found no spots, giving up");
        	return null;
        }
        
        //System.out.println("I think we found a good destination at " + tx + ", " + ty + ", " + tz);
//        System.out.println("canEntitySee = " + canEntitySee(targetEntity, tx, ty, tz));
        return Vec3.createVectorHelper(tx, ty, tz);
    }
    
    /**
     * Can the specified entity see the specified location?
     */
    protected boolean canEntitySee(Entity entity, double dx, double dy, double dz) {
        return worldObj.rayTraceBlocks(Vec3.createVectorHelper(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ), Vec3.createVectorHelper(dx, dy, dz)) == null;

    }
    
	@SuppressWarnings("unchecked")
	public int countMyMinions() {
    	// check if there are enough minions.  we check a 32x16x32 area
		List<EntityTFIceCrystal> nearbyMinons = worldObj.getEntitiesWithinAABB(EntityTFIceCrystal.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX + 1, posY + 1, posZ + 1).expand(32.0D, 16.0D, 32.0D));

		return nearbyMinons.size();
	}


	public void incrementSuccessfulDrops() {
		this.successfulDrops++;
	}


	@Override
	public void doBreathAttack(Entity target) {
		if (target.attackEntityFrom(DamageSource.causeMobDamage(this), BREATH_DAMAGE)) {
			// slow target?
    	}
	}
	

}
