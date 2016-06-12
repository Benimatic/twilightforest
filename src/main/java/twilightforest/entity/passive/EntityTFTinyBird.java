package twilightforest.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import twilightforest.TFAchievementPage;
import twilightforest.TwilightForestMod;
import twilightforest.entity.ai.EntityAITFBirdFly;


public class EntityTFTinyBird extends EntityTFBird {
	
	private static final int DATA_BIRDTYPE = 16;
    private static final int DATA_BIRDFLAGS = 17;
	
	/**
     * randomly selected BlockPos in a 7x6x7 box around the bat (y offset -2 to 4) towards which it will fly.
     * upon getting close a new target will be selected
     */
    private BlockPos currentFlightTarget;
    private int currentFlightTime;

    public EntityTFTinyBird(World par1World) {
		super(par1World);
		//texture = TwilightForestMod.MODEL_DIR + "tinybirdbrown.png";
		
        this.setSize(0.5F, 0.9F);
		
		// maybe this will help them move cuter?
		//this.stepHeight = 2;
		
		// bird AI
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAITFBirdFly(this));
        this.tasks.addTask(1, new EntityAITempt(this, 1.0F, Items.WHEAT_SEEDS, true));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0F));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        
        // random color
        setBirdType(rand.nextInt(4));
        
        setIsBirdLanded(true);
	}
	
	@Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(DATA_BIRDTYPE, Byte.valueOf((byte)0));
        this.dataWatcher.addObject(DATA_BIRDFLAGS, Byte.valueOf((byte)0));
    }
	
	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0D); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000001192092896D);
    }
    
//    /**
//     * Returns the texture's file path as a String.
//     */
//	@Override
//    public String getTexture()
//    {
//        switch (this.getBirdType())
//        {
//            case 0:
//                return TwilightForestMod.MODEL_DIR + "tinybirdbrown.png";
//
//            case 1:
//                return TwilightForestMod.MODEL_DIR + "tinybirdblue.png";
//
//            case 2:
//                return TwilightForestMod.MODEL_DIR + "tinybirdred.png";
//
//            case 3:
//                return TwilightForestMod.MODEL_DIR + "tinybirdgold.png";
//
//            default:
//                return super.getTexture();
//        }
//    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("BirdType", this.getBirdType());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
	@Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setBirdType(par1NBTTagCompound.getInteger("BirdType"));
    }

    public int getBirdType()
    {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    public void setBirdType(int par1)
    {
        this.dataWatcher.updateObject(DATA_BIRDTYPE, Byte.valueOf((byte)par1));
    }
	
    /**
     * Returns the sound this mob makes while it's alive.
     */
	@Override
    protected String getLivingSound()
    {
        return TwilightForestMod.ID + ":mob.tinybird.chirp";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
	@Override
    protected String getHurtSound()
    {
        return TwilightForestMod.ID + ":mob.tinybird.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
	@Override
    protected String getDeathSound()
    {
        return TwilightForestMod.ID + ":mob.tinybird.hurt";
    }

	/**
	 * Actually only used for the shadow
	 */
	@Override
	public float getRenderSizeModifier() {
		 return 0.3F;
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
     * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
     * Args: x, y, z
     */
	@Override
    public float getBlockPathWeight(int par1, int par2, int par3)
    {
    	// prefer standing on leaves
		Material underMaterial = this.worldObj.getBlock(par1, par2 - 1, par3).getMaterial();
		if (underMaterial == Material.leaves) {
			return 200.0F;
		}
		if (underMaterial == Material.wood) {
			return 15.0F;
		}
		if (underMaterial == Material.grass) {
			return 9.0F;
		}
		// default to just prefering lighter areas
		return this.worldObj.getLightBrightness(par1, par2, par3) - 0.5F;
    }

    /**
     * Trigger achievement when killed
     */
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).triggerAchievement(TFAchievementPage.twilightHunter);
		}
	}
	
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        
        // while we are flying, try to level out somewhat
        if (!this.isBirdLanded())
        {
        	this.motionY *= 0.6000000238418579D;
        }
        
        
    }

    protected void updateAITasks()
    {
        super.updateAITasks();

        if (this.isBirdLanded())
        {
        	this.currentFlightTime = 0;
        	
            if (this.rand.nextInt(200) == 0 && !isLandableBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 1), MathHelper.floor_double(this.posZ)))
            {
                this.setIsBirdLanded(false);
                this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1015, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
                this.motionY = 0.4;
                //FMLLog.info("bird taking off because it is no longer on land");
            }
            else
            {
                if (isSpooked())
                {
                    this.setIsBirdLanded(false);
                    this.motionY = 0.4;
                    this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1015, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
                    //FMLLog.info("bird taking off because it was spooked");
                }
            }
        }
        else
        {
        	this.currentFlightTime++;
        	
            if (this.currentFlightTarget != null && (!this.worldObj.isAirBlock(this.currentFlightTarget.posX, this.currentFlightTarget.posY, this.currentFlightTarget.posZ) || this.currentFlightTarget.posY < 1))
            {
                this.currentFlightTarget = null;
            }

            if (this.currentFlightTarget == null || this.rand.nextInt(30) == 0 || this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0F)
            {
            	int yTarget = this.currentFlightTime < 100 ? 2 : 4; 
            	
                this.currentFlightTarget = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - yTarget, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
            }

            double d0 = (double)this.currentFlightTarget.posX + 0.5D - this.posX;
            double d1 = (double)this.currentFlightTarget.posY + 0.1D - this.posY;
            double d2 = (double)this.currentFlightTarget.posZ + 0.5D - this.posZ;
            this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
            this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
            this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
            float f = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
            float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
            this.moveForward = 0.5F;
            this.rotationYaw += f1;


            if (this.rand.nextInt(10) == 0 && isLandableBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 1), MathHelper.floor_double(this.posZ)))
            {


            		this.setIsBirdLanded(true);

            		this.motionY = 0;

            		//this.posY = MathHelper.floor_double(posY);
            		//FMLLog.info("bird landing");
            }
        }
    }

    /**
     * Return true if the bird is spooked
     */
	public boolean isSpooked() {
		EntityPlayer closestPlayer = this.worldObj.getClosestPlayerToEntity(this, 4.0D);
		
		return this.hurtTime > 0 || (closestPlayer != null && (closestPlayer.inventory.getCurrentItem() == null || closestPlayer.inventory.getCurrentItem().getItem() != Items.WHEAT_SEEDS));
	}
    
    
    /**
     * Can the bird land here?
     */
    public boolean isLandableBlock(int x, int y, int z)
    {
        Block block = this.worldObj.getBlock(x, y, z);
        
        if (block == Blocks.AIR)
        {
            return false;
        }
        else
        {
        	return block.isLeaves(worldObj, x, y, z) || block.isSideSolid(worldObj, x, y, z, ForgeDirection.UP);
        }
    }

    
    public boolean isBirdLanded()
    {
        return (this.dataWatcher.getWatchableObjectByte(DATA_BIRDFLAGS) & 1) != 0;
    }

    public void setIsBirdLanded(boolean par1)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(DATA_BIRDFLAGS);

        if (par1)
        {
            this.dataWatcher.updateObject(DATA_BIRDFLAGS, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.dataWatcher.updateObject(DATA_BIRDFLAGS, Byte.valueOf((byte)(b0 & -2)));
        }
    }
    
    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return false;
    }

    protected void collideWithEntity(Entity par1Entity) {}

    protected void func_85033_bc() {}
}
