package twilightforest.entity.boss;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFSwarmSpider;
import twilightforest.item.TFItems;
import twilightforest.world.ChunkGeneratorTwilightForest;
import twilightforest.world.TFBiomeProvider;
import twilightforest.world.WorldProviderTwilightForest;




public class EntityTFLich extends EntityMob implements IBossDisplayData {
	public static final ResourceLocation LOOT_TABLE = new ResourceLocation(TwilightForestMod.ID, "entities/lich");
	private static final DataParameter<Byte> DATA_ISCLONE = EntityDataManager.createKey(EntityTFLich.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> DATA_SHIELDSTRENGTH = EntityDataManager.createKey(EntityTFLich.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> DATA_MINIONSLEFT = EntityDataManager.createKey(EntityTFLich.class, DataSerializers.BYTE);
	private static final DataParameter<Integer> DATA_BOSSHEALTH = EntityDataManager.createKey(EntityTFLich.class, DataSerializers.VARINT);
	private static final DataParameter<Byte> DATA_ATTACKTYPE = EntityDataManager.createKey(EntityTFLich.class, DataSerializers.BYTE);

	EntityTFLich masterLich;

	private static final ItemStack heldItems[] = {new ItemStack(TFItems.scepterTwilight, 1), new ItemStack(TFItems.scepterZombie, 1), new ItemStack(Items.GOLDEN_SWORD, 1)};
	public static final int MAX_SHADOW_CLONES = 2;
	public static final int INITIAL_SHIELD_STRENGTH = 5;
	public static final int MAX_ACTIVE_MINIONS = 3;
	public static final int INITIAL_MINIONS_TO_SUMMON = 9;
	public static final int MAX_HEALTH = 100;

	
	/**
	 * Make a new master lich.
	 */
	public EntityTFLich(World world) {
		super(world);
		setSize(1.1F, 2.5F);
        //texture = TwilightForestMod.MODEL_DIR + "twilightlich64.png";
//        this.yOffset = .25F;
        
        setShadowClone(false);
        this.masterLich = null;
        this.isImmuneToFire = true;
        setShieldStrength(INITIAL_SHIELD_STRENGTH);
        setMinionsToSummon(INITIAL_MINIONS_TO_SUMMON);

        this.experienceValue = 217;

	}
	
    public EntityTFLich(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x, y, z);
    }

	/**
	 * Make a lich shadow clone
	 */
	public EntityTFLich(World world, EntityTFLich otherLich) {
		this(world);
		
		setShadowClone(true);
		this.masterLich = otherLich;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(DATA_ISCLONE, (byte) 0);
		dataManager.register(DATA_SHIELDSTRENGTH, (byte) 0);
		dataManager.register(DATA_MINIONSLEFT, (byte) 0);
        dataManager.register(DATA_BOSSHEALTH, EntityTFLich.MAX_HEALTH);
		dataManager.register(DATA_ATTACKTYPE, (byte) 0);
	}



	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(MAX_HEALTH); // max health
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D); // attack damage
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.800000011920929D); // movement speed
    }
	
	
	@Override
    public ItemStack getHeldItem()
    {
        return heldItems[getPhase() - 1];
    }

    @Override
    public void setInWeb() {}

    @Override
	protected boolean canDespawn()
    {
        return false;
    }

    @Override
    public boolean isInLava()
    {
        return false;
    }

    @Override
    public boolean isInWater()
    {
    	return false;
    }
     
	/**
	 * What phase of the fight are we on?
	 * 
	 * 1 - reflecting bolts, shield up
	 * 2 - summoning minions
	 * 3 - melee
	 */
	public int getPhase() {
		if (isShadowClone() || getShieldStrength() > 0) {
			return 1;
		}
		else if (getMinionsToSummon() > 0) {
			return 2;
		}
		else {
			return 3;
		}
	}

    @Override
	public void onLivingUpdate() {
        // determine the hand position
        float angle = ((renderYawOffset * 3.141593F) / 180F);
        
        double dx = posX + (MathHelper.cos(angle) * 0.65);
        double dy = posY + (height * 0.94);
        double dz = posZ + (MathHelper.sin(angle) * 0.65);

    	
    	// add particles!
    	
    	// how many particles do we want to add?!
    	int factor = (80 - attackTime) / 10;
    	int particles = factor > 0 ? rand.nextInt(factor) : 1;
    	
    	
        for (int j1 = 0; j1 < particles; j1++)
        {
        	float sparkle = 1.0F - (attackTime + 1.0F) / 60.0F;
        	sparkle *= sparkle;
        	
            float red = 0.37F * sparkle;
            float grn = 0.99F * sparkle;
            float blu = 0.89F * sparkle;
            
            // change color for fireball
        	if (this.getNextAttackType() != 0)
        	{
                red = 0.99F * sparkle;
                grn = 0.47F * sparkle;
                blu = 0.00F * sparkle;
        	}
            
            world.spawnParticle(EnumParticleTypes.SPELL_MOB, dx + (rand.nextGaussian() * 0.025), dy + (rand.nextGaussian() * 0.025), dz + (rand.nextGaussian() * 0.025), red, grn, blu);
        }
        
		if (isShadowClone()) {
			// clones despawn if they have strayed from the master
			checkForMaster();
		}
		
		// update health
        if (!this.world.isRemote)
        {
            dataManager.set(DATA_BOSSHEALTH, (int) getHealth());
        }
		
        super.onLivingUpdate();
    }
    
    
    /**
     * Called when we get attacked.
     * 
     * Never switch targets to any lich.
     * 
     * TODO maybe we could compare masters to make teams or something.
     */
    @Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float damage) {
    	// if we're in a wall, teleport for gosh sakes
    	if (par1DamageSource.getDamageType() == "inWall" && getAttackTarget() != null) {
    		teleportToSightOfEntity(getAttackTarget());
    	}
    	
    	if (isShadowClone()) {
    		this.world.playSoundAtEntity(this, "random.fizz", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
    		return false;
    	}
    	
		EntityLivingBase prevTarget = this.getAttackTarget();
		
//		System.out.println("Damage source is " + par1DamageSource);
//		System.out.println("Damage type is " + par1DamageSource.getDamageType());
//		System.out.println("Damage source source is " + par1DamageSource.getSourceOfDamage());

		// ignore all bolts that are not reflected
		if (par1DamageSource.getEntity() instanceof EntityTFLich) {
			return false;
		}
		
		// if our shield is up, ignore any damage that can be blocked.
		if (getShieldStrength() > 0)
		{
			if (par1DamageSource.isUnblockable() && damage > 2) 
			{
				// reduce shield for magic damage greater than 1 heart
				if (getShieldStrength() > 0) {
					setShieldStrength(getShieldStrength() - 1);
					this.world.playSoundAtEntity(this, "random.break", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				}
			}
			else
			{
				this.world.playSoundAtEntity(this, "random.break", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				// HACK for creative mode: but get annoyed at what's causing it.
				if (par1DamageSource.getEntity() instanceof EntityPlayer) 
				{
					this.setAttackTarget((EntityPlayer) par1DamageSource.getEntity());
				}
			}
			
			return false;
		}

		// never attack another lich?
		//TODO: this could better check who is actually attacking against the masterLich variable thing
		if (super.attackEntityFrom(par1DamageSource, damage)) 
		{
			if (getAttackTarget() instanceof EntityTFLich) {
				this.setAttackTarget(prevTarget);
			}
			
			// if we were attacked successfully during phase 1 or 2, teleport
			// during phase 3, 1 in 4 chance of teleport
			if (this.getPhase() < 3 || rand.nextInt(4) == 0)
			{
				this.teleportToSightOfEntity(getAttackTarget());
			}
			
			return true;
		}
		else 
		{
			return false;
		}
	}
    
//    /**
//     * Another method to stop the shadow clones from being attacked
//     */
//	@Override
//	public boolean canAttackWithItem() {
//		if (isShadowClone()) {
//			return false;
//		}
//		else {
//			return super.canAttackWithItem();
//		}
//	}


	/**
     * Attack!
     */
    @Override
    protected void attackEntity(Entity targetedEntity, float f)
    {
    	if (!isShadowClone() && attackTime % 15 == 0) {
    		popNearbyMob();
    	}
    	
    	if (getPhase() == 1) {
	    	if (attackTime == 60 && !world.isRemote) {
	
	    		teleportToSightOfEntity(targetedEntity);
	    		
	    		if (!isShadowClone()) {
		    		// if we are the master, check for clones
		    		checkAndSpawnClones(targetedEntity);
	    		}
	    	}
	    	
	    	if(getEntitySenses().canSee(targetedEntity) && attackTime == 0 && f < 20F)
	    	{
    			if (this.getNextAttackType() == 0)
    			{
    				// if not, and we can see the target, launch a bolt
    				launchBoltAt(targetedEntity);
    			}
    			else
    			{
    				launchBombAt(targetedEntity);
    			}
    			
    			// decide next attack type
    			if (rand.nextInt(3) > 0) 
    			{
    				this.setNextAttackType(0);
    			}
    			else
    			{
    				this.setNextAttackType(1);
    			}
    			attackTime = 100;
	    		
	    	}
	    	// do not chase the player
    		hasAttacked = true;
    	}
    	if (getPhase() == 2 && !isShadowClone()) {
    		despawnClones();
    		
    		// spawn minions every so often
    		if (attackTime % 15 == 0) {
    			// spawn minion
    			checkAndSpawnMinions(targetedEntity);
    		}
    		
    		if (attackTime == 0) {
	    		// if we're in melee, attack
	    		if (f < 2.0F) {
	    			// melee attack
	    			this.attackEntityAsMob(targetedEntity);
	    			attackTime = 20;
	    		}
	    		else if (f < 20F && getEntitySenses().canSee(targetedEntity)) {
	    			// if not, and we can see the target, launch a bolt
	    			if (this.getNextAttackType() == 0)
	    			{
	    				// if not, and we can see the target, launch a bolt
	    				launchBoltAt(targetedEntity);
	    			}
	    			else
	    			{
	    				launchBombAt(targetedEntity);
	    			}
	    			
	    			// decide next attack type
	    			if (rand.nextInt(2) > 0) 
	    			{
	    				this.setNextAttackType(0);
	    			}
	    			else
	    			{
	    				this.setNextAttackType(1);
	    			}
		    		attackTime = 60;
	    		}
	    		else {
	    			// if not, teleport around
	    			teleportToSightOfEntity(targetedEntity);
	    			attackTime = 20;
	    			
	    		}
    		}
    		// do not chase
    		hasAttacked = true;
	    		
    	}
    	if (getPhase() == 3) {
    		// melee!
            if (this.attackTime <= 0 && f < 2.0F && targetedEntity.boundingBox.maxY > this.boundingBox.minY && targetedEntity.boundingBox.minY < this.boundingBox.maxY)
            {
                this.attackTime = 20;
                this.attackEntityAsMob(targetedEntity);
                hasAttacked = true;
            }
    	}
    	
    	
    }

	/**
     * Fires a lich bolt at the target
     * @param targetedEntity
     */
	protected void launchBoltAt(Entity targetedEntity) {
		float bodyFacingAngle = ((renderYawOffset * 3.141593F) / 180F);
		double sx = posX + (MathHelper.cos(bodyFacingAngle) * 0.65);
		double sy = posY + (height * 0.82);
		double sz = posZ + (MathHelper.sin(bodyFacingAngle) * 0.65);
		
		double tx = targetedEntity.posX - sx;
		double ty = (targetedEntity.getEntityBoundingBox().minY + (double)(targetedEntity.height / 2.0F)) - (posY + height / 2.0F);
		double tz = targetedEntity.posZ - sz;
		
		playSound(SoundEvents.ENTITY_GHAST_SHOOT, getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);

		EntityTFLichBolt projectile = new EntityTFLichBolt(world, this);
		projectile.setLocationAndAngles(sx, sy, sz, rotationYaw, rotationPitch);
		projectile.setThrowableHeading(tx, ty, tz, 0.5F, 1.0F);

		world.spawnEntity(projectile);
	}

	/**
     * Fires a lich bomb at the target
     * @param targetedEntity
     */
	protected void launchBombAt(Entity targetedEntity) {
		float bodyFacingAngle = ((renderYawOffset * 3.141593F) / 180F);
		double sx = posX + (MathHelper.cos(bodyFacingAngle) * 0.65);
		double sy = posY + (height * 0.82);
		double sz = posZ + (MathHelper.sin(bodyFacingAngle) * 0.65);
		
		double tx = targetedEntity.posX - sx;
		double ty = (targetedEntity.getEntityBoundingBox().minY + (double)(targetedEntity.height / 2.0F)) - (posY + height / 2.0F);
		double tz = targetedEntity.posZ - sz;
		
		playSound(SoundEvents.ENTITY_GHAST_SHOOT, getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);

		EntityTFLichBomb projectile = new EntityTFLichBomb(world, this);
		projectile.setLocationAndAngles(sx, sy, sz, rotationYaw, rotationPitch);
		projectile.setThrowableHeading(tx, ty, tz, 0.35F, 1.0F);
		
		world.spawnEntity(projectile);
	}

    /**
     * Check the surrounding area for weaker monsters, and if we find any, magically destroy them.
     */
    @SuppressWarnings("unchecked")
	protected void popNearbyMob() {
		List<Entity> nearbyMobs = world.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(posX, posY, posZ, posX + 1, posY + 1, posZ + 1).expand(32.0D, 16.0D, 32.0D));

		for (Entity entity : nearbyMobs) {
			if (entity instanceof EntityLiving && canPop(entity) && getEntitySenses().canSee(entity)) {
				EntityLiving mob = (EntityLiving)entity;

				if (!world.isRemote) {
					mob.setDead();

					mob.spawnExplosionParticle();

					// play death sound
//					world.playSoundAtEntity(mob, mob.getDeathSound(), mob.getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
				}

				// make trail so it's clear that we did it
				makeRedMagicTrail(mob.posX, mob.posY + mob.height / 2.0, mob.posZ, this.posX, this.posY + this.height / 2.0, this.posZ);

				// one per cycle, please
				break;
			}
		}
		
		
	}
	
	/**
	 * Can we pop the nearby thing?
	 */
	@SuppressWarnings("rawtypes")
	protected boolean canPop(Entity nearby) {
		Class[] poppable = {EntitySkeleton.class, EntityZombie.class, EntityEnderman.class, EntitySpider.class, EntityCreeper.class, EntityTFSwarmSpider.class};
		
		for (Class pop : poppable) {
			if (nearby.getClass() == pop) {
				return true;
			}
		}
		return false;
	}

	/**
     * Look and see if there are enough clones in the surrounding area.  If there are not, spawn one.
     * @param targetedEntity 
     */
    protected void checkAndSpawnClones(Entity targetedEntity) {
    	// if not, spawn one!
    	if (countMyClones() < EntityTFLich.MAX_SHADOW_CLONES) {
    		spawnShadowClone(targetedEntity);
    	}
	}
    
	@SuppressWarnings("unchecked")
	protected int countMyClones() {
    	// check if there are enough clones.  we check a 32x16x32 area
		List<EntityTFLich> nearbyLiches = world.getEntitiesWithinAABB(EntityTFLich.class, new AxisAlignedBB(posX, posY, posZ, posX + 1, posY + 1, posZ + 1).expand(32.0D, 16.0D, 32.0D));
		int count = 0;

		for (EntityTFLich nearbyLich : nearbyLiches) {
			if (nearbyLich.isShadowClone() && nearbyLich.masterLich == this) {
				count++;
			}
		}
		
		return count;
	}
	
	public boolean wantsNewClone(EntityTFLich clone) {
		return clone.isShadowClone() && countMyClones() < EntityTFLich.MAX_SHADOW_CLONES;
	}


	protected void spawnShadowClone(Entity targetedEntity) {
//		System.out.println("Lich is making a shadow clone!");
		
		
		// find a good spot
		Vec3d cloneSpot = findVecInLOSOf(targetedEntity);
		
		// put a clone there
		EntityTFLich newClone = new EntityTFLich(world, this);
		newClone.setPosition(cloneSpot.xCoord, cloneSpot.yCoord, cloneSpot.zCoord);
		world.spawnEntity(newClone);
		
		newClone.entityToAttack = targetedEntity;
		newClone.attackTime = 60 + rand.nextInt(3) - rand.nextInt(3);
		
		// make sparkles leading to it
		makeTeleportTrail(posX, posY, posZ, cloneSpot.xCoord, cloneSpot.yCoord, cloneSpot.zCoord);
	}
	
	/**
	 * Despawn neaby clones
	 */
	@SuppressWarnings("unchecked")
	protected void despawnClones() {
		List<EntityTFLich> nearbyLiches = world.getEntitiesWithinAABB(this.getClass(), new AxisAlignedBB(posX, posY, posZ, posX + 1, posY + 1, posZ + 1).expand(32.0D, 16.0D, 32.0D));
		
		for (EntityTFLich nearbyLich : nearbyLiches) {
			if (nearbyLich.isShadowClone()) {
				nearbyLich.isDead = true;
			}
		}
	}



	protected void checkAndSpawnMinions(Entity targetedEntity) {
		if (!world.isRemote && this.getMinionsToSummon() > 0) {
			int minions = countMyMinions();
			
	    	// if not, spawn one!
	    	if (minions < EntityTFLich.MAX_ACTIVE_MINIONS) {
	    		spawnMinionAt((EntityLivingBase)targetedEntity);
	    		this.setMinionsToSummon(this.getMinionsToSummon() - 1);
	    	}
		}
		// if there's no minions left to summon, we should move into phase 3 naturally
	}
	
	@SuppressWarnings("unchecked")
	protected int countMyMinions() {
    	// check if there are enough clones.  we check a 32x16x32 area
		List<EntityTFLichMinion> nearbyMinons = world.getEntitiesWithinAABB(EntityTFLichMinion.class, new AxisAlignedBB(posX, posY, posZ, posX + 1, posY + 1, posZ + 1).expand(32.0D, 16.0D, 32.0D));
		int count = 0;
		
		for (EntityTFLichMinion nearbyMinon : nearbyMinons) {
			if (nearbyMinon.master == this) {
				count++;
			}
		}
		
		return count;
	}

	protected void spawnMinionAt(EntityLivingBase targetedEntity) {
		
		// find a good spot
		Vec3d minionSpot = findVecInLOSOf(targetedEntity);
		
		// put a clone there
		EntityTFLichMinion minion = new EntityTFLichMinion(world, this);
		minion.setPosition(minionSpot.xCoord, minionSpot.yCoord, minionSpot.zCoord);
		//minion.initCreature();
		world.spawnEntity(minion);
		
		minion.setAttackTarget(targetedEntity);
		
		minion.spawnExplosionParticle();
		this.world.playSoundAtEntity(minion, "random.pop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
		
		// make sparkles leading to it
		makeBlackMagicTrail(posX, posY + this.getEyeHeight(), posZ, minionSpot.xCoord, minionSpot.yCoord + minion.height / 2.0, minionSpot.zCoord);
	}
	
	public boolean wantsNewMinion(EntityTFLichMinion minion) {
		return countMyMinions() < EntityTFLich.MAX_ACTIVE_MINIONS;
	}

	/**
	 * A method on the clones that despawns this clone if we are too far from the master
	 */
	protected void checkForMaster() {
		if (masterLich == null) {
			findNewMaster();
		}
		if (!world.isRemote) {
			if (masterLich == null || masterLich.isDead) {
				this.isDead = true;
			}
			else {
				//double distance = (this.posX - masterLich.posX) * (this.posX - masterLich.posX) + (this.posY - masterLich.posY) * (this.posY - masterLich.posY) + (this.posZ - masterLich.posZ) * (this.posZ - masterLich.posZ);
//				System.out.println("Clone says that distance from master is " + distance);
			}
		}
	}

	/**
	 * Find a new master for this clone
	 */
	@SuppressWarnings("unchecked")
	private void findNewMaster() {
		List<EntityTFLich> nearbyLiches = world.getEntitiesWithinAABB(EntityTFLich.class, new AxisAlignedBB(posX, posY, posZ, posX + 1, posY + 1, posZ + 1).expand(32.0D, 16.0D, 32.0D));
		
		for (EntityTFLich nearbyLich : nearbyLiches) {
			if (!nearbyLich.isShadowClone() && nearbyLich.wantsNewClone(this)) {
				this.masterLich = nearbyLich;
				
				// animate our new linkage!
				makeTeleportTrail(posX, posY, posZ, nearbyLich.posX, nearbyLich.posY, nearbyLich.posZ);
				// become angry at our masters target
				setAttackTarget(masterLich.getAttackTarget());
				
				// quit looking
				break;
			}
		}
	}

    
    /**
     * Teleports to within line of sight of (but not necessarily in view of) the target
     */
    protected void teleportToSightOfEntity(Entity entity)
    {
    	Vec3d dest = findVecInLOSOf(entity);
    	double srcX = posX;
    	double srcY = posY;
    	double srcZ = posZ;
        
        if (dest != null) {
        	teleportToNoChecks(dest.xCoord, dest.yCoord, dest.zCoord);
            faceEntity(entity, 100F, 100F);
            this.renderYawOffset = this.rotationYaw;
            
            if (!this.getEntitySenses().canSee(entity)) {
            	// um teleport mishap, return to start
//            	System.out.println("Teleport fail!!!");
            	teleportToNoChecks(srcX, srcY, srcZ);
            	
            }
        }
    }
    
    /**
     * Returns coords that would be good to teleport to.
     * 
     * Returns null if we can't find anything
     */
    protected Vec3d findVecInLOSOf(Entity targetEntity)
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
        	int bx = MathHelper.floor(tx);
        	int by = MathHelper.floor(ty);
        	int bz = MathHelper.floor(tz);
        	while (!groundFlag && ty > 0) 
        	{
                Block whatsThere = world.getBlock(bx, by - 1, bz);
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
        	AxisAlignedBB destBox = new AxisAlignedBB(tx - halfWidth, ty - yOffset + ySize, tz - halfWidth, tx + halfWidth, ty - yOffset + ySize + height, tz + halfWidth);
        	if (world.getCollidingBoundingBoxes(this, destBox).size() > 0)
            {
//        		System.out.println("teleport find failed because of collision");
        		continue;
            }
        	
        	if (world.isAnyLiquid(destBox)) {
//        		System.out.println("teleport find failed because of liquid at destination");
        		continue;
        	}
        	
        	// if we made it this far, we win!
        	break;
        }
        
        if (tries == 99) {
        	return null;
        }
        
//        System.out.println("I think we found a good destination at " + tx + ", " + ty + ", " + tz);
//        System.out.println("canEntitySee = " + canEntitySee(targetEntity, tx, ty, tz));
        return new Vec3d(tx, ty, tz);
    }
    
    /**
     * Can the specified entity see the specified location?
     */
    protected boolean canEntitySee(Entity entity, double dx, double dy, double dz) {
        return world.rayTraceBlocks(new Vec3d(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ), new Vec3d(dx, dy, dz)) == null;

    }

    /**
     * Does not check that the teleport destination is valid, we just go there
     */
    protected boolean teleportToNoChecks(double destX, double destY, double destZ)
    {
    	// save original position
    	double srcX = posX;
    	double srcY = posY;
    	double srcZ = posZ;

    	// change position
    	setPosition(destX, destY, destZ);
	
    	makeTeleportTrail(srcX, srcY, srcZ, destX, destY, destZ);

    	world.playSoundEffect(srcX, srcY, srcZ, "mob.endermen.portal", 1.0F, 1.0F);
    	world.playSoundAtEntity(this, "mob.endermen.portal", 1.0F, 1.0F);
    	
    	// sometimes we need to do this
    	this.isJumping = false;
    	
    	return true;
    }

    /**
     * Make a trail of particles from one point to another
     */
    protected void makeTeleportTrail(double srcX, double srcY, double srcZ, double destX, double destY, double destZ) {
		// make particle trail
    	int particles = 128;
    	for (int i = 0; i < particles; i++)
    	{
    		double trailFactor = i / (particles - 1.0D);
    		float f = (rand.nextFloat() - 0.5F) * 0.2F;
    		float f1 = (rand.nextFloat() - 0.5F) * 0.2F;
    		float f2 = (rand.nextFloat() - 0.5F) * 0.2F;
    		double tx = srcX + (destX - srcX) * trailFactor + (rand.nextDouble() - 0.5D) * width * 2D;
    		double ty = srcY + (destY - srcY) * trailFactor + rand.nextDouble() * height;
    		double tz = srcZ + (destZ - srcZ) * trailFactor + (rand.nextDouble() - 0.5D) * width * 2D;
    		world.spawnParticle("spell", tx, ty, tz, f, f1, f2);
    	}
	}

    /**
     * Make a trail of particles from one point to another
     */
    protected void makeRedMagicTrail(double srcX, double srcY, double srcZ, double destX, double destY, double destZ) {
		// make particle trail
    	int particles = 32;
    	for (int i = 0; i < particles; i++)
    	{
    		double trailFactor = i / (particles - 1.0D);
    		float f = 1.0F;
    		float f1 = 0.5F;
    		float f2 = 0.5F;
    		double tx = srcX + (destX - srcX) * trailFactor + rand.nextGaussian() * 0.005;
    		double ty = srcY + (destY - srcY) * trailFactor + rand.nextGaussian() * 0.005;
    		double tz = srcZ + (destZ - srcZ) * trailFactor + rand.nextGaussian() * 0.005;
    		world.spawnParticle("mobSpell", tx, ty, tz, f, f1, f2);
    	}
	}

    /**
     * Make a trail of particles from one point to another
     */
    protected void makeBlackMagicTrail(double srcX, double srcY, double srcZ, double destX, double destY, double destZ) {
		// make particle trail
    	int particles = 32;
    	for (int i = 0; i < particles; i++)
    	{
    		double trailFactor = i / (particles - 1.0D);
    		float f = 0.2F;
    		float f1 = 0.2F;
    		float f2 = 0.2F;
    		double tx = srcX + (destX - srcX) * trailFactor + rand.nextGaussian() * 0.005;
    		double ty = srcY + (destY - srcY) * trailFactor + rand.nextGaussian() * 0.005;
    		double tz = srcZ + (destZ - srcZ) * trailFactor + rand.nextGaussian() * 0.005;
    		world.spawnParticle("mobSpell", tx, ty, tz, f, f1, f2);
    	}
	}

    /**
     * @return Is this lich a shadow clone or the real one?
     */
    public boolean isShadowClone()
    {
        return (dataManager.get(DATA_ISCLONE) & 2) != 0;
    }

    /**
     * sets Is this lich a shadow clone or the real one?
     */
    public void setShadowClone(boolean par1)
    {
        byte var2 = dataManager.get(DATA_ISCLONE);

        if (par1)
        {
            dataManager.set(DATA_ISCLONE, (byte) (var2 | 2));
        }
        else
        {
            dataManager.set(DATA_ISCLONE, (byte) (var2 & -3));
        }
    }
    
    public byte getShieldStrength() {
		return dataManager.get(DATA_SHIELDSTRENGTH);
	}

	public void setShieldStrength(int shieldStrength) {
		dataManager.set(DATA_SHIELDSTRENGTH, (byte) shieldStrength);
	}

	public byte getMinionsToSummon() {
		return dataManager.get(DATA_MINIONSLEFT);
	}

	public void setMinionsToSummon(int minionsToSummon) {
		dataManager.set(DATA_MINIONSLEFT, (byte) minionsToSummon);
	}

	public byte getNextAttackType() {
		return dataManager.get(DATA_ATTACKTYPE);
	}

	public void setNextAttackType(int attackType) {
		dataManager.set(DATA_ATTACKTYPE, (byte) attackType);
	}

    @Override
	protected String getAmbientSound()
    {
        return "mob.blaze.breathe";
    }

    @Override
	protected String getHurtSound()
    {
        return "mob.blaze.hit";
    }

    @Override
	protected String getDeathSound()
    {
        return "mob.blaze.death";
    }

	@Override
	public ResourceLocation getLootTable() {
		return LOOT_TABLE;
	}

    @Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("ShadowClone", isShadowClone());
        nbttagcompound.setByte("ShieldStrength", getShieldStrength());
        nbttagcompound.setByte("MinionsToSummon", getMinionsToSummon());
    }

    @Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setShadowClone(nbttagcompound.getBoolean("ShadowClone"));
        setShieldStrength(nbttagcompound.getByte("ShieldStrength"));
        setMinionsToSummon(nbttagcompound.getByte("MinionsToSummon"));
    }

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightKillLich);
			
		}

		// mark the tower as defeated
		if (!world.isRemote && !this.isShadowClone()) {
			int dx = MathHelper.floor(this.posX);
			int dy = MathHelper.floor(this.posY);
			int dz = MathHelper.floor(this.posZ);
			
			if (world.provider instanceof WorldProviderTwilightForest){
				ChunkGeneratorTwilightForest chunkProvider = ((WorldProviderTwilightForest)world.provider).getChunkProvider();
				TFFeature nearbyFeature = ((TFBiomeProvider)world.provider.getBiomeProvider()).getFeatureAt(dx, dz, world);

				if (nearbyFeature == TFFeature.lichTower) {
					chunkProvider.setStructureConquered(dx, dy, dz, true);
				}
			}
		}
	}

    @Override
	public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
    
}
