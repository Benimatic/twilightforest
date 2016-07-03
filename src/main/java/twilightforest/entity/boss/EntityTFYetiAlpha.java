package twilightforest.entity.boss;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.entity.ai.EntityAIStayNearHome;
import twilightforest.entity.ai.EntityAITFThrowRider;
import twilightforest.entity.ai.EntityAITFYetiRampage;
import twilightforest.entity.ai.EntityAITFYetiTired;
import twilightforest.item.TFItems;
import twilightforest.world.ChunkProviderTwilightForest;
import twilightforest.world.TFBiomeProvider;
import twilightforest.world.WorldProviderTwilightForest;

public class EntityTFYetiAlpha extends EntityMob implements IRangedAttackMob
{

	
    private static final int RAMPAGE_FLAG = 16;
	private static final int TIRED_FLAG = 17;
	private int collisionCounter;
	private boolean canRampage;



	public EntityTFYetiAlpha(World par1World)
    {
        super(par1World);
        this.setSize(3.8F, 5.0F);

        
        this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(1, new EntityAITFYetiTired(this, 100));
		this.tasks.addTask(2, new EntityAITFThrowRider(this, 1.0F));
		this.tasks.addTask(3, new EntityAIStayNearHome(this, 2.0F));
		this.tasks.addTask(4, new EntityAITFYetiRampage(this, 10, 180));

		this.tasks.addTask(5, new EntityAIAttackRanged(this, 1.0D, 40, 40, 40.0F));
        this.tasks.addTask(6, new EntityAIWander(this, 2.0F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false));
        
        this.experienceValue = 317;

    }
	
