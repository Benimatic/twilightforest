package twilightforest.entity.boss;

import java.util.*;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.TFTreasure;
import twilightforest.TwilightForestMod;
import twilightforest.block.BlockTFTowerDevice;
import twilightforest.block.TFBlocks;
import twilightforest.block.enums.TowerDeviceVariant;
import twilightforest.client.particle.TFParticleType;
import twilightforest.entity.EntityTFMiniGhast;
import twilightforest.entity.EntityTFTowerGhast;
import twilightforest.world.ChunkGeneratorTwilightForest;
import twilightforest.world.TFBiomeProvider;
import twilightforest.world.TFWorld;

public class EntityTFUrGhast extends EntityTFTowerGhast implements IBossDisplayData {

	private static final DataParameter<Boolean> DATA_TANTRUM = EntityDataManager.createKey(EntityTFUrGhast.class, DataSerializers.BOOLEAN);

	//private static final int CRUISING_ALTITUDE = 235; // absolute cruising altitude
	private static final int HOVER_ALTITUDE = 20; // how far, relatively, do we hover over ghast traps?


    public double courseX;
    public double courseY;
    public double courseZ;
    
    private final Set<BlockPos> trapLocations = new HashSet<>();
    private ArrayList<BlockPos> travelCoords = new ArrayList<>();
    
    int currentTravelCoordIndex;
    
    int travelPathRepetitions;
    int desiredRepetitions;
    
    int nextTantrumCry;
    
    private float damageUntilNextPhase = 45; // how much damage can we take before we toggle tantrum mode
    private boolean noTrapMode; // are there no traps nearby?  just float around

