package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import twilightforest.TFAchievementPage;
import twilightforest.block.TFBlocks;
import twilightforest.entity.ai.EntityAITFCollideAttackFixed;
import twilightforest.entity.boss.EntityTFIceBomb;
import twilightforest.item.TFItems;

public class EntityTFTroll extends EntityMob implements IRangedAttackMob
{
    private static final int ROCK_FLAG = 16;
    
    private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
    private EntityAITFCollideAttackFixed aiAttackOnCollide = new EntityAITFCollideAttackFixed(this, EntityPlayer.class, 1.2D, false);

	public EntityTFTroll(World par1World)
    {
        super(par1World);
        this.setSize(1.4F, 2.4F);

        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
    
        if (par1World != null && !par1World.isRemote)
        {
            this.setCombatTask();
        }
    }
	

    /**
     * Returns true if the newer Entity AI code should be run
     */
    @Override
	protected boolean isAIEnabled()
    {
        return true;
    }
    
	/**
	 * Set monster attributes
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D); // movement speed
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
    }
	
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(ROCK_FLAG, Byte.valueOf((byte)0));
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
	public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    }
    
    
    /**
     * Determines whether this troll has a rock or not.
     */
    public boolean hasRock() {
        return (this.dataWatcher.getWatchableObjectByte(ROCK_FLAG) & 2) != 0;
    }

    /**
     * Sets whether this troll has a rock or not.
     */
    public void setHasRock(boolean rock) {
        byte b0 = this.dataWatcher.getWatchableObjectByte(ROCK_FLAG);

        if (rock) {
            this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
            this.dataWatcher.updateObject(ROCK_FLAG, Byte.valueOf((byte)(b0 | 2)));
        } else {
            this.dataWatcher.updateObject(ROCK_FLAG, Byte.valueOf((byte)(b0 & -3)));
        }
        
        this.setCombatTask();
    }
    
    /**
     * Swing!
     */
	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		
        this.swingItem();
		
		return super.attackEntityAsMob(par1Entity);
	}
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("HasRock", this.hasRock());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setHasRock(par1NBTTagCompound.getBoolean("HasRock"));
    }
    
    
    /**
     * sets this entity's combat AI.
     */
    public void setCombatTask()
    {
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);

        if (this.hasRock()) {
            this.tasks.addTask(4, this.aiArrowAttack);
        } else {
            this.tasks.addTask(4, this.aiAttackOnCollide);
        }
    }

    /**
	 * handles entity death timer, experience orb and particle creation
	 */
	protected void onDeathUpdate() {
		super.onDeathUpdate();
		
		if (this.deathTime % 5 == 0) {
			this.ripenTrollBerNearby(this.deathTime / 5);
		}
		
		if (this.deathTime == 1) {
			//this.makeTrollStoneInAABB(this.boundingBox);
		}
	}


	private void ripenTrollBerNearby(int offset) {
		int sx = MathHelper.floor_double(this.posX);
		int sy = MathHelper.floor_double(this.posY);
		int sz = MathHelper.floor_double(this.posZ);
		
		int range = 12;
		for (int dx = -range; dx < range; dx++) {
			for (int dy = -range; dy < range; dy++) {
				for (int dz = -range; dz < range; dz++) {
					if (true) {
						int cx = sx + dx;
						int cy = sy + dy;
						int cz = sz + dz;

						ripenBer(offset, cx, cy, cz);
					}
				}
			}
		}
	}


	private void ripenBer(int offset, int cx, int cy, int cz) {
		if (this.worldObj.getBlock(cx, cy, cz) == TFBlocks.unripeTrollBer && this.rand.nextBoolean() && (Math.abs(cx + cy + cz) % 5 == offset)) {
			this.worldObj.setBlock(cx, cy, cz, TFBlocks.trollBer);
			worldObj.playAuxSFX(2004, cx, cy, cz, 0);

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
		}
		
	}
		
	/**
     * Turns blocks to trollstone inside the given bounding box.
     */
    private void makeTrollStoneInAABB(AxisAlignedBB par1AxisAlignedBB)
    {
    	//System.out.println("Destroying blocks in " + par1AxisAlignedBB);
    	
        int minX = MathHelper.ceiling_double_int(par1AxisAlignedBB.minX);
        int minY = MathHelper.ceiling_double_int(par1AxisAlignedBB.minY);
        int minZ = MathHelper.ceiling_double_int(par1AxisAlignedBB.minZ);
        int maxX = MathHelper.floor_double(par1AxisAlignedBB.maxX);
        int maxY = MathHelper.floor_double(par1AxisAlignedBB.maxY);
        int maxZ = MathHelper.floor_double(par1AxisAlignedBB.maxZ);

        for (int dx = minX; dx <= maxX; ++dx)
        {
            for (int dy = minY; dy <= maxY; ++dy)
            {
                for (int dz = minZ; dz <= maxZ; ++dz)
                {
                    Block currentID = this.worldObj.getBlock(dx, dy, dz);
                    
                    if (currentID == Blocks.air)
                    {
                    	this.worldObj.setBlock(dx, dy, dz, TFBlocks.trollSteinn);
                        // here, this effect will have to do
            			worldObj.playAuxSFX(2001, dx, dy, dz, Block.getIdFromBlock(TFBlocks.trollSteinn) + (0 << 12));
                     }
                }
            }
        }
    }
    
    protected Item getDropItem()
    {
        return null;
    }

    protected void dropRareDrop(int p_70600_1_)
    {
        this.dropItem(TFItems.magicBeans, 1);
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void attackEntityWithRangedAttack(EntityLivingBase target, float par2)
    {
    	if (this.hasRock()) {

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

    
    /**
     * Swings the item the player is holding.
     */
    public void swingItem() {
        if (!this.isSwingInProgress || this.swingProgressInt >= this.getArmSwingAnimationEnd() / 2 || this.swingProgressInt < 0) {
            this.swingProgressInt = -1;
            this.isSwingInProgress = true;

            if (this.worldObj instanceof WorldServer)
            {
                ((WorldServer)this.worldObj).getEntityTracker().func_151247_a(this, new S0BPacketAnimation(this, 0));
            }
        }
    }
	
    /**
     * Updates the arm swing progress counters and animation progress
     */
    protected void updateArmSwingProgress()
    {
        int maxSwing = this.getArmSwingAnimationEnd();

        if (this.isSwingInProgress)
        {
            ++this.swingProgressInt;

            if (this.swingProgressInt >= maxSwing)
            {
                this.swingProgressInt = 0;
                this.isSwingInProgress = false;
            }
        }
        else
        {
            this.swingProgressInt = 0;
        }

        this.swingProgress = (float)this.swingProgressInt / (float)maxSwing;
    }
    
    /**
     * Returns an integer indicating the end point of the swing animation, used by {@link #swingProgress} to provide a
     * progress indicator. Takes dig speed enchantments into account.
     */
    private int getArmSwingAnimationEnd()
    {
        return 6;
    }
}
