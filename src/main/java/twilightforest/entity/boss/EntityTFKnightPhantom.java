package twilightforest.entity.boss;

import java.util.List;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.TFTreasure;
import twilightforest.TwilightForestMod;
import twilightforest.item.TFItems;
import twilightforest.world.ChunkGeneratorTwilightForest;
import twilightforest.world.TFBiomeProvider;
import twilightforest.world.TFWorld;
import twilightforest.world.WorldProviderTwilightForest;

public class EntityTFKnightPhantom extends EntityFlying implements IMob 
{
	
	private static final float CIRCLE_SMALL_RADIUS = 2.5F;
	private static final float CIRCLE_LARGE_RADIUS = 8.5F;
	private static final DataParameter<Boolean> FLAG_CHARGING = EntityDataManager.createKey(EntityTFKnightPhantom.class, DataSerializers.BOOLEAN);
	private int number;
	private int ticksProgress;
	private Formation currentFormation;
	
    private BlockPos homePosition = new BlockPos(0, -1, 0);
    /** If -1 there is no maximum distance */
    private float maximumHomeDistance = -1.0F;

	private BlockPos chargePos = BlockPos.ORIGIN;

	private enum Formation { HOVER, LARGE_CLOCKWISE, SMALL_CLOCKWISE, LARGE_ANTICLOCKWISE, SMALL_ANTICLOCKWISE, CHARGE_PLUSX, CHARGE_MINUSX, CHARGE_PLUSZ, CHARGE_MINUSZ, WAITING_FOR_LEADER, ATTACK_PLAYER_START,  ATTACK_PLAYER_ATTACK};

