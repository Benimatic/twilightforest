package twilightforest.entity.boss;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.TFSounds;
import twilightforest.block.BlockTFBossSpawner;
import twilightforest.block.TFBlocks;
import twilightforest.block.enums.SpawnerVariant;
import twilightforest.item.TFItems;
import twilightforest.world.ChunkGeneratorTwilightForest;
import twilightforest.world.TFBiomeProvider;
import twilightforest.world.WorldProviderTwilightForest;


public class EntityTFNaga extends EntityMob
implements IMob, IBossDisplayData, IEntityMultiPart {

	private static int TICKS_BEFORE_HEALING = 600;

	private static int MAX_SEGMENTS = 12;

	int currentSegments = 0; // not including head
	
	float segmentHealth;
	
	int LEASH_X = 46;
	int LEASH_Y = 7;
	int LEASH_Z = 46;
	
	EntityTFNagaSegment[] body;

    protected PathEntity pathToEntity;
    protected Entity targetEntity;
    
    int circleCount;
    int intimidateTimer;
    int crumblePlayerTimer;
    int chargeCount;

    boolean clockwise;
	
	public int ticksSinceDamaged = 0;
	
	public EntityTFNaga(World world) {
		super(world);
		
		//this.texture = TwilightForestMod.MODEL_DIR + "nagahead.png";
		this.setSize(1.75f, 3.0f);
		//this.moveSpeed = 0.6f;
		this.stepHeight = 2;

		this.setHealth(getMaxHealth());
		this.segmentHealth = getMaxHealth() / 10;
		setSegmentsPerHealth();
        
        this.experienceValue = 217;
        
		this.ignoreFrustumCheck = true;
		
		circleCount = 15;
		
		// make segments
		this.body = new EntityTFNagaSegment[MAX_SEGMENTS];
		for (int i = 0; i < body.length; i++)
		{
			this.body[i] = new EntityTFNagaSegment(this, i);
			world.spawnEntity(body[i]);
		}
		
		this.goNormal();
	}
	
	@Override
    protected void entityInit()
    {
        super.entityInit();
    }
	
	public float getMaxHealthPerDifficulty() {
		if (world != null) {
			if (world.getDifficulty() == EnumDifficulty.EASY)
			{
				return 120;
			}
			else if (world.getDifficulty() == EnumDifficulty.NORMAL)
			{
				return 200;
			}
			else if (world.getDifficulty() == EnumDifficulty.HARD)
			{
				return 250;
			}
			else
			{
				//????
				return 200;
			}
		}
		else {
			// why is the world null?
			return 200;
		}

	}

	@Override
    protected boolean canDespawn()
    {
        return false;
    }
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getMaxHealthPerDifficulty());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    }
    

	
	/**
	 * Determine how many segments, from 2-12, the naga should have, dependent on its current health
	 */
	protected int setSegmentsPerHealth() 
	{
		int oldSegments = this.currentSegments;
		int newSegments = (int) ((this.getHealth() / segmentHealth) + (getHealth() > 0 ? 2 : 0));
		
		// certain types of overkill seem to make this happen by setting health to a negative number
		if (newSegments < 0) {
			newSegments = 0;
		}
		
		// why is this happening?  healing rays?
		if (newSegments > MAX_SEGMENTS)
		{
			newSegments = MAX_SEGMENTS;
		}
		
		if (newSegments != oldSegments) {
			// detonate unused segments
			if (newSegments < oldSegments) {
				for (int i = newSegments; i < oldSegments; i++) {
					if (body != null && body[i] != null) {
						body[i].selfDestruct();
					}
				}
			}
			else
			{
				// grow new segments?
				this.spawnBodySegments();
			}
		}

		// change current variables
		this.currentSegments = newSegments;
		setMovementFactorPerSegments();

		return currentSegments;
	}
	
	/**
	 * Sets how fast the naga should be moving, depending on how many segments it has left;
	 */
	protected void setMovementFactorPerSegments() {
        float movementFactor = 0.6F - (currentSegments / 12f * 0.2f); 
		
		
		
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(movementFactor); // movement speed

//        landMovementFactor = 0.6F - (currentSegments / 12f * 0.2f); 
//        jumpMovementFactor = landMovementFactor / 2F;
        
//        for (int i = 0; i < currentSegments; i++) {
//        	if (body != null && body.length > i && body[i] != null) {
//        		body[i].landMovementFactor = this.landMovementFactor * 1.25F;
//        		body[i].jumpMovementFactor = this.jumpMovementFactor * 1.25F;
//        	}
//        }
	}

    @Override
    public boolean canTriggerWalking()
    {
        return false;
    }
    
    @Override
	public boolean isInLava()
    {
        return false;
    }

    @Override
	public void onUpdate() {
		despawnIfInvalid();
		
		if (deathTime > 0) {
            for(int k = 0; k < 5; k++)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                EnumParticleTypes explosionType = rand.nextBoolean() ?  EnumParticleTypes.EXPLOSION_HUGE : EnumParticleTypes.EXPLOSION_NORMAL;
                
                world.spawnParticle(explosionType, (posX + rand.nextFloat() * width * 2.0F) - width, posY + rand.nextFloat() * height, (posZ + rand.nextFloat() * width * 2.0F) - width, d, d1, d2);
            }
		}
		
		// update health
        this.ticksSinceDamaged++;
        
        if (!this.world.isRemote && this.ticksSinceDamaged > TICKS_BEFORE_HEALING && this.ticksSinceDamaged % 20 == 0)
        {
        	this.heal(1);
        }