	public EntityTFUrGhast(World par1World) 
	{
		super(par1World);
        this.setSize(14.0F, 18.0F);
    	this.wanderFactor = 32.0F;
    	this.noClip = true;
    	this.setInTantrum(false);
        this.experienceValue = 317;
	}
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(250);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(128.0D);
    }
	
	@Override
    protected void entityInit()
    {
        super.entityInit();
		dataManager.register(DATA_TANTRUM, false);
    }

    @Override
	protected boolean canDespawn()
    {
        return false;
    }
	
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
                EnumParticleTypes explosionType = rand.nextBoolean() ?  EnumParticleTypes.EXPLOSION_HUGE : EnumParticleTypes.EXPLOSION_NORMAL;
                world.spawnParticle(explosionType, (posX + rand.nextFloat() * width * 2.0F) - width, posY + rand.nextFloat() * height, (posZ + rand.nextFloat() * width * 2.0F) - width, d, d1, d2);
            }
		}
    }

	@Override
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

    	float oldHealth = getHealth();
    	
        if ("fireball".equals(source.getDamageType()) && source.getEntity() instanceof EntityPlayer)
        {
        	// 'hide' fireball attacks so that we don't take 1000 damage.
        	attackSuccessful = super.attackEntityFrom(DamageSource.causeThrownDamage(source.getSourceOfDamage(), source.getEntity()), damage);
        }
        else
        {
        	attackSuccessful = super.attackEntityFrom(source, damage);
        }

        float lastDamage = getHealth() - oldHealth;
 
        if (!world.isRemote)
        {
        	if (this.hurtTime == this.maxHurtTime)
        	{
        		this.damageUntilNextPhase -= lastDamage;

				TwilightForestMod.LOGGER.debug("Urghast Attack successful, {} damage until phase switch.", this.damageUntilNextPhase);

        		if (this.damageUntilNextPhase <= 0)
        		{
        			this.switchPhase();
        		}
        	}
        	else
        	{
				TwilightForestMod.LOGGER.debug("Urghast Attack fail with {} type attack for {} damage", source.damageType, damage);
        	}
        }

        return attackSuccessful;
    }

	private void switchPhase() {
		if (this.isInTantrum()) {
			this.setInTantrum(false);
		} else {
			this.startTantrum();
		}
		
		this.damageUntilNextPhase = 48;
	}

	/**
     * Start throwing a tantrum
     */
	private void startTantrum() {
		this.setInTantrum(true);
		
		// start raining
		int rainTime = 300 * 20;
		
        WorldInfo worldInfo = world.getWorldInfo();
		
		worldInfo.setRaining(true);
		worldInfo.setThundering(true);
		worldInfo.setRainTime(rainTime);
		worldInfo.setThunderTime(rainTime);
		
		spawnGhastsAtTraps();
	}

	/**
	 * Spawn ghasts at two of the traps
	 */
	private void spawnGhastsAtTraps() {
		// spawn ghasts around two of the traps
		List<BlockPos> ghastSpawns = new ArrayList<BlockPos>(this.trapLocations);
		Collections.shuffle(ghastSpawns);
		
		int numSpawns = Math.min(2, ghastSpawns.size());

		for (int i = 0; i < numSpawns; i++)
		{
			BlockPos spawnCoord = ghastSpawns.get(i);
			spawnMinionGhastsAt(spawnCoord.getX(), spawnCoord.getY(), spawnCoord.getZ());
		}
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
    	
		// lightning strike
		this.world.addWeatherEffect(new EntityLightningBolt(world, x, y + 4, z, false));

    	
    	for (int i = 0; i < tries; i++)
    	{
    		EntityTFMiniGhast minion = new EntityTFMiniGhast(world);
    		
    		double sx = x + ((rand.nextDouble() - rand.nextDouble()) * rangeXZ);
    		double sy = y + (rand.nextDouble() * rangeY);
    		double sz = z + ((rand.nextDouble() - rand.nextDouble()) * rangeXZ);
    		
    		minion.setLocationAndAngles(sx, sy, sz, this.world.rand.nextFloat() * 360.0F, 0.0F);
    		minion.makeBossMinion();

    		if (minion.getCanSpawnHere())
    		{
    			this.world.spawnEntity(minion);
    			minion.spawnExplosionParticle();
    		}

    		if (++spawns >= maxSpawns)
    		{
    			break;
    		}
    	}
	}

    @Override
	protected void updateAITasks()
    {
    	super.updateAITasks();
    	this.detachHome();

        // despawn mini ghasts that are in our AABB
		List<EntityTFMiniGhast> nearbyGhasts = world.getEntitiesWithinAABB(EntityTFMiniGhast.class, this.getEntityBoundingBox().expand(1, 1, 1));
		for (EntityTFMiniGhast ghast : nearbyGhasts)
		{
			ghast.setDead();
			this.heal(2);
		}
        
        // trap locations?
        if (this.trapLocations.isEmpty() && !this.noTrapMode)
        {
        	this.scanForTrapsTwice();
        }
        
        // did we find any traps?
        if (this.trapLocations.isEmpty() && !this.noTrapMode)
        {
			TwilightForestMod.LOGGER.debug("Ur-ghast cannot find traps nearby, entering trap-less mode");
        	this.noTrapMode = true;
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
        		distanceToWaypoint = (double)MathHelper.sqrt(distanceToWaypoint);

        		double speed = 0.05D;
        		this.motionX += offsetX / distanceToWaypoint * speed;
        		this.motionY += offsetY / distanceToWaypoint * speed;
        		this.motionZ += offsetZ / distanceToWaypoint * speed;
        	}
    }

	private void doTantrumDamageEffects() {
		// harm player below
		AxisAlignedBB below = this.getEntityBoundingBox().offset(0, -16, 0).expand(0, 16, 0);

		List<EntityPlayer> playersBelow = world.getEntitiesWithinAABB(EntityPlayer.class, below);

		for (EntityPlayer player : playersBelow)
		{
			if (world.canSeeSky(new BlockPos(player)))
			{
				player.attackEntityFrom(DamageSource.anvil, 3);
			}
		}

		// also suck up mini ghasts
		List<EntityTFMiniGhast> ghastsBelow = world.getEntitiesWithinAABB(EntityTFMiniGhast.class, below);

		for (EntityTFMiniGhast ghast : ghastsBelow)
		{
			ghast.motionY += 1;
		}
	}

    private void shedTear()
    {
		TwilightForestMod.proxy.spawnParticle(this.world, TFParticleType.BOSS_TEAR, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0, 0, 0);
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
	private boolean checkGhastsAtTraps() {
		int trapsWithEnoughGhasts = 0;
		
		for (BlockPos trap : this.trapLocations)
		{
			AxisAlignedBB aabb = new AxisAlignedBB(trap, trap.add(1, 1, 1)).expand(8D, 16D, 8D);
			
			List<EntityTFMiniGhast> nearbyGhasts = world.getEntitiesWithinAABB(EntityTFMiniGhast.class, aabb);
			
			if (nearbyGhasts.size() >= 4)
			{
				trapsWithEnoughGhasts++;
			}
		}
		
		return trapsWithEnoughGhasts >= 1;
	}

	private void makeTravelPath() 
	{
		ArrayList<BlockPos> potentialPoints;

		int px = MathHelper.floor(this.posX);
		int py = MathHelper.floor(this.posY);
		int pz = MathHelper.floor(this.posZ);
		
		if (!this.noTrapMode)
		{
			// make a copy of the trap locations list
			potentialPoints = new ArrayList<BlockPos>(this.trapLocations);
		}
		else
		{
			potentialPoints = new ArrayList<BlockPos>();
			potentialPoints.add(new BlockPos(px + 20, py - HOVER_ALTITUDE, pz));
			potentialPoints.add(new BlockPos(px, py - HOVER_ALTITUDE, pz - 20));
			potentialPoints.add(new BlockPos(px - 20, py - HOVER_ALTITUDE, pz));
			potentialPoints.add(new BlockPos(px, py - HOVER_ALTITUDE, pz + 20));
			
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
			travelCoords.add(new BlockPos(px, py - HOVER_ALTITUDE, pz));
		}
	}

	@Override
	protected void spitFireball() {
		double offsetX = this.getAttackTarget().posX - this.posX;
		double offsetY = this.getAttackTarget().getEntityBoundingBox().minY + (double)(this.getAttackTarget().height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
		double offsetZ = this.getAttackTarget().posZ - this.posZ;
		
		// fireball sound effect
		this.world.playEvent(1008, new BlockPos(this), 0);
		
		EntityTFUrGhastFireball entityFireball = new EntityTFUrGhastFireball(this.world, this, offsetX, offsetY, offsetZ);
		entityFireball.explosionPower = 1;
		double shotSpawnDistance = 8.5D;
		Vec3d lookVec = this.getLook(1.0F);
		entityFireball.posX = this.posX + lookVec.xCoord * shotSpawnDistance;
		entityFireball.posY = this.posY + (double)(this.height / 2.0F) + lookVec.yCoord * shotSpawnDistance;
		entityFireball.posZ = this.posZ + lookVec.zCoord * shotSpawnDistance;
		this.world.spawnEntity(entityFireball);
		
		for (int i = 0; i < 2; i++)
		{
			entityFireball = new EntityTFUrGhastFireball(this.world, this, offsetX + (rand.nextFloat() - rand.nextFloat()) * 8, offsetY, offsetZ + (rand.nextFloat() - rand.nextFloat()) * 8);
			entityFireball.explosionPower = 1;
			entityFireball.posX = this.posX + lookVec.xCoord * shotSpawnDistance;
			entityFireball.posY = this.posY + (double)(this.height / 2.0F) + lookVec.yCoord * shotSpawnDistance;
			entityFireball.posZ = this.posZ + lookVec.zCoord * shotSpawnDistance;
			this.world.spawnEntity(entityFireball);
		}

	}
	
	/**
	 * Scan a few chunks around us for ghast trap blocks and if we find any, add them to our list
	 */
	private void scanForTrapsTwice()
	{
		int scanRangeXZ = 48;
		int scanRangeY = 32;

		scanForTraps(scanRangeXZ, scanRangeY, new BlockPos(this));
		
		if (trapLocations.size() > 0)
		{
			// average the location of the traps we've found, and scan again from there
			int ax = 0, ay = 0, az = 0;

			for(BlockPos trapCoords : trapLocations)
			{
				ax += trapCoords.getX();
				ay += trapCoords.getY();
				az += trapCoords.getZ();
			}

			ax /= trapLocations.size();
			ay /= trapLocations.size();
			az /= trapLocations.size();

			scanForTraps(scanRangeXZ, scanRangeY, new BlockPos(ax, ay, az));
		}
	}

	protected void scanForTraps(int scanRangeXZ, int scanRangeY, BlockPos pos) {
		for (int sx = -scanRangeXZ; sx <= scanRangeXZ; sx++) {
			for (int sz = -scanRangeXZ; sz <= scanRangeXZ; sz++) {
				for (int sy = -scanRangeY; sy <= scanRangeY; sy++) {
					BlockPos trapCoords = pos.add(sx, sy, sz);
					if (isTrapAt(trapCoords)) {
						trapLocations.add(trapCoords);
					}
				}
			}
		}
	}
	
	private boolean isTrapAt(BlockPos pos)
	{
		IBlockState inactive = TFBlocks.towerDevice.getDefaultState().withProperty(BlockTFTowerDevice.VARIANT, TowerDeviceVariant.GHASTTRAP_INACTIVE);
		IBlockState active = TFBlocks.towerDevice.getDefaultState().withProperty(BlockTFTowerDevice.VARIANT, TowerDeviceVariant.GHASTTRAP_ACTIVE);
		return world.isBlockLoaded(pos)
				&& (world.getBlockState(pos) == inactive || world.getBlockState(pos) == active);
	}

    @Override
	public boolean isBurning()
    {
    	return false;
    }

	@Override
	public boolean canBePushed() {
		return false;
	}
	
    public boolean isInTantrum()
    {
        return dataManager.get(DATA_TANTRUM);
    }

    public void setInTantrum(boolean par1)
    {
    	dataManager.set(DATA_TANTRUM, par1);
    	this.damageUntilNextPhase = 48;
    }

    @Override
    protected float getSoundVolume()
    {
        return 16F;
    }
    
    @Override
    protected float getSoundPitch()
    {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.5F;
    }

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

	@Override
    protected void onDeathUpdate()
    {
    	super.onDeathUpdate();
    	
        if (this.deathTime == 20 && !world.isRemote)
        {
        	BlockPos chestCoords = this.findChestCoords();
        	TFTreasure.darktower_boss.generateChest(world, chestCoords);
        }
    }
    
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightProgressUrghast);
			
		}

		// mark the tower as defeated
		if (!world.isRemote && TFWorld.getChunkGenerator(world) instanceof ChunkGeneratorTwilightForest) {
        	BlockPos chestCoords = this.findChestCoords();
			int dx = chestCoords.getX();
			int dy = chestCoords.getY();
			int dz = chestCoords.getZ();

			ChunkGeneratorTwilightForest generator = (ChunkGeneratorTwilightForest) TFWorld.getChunkGenerator(world);
			TFFeature nearbyFeature = ((TFBiomeProvider)world.provider.getBiomeProvider()).getFeatureAt(dx, dz, world);

			if (nearbyFeature == TFFeature.darkTower) {
				generator.setStructureConquered(dx, dy, dz, true);
			}
		}
	}

    private BlockPos findChestCoords() {
    	if (trapLocations.size() > 0)
    	{
    		// average the location of the traps we've found, and scan again from there
    		int ax = 0, ay = 0, az = 0;

    		for(BlockPos trapCoords : trapLocations)
    		{
    			ax += trapCoords.getX();
    			ay += trapCoords.getY();
    			az += trapCoords.getZ();
    		}

    		ax /= trapLocations.size();
    		ay /= trapLocations.size();
    		az /= trapLocations.size();


    		return new BlockPos(ax, ay + 2, az);
    	}
    	else
    	{
    		return new BlockPos(this);
    	}
	}
}
