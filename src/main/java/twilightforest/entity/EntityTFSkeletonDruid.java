
package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
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
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.item.TFItems;



public class EntityTFSkeletonDruid extends EntityMob implements IRangedAttackMob
{

	public EntityTFSkeletonDruid(World world)
	{
		super(world);
		//texture = TwilightForestMod.MODEL_DIR + "skeletondruid.png";

		//this.moveSpeed = 0.25F;
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIRestrictSun(this));
		this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
		this.tasks.addTask(4, new EntityAIAttackRanged(this, 1.0D, 60, 10.0F));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true, false, null));
		
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_HOE));

	}

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    protected String getLivingSound()
    {
        return "mob.skeleton.say";
    }

    @Override
    protected String getHurtSound()
    {
        return "mob.skeleton.hurt";
    }

    @Override
    protected String getDeathSound()
    {
        return "mob.skeleton.death";
    }

    @Override
    protected void playStepSound(BlockPos pos, Block par4)
    {
        this.playSound("mob.skeleton.step", 0.15F, 1.0F);
    }

    @Override
	protected Item getDropItem()
    {
        return TFItems.torchberries;
    }
    
    @Override
    protected void dropFewItems(boolean par1, int lootingModifier)
    {
    	int numberOfItemsToDrop;
    	int i;

    	numberOfItemsToDrop = this.rand.nextInt(3 + lootingModifier);

    	for (i = 0; i < numberOfItemsToDrop; ++i)
    	{
    		this.dropItem(TFItems.torchberries, 1);
    	}

    	numberOfItemsToDrop = this.rand.nextInt(3 + lootingModifier);

    	for (i = 0; i < numberOfItemsToDrop; ++i)
    	{
    		this.dropItem(Items.BONE, 1);
    	}
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).addStat(TFAchievementPage.twilightHunter);
		}
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase attackTarget, float extraDamage) {
		EntityTFNatureBolt natureBolt = new EntityTFNatureBolt(this.worldObj, this);
		this.worldObj.playSoundAtEntity(this, "mob.ghast.fireball", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
		
		natureBolt.setTarget(attackTarget);

		double tx = attackTarget.posX - this.posX;
		double ty = attackTarget.posY + attackTarget.getEyeHeight() - 2.699999988079071D - this.posY;
		double tz = attackTarget.posZ - this.posZ;
		float heightOffset = MathHelper.sqrt_double(tx * tx + tz * tz) * 0.2F;
		natureBolt.setThrowableHeading(tx, ty + heightOffset, tz, 0.6F, 6.0F); // 0.6 speed, 6.0 inaccuracy
		this.worldObj.spawnEntityInWorld(natureBolt);

	}
	
    @Override
    protected boolean isValidLightLevel()
    {
    	boolean valid = false;
        int dx = MathHelper.floor_double(this.posX);
        int dy = MathHelper.floor_double(this.getEntityBoundingBox().minY);
        int dz = MathHelper.floor_double(this.posZ);
		BlockPos pos = new BlockPos(dx, dy, dz);

        if (this.worldObj.getLightFor(EnumSkyBlock.SKY, pos) > this.rand.nextInt(32))
        {
        	valid = false;
        }
        else
        {
            int light = this.worldObj.getLight(pos);

             valid = light <= this.rand.nextInt(12);

        }
    	return valid;
    }
}