	public EntityTFKnightPhantom(World par1World) {
		super(par1World);

		this.setSize(1.5F, 3.0F);

		this.noClip = true;
        this.isImmuneToFire = true;
        
		this.currentFormation = Formation.HOVER;
		
		this.experienceValue = 93;
		
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(TFItems.knightlySword));
        this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(TFItems.phantomPlate));
        this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(TFItems.phantomHelm));
	}
	
	@Override
    protected void entityInit()
    {
        super.entityInit();
        dataManager.register(FLAG_CHARGING, false);
    }

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(35.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }
	
    @Override
	protected boolean canDespawn()
    {
        return false;
    }

	@Override
    public boolean attackEntityFrom(DamageSource source, float par2)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else if (source == DamageSource.inWall)
        {
            return false;
        }
        else
        {
            return super.attackEntityFrom(source, par2);
        }
    }

	@Override
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	
    	if (this.isChargingAtPlayer())
    	{
	    	// make particles
	        for (int i = 0; i < 4; ++i)
	        {
	        	Item particleID = this.rand.nextBoolean() ? TFItems.phantomHelm : TFItems.knightlySword;
	        	
			    world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX + (this.rand.nextFloat() * this.rand.nextFloat() - 0.5D) * this.width, this.posY + this.rand.nextFloat() * (this.height - 0.75D) + 0.5D, this.posZ + (this.rand.nextFloat() * this.rand.nextFloat() - 0.5D) * this.width, 0, -0.1, 0, Item.getIdFromItem(particleID));
			    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextFloat() * this.rand.nextFloat() - 0.5D) * this.width, this.posY + this.rand.nextFloat() * (this.height - 0.75D) + 0.5D, this.posZ + (this.rand.nextFloat() * this.rand.nextFloat() - 0.5D) * this.width, 0, 0.1, 0);
	        }
    	}
    }
    
	@Override
    protected void onDeathUpdate()
    {
		super.onDeathUpdate();
		
        for (int i = 0; i < 20; ++i)
        {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2);
        }


    }
	
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightProgressKnights);
		}
		
		// mark the stronghold as defeated
		if (!world.isRemote && TFWorld.getChunkGenerator(world) instanceof ChunkGeneratorTwilightForest) {
			int dx = getHomePosition().getX();
			int dy = getHomePosition().getY();
			int dz = getHomePosition().getZ();
			
			ChunkGeneratorTwilightForest generator = (ChunkGeneratorTwilightForest) TFWorld.getChunkGenerator(world);
			TFFeature nearbyFeature = ((TFBiomeProvider)world.provider.getBiomeProvider()).getFeatureAt(dx, dz, world);
			
			if (nearbyFeature == TFFeature.tfStronghold) {
				generator.setStructureConquered(dx, dy, dz, true);
			}
		}
		
        
        // make treasure for killing the last knight
        if (!this.world.isRemote) {
        	// am I the last one?!?!
    		List<EntityTFKnightPhantom> nearbyKnights = getNearbyKnights();
        	if (nearbyKnights.size() <= 1)
        	{
        		// 	make a treasure!'
        		//System.out.println("I think I'm the last one!");
        		this.makeATreasure();
        	}
        }

	}

	/**
	 * Make a treasure for when we're dead
	 */
	private void makeATreasure() {
		if (this.getHomePosition().getY() != -1) {
			// if we have a proper home position, generate the treasure there
			TFTreasure.stronghold_boss.generateChest(world, null, getHomePosition().posX, getHomePosition().posY - 1, getHomePosition().posZ);
		} else {
			// if not, spawn it right where we are
			int px = MathHelper.floor(this.lastTickPosX);
			int py = MathHelper.floor(this.lastTickPosY);
			int pz = MathHelper.floor(this.lastTickPosZ);
			
			TFTreasure.stronghold_boss.generateChest(world, null, px, py, pz);
		}
	}

	@Override
    protected void updateAITasks()
    {
        if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
            this.setDead();
        }

        this.despawnEntity();
        
        this.noClip = this.ticksProgress % 20 != 0;
        
        ticksProgress++;
        
        if (ticksProgress >= getMaxTicksForFormation())
        {
        	switchToNextFormation();
        }

        float seekRange = this.isChargingAtPlayer() ? 24 : 9;

        EntityPlayer target = this.world.getNearestAttackablePlayer(this, seekRange, seekRange);
        //EntityPlayer target = this.world.getClosestPlayerToEntity(this, seekRange);

        if (target != null && this.currentFormation == Formation.ATTACK_PLAYER_START)
        {
			BlockPos targetPos = new BlockPos(target.lastTickPosX, target.lastTickPosY, target.lastTickPosZ);

        	if (this.isWithinHomeArea(targetPos))
        	{
				this.chargePos = targetPos;
        	}
        	else
        	{
				this.chargePos = getHomePosition();
        	}
        }

        Vec3d dest = this.getDestination();

