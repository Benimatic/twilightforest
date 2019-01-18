package twilightforest.entity;

import twilightforest.entity.ai.EntityAITFGiantAttackOnCollide;
import twilightforest.item.TFItems;
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
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityTFGiantMiner extends EntityMob {

	public EntityTFGiantMiner(World par1World) {
		super(par1World);
		this.setSize(this.width * 4.0F, this.height * 4.0F);


		this.tasks.addTask(1, new EntityAISwimming(this));

        this.tasks.addTask(4, new EntityAITFGiantAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		
        this.setCurrentItemOrArmor(0, new ItemStack(Items.stone_pickaxe));
        
        for (int i = 0; i < this.equipmentDropChances.length; ++i)
        {
            this.equipmentDropChances[i] = 0F;
        }
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D*1.5D+twilightforest.TwilightForestMod.Scatter.nextInt(40)-twilightforest.TwilightForestMod.Scatter.nextInt(40));
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D*1.5D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D*1.5D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D*1.5D);
	}
	

    /**
     * Returns true if the newer Entity AI code should be run
     */
    @Override
	protected boolean isAIEnabled() {
        return true;
    }

    
    protected Item getDropItem()
    {
        return TFItems.giantPick;
    }
    
    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        Item item = this.getDropItem();
        // just drop 1 item every time
        if (item != null && par1) {
            this.dropItem(item, 1);
        }
    }
    
    public void makeNonDespawning() {
    	this.func_110163_bv();
    }

}