	@Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(RAMPAGE_FLAG, Byte.valueOf((byte)0));
        this.dataWatcher.addObject(TIRED_FLAG, Byte.valueOf((byte)0));
    }
    
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.38D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
    }

    @Override
	public void onLivingUpdate()
    {
    	if (!this.getPassengers().isEmpty())
    	{
    		
            // stop player sneaking so that they can't dismount!
            if (this.getPassengers().get(0).isSneaking())
            {
            	//System.out.println("Pinch beetle sneaking detected!");
            	
            	this.getPassengers().get(0).setSneaking(false);
            }
    	}

    	super.onLivingUpdate();

    	// look at things we are holding
    	if (this.riddenByEntity != null)
    	{
            this.getLookHelper().setLookPositionWithEntity(riddenByEntity, 100F, 100F);
    	}
    	
    	if (this.isCollided) {
    		this.collisionCounter++;
    	}
    	
    	if (this.collisionCounter >= 15) {
    		if (!this.worldObj.isRemote) {
    			this.destroyBlocksInAABB(this.getEntityBoundingBox());
    		}
    		this.collisionCounter = 0;
    	}
    	
    	// add rampage snow effects
    	if (this.isRampaging()) {
    		
    		float rotation = this.ticksExisted / 10F;
    		
    		
    		for (int i = 0; i < 20; i++) {
    			addSnowEffect(rotation + (i * 50), i + rotation);
    		}

    		// also swing limbs
			this.limbSwingAmount += 0.6;
    	}
    	
    	//when tired, spawn tears/sweat
    	if (this.isTired())
    	{
    		for (int i = 0; i < 20; i++)
    		{
    			this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (this.rand.nextDouble() - 0.5D) * this.width * 0.5, this.posY + this.getEyeHeight(), this.posZ + (this.rand.nextDouble() - 0.5D) * this.width * 0.5, (rand.nextFloat() - 0.5F) * 0.75F, 0, (rand.nextFloat() - 0.5F) * 0.75F);
    		}
    	}

    }


	private void addSnowEffect(float rotation, float hgt) {
		double px = 3F * Math.cos(rotation);
		double py = hgt % 5F;
		double pz = 3F * Math.sin(rotation);
		
		TwilightForestMod.proxy.spawnParticle(this.worldObj, "snowstuff", this.lastTickPosX + px, this.lastTickPosY + py, this.lastTickPosZ + pz, 0, 0, 0);
	}
    

	/**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    @Override
	public boolean interact(EntityPlayer par1EntityPlayer)
    {
        if (super.interact(par1EntityPlayer))
        {
            return true;
        }
        else if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == par1EntityPlayer))
        {
            par1EntityPlayer.mountEntity(this);
            return true;
        }
        else
        {
            return false;
        }
    }
	
    @Override
	public boolean attackEntityAsMob(Entity par1Entity) 
    {
    	if (this.getPassengers().isEmpty() && par1Entity.getRidingEntity() == null)
    	{
    		par1Entity.startRiding(this);
    	}
    	
		return super.attackEntityAsMob(par1Entity);
	}
    
    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
    	// no arrow damage when in ranged mode
    	if (!this.canRampage && !this.isTired() && par1DamageSource.isProjectile()) {
    		return false;
    	}
    	
        boolean success = super.attackEntityFrom(par1DamageSource, par2);
        
        this.canRampage = true;
        
        return success;
    }
    
    @Override
    protected void dropFewItems(boolean flag, int looting) {
    	Item fur = getDropItem();
    	if(fur != null)
    	{
    		int drops = 6 + rand.nextInt(6 + looting);
    		for(int d = 0; d < drops; d++)
    		{
    			this.dropItem(fur, 1);
    		}

    	}
    	
    	Item bombs = TFItems.iceBomb;
		int drops = 6 + rand.nextInt(6 + looting);
		for(int d = 0; d < drops; d++)
		{
			this.dropItem(bombs, 1);
		}

    }
    
    @Override
	protected Item getDropItem()
    {
        return TFItems.alphaFur;
    }
    
    @Override
	public void updatePassenger(Entity passenger)
    {
        if (passenger != null)
        {
        	Vec3d riderPos = this.getRiderPosition();
        	
            passenger.setPosition(riderPos.xCoord, riderPos.yCoord, riderPos.zCoord);
        }
    }
    
    @Override
	public double getMountedYOffset()
    {
        return 5.75D;
    }
    
    /**
     * Used to both get a rider position and to push out of blocks
     */
    public Vec3d getRiderPosition()
    {
    	if (this.riddenByEntity != null)
    	{
    		float distance = 0.4F;

    		double var1 = Math.cos((this.rotationYaw + 90) * Math.PI / 180.0D) * distance;
    		double var3 = Math.sin((this.rotationYaw + 90) * Math.PI / 180.0D) * distance;

    		return new Vec3d(this.posX + var1, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + var3);
    	}
    	else
    	{
    		return new Vec3d(this.posX, this.posY, this.posZ);
    	}
    }

    @Override
    public boolean canRiderInteract()
    {
        return true;
    }
    
	/**
     * Destroys all blocks that aren't associated with 'The End' inside the given bounding box.
     */
    public boolean destroyBlocksInAABB(AxisAlignedBB par1AxisAlignedBB)
    {
    	//System.out.println("Destroying blocks in " + par1AxisAlignedBB);
    	
        int minX = MathHelper.floor_double(par1AxisAlignedBB.minX);
        int minY = MathHelper.floor_double(par1AxisAlignedBB.minY);
        int minZ = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        int maxX = MathHelper.floor_double(par1AxisAlignedBB.maxX);
        int maxY = MathHelper.floor_double(par1AxisAlignedBB.maxY);
        int maxZ = MathHelper.floor_double(par1AxisAlignedBB.maxZ);
        boolean wasBlocked = false;
        for (int dx = minX; dx <= maxX; ++dx)
        {
            for (int dy = minY; dy <= maxY; ++dy)
            {
                for (int dz = minZ; dz <= maxZ; ++dz)
                {
                    Block currentID = this.worldObj.getBlock(dx, dy, dz);
                    
                    if (currentID != Blocks.AIR)
                    {
                    	int currentMeta = this.worldObj.getBlockMetadata(dx, dy, dz);
                    	
                    	if (currentID != Blocks.OBSIDIAN && currentID != Blocks.END_STONE && currentID != Blocks.BEDROCK)
                        {
                            this.worldObj.setBlock(dx, dy, dz, Blocks.AIR, 0, 2);
                            
                            // here, this effect will have to do
                			worldObj.playAuxSFX(2001, dx, dy, dz, Block.getIdFromBlock(currentID) + (currentMeta << 12));
                        }
                        else
                        {
                            wasBlocked = true;
                        }
                    }
                }
            }
        }

        return wasBlocked;
    }



    public void makeRandomBlockFall() {
    	// begin turning blocks into falling blocks
    	makeRandomBlockFall(30);
    }


	private void makeRandomBlockFall(int range) {
		// find a block nearby
    	int bx = MathHelper.floor_double(this.posX) + this.getRNG().nextInt(range) - this.getRNG().nextInt(range);
    	int bz = MathHelper.floor_double(this.posZ) + this.getRNG().nextInt(range) - this.getRNG().nextInt(range);
    	int by = MathHelper.floor_double(this.posY + this.getEyeHeight());

    	makeBlockFallAbove(bx, bz, by);
	}


	private void makeBlockFallAbove(int bx, int bz, int by) {
		if (worldObj.isAirBlock(bx, by, bz)) {
    		for (int i = 1; i < 30; i++) {
    	    	if (!worldObj.isAirBlock(bx, by + i, bz)) {
    	    		makeBlockFall(bx, by + i, bz);
    	    		break;
    	    	}
    		}
    	}
	}


	public void makeNearbyBlockFall() {
    	makeRandomBlockFall(15);
	}


	public void makeBlockAboveTargetFall() {
		if (this.getAttackTarget() != null) {
		
			int bx = MathHelper.floor_double(this.getAttackTarget().posX);
			int bz = MathHelper.floor_double(this.getAttackTarget().posZ);
			int by = MathHelper.floor_double(this.getAttackTarget().posY + this.getAttackTarget().getEyeHeight());

			makeBlockFallAbove(bx, bz, by);
		}

	}


	private void makeBlockFall(int bx, int by, int bz) {
		//worldObj.setBlock(bx, by, bz, Blocks.GRAVEL);
		
		//EntityFallingBlock sand;
		
        Block currentID = this.worldObj.getBlock(bx, by, bz);
    	int currentMeta = this.worldObj.getBlockMetadata(bx, by, bz);
    	
    	// just set it to ice for now
		worldObj.setBlock(bx, by, bz, Blocks.PACKED_ICE);

		worldObj.playAuxSFX(2001, bx, by, bz, Block.getIdFromBlock(currentID) + (currentMeta << 12));

		
		EntityTFFallingIce ice = new EntityTFFallingIce(worldObj, bx, by - 3, bz);
		worldObj.spawnEntityInWorld(ice);


	}

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float par2)
    {
    	if (!this.canRampage) {

    		EntityTFIceBomb ice = new EntityTFIceBomb(this.worldObj, this);

    		double d0 = target.posX - this.posX;
    		double d1 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D - target.posY;
    		double d2 = target.posZ - this.posZ;
    		float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2) * 0.2F;
    		ice.setThrowableHeading(d0, d1 + (double)f1, d2, 0.75F, 12.0F);

    		this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
    		this.worldObj.spawnEntityInWorld(ice);
    	}
    }

    @Override
    public boolean canDespawn() {
    	return false;
    }
    
    public boolean canRampage() {
    	return this.canRampage;
    }

    /**
     * Set whether this yeti is currently in rampage mode.
     */
    public void setRampaging(boolean par1)
    {
        this.getDataWatcher().updateObject(RAMPAGE_FLAG, Byte.valueOf((byte)(par1 ? 1 : 0)));
    }

    /**
     * Return whether this yeti is currently in rampage mode.
     */
    public boolean isRampaging()
    {
        return this.getDataWatcher().getWatchableObjectByte(RAMPAGE_FLAG) == 1;
    }

    /**
     * Set whether this yeti is currently tired.
     */
    public void setTired(boolean par1)
    {
        this.getDataWatcher().updateObject(TIRED_FLAG, Byte.valueOf((byte)(par1 ? 1 : 0)));
        this.canRampage = false;
    }

    /**
     * Return whether this yeti is currently tired.
     */
    public boolean isTired()
    {
        return this.getDataWatcher().getWatchableObjectByte(TIRED_FLAG) == 1;
    }
    
    @Override
    public void fall(float par1, float mult)
    {
        super.fall(par1, mult);
        
        if (this.isRampaging()) {
        	// make jump effects
			this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
			
            int i = MathHelper.floor_double(this.posX);
            int j = MathHelper.floor_double(this.posY - 0.20000000298023224D - (double)this.getYOffset());
            int k = MathHelper.floor_double(this.posZ);			

            this.worldObj.playAuxSFX(2006, i, j, k, 20);
            this.worldObj.playAuxSFX(2006, i, j, k, 30);
            
            // hit everything around
            if (!this.worldObj.isRemote) {
            	hitNearbyEntities();
            }
            
        }
    }


	private void hitNearbyEntities() {
		List<Entity> nearby = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(5, 0, 5));
		
		for (Entity entity : nearby) {
			if (entity instanceof EntityLivingBase) {
				
				boolean hit = entity.attackEntityFrom(DamageSource.causeMobDamage(this), 5F);
				
		        if (hit)
		        {
		        	entity.motionY += 0.4000000059604645D;
		        }
			}
		}
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightProgressYeti);
		}
		
		// mark the lair as defeated
		if (!worldObj.isRemote) {
			int dx = MathHelper.floor_double(this.posX);
			int dy = MathHelper.floor_double(this.posY);
			int dz = MathHelper.floor_double(this.posZ);
			
			if (worldObj.provider instanceof WorldProviderTwilightForest){
				ChunkProviderTwilightForest chunkProvider = ((WorldProviderTwilightForest)worldObj.provider).getChunkProvider();
				TFFeature nearbyFeature = ((TFBiomeProvider)worldObj.provider.worldChunkMgr).getFeatureAt(dx, dz, worldObj);

				if (nearbyFeature == TFFeature.yetiCave) {
					chunkProvider.setStructureConquered(dx, dy, dz, true);
				}
			}
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
            this.setHomePosAndDistance(new BlockPos(hx, hy, hz), 30);
        }
        if (!nbttagcompound.getBoolean("HasHome"))
        {
        	this.detachHome();
        }
     }

}
