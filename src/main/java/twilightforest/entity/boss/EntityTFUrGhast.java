package twilightforest.entity.boss;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.TFTreasure;
import twilightforest.TwilightForestMod;
import twilightforest.block.BlockTFTowerDevice;
import twilightforest.block.TFBlocks;
import twilightforest.entity.EntityTFMiniGhast;
import twilightforest.entity.EntityTFTowerGhast;
import twilightforest.world.ChunkProviderTwilightForest;
import twilightforest.world.TFWorldChunkManager;
import twilightforest.world.WorldProviderTwilightForest;
import cpw.mods.fml.common.FMLLog;

public class EntityTFUrGhast extends EntityTFTowerGhast implements IBossDisplayData {

//	private static final int DATA_BOSSHEALTH = 17;
	private static final int DATA_TANTRUM = 18;


	//private static final int CRUISING_ALTITUDE = 235; // absolute cruising altitude
	private static final int HOVER_ALTITUDE = 20; // how far, relatively, do we hover over ghast traps?


    public double courseX;
    public double courseY;
    public double courseZ;
    
    ArrayList<ChunkCoordinates> trapLocations;
    ArrayList<ChunkCoordinates> travelCoords;
    
    int currentTravelCoordIndex;
    
    int travelPathRepetitions;
    int desiredRepetitions;
    
    int nextTantrumCry;
    
    float damageUntilNextPhase; // how much damage can we take before we toggle tantrum mode
    
    boolean noTrapMode; // are there no traps nearby?  just float around

