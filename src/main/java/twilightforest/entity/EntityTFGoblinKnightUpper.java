package twilightforest.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import twilightforest.entity.ai.EntityAITFHeavySpearAttack;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityTFGoblinKnightUpper extends EntityMob {
	
	private static final int SHIELD_DAMAGE_THRESHOLD = 10;

	private static final int DATA_EQUIP = 17;
	
	public int shieldHits;

	public int heavySpearTimer;


	public EntityTFGoblinKnightUpper(World par1World) {
		super(par1World);
        //texture = TwilightForestMod.MODEL_DIR + "doublegoblin.png";
        //moveSpeed = 0.28F;
        setSize(1.1F, 1.3F);

        this.tasks.addTask(0, new EntityAITFHeavySpearAttack(this));
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false));
		
		this.setHasArmor(true);
		this.setHasShield(true);
		
        //this.setCurrentItemOrArmor(0, new ItemStack(Items.SWORDSTEEL));

		
		this.shieldHits = 0;
	}

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D); // movement speed
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D); // attack damage
    }
	
	@Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(DATA_EQUIP, Byte.valueOf((byte)0));
    }
	
    public boolean hasArmor()
    {
        return (dataWatcher.getWatchableObjectByte(DATA_EQUIP) & 1) > 0;
    }

    public void setHasArmor(boolean flag)
    {
    	byte otherFlags = dataWatcher.getWatchableObjectByte(DATA_EQUIP);
    	otherFlags &= 126;
    	
        if (flag)
        {
            dataWatcher.updateObject(DATA_EQUIP, Byte.valueOf((byte) (otherFlags | 1)));
        }
        else
        {
            dataWatcher.updateObject(DATA_EQUIP, Byte.valueOf((byte)otherFlags));
        }
    }
    
    public boolean hasShield()
    {
        return (dataWatcher.getWatchableObjectByte(DATA_EQUIP) & 2) > 0;
    }

    public void setHasShield(boolean flag)
    {
    	byte otherFlags = dataWatcher.getWatchableObjectByte(DATA_EQUIP);
    	otherFlags &= 125;
    	
        if (flag)
        {
            dataWatcher.updateObject(DATA_EQUIP, Byte.valueOf((byte) (otherFlags | 2)));
        }
        else
        {
            dataWatcher.updateObject(DATA_EQUIP, Byte.valueOf((byte)otherFlags));
        }
    }
    
	@Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("hasArmor", this.hasArmor());
        par1NBTTagCompound.setBoolean("hasShield", this.hasShield());
    }

	@Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setHasArmor(par1NBTTagCompound.getBoolean("hasArmor"));
        this.setHasShield(par1NBTTagCompound.getBoolean("hasShield"));
    }
    
    public void onUpdate()
    {
        if (this.isEntityAlive())
        {
            // synch target with lower goblin
        	if (this.ridingEntity != null && this.ridingEntity instanceof EntityLiving && this.getAttackTarget() == null)
        	{
        		this.setAttackTarget(((EntityLiving) this.ridingEntity).getAttackTarget());
        	}

        	// keep doing attack timer
        	if (this.heavySpearTimer > 0)
        	{
        		--this.heavySpearTimer;
        		
        		if (this.heavySpearTimer == 25)
        		{
        			//System.out.println("Landing spear attack in world " + this.worldObj);
        			
        			this.landHeavySpearAttack();
        		}
        	}
        	
        	// break shield if we're on the ground
        	if (this.ridingEntity == null && this.hasShield())
        	{
        		this.breakShield();
        	}
        }

        super.onUpdate();
    }
	
    @SuppressWarnings("unchecked")
	private void landHeavySpearAttack() {
		
    	
    	// find vector in front of us
		Vec3d vector = this.getLookVec();
		
		double dist = 1.25;
		double px = this.posX + vector.xCoord * dist;
		double py = this.getEntityBoundingBox().minY - 0.75;
		double pz = this.posZ + vector.zCoord * dist;

		
		for (int i = 0; i < 50; i++)
		{
			//worldObj.spawnParticle("crit", px + (rand.nextFloat() - rand.nextFloat()) * 0.5F, py + rand.nextFloat(), pz + (rand.nextFloat() - rand.nextFloat()) * 0.5F, 0, 0.5, 0);
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, px, py, pz, (rand.nextFloat() - rand.nextFloat()) * 0.25F, 0, (rand.nextFloat() - rand.nextFloat()) * 0.25F);
		}
		
		// damage things in front that aren't us or our "mount"
		double radius = 1.5D;

		AxisAlignedBB spearBB =  new AxisAlignedBB(px - radius, py - radius, pz - radius, px + radius, py + radius, pz + radius);

		List<Entity> inBox = worldObj.getEntitiesWithinAABB(Entity.class, spearBB);
		
		for (Entity entity : inBox)
		{
			if (!getPassengers().contains(entity) && entity != this)
			{
				super.attackEntityAsMob(entity);
			}
		}

	}

    public int getAttackStrength(Entity par1Entity)
    {
    	if (this.heavySpearTimer > 0)
    	{
    		return 20;
    	}
    	else
    	{
    		return 8;
    	}
    }

	@Override
	public void updateRidden()
	{
//		if (this.ridingEntity != null)
//		{
//			this.renderYawOffset = ((EntityLiving)this.ridingEntity).renderYawOffset;
//		}
		super.updateRidden();
		if (this.ridingEntity != null)
		{
			this.renderYawOffset = ((EntityLiving)this.ridingEntity).renderYawOffset;
		}
	}
	

    @SideOnly(Side.CLIENT)
	@Override
    public void handleStatusUpdate(byte par1)
    {
        if (par1 == 4)
        {
            this.heavySpearTimer = 60;
            //this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
        }
        else
        {
            super.handleStatusUpdate(par1);
        }
    }

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		
		if (this.heavySpearTimer > 0)
		{
			return false;
		}
		
        if (rand.nextInt(2) == 0)
        {
        	// do mega spear attack
        	this.startHeavySpearAttack();
        	
        	return false;
        }
        
        // normal attack
        this.swingItem();
        
        //System.out.println("Spear!");
		
		return super.attackEntityAsMob(par1Entity);
	}


    private void startHeavySpearAttack() {
        //System.out.println("Heavy Spear!");

    	
        this.heavySpearTimer = 60;
        this.worldObj.setEntityState(this, (byte)4);
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float damageAmount) {
		// don't take suffocation damage while riding
		if (par1DamageSource == DamageSource.inWall && !this.getPassengers().isEmpty())
		{
			return false;
		}
		
		// check the angle of attack, if applicable
    	Entity attacker = null;
        if (par1DamageSource.getSourceOfDamage() != null)
        {
        	attacker = par1DamageSource.getSourceOfDamage();
        }

        if (par1DamageSource.getEntity() != null)
        {
        	attacker = par1DamageSource.getEntity();
        }
        
        if (attacker != null)
        {
        	// determine angle
        	
	    	double dx = this.posX - attacker.posX;
	    	double dz = this.posZ - attacker.posZ;
	    	float angle = (float)((Math.atan2(dz, dx) * 180D) / Math.PI) - 90F;
	
	    	float difference = MathHelper.abs((this.renderYawOffset - angle) % 360);
	    	
	    	//System.out.println("Difference in angle of approach is " + difference);
	
	    	// if we have a shield, the shield will take the damage instead
	    	if (this.hasShield() && difference > 150 && difference < 230)
	    	{
		    	if (takeHitOnShield(par1DamageSource, damageAmount))
		    	{
		    		return false;
		    	}
	    	}
	    	else
	    	{
	    		// unblocked hits may eventually break shield
	    		if (this.hasShield() && rand.nextBoolean())
	    		{
	    			damageShield();
	    		}
	    	}
	    	
	    	// break armor?
	    	if (this.hasArmor() && (difference > 300 || difference < 60))
	    	{
		    	breakArmor();
	    	}
        }
        
        boolean attackSuccess = super.attackEntityFrom(par1DamageSource, damageAmount);
        
        if (attackSuccess && this.ridingEntity != null && this.ridingEntity instanceof EntityLiving && attacker != null)
        {
        	((EntityLiving)this.ridingEntity).knockBack(attacker, damageAmount, 0.1, 0.1);
        }
		
		// 
		return attackSuccess;
	}

	public void breakArmor() {
		this.renderBrokenItemStack(new ItemStack(Items.IRON_CHESTPLATE));
		this.renderBrokenItemStack(new ItemStack(Items.IRON_CHESTPLATE));
		this.renderBrokenItemStack(new ItemStack(Items.IRON_CHESTPLATE));
		
		this.setHasArmor(false);
	}
	
	public void breakShield() {
		
		this.renderBrokenItemStack(new ItemStack(Items.IRON_CHESTPLATE));
		this.renderBrokenItemStack(new ItemStack(Items.IRON_CHESTPLATE));
		this.renderBrokenItemStack(new ItemStack(Items.IRON_CHESTPLATE));
		
		this.setHasShield(false);
	}


	public boolean takeHitOnShield(DamageSource par1DamageSource, float damageAmount) {
		//System.out.println("Hit blocked by shield");
		
		if (damageAmount > SHIELD_DAMAGE_THRESHOLD && !this.worldObj.isRemote)
		{
			damageShield();
		}
		else
		{
			this.worldObj.playSoundAtEntity(this, "random.break", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
		}
		

		// knock back slightly
		EntityLiving toKnockback = (this.ridingEntity != null && this.ridingEntity instanceof EntityLiving) ?  (EntityLiving)this.ridingEntity : this;
		
        if (par1DamageSource.getEntity() != null)
        {
            double d0 = par1DamageSource.getEntity().posX - this.posX;
            double d1;

            for (d1 = par1DamageSource.getEntity().posZ - this.posZ; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D)
            {
                d0 = (Math.random() - Math.random()) * 0.01D;
            }
        	
            toKnockback.knockBack(par1DamageSource.getEntity(), 0, d0 / 4D, d1 / 4D);
            
            // also set revenge target
            if (par1DamageSource.getEntity() instanceof EntityLiving)
            {
                this.setRevengeTarget((EntityLiving)par1DamageSource.getEntity());
            }
        }

		
		return true;
	}


	private void damageShield() {
		// door break noise
		//this.worldObj.playAuxSFX(1010, MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 0);
		this.worldObj.playSoundAtEntity(this, "mob.zombie.metal", 0.25F, 0.25F);
		
		this.shieldHits++;
		
		if (!worldObj.isRemote && this.shieldHits >= 3)
		{
			this.breakShield();
		}
	}

	@Override
    public int getTotalArmorValue()
    {
        int armor = super.getTotalArmorValue();
        
        if (this.hasArmor())
        {
        	armor += 20; // todo 1.9 attributes
        }

        if (armor > 20)
        {
            armor = 20;
        }

        return armor;
    }

    @Override
    protected Item getDropItem()
    {
        return TFItems.armorShard;
    }
    

}
