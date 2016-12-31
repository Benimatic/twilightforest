package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
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
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import twilightforest.TFAchievementPage;
import twilightforest.TwilightForestMod;
import twilightforest.entity.ai.EntityAITFMagicAttack;
import twilightforest.item.TFItems;

public class EntityTFDeathTome extends EntityMob {

	public EntityTFDeathTome(World par1World) {
		super(par1World);
        //texture = "/item/book.png";

        //this.attackStrength = 4;
        //this.moveSpeed = 0.25F;
        
        this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(4, new EntityAITFMagicAttack(this, 1.0F, 2, 60));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0F));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

    public int getAttackStrength(Entity par1Entity)
    {
        return 4;
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
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D*1.5D+twilightforest.TwilightForestMod.Scatter.nextInt(15)-twilightforest.TwilightForestMod.Scatter.nextInt(15)); // max health
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D*1.5D); // movement speed
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D*1.5);
    }

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
        for (int i = 0; i < 1; ++i)
        {
            this.worldObj.spawnParticle("enchantmenttable", this.posX + (this.rand.nextFloat() - 0.5D) * this.width, this.posY + this.rand.nextFloat() * (this.height - 0.75D) + 0.5D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, 
            		0, 0.5, 0);
        }
	}
	
    /**
     * Called when the entity is attacked.
     */
    @Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
    	if (par1DamageSource.isFireDamage())
    	{
    		par2 *= 2;
    	}
    	
    	if (super.attackEntityFrom(par1DamageSource, par2))
        {
        	// we took damage
    		if (this.rand.nextInt(2) == 0)
    		{
    			func_145778_a(Items.paper, 1, 1.0F);
    		}
            return true;
        }
        else
        {
            return false;
        }
    }
	
    /**
     * Returns the item ID for the item the mob drops on death.
     */
    @Override
	protected Item getDropItem()
    {
        return Items.paper;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    @Override
	protected void dropFewItems(boolean par1, int par2)
    {
        int var3 = this.rand.nextInt(3 + par2);
        int var4;

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Items.paper, 1);
        }

        if (this.rand.nextInt(5) - par2 <= 0)
        {
        	this.dropItem(Items.writable_book, 1);
        }
        else
        {
        	this.dropItem(Items.book, 1);
        }
    }

    @Override
	protected void dropRareDrop(int par1)
    {
        this.dropItem(TFItems.magicMapFocus, 1);
    }
	

    /**
     * Trigger achievement when killed
     */
	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (worldObj.provider.dimensionId == TwilightForestMod.dimensionID && par1DamageSource.getSourceOfDamage() instanceof EntityPlayer) {
			((EntityPlayer)par1DamageSource.getSourceOfDamage()).triggerAchievement(TFAchievementPage.twilightHunter);
		}
	}

}