	public EntityTFUrGhast(World par1World) 
	{
		super(par1World);
		
        this.setSize(14.0F, 18.0F);
        this.aggroRange = 128.0F;
    	this.wanderFactor = 32.0F;
    	
        //this.texture = TwilightForestMod.MODEL_DIR + "towerboss.png";


    	this.noClip = true;
    	
    	this.trapLocations = new ArrayList<ChunkCoordinates>();
    	this.travelCoords = new ArrayList<ChunkCoordinates>();
    	
    	this.setInTantrum(false);

    	this.damageUntilNextPhase = 45;
    	
        this.experienceValue = 317;
        
        this.noTrapMode = false;

	}
	
//    public int getMaxHealth()
//    {
//        return 250;
//    }
    
	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(250*1.5+twilightforest.TwilightForestMod.Scatter.nextInt(375/3)-twilightforest.TwilightForestMod.Scatter.nextInt(375/3)); // max health
    }
	
	@Override
    protected void entityInit()
    {
        super.entityInit();
//        this.dataWatcher.addObject(DATA_BOSSHEALTH, (this.getMaxHealth()));
        this.dataWatcher.addObject(DATA_TANTRUM, ((byte) 0));
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    @Override
	protected boolean canDespawn()
    {
        return false;
    }
	
    /**
     * Keep health updated
     */
    @Override
	public void onUpdate() {
		
		super.onUpdate();

        // extra death explosions
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
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float damage)
    {
    	// ignore suffocation
    	if (source == DamageSource.inWall)
    	{
    		return false;
    	}
    	
    	boolean attackSuccessful = false; 
    	
    	// in tantrum mode take only 1/4 damage
    	if (this.isInTantrum())
    	{
    		damage /= 4;
    	}
    	
        if ("fireball".equals(source.getDamageType()) && source.getEntity() instanceof EntityPlayer)
        {
        	// 'hide' fireball attacks so that we don't take 1000 damage.
        	attackSuccessful = super.attackEntityFrom(DamageSource.causeThrownDamage(source.getSourceOfDamage(), source.getEntity()), damage);
        }
        else
        {
        	attackSuccessful = super.attackEntityFrom(source, damage);
        }
 
        if (!worldObj.isRemote)
        {
        	if (this.hurtTime == this.maxHurtTime)
        	{
        		this.damageUntilNextPhase -= this.getLastDamage();

        		FMLLog.info("[Urghast] Attack successful, %f damage until phase switch.", this.damageUntilNextPhase);

        		if (this.damageUntilNextPhase <= 0)
        		{
        			this.switchPhase();
        		}
        	}
        	else
        	{
        		FMLLog.info("[Urghast] Attack fail with %s type attack for %f damage", source.damageType, damage);
        	}
        }

        return attackSuccessful;
    }

	private float getLastDamage() {

		
		return this.prevHealth - this.getHealth();
	}

	/**
	 * Move to the next phase of our behavior
	 */
	private void switchPhase() {
		if (this.isInTantrum()) {
			this.stopTantrum();
		} else {
			this.startTantrum();
		}
		
		this.damageUntilNextPhase = 48;
	}

	/**
     * Start throwing a tantrum
     */
	protected void startTantrum() {
		this.setInTantrum(true);
		
		// start raining
		int rainTime = 300 * 20;
		
        WorldInfo worldInfo = MinecraftServer.getServer().worldServers[0].getWorldInfo();
		
		//System.out.println("Starting rain and thunder.  world = " + worldObj);
		
		worldInfo.setRaining(true);
		worldInfo.setThundering(true);
		worldInfo.setRainTime(rainTime);
		worldInfo.setThunderTime(rainTime);
		
		spawnGhastsAtTraps();
	}

	/**
	 * Spawn ghasts at two of the traps
	 */
	protected void spawnGhastsAtTraps() {
		// spawn ghasts around two of the traps
		ArrayList<ChunkCoordinates> ghastSpawns = new ArrayList<ChunkCoordinates>(this.trapLocations);
		
		int numSpawns = Math.min(2, ghastSpawns.size());

		for (int i = 0; i < numSpawns; i++)
		{
			int index = this.rand.nextInt(ghastSpawns.size());
			
			ChunkCoordinates spawnCoord = ghastSpawns.get(index);
			ghastSpawns.remove(index);
			
			spawnMinionGhastsAt(spawnCoord.posX, spawnCoord.posY, spawnCoord.posZ);
		}
	}
    
	public void stopTantrum() 
	{
		this.setInTantrum(false);
		
	}

	/**
	 * Spawn up to 6 minon ghasts around the indicated area
	 */
    private void spawnMinionGhastsAt(int x, int y, int z) {
    	int tries = 24;
    	int spawns = 0;
    	int maxSpawns = 6;
    	
    	int rangeXZ = 4;
    	int rangeY = 8;
    	
		//System.out.printf("Spawning minions near %d, %d, %d.\n", x, y, z);
		
		// lightning strike
		this.worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, x, y + 4, z));

    	
    	for (int i = 0; i < tries; i++)
    	{
    		EntityTFMiniGhast minion = new EntityTFMiniGhast(worldObj);
    		
    		double sx = x + ((rand.nextFloat() - rand.nextFloat()) * rangeXZ);
    		double sy = y + (rand.nextFloat() * rangeY);
    		double sz = z + ((rand.nextFloat() - rand.nextFloat()) * rangeXZ);
    		
    		minion.setLocationAndAngles(sx, sy, sz, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);
    		minion.makeBossMinion();

    		if (minion.getCanSpawnHere())
    		{
    			//System.out.println("Spawned minion!");
    			
    			this.worldObj.spawnEntityInWorld(minion);
    			minion.spawnExplosionParticle();
    		}
    		else
    		{
    			//System.out.println("Minion can't spawn!");
    		}

    		// limit the total number of successful spawns
    		if (++spawns >= maxSpawns)
    		{
    			//System.out.println("Spawned maxiumum minions.");
    			break;
    		}
    	}

	}

	/**
     * Altered Ghast AI
     */
    @SuppressWarnings("unchecked")
	protected void updateEntityActionState()
    {
        if (!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL)
        {
            this.setDead();
        }

        this.despawnEntity();
        
        // despawn mini ghasts that are in our AABB
		List<EntityTFMiniGhast> nearbyGhasts = worldObj.getEntitiesWithinAABB(EntityTFMiniGhast.class, this.boundingBox.expand(1, 1, 1));
		for (EntityTFMiniGhast ghast : nearbyGhasts)
		{
			ghast.setDead();
			this.heal(2);
		}
        
        
        // trap locations?
        if (this.trapLocations.isEmpty() && !this.noTrapMode)
        {
        	//System.out.println("Tower boss scannning for traps.");
        	
        	this.scanForTrapsTwice();
        	
        	//System.out.println("Traps found: " + this.trapLocations.size());
        }
        
        // did we find any traps?
        if (this.trapLocations.isEmpty() && !this.noTrapMode)
        {
        	FMLLog.info("[TwilightForest] Ur-ghast cannot find traps nearby, entering trap-less mode");
        	this.noTrapMode = true;
        }
        
        // check if in trap
        if (this.inTrapCounter > 0)
        {
        	this.inTrapCounter--;
        	this.targetedEntity = null;
        	return; // do nothing if in trap;
        }
        
        this.prevAttackCounter = this.attackCounter;
        
        // target
        if (this.targetedEntity != null && this.targetedEntity.isDead)
        {
            this.targetedEntity = null;
        }

        if (this.targetedEntity == null)
        {
            this.targetedEntity = findPlayerInRange();
        }
        else if (!this.isAggressive && this.targetedEntity instanceof EntityPlayer)
        {
        	checkToIncreaseAggro((EntityPlayer) this.targetedEntity);
        }
        
        
        // tantrum?
        if (this.isInTantrum())
        {
        	shedTear();
        	
        	this.targetedEntity = null;
        	
        	// cry?
        	if (--this.nextTantrumCry <= 0)
        	{
        		this.playSound(getHurtSound(), this.getSoundVolume(), this.getSoundPitch());
        		this.nextTantrumCry = 20 + rand.nextInt(30);
        	}
        	
        	if (this.ticksExisted % 10 == 0)
        	{
        		doTantrumDamageEffects();
        	}

        }

        
        // do this
		checkAndChangeCourse();
      
        
        // check if we are at our waypoint target
		double offsetX = this.waypointX - this.posX;
		double offsetY = this.waypointY - this.posY;
		double offsetZ = this.waypointZ - this.posZ;
        double distanceToWaypoint = offsetX * offsetX + offsetY * offsetY + offsetZ * offsetZ;

        if (distanceToWaypoint < 1.0D || distanceToWaypoint > 3600.0D)
        {
            makeNewWaypoint();
        }
        
        
        
        // move?
        if (this.courseChangeCooldown-- <= 0)
        	{
                this.courseChangeCooldown += this.rand.nextInt(5) + 0;
        		distanceToWaypoint = (double)MathHelper.sqrt_double(distanceToWaypoint);

        		double speed = 0.05D;
        		this.motionX += offsetX / distanceToWaypoint * speed;
        		this.motionY += offsetY / distanceToWaypoint * speed;
        		this.motionZ += offsetZ / distanceToWaypoint * speed;
        	}

        // check if our target is still within range
		double targetRange = (this.aggroCounter > 0 || this.isAggressive) ? aggroRange : stareRange;

        if (this.targetedEntity != null && this.targetedEntity.getDistanceSqToEntity(this) < targetRange * targetRange && this.canEntityBeSeen(this.targetedEntity))
        {
            // turn towards target
        	this.faceEntity(this.targetedEntity, 10F, this.getVerticalFaceSpeed());
        	
        	// attack if aggressive
            if (this.isAggressive)
            {
                if (this.attackCounter == 10)
                {
                    //this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1007, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
            		this.playSound("mob.ghast.charge", this.getSoundVolume(), this.getSoundPitch());
                }

                ++this.attackCounter;

                if (this.attackCounter == 20)
                {
                	spitFireball();
            		this.attackCounter = -40;
                }
            }
         }
        else
        {
        	// ignore player, move normally
            this.isAggressive = false;
        	this.targetedEntity = null;
        	this.rotationYaw = -(org.bogdang.modifications.math.TrigMath2.atan2((float)this.motionX, (float)this.motionZ)) * 180.0F / (float)Math.PI;
        	
        	// changing the pitch with movement looks goofy and un-ghast-like
        	//double dist = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ *  this.motionZ);
        	this.rotationPitch = 0;//(float) (-((org.bogdang.modifications.math.TrigMath2.atan2(this.motionY, dist) * 180D) / Math.PI));;

        }

        if (this.attackCounter > 0 && !this.isAggressive)
        {
            --this.attackCounter;
        }

        // set aggro status
        byte currentAggroStatus = this.dataWatcher.getWatchableObjectByte(16);
        byte newAggroStatus = (byte)(this.attackCounter > 10 ? 2 : (this.aggroCounter > 0 || this.isAggressive) ? 1 : 0);

        if (currentAggroStatus != newAggroStatus)
        {
        	this.dataWatcher.updateObject(16, newAggroStatus);
        }
    }

	@SuppressWarnings("unchecked")
	private void doTantrumDamageEffects() {
		// harm player below
		AxisAlignedBB below = this.boundingBox.getOffsetBoundingBox(0, -16, 0).expand(0, 16, 0);

		List<EntityPlayer> playersBelow = worldObj.getEntitiesWithinAABB(EntityPlayer.class, below);

		for (EntityPlayer player : playersBelow)
		{
			int dx = MathHelper.floor_double(player.posX);
			int dy = MathHelper.floor_double(player.posY);
			int dz = MathHelper.floor_double(player.posZ);

			if (worldObj.canBlockSeeTheSky(dx, dy, dz))
			{
				player.attackEntityFrom(DamageSource.anvil, 3*1.5f);
			}
		}

		// also suck up mini ghasts
		List<EntityTFMiniGhast> ghastsBelow = worldObj.getEntitiesWithinAABB(EntityTFMiniGhast.class, below);

		for (EntityTFMiniGhast ghast : ghastsBelow)
		{
			ghast.motionY += 1;
		}
	}

    /**
     * Make a tear particle fall off the ghast
     */
    private void shedTear() 
    {
		TwilightForestMod.proxy.spawnParticle(this.worldObj, "bosstear", this.posX + (this.rand.nextFloat() - 0.5D) * (double)this.width, this.posY + this.rand.nextFloat() * (double)this.height - 0.25D, this.posZ + (this.rand.nextFloat() - 0.5D) * (double)this.width, 0, 0, 0);
	}

	/**
     * Waypoints should be closer to our course than we are now.  Try 50 times and take the closest
     */
	protected void makeNewWaypoint() 
	{

        double closestDistance = this.getDistanceSq(this.courseX, this.courseY, this.courseZ);
		
		for (int i = 0; i < 50; i++)
		{
			double potentialX = this.posX + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * wanderFactor);
			double potentialY = this.courseY + this.rand.nextFloat() * 8.0F - 4.0F;
			double potentialZ = this.posZ + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * wanderFactor);

			double offsetX = this.courseX - potentialX;
			double offsetY = this.courseY - potentialY;
			double offsetZ = this.courseZ - potentialZ;
			
			double potentialDistanceToCourse = offsetX * offsetX + offsetY * offsetY + offsetZ * offsetZ;
			
			if (potentialDistanceToCourse < closestDistance)
			{
				this.waypointX = potentialX;
				this.waypointY = potentialY;
				this.waypointZ = potentialZ;
				
				closestDistance = potentialDistanceToCourse;
			}
		}
		
		
	}

	protected void checkAndChangeCourse() {
		// check course.
        if (courseX == 0 && courseY == 0 && courseZ == 0)
        {
        	changeCourse();
        }
        
        double offsetX = this.courseX - this.posX;
        double offsetY = this.courseY - this.posY;
        double offsetZ = this.courseZ - this.posZ;
        double distanceToCourse = offsetX * offsetX + offsetY * offsetY + offsetZ * offsetZ;
        
        if (distanceToCourse < 100.0D)
        {
        	changeCourse();
        }
	}
    

	/**
	 * Change to a new course target
	 */
    private void changeCourse() {
		//System.out.println("Boss changing course");
		
		if (this.travelCoords.isEmpty())
		{
			//System.out.println("Boss travel coords is empty, making a new list");
			makeTravelPath();
		}
		
		if (!this.travelCoords.isEmpty())
		{
			if (this.currentTravelCoordIndex >= travelCoords.size())
			{
				this.currentTravelCoordIndex = 0;
				this.travelPathRepetitions++;
				
				//System.out.println("Tower boss has repeated path " + travelPathRepetitions + " times.");
				
				// when we're in tantrum mode, this is a good time to check if we need to spawn more ghasts
				if (!checkGhastsAtTraps())
				{
					this.spawnGhastsAtTraps();
				}
			}
	
			this.courseX = travelCoords.get(currentTravelCoordIndex).posX;
			this.courseY = travelCoords.get(currentTravelCoordIndex).posY + HOVER_ALTITUDE;
			this.courseZ = travelCoords.get(currentTravelCoordIndex).posZ;
			
			this.currentTravelCoordIndex++;
		}

		
	}

    /**
     * Check if there are at least 4 ghasts near at least 2 traps.  Return false if not.
     */
	@SuppressWarnings("unchecked")
	private boolean checkGhastsAtTraps() {
		int trapsWithEnoughGhasts = 0;
		
		for (ChunkCoordinates trap : this.trapLocations)
		{
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(trap.posX, trap.posY, trap.posZ, trap.posX + 1, trap.posY + 1, trap.posZ + 1).expand(8D, 16D, 8D);
			
			List<EntityTFMiniGhast> nearbyGhasts = worldObj.getEntitiesWithinAABB(EntityTFMiniGhast.class, aabb);
			
			//System.out.println("Checking for ghasts.  There are " + nearbyGhasts.size() + " ghasts near this trap.");

			
			if (nearbyGhasts.size() >= 4)
			{
				trapsWithEnoughGhasts++;
			}
		}
		
		//System.out.println("Checking traps for ghasts.  There are " + trapsWithEnoughGhasts + " traps with enough ghasts.");
		
		return trapsWithEnoughGhasts >= 1;
		
	}

	private void makeTravelPath() 
	{
		ArrayList<ChunkCoordinates> potentialPoints;

		int px = MathHelper.floor_double(this.posX);
		int py = MathHelper.floor_double(this.posY);
		int pz = MathHelper.floor_double(this.posZ);
		
		if (!this.noTrapMode)
		{
			// make a copy of the trap locations list
			potentialPoints = new ArrayList<ChunkCoordinates>(this.trapLocations);
		}
		else
		{
			potentialPoints = new ArrayList<ChunkCoordinates>();
			potentialPoints.add(new ChunkCoordinates(px + 20, py - HOVER_ALTITUDE, pz));
			potentialPoints.add(new ChunkCoordinates(px, py - HOVER_ALTITUDE, pz - 20));
			potentialPoints.add(new ChunkCoordinates(px - 20, py - HOVER_ALTITUDE, pz));
			potentialPoints.add(new ChunkCoordinates(px, py - HOVER_ALTITUDE, pz + 20));
			
			//System.out.println("Adding fake points to the potentials list");
		}
		
		// put the potential points on our travel coords list in random order
		travelCoords.clear();

		while (!potentialPoints.isEmpty())
		{
			int index = rand.nextInt(potentialPoints.size());

			travelCoords.add(potentialPoints.get(index));

			potentialPoints.remove(index);
		}
		
		if (this.noTrapMode)
		{
			// if in no trap mode, head back to the middle when we're done
			travelCoords.add(new ChunkCoordinates(px, py - HOVER_ALTITUDE, pz));	
		}
	}

	/**
     * Spit a fireball
     */
	protected void spitFireball() {
		double offsetX = this.targetedEntity.posX - this.posX;
		double offsetY = this.targetedEntity.boundingBox.minY + (double)(this.targetedEntity.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
		double offsetZ = this.targetedEntity.posZ - this.posZ;
		
		// fireball sound effect
		this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1008, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
		
		EntityTFUrGhastFireball entityFireball = new EntityTFUrGhastFireball(this.worldObj, this, offsetX, offsetY, offsetZ);
		entityFireball.field_92057_e = 1;
		double shotSpawnDistance = 8.5D;
		Vec3 lookVec = this.getLook(1.0F);
		entityFireball.posX = this.posX + lookVec.xCoord * shotSpawnDistance;
		entityFireball.posY = this.posY + (double)(this.height / 2.0F) + lookVec.yCoord * shotSpawnDistance;
		entityFireball.posZ = this.posZ + lookVec.zCoord * shotSpawnDistance;
		this.worldObj.spawnEntityInWorld(entityFireball);
		
		for (int i = 0; i < 2; i++)
		{
			entityFireball = new EntityTFUrGhastFireball(this.worldObj, this, offsetX + (rand.nextFloat() - rand.nextFloat()) * 8, offsetY, offsetZ + (rand.nextFloat() - rand.nextFloat()) * 8);
			entityFireball.field_92057_e = 1;
			entityFireball.posX = this.posX + lookVec.xCoord * shotSpawnDistance;
			entityFireball.posY = this.posY + (double)(this.height / 2.0F) + lookVec.yCoord * shotSpawnDistance;
			entityFireball.posZ = this.posZ + lookVec.zCoord * shotSpawnDistance;
			this.worldObj.spawnEntityInWorld(entityFireball);
		}

	}
	
	/**
	 * Scan a few chunks around us for ghast trap blocks and if we find any, add them to our list
	 */
	private void scanForTrapsTwice()
	{
		int scanRangeXZ = 48;
		int scanRangeY = 32;

		int px = MathHelper.floor_double(this.posX);
		int py = MathHelper.floor_double(this.posY);
		int pz = MathHelper.floor_double(this.posZ);

		scanForTraps(scanRangeXZ, scanRangeY, px, py, pz);
		
		if (trapLocations.size() > 0)
		{
			// average the location of the traps we've found, and scan again from there
			int ax = 0, ay = 0, az = 0;

			for(ChunkCoordinates trapCoords : trapLocations)
			{
				ax += trapCoords.posX;
				ay += trapCoords.posY;
				az += trapCoords.posZ;
			}

			ax /= trapLocations.size();
			ay /= trapLocations.size();
			az /= trapLocations.size();

			scanForTraps(scanRangeXZ, scanRangeY, ax, ay, az);
		}
	}

	/**
	 * Scan in the specified range for traps
	 */
	protected void scanForTraps(int scanRangeXZ, int scanRangeY, int px, int py, int pz) {
		for (int sx = -scanRangeXZ; sx <= scanRangeXZ; sx++) {
			for (int sz = -scanRangeXZ; sz <= scanRangeXZ; sz++) {
				for (int sy = -scanRangeY; sy <= scanRangeY; sy++) {
					if (isTrapAt(px + sx, py + sy, pz + sz)) {
						// add to list
						ChunkCoordinates trapCoords = new ChunkCoordinates(px + sx, py + sy, pz + sz);
						if (!trapLocations.contains(trapCoords))
						{
							trapLocations.add(trapCoords);
						}
					}
				}
			}
		}
	}
	
	private boolean isTrapAt(int x, int y, int z)
	{
		return worldObj.blockExists(x, y, z) 
				&& worldObj.getBlock(x, y, z) == TFBlocks.towerDevice 
				&& (worldObj.getBlockMetadata(x, y, z) == BlockTFTowerDevice.META_GHASTTRAP_INACTIVE || worldObj.getBlockMetadata(x, y, z) == BlockTFTowerDevice.META_GHASTTRAP_ACTIVE);
	}
    
    /**
     * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
     */
    @Override
	public boolean isBurning()
    {
    	// never burn
    	return false;
    }

    /**
     * No point in ever being pushed
     */
	@Override
	public boolean canBePushed() {
		return false;
	}
	
    /**
     * Are we throwing a tantrum?
     */
    public boolean isInTantrum()
    {
        return this.dataWatcher.getWatchableObjectByte(DATA_TANTRUM) != (byte)0;
    }

    /**
     * Sets whether we are throwing a tantrum
     */
    public void setInTantrum(boolean par1)
    {
    	this.dataWatcher.updateObject(DATA_TANTRUM, par1 ? ((byte)-1) : (byte)0);
    	
    	// can we just reset this each time it is called?
    	this.damageUntilNextPhase = 48;
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 16F;
    }
    
    /**
     * Gets the pitch of living sounds in living entities.
     */
    protected float getSoundPitch()
    {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.5F;
    }

	/**
	 * Needed for boss health bar on the client
	 */
//	@Override
//	public int getBossHealth() {
//        return this.dataWatcher.getWatchableObjectInt(DATA_BOSSHEALTH);
//	}
	
    @Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setBoolean("inTantrum", this.isInTantrum());
        super.writeEntityToNBT(nbttagcompound);
    }

    @Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        this.setInTantrum(nbttagcompound.getBoolean("inTantrum"));
    }
    
    /**
     * handles entity death timer, experience orb and particle creation
     */
    protected void onDeathUpdate()
    {
    	super.onDeathUpdate();
    	
        if (this.deathTime == 20 && !worldObj.isRemote)
        {
        	// make chest
        	ChunkCoordinates chestCoords = this.findChestCoords();
        	TFTreasure.darktower_boss.generate(worldObj, null, chestCoords.posX, chestCoords.posY, chestCoords.posZ);
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
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).triggerAchievement(TFAchievementPage.twilightProgressUrghast);
			
		}

		// mark the tower as defeated
		if (!worldObj.isRemote && worldObj.provider instanceof WorldProviderTwilightForest) {
        	ChunkCoordinates chestCoords = this.findChestCoords();
			int dx = chestCoords.posX;
			int dy = chestCoords.posY;
			int dz = chestCoords.posZ;

			ChunkProviderTwilightForest chunkProvider = ((WorldProviderTwilightForest)worldObj.provider).getChunkProvider();
			TFFeature nearbyFeature = ((TFWorldChunkManager)worldObj.provider.worldChunkMgr).getFeatureAt(dx, dz, worldObj);

			if (nearbyFeature == TFFeature.darkTower) {
				chunkProvider.setStructureConquered(dx, dy, dz, true);
			}
		}
	}

    private ChunkCoordinates findChestCoords() {
    	if (trapLocations.size() > 0)
    	{
    		// average the location of the traps we've found, and scan again from there
    		int ax = 0, ay = 0, az = 0;

    		for(ChunkCoordinates trapCoords : trapLocations)
    		{
    			ax += trapCoords.posX;
    			ay += trapCoords.posY;
    			az += trapCoords.posZ;
    		}

    		ax /= trapLocations.size();
    		ay /= trapLocations.size();
    		az /= trapLocations.size();


    		return new ChunkCoordinates(ax, ay + 2, az);
    	}
    	else
    	{
    		return new ChunkCoordinates(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
    	}
	}
}
