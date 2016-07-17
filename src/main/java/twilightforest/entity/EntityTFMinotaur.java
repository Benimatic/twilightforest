package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.entity.ai.EntityAITFChargeAttack;
import twilightforest.item.TFItems;

public class EntityTFMinotaur extends EntityMob implements ITFCharger {

    private static final DataParameter<Boolean> CHARGING = EntityDataManager.createKey(EntityTFMinotaur.class, DataSerializers.BOOLEAN);

	public EntityTFMinotaur(World par1World) {
		super(par1World);
		//this.texture = TwilightForestMod.MODEL_DIR + "minotaur.png";

        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_AXE));
	}

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAITFChargeAttack(this, 2.0F));
        this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, false, true, null));
    }

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
    }

	@Override
    protected void entityInit()
    {
        super.entityInit();
        dataManager.register(CHARGING, false);
    }

    @Override
    public boolean isCharging()
    {
        return dataManager.get(CHARGING);
    }

    @Override
    public void setCharging(boolean flag)
    {
        dataManager.set(CHARGING, flag);
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        boolean success = super.attackEntityAsMob(par1Entity);

        if (success && this.isCharging())
        {
            par1Entity.motionY += 0.4000000059604645D;
            this.worldObj.playSoundAtEntity(this, "mob.irongolem.throw", 1.0F, 1.0F);
        }

        return success;
    }

    @Override
	public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	
    	//when charging, move legs fast
    	if (isCharging())
    	{
			this.limbSwingAmount += 0.6;
    	}

    }

    @Override
	protected SoundEvent getAmbientSound()
    {
        return "mob.cow.say";
    }

    @Override
	protected SoundEvent getHurtSound()
    {
        return "mob.cow.hurt";
    }

    @Override
	protected SoundEvent getDeathSound()
    {
        return "mob.cow.hurt";
    }

    @Override
	protected void playStepSound(BlockPos pos, Block par4)
    {
        this.worldObj.playSoundAtEntity(this, "mob.cow.step", 0.15F, 0.8F);
    }

    @Override
	protected float getSoundPitch()
    {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.7F;
    }

    @Override
	protected Item getDropItem()
    {
        return TFItems.meefRaw;
    }

    @Override
	protected void dropFewItems(boolean par1, int par2)
    {
        int numDrops = this.rand.nextInt(2) + this.rand.nextInt(1 + par2);

        for (int i = 0; i < numDrops; ++i)
        {
            if (this.isBurning())
            {
                this.dropItem(TFItems.meefSteak, 1);
            }
            else
            {
                this.dropItem(TFItems.meefRaw, 1);
            }
        }
    }
    
    @Override
	protected void dropRareDrop(int par1)
    {
        this.dropItem(TFItems.mazeMapFocus, 1);
    }

    
//    /**
//     * Initialize this creature.
//     */
//    @Override
//	public void initCreature()
//    {
//    	//this.func_82164_bB();
//        this.func_82162_bC();
//    }

    @Override
    public void onDeath(DamageSource par1DamageSource) {
    	super.onDeath(par1DamageSource);
    	if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
    		((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
    	}
    }

}
