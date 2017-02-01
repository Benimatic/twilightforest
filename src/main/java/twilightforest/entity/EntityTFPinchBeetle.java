package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.entity.ai.EntityAITFChargeAttack;
import twilightforest.entity.ai.EntityAITFKidnapRider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityTFPinchBeetle extends EntityMob
{
	public EntityTFPinchBeetle(World world) {
		super(world);
		//texture = TwilightForestMod.MODEL_DIR + "pinchbeetle.png";
		setSize(1.2F, 1.1F);
	}

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAITFKidnapRider(this, 2.0F));
        this.tasks.addTask(2, new EntityAITFChargeAttack(this, 2.0F));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        //this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, false, null));
    }

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    @Override
	public int getTotalArmorValue()
    {
        int var1 = super.getTotalArmorValue() + 2;

        if (var1 > 20)
        {
            var1 = 20;
        }

        return var1;
    }

    @Override
	protected String getHurtSound()
    {
        return "mob.spider.say";
    }

    @Override
	protected String getDeathSound()
    {
        return "mob.spider.death";
    }

    @Override
	protected void playStepSound(BlockPos pos, Block var4)
    {
        this.world.playSoundAtEntity(this, "mob.spider.step", 0.15F, 1.0F);
    }

    @Override
	public void onLivingUpdate()
    {
    	if (!this.getPassengers().isEmpty())
    	{
    		this.setSize(1.9F, 2.0F);
    		
            // stop player sneaking so that they can't dismount!
            if (this.getPassengers().get(0).isSneaking())
            {
            	//System.out.println("Pinch beetle sneaking detected!");
            	
            	this.getPassengers().get(0).setSneaking(false);
            }
    	}
    	else
    	{
    		this.setSize(1.2F, 1.1F);

    	}
    	
    	super.onLivingUpdate();

    	// look at things in our jaws
    	if (!this.getPassengers().isEmpty())
    	{
            this.getLookHelper().setLookPositionWithEntity(getPassengers().get(0), 100F, 100F);
    		//this.faceEntity(riddenByEntity, 100F, 100F);

            
            // push out of user in wall
            Vec3d riderPos = this.getRiderPosition();
            this.pushOutOfBlocks(riderPos.xCoord, riderPos.yCoord, riderPos.zCoord); // push out of block
            

    	}
    }

    @Override
    public void onDeath(DamageSource par1DamageSource) {
    	super.onDeath(par1DamageSource);
    	if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
    		((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
    	}
    }

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() 
	{
		return 1.1F;
	}

    @Override
	public boolean attackEntityAsMob(Entity par1Entity) 
    {
    	if (this.getPassengers().isEmpty() && !par1Entity.isRiding())
    	{
    		par1Entity.startRiding(this);
    	}
    	
		return super.attackEntityAsMob(par1Entity);
	}

    @Override
	public boolean processInteract(EntityPlayer par1EntityPlayer, EnumHand hand, @Nullable ItemStack stack)
    {
        if (super.processInteract(par1EntityPlayer, hand, stack))
        {
            return true;
        }
//        else if (!this.world.isRemote && (this.riddenByEntity == null || this.riddenByEntity == par1EntityPlayer))
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
	public float getEyeHeight() {
		return 0.25F;
	}

    @Override
	public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
	public void updatePassenger(Entity passenger)
    {
        if (!this.getPassengers().isEmpty())
        {
        	Vec3d riderPos = this.getRiderPosition();
        	
            this.getPassengers().get(0).setPosition(riderPos.xCoord, riderPos.yCoord, riderPos.zCoord);
        }
    }

    @Override
	public double getMountedYOffset()
    {
        return 0.75D;
    }

    public Vec3d getRiderPosition()
    {
    	if (!this.getPassengers().isEmpty())
    	{
    		float distance = 0.9F;

    		double var1 = Math.cos((this.rotationYaw + 90) * Math.PI / 180.0D) * distance;
    		double var3 = Math.sin((this.rotationYaw + 90) * Math.PI / 180.0D) * distance;

    		return new Vec3d(this.posX + var1, this.posY + this.getMountedYOffset() + this.getPassengers().get(0).getYOffset(), this.posZ + var3);
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
}