//
//		
//        if (!this.world.isRemote)
//        {
//            this.dataWatcher.updateObject(DATA_BOSSHEALTH, Integer.valueOf((int)this.getHealth()));
//        }
//        else
//        {
////        	if (this.getBossHealth() != this.getHealth())
////        	{
////        		this.setEntityHealth(this.getBossHealth());
////        	}
//        	if (this.getHealth() > 0)
//        	{
//        		// this seems to get set off during creation at some point
//        		this.deathTime = 0;
//        	}
//        }
        
    	setSegmentsPerHealth();

		
		super.onUpdate();
		
//        if (!this.world.isRemote)
//        {
//        	for (int i = 0; i < this.body.length; i++)
//        	{
//        		body[i].onUpdate();
//        	}
//        }
		
    	
		// move bodies
		moveSegments();
		
		for (int i = 0; i < body.length; i++)
		{
			if (!body[i].addedToChunk && !world.isRemote)
			{
				world.spawnEntity(body[i]);
			}
		}
    }

    @Override
    protected void updateAITasks()
    {
        super.updateAITasks();
//    	super.updateEntityActionState();
    	
    	/*
    	// Resist fire
    	if (fire >= 20) {
    		fire -= 10;
    	}
    	*/

    	// NAGA SMASH!
        if (isCollidedHorizontally && hasTarget())
        {
            breakNearbyBlocks();
        }
        
        // break targeting if our target goes outside the walls
        if (targetEntity != null && !this.isEntityWithinHomeArea(targetEntity))
        {
        	this.targetEntity = null;
        }

        // perform target and path maintenance 
        if (targetEntity == null)
        {
            targetEntity = findTarget();
            if(targetEntity != null)
            {
            	acquireNewPath();
            }
        }
        else if (!targetEntity.isEntityAlive())
        {
            targetEntity = null;
        }
        else
        {
        	float targetDistance = targetEntity.getDistanceToEntity(this);

        	if (targetDistance > 80) {
        		targetEntity = null;
        	}
        	else {
        		if(canEntityBeSeen(targetEntity))
        		{
        			attackEntity(targetEntity, targetDistance);
        		}
        		else
        		{
        			//attackBlockedEntity(entityToAttack, f1);
        		}
        	}
        }
        
        if(!hasPath())
        {
        	acquireNewPath();
        }

        boolean inWater = isInWater();
        boolean inLava = isInLava();
        
        //rotationPitch = 0.0F;
        
        Vec3d vec3d = hasPath() ? pathToEntity.getPosition(this) : null;
        
        // if we are very close to the path point, go to the next point, unless the path is finished
        for(double d = width * 4.0F; vec3d != null && vec3d.squareDistanceTo(posX, vec3d.yCoord, posZ) < d * d;)
        {
            pathToEntity.incrementPathIndex();
            if(pathToEntity.isFinished())
            {
                vec3d = null;
                pathToEntity = null;
            } else
            {
                vec3d = pathToEntity.getPosition(this);
            }
        }

        isJumping = false;
        if(vec3d != null)
        {
            double d1 = vec3d.xCoord - posX;
            double d2 = vec3d.zCoord - posZ;
            
            double dist = MathHelper.sqrt(d1 * d1 + d2 * d2);
            
            int i = MathHelper.floor(boundingBox.minY + 0.5D);
            double d3 = vec3d.yCoord - i;
            float f2 = (float)((Math.atan2(d2, d1) * 180D) / 3.1415927410125732D) - 90F;
            float f3 = f2 - rotationYaw;
            moveForward = getMoveSpeed();
    		this.setAIMoveSpeed(0.5f);

            //this.moveForward = (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue();
            
            // slither!
            if (dist > 4 && chargeCount == 0) {
            	moveStrafing = MathHelper.cos(this.ticksExisted * 0.3F) * getMoveSpeed() * 0.6F;
            }
            
            for(; f3 < -180F; f3 += 360F) { }
            for(; f3 >= 180F; f3 -= 360F) { }
            if(f3 > 30F)
            {
                f3 = 30F;
            }
            if(f3 < -30F)
            {
                f3 = -30F;
            }
            rotationYaw += f3;
            if(d3 > 0.6D)
            {
                isJumping = true;
            }
        }
        if(intimidateTimer > 0 && hasTarget())
        {
            faceEntity(targetEntity, 30F, 30F);
            moveForward = 0.1f;
        }
        if(intimidateTimer > 0 && hasTarget())
        {
            faceEntity(targetEntity, 30F, 30F);
            moveForward = 0.1f;
        }
        if(rand.nextFloat() < 0.8F && (inWater || inLava))
        {
            isJumping = true;
        }
    }
    
    private float getMoveSpeed() {
		return 0.5F;
	}

	/**
	 * Utility method to set our move speed
	 */
	private void setMoveSpeed(float f) {
		//this.setAIMoveSpeed(0.5f);
		this.setAIMoveSpeed(f);
	}

	/**
     * Breaks blocks near the naga
     */
    protected void breakNearbyBlocks() {
        int minx = MathHelper.floor(getEntityBoundingBox().minX - 0.5D);
        int miny = MathHelper.floor(getEntityBoundingBox().minY + 1.01D);
        int minz = MathHelper.floor(getEntityBoundingBox().minZ - 0.5D);
        int maxx = MathHelper.floor(getEntityBoundingBox().maxX + 0.5D);
        int maxy = MathHelper.floor(getEntityBoundingBox().maxY + 0.001D);
        int maxz = MathHelper.floor(getEntityBoundingBox().maxZ + 0.5D);
        if(world.isAreaLoaded(new BlockPos(minx, miny, minz), new BlockPos(maxx, maxy, maxz)))
        {
            for(int dx = minx; dx <= maxx; dx++)
            {
                for(int dy = miny; dy <= maxy; dy++)
                {
                    for(int dz = minz; dz <= maxz; dz++)
                    {
						// todo limit what can be broken
						world.destroyBlock(new BlockPos(dx, dy, dz), true);
                    }
                }
            }
        }
    }
    
	@Override
    protected SoundEvent getAmbientSound()
    {
        return rand.nextInt(3) != 0 ? TFSounds.NAGA_HISS : TFSounds.NAGA_RATTLE;
    }
 
	@Override
    protected SoundEvent getHurtSound()
    {
		return TFSounds.NAGA_HURT;
    }

	@Override
    protected SoundEvent getDeathSound()
    {
		return TFSounds.NAGA_HURT;
    }

	/**
     * Finds a new path.  Currently this is designed to circle the target entity
     */
    protected void acquireNewPath() {
    	
    	// if we don't have a target, do nuffin
    	if (!hasTarget()) 
    	{
    		wanderRandomly();
    		return;
    	}

    	// if we're supposed to intimidate, don't set a path
    	if (intimidateTimer > 0) 
    	{

    		pathToEntity = null;
    		intimidateTimer--;

    		if (intimidateTimer == 0) 
    		{
    			clockwise = !clockwise;

    			// check to see if we want to charge or crumble blocks around the player
    			if (targetEntity.boundingBox.minY > boundingBox.maxY)
    			{
    				// crumble!
    				doCrumblePlayer();
    			}
    			else
    			{
    				doCharge();
    			}
    		}

    		return;
    	}
    	
    	// crumbling is like intimidating, but we just charge after it
    	if (crumblePlayerTimer > 0)
    	{
    		pathToEntity = null;
    		crumblePlayerTimer--;

    		crumbleBelowTarget(2);
    		crumbleBelowTarget(3);
    		
    		if (crumblePlayerTimer == 0) {
    			doCharge();
    		}
    	}
    	
    	// looks like we're charging!
    	if (chargeCount > 0) 
    	{
    		chargeCount--;

    		Vec3d tpoint = findCirclePoint(targetEntity, 14, Math.PI);
    		pathToEntity = world.getEntityPathToXYZ(this, MathHelper.floor(tpoint.xCoord), MathHelper.floor(tpoint.yCoord), MathHelper.floor(tpoint.zCoord), 40F, true, true, true, true);

    		if (chargeCount == 0) 
    		{
    			doCircle();
    		}
    	}

    	// circle if we're supposed to
    	if (circleCount > 0) {
    		
    		circleCount--;
    		
    		// normal radius is 13
    		double radius = circleCount % 2 == 0 ? 12.0 : 14.0;
    		double rotation = 1; // in radians
    		
    		// hook out slightly before circling
    		if (circleCount > 1 && circleCount < 3) {
    			radius = 16;
    		}
    		
    		// head almost straight at the player at the end
    		if (circleCount == 1) {
    			rotation = 0.1;
    		}
    		
    		Vec3d tpoint = findCirclePoint(targetEntity, radius, rotation);
    		
    		pathToEntity = world.getEntityPathToXYZ(this, (int)tpoint.xCoord, (int)tpoint.yCoord, (int)tpoint.zCoord, 40F, true, true, true, true);
    		
    		if (circleCount == 0) {
    			doIntimidate();
    		}
    		

    	} 

    }

    /**
     * Crumbles blocks below our current targetEntity.
     */
    protected void crumbleBelowTarget(int range) {
		int floor = (int) getEntityBoundingBox().minY; // the block level the naga is standing on.
		int targetY = (int) targetEntity.getEntityBoundingBox().minY; // the block level the target is standing on.
		
		if (targetY > floor)
		{
			int dx = (int) targetEntity.posX + rand.nextInt(range) - rand.nextInt(range);
			int dz = (int) targetEntity.posZ + rand.nextInt(range) - rand.nextInt(range);
			int dy = targetY - rand.nextInt(range) + rand.nextInt(range > 1 ? range - 1 : range);
			
			if (dy <= floor) {
				dy = targetY;
			}

//			System.out.println("Crumbling block at " + dx + ", " + dy + ", " + dz);
			BlockPos pos = new BlockPos(dx, dy, dz);

			if (!world.isAirBlock(pos))
			{
				// todo limit what can be broken
				world.destroyBlock(pos, true);
				
				// sparkle!!
	            for(int k = 0; k < 20; k++)
	            {
	                double d = rand.nextGaussian() * 0.02D;
	                double d1 = rand.nextGaussian() * 0.02D;
	                double d2 = rand.nextGaussian() * 0.02D;
	                
	                world.spawnParticle(EnumParticleTypes.CRIT, (posX + rand.nextFloat() * width * 2.0F) - width, posY + rand.nextFloat() * height, (posZ + rand.nextFloat() * width * 2.0F) - width, d, d1, d2);
	            }
			}
		}
	}

	/**
	 * Begin the circle cycle
	 */
	protected void doCircle() {
//		System.out.println("Resuming circle mode");
		circleCount += 10 + rand.nextInt(10);
		goNormal();
	}

	/**
	 * Crumble blocks around the player.  This could use maybe some sort of animation or effect to show it is happening.
	 */
	protected void doCrumblePlayer() {
//		System.out.println("Crumbling blocks around the player");
		crumblePlayerTimer = 20 + rand.nextInt(20);
		goSlow();
	}

	/**
	 * Charge the player.  Although the count is 4, we actually charge only 3 times.
	 */
	protected void doCharge() {
		// charrrrrge!
//		System.out.println("Starting to charge!");
		chargeCount = 4;
		goFast();
	}
    
	/**
	 * Intimidate the player.  This mostly involves pausing and staring.
	 */
	protected void doIntimidate() {
//		System.out.println("Intimidating!");
    	// start the timer 
		intimidateTimer += 15 + rand.nextInt(10);
		goSlow();

    }
    
    /**
     * Sets the naga to move slowly, such as when he is intimidating the player
     */
	protected void goSlow() {
		// move slowly
//		moveForward = 0f;
		moveStrafing = 0;
		setMoveSpeed(0.1f);
		pathToEntity = null;
    }

    /**
     * Normal speed, like when he is circling
     */
	protected void goNormal() {
		setMoveSpeed(0.6F);
    }

	/**
     * Fast, like when he is charging
     */
	protected void goFast() {
		setMoveSpeed(1.0F);
    }

    @Override
	public boolean canBePushed() {
		return false;
	}

	/**
     * Finds a point that allows us to circle the player clockwise.
     */
    protected Vec3d findCirclePoint(Entity toCircle, double radius, double rotation)
    {
    	// compute angle
        double vecx = posX - toCircle.posX;
        double vecz = posZ - toCircle.posZ;
        float rangle = (float)(Math.atan2(vecz, vecx));

        // add a little, so he circles (clockwise)
        rangle += clockwise ? rotation : -rotation;

        // figure out where we're headed from the target angle
        double dx = MathHelper.cos(rangle) * radius;
        double dz = MathHelper.sin(rangle) * radius;
        
        double dy = Math.min(boundingBox.minY, toCircle.posY);

        // add that to the target entity's position, and we have our destination
    	return new Vec3d(toCircle.posX + dx, dy, toCircle.posZ + dz);
    }
    
    public boolean hasTarget() {
    	return targetEntity != null;
    }
	
    /**
     * Copied from EntityMob
     */
    protected Entity findTarget()
    {
        EntityPlayer entityplayer = world.getClosestVulnerablePlayerToEntity(this, 32D);
        //EntityPlayer entityplayer = world.getClosestPlayerToEntity(this, 32D);
        if (entityplayer != null && canEntityBeSeen(entityplayer) && isEntityWithinHomeArea(entityplayer))
        {
        	// check range
            return entityplayer;
        } 
        else
        {
            return null;
        }
    }

    @Override
	public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
    	// reject damage from outside of our home radius
        if (damagesource.getSourceOfDamage() != null)
        {
        	if (!this.isEntityWithinHomeArea(damagesource.getSourceOfDamage()))
        	{
        		return false;
        	}

        }

        if (damagesource.getEntity() != null)
        {
        	if (!this.isEntityWithinHomeArea(damagesource.getEntity()))
        	{
        		return false;
        	}
        }
    	
        // normal damage processing
    	if (super.attackEntityFrom(damagesource, i))
    	{
    		setSegmentsPerHealth();

    		Entity entity = damagesource.getEntity();

    		if(entity != this)
    		{
    			targetEntity = entity;
    		}
    		
    		this.ticksSinceDamaged = 0;
    		
    		return true;
    	} 
    	else
    	{
//    		System.out.println("Naga rejected damage");
    		
    		return false;
    	}
    }

    /**
     * We attempt to attack another entity.  Checks y height and attackTime counter
     */
    protected void attackEntity(Entity toAttack, float f)
    {
        if(attackTime <= 0 && f < 4.0F && toAttack.boundingBox.maxY > (boundingBox.minY - 2.5) && toAttack.boundingBox.minY < (boundingBox.maxY + 2.5))
        {
            attackTime = 20;
            attackEntityAsMob(toAttack);
            
            if (getMoveSpeed() > 0.8) {
            	// charging, apply extra pushback
                toAttack.addVelocity(-MathHelper.sin((rotationYaw * 3.141593F) / 180F) * 1.0F, 0.10000000000000001D, MathHelper.cos((rotationYaw * 3.141593F) / 180F) * 1.0F);
            }
        }
    }

    /**
     * Like it says in the method title, find a random destinaton and set sail.
     */
    protected void wanderRandomly()
    {
    	goNormal();
    	
        boolean flag = false;
        int tx = -1;
        int ty = -1;
        int tz = -1;
        float worstweight = -99999F;
        for(int l = 0; l < 10; l++)
        {
            int dx = MathHelper.floor((posX + rand.nextInt(21)) - 6D);
            int dy = MathHelper.floor((posY + rand.nextInt(7)) - 3D);
            int dz = MathHelper.floor((posZ + rand.nextInt(21)) - 6D);
            
            // if we are thinking about out of bounds, head back home instead
            if (!this.isWithinHomeDistance(dx, dy, dz))
            {
//            	System.err.println("Naga wants to go out of bounds");
            	
            	dx = this.getHomePosition().posX + rand.nextInt(21) - rand.nextInt(21);
            	dy = this.getHomePosition().posY + rand.nextInt(7) - rand.nextInt(7);
            	dz = this.getHomePosition().posZ + rand.nextInt(21) - rand.nextInt(21);
            }
            
            float weight = getBlockPathWeight(dx, dy, dz);
            if(weight > worstweight)
            {
                worstweight = weight;
                tx = dx;
                ty = dy;
                tz = dz;
                flag = true;
            }
        }

        if(flag)
        {
            pathToEntity = world.getEntityPathToXYZ(this, tx, ty, tz, 80F, true, true, true, true);
        }
    }

    /**
     * Copied from EntityCreature
     */
    @Override
	public float getBlockPathWeight(int i, int j, int k)
    {
    	// if it's out of bounds, we hate it
		if (!this.isWithinHomeDistance(i, j, k))
		{
			return Float.MIN_VALUE;
		}
		else
		{
			return 0.0F;
		}
    }

    /**
     * Copied from EntityCreature
     */
    @Override
	public boolean hasPath()
    {
    	return pathToEntity != null;
    }

    @Override
	protected Item getDropItem()
    {
        return TFItems.nagaScale;
    }

    @Override
    protected void dropFewItems(boolean flag, int z) {
    	Item i = getDropItem();
    	if(i != null)
    	{
    		int j = 6 + rand.nextInt(6);
    		for(int k = 0; k < j; k++)
    		{
    			this.dropItem(i, 1);
    		}

    	}
    	
        // trophy
        this.entityDropItem(new ItemStack(TFItems.trophy, 1, 1), 0);
    }

	/**
     * Check to make sure all parts exist.  If they don't, despawn.
     */
	protected void despawnIfInvalid() {
		// check to see if we're valid
        if(!world.isRemote && world.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
        	despawnMe();
        }
	}
	
	/**
	 * Despawn the naga, and restore the boss spawner at our home location, if set
	 */
	protected void despawnMe() {
		if (isLeashed()) 
		{
			BlockPos home = this.getHomePosition();
			world.setBlockState(home, TFBlocks.bossSpawner.getDefaultState().withProperty(BlockTFBossSpawner.VARIANT, SpawnerVariant.NAGA), 2);
		}
		setDead();
	}
	
	/**
	 * Are there free-roaming nagas out there?  Should there be?
	 */
	public boolean isLeashed() {
		return this.getMaximumHomeDistance() > -1;
	}

	@Override
	public boolean isWithinHomeDistanceFromPosition(BlockPos pos)
    {
		if (this.getMaximumHomeDistance() == -1)
		{
			return true;
		}
		else
		{
			int distX = Math.abs(this.getHomePosition().getX() - pos.getX());
			int distY = Math.abs(this.getHomePosition().getY() - pos.getY());
			int distZ = Math.abs(this.getHomePosition().getZ() - pos.getZ());

			return distX <= LEASH_X && distY <= LEASH_Y && distZ <= LEASH_Z;
		}
    }

	/**
	 * Is the entity within our home area?
	 */
	public boolean isEntityWithinHomeArea(Entity entity)
	{
		return isWithinHomeDistance(MathHelper.floor(entity.posX), MathHelper.floor(entity.posY), MathHelper.floor(entity.posZ));
	}


	/**
	 * Spawns the body of the naga
	 */
	protected void spawnBodySegments() 
	{
		if (!world.isRemote)
		{
			if (body == null)
			{
				body = new EntityTFNagaSegment[MAX_SEGMENTS];
			}
			for (int i = 0; i < currentSegments; i++) 
			{
				if (body[i] == null || body[i].isDead)
				{
					body[i] = new EntityTFNagaSegment(this, i);
					body[i].setLocationAndAngles(posX + 0.1 * i, posY + 0.5D, posZ + 0.1 * i, rand.nextFloat() * 360F, 0.0F);
					world.spawnEntity(body[i]);
				}
			}
		}
	}
	
	/**
	 * Sets the heading (ha ha) of the body segments
	 */
	protected void moveSegments() {
		for (int i = 0; i < this.currentSegments; i++)
		{
			double followX, followY, followZ;
			Entity leader;
			
			if (i == 0)
			{
				leader = this;
			}
			else
			{
				leader = this.body[i - 1];
			}
			
			followX = leader.posX;
			followY = leader.posY;
			followZ = leader.posZ;

			
			// also weight the position so that the segments straighten out a little bit, and the front ones straighten more
	    	float angle = (((leader.rotationYaw + 180) * 3.141593F) / 180F);

			
			double straightenForce = 0.05D + (1.0 / (float)(i + 1)) * 0.5D;
			
	    	double idealX = -MathHelper.sin(angle) * straightenForce;
	    	double idealZ = MathHelper.cos(angle) * straightenForce;
			
			
			Vec3d diff = new Vec3d(body[i].posX - followX, body[i].posY - followY, body[i].posZ - followZ);
			diff = diff.normalize();

			// weight so segments drift towards their ideal position
			diff = diff.addVector(idealX, 0, idealZ);
			diff = diff.normalize();
			
//			if (world.isRemote)
//			{
//				System.out.println("Difference for segment " + i + " is " + diff.xCoord + ", " + diff.yCoord + ", " + diff.zCoord);
//			}
		
			double f = 2.0D;

			

            double destX = followX + f * diff.xCoord;
            double destY = followY + f * diff.yCoord;
            double destZ = followZ + f * diff.zCoord;
            
//            if (body[i].onGround && diff.yCoord > 0)
//            {
//            	destY = body[i].posY;
//            }
            
            
            body[i].setPosition(destX, destY, destZ);
            
            body[i].motionX = f * diff.xCoord;
            body[i].motionY = f * diff.yCoord;
            body[i].motionZ = f * diff.zCoord;
            
            double distance = (double)MathHelper.sqrt(diff.xCoord * diff.xCoord + diff.zCoord * diff.zCoord);
            
            if (i == 0)
            {
				diff = diff.addVector(0, -0.15, 0);
            }
            
            body[i].setRotation((float) (Math.atan2(diff.zCoord, diff.xCoord) * 180.0D / Math.PI) + 90.0F, -(float)(Math.atan2(diff.yCoord, distance) * 180.0D / Math.PI));

	
			
//			if (world.isRemote)
//			{
//				System.out.println("Client body segment " + i + " to " + body[i].posX + ", " + body[i].posY + ", " + body[i].posZ);
//			}
//			else
//			{
//				System.out.println("Server body segment " + i + " to " + body[i].posX + ", " + body[i].posY + ", " + body[i].posZ);
//			}
            
		}


		
	}
	
    @Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
    	BlockPos home = this.getHomePosition();
        nbttagcompound.setTag("Home", newDoubleNBTList(new double[] {
        		home.getX(), home.getY(), home.getZ()
            }));
        nbttagcompound.setBoolean("HasHome", this.hasHome());
        super.writeEntityToNBT(nbttagcompound);
    }

    @Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        if (nbttagcompound.hasKey("Home", 9))
        {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Home", 6);
            int hx = (int) nbttaglist.getDoubleAt(0);
            int hy = (int) nbttaglist.getDoubleAt(1);
            int hz = (int) nbttaglist.getDoubleAt(2);
            this.setHomePosAndDistance(new BlockPos(hx, hy, hz), 20);
        }
        if (!nbttagcompound.getBoolean("HasHome"))
        {
        	this.detachHome();
        }
        
        // check health and segments
        setSegmentsPerHealth();
    }

    /**
     * Trigger achievement when killed
     */
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightKillNaga);
		}
		
		// mark the courtyard as defeated
		if (!world.isRemote && world.provider instanceof WorldProviderTwilightForest) {
			int dx = MathHelper.floor(this.posX);
			int dy = MathHelper.floor(this.posY);
			int dz = MathHelper.floor(this.posZ);
			
			ChunkGeneratorTwilightForest chunkProvider = ((WorldProviderTwilightForest)world.provider).getChunkProvider();
			TFFeature nearbyFeature = ((TFBiomeProvider)world.provider.getBiomeProvider()).getFeatureAt(dx, dz, world);
			
			if (nearbyFeature == TFFeature.nagaCourtyard) {
				chunkProvider.setStructureConquered(dx, dy, dz, true);
			}
		}

	}

//	public float getMoveSpeed() {
//		return this.moveSpeed;
//	}

//	/**
//	 * Needed for boss health bar on the client
//	 */
//	@Override
//	public int getBossHealth() {
//        return this.dataWatcher.getWatchableObjectInt(EntityTFNaga.DATA_BOSSHEALTH);
//	}

	@Override
	public World getWorld() {
		return this.world;
	}

	@Override
	public boolean attackEntityFromPart(EntityDragonPart entitydragonpart, DamageSource damagesource, float i) {
		return false;
	}

    @Override
    public Entity[] getParts()
    {
        return body;
    }
}
