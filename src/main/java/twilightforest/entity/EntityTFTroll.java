package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackRanged;
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
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.block.TFBlocks;
import twilightforest.entity.boss.EntityTFIceBomb;
import twilightforest.item.TFItems;

public class EntityTFTroll extends EntityMob implements IRangedAttackMob
{
    private static final DataParameter<Boolean> ROCK_FLAG = EntityDataManager.createKey(EntityTFTroll.class, DataSerializers.BOOLEAN);

    private EntityAIAttackRanged aiArrowAttack = new EntityAIAttackRanged(this, 1.0D, 20, 60, 15.0F);
    private EntityAIAttackMelee aiAttackOnCollide = new EntityAIAttackMelee(this, 1.2D, false);

	public EntityTFTroll(World par1World)
    {
        super(par1World);
        this.setSize(1.4F, 2.4F);
    }

    @Override
    public void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, false, null));

        if (worldObj != null && !worldObj.isRemote)
        {
            this.setCombatTask();
        }
    }

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(ROCK_FLAG, false);
    }
    
    public boolean hasRock() {
        return this.dataManager.get(ROCK_FLAG);
    }

    public void setHasRock(boolean rock) {
        if (rock) {
            this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
            this.dataManager.set(ROCK_FLAG, true);
        } else {
            this.dataManager.set(ROCK_FLAG, false);
        }
        
        this.setCombatTask();
    }
    
	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
        swingArm(EnumHand.MAIN_HAND);
		return super.attackEntityAsMob(par1Entity);
	}

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("HasRock", this.hasRock());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setHasRock(par1NBTTagCompound.getBoolean("HasRock"));
    }
    
    
    /**
     * sets this entity's combat AI.
     */
    private void setCombatTask()
    {
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);

        if (this.hasRock()) {
            this.tasks.addTask(4, this.aiArrowAttack);
        } else {
            this.tasks.addTask(4, this.aiAttackOnCollide);
        }
    }

    @Override
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
		BlockPos pos = new BlockPos(this);
		
		int range = 12;
		for (int dx = -range; dx < range; dx++) {
			for (int dy = -range; dy < range; dy++) {
				for (int dz = -range; dz < range; dz++) {
                    ripenBer(offset, pos.add(dx, dy, dz));
				}
			}
		}
	}


	private void ripenBer(int offset, BlockPos pos) {
		if (this.worldObj.getBlockState(pos).getBlock() == TFBlocks.unripeTrollBer && this.rand.nextBoolean() && (Math.abs(pos.getX() + pos.getY() + pos.getZ()) % 5 == offset)) {
			this.worldObj.setBlockState(pos, TFBlocks.trollBer.getDefaultState());
			worldObj.playEvent(2004, pos, 0);
		}
	}


	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
		}
	}
		
    private void makeTrollStoneInAABB(AxisAlignedBB par1AxisAlignedBB)
    {
    	//System.out.println("Destroying blocks in " + par1AxisAlignedBB);
    	
        int minX = MathHelper.ceiling_double_int(par1AxisAlignedBB.minX);
        int minY = MathHelper.ceiling_double_int(par1AxisAlignedBB.minY);
        int minZ = MathHelper.ceiling_double_int(par1AxisAlignedBB.minZ);
        int maxX = MathHelper.floor(par1AxisAlignedBB.maxX);
        int maxY = MathHelper.floor(par1AxisAlignedBB.maxY);
        int maxZ = MathHelper.floor(par1AxisAlignedBB.maxZ);

        for (BlockPos pos : BlockPos.getAllInBox(new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ))) {
            if (worldObj.isAirBlock(pos)) {
                worldObj.setBlockState(pos, TFBlocks.trollSteinn.getDefaultState());
                worldObj.playEvent(2001, pos, Block.getStateId(TFBlocks.trollSteinn.getDefaultState()));
            }
        }
    }

    @Override
    protected Item getDropItem()
    {
        return null;
    }

    protected void dropRareDrop(int p_70600_1_)
    {
        this.dropItem(TFItems.magicBeans, 1);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float par2)
    {
    	if (this.hasRock()) {

    		EntityTFIceBomb ice = new EntityTFIceBomb(this.worldObj, this);

    		double d0 = target.posX - this.posX;
    		double d1 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D - target.posY;
    		double d2 = target.posZ - this.posZ;
    		float f1 = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
    		ice.setThrowableHeading(d0, d1 + (double)f1, d2, 0.75F, 12.0F);

    		this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
    		this.worldObj.spawnEntity(ice);
    	}
    }

    @Override
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
        return 6; // todo 1.9 reconcile with EntityLivingBase's version
    }
}
