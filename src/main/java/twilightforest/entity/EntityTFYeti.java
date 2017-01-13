package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.entity.ai.EntityAITFThrowRider;
import twilightforest.item.TFItems;

import javax.annotation.Nullable;

public class EntityTFYeti extends EntityMob
{

	private static final DataParameter<Byte> ANGER_FLAG = EntityDataManager.createKey(EntityTFYeti.class, DataSerializers.BYTE);

	public EntityTFYeti(World par1World)
    {
        super(par1World);
        this.setSize(1.4F, 2.4F);
    }

    @Override
    protected void initEntityAI() {
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.tasks.addTask(1, new EntityAITFThrowRider(this, 1.0F));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0F));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, false, null));
    }

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.38D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(4.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(ANGER_FLAG, (byte) 0);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
	public void onLivingUpdate()
    {
    	if (!this.getPassengers().isEmpty())
    	{
    		this.setSize(1.4F, 2.4F);
    		
            // stop player sneaking so that they can't dismount!
            if (this.getPassengers().get(0).isSneaking())
            {
            	//System.out.println("Pinch beetle sneaking detected!");
            	
            	this.getPassengers().get(0).setSneaking(false);
            }
    	}
    	else
    	{
    		this.setSize(1.4F, 2.4F);

    	}
    	
    	super.onLivingUpdate();

    	// look at things in our jaws
    	if (!this.getPassengers().isEmpty())
    	{
            this.getLookHelper().setLookPositionWithEntity(getPassengers().get(0), 100F, 100F);

            // push out of user in wall
            Vec3d riderPos = this.getRiderPosition(getPassengers().get(0));
            this.pushOutOfBlocks(riderPos.xCoord, riderPos.yCoord, riderPos.zCoord);
            

    	}
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack)
    {
        if (super.processInteract(player, hand, stack))
        {
            return true;
        }
//        else if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == par1EntityPlayer))
//        {
//            par1EntityPlayer.mountEntity(this);
//            return true;
//        }
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
    	if (par1DamageSource.getSourceOfDamage() != null) {
    		// become angry
    		this.setAngry(true);
    	}
    	
    	return super.attackEntityFrom(par1DamageSource, par2);
    }


    
    /**
     * Determines whether this yeti is angry or not.
     */
    public boolean isAngry() {
        return (this.dataManager.get(ANGER_FLAG) & 2) != 0;
    }

    /**
     * Sets whether this yeti is angry or not.
     */
    public void setAngry(boolean anger) {
        byte b0 = this.dataManager.get(ANGER_FLAG);

        if (anger) {
            this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
            this.dataManager.set(ANGER_FLAG, (byte) (b0 | 2));
        } else {
            this.dataManager.set(ANGER_FLAG, (byte) (b0 & -3));
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("Angry", this.isAngry());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setAngry(par1NBTTagCompound.getBoolean("Angry"));
    }

	/**
     * Put the player out in front of where we are
     */
    @Override
	public void updatePassenger(Entity passenger)
    {
        Vec3d riderPos = this.getRiderPosition(passenger);
        passenger.setPosition(riderPos.xCoord, riderPos.yCoord, riderPos.zCoord);
    }
    
    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    @Override
	public double getMountedYOffset()
    {
        return 2.25D;
    }
    
    /**
     * Used to both get a rider position and to push out of blocks
     */
    private Vec3d getRiderPosition(@Nullable Entity passenger)
    {
    	if (passenger != null)
    	{
    		float distance = 0.4F;

    		double var1 = Math.cos((this.rotationYaw + 90) * Math.PI / 180.0D) * distance;
    		double var3 = Math.sin((this.rotationYaw + 90) * Math.PI / 180.0D) * distance;

    		return new Vec3d(this.posX + var1, this.posY + this.getMountedYOffset() + passenger.getYOffset(), this.posZ + var3);
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

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
		}
	}

	@Override
	public boolean getCanSpawnHere()
    {
		// are we in the snow
		if (worldObj.getBiome(new BlockPos(this)) == TFBiomeBase.tfSnow) {
			// don't check light level
	        return worldObj.checkNoEntityCollision(getEntityBoundingBox()) && worldObj.getCollisionBoxes(this, getEntityBoundingBox()).size() == 0;
		}
		else {
			// normal EntityMob spawn check, checks light level
			return super.getCanSpawnHere();
		}
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
	@Override
	protected boolean isValidLightLevel() {
		if (worldObj.getBiome(new BlockPos(this)) == TFBiomeBase.tfSnow) {
			return true;
		} else {
			return super.isValidLightLevel();
		}
	}

    @Override
    protected Item getDropItem()
    {
        return TFItems.arcticFur;
    }

	
}