//        if (this.getNumber() == 0)
//        {
//        	System.out.printf("Knight Phantom %d moving towards %f, %f, %f.  Is in formation %s, progress %d.\n", this.getNumber(), dest.xCoord, dest.yCoord, dest.zCoord, this.currentFormation, ticksProgress);
//        	System.out.printf("Knight Phantom %d at position %f, %f, %f.\n", this.getNumber(), this.posX, this.posY, this.posZ);
//        }
        
        double moveX = dest.xCoord - this.posX;
        double moveY = dest.yCoord - this.posY;
        double moveZ = dest.zCoord - this.posZ;
        
        double factor = moveX * moveX + moveY * moveY + moveZ * moveZ;
        
        factor = (double)MathHelper.sqrt(factor);

        double speed = 0.1D;//this.isChargingAtPlayer() ? 0.1D : 0.05D;
        
		this.motionX += moveX / factor * speed;
        this.motionY += moveY / factor * speed;
        this.motionZ += moveZ / factor * speed;
        
        if (target != null)
        {
        	this.faceEntity(target, 10.0F, 500.0F);
        	
            if (target.isEntityAlive())
            {
                float f1 = target.getDistanceToEntity(this);

                if (this.getEntitySenses().canSee(target))
                {
                    this.attackEntity(target, f1);
                }
            }
            
            // launch axe at the appropriate time in our routine
            if (isAxeKnight() && this.currentFormation == Formation.ATTACK_PLAYER_ATTACK && (this.ticksProgress % 4) == 0)
            {
            	this.launchAxeAt(target);
            }
            
            // same for picks
            if (isPickKnight() && this.currentFormation == Formation.ATTACK_PLAYER_ATTACK && (this.ticksProgress % 4) == 0)
            {
            	this.launchPicks();
            }

        }
        
        
        //this.setPosition(dest.xCoord, dest.yCoord, dest.zCoord);
    }
    
    
    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity par1Entity, float par2)
    {
    	
        if (this.attackTime <= 0 && par2 < 2.0F && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY)
        {
            this.attackTime = 20;
            this.attackEntityAsMob(par1Entity);

        }
    }

	@Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        float f = getAttackDamage();
        int i = 0;

        if (par1Entity instanceof EntityLivingBase)
        {
            f += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)par1Entity);
            i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)par1Entity);
        }

        boolean flag = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag)
        {
            if (i > 0)
            {
                par1Entity.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
            {
                par1Entity.setFire(j * 4);
            }

            if (par1Entity instanceof EntityLivingBase)
            {
                //EnchantmentThorns.func_151367_b(this, (EntityLivingBase)par1Entity, this.rand);
            }
        }

        return flag;
    }


    /**
     * How much damage do we deal, per attack?
     * @return
     */
	private float getAttackDamage() {
		float damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
		
		if (this.isChargingAtPlayer())
		{
			damage += 7; // todo 1.9 attribute
		}
		
		return damage;
	}

	/**
     * Fires an axe at the target
     * @param targetedEntity
     */
	protected void launchAxeAt(Entity targetedEntity) {
		float bodyFacingAngle = ((renderYawOffset * 3.141593F) / 180F);
		double sx = posX + (MathHelper.cos(bodyFacingAngle) * 1);
		double sy = posY + (height * 0.82);
		double sz = posZ + (MathHelper.sin(bodyFacingAngle) * 1);
		
		double tx = targetedEntity.posX - sx;
		double ty = (targetedEntity.getEntityBoundingBox().minY + (double)(targetedEntity.height / 2.0F)) - (posY + height / 2.0F);
		double tz = targetedEntity.posZ - sz;
		
		playSound(SoundEvents.ENTITY_SNOWBALL_THROW, getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.4F);
		EntityTFThrownAxe projectile = new EntityTFThrownAxe(world, this);
		
		float speed = 0.75F;

		projectile.setThrowableHeading(tx, ty, tz, speed, 1.0F);
		
		projectile.setLocationAndAngles(sx, sy, sz, rotationYaw, rotationPitch);

		world.spawnEntity(projectile);
	}
	
	protected void launchPicks()
	{
		world.playSoundAtEntity(this, "random.bow", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.4F);

		for (int i = 0; i < 8; i++)
		{
			float throwAngle = i * 3.14159165F / 4F; 

			
			double sx = posX + (MathHelper.cos(throwAngle) * 1);
			double sy = posY + (height * 0.82);
			double sz = posZ + (MathHelper.sin(throwAngle) * 1);

			double vx = MathHelper.cos(throwAngle);
			double vy = 0;
			double vz = MathHelper.sin(throwAngle);

			
			EntityTFThrownPick projectile = new EntityTFThrownPick(world, this);
			

			projectile.setLocationAndAngles(sx, sy, sz, i * 45F, rotationPitch);
			
			float speed = 0.5F;

			projectile.setThrowableHeading(vx, vy, vz, speed, 1.0F);

			world.spawnEntity(projectile);
		}
	}

	@Override
    public boolean canBePushed()
    {
        return true;
    }
    
    @Override
    public void knockBack(Entity par1Entity, float damage, double par3, double par5)
    {
        this.isAirBorne = true;
        float f = MathHelper.sqrt(par3 * par3 + par5 * par5);
        float distance = 0.2F;
        this.motionX /= 2.0D;
        this.motionY /= 2.0D;
        this.motionZ /= 2.0D;
        this.motionX -= par3 / (double)f * (double)distance;
        this.motionY += (double)distance;
        this.motionZ -= par5 / (double)f * (double)distance;

        if (this.motionY > 0.4000000059604645D)
        {
            this.motionY = 0.4000000059604645D;
        }
    }

    /**
     * Called each time the current formation ends.
     * 
     * If the current knight is the leader knight, it will pick and broadcast a new formation.
     */
    public void switchToNextFormation() {
		List<EntityTFKnightPhantom> nearbyKnights = getNearbyKnights();

    	if (this.currentFormation == Formation.ATTACK_PLAYER_START)
    	{
    		this.switchToFormation(Formation.ATTACK_PLAYER_ATTACK);
    	}
    	else if (this.currentFormation == Formation.ATTACK_PLAYER_ATTACK)
    	{
    		if (nearbyKnights.size() > 1)
    		{
    			this.switchToFormation(Formation.WAITING_FOR_LEADER);
    		}
    		else
    		{
    			// random weapon switch!
    			switch (rand.nextInt(3))
    			{
    			case 0:
    		        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(TFItems.knightlySword));
    		        break;
    			case 1:
    		        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(TFItems.knightlyAxe));
    		        break;
    			case 2:
    		        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(TFItems.knightlyPick));
    		        break;
    			}
    			
    			this.switchToFormation(Formation.ATTACK_PLAYER_START);
    		}
    	}
    	else if (this.currentFormation == Formation.WAITING_FOR_LEADER)
    	{
			//System.out.println("I am done waiting");

    		// try to find a nearby knight and do what they're doing
    		if (nearbyKnights.size() > 1)
    		{
    			this.switchToFormation(nearbyKnights.get(1).currentFormation);
    			this.ticksProgress = nearbyKnights.get(1).ticksProgress;
    		}
    		else
    		{
    			this.switchToFormation(Formation.ATTACK_PLAYER_START);
    			//System.out.println("Can't find nearby knight, charging");
    		}
    	}
    	else
    	{

    		if (isThisTheLeader(nearbyKnights))
    		{
    			// pick a random formation
    			pickRandomFormation();

    			// broadcast it
    			broadcastMyFormation(nearbyKnights);

    			// if no one is charging
    			if (isNobodyCharging(nearbyKnights))
    			{
    				makeARandomKnightCharge(nearbyKnights);
    			}
    		}
    	}
    }


	private List<EntityTFKnightPhantom> getNearbyKnights() {
		return world.getEntitiesWithinAABB(EntityTFKnightPhantom.class, new AxisAlignedBB(this.posX, this.posY, this.posZ, this.posX + 1, this.posY + 1, this.posZ + 1).expand(32.0D, 8.0D, 32.0D));
	}

    /**
     * Pick a random formation.  Called by the leader when his current formation duration ends
     */
	private void pickRandomFormation() {
    	switch (rand.nextInt(8))
    	{
    	case 0:
    		currentFormation = Formation.SMALL_CLOCKWISE;
    		break;
    	case 1:
    		currentFormation = Formation.SMALL_ANTICLOCKWISE;
    		//currentFormation = Formation.LARGE_ANTICLOCKWISE;
    		break;
    	case 2:
    		currentFormation = Formation.SMALL_ANTICLOCKWISE;
    		break;
    	case 3:
    		currentFormation = Formation.CHARGE_PLUSX;
    		break;
    	case 4:
    		currentFormation = Formation.CHARGE_MINUSX;
    		break;
    	case 5:
    		currentFormation = Formation.CHARGE_PLUSZ;
    		break;
    	case 6:
    		currentFormation = Formation.CHARGE_MINUSZ;
    		break;
    	case 7:
    		currentFormation = Formation.SMALL_CLOCKWISE;
    		//currentFormation = Formation.LARGE_CLOCKWISE;
    		break;
    	}

    	this.switchToFormation(currentFormation);
    }

    /**
     * Check within 20ish squares.  If this phantom is the lowest numbered one, return true
     * @param nearbyKnights 
     */
    private boolean isThisTheLeader(List<EntityTFKnightPhantom> nearbyKnights) {

    	boolean iAmTheLowest = true;

    	//System.out.println("Checking " + nearbyKnights.size() + " knights to see if I'm the leader");


    	// find more knights

    	for (EntityTFKnightPhantom knight : nearbyKnights)
    	{
    		if (knight.getNumber() < this.getNumber())
    		{
    			iAmTheLowest = false;
    			break; // don't bother checking more
    		}
    	}

    	return iAmTheLowest;
    }

    private boolean isNobodyCharging(List<EntityTFKnightPhantom> nearbyKnights) {
    	boolean noCharge = true;
    	for (EntityTFKnightPhantom knight : nearbyKnights)
    	{
    		if (knight.isChargingAtPlayer())
    		{
    			noCharge = false;
    			break; // don't bother checking more
    		}
    	}

    	return noCharge;
    }

    /**
     * Tell a random knight from the list to charge
     */
    private void makeARandomKnightCharge(List<EntityTFKnightPhantom> nearbyKnights) {
    	int randomNum = rand.nextInt(nearbyKnights.size());

    	nearbyKnights.get(randomNum).switchToFormation(Formation.ATTACK_PLAYER_START);

    	//System.out.println("Telling knight " + randomNum + " to charge");
    }


    /**
     * Tell all the knights on the list to do something
     */
	private void broadcastMyFormation(List<EntityTFKnightPhantom> nearbyKnights) {
    	// find more knights

    	//System.out.println("Broadcasting to " + nearbyKnights.size() + " knights");

    	for (EntityTFKnightPhantom knight : nearbyKnights)
    	{
    		if (!knight.isChargingAtPlayer())
    		{
    			//System.out.println("Telling knight " + knight + " to switch");
    			knight.switchToFormation(this.currentFormation);
    		}
    	}

    	//System.out.println("knight phantom broadcast switch to formation " + this.currentFormation);
    }

    public boolean isChargingAtPlayer()
    {
    	return dataManager.get(FLAG_CHARGING);
    }

    public void setChargingAtPlayer(boolean flag)
    {
		dataManager.set(FLAG_CHARGING, flag);
    }
    
    @Override
	protected String getAmbientSound()
    {
        return TwilightForestMod.ID + ":mob.wraith.wraith";
    }

    @Override
	protected String getHurtSound()
    {
        return TwilightForestMod.ID + ":mob.wraith.wraith";
    }

    @Override
	protected String getDeathSound()
    {
        return TwilightForestMod.ID + ":mob.wraith.wraith";
    }


    private void switchToFormationByNumber(int formationNumber) {

    	currentFormation = Formation.values()[formationNumber];

    	this.ticksProgress = 0;
    }

    public void switchToFormation(Formation formation)
    {
    	//System.out.println("Knight " + this.getNumber() + " now switching to formation " + formation);

    	this.currentFormation = formation;
    	this.ticksProgress = 0;

    	this.setChargingAtPlayer(this.currentFormation == Formation.ATTACK_PLAYER_START || this.currentFormation == Formation.ATTACK_PLAYER_ATTACK);

    }

    public int getFormationAsNumber()
    {
    	return this.currentFormation.ordinal();	
    }


    public int getTicksProgress() {
    	return ticksProgress;
    }

	public void setTicksProgress(int ticksProgress) {
		this.ticksProgress = ticksProgress;
	}

	public int getMaxTicksForFormation()
	{
		switch (currentFormation)
		{
		default:
		case HOVER:
			return 90;
		case LARGE_CLOCKWISE:
			return 180;
		case SMALL_CLOCKWISE:
			return 90;
		case LARGE_ANTICLOCKWISE:
			return 180;
		case SMALL_ANTICLOCKWISE:
			return 90;
		case CHARGE_PLUSX:
			return 180;
		case CHARGE_MINUSX:
			return 180;
		case CHARGE_PLUSZ:
			return 180;
		case CHARGE_MINUSZ:
			return 180;
		case ATTACK_PLAYER_START:
			return 50;
		case ATTACK_PLAYER_ATTACK:
			return 50;
		case WAITING_FOR_LEADER:
			return 10;
		}
	}

	private Vec3d getDestination() {
		
		if (!this.hasHome())
		{
			// hmmm
		}

		switch (currentFormation)
		{
		case LARGE_CLOCKWISE:
			return getCirclePosition(CIRCLE_LARGE_RADIUS, true);
		case SMALL_CLOCKWISE:
			return getCirclePosition(CIRCLE_SMALL_RADIUS, true);
		case LARGE_ANTICLOCKWISE:
			return getCirclePosition(CIRCLE_LARGE_RADIUS, false);
		case SMALL_ANTICLOCKWISE:
			return getCirclePosition(CIRCLE_SMALL_RADIUS, false);
		case CHARGE_PLUSX:
			return getMoveAcrossPosition(true, true);
		case CHARGE_MINUSX:
			return getMoveAcrossPosition(false, true);
		case CHARGE_PLUSZ:
			return getMoveAcrossPosition(true, false);
		case ATTACK_PLAYER_START:
		case HOVER:
			return getHoverPosition(CIRCLE_LARGE_RADIUS);
		case CHARGE_MINUSZ:
			return getMoveAcrossPosition(false, false);
		case WAITING_FOR_LEADER:
			return getLoiterPosition();
		case ATTACK_PLAYER_ATTACK:
			return getAttackPlayerPosition();
		default:
			return getLoiterPosition();
		}
	}

	private Vec3d getMoveAcrossPosition(boolean plus, boolean alongX) {
		
		float offset0 = (this.getNumber() * 3F) - 7.5F;
		
		float offset1;
		
		if (this.ticksProgress < 60)
		{
			offset1 = -7F;
		}
		else
		{
			offset1 = -7F + (((this.ticksProgress - 60) / 120F) * 14F);
		}
		
		if (!plus)
		{
			offset1 *= -1;
		}
		
		
		double dx = this.getHomePosition().getX() + (alongX ? offset0 : offset1);
		double dy = this.getHomePosition().getY() + Math.cos(this.ticksProgress / 7F + this.getNumber());
		double dz = this.getHomePosition().getZ() + (alongX ? offset1 : offset0);
		return new Vec3d(dx, dy, dz);
	}

	protected Vec3d getCirclePosition(float distance, boolean clockwise) {
		float angle = (this.ticksProgress * 2.0F);
		
		if (!clockwise)
		{
			angle *= -1;
		}
		
		angle += (60F * this.getNumber());
		
		double dx = this.getHomePosition().getX() + Math.cos((angle) * Math.PI / 180.0D) * distance;
		double dy = this.getHomePosition().getY() + Math.cos(this.ticksProgress / 7F + this.getNumber());
		double dz = this.getHomePosition().getZ() + Math.sin((angle) * Math.PI / 180.0D) * distance;
		return new Vec3d(dx, dy, dz);
	}

	private Vec3d getHoverPosition(float distance) {
		// bound this by distance so we don't hover in walls if we get knocked into them
		
		double dx = this.lastTickPosX;
		double dy = this.getHomePosition().getY() + Math.cos(this.ticksProgress / 7F + this.getNumber());
		double dz = this.lastTickPosZ;
		
		// let's just bound this by 2D distance
		double ox = (this.getHomePosition().getX() - dx);
		double oz = (this.getHomePosition().getZ() - dz);
		double dDist = Math.sqrt(ox * ox + oz * oz);
		
		if (dDist > distance)
		{
//			System.out.println("Phantom hovering beyond normal bounds");
			
			// normalize back to boundaries
			
			dx = this.getHomePosition().getX() + (ox / dDist * distance);
			dz = this.getHomePosition().getZ() + (oz / dDist * distance);
		}
		
		return new Vec3d(dx, dy, dz);
	}

	private Vec3d getLoiterPosition() {
		double dx = this.getHomePosition().getX();
		double dy = this.getHomePosition().getY() + Math.cos(this.ticksProgress / 7F + this.getNumber());
		double dz = this.getHomePosition().getZ();
		return new Vec3d(dx, dy, dz);
	}


	private Vec3d getAttackPlayerPosition() {
		if (isSwordKnight())
		{
			return new Vec3d(this.chargePos);
		}
		else
		{
			return getHoverPosition(CIRCLE_LARGE_RADIUS);
		}

	}


	public boolean isSwordKnight() {
		return this.getHeldItemMainhand() != null && this.getHeldItemMainhand().getItem() == TFItems.knightlySword;
	}
	public boolean isAxeKnight() {
		return this.getHeldItemMainhand() != null && this.getHeldItemMainhand().getItem() == TFItems.knightlyAxe;
	}
	public boolean isPickKnight() {
		return this.getHeldItemMainhand() != null && this.getHeldItemMainhand().getItem() == TFItems.knightlyPick;
	}


	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
		
		// set weapon per number
		switch (number % 3)
		{
		case 0:
	        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(TFItems.knightlySword));
	        break;
		case 1:
	        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(TFItems.knightlyAxe));
	        break;
		case 2:
	        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(TFItems.knightlyPick));
	        break;
		}
	}
	
    @Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
    	BlockPos home = this.getHomePosition();
        nbttagcompound.setTag("Home", newDoubleNBTList(new double[] {
        		home.getX(), home.getY(), home.getZ()
            }));
        nbttagcompound.setBoolean("HasHome", this.hasHome());
        nbttagcompound.setInteger("MyNumber", this.getNumber());
        nbttagcompound.setInteger("Formation", this.getFormationAsNumber());
        nbttagcompound.setInteger("TicksProgress", this.getTicksProgress());
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
            this.setHomeArea(new BlockPos(hx, hy, hz), 20);
        }
        if (!nbttagcompound.getBoolean("HasHome"))
        {
        	this.detachHome();
        }
        this.setNumber(nbttagcompound.getInteger("MyNumber"));
        this.switchToFormationByNumber(nbttagcompound.getInteger("Formation"));
        this.setTicksProgress(nbttagcompound.getInteger("TicksProgress"));
    }

//    public boolean func_110173_bK()
//    {
//        return this.func_110176_b(MathHelper.floor(this.posX), MathHelper.floor(this.posY), MathHelper.floor(this.posZ));
//    }

    public boolean isWithinHomeArea(BlockPos pos)
    {
        return this.maximumHomeDistance == -1.0F ? true : this.homePosition.distanceSq(pos) < this.maximumHomeDistance * this.maximumHomeDistance;
    }

    public void setHomeArea(BlockPos pos, int par4)
    {
        this.homePosition = pos;
        this.maximumHomeDistance = (float)par4;

    }

    public BlockPos getHomePosition()
    {
        return this.homePosition;
    }

    public float getMaximumHomeDistance()
    {
        return this.maximumHomeDistance;
    }

    public void detachHome()
    {
        this.maximumHomeDistance = -1.0F;
    }

    public boolean hasHome()
    {
        return this.maximumHomeDistance != -1.0F;
    }
    
    
}
